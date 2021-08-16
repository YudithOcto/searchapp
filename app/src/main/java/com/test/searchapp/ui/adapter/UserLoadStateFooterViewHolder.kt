package com.test.searchapp.ui.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.test.searchapp.databinding.AdapterUserLoadStateFooterBinding

class UserLoadStateFooterViewHolder(
    private val binding: AdapterUserLoadStateFooterBinding,
    private val onRetry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener {
            onRetry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            tvError.isVisible = loadState !is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading
        }
    }
}