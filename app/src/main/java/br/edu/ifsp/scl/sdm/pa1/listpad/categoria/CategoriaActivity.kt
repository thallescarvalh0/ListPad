package br.edu.ifsp.scl.sdm.pa1.listpad.categoria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.adapter.CategoriaAdapter
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model.Categoria
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCategoriaBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class CategoriaActivity : AppCompatActivity() {

    private val activityCategoriaBinding: ActivityCategoriaBinding by lazy {
        ActivityCategoriaBinding.inflate(layoutInflater)
    }

    lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityCategoriaBinding.root)

        title = getString(R.string.activity_title_categoria)

        activityCategoriaBinding.adicionarCategoria.setOnClickListener {
            val cadastroCategoriaIntent = Intent(this, CadastroCategoriaActivity::class.java)
            startActivity(cadastroCategoriaIntent)

        }
    }

    override fun onStart() {
        super.onStart()
        updateUI()
        categoriaAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        categoriaAdapter.stopListening()
    }

    private fun updateUI(){
        val query: Query = FirebaseInstance.dbFirestore.collection(DBConstantes.TABLE_CATEGORIA).orderBy("descricao")
        val options: FirestoreRecyclerOptions<Categoria> = FirestoreRecyclerOptions.Builder<Categoria>()
            .setQuery(query,Categoria::class.java).build()

        categoriaAdapter = CategoriaAdapter(options)

        val recyclerview = findViewById<RecyclerView>(R.id.rvListCategoria)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = categoriaAdapter

        val clickListener = object :CategoriaAdapter.CategoriaClickListener{
            override fun onItemClick(pos: Int) {
                //val intent = Intent(applicationContext,DetalheActivity::class.java)
               // intent.putExtra("contatoID", contatoAdapter.snapshots.getSnapshot(pos).id)
               // startActivity(intent)
            }

        }

        categoriaAdapter.clickListener = clickListener
    }
}