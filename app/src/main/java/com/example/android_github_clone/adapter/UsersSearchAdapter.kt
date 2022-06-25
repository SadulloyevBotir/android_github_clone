package com.example.android_github_clone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_github_clone.databinding.ItemSearchUserBinding
import com.example.android_github_clone.model.repositories_search_response.User
import com.example.android_github_clone.model.repositories_search_response.UserResponse

class UsersSearchAdapter : BaseAdapter() {
    private val differ = AsyncListDiffer(this, ITEM_DIFF)


    inner class UserSearchViewHolder(var binding: ItemSearchUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val user = differ.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(user.avatar_url).into(ivProfile)
                tvNikname.text = user.login
                tvProjectName.text = user.repos_url
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

    fun addItems(response: UserResponse) {
        differ.submitList(response.items)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User,
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User,
            ): Boolean {
                return true
            }
        }
    }
}