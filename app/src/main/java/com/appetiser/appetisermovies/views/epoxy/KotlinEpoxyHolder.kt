package com.appetiser.appetisermovies.views.epoxy

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class KotlinEpoxyHolder : EpoxyHolder() {

    private lateinit var itemView: View
    lateinit var context: Context

    override fun bindView(itemView: View) {
        this.itemView = itemView
        context = itemView.context
    }

    protected fun <V : View> bind(id: Int): ReadOnlyProperty<KotlinEpoxyHolder, V> =
        Lazy { holder: KotlinEpoxyHolder, prop ->
            holder.itemView.findViewById(id) as? V
                ?: throw IllegalStateException("View ID $id for ${prop.name} not found.")
        }

    private class Lazy<V>(private val initializer: (KotlinEpoxyHolder, KProperty<*>) -> V) :
        ReadOnlyProperty<KotlinEpoxyHolder, V> {

        private object EMPTY

        private var value: Any? = EMPTY

        override fun getValue(thisRef: KotlinEpoxyHolder, property: KProperty<*>): V {
            if (value == EMPTY) {
                value = initializer(thisRef, property)
            }

            @Suppress("UNCHECKED_CAST")
            return value as V
        }
    }
}
