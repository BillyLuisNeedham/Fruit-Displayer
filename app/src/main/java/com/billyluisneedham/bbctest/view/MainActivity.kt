package com.billyluisneedham.bbctest.view

import android.os.Bundle
import com.billyluisneedham.bbctest.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.flMainActivity, FruitListFragment()).commit()
    }
}