package br.edu.ifsp.scl.sdm.pa1.listpad.categoria.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model.Categoria
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class CategoriaAdapter(options: FirestoreRecyclerOptions<Categoria>)
    : FirestoreRecyclerAdapter<Categoria, CategoriaAdapter.CategoriaViewHolder> (options) {

    inner class CategoriaViewHolder(view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriaAdapter.CategoriaViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: CategoriaAdapter.CategoriaViewHolder,
        position: Int,
        model: Categoria
    ) {
        TODO("Not yet implemented")
    }
}
