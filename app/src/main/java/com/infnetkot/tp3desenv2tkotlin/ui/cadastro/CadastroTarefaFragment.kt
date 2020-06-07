package com.infnetkot.tp3desenv2tkotlin.ui.cadastro

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.infnetkot.tp3desenv2tkotlin.MenuActivity

import com.infnetkot.tp3desenv2tkotlin.R
import com.infnetkot.tp3desenv2tkotlin.dao.TarefaDao
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import com.infnetkot.tp3desenv2tkotlin.model.Tarefa
import kotlinx.android.synthetic.main.fragment_cadastro_tarefa.*

/**
 * A simple [Fragment] subclass.
 */
class CadastroTarefaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro_tarefa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cadastrar_tarefa.setOnClickListener {
            var descricao = texto_descricao_tarefa.text.toString()

            if(descricao.isNullOrBlank())
                Toast.makeText(activity,"Preencha corretamente o campo descrição",Toast.LENGTH_SHORT)
            else
                if(descricao.length > 80)
                    Toast.makeText(activity,"Descrição não pode ter mais de 80 caracteres",Toast.LENGTH_SHORT)
                else
                    cadastrarTarefa(descricao,requireActivity(),texto_descricao_tarefa)
        }
    }

    fun cadastrarTarefa(descricao : String,activity: Activity,elemento : TextInputEditText)
    {
        var dialogo = LoadingAlerta(activity)
        var tarefa = Tarefa(descricao)

        dialogo.startLoadingDialog("Cadastrando...")

        TarefaDao().add(tarefa).addOnSuccessListener {
            elemento.setText("")
            dialogo.dismiss()
            activity.findNavController(R.id.nav_host_fragment).navigate(R.id.nav_tarefas)
        }
            .addOnFailureListener {
                dialogo.dismiss()
                Toast.makeText(activity,"A tarefa não pode ser cadastrada.",Toast.LENGTH_SHORT)
            }
    }
}
