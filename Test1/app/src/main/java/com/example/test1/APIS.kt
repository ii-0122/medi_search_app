package com.example.test1

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIS {
    // Database 연결

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("user")
    fun post_users(
        @Body jsonparams: PostModel
    ): Call<PostResult>

    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("score_num")
    fun get_datas(): Call<HTTP_GET_Model>


    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = ${DB_URL} // 주소

        fun create(): APIS {
            val gson:Gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(APIS::class.java)
        }
    }
}
