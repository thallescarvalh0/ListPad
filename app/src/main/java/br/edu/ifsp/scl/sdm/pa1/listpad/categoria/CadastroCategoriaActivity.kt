package br.edu.ifsp.scl.sdm.pa1.listpad.categoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model.Categoria
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCadastroCategoriaBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.google.firebase.firestore.ktx.toObject

class CadastroCategoriaActivity : AppCompatActivity() {

    private val activityCadastroCategoriaActivityBinding: ActivityCadastroCategoriaBinding by lazy {
        ActivityCadastroCategoriaBinding.inflate(layoutInflater)
    }

    lateinit var categoriaID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityCadastroCategoriaActivityBinding.root)

        title = getString(R.string.activity_title_cad_categoria)

        categoriaID = this.intent.getStringExtra(DBConstantes.CATEGORIA_ID_INTENT) as String

        if (categoriaID.isNotEmpty()) {
            FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA)
                .document(categoriaID).addSnapshotListener { value, error ->
                if (value != null) {
                    val cIntent = value.toObject<Categoria>()
                    val descricao = cIntent?.descricao.toString()
                    activityCadastroCategoriaActivityBinding.edtDescricaoCategoria.setText(descricao)
                    activityCadastroCategoriaActivityBinding.btnCadastroCategoria.text = getString(R.string.salvar_text_button)
                    activityCadastroCategoriaActivityBinding.btnCadastroCategoria.isEnabled = false

                }

            }
        }
        activityCadastroCategoriaActivityBinding.btnCadastroCategoria.setOnClickListener { click->

            val c = Categoria (descricao =
            activityCadastroCategoriaActivityBinding.edtDescricaoCategoria.text.toString())

            if (categoriaID.isNotEmpty()) {
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA).
                document(categoriaID).set(c)
            }
            else{
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA).add(c)
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
                activityCadastroCategoriaActivityBinding.btnCadastroCategoria.isEnabled = true
                true
            }
           R.id.removerMI->{
               if (categoriaID.isNotEmpty()){
                   activityCadastroCategoriaActivityBinding.edtDescricaoCategoria.setText("")
                   FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA).
                    document(categoriaID).delete()
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