package com.zhuravlev.stockobserverapp.storage.net

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_URL = "https://finnhub.io/api/v1"
const val TOKEN = "c0nukbv48v6qah6ron2g"

private var okHttpClient = OkHttpClient()
private var retrofit: Retrofit? = null
private var gson = Gson()
private var api: FinnhubApi? = null

fun getOkHttpClient(): OkHttpClient {
    return okHttpClient
}

fun getRetrofit(): Retrofit {
    if (retrofit == null) {
        retrofit = Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
    }
    return retrofit!!
}

fun getApiService(): FinnhubApi {
    if (api == null) {
        api = getRetrofit().create(FinnhubApi::class.java)
    }
    return api!!
}
