package com.example.kinemaapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.kinemaapp.R
import com.example.kinemaapp.data.DetailModel
import com.example.kinemaapp.databinding.FragmentDetailBinding
import com.example.kinemaapp.service.MovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailFragment : Fragment() {

    private  var args = navArgs<DetailFragmentArgs>()
    private lateinit var binding : FragmentDetailBinding

    private val BASE_URL ="https://api.themoviedb.org/3/"
    private var detailMovie: DetailModel?=null
    private lateinit var retrofit: Retrofit
    private lateinit var service: MovieAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startRetrofit()
        detailMovieRetrofit(args.value.id)
    }


    private fun startRetrofit(){
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(MovieAPI::class.java)
    }
    private fun detailMovieRetrofit(movieId:Int) {

        val call = service.getDetail(id = movieId)

        call.enqueue(object: Callback<DetailModel> {
            override fun onResponse(
                call: Call<DetailModel>,
                response: Response<DetailModel>
            ) {
                if (response.isSuccessful){
                    detailMovie = response.body()
                    println("detailMovie!!.id:"+detailMovie!!.id+"detailMovie!!.nam:"+detailMovie!!.originalTitle)
                    writeUI()
                }else{
                    println("response ne:${response.body()}")
                }
            }

            override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    private fun writeUI() {
        detailMovie?.let {
            binding.imageViewDetail.load("https://image.tmdb.org/t/p/w342"+it.posterPath)
            binding.textViewDetailName.text = it.title
            var category_ = " "
            for (category in it.genres){
                category_ += category.name +" "
                //+=  category.name
            }
            binding.textViewCategoryDetail.text = "Category:$category_"
            binding.textViewDescriptionDetail.text = it.overview


            val average = it.voteAverage/2
           // binding.textViewVotesDetail.text = average.toString()
            binding.textViewVotesDetail.text =String.format("%.1f", average)
            averageStar(average)
        }
       // detailMovie
    }

    private fun averageStar(average:Double){
        if (average>0){
            binding.imageView.setImageResource(R.drawable.ic_star_half_24)
        }
        if (average>=1){
            binding.imageView.setImageResource(R.drawable.ic_star_24)
            binding.imageView2.setImageResource(R.drawable.ic_star_half_24)
        }
        if (average>=2){
            binding.imageView2.setImageResource(R.drawable.ic_star_24)
            binding.imageView3.setImageResource(R.drawable.ic_star_half_24)
        }
        if (average>=3){
            binding.imageView3.setImageResource(R.drawable.ic_star_24)
            binding.imageView4.setImageResource(R.drawable.ic_star_half_24)
        }
        if (average>=4){
            binding.imageView4.setImageResource(R.drawable.ic_star_24)
            binding.imageView5.setImageResource(R.drawable.ic_star_half_24)
        }
        if (average>=4.5){
            binding.imageView5.setImageResource(R.drawable.ic_star_24)
        }
    }

}