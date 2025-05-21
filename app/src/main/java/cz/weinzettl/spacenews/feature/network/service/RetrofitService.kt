package cz.weinzettl.spacenews.feature.network.service

import retrofit2.Retrofit

interface RetrofitService {

    fun getClient(): Retrofit
}