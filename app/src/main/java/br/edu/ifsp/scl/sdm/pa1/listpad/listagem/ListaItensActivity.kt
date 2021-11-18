package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityListaItensBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.adapter.ListaItensAdapter
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.Lista
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.ListaDetalhes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class ListaItensActivity : AppCompatActivity() {
    
    private val listaItensActivity : ActivityListaItensBinding by lazy {
        ActivityListaItensBinding.inflate(layoutInflater)
    }

    private lateinit var listaID: String
    private lateinit var listaItensAdapter: ListaItensAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(listaItensActivity.root)

        listaID = this.intent.getStringExtra(DBConstantes.LISTA_ID_INTENT) as String

        if (listaID.isNotEmpty()){
            FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA)
                .document(listaID).addSnapshotListener{ value, _ ->
                    if (value!=null){
                        val listaIntent = value.toObject<Lista>()
                        val nome = listaIntent?.nome.toString()
                        title = nome

                    }
                }
            updateItensLista()
        }

        listaItensActivity.adicionarItensLista.setOnClickListener {
            val intent = Intent(applicationContext, CadastroListaItensActivity::class.java)
            intent.putExtra(DBConstantes.LISTA_ID_INTENT, listaID)
            intent.putExtra(DBConstantes.LISTA_ITENS_ID_INTENT, "")
            startActivity(intent)
        }

    }

    override fun onStop() {
        super.onStop()
        listaItensAdapter.stopListening()
    }

    override fun onStart() {
        super.onStart()
        listaItensAdapter.startListening()
    }

    private fun updateItensLista(){
        val query: Query = FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
        document(listaID).collection(DBConstantes.TABLE_LISTA_ITENS)
        val options: FirestoreRecyclerOptions<ListaDetalhes> = FirestoreRecyclerOptions.Builder<ListaDetalhes>()
            .setQuery(query, ListaDetalhes::class.java).build()

        listaItensAdapter = ListaItensAdapter(options)

        val recyclerView = findViewById<RecyclerView>(R.id.rvListaItens)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaItensAdapter

        val clickItemListener = object :ListaItensAdapter.ListaItensClickListener{
            override fun onItemListClick(position: Int) {
                val intent = Intent(applicationContext, CadastroListaItensActivity::class.java)
                intent.putExtra(DBConstantes.LISTA_ID_INTENT, listaItensAdapter.snapshots.getSnapshot(position).id)
                intent.putExtra(DBConstantes.LISTA_ITENS_ID_INTENT, "")
                startActivity(intent)
            }

        }

        listaItensAdapter.clickItemListener = clickItemListener
    }


}