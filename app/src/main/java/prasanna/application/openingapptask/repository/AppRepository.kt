package prasanna.application.openingapptask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import prasanna.application.openingapptask.api.OpeningAppDataApi
import prasanna.application.openingapptask.model.OpeningAppData

class AppRepository(private val api: OpeningAppDataApi) {

    private val mutableLiveData = MutableLiveData<OpeningAppData>()

    val openingAppLiveData : LiveData<OpeningAppData>
        get() = mutableLiveData

    suspend fun getOpeningAppDataFromRepo(){
        val result = api.getOpeningAppDataFromApi()
        if(result.isSuccessful && result.body() != null)
        {
            mutableLiveData.postValue(result.body())
        }
    }
}