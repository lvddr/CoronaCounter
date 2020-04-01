package com.lvddr.coronacounter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val glist = GetList(this)
        glist.execute()
        val submitBtn: Button = findViewById(R.id.button2)
        submitBtn.setOnClickListener() {
            val gcountry = GetCountry(this)
            gcountry.execute()
        }
        val gotoRec: Button = findViewById(R.id.button)
        gotoRec.setOnClickListener() {
            val intent = Intent(this, RecyclerView::class.java)
            startActivity(intent)
        }
    }


    inner class GetCountry internal constructor(context: MainActivity) : AsyncTask<Void?, Void?, String?>() {
        var cases: String? = null
        var dead: String? = null
        var recovered: String? = null
        override fun onPreExecute() {
            textView.text = "Loading"
            textView3.text = ""
            textView4.text = ""
        }

        override fun doInBackground(vararg params: Void?): String? {
            val docc = Jsoup.connect("https://www.worldometers.info/coronavirus/" + spinner.selectedItem.toString()).followRedirects(true).get()
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
    inner class GetList internal constructor(context: MainActivity) : AsyncTask<Void, Void, List<String>>() {
        var cases: String? = null
        var dead: String? = null
        var recovered: String? = null

        override fun onPreExecute() {
            textView.text = "Loading"
            Toast.makeText(this@MainActivity, "Currently loading worldwide stats", Toast.LENGTH_LONG).show()
        }

        override fun doInBackground(vararg params: Void?): List<String> {
            val cipa = Jsoup.connect("https://www.worldometers.info/coronavirus").get()
            val a = cipa.select("#main_table_countries_today").first()
            val b = a.select("a[href].mt_a").map { it.attr("href") }
            cases = cipa.select("#maincounter-wrap > div > span").get(0).text()
            dead = cipa.select("#maincounter-wrap > div > span").get(1).text()
            recovered = cipa.select("#maincounter-wrap > div > span").get(2).text()
            return b
        }

        override fun onPostExecute(result: List<String>) {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, result)
            spinner.setAdapter(adapter)
            textView.text = "Ready"
            cases?.let { findViewById<TextView>(R.id.textView).text = it }
            dead?.let { findViewById<TextView>(R.id.textView3).text = it }
            recovered?.let { findViewById<TextView>(R.id.textView4).text = it }


        }
    }
}

