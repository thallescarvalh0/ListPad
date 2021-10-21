package br.edu.ifsp.scl.sdm.pa1.listpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.CategoriaActivity

import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.CadastroListaActivity

import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object Extras {
        const val EXTRA_LISTA = "EXTRA_LISTA"
        const val EXTRA_POSICAO_LISTA = "EXTRA_POSICAO_LISTA"

    }
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var cadastroListaResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        cadastroListaResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            //todo faz algo após result da tela
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.cadastro_lista -> {
                cadastroListaResultLauncher.launch(Intent(this, CadastroListaActivity::class.java))
                true
            }
            R.id.cadastro_categoria -> {
                val cadastroCategoriaIntent = Intent(this, CategoriaActivity::class.java)
                startActivity(cadastroCategoriaIntent)
                true
            }
            R.id.sair_app -> {
                finish()
                true
            }
            else ->{
                true
            }
        }
    }
}