package com.social.oscarespejosagues.mysocialweb

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupButton.setOnClickListener{
            //sing Up use

            val username = usernameInput.text.toString();
            val email = emailInput.text.toString();
            val password = emailInput.text.toString();
            //Validation
            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                //Do sign up
                progressbar.visibility = View.VISIBLE;
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = FirebaseAuth.getInstance().currentUser
                            user?.let { userAth->
                                val userProfile = UserProfile(username = username, userId = userAth.uid, email = email);
                                val db = FirebaseFirestore.getInstance();
                                db.collection("users").document(userAth.uid).set(userProfile).addOnCompleteListener { Task->
                                    if (Task.isSuccessful){
                                        //all ok
                                        finish();
                                    }else{
                                        //0ops
                                        Toast.makeText(this,"Could not create user", Toast.LENGTH_LONG).show();
                                        progressbar.visibility = View.GONE;
                                    }
                                };
                                userAth.uid
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this@SignUpActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressbar.visibility = View.GONE;
                        }
                    }
            }
        }


        gotologin.setOnClickListener{
            //TODO: Make the login
        }
    }
}
