package com.example.kinemaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinemaapp.R
import com.example.kinemaapp.data.AllMovieModel
import com.example.kinemaapp.util.Listener

class AllMovieAdapter(private val movieList:List<AllMovieModel.Item>,private val listener: Listener) : RecyclerView.Adapter<AllMovieAdapter.RowHolder>() {




    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(allMovieModel: AllMovieModel.Item,listener: Listener/*,colors:Array<String>,position: Int*/){

            itemView.setOnClickListener {
                listener.onItemClick(allMovieModel)
            }
            itemView.findViewById<TextView>(R.id.allMovie_text_movie).text = allMovieModel.title
            itemView.findViewById<ImageView>(R.id.allMovie_imageView_movie).load("https://image.tmdb.org/t/p/w342"+allMovieModel.posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.allmovie_row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(movieList[position],listener)
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }
}