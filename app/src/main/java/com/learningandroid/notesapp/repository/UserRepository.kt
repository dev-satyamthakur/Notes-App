package com.learningandroid.notesapp.repository

import android.util.Log
import com.learningandroid.notesapp.api.UserAPI
import com.learningandroid.notesapp.models.UserRequest
import com.learningandroid.notesapp.utils.Constants.TAG
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    suspend fun registerUser(userRequest: UserRequest) {
        val response = userAPI.signup(userRequest)
        Log.d(TAG, response.body().toString())
    }

    suspend fun loginUser(userRequest: UserRequest) {
        val response = userAPI.signin(userRequest)
        Log.d(TAG, response.body().toString())
    }
}