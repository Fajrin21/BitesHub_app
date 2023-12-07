package com.example.biteshub.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.biteshub.api.APIConfig
import com.example.biteshub.api.APIService
import com.example.biteshub.dataclass.LoginDataAccount
import com.example.biteshub.dataclass.RegisterDataAccount
import com.example.biteshub.dataclass.ResponseDetail
import com.example.biteshub.dataclass.ResponseLogin
import com.example.biteshub.dataclass.*
import com.example.biteshub.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val apiService: APIService) {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<ResponseLogin>()
    var userlogin: LiveData<ResponseLogin> = _userLogin

    fun getResponseLogin(loginDataAccount: LoginDataAccount) {
        wrapEspressoIdlingResource {
            _isLoading.value = true
            val api = APIConfig.getApiService().loginUser(loginDataAccount)
            api.enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()

                    if (response.isSuccessful) {
                        _userLogin.value = responseBody!!
                        _message.value = "Hello ${_userLogin.value!!.loginResult.username}!"
                    } else {
                        when (response.code()) {
                            401 -> _message.value =
                                "Email atau password yang anda masukan salah, silahkan coba lagi"
                            408 -> _message.value =
                                "Koneksi internet anda lambat, silahkan coba lagi"
                            else -> _message.value = "Pesan error: " + response.message()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = "Pesan error: " + t.message.toString()
                }

            })
        }
    }

    fun getResponseRegister(registDataUser: RegisterDataAccount) {
        wrapEspressoIdlingResource {
            _isLoading.value = true
            val api = APIConfig.getApiService().registUser(registDataUser)
            api.enqueue(object : Callback<ResponseDetail> {
                override fun onResponse(
                    call: Call<ResponseDetail>,
                    response: Response<ResponseDetail>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        when(response.code()){
                            201 -> _message.value =
                                "Akun Berhasil dibuat"
                            else -> _message.value = "Yeay akun berhasil dibuat"
                        }
                    } else {
                        when (response.code()) {
                            400 -> _message.value =
                                "Email yang anda masukan sudah terdaftar, silahkan coba lagi"
                            408 -> _message.value =
                                "Koneksi internet anda lambat, silahkan coba lagi"
                            else -> _message.value = "Pesan error: " + response.message()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = "Pesan error: " + t.message.toString()
                }

            })
        }
    }
}