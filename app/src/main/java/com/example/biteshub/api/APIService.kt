package com.example.biteshub.api

import com.example.biteshub.dataclass.LoginDataAccount
import com.example.biteshub.dataclass.RegisterDataAccount
import com.example.biteshub.dataclass.ResponseDetail
import com.example.biteshub.dataclass.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("register")
    fun registUser(@Body requestRegister: RegisterDataAccount): Call<ResponseDetail>

    @POST("login")
    fun loginUser(@Body requestLogin: LoginDataAccount): Call<ResponseLogin>
}