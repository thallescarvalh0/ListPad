package br.edu.ifsp.scl.sdm.pa1.listpad.categoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model.Categoria
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCadastroCategoriaBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance

class CadastroCategoriaActivity : AppCompatActivity() {

    private val activityCadastroCategoriaActivityBinding: ActivityCadastroCategoriaBinding by lazy {
        ActivityCadastroCategoriaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityCadastroCategoriaActivityBinding.root)

        title = getString(R.string.activity_title_cad_categoria)

        activityCadastroCategoriaActivityBinding.btnCadastroCategoria.setOnClickListener { click->
            val c = Categoria (descricao =
            activityCadastroCategoriaActivityBinding.edtDescricaoCategoria.text.toString())

            FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA).add(c)
            finish()
        }

    }
}