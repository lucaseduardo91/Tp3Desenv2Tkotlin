package com.infnetkot.tp3desenv2tkotlin.ui.informacoes

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.infnetkot.tp3desenv2tkotlin.R
import com.infnetkot.tp3desenv2tkotlin.dialog.LoadingAlerta
import com.infnetkot.tp3desenv2tkotlin.services.ObterCotacaoService
import kotlinx.android.synthetic.main.fragment_informacoes.*


class InformacoesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacoes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var cot = texto_cot_dolar

        CarregarInfoDolarAsync(requireActivity(),cot).execute()
    }

    class CarregarInfoDolarAsync(activity: Activity, cot : TextView) : AsyncTask<Void, Void, String?>()
    {
        var activity = activity
        var cot = cot
        //var dialogApi = LoadingAlerta(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            //dialogApi.startLoadingDialog("Buscando cotação do dólar...")
        }

        override fun doInBackground(vararg params: Void?): String? {

            var sucesso = ObterCotacaoService.getInstance().buscarCotacao()
            //dialogApi.dismiss()
            return sucesso
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if(!result.isNullOrBlank())
            {
                cot.text = "Preço de compra do dólar: R$" + result
            }
            else
                Toast.makeText(activity, "Ocorreu um problema na obtenção da cotação do dólar!", Toast.LENGTH_SHORT).show()

        }

    }
}
