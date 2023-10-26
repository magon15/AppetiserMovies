package com.appetiser.appetisermovies.views.epoxy

import android.annotation.SuppressLint
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.appetiser.appetisermovies.R
import com.appetiser.appetisermovies.data.model.Movie
import com.bumptech.glide.Glide

@EpoxyModelClass
abstract class MovieEpoxyModel: EpoxyModelWithHolder<MovieEpoxyModel.Companion.Holder>() {

    @EpoxyAttribute
    lateinit var movie: Movie

    @EpoxyAttribute
    var favorite: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ((Movie, Boolean, ImageView) -> Unit)? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var favoriteListener: ((Movie, Boolean) -> Unit)? = null

    override fun getDefaultLayout(): Int {
        return R.layout.item_movie
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        val context = holder.context

        holder.apply {
            //set image poster
            Glide.with(context)
                .load(movie.artworkUrl100)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_placeholder))
                .into(imagePoster)

            //set movie details
            textMovieTitle.text = movie.trackName
            textGenre.text = movie.primaryGenreName
            textPrice.text = "${movie.currency} ${movie.trackPrice}"

            //set click listeners
            button.setOnClickListener {
                clickListener?.let { it(movie, favorite, imagePoster) }
            }

            buttonFavorite.setImageDrawable(context.getDrawable(
                if(favorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite
            ))
            buttonFavorite.setOnClickListener {
                favoriteListener?.let { it(movie, !favorite) }
            }
        }
    }

    companion object {
        class Holder: KotlinEpoxyHolder() {
            val imagePoster by bind<ImageView>(R.id.imageMovie)
            val button by bind<ImageButton>(R.id.buttonSelectMovie)
            val buttonFavorite by bind<ImageButton>(R.id.buttonFavorite)
            val textMovieTitle by bind<TextView>(R.id.textMovieTitle)
            val textGenre by bind<TextView>(R.id.textGenre)
            val textPrice by bind<TextView>(R.id.textPrice)
        }
    }

}