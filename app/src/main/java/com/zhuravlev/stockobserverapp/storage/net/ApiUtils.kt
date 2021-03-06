package com.zhuravlev.stockobserverapp.storage.net

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_URL = "https://finnhub.io/api/v1/"
const val WEB_SOCKET_URL = "wss://ws.finnhub.io"
const val TOKEN = "c118csf48v6tj8r9aq5g"

private val okHttpClient = OkHttpClient()
private var retrofit: Retrofit? = null
private val request: Request = Request.Builder().url("$WEB_SOCKET_URL?token=$TOKEN").build()
private var webSocket: WebSocket? = null
private val gson = Gson()
private var api: FinnhubApi? = null

fun getOkHttpClient(): OkHttpClient {
    return okHttpClient
}

fun getWebSocket(): WebSocket {
    if (webSocket == null) {
        webSocket = okHttpClient.newWebSocket(request, WebSocketFinnhubListener())
    }
    return webSocket!!
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
