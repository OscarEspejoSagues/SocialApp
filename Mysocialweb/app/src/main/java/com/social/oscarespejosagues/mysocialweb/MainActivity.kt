package com.social.oscarespejosagues.mysocialweb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabs.setOnNavigationItemSelectedListener { tab->

            lateinit var fragment: Fragment;

            when(tab.itemId){//switch se llama when
                R.id.tab_home -> {
                    fragment = HomeFragment() //creas el fragment home
                }
                R.id.tab_news ->{
                    fragment = NewsFragment() //creas el fragment home
                }
                R.id.tab_user ->{
                    fragment = UserFragment() //creas el fragment home
                }
            }
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
            return@setOnNavigationItemSelectedListener true
        }

        tabs.selectedItemId = R.id.tab_home
    }
}
