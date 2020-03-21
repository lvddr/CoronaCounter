package com.lvddr.coronacounter

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.poland.*
import org.jsoup.Jsoup

class Poland : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poland)
        val submitBtn: Button = findViewById(R.id.button2)
        submitBtn.setOnClickListener() {
            if(editText.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter some info", Toast.LENGTH_LONG).show()
            } else {
                val async = AsyncT(this)
                async.execute()
            }

        }

    }


    inner class AsyncT internal constructor(context: Poland): AsyncTask<Void?, Void?, String?>() {
        var cases: String? = null
        var dead: String? = null
        var recovered: String? = null
        override fun onPreExecute() {
            textView.text = "Loading"
            textView3.text = ""
            textView4.text = ""
        }
        override fun doInBackground(vararg params: Void?): String? {
            val docc = Jsoup.connect("https://www.worldometers.info/coronavirus/country/" + editText.text.toString()).followRedirects(true).get()
            cases = docc.select("#maincounter-wrap > div > span").get(0).text()
            dead = docc.select("#maincounter-wrap > div > span").get(1).text()
            recovered = docc.select("#maincounter-wrap > div > span").get(2).text()
            // rozpierdole cos jak tego nie naprawie
            println(cases)
            return null
        }

        override fun onPostExecute(result: String?) {
            cases?.let { findViewById<TextView>(R.id.textView).text = it}
            dead?.let { findViewById<TextView>(R.id.textView3).text = it}
            recovered?.let { findViewById<TextView>(R.id.textView4).text = it}
        }

    }
}