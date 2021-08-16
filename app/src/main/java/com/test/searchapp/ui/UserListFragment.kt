package com.test.searchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.searchapp.R
import com.test.searchapp.databinding.FragmentUserListBinding
import com.test.searchapp.ui.adapter.UserListAdapter
import com.test.searchapp.ui.adapter.UserLoadStateAdapter
import com.test.searchapp.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var searchJob: Job? = null
    private val userListAdapter by lazy { UserListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListener()
        setupObserver()
        viewModel.getUsersByName()
    }

    private fun setupListener() {
        with(binding) {
            searchView.addTextChangedListener {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(500)
                    viewModel.getUsersByName(it.toString())
                }
            }

            btnTryAgain.setOnClickListener { retry() }
        }
    }

    private fun setupAdapter() {
        binding.listUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userListAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter(::retry)
            )
        }

        userListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                listUser.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnTryAgain.isVisible =loadState.source.refresh is LoadState.Error
                textNotFound.isVisible = loadState.source.refresh is LoadState.Error

                //not found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    userListAdapter.itemCount < 1){
                    listUser.isVisible = false
                    textNotFound.isVisible = true
                }
            }
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            state.observe(viewLifecycleOwner, ::renderList)
        }
    }

    private fun renderList(state: UserViewModel.State) {
        when (state) {
            is UserViewModel.State.GetUserData -> {
                userListAdapter.submitData(viewLifecycleOwner.lifecycle, state.data)
            }
        }
    }

    private fun retry() {
        userListAdapter.retry()
    }

    override fun onDestroyView() {
        searchJob = null
        binding.listUser.adapter = null
        _binding = null
        super.onDestroyView()
    }
}