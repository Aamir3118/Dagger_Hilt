package com.example.hiltexample.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiltexample.R
import com.example.hiltexample.data.model.User

class MainAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.findViewById<AppCompatTextView>(R.id.textViewUserName).text = user.name
            itemView.findViewById<AppCompatTextView>(R.id.textViewUserEmail).text = user.email
            Glide.with(itemView.findViewById<ImageView>(R.id.imageViewAvatar).context)
                .load(user.avatar)
                .into(itemView.findViewById(R.id.imageViewAvatar))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent, false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}