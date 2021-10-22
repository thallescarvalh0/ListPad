package br.edu.ifsp.scl.sdm.pa1.listpad.utils

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseInstance {
    val dbFirestore = Firebase.firestore
}