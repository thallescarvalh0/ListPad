package br.edu.ifsp.scl.sdm.pa1.listpad.categoria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCategoriaBinding

class CategoriaActivity : AppCompatActivity() {

    private val activityCategoriaBinding: ActivityCategoriaBinding by lazy {
        ActivityCategoriaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityCategoriaBinding.root)

        title = getString(R.string.activity_title_categoria)

        activityCategoriaBinding.adicionarCategoria.setOnClickListener {
            val cadastroCategoriaIntent = Intent(this, CadastroCategoriaActivity::class.java)
            startActivity(cadastroCategoriaIntent)

        }
    }


}