package com.infnetkot.tp3desenv2tkotlin.model

import com.google.firebase.firestore.DocumentId

data class Tarefa (
    var descricao : String = "",
    @DocumentId var id: String? = null
)