package com.appetiser.appetisermovies.views.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.appetiser.appetisermovies.R

@EpoxyModelClass
abstract class MovieHeaderEpoxyModel: EpoxyModelWithHolder<MovieHeaderEpoxyModel.Companion.Holder>() {

    @EpoxyAttribute
    var header: Int? = null

    override fun getDefaultLayout(): Int {
        return R.layout.item_list_header
    }

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.apply {
            header?.let {
                textHeader.text = holder.context.getString(it)
            }
        }
    }

    companion object {
        class Holder: KotlinEpoxyHolder() {
            val textHeader by bind<TextView>(R.id.textHeader)
        }
    }

}