package br.edu.ifsp.scl.sdm.pa1.listpad.categoria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model.Categoria
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityCategoriaBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CategoriaActivity : AppCompatActivity() {

    private val query: Query by lazy {
        FirebaseFirestore.getInstance().collection("categoria")
    }
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

        val options = FirestoreRecyclerOptions.Builder<Categoria>().setQuery(query,Categoria::class.java).build()


    }


}