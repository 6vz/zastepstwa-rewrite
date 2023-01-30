package com.mateusz.zastepstwa

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.time.Instant

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isDarkTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES == Configuration.UI_MODE_NIGHT_YES
        println("Now is dark theme: $isDarkTheme")
        if (isDarkTheme) {
            // create a new instance of alertbuilder
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Hej! Zatrzymaj się na chwilę!")
            builder.setMessage("Używasz trybu ciemnego który nie jest dokońca wspierany, rozważ wyłączenie trybu ciemnego, lub dodanie aplikacji do wyjątków")
            builder.setPositiveButton("Zamknij") { dialog, which ->
                // Do nothing
            }
            builder.show()
        } else {
            println("Dark theme is not enabled")
        }


        val todayButton = findViewById<Button>(R.id.selectToday)
        val tomorrowButton = findViewById<Button>(R.id.selectTommorow)
        val customButton = findViewById<Button>(R.id.selectCustom)

        fun checkActivity() {
            val thisDay = java.time.LocalDate.now()
            val nextDay = java.time.LocalDate.now().plusDays(1)
            // if thisDay is a saturday or sunday disable todayButton and change text to "Dzień poza planem"
            if (thisDay.dayOfWeek.value == 6 || thisDay.dayOfWeek.value == 7) {
                todayButton.text = "Dzień poza planem"
                todayButton.isEnabled = false
            } else {
                todayButton.text = "Dzisiaj (${thisDay.dayOfMonth}.${thisDay.monthValue}.${thisDay.year})"
                todayButton.isEnabled = true
            }
            // if nextDay is a saturday or sunday disable tomorrowButton and change text to "Dzień poza planem"
            if (nextDay.dayOfWeek.value == 6 || nextDay.dayOfWeek.value == 7) {
                tomorrowButton.text = "Dzień poza planem"
                tomorrowButton.isEnabled = false
            } else {
                tomorrowButton.text = "Jutro (${nextDay.dayOfMonth}.${nextDay.monthValue}.${nextDay.year})"
                tomorrowButton.isEnabled = true
            }
        }



        checkActivity()

        customButton.setOnClickListener() {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Wybierz datę")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                val start = datePicker.selection
                val dateTime = Instant.ofEpochMilli(start!!)
                val date = dateTime.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                var month = if (date.monthValue < 10) "0${date.monthValue}" else date.monthValue.toString()
                var day = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else date.dayOfMonth.toString()
                var year = date.year.toString()
                if (date.dayOfWeek.value == 6 || date.dayOfWeek.value == 7) {
                    Snackbar.make(findViewById(R.id.root), "Wybrany dzień jest weekendem. Nie chodzisz do szkoły w weekend... Racja?", Snackbar.LENGTH_LONG).show()
                } else {
                    val url = "https://zastepstwa.zschie.pl/pliki/$day.$month.$year.pdf"
                    // open that in default pdf viewer
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                    intent.setDataAndType(android.net.Uri.parse(url), "application/pdf")
                    intent.flags = android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    if (intent.resolveActivity(packageManager) == null) {
                        Snackbar.make(findViewById(R.id.root), "Jeśli żądanie zostało odrzucone, oznacza to że na ten dzień serwer nie znalazł zastępstw", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        todayButton.setOnClickListener() {
            val today = java.time.LocalDate.now()
            var month = if (today.monthValue < 10) "0${today.monthValue}" else today.monthValue.toString()
            var day = if (today.dayOfMonth < 10) "0${today.dayOfMonth}" else today.dayOfMonth.toString()
            var year = today.year.toString()
            if (today.dayOfWeek.value == 6 || today.dayOfWeek.value == 7) {
                Snackbar.make(findViewById(R.id.root), "Dzisiaj jest weekend. Nie chodzisz do szkoły w weekend... Racja?", Snackbar.LENGTH_LONG).show()
            } else {
                val url = "https://zastepstwa.zschie.pl/pliki/$day.$month.$year.pdf"
                // open that in default pdf viewer
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                intent.setDataAndType(android.net.Uri.parse(url), "application/pdf")
                intent.flags = android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                if (intent.resolveActivity(packageManager) == null) {
                    Snackbar.make(findViewById(R.id.root), "Jeśli żądanie zostało odrzucone, oznacza to że na ten dzień serwer nie znalazł zastępstw", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        tomorrowButton.setOnClickListener() {
            val tomorrow = java.time.LocalDate.now().plusDays(1)
            var month = if (tomorrow.monthValue < 10) "0${tomorrow.monthValue}" else tomorrow.monthValue.toString()
            var day = if (tomorrow.dayOfMonth < 10) "0${tomorrow.dayOfMonth}" else tomorrow.dayOfMonth.toString()
            var year = tomorrow.year.toString()
            if (tomorrow.dayOfWeek.value == 6 || tomorrow.dayOfWeek.value == 7) {
                Snackbar.make(findViewById(R.id.root), "Jutro jest weekend. Nie chodzisz do szkoły w weekend... Racja?", Snackbar.LENGTH_LONG).show()
            } else {
                val url = "https://zastepstwa.zschie.pl/pliki/$day.$month.$year.pdf"
                // open that in default pdf viewer
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                intent.setDataAndType(android.net.Uri.parse(url), "application/pdf")
                intent.flags = android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
                if (intent.resolveActivity(packageManager) == null) {
                    Snackbar.make(findViewById(R.id.root), "Jeśli żądanie zostało odrzucone, oznacza to że na ten dzień serwer nie znalazł zastępstw", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}