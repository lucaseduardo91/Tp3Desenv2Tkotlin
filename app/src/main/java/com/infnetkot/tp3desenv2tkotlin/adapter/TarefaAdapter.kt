package com.infnetkot.tp3desenv2tkotlin.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.infnetkot.tp3desenv2tkotlin.R
import com.infnetkot.tp3desenv2tkotlin.dao.TarefaDao
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa
import com.infnetkot.tp3desenv2tkotlin.util.TarefaStorage

class TarefaAdapter (tarefas : MutableList<Tarefa>, activity: Activity, tarefaStorage: TarefaStorage) :
    RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>(){
    var listaTarefas = tarefas
    var activity = activity
    var tarefaStorage = tarefaStorage

    class TarefaViewHolder(itemView: View)
                            : RecyclerView.ViewHolder(itemView){

        var descricao = itemView.findViewById<TextView>(R.id.texto_tarefa_lista)

        var excluir = itemView.findViewById<ImageButton>(R.id.exclui_tarefa)
        var editar = itemView.findViewById<ImageButton>(R.id.edita_tarefa)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val card = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)

        return TarefaViewHolder(card)
    }

    override fun getItemCount() = listaTarefas.size

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {

        holder.descricao.text = listaTarefas[position].descricao
        holder.excluir.setOnClickListener{
            excluirTarefa(listaTarefas,position, this, activity)
        }
        holder.editar.setOnClickListener {
            tarefaStorage.adicionaTarefa(listaTarefas[position])
            activity.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_editar_tarefa)
        }

    }

    fun excluirTarefa(tarefas : MutableList<Tarefa>,position : Int,adapter: TarefaAdapter, activity: Activity)
    {
        TarefaDao().delete(tarefas[position].id!!).addOnSuccessListener {
            tarefas.removeAt(position)

            adapter.notifyItemRemoved(position);
        }
            .addOnFailureListener {
                Toast.makeText(activity,"Não foi possível excluir a tarefa!",Toast.LENGTH_SHORT)
            }
    }
}