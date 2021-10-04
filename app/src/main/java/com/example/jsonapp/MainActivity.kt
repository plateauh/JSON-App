package com.example.jsonapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var amountEditText: EditText
lateinit var currencySpinner: Spinner
lateinit var convertButton: Button
lateinit var resultTextView: TextView
lateinit var dateTextView: TextView
lateinit var context: Context

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        amountEditText = findViewById(R.id.amount_et)
        dateTextView = findViewById(R.id.date_tv)

        // from https://stackoverflow.com/a/17650125
        currencySpinner = findViewById(R.id.currency_spinner)
        val currencies = listOf("Choose currency", "", "")
        currencySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        apiCall()


    }

    private fun apiCall() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<EurDetails?>? = apiInterface!!.doGetListResources()
        call?.enqueue(object: Callback<EurDetails?>{
            override fun onResponse(call: Call<EurDetails?>?, response: Response<EurDetails?>) {
                val details: EurDetails? = response.body()
                dateTextView.text = details?.date.toString()
            }

            override fun onFailure(call: Call<EurDetails?>, t: Throwable) {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
    }
}