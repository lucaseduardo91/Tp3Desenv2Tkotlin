package com.infnetkot.tp3desenv2tkotlin.ui.listatarefas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

import com.infnetkot.tp3desenv2tkotlin.R
import com.infnetkot.tp3desenv2tkotlin.adapter.TarefaAdapter
import com.infnetkot.tp3desenv2tkotlin.dao.TarefaDao
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa
import com.infnetkot.tp3desenv2tkotlin.util.TarefaStorage
import kotlinx.android.synthetic.main.fragment_lista_tarefas.*

class ListaTarefasFragment : Fragment() {

    private lateinit var tarefaStorage: TarefaStorage
    private lateinit var listener: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_tarefas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().let { act->
            tarefaStorage = ViewModelProviders.of(act)
                .get(TarefaStorage::class.java) }

        var dialogo = LoadingAlerta(requireActivity())

        listener = TarefaDao().setUpTarefaSnapshotListener{
                qSnapshot, err ->
            if (err != null){
                Toast.makeText(activity,"Problema na sincronização da lista",Toast.LENGTH_SHORT)
            } else {
                var lista = qSnapshot!!.toObjects(Tarefa::class.java)
                if(lista != null)
                {
                    var listagem = requireActivity().findViewById<RecyclerView>(R.id.listagem_tarefas)
                    listagem.adapter = TarefaAdapter(lista,requireActivity(),tarefaStorage)
                    listagem.layoutManager = LinearLayoutManager(this.context)
                }
                else
                {
                    dialogo.startLoadingDialog("Carregando tarefas...")
                    setupRecyclerView(dialogo)
                }

            }
        }

        dialogo.startLoadingDialog("Carregando tarefas...")
        setupRecyclerView(dialogo)
    }

    override fun onStop() {
        super.onStop()
        if(listener != null)
            listener.remove()
    }

    fun carregarLista(report: (Boolean, String, QuerySnapshot?) -> Unit) {
        TarefaDao().all()
            .addOnFailureListener {
                report(false, it.message.toString(), null)
            }
            .addOnSuccessListener {
                report(true,
                    "Consulta realizada com sucesso.", it)
            }
    }

    fun setupRecyclerView(dialogo : LoadingAlerta) {

        carregarLista { status, msg, querySnapshot ->
            if (status) {
                if (querySnapshot != null && !querySnapshot?.isEmpty) {
                    dialogo.dismiss()
                    listagem_tarefas.adapter = TarefaAdapter(querySnapshot.toObjects(Tarefa::class.java),requireActivity(),tarefaStorage)
                    listagem_tarefas.layoutManager = LinearLayoutManager(this.context)
                }
                else{
                        dialogo.dismiss()
                        listagem_tarefas.visibility = View.GONE
                        empty_view_tarefas.visibility = View.VISIBLE
                    }
            }
            else {
                dialogo.dismiss()
                Toast.makeText(requireActivity(),"Falha na consulta de tarefas!",Toast.LENGTH_SHORT)
            }
        }
    }
}
