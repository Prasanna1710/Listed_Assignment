package prasanna.application.openingapptask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import prasanna.application.openingapptask.databinding.LinkItemBinding

import prasanna.application.openingapptask.model.TopLink

class TopLinksAdapter: RecyclerView.Adapter<TopLinksAdapter.LinksViewHolder>() {

    class LinksViewHolder(binding: LinkItemBinding): RecyclerView.ViewHolder(binding.root) {
        val linkName = binding.sampleLinkNameTv
        val link = binding.linkTv
        val timeAgo = binding.sampleLinkTimeAgoTv
        val clicks = binding.clicksTv
    }

    private val diffCallback = object: DiffUtil.ItemCallback<TopLink>(){
        override fun areItemsTheSame(oldItem: TopLink, newItem: TopLink): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TopLink, newItem: TopLink): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var linksList : List<TopLink>
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
        val topLink = linksList[position]
        holder.linkName.text = topLink.title
        holder.link.text = topLink.smart_link
        holder.timeAgo.text = topLink.times_ago
        holder.clicks.text = topLink.total_clicks.toString()
    }

    override fun getItemCount(): Int {
        return linksList.size
    }

}