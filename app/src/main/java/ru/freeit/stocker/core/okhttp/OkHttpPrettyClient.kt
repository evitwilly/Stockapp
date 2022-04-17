package ru.freeit.stocker.core.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.freeit.stocker.core.coroutines.default
import java.lang.IllegalArgumentException

class ServerException : Exception("server exception")

class OkHttpPrettyClient(private val okHttp: OkHttpClient) {

    fun fetchGetJson(url: String) : String {
        val request = Request.Builder().url(url).build()
        val response = okHttp.newCall(request).execute()
        return response.body?.string() ?: throw ServerException()
    }
}