package prasanna.application.openingapptask.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import prasanna.application.openingapptask.ui.fragments.RecentLinksFragment
import prasanna.application.openingapptask.ui.fragments.TopLinksFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            TopLinksFragment()
        }else{
            RecentLinksFragment()
        }
    }
}