package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCadastroListaBinding

class CadastroListaActivity : AppCompatActivity() {

    private val cadastroListaBinding : ActivityCadastroListaBinding by lazy {
        ActivityCadastroListaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cadastroListaBinding.root)

        title = getString(R.string.cadastrar_lista)

        cadastroListaBinding.btnCadastroLista.setOnClickListener {

            setResult(RESULT_OK)
            finish()
        }
    }
}