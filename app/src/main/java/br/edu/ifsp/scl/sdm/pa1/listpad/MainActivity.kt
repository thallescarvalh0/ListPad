package br.edu.ifsp.scl.sdm.pa1.listpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.CategoriaActivity
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.CadastroListaActivity
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.ListaItensActivity
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.adapter.ListaAdapter
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.Lista
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var listaAdapter: ListaAdapter

    override fun onStart() {
        super.onStart()
        updateLista()
        listaAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        listaAdapter.stopListening()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.cadastro_lista -> {
                val intent = Intent(this, CadastroListaActivity::class.java)
                intent.putExtra(DBConstantes.LISTA_ID_INTENT, "")
                startActivity(intent)
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

    private fun updateLista(){
        val query: Query = FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).orderBy("nome")
        val options: FirestoreRecyclerOptions<Lista> = FirestoreRecyclerOptions.Builder<Lista>()
            .setQuery(query, Lista::class.java).build()

        listaAdapter = ListaAdapter(options)

        val recyclerView = findViewById<RecyclerView>(R.id.rvListagem)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaAdapter

        val clickListener = object :ListaAdapter.ListaClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, ListaItensActivity::class.java)
                intent.putExtra(DBConstantes.LISTA_ID_INTENT, listaAdapter.snapshots.getSnapshot(position).id)
                startActivity(intent)
            }

            override fun onImageDeletarClick(position: Int) {
                FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_LISTA).
                        document(listaAdapter.snapshots.getSnapshot(position).id).delete()
                Toast.makeText(this@MainActivity, "Item excluído", Toast.LENGTH_SHORT).show()
            }

            override fun onImageEditarClick(position: Int) {
                val intent = Intent(applicationContext, CadastroListaActivity::class.java)
                intent.putExtra(DBConstantes.LISTA_ID_INTENT, listaAdapter.snapshots.getSnapshot(position).id)
                startActivity(intent)
            }
        }
        listaAdapter.clickListener = clickListener
    }

}