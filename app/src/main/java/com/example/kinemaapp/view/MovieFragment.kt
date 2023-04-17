package com.example.kinemaapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinemaapp.R
import com.example.kinemaapp.adapter.AllMovieAdapter
import com.example.kinemaapp.data.AllMovieModel
import com.example.kinemaapp.databinding.FragmentMovieBinding
import com.example.kinemaapp.service.MovieAPI
import com.example.kinemaapp.util.Listener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFragment : Fragment() ,Listener{
    private val BASE_URL ="https://api.themoviedb.org/3/"
    private var allMovieList: AllMovieModel?=null
    private lateinit var allMovieAdapter: AllMovieAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var service: MovieAPI

    private lateinit var binding : FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var layoutManagerallMovie : RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerView2.layoutManager = layoutManagerallMovie
        startRetrofit()
        startAllMovieRetrofit()

    }

    private fun startRetrofit(){
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(MovieAPI::class.java)
    }

    private fun startAllMovieRetrofit() {

        val call = service.getAllMovies(page = 1)

        call.enqueue(object: Callback<AllMovieModel> {
            override fun onResponse(
                call: Call<AllMovieModel>,
                response: Response<AllMovieModel>
            ) {
                if (response.isSuccessful){
                    allMovieList = response.body()
                    allMovieAdapter = AllMovieAdapter(allMovieList!!.items,this@MovieFragment)
                    binding.recyclerView2.adapter = allMovieAdapter

                    println("all movie size:"+allMovieList!!.items.size)
                    for (movie : AllMovieModel.Item in allMovieList!!.items){
                        println("99:"+movie.title)
                    }
                }else{
                    println("response ne:${response.body()}")
                }
            }

            override fun onFailure(call: Call<AllMovieModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onItemClick(model: Any) {
        if(model is AllMovieModel.Item){
            Toast.makeText(context,"movie name:${model.title}",Toast.LENGTH_SHORT).show()
            val action =   MovieFragmentDirections.actionMovieFragmentToDetailFragment(id = model.id)
            findNavController().navigate(action)
        }
    }


}