package com.social.oscarespejosagues.mysocialweb


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adriaortizmartinez.epicsoundboardlmao.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        addyournews.setOnClickListener{
            if (FirebaseAuth.getInstance().currentUser == null){
                Toast.makeText(activity,"You need to log in to publish news :D", Toast.LENGTH_LONG).show();
            }else {
                val addnewsintent = Intent(
                    activity,
                    AddNewsActivity::class.java
                )
                startActivity(addnewsintent)
                return@setOnClickListener
            }
        }
        refreshData()
    }
    fun refreshData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("news").get().addOnCompleteListener {task->
            if (task.isSuccessful){
                val list = ArrayList<NewsModel>()
                task.result?.forEach{documentSnapshot->
                    documentSnapshot.data //ES la info del documento
                    val noticia = documentSnapshot.toObject(NewsModel::class.java)
                    Log.e("NewsFragment", noticia.title)
                    list.add(noticia);
                }
                activity?.let {
                    recycleviewnews.adapter = NewsAdapter(list);
                    recycleviewnews.layoutManager = LinearLayoutManager(activity);
                }
            }else{
                //todo check erros
                Toast.makeText(activity,"Error refreshing, check internet connexion", Toast.LENGTH_LONG).show();
            }
        }
    }
}
