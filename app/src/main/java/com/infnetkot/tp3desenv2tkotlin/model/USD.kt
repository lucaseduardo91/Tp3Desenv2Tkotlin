package com.infnetkot.tp3desenv2tkotlin.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class USD (
    @SerializedName("code")
    var code: String,
    @SerializedName("codein")
    var codein: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("high")
    var high: String,
    @SerializedName("low")
    var low: String,
    @SerializedName("varBid")
    var varBid: String,
    @SerializedName("pctChange")
    var pctChange: String,
    @SerializedName("bid")
    var bid: String,
    @SerializedName("ask")
    var ask: String,
    @SerializedName("timestamp")
    var timestamp: String,
    @SerializedName("createDate")
    var create_date: String
)
/*"USD": {
        "code": "USD",
        "codein": "BRL",
        "name": "Dólar Comercial",
        "high": "4.9002",
        "low": "4.8999",
        "varBid": "0.0009",
        "pctChange": "0.02",
        "bid": "4.8998",
        "ask": "4.9004",
        "timestamp": "1591736433",
        "create_date": "2020-06-09 21:00:03"
    }*/