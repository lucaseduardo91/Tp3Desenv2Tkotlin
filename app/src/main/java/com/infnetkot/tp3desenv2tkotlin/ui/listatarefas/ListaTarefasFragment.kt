package com.infnetkot.tp3desenv2tkotlin.ui.listatarefas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QuerySnapshot

import com.infnetkot.tp3desenv2tkotlin.R
import com.infnetkot.tp3desenv2tkotlin.adapter.TarefaAdapter
import com.infnetkot.tp3desenv2tkotlin.dao.TarefaDao
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa
import kotlinx.android.synthetic.main.fragment_lista_tarefas.*

class ListaTarefasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_tarefas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dialogo = LoadingAlerta(requireActivity())

        dialogo.startLoadingDialog("Carregando tarefas...")
        setupRecyclerView(dialogo)
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
                    listagem_tarefas.adapter = TarefaAdapter(querySnapshot.toObjects(Tarefa::class.java),requireActivity())
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
