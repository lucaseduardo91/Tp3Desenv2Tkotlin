package com.infnetkot.tp3desenv2tkotlin.util

import androidx.lifecycle.ViewModel
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa

class TarefaStorage : ViewModel(){
    var tarefa : Tarefa? = null

    fun adicionaTarefa(novaTarefa : Tarefa) {
        tarefa = novaTarefa
    }
}