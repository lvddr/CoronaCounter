package com.lvddr.coronacounter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.poland.*
import org.jsoup.Jsoup

class Poland : AppCompatActivity() {
    var context: Context = this@Poland

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poland)
        val glist = GetList(this)
        glist.execute()
        val submitBtn: Button = findViewById(R.id.button2)
        submitBtn.setOnClickListener() {
            val gcountry = GetCountry(this)
            gcountry.execute()
        }
    }


    inner class GetCountry internal constructor(context: Poland) : AsyncTask<Void?, Void?, String?>() {
        var cases: String? = null
        var dead: String? = null
        var recovered: String? = null
        override fun onPreExecute() {
            textView.text = "Loading"
            textView3.text = ""
            textView4.text = ""
        }

        override fun doInBackground(vararg params: Void?): String? {
            val docc = Jsoup.connect("https://www.worldometers.info/coronavirus/" + actv.text.toString()).followRedirects(true).get()
            cases = docc.select("#maincounter-wrap > div > span").get(0).text()
            dead = docc.select("#maincounter-wrap > div > span").get(1).text()
            recovered = docc.select("#maincounter-wrap > div > span").get(2).text()
            println(cases)
            return null
        }

        override fun onPostExecute(result: String?) {
            cases?.let { findViewById<TextView>(R.id.textView).text = it }
            dead?.let { findViewById<TextView>(R.id.textView3).text = it }
            recovered?.let { findViewById<TextView>(R.id.textView4).text = it }
        }

    }
    inner class GetList internal constructor(context: Poland) : AsyncTask<Void, Void, List<String>>() {
        var cases: String? = null

        override fun onPreExecute() {
            textView.text = "Loading"
        }

        override fun doInBackground(vararg params: Void?): List<String> {
            val cipa = Jsoup.connect("https://www.worldometers.info/coronavirus").get()
            val a = cipa.select("#main_table_countries_today").first()
            val b = a.select("a[href].mt_a").map { it.attr("href") }
            return b
        }

        override fun onPostExecute(result: List<String>) {
            val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, result)
            actv.threshold = 0
            actv.setAdapter(adapter)
            textView.text = "Ready"


        }
    }
}

