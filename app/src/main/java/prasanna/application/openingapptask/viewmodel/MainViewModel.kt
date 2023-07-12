package prasanna.application.openingapptask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import prasanna.application.openingapptask.model.OpeningAppData
import prasanna.application.openingapptask.repository.AppRepository

class MainViewModel(private val repository: AppRepository) : ViewModel(){
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOpeningAppDataFromRepo()
        }
    }
    val vmOpeningAppLiveData: LiveData<OpeningAppData>
        get() =repository.openingAppLiveData
}