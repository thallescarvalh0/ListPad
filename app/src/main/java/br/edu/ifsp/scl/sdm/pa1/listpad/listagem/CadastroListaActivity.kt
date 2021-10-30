package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCadastroListaBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.Lista
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.google.firebase.firestore.ktx.toObject
import android.widget.Spinner

class CadastroListaActivity : AppCompatActivity() {

    private val cadastroListaBinding : ActivityCadastroListaBinding by lazy {
        ActivityCadastroListaBinding.inflate(layoutInflater)
    }

    lateinit var listaID: String
    lateinit var categoriaSpinner: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cadastroListaBinding.root)

        title = getString(R.string.cadastrar_lista)

        listaID = this.intent.getStringExtra(DBConstantes.LISTA_ID_INTENT) as String

        if (listaID.isNotEmpty()){
            FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA)
                .document(listaID).addSnapshotListener{ value, _ ->
                    if (value!=null){
                        val listaIntent = value.toObject<Lista>()
                        val nome = listaIntent?.nome.toString()
                        val descricao = listaIntent?.descricao.toString()
                        val ckUrgente = listaIntent?.urgente as Boolean
                        categoriaSpinner = listaIntent.categoria.toString()

                        cadastroListaBinding.edtNomeLista.setText(nome)
                        cadastroListaBinding.edtDescricaoLista.setText(descricao)
                        cadastroListaBinding.ckUrgenteLista.isChecked = ckUrgente
                        cadastroListaBinding.btnCadastroLista.text = "Salvar"
                        cadastroListaBinding.btnCadastroLista.isEnabled = false


                    }
                }
        }

        val listaSpinner = ArrayList<String>()
        val adapter = ArrayAdapter(applicationContext, R.layout.spinner_item,listaSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cadastroListaBinding.spCategoria.adapter = adapter
        FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA).get().addOnCompleteListener{
            if (it.isSuccessful){
                for ( documentSnapshot in ( it.result )!!){
                    val valor = documentSnapshot.getString("descricao")
                    if (valor != null) {
                        listaSpinner.add(valor)
                    }
                }
                if (categoriaSpinner.isNotEmpty()){
                    cadastroListaBinding.spCategoria.setSelection(getIndex(cadastroListaBinding.spCategoria,
                        categoriaSpinner))
                }
                adapter.notifyDataSetChanged()
            }
        }

        cadastroListaBinding.btnCadastroLista.setOnClickListener {

            val list = Lista(
                cadastroListaBinding.edtNomeLista.text.toString(),
                cadastroListaBinding.edtDescricaoLista.text.toString(),
                cadastroListaBinding.spCategoria.selectedItem.toString(),
                cadastroListaBinding.ckUrgenteLista.isChecked
            )

            if (listaID.isNotEmpty()){
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
                document(listaID).set(list)
            }
            else {
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).add(list)
            }

            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)
        menu?.findItem(R.id.removerMI)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
            R.id.editarMI -> {
                cadastroListaBinding.btnCadastroLista.isEnabled = true
                true
            }
            else -> {
                false
            }
        }
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.adapter.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return -1
    }
}