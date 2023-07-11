package prasanna.application.openingapptask.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import prasanna.application.openingapptask.viewmodel.MainViewModel

class ViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}