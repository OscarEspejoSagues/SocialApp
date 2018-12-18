package com.social.oscarespejosagues.mysocialweb

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.adriaortizmartinez.epicsoundboardlmao.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_add_news.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class AddNewsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        val username = usernameInputAddNews.text.toString();
        val title = titleInputAddNews.text.toString();
        val description = descriptionInputAddNews.text.toString();
        val url = urlInputAddNews.text.toString();

            if (!title.isEmpty() && !description.isEmpty() && !url.isEmpty()){
                val db = FirebaseFirestore.getInstance()
                db.collection("news").get().addOnSuccessListener { documentSnapshot ->
                    val newNew = documentSnapshot.toObjects(NewsModel::class.java) //deberia ser el singular
                    newNew?.let {
                        val userNews = NewsModel(author = username, title = title, description = description, urlImage = url)
                        db.collection("news").add(userNews)
                            .addOnSuccessListener{
                                Log.i("MainActivity", "NEEEEEEEW NEWS");
                        }.addOnFailureListener {
                                Toast.makeText(this,"ERROR", Toast.LENGTH_LONG).show();
                            }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this,"Error sending the missage", Toast.LENGTH_LONG).show();
                }
            }
    }
    fun refreshData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("news").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = ArrayList<NewsModel>()
                task.result?.forEach{documentSnapshot->
                    documentSnapshot.data //ES la info del documento
                    val news = documentSnapshot.toObject(NewsModel::class.java)
                    list.add(news);
                }
            }else{
                Toast.makeText(this,"Error refreshing, check internet connexion", Toast.LENGTH_LONG).show();
                pulltorefresh.isRefreshing = false;
            }
        }
    }
}