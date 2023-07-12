package prasanna.application.openingapptask

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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
    private lateinit var lineChart: LineChart
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


        lineChart = binding.chartView
        viewPager2 = binding.viewPager2
        tabLayout = binding.tabLayout
        fragmentPageAdapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = fragmentPageAdapter
        addTabs()
        setListeners()
        setLineChart()


        mainViewModel.vmOpeningAppLiveData.observe(this) {
            try {
                topLocation = it.top_location
                topSource = it.top_source
                todayClicks = it.total_clicks

                binding.topSourcetv.text = topSource
                binding.topLocationTv.text = topLocation
                binding.todayTotalClicksTv.text = todayClicks.toString()

                val urlChart = it.data.overall_url_chart
                val juneClicks = urlChart.`2023-06-11` + urlChart.`2023-06-12` + urlChart.`2023-06-13` +
                        urlChart.`2023-06-14` + urlChart.`2023-06-15` + urlChart.`2023-06-16` +
                        urlChart.`2023-06-17` + urlChart.`2023-06-18` + urlChart.`2023-06-19` +
                        urlChart.`2023-06-20` + urlChart.`2023-06-21` + urlChart.`2023-06-22` +
                        urlChart.`2023-06-23` + urlChart.`2023-06-24` + urlChart.`2023-06-25` +
                        urlChart.`2023-06-26` + urlChart.`2023-06-27` + urlChart.`2023-06-28` +
                        urlChart.`2023-06-29` + urlChart.`2023-06-30`

                val julyClicks = urlChart.`2023-07-01` + urlChart.`2023-07-02` + urlChart.`2023-07-03` +
                        urlChart.`2023-07-04` + urlChart.`2023-07-05` + urlChart.`2023-07-06` +
                        urlChart.`2023-07-07` + urlChart.`2023-07-08` + urlChart.`2023-07-09` +
                        urlChart.`2023-07-10` + urlChart.`2023-07-11`


                val entries = arrayListOf<Entry>()
                entries.add(Entry(0f, 0f))
                entries.add(Entry(1f, 0f))
                entries.add(Entry(2f, 0f))
                entries.add(Entry(3f, 0f))
                entries.add(Entry(4f, 0f))
                entries.add(Entry(5f, juneClicks.toFloat()))
                entries.add(Entry(6f, julyClicks.toFloat()))

                val dataset = LineDataSet(entries, "Clicks")
                dataset.color = Color.BLUE

                val lineData = LineData(dataset)
                lineChart.data = lineData
                lineChart.invalidate()

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

    private fun setLineChart(){
        val description = Description()
        description.text = "OverView"
        description.setPosition(150f, 16f)
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)

        val xArray = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "July")

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xArray)
        xAxis.labelCount = 7
        xAxis.granularity = 1f

        val yAxis = lineChart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 200f
        yAxis.axisLineWidth = 1f
        yAxis.axisLineColor = Color.BLUE
        yAxis.labelCount = 4
    }

}