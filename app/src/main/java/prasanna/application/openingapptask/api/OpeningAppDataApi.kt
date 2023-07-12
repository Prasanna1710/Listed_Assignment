package prasanna.application.openingapptask.api

import prasanna.application.openingapptask.model.OpeningAppData
import retrofit2.Response
import retrofit2.http.GET

interface OpeningAppDataApi {
    @GET("/api/v1/dashboardNew")
    suspend fun getOpeningAppDataFromApi(): Response<OpeningAppData>
}