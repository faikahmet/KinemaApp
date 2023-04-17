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
import com.example.kinemaapp.adapter.MovieAdapter
import com.example.kinemaapp.adapter.TopRatedAdapter
import com.example.kinemaapp.adapter.UpcomingAdapter
import com.example.kinemaapp.data.GetMoviesResponse
import com.example.kinemaapp.data.MovieModel
import com.example.kinemaapp.data.TopRatedMovieModel
import com.example.kinemaapp.data.UpcomingModel
import com.example.kinemaapp.databinding.FragmentMainBinding
import com.example.kinemaapp.service.MovieAPI
import com.example.kinemaapp.util.Listener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainFragment : Fragment(),Listener, TopRatedAdapter.TopRatedListener,UpcomingAdapter.UpcomingListener {

    private val BASE_URL ="https://api.themoviedb.org/3/"
    private var popularMovieList: GetMoviesResponse?=null
    private var topRatedMovie: TopRatedMovieModel?=null
    private var upcomingMovie: UpcomingModel?=null

    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var upcomingAdapter: UpcomingAdapter


    private lateinit var retrofit: Retrofit
    private lateinit var service: MovieAPI

    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var layoutManagerTopRated : RecyclerView.LayoutManager = LinearLayoutManager(context)

        var  layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        var  layoutManagerupcoming = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.recyclerViewTopRatedMovie.layoutManager = layoutManagerTopRated
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerViewupcoming.layoutManager = layoutManagerupcoming

        startRetrofit()
        startPopularRetrofit()
        startTopRatedRetrofit()
        startUpcomingRetrofit()
    }



    private fun startRetrofit(){
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        service = retrofit.create(MovieAPI::class.java)
    }


    private fun startPopularRetrofit() {

        val call = service.getPopularMovies(page = 1)

        call.enqueue(object: Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                if (response.isSuccessful){
                    popularMovieList = response.body()
                    movieAdapter = MovieAdapter(popularMovieList!!.movies,this@MainFragment)
                    binding.recyclerView.adapter = movieAdapter
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }



    private fun startTopRatedRetrofit() {
        val call = service.getTopRated()

        call.enqueue(object: Callback<TopRatedMovieModel> {
            override fun onResponse(
                call: Call<TopRatedMovieModel>,
                response: Response<TopRatedMovieModel>
            ) {
                if (response.isSuccessful){
                    topRatedMovie = response.body()
                    topRatedAdapter = TopRatedAdapter(topRatedMovie!!.results,this@MainFragment)
                    binding.recyclerViewTopRatedMovie.adapter = topRatedAdapter

                    println("TopRated:"+topRatedMovie!!.results)
                }else{
                    println("response ne:${response.body()}")
                }
            }

            override fun onFailure(call: Call<TopRatedMovieModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    private fun startUpcomingRetrofit() {
        val call = service.getUpcoming(page = 1)

        call.enqueue(object: Callback<UpcomingModel> {
            override fun onResponse(
                call: Call<UpcomingModel>,
                response: Response<UpcomingModel>
            ) {
                if (response.isSuccessful){
                    upcomingMovie = response.body()
                    upcomingAdapter = UpcomingAdapter(upcomingMovie!!.results,this@MainFragment)
                    binding.recyclerViewupcoming.adapter = upcomingAdapter
                }
            }

            override fun onFailure(call: Call<UpcomingModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onItemClick(model: Any) {
        if (model is MovieModel) {
            Toast.makeText(context,"movie name:${model.title}", Toast.LENGTH_SHORT).show()
            val action =        MainFragmentDirections.actionMainFragmentToDetailFragment(id = model.id.toInt())
            findNavController().navigate(action)
        }
    }

    override fun onitemClicked(result: TopRatedMovieModel.Result) {
        Toast.makeText(context,"movie name:${result.title}", Toast.LENGTH_SHORT).show()
        val action =   MainFragmentDirections.actionMainFragmentToDetailFragment(id = result.id)
        findNavController().navigate(action)
    }

    override fun uponClickListener(result: UpcomingModel.Result) {
        Toast.makeText(context,"movie name:${result.title}", Toast.LENGTH_SHORT).show()
       val action =  MainFragmentDirections.actionMainFragmentToDetailFragment(id = result.id)
        findNavController().navigate(action)
    }


}