package com.test.searchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.test.searchapp.databinding.AdapterUserLoadStateFooterBinding

class UserLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UserLoadStateFooterViewHolder>() {
    override fun onBindViewHolder(holder: UserLoadStateFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UserLoadStateFooterViewHolder {
        val binding =
            AdapterUserLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserLoadStateFooterViewHolder(binding) {
            retry.invoke()
        }
    }
}