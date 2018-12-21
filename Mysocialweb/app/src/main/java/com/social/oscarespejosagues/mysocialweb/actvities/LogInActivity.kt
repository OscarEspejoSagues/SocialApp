package com.social.oscarespejosagues.mysocialweb.actvities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.social.oscarespejosagues.mysocialweb.R
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        ButtonLogIn.setOnClickListener {
            val email = emailInputLogIn.text.toString();
            val password = passwordInputLogIn.text.toString();
            Log.e("MainActivity",password.toString())
            val auth = FirebaseAuth.getInstance();

            if (!email.isEmpty() && !password.isEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                    //Log.e("MainActivity",task.exception.toString())
                    progressbarlogin.visibility = View.VISIBLE;
                    if (task.isSuccessful){
                        Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this,"Your profile its not correct", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}