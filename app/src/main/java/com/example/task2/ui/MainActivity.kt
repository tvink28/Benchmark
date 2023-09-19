package com.example.task2.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.task2.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_TOAST: Long = 2000

class MainActivity : AppCompatActivity(), TabConfigurationStrategy {

    private var tabLayoutMediator: TabLayoutMediator? = null
    private val batteryReceiver = BatteryReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFilterBatteryReceiver = IntentFilter(Intent.ACTION_BATTERY_LOW)
        registerReceiver(batteryReceiver, intentFilterBatteryReceiver)

        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        supportActionBar?.let {
            it.elevation = 0F
            it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            it.setCustomView(R.layout.view_actionbar)
        }

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, this)
        tabLayoutMediator?.attach()

        lifecycleScope.launch {
            delay(DELAY_TOAST)
            Toast.makeText(this@MainActivity, R.string.toast_database, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator?.detach()
        unregisterReceiver(batteryReceiver)
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        val (textId, backgroundId) = when (position) {
            0 -> Pair(R.string.tab_collections, R.drawable.tab_drawable_selector_1)
            else -> Pair(R.string.tab_maps, R.drawable.tab_drawable_selector_2)
        }

        with(tab) {
            text = getString(textId)
            view.setBackgroundResource(backgroundId)
        }
    }
}
