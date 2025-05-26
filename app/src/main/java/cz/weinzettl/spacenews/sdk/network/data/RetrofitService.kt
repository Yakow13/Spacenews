package cz.weinzettl.spacenews.sdk.network.data

import retrofit2.Retrofit

interface RetrofitService {

    fun getClient(): Retrofit
}