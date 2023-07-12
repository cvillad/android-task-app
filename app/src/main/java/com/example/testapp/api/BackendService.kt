package com.example.testapp.api

import com.example.testapp.models.Auth
import com.example.testapp.models.GetTasksResponse
import com.example.testapp.models.Task
import com.example.testapp.models.TaskBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendService {
    @POST("users/login")
    suspend fun login(@Body user: Auth.LoginBody): Auth.DefaultResponse

    @PATCH("tasks/{id}")
    suspend fun updateTask(
        @Header("authorization") authorization: String,
        @Path("id") id: String,
        @Body task: TaskBody
    ): Task

    @GET("tasks")
    suspend fun fetchTasks(@Header("authorization") authorization: String,): GetTasksResponse

    companion object {
        private var client: BackendService? = null
        private var baseUrl: String = "https://de0e-186-98-30-225.ngrok-free.app"

        fun getClient(): BackendService {
            if (client != null) {
                return client!!
            }
            val requestClient = OkHttpClient.Builder().apply {
                addInterceptor(Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(builder)
                })
            }.build()
            client = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(requestClient)
                    .build().create(BackendService::class.java)
            return client!!
        }
    }
}