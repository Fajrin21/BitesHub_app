package com.example.biteshub.injection

import android.content.Context
import com.example.biteshub.api.APIConfig
import com.example.biteshub.repository.MainRepository

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val apiService = APIConfig.getApiService()
        return MainRepository(apiService)
    }
}