package br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lista(
    val nome: String? = null,
    val descricao: String? = null,
    val categoria: String? = null,
    val urgente: Boolean = false
):Parcelable
