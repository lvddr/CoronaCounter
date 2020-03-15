package com.lvddr.coronacounter

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.lvddr.coronacounter.R.layout.activity_main
import org.jsoup.Jsoup

class FetchStat: Thread() {
    override fun run() {
        val url = "https://www.worldometers.info/coronavirus/"
        val doc1 = Jsoup.connect(url).get()
        val contentDiv = doc1.select("#maincounter-wrap > div > span").first().text()
        println("Number of confirmed Coronavirus cases is " + contentDiv)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val thread = FetchStat()
        thread.start()


    }
}




