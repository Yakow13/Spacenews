package cz.weinzettl.spacenews.feature.network.service.impl

import cz.weinzettl.spacenews.BuildConfig
import cz.weinzettl.spacenews.feature.network.service.RetrofitService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class DefaultRetrofitService : RetrofitService {

    override fun getClient(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val client = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
                }).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    companion object {
        private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4"
    }
}