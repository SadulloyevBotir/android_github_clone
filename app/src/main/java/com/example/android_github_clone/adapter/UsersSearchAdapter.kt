package com.example.android_github_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_github_clone.databinding.ItemSearchUserBinding
import com.example.android_github_clone.model.ItemsItem
import com.example.android_github_clone.model.UsersResponse

class UsersSearchAdapter : BaseAdapter() {
    private val differ = AsyncListDiffer(this, ITEM_DIFF)


    inner class UserSearchViewHolder(var binding: ItemSearchUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val user = differ.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(user.avatarUrl).into(ivProfile)
                tvNikname.text = user.reposUrl
                tvProjectName.text = user.login
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemSearchUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserSearchViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserSearchViewHolder) holder.onBind(position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun addItems(response: UsersResponse?) {
        differ.submitList(response?.items)
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