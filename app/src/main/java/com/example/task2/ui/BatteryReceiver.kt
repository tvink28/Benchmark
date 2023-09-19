package com.example.task2.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.task2.R

class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_LOW) {
            Toast.makeText(context, R.string.receiver_battery, Toast.LENGTH_LONG).show()
        }
    }
}