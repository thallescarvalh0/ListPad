package br.edu.ifsp.scl.sdm.pa1.listpad.listagem.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ListaDetalhes(
    val descricao: String? = null,
    val urgente: Boolean = false

): Parcelable

