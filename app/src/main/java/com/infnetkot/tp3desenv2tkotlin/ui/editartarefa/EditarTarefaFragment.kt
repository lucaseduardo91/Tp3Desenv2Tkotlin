package com.infnetkot.tp3desenv2tkotlin.ui.editartarefa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.infnetkot.tp3desenv2tkotlin.R
import com.infnetkot.tp3desenv2tkotlin.dao.TarefaDao
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa
import com.infnetkot.tp3desenv2tkotlin.util.TarefaStorage
import kotlinx.android.synthetic.main.fragment_editar_tarefa.*

class EditarTarefaFragment : Fragment() {

    private lateinit var tarefaStorage: TarefaStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_tarefa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().let { act->
            tarefaStorage = ViewModelProviders.of(act)
                .get(TarefaStorage::class.java) }

        texto_edicao_tarefa.setText(tarefaStorage.tarefa!!.descricao)

        btn_editar_tarefa.setOnClickListener {
            var textoDescricao = texto_edicao_tarefa.text.toString()
            if(textoDescricao.isNullOrBlank() || textoDescricao.length > 80)
                Toast.makeText(activity,"A descrição deve possuir de 1 a 80 caracteres",Toast.LENGTH_SHORT)
            else
                atualizarTarefa(textoDescricao)
        }
    }

    fun atualizarTarefa(texto : String)
    {
        var dialogo = LoadingAlerta(requireActivity())
        dialogo.startLoadingDialog("Atualizando...")

        TarefaDao().update(tarefaStorage.tarefa!!.id!!,texto).addOnSuccessListener {
            dialogo.dismiss()
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.nav_tarefas)
        }
            .addOnFailureListener {
                Toast.makeText(activity,"Não foi possível atualizar a tarefa.",Toast.LENGTH_SHORT)
            }
    }
}
