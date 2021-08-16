package com.test.searchapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.test.searchapp.databinding.AdapterUserItemBinding
import com.test.searchapp.domain.model.User
import com.test.searchapp.util.loadImage

class UserViewHolder(private val binding: AdapterUserItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        with(binding) {
            textUserName.text = user.name
            imageUser.loadImage(user.image)
        }
    }
}