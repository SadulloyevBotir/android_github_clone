package com.example.android_github_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_github_clone.databinding.ItemExploreTypeoneBinding
import com.example.android_github_clone.databinding.ItemExploreTypetwoBinding
import com.example.android_github_clone.model.Explore

class ExploreAdapter :
    BaseAdapter() {
    private var differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private val TYPE_1: Int = 0
    private val TYPE_2: Int = 1

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addItems(items:List<Explore>){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExploreAdapterTypeOneViewHolder(ItemExploreTypeoneBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Explore>() {
            override fun areItemsTheSame(oldItem: Explore, newItem: Explore): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Explore, newItem: Explore): Boolean {
                return oldItem == newItem
            }

        }
    }

    private class ExploreAdapterTypeOneViewHolder(var binding: ItemExploreTypeoneBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class ExploreAdapterTypeTwoViewHolder(var binding: ItemExploreTypetwoBinding) :
        RecyclerView.ViewHolder(binding.root)
}