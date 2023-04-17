package com.example.kinemaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinemaapp.R
import com.example.kinemaapp.data.TopRatedMovieModel
import com.example.kinemaapp.util.Listener

class TopRatedAdapter (private val topRatedMovie: List<TopRatedMovieModel.Result>,private val listener: TopRatedListener) : RecyclerView.Adapter<TopRatedAdapter.RowHolder>() {

    interface TopRatedListener{
        fun onitemClicked(result:TopRatedMovieModel.Result)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(lastModel: List<TopRatedMovieModel.Result>,position: Int,listener: TopRatedListener){

            itemView.setOnClickListener {
                listener.onitemClicked(lastModel[position])
            }
            println("25.satır:${lastModel[position].title} poster:${lastModel[position].posterPath}")
            itemView.findViewById<TextView>(R.id.lastMovie_textView).text = lastModel[position].title
            itemView.findViewById<ImageView>(R.id.lastMovie_imageView).load("https://image.tmdb.org/t/p/w342"+lastModel[position].posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.toprated_row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(topRatedMovie,position,listener)
    }

    override fun getItemCount(): Int {
        return 1
    }
}