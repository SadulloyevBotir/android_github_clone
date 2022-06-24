package com.example.android_github_clone.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_github_clone.databinding.ItemSearchRepositoriesBinding
import com.example.android_github_clone.model.repositories_search_response.ItemsItem
import com.example.android_github_clone.model.repositories_search_response.RepositoriesResponse

class RepositoriesSearchAdapter() : BaseAdapter() {
    private val differ = AsyncListDiffer(this, ITEM_DIFF)

    inner class ViewHolderForSearchRepositories(var binding: ItemSearchRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            val userRepositories = differ.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(userRepositories.owner?.avatar_url)
                    .into(ivProfile)
                tvName.text = userRepositories.owner?.login
                tvProjectName.text = userRepositories.name
                tvLanguage.text = userRepositories.language.toString()
                tvStarsCount.text = userRepositories.stargazers_count.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemSearchRepositoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolderForSearchRepositories(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderForSearchRepositories) holder.onBind(position)

    }

    override fun getItemCount(): Int = differ.currentList.size


    fun addItems(repositoriesResponse: RepositoriesResponse?) {
        differ.submitList(repositoriesResponse?.items)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem,
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem,
            ): Boolean {
                return true
            }
        }
    }
}
