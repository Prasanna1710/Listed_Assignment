package prasanna.application.openingapptask.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object RetrofitInstance {

    private const val AUTH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"

    private val httpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer $AUTH_TOKEN")

        val request = requestBuilder.build()
        chain.proceed(request)
    }).build()

    val api: OpeningAppDataApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.inopenapp.com")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpeningAppDataApi::class.java)
    }
}