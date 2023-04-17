package com.example.kinemaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinemaapp.R
import com.example.kinemaapp.data.MovieModel
import com.example.kinemaapp.util.Listener

class MovieAdapter(private val movieList:List<MovieModel>,private val listener: Listener) : RecyclerView.Adapter<MovieAdapter.RowHolder>() {


    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movieModel: MovieModel, listener: Listener/*,colors:Array<String>,position: Int*/){

            itemView.setOnClickListener {
                listener.onItemClick(movieModel)
            }
            itemView.findViewById<TextView>(R.id.text_movie).text = movieModel.title
            itemView.findViewById<ImageView>(R.id.imageView_movie).load("https://image.tmdb.org/t/p/w342"+movieModel.posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(movieList[position],listener)
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }
}