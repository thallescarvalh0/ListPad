package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityListaItensBinding

class ListaItensActivity : AppCompatActivity() {
    
    private val listaItensActivity : ActivityListaItensBinding by lazy {
        ActivityListaItensBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(listaItensActivity.root)
        
        title = "Itens Lista ______" // TODO: 16/10/2021 incluir descrição da lista selecionada 
    }
}