package com.mateusz.zastepstwa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val webView = findViewById<android.webkit.WebView>(R.id.webRoot)
        val fab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        val day = intent.getStringExtra("day")
        val month = intent.getStringExtra("month")
        val year = intent.getStringExtra("year")

        Snackbar.make(findViewById(R.id.rootDisplay), "Próba uzyskania zastępstw dla dnia $day.$month.$year", Snackbar.LENGTH_SHORT).show()
        webView.loadUrl("https://zst.6vz.dev/get?day=$day&month=$month")

        fab.setOnClickListener() {
            finish()
        }
    }
}