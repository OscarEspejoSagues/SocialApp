package com.adriaortizmartinez.epicsoundboardlmao

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
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
        viewHolder.username.text = List[position].username
        viewHolder.text.text = List[position].text
        viewHolder.createdAt.text = List[position].createdAt.toString();
        Glide.with(viewHolder.userAvatar.context).load(message.avatarUrl).apply(
            RequestOptions()
                .transforms(CenterCrop())
                .placeholder(R.drawable.ic_sentiment)
        ).into(viewHolder.userAvatar)
        //viewHolder.userAvatar = message.avatarUrl
        /*viewHolder.soundButton.setOnClickListener{
            onSoundClickListener?.onItemClick(soundList[position], position)
        }*/
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var username: TextView = itemView.userNameMissage;
        var text: TextView = itemView.messageText;
        var createdAt: TextView = itemView.messageDate;
        var userAvatar: ImageView = itemView.userAvatar //ERROR BUT WORKS THE IMAGE IS CIRCLE
    }
}