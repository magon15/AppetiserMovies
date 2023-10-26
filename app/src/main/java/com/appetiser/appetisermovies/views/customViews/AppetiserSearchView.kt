package com.appetiser.appetisermovies.views.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.appetiser.appetisermovies.R
import com.appetiser.appetisermovies.databinding.ViewAppetiserSearchBinding

class AppetiserSearchView@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewAppetiserSearchBinding

    init {
        binding = ViewAppetiserSearchBinding.inflate(LayoutInflater.from(context), this, true)

        val generalAttrs = context.obtainStyledAttributes(attrs, R.styleable.General)
        try {
            //set search hint text
            val hint = generalAttrs.getString(R.styleable.General_android_hint) ?: ""
            val editText = binding.appetiserSearchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = hint

            //set search text colors
            editText.setTextColor(ContextCompat.getColor(context, R.color.white))
            editText.setHintTextColor(ContextCompat.getColor(context, R.color.gray))
        } finally {
            generalAttrs.recycle()
        }

        //set search icon colors
        binding.appetiserSearchView.apply {
            val searchIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(ContextCompat.getColor(context, R.color.gray))
            val searchButton = findViewById<ImageView>(androidx.appcompat.R.id.search_button)
            searchButton.setColorFilter(ContextCompat.getColor(context, R.color.gray))
            val searchCancel = findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            searchCancel.setColorFilter(ContextCompat.getColor(context, R.color.gray))
        }
    }

    fun setupOnQueryChange(queryListener: (String) -> Unit) {
        binding.appetiserSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                queryListener(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                queryListener(query)
                return true
            }
        })
    }

    fun setQuery(query: String) {
        binding.appetiserSearchView.isIconified = false
        binding.appetiserSearchView.setQuery(query, true)
        binding.appetiserSearchView.clearFocus()
    }
}