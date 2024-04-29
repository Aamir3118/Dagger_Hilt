package com.example.hiltexample.ui.main.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltexample.data.model.User
import com.example.hiltexample.data.repository.MainRepository
import com.example.hiltexample.utils.NetworkHelper
import com.example.hiltexample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUser().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                    } else {
                        _users.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _users.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}