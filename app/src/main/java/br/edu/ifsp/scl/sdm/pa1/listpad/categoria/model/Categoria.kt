package br.edu.ifsp.scl.sdm.pa1.listpad.categoria.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Categoria(
    val descricao: String? = null
): Parcelable
