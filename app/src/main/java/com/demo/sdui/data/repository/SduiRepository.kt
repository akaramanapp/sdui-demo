package com.demo.sdui.data.repository

import com.demo.sdui.data.model.SduiPage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class SduiRepository {

    companion object {
        // Android emülatörü için 10.0.2.2 → host makinenin localhost'u
        // Fiziksel cihaz kullanıyorsan makinenin LAN IP'sini yaz (ör. 192.168.1.x)
        private const val BASE_URL = "http://10.0.2.2:3000"
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    suspend fun fetchPage(): SduiPage = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("$BASE_URL/api/page")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                error("Sunucu hatası: HTTP ${response.code}")
            }
            val body = response.body?.string()
                ?: error("Sunucudan boş yanıt geldi")
            gson.fromJson(body, SduiPage::class.java)
        }
    }
}
