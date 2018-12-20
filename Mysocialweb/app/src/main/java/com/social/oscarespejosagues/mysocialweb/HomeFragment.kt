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
import kotlinx.android.synthetic.main.fragment_home.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pulltorefresh.setOnRefreshListener{
            refreshData();
        }
        refreshData()
        val db = FirebaseFirestore.getInstance()

        SendButton.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                //pasas activity por que el fragment no tiene la actividad
                val signUpIntent = Intent(
                    activity,
                    SignUpActivity::class.java
                ) //un intent es intento de acción permite pasar parametros basicamente es con estos parametros intento ir a X
                startActivity(signUpIntent)
                return@setOnClickListener
            }

            if(userInput.text.toString().isEmpty()){
                return@setOnClickListener
            }

            val db = FirebaseFirestore.getInstance()
            //Get User
            val authUser = FirebaseAuth.getInstance().currentUser!!
            db.collection("users").document(authUser.uid).get().addOnSuccessListener { documentSnapshot ->
                val userProfile = documentSnapshot.toObject(MessageModel::class.java);
                //Get user text
                userProfile?.let {
                    var userText = userInput.text.toString()
                    //Create Message Model
                    val userMessage = MessageModel(text = userText, createdAt = Date(), username = userProfile.username, userId = authUser.uid, avatarUrl = userProfile.avatarUrl)
                    //Insert to Firebase
                    db.collection("messages").add(userMessage)
                        .addOnSuccessListener {
                            refreshData()
                            userInput.text.clear()
                        }.addOnFailureListener {
                            Log.e("HomeFragment", it.message)
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(activity,"Error sending the missage", Toast.LENGTH_LONG).show();
            }
        }
    }
    fun refreshData(){
        pulltorefresh.isRefreshing = true
        val db = FirebaseFirestore.getInstance()
        db.collection("messages").orderBy("createdAt", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = ArrayList<MessageModel>()
                task.result?.forEach{documentSnapshot->
                    documentSnapshot.data //ES la info del documento
                    val message = documentSnapshot.toObject(MessageModel::class.java)
                    //Log.i("MainActivity", "Got message with text:"+message.text);
                    list.add(message);
                }
                activity?.let {
                    recycleview.adapter = MessageAdapter(list);
                    recycleview.layoutManager = LinearLayoutManager(activity);
                    //End refresh
                    pulltorefresh.isRefreshing = false;
                }
            }else{
                Toast.makeText(activity,"Error refreshing, check internet connexion", Toast.LENGTH_LONG).show();
                pulltorefresh.isRefreshing = false;
            }
        }
    }
}
