package prasanna.application.openingapptask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import prasanna.application.openingapptask.api.RetrofitInstance
import prasanna.application.openingapptask.databinding.FragmentRecentLinksBinding
import prasanna.application.openingapptask.repository.AppRepository
import prasanna.application.openingapptask.repository.ViewModelFactory
import prasanna.application.openingapptask.ui.adapter.RecentLinksAdapter
import prasanna.application.openingapptask.viewmodel.MainViewModel


class RecentLinksFragment : Fragment() {

    private lateinit var recentLinksBinding: FragmentRecentLinksBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recentLinksAdapter: RecentLinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recentLinksBinding = FragmentRecentLinksBinding.inflate(layoutInflater)

        val openingAppDataApi = RetrofitInstance.api
        val repository = AppRepository(openingAppDataApi)
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(
                repository)
        )[MainViewModel::class.java]

        setRecyclerView()

        mainViewModel.vmOpeningAppLiveData.observe(viewLifecycleOwner){
            recentLinksAdapter.linksList = it.data.recent_links
        }

        return recentLinksBinding.root
    }

    private fun setRecyclerView() = recentLinksBinding.recentLinksFragmentRv.apply {
        recentLinksAdapter = RecentLinksAdapter()
        adapter = recentLinksAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}