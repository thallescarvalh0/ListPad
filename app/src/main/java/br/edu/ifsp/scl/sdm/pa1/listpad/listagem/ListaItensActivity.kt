package br.edu.ifsp.scl.sdm.pa1.listpad.listagem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa1.listpad.databinding.ActivityListaItensBinding
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.adapter.ListaAdapter
import br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model.Lista
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.DBConstantes
import br.edu.ifsp.scl.sdm.pa1.listpad.utils.FirebaseInstance
import com.google.firebase.firestore.ktx.toObject

class ListaItensActivity : AppCompatActivity() {
    
    private val listaItensActivity : ActivityListaItensBinding by lazy {
        ActivityListaItensBinding.inflate(layoutInflater)
    }

    private lateinit var listaID: String
    private lateinit var listaAdapter: ListaAdapter
    
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
            //updatelista
            listaAdapter.startListening()
        }

    }

    override fun onStop() {
        super.onStop()
        listaAdapter.stopListening()
    }


}