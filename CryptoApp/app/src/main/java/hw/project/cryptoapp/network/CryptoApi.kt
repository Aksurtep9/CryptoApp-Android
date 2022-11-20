package hw.project.cryptoapp.network
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CryptoApi {
    @Headers("X-CMC_PRO_API_KEY: 32751ff4-df46-4029-88e9-8b405fe82533")
    @GET("/v1/cryptocurrency/listings/latest?limit=1000")
    fun getCryptos(
    ): Call<CryptoData?>?
}

