package com.social.oscarespejosagues.mysocialweb.actvities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.social.oscarespejosagues.mysocialweb.models.NewsModel
import com.social.oscarespejosagues.mysocialweb.R
import kotlinx.android.synthetic.main.activity_add_news.*
import java.util.*


class AddNewsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)


        uploadthenews.setOnClickListener {
                val username = usernameInputAddNews.text.toString();
                val title = titleInputAddNews.text.toString();
                val description = descriptionInputAddNews.text.toString();
                val url = urlInputAddNews.text.toString();
                if (!username.isEmpty() && !title.isEmpty() && !description.isEmpty() && !url.isEmpty()){
                val db = FirebaseFirestore.getInstance()
                val userNews = NewsModel(
                    author = username,
                    title = title,
                    createdAt = Date(),
                    description = description,
                    imageUrl = url
                )
                db.collection("news").add(userNews)
                    .addOnSuccessListener{
                        Toast.makeText(this,"Uploaded News", Toast.LENGTH_LONG).show();
                        finish();
                    }.addOnFailureListener {
                        Toast.makeText(this,"ERROR", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this,"You must enter all the data", Toast.LENGTH_LONG).show();
                }
        }
    }
}