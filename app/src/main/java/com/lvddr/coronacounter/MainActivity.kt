package com.lvddr.coronacounter

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lvddr.coronacounter.R.layout.activity_main
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val task = Async(this)
        task.execute()

    }



    inner class Async internal constructor(context: MainActivity) : AsyncTask<Void, Void, String>(){


        override fun doInBackground(vararg params: Void): String? {

            val url = "https://www.worldometers.info/coronavirus/"
            val doc1 = Jsoup.connect(url).get()
            val cases = doc1.select("#maincounter-wrap > div > span").first().text()
            val dead = doc1.select("#maincounter-wrap > div > span").get(1).text()
            val recovered = doc1.select("#maincounter-wrap > div > span").get(2).text()
            println(cases)
            println(dead)
            println(recovered)
            return null
            }

        override fun onPostExecute(result: String?) {
            findViewById<TextView>(R.id.textView).text = cases
        }


    }
}




