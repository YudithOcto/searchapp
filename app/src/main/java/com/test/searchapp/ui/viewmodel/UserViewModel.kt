package com.test.searchapp.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.test.searchapp.domain.model.User
import com.test.searchapp.repository.UserRepository
import com.test.searchapp.util.REGEX_ALLOWED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(), LifecycleObserver {

    val state = MutableLiveData<State>()

    fun getUsersByName(query: String? = "") {
        viewModelScope.launch {
            repository.getUsersByName(query.encodeQuery()).collect {
                state.postValue(State.GetUserData(it))
            }
        }
    }

    private fun String?.encodeQuery(): String =  Uri.encode("$this+in:login", REGEX_ALLOWED)

    sealed class State {
        data class GetUserData(val data: PagingData<User>) : State()
    }
}