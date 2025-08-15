package com.tinysurvivors

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val title = findViewById<TextView>(R.id.tv_title)
        title.text = getString(R.string.app_name)

        val openSettings = findViewById<Button>(R.id.btn_open_accessibility_settings)
        val switchBot = findViewById<Switch>(R.id.switch_bot_enabled)

        switchBot.isChecked = prefs.getBoolean("bot_enabled", true)

        openSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        switchBot.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("bot_enabled", isChecked).apply()
        }
    }
}
