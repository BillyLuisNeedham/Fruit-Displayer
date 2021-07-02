package com.billyluisneedham.bbctest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.billyluisneedham.bbctest.R
import com.billyluisneedham.bbctest.ui.fruitlist.FruitListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.flMainActivity, FruitListFragment()).commit()
    }
}