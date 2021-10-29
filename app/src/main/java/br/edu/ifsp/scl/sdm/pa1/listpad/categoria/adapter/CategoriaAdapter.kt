package br.edu.ifsp.scl.sdm.pa1.listpad.categoria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model.Categoria
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class CategoriaAdapter(options: FirestoreRecyclerOptions<Categoria>)
    : FirestoreRecyclerAdapter<Categoria, CategoriaAdapter.CategoriaViewHolder> (options) {

    var clickListener: CategoriaClickListener?=null

    inner class CategoriaViewHolder(view: View):RecyclerView.ViewHolder(view){
        val descricaoCategoria = view.findViewById<TextView>(R.id.tvDescricaoCategoria)
        init {
            view.setOnClickListener{clickListener?.onItemClick(bindingAdapterPosition)}
        }

    }

    interface CategoriaClickListener{ fun onItemClick(position: Int) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriaAdapter.CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoria_celula,parent,false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CategoriaAdapter.CategoriaViewHolder,
        position: Int,
        model: Categoria
    ) {
        holder.descricaoCategoria.text = model.descricao
    }

}
