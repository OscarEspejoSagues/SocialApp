package com.adriaortizmartinez.epicsoundboardlmao

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.social.oscarespejosagues.mysocialweb.MessageModel
import com.social.oscarespejosagues.mysocialweb.R
import kotlinx.android.synthetic.main.row_message.view.*

class MessageAdapter(var List: ArrayList<MessageModel>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    public var onItemClickListener: OnItemClickListener<MessageModel>? = null

    override fun getItemCount(): Int {
        return List.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = List[position]
        viewHolder.text.text = List[position].text
        Glide.with(viewHolder.userAvatar).load(message.avatarUrl).into(viewHolder.userAvatar)
        //viewHolder.userAvatar = message.avatarUrl
        /*viewHolder.soundButton.setOnClickListener{
            onSoundClickListener?.onItemClick(soundList[position], position)
        }*/
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var text: TextView = itemView.messageText;
        var userAvatar: ImageView = itemView.userAvatar
    }
}