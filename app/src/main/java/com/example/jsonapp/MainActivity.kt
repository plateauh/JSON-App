package com.example.jsonapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

lateinit var amountEditText: EditText
lateinit var currencySpinner: Spinner
lateinit var convertButton: Button
lateinit var resultTextView: TextView
lateinit var dateTextView: TextView
lateinit var currenciesFactors: ArrayList<Double?> // Holds factors we get from API
lateinit var context: Context

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        currenciesFactors = arrayListOf()
        amountEditText = findViewById(R.id.amount_et)
        resultTextView = findViewById(R.id.result_tv)
        dateTextView = findViewById(R.id.date_tv)
        convertButton = findViewById(R.id.convert_btn)

        // from https://stackoverflow.com/a/17650125
        currencySpinner = findViewById(R.id.currency_spinner)
        currencySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf("Choose currency", "sar", "usd", "aud", "jpy")) // Notice that 0th item is invalid & handled later

        var selectedItem = 0 // holds the index of spinner item selected

        // from https://www.geeksforgeeks.org/spinner-in-kotlin/
        currencySpinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "Please select currency", Toast.LENGTH_SHORT).show()
            }
        }

        setDataFromAPI()

        convertButton.setOnClickListener {
            /* try/catch instead of if/else
               currenciesFactors & spinner list have a relationship (currenciesFactors index + 1 corresponds to spinner list index)
               .times() works as *
               if amount is empty or currency is invalid, an exception will be thrown
            */
            try {
                resultTextView.text = "Result: ${(currenciesFactors[selectedItem-1]?.times(amountEditText.text.toString().toDouble())).toString()}"
            } catch (e: Exception){
                Log.d("Exception", e.toString())
                Toast.makeText(context, "Please enter amount/currency", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDataFromAPI() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<EurDetails?>? = apiInterface!!.doGetListResources()
        call?.enqueue(object : Callback<EurDetails?> {
            override fun onResponse(call: Call<EurDetails?>?, response: Response<EurDetails?>) {
                val details: EurDetails? = response.body()
                // insert currency factors to their array list
                currenciesFactors.add(details?.eur?.sar)
                currenciesFactors.add(details?.eur?.usd)
                currenciesFactors.add(details?.eur?.aud)
                currenciesFactors.add(details?.eur?.jpy)
                dateTextView.text = "${dateTextView.text} ${details?.date}"
            }

            override fun onFailure(call: Call<EurDetails?>, t: Throwable) {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
    }
}