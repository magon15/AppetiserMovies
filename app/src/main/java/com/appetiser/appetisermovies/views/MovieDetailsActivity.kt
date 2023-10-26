package com.appetiser.appetisermovies.views

import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.appetiser.appetisermovies.R
import com.appetiser.appetisermovies.data.model.Movie
import com.appetiser.appetisermovies.databinding.ActivityMovieDetailBinding
import com.appetiser.appetisermovies.view_models.MoviesViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity: FragmentActivity() {

    private val viewModel by viewModels<MoviesViewModel>()

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var movie: Movie
    private var isFavorite = false

    companion object {
        const val MOVIE = "MOVIE"
        const val FAVORITE = "FAVORITE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        //get movie details
        movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getSerializableExtra(MOVIE, Movie::class.java)!!
        }else{
            intent.getSerializableExtra(MOVIE) as Movie
        }
        isFavorite = intent.getBooleanExtra(FAVORITE, false)

        //Set movie as recently viewed. This will also persist the remote Movie object to a local MovieLocal object.
        viewModel.setMovieAsRecentlyViewed(movie)

        //load image poster
        Glide.with(binding.imageMovie).load(movie.artworkUrl100).into(binding.imageMovie)

        //set movie details
        binding.textMovieTitle.text = movie.trackName
        binding.textPrice.text = "${movie.currency} ${movie.trackPrice}"
        binding.textGenre.text = movie.primaryGenreName
        binding.textMovieDescription.text = movie.longDescription

        binding.buttonExit.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        //set movie as favorite or not
        updateFavoriteView()
        binding.buttonFavorite.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteView()
            viewModel.setMovieAsFavorite(isFavorite, movie)
        }

        setupMovieDetailsScrollView()

    }

    private fun updateFavoriteView() {
        binding.buttonFavorite.setImageDrawable(ContextCompat.getDrawable(this,
            if(isFavorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite
        ))
    }

    private fun setupMovieDetailsScrollView() {
        //50% of the view is for the movie poster and other 50% for movie details. We therefore set the top padding of the scrollview
        //as half of the screen height as implemented below.
        binding.root.viewTreeObserver.addOnGlobalLayoutListener (object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val screenHeightPadding = binding.root.height / 2 - binding.viewGradientMovieDetail.height
                binding.scrollViewMovieDetails.setPadding(0, screenHeightPadding, 0, binding.scrollViewMovieDetails.paddingBottom)
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        //slide up movie details at screen show
        val slideUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        slideUpAnimation.interpolator = FastOutSlowInInterpolator()
        binding.scrollViewMovieDetails.startAnimation(slideUpAnimation)
    }
}