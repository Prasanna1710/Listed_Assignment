package prasanna.application.openingapptask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import prasanna.application.openingapptask.api.RetrofitInstance
import prasanna.application.openingapptask.databinding.ActivityMainBinding
import prasanna.application.openingapptask.repository.AppRepository
import prasanna.application.openingapptask.repository.ViewModelFactory
import prasanna.application.openingapptask.ui.adapter.FragmentPageAdapter
import prasanna.application.openingapptask.viewmodel.MainViewModel
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var fragmentPageAdapter: FragmentPageAdapter
    private var topLocation = ""
    private var topSource = ""
    private var todayClicks = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val openingAppDataApi =RetrofitInstance.api
        val repository = AppRepository(openingAppDataApi)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]


        viewPager2 = binding.viewPager2
        tabLayout = binding.tabLayout
        fragmentPageAdapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = fragmentPageAdapter
        addTabs()
        setListeners()


        mainViewModel.vmOpeningAppLiveData.observe(this) {
            try {
                topLocation = it.top_location
                topSource = it.top_source
                todayClicks = it.total_clicks

                binding.topSourcetv.text = topSource
                binding.topLocationTv.text = topLocation
                binding.todayTotalClicksTv.text = todayClicks.toString()

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

    private fun addTabs(){
        val tab1 = tabLayout.newTab().setText("Top Links")
        val tab2 = tabLayout.newTab().setText("Recent Links")
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)
    }

    private fun setListeners(){
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }


}