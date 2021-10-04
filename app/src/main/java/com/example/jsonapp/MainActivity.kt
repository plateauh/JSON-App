package com.example.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

lateinit var amountEditText: EditText
lateinit var currencySpinner: Spinner
lateinit var convertButton: Button
lateinit var resultTextView: TextView
lateinit var dateTextView: TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amountEditText = findViewById(R.id.amount_et)

        // from https://stackoverflow.com/a/17650125
        currencySpinner = findViewById(R.id.currency_spinner)
        val currencies = listOf("Choose currency", "", "")
        currencySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)


    }
}