package br.edu.ifsp.scl.sdm.pa1.listpad.listagem.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.pa1.listpad.R
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.ListaDetalhes
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ListaItensAdapter(options: FirestoreRecyclerOptions<ListaDetalhes>)
    : FirestoreRecyclerAdapter<ListaDetalhes, ListaItensAdapter.ListaItensViewHolder>(options){

    var clickItemListener: ListaItensClickListener?=null

    inner class ListaItensViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nomeLista = view.findViewById<TextView>(R.id.nomeItemEt)
        val ckUrgenteLista = view.findViewById<CheckBox>(R.id.urgenteItemCk)
        init {
            view.setOnClickListener{ clickItemListener?.onItemListClick(bindingAdapterPosition) }
        }

    }

    interface ListaItensClickListener{
        fun onItemListClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): ListaItensViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_lista_itens, parent, false)
        return ListaItensViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListaItensViewHolder,
        position: Int,
        model: ListaDetalhes
    ) {
        holder.nomeLista.text = model.descricao
        holder.ckUrgenteLista.isChecked = model.urgente
    }

}