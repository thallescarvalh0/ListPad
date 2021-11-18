package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCadastroListaItensBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.ListaDetalhes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.google.firebase.firestore.ktx.toObject


class CadastroListaItensActivity : AppCompatActivity() {


    private val activityListaItensBinding: ActivityCadastroListaItensBinding by lazy {
        ActivityCadastroListaItensBinding.inflate(layoutInflater)
    }

    private lateinit var listaID: String
    private lateinit var listaItemID: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityListaItensBinding.root)

        title = getString(R.string.cadastro_itens_lista)

        listaID = this.intent.getStringExtra(DBConstantes.LISTA_ID_INTENT) as String
        listaItemID = this.intent.getStringExtra(DBConstantes.LISTA_ITENS_ID_INTENT) as String

        if((listaID.isNotEmpty()) && (listaItemID.isNotEmpty())){
            FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
            document(listaID).collection(DBConstantes.TABLE_LISTA_ITENS).document(listaItemID).
            addSnapshotListener { value, _ ->
                if (value != null) {
                    val listaItemIntent = value.toObject<ListaDetalhes>()
                    val descricao = listaItemIntent?.descricao.toString()
                    activityListaItensBinding.edtDescricaoItemLista.setText(descricao)
                    activityListaItensBinding.btnCadastroItemLista.text = getString(R.string.salvar_text_button)
                    activityListaItensBinding.btnCadastroItemLista.isEnabled = false
                }
            }
        }

        activityListaItensBinding.btnCadastroItemLista.setOnClickListener{

            val item = ListaDetalhes(
                activityListaItensBinding.edtDescricaoItemLista.text.toString(), false)

            if ((listaID.isNotEmpty()) && (listaItemID.isNotEmpty())){
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
                document(listaID).collection(DBConstantes.TABLE_LISTA_ITENS).document(listaItemID).set(item)
            }
            else{
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
                document(listaID).collection(DBConstantes.TABLE_LISTA_ITENS).add(item)
            }
            finish()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editarMI ->{
                activityListaItensBinding.btnCadastroItemLista.isEnabled = true
                true
            }
            R.id.removerMI->{
                if ((listaID.isNotEmpty()) && (listaItemID.isNotEmpty())){
                    activityListaItensBinding.edtDescricaoItemLista.setText("")
                    FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
                    document(listaID).collection(DBConstantes.TABLE_LISTA_ITENS).
                    document(listaItemID).delete()
                }
                finish()
                true
            }
            else ->{
                false
            }
        }
    }
}