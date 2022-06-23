package com.example.android_github_clone.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android_github_clone.R

object Extensions {

    fun SwipeRefreshLayout.setUpColors(context: Context) {
        this.setColorSchemeColors(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
        this.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                context,
                R.color.purple_500
            )
        )
    }

    fun Activity.fireToast(message: String) {
        Toast.makeText(this.baseContext, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.fireToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

}

