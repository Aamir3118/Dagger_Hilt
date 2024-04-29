package com.example.hiltexample.data.repository

import com.example.hiltexample.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getUser() = apiHelper.getUsers()
}