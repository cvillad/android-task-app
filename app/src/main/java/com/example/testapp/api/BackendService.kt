package com.example.testapp.api

import com.example.testapp.models.Auth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BackendService {
    @POST("users/login")
    suspend fun login(@Body user: Auth.LoginBody): Auth.DefaultResponse

    companion object {
        private var client: BackendService? = null
        private var baseUrl: String = "https://de0e-186-98-30-225.ngrok-free.app"

        fun getClient(authorization: String? = null): BackendService {
            if (authorization == null) {
                client = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(BackendService::class.java)
            } else {
                val builder = OkHttpClient.Builder().apply {
                    addInterceptor(
                        Interceptor {
                            val requestBuilder = it.request().newBuilder()
                            requestBuilder.header("Authorization", authorization)
                            return@Interceptor it.proceed(requestBuilder.build())
                        }
                    )
                }.build()
                client = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder)
                    .build().create(BackendService::class.java)
            }
            return client!!
        }
    }
}