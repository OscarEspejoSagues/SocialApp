package com.social.oscarespejosagues.mysocialweb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        ButtonLogIn.setOnClickListener {
            val username = usernameInputLogIn.text.toString();
            val password = passwordInputLogIn.text.toString();

            if (!username.isEmpty() && !password.isEmpty()){
                //ToDO check if the user exist and let him enter in his account
            }
        }
    }
}