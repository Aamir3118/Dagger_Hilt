package com.example.hiltexample.data.api

import com.example.hiltexample.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
