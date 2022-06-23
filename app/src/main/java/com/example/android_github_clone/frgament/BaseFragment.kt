package com.example.android_github_clone.frgament

import androidx.fragment.app.Fragment
import com.example.android_github_clone.model.Explore

public open class BaseFragment : Fragment() {
    protected open fun prepareModelsList(): ArrayList<Explore> {
        val list = ArrayList<Explore>()
        for (i in 1..15) {
            list.add(Explore(null, null))
        }
        return list
    }
}