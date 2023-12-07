package com.example.biteshub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.biteshub.dataclass.LoginDataAccount
import com.example.biteshub.dataclass.RegisterDataAccount
import com.example.biteshub.dataclass.ResponseLogin
import com.example.biteshub.repository.MainRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val messageLiveData = MutableLiveData<String>()
    var message: LiveData<String> = mainRepository.message
    var isLoading: LiveData<Boolean> = mainRepository.isLoading

    val userlogin: LiveData<ResponseLogin> = mainRepository.userlogin

    fun login(loginDataAccount: LoginDataAccount) {
        mainRepository.getResponseLogin(loginDataAccount)
    }

    fun register(registDataUser: RegisterDataAccount) {
        mainRepository.getResponseRegister(registDataUser)
    }
}