package com.example.kinemaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinemaapp.R
import com.example.kinemaapp.data.UpcomingModel

class UpcomingAdapter (private val upcomingList:List<UpcomingModel.Result>,private val listener:UpcomingListener) : RecyclerView.Adapter<UpcomingAdapter.RowHolder>() {


    interface UpcomingListener{
        fun uponClickListener(result:UpcomingModel.Result)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(upcomingModel: UpcomingModel.Result,listener:UpcomingListener/*,colors:Array<String>,position: Int*/){

            itemView.setOnClickListener {
                listener.uponClickListener(upcomingModel)
            }
            itemView.findViewById<TextView>(R.id.upcoming_textView).text = upcomingModel.title
            itemView.findViewById<ImageView>(R.id.upcoming_imageView).load("https://image.tmdb.org/t/p/w342"+upcomingModel.posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(upcomingList[position],listener)
    }

    override fun getItemCount(): Int {
        return upcomingList.count()
    }
}