package com.example.redfinapp.util

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.example.redfinapp.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*
    Taken from: https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
 */

class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer

                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

fun Activity.showSnackbar(view: View, message: String, isError: Boolean = false) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        .also { snackbar ->
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).also {
                it.gravity = Gravity.CENTER_HORIZONTAL
                it.textAlignment = View.TEXT_ALIGNMENT_CENTER
                if (isError) {
                    snackbar.setBackgroundTint(getThemeColor(R.attr.colorError))
                    it.setBackgroundColor(getThemeColor(R.attr.colorError))
                }
            }
        }
        .show()
}

fun <T> MutableLiveData<LiveEvent<T>>.newEvent(item: T) {
    this.value = LiveEvent(item)
}

fun View.makeGone() {
    this.visibility = GONE
}

fun View.makeVisible() {
    this.visibility = VISIBLE
}

@ColorInt
fun Context.getThemeColor(@AttrRes attribute: Int) = TypedValue().let {
    theme.resolveAttribute(attribute, it, true); it.data
}

