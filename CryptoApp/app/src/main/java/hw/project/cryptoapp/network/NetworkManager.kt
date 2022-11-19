package hw.project.cryptoapp.network

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val cryptoApi : CryptoApi

    private const val SERVICE_URL = "https://pro-api.coinmarketcap.com"
    private const val APP_ID = "32751ff4-df46-4029-88e9-8b405fe82533"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cryptoApi = retrofit.create(CryptoApi::class.java)
    }

    fun getCryptos(): Call<CryptoData?>? {
       return cryptoApi.getCryptos()
    }
}