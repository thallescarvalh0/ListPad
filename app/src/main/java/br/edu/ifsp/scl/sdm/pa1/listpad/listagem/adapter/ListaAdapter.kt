package br.edu.ifsp.scl.sdm.pa1.listpad.listagem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.Lista
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class ListaAdapter(options: FirestoreRecyclerOptions<Lista>)
    :FirestoreRecyclerAdapter<Lista, ListaAdapter.ListaViewHolder> (options)
{

    var clickListener: ListaClickListener?=null

    inner class ListaViewHolder(view: View):RecyclerView.ViewHolder(view){
        val nomeLista = view.findViewById<TextView>(R.id.nomeListaEt)
        val descricaoLista = view.findViewById<TextView>(R.id.descricaoListaEt)
        val categoriaLista = view.findViewById<TextView>(R.id.categoriaListaEt)
        val ckUrgenteLista = view.findViewById<CheckBox>(R.id.urgenteCk)
        init {
            view.setOnClickListener{ clickListener?.onItemClick(bindingAdapterPosition) }

            view.findViewById<ImageView>(R.id.deletarListaImg)
                .setOnClickListener{
                    clickListener?.onImageDeletarClick(bindingAdapterPosition)
                }

            view.findViewById<ImageView>(R.id.editarListaImg)
                .setOnClickListener{
                    clickListener?.onImageEditarClick(bindingAdapterPosition)
                }
        }

    }

    interface ListaClickListener{
        fun onItemClick(position: Int)
        fun onImageDeletarClick(position: Int)
        fun onImageEditarClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_lista, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int, model: Lista) {
        holder.nomeLista.text = model.nome
        holder.descricaoLista.text = model.descricao
        holder.categoriaLista.text = model.categoria
        holder.ckUrgenteLista.isChecked = model.urgente
    }
}