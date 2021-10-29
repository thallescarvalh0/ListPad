package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCadastroListaBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.Lista
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.google.firebase.firestore.ktx.toObject

class CadastroListaActivity : AppCompatActivity() {

    private val cadastroListaBinding : ActivityCadastroListaBinding by lazy {
        ActivityCadastroListaBinding.inflate(layoutInflater)
    }

    lateinit var listaID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cadastroListaBinding.root)

        title = getString(R.string.cadastrar_lista)

        listaID = this.intent.getStringExtra(DBConstantes.LISTA_ID_INTENT) as String

        if (listaID.isNotEmpty()){
            FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA)
                .document(listaID).addSnapshotListener{ value, error ->
                    if (value!=null){
                        val listaIntent = value.toObject<Lista>()
                        val nome = listaIntent?.nome.toString()
                        val descricao = listaIntent?.descricao.toString()
                        val ckUrgente = listaIntent?.urgente as Boolean
                        val categoria = listaIntent?.categoria.toString() //TODO("regularizar spinner")

                        cadastroListaBinding.edtNomeLista.setText(nome)
                        cadastroListaBinding.edtDescricaoLista.setText(descricao)
                        cadastroListaBinding.ckUrgenteLista.isChecked = ckUrgente
                        //TODO("REGULARIZAR CATEGORIA")
                        cadastroListaBinding.btnCadastroLista.text = "Salvar"
                        cadastroListaBinding.btnCadastroLista.isEnabled = false

                    }
                }
        }
        cadastroListaBinding.btnCadastroLista.setOnClickListener {

            val list = Lista(
                cadastroListaBinding.edtNomeLista.text.toString(),
                cadastroListaBinding.edtDescricaoLista.text.toString(),
                "categoria Teste",
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
}