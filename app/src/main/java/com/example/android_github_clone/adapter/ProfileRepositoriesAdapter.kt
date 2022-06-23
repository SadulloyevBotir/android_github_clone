package com.example.android_github_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_github_clone.databinding.ItemProfieRepositoriesBinding
import com.example.android_github_clone.model.Explore
import com.example.android_github_clone.model.UserRepositoriesResponseItem

class ProfileRepositoriesAdapter :
    BaseAdapter() {

    private var differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addItems(items: List<UserRepositoriesResponseItem>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfileRepositoriesAdapterViewHolder(ItemProfieRepositoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProfileRepositoriesAdapterViewHolder) holder.onBind(position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserRepositoriesResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserRepositoriesResponseItem,
                newItem: UserRepositoriesResponseItem,
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: UserRepositoriesResponseItem,
                newItem: UserRepositoriesResponseItem,
            ): Boolean {
                return true
            }


        }
    }

    inner class ProfileRepositoriesAdapterViewHolder(var binding: ItemProfieRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val userRepositories = differ.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(userRepositories.owner?.avatarUrl)
                    .into(ivProfile)
                tvNikname.text = userRepositories.owner?.login
                tvProjectName.text = userRepositories.name
                tvStarsCount.text = userRepositories.stargazersCount.toString()
                tvLanguage.text = userRepositories.language
            }
        }
    }
}