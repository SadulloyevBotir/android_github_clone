package com.example.android_github_clone.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ProfileRepositoriesItemDecoration(
    private val left: Int,
    private val right: Int,
    private var top: Int,
    private val bottom: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        outRect.right = right
        outRect.left = left
        outRect.top = top
        outRect.bottom = bottom
    }
}