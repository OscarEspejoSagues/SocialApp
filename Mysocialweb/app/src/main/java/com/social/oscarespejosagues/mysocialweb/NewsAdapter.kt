package com.social.oscarespejosagues.mysocialweb

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.adriaortizmartinez.epicsoundboardlmao.OnItemClickListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_message.view.*
import kotlinx.android.synthetic.main.row_news.view.*
import java.util.*

class NewsAdapter(var List: ArrayList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    public var onItemClickListener: OnItemClickListener<MessageModel>? = null

    override fun getItemCount(): Int {
        return List.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        val news = List[position]
        viewHolder.Nauthor.text = List[position].author;
        viewHolder.Ntitle.text = List[position].title;
        viewHolder.Ntextnews.text = List[position].description;
        Glide.with(viewHolder.Nurl).load(news.urlImage).into(viewHolder.Nurl)
        //viewHolder.Nurl = message.avatarUrl
        /*viewHolder.soundButton.setOnClickListener{
            onSoundClickListener?.onItemClick(soundList[position], position)
        }*/
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var Nauthor: TextView = itemView.TVauthor;
        var Ntitle: TextView = itemView.TVtitle;
        var Ntextnews: TextView = itemView.TVtextnews;
        var Nurl: ImageView = itemView.IVurlimage;
    }
}

