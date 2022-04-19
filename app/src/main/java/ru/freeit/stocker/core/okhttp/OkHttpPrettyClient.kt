package ru.freeit.stocker.core.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class OkHttpPrettyClient(private val okHttp: OkHttpClient) {

    fun fetchGetJson(url: String) : String {
        val request = Request.Builder().url(url).build()
        val response = okHttp.newCall(request).execute()
        return response.body?.string() ?: throw ServerException()
    }

    fun webSocket(url: String, listener: WebSocketListener) : WebSocket {
        val request = Request.Builder().url(url).build()
        return okHttp.newWebSocket(request, listener)
    }
}