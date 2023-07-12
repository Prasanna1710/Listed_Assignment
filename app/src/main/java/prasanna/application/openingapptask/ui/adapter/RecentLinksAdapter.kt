package prasanna.application.openingapptask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import prasanna.application.openingapptask.databinding.LinkItemBinding
import prasanna.application.openingapptask.model.RecentLink
import prasanna.application.openingapptask.model.TopLink


class RecentLinksAdapter(): RecyclerView.Adapter<RecentLinksAdapter.LinksViewHolder>() {

    class LinksViewHolder(binding: LinkItemBinding): RecyclerView.ViewHolder(binding.root) {
        val linkName = binding.sampleLinkNameTv
        val link = binding.linkTv
        val timeAgo = binding.sampleLinkTimeAgoTv
        val clicks = binding.clicksTv
    }

    private val diffCallback = object: DiffUtil.ItemCallback<RecentLink>(){
        override fun areItemsTheSame(oldItem: RecentLink, newItem: RecentLink): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: RecentLink, newItem: RecentLink): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var linksList : List<RecentLink>
        get() = differ.currentList
        set(value){
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder {
        return LinksViewHolder(
            LinkItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: LinksViewHolder, position: Int) {
        val recentLink = linksList[position]
        holder.linkName.text = recentLink.title
        holder.link.text = recentLink.smart_link
        holder.timeAgo.text = recentLink.times_ago
        holder.clicks.text = recentLink.total_clicks.toString()
    }

    override fun getItemCount(): Int {
        return linksList.size
    }

}