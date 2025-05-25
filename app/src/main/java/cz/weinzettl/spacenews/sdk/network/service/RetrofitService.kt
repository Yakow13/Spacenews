package cz.weinzettl.spacenews.sdk.network.service

import retrofit2.Retrofit

interface RetrofitService {

    fun getClient(): Retrofit
}