package com.example.jsonapp

import com.google.gson.annotations.SerializedName
import java.util.*

class EurDetails {


    @SerializedName("date")
    var date: Date? = null

    @SerializedName("eur")
    var eur: Currencies? = null

    class Currencies {

        @SerializedName("sar")
        var sar: String? = null

        @SerializedName("usd")
        var usd: String? = null

        @SerializedName("aud")
        var aud: String? = null

        @SerializedName("jpy")
        var jpy: String? = null
    }
}