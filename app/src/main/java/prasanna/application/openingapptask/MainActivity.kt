package prasanna.application.openingapptask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import prasanna.application.openingapptask.api.RetrofitInstance
import prasanna.application.openingapptask.repository.AppRepository
import prasanna.application.openingapptask.repository.ViewModelFactory
import prasanna.application.openingapptask.viewmodel.MainViewModel
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openingAppDataApi =RetrofitInstance.api
        val repository = AppRepository(openingAppDataApi)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]

        mainViewModel.vmOpeningAppLiveData.observe(this) {
            try {
                val recentLink = it.data.recent_links
                val topLink = it.data.top_links
                val urlChart = it.data.overall_url_chart
                val extraIncome = it.extra_income
                val topLocation = it.top_location

                Log.e("####",extraIncome.toString())
                Log.e("####", recentLink[0].toString())
                Log.e("####", topLocation)
                Log.e("OverallURL", urlChart.toString())
                Toast.makeText(
                    this@MainActivity,
                    "Success",
                    Toast.LENGTH_SHORT
                ).show()
            }catch (e: IOException){
                Log.e("####", "$e")
            }catch (e: HttpException){
                Log.e("####", "$e")
            }
        }
    }

    private fun getApiData(){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstance.api.getOpeningAppDataFromApi()
            }catch (e: IOException){
                Log.e("####", "$e")
                return@launch
            }catch (e: HttpException){
                Log.e("####", "$e")
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                Log.e("####", response.body()!!.applied_campaign.toString())
                Log.e("####", response.body()!!.data.recent_links.toString())
                Log.e("####", response.body()!!.data.top_links.toString())
                Log.e("####", response.body()!!.data.overall_url_chart.toString())
            }else{
                Log.e("####", "Response Unsuccessful")
            }
        }
    }

}