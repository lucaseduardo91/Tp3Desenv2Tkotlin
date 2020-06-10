package com.infnetkot.tp3desenv2tkotlin.interfaces

import com.infnetkot.tp3desenv2tkotlin.model.USD
import retrofit2.Call
import retrofit2.http.*

interface EndpointsCotacaoApi {

    @GET("USD-BRL")
    fun buscarCotacaoDolar() : Call<List<USD>>
}