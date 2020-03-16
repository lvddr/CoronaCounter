package com.lvddr.coronacounter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lvddr.coronacounter.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val thread = FetchStat()
        thread.start()
    }
    inner class FetchStat: Thread() {
        override fun run() {
            val url = "https://www.worldometers.info/coronavirus/"
            val doc1 = Jsoup.connect(url).get()
            val cases = doc1.select("#maincounter-wrap > div > span").first().text()
            val dead = doc1.select("#maincounter-wrap > div > span").get(1).text()
            val recovered = doc1.select("#maincounter-wrap > div > span").get(2).text()
            runOnUiThread() {
                findViewById<TextView>(R.id.textView).text = cases
                findViewById<TextView>(R.id.textView3).text = dead
                findViewById<TextView>(R.id.textView4).text = recovered
            }
        }

    }
}




