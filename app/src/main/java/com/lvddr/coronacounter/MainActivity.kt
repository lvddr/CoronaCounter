package com.lvddr.coronacounter

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lvddr.coronacounter.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.textView
import kotlinx.android.synthetic.main.poland.*
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val task = Async(this)
        task.execute()
        gtpl.setOnClickListener() {
            val intent = Intent(this, Poland::class.java)
            startActivity(intent)
        }
    }

    inner class Async internal constructor(context: MainActivity) : AsyncTask<Void, Void, String>(){
        var cases: String? = null
        var dead: String? = null
        var recovered: String? = null

        override fun onPreExecute() {
            textView.text = getString(R.string.loading)
        }

        override fun doInBackground(vararg params: Void): String? {

            val url = "https://www.worldometers.info/coronavirus/"
            val doc1 = Jsoup.connect(url).get()
            cases = doc1.select("#maincounter-wrap > div > span").first().text()
            dead = doc1.select("#maincounter-wrap > div > span").get(1).text()
            recovered = doc1.select("#maincounter-wrap > div > span").get(2).text()

            return null
        }

        override fun onPostExecute(result: String?) {
            cases?.let { findViewById<TextView>(R.id.textView).text = it}
            dead?.let { findViewById<TextView>(R.id.textView3).text = it}
            recovered?.let { findViewById<TextView>(R.id.textView4).text = it}
        }


    }
}




