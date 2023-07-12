package prasanna.application.openingapptask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import prasanna.application.openingapptask.api.RetrofitInstance
import prasanna.application.openingapptask.databinding.FragmentTopLinksBinding
import prasanna.application.openingapptask.repository.AppRepository
import prasanna.application.openingapptask.repository.ViewModelFactory
import prasanna.application.openingapptask.ui.adapter.TopLinksAdapter
import prasanna.application.openingapptask.viewmodel.MainViewModel


class TopLinksFragment : Fragment() {
    private lateinit var topLinksBinding: FragmentTopLinksBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var topLinksAdapter: TopLinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        topLinksBinding = FragmentTopLinksBinding.inflate(layoutInflater)

        val openingAppDataApi = RetrofitInstance.api
        val repository = AppRepository(openingAppDataApi)
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(
                repository)
        )[MainViewModel::class.java]

        setRecyclerView()

        mainViewModel.vmOpeningAppLiveData.observe(viewLifecycleOwner){
            topLinksAdapter.linksList = it.data.top_links
        }

        return topLinksBinding.root
    }

    private fun setRecyclerView() = topLinksBinding.topLinksFragmentRv.apply {
        topLinksAdapter = TopLinksAdapter()
        adapter = topLinksAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}