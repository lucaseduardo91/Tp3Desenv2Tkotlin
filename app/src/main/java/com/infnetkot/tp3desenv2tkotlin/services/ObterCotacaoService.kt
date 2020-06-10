package com.infnetkot.tp3desenv2tkotlin.services

import com.infnetkot.tp3desenv2tkotlin.interfaces.EndpointsCotacaoApi
import com.infnetkot.tp3desenv2tkotlin.servicoexterno.ConectorApi

class ObterCotacaoService {

    companion object{
        private var cotacao : ObterCotacaoService? = null

        fun getInstance() : ObterCotacaoService{
            if(cotacao == null)
                cotacao = ObterCotacaoService()

            return cotacao as ObterCotacaoService
        }
    }

    fun buscarCotacao() : String?{
        val retrofitClient = ConectorApi
            .getRetrofitInstance("https://economia.awesomeapi.com.br/json/")

        val endpoint = retrofitClient.create(EndpointsCotacaoApi::class.java)
        val callback = endpoint.buscarCotacaoDolar()

        var resposta = callback.execute()

        if(resposta.isSuccessful)
        {
            var corpo = resposta.body()!!.first()
            return corpo.bid
        }

        return null
    }
}