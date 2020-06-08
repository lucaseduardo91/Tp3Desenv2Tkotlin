package com.infnetkot.tp3desenv2tkotlin.dao

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa

class TarefaDao {
    val coll_name = "tarefas"
    var db = FirebaseFirestore.getInstance()

    fun add(tarefa: Tarefa): Task<DocumentReference> {
        val task = db
            .collection(coll_name)
            .add(tarefa)
        return task
    }

    fun all(): Task<QuerySnapshot> {
        val task = db
            .collection(coll_name)
            .get()
        return task
    }

    fun delete(id : String) : Task<Void>{
        var task = db.collection(coll_name).document(id)
            .delete()
        return task
    }

    fun update(id : String, descricao : String) : Task<Void>{
        var task = db.collection(coll_name).document(id)
            .update("descricao", descricao)
        return task
    }
}