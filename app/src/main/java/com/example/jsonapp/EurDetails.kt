package com.example.jsonapp

import com.google.gson.annotations.SerializedName

class EurDetails {


    @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Currencies? = null

    class Currencies {

        @SerializedName("sar")
        var sar: Double? = null

        @SerializedName("usd")
        var usd: Double? = null

        @SerializedName("aud")
        var aud: Double? = null

        @SerializedName("jpy")
        var jpy: Double? = null
    }
}