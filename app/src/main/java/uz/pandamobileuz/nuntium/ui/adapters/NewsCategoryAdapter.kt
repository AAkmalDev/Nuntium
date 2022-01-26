package uz.pandamobileuz.nuntium.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.databinding.NewsHomeItemBinding
import uz.pandamobileuz.nuntium.ui.main.SaveViewModel
import uz.pandamobileuz.nuntium.utils.SingleBlock

class NewsCategoryAdapter(val viewModel: SaveViewModel) :
    PagingDataAdapter<NewsApi, NewsCategoryAdapter.VHolder>(NewsDiffCallBack()) {

    private var listener: SingleBlock<NewsApi>? = null

    inner class VHolder(private val binding: NewsHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsApi: NewsApi) {
            binding.apply {
                newsImage.load(newsApi.urlToImage) {
                    crossfade(true)
                    crossfade(500)
                    placeholder(R.drawable.placeholder_image)
                    error(R.drawable.placeholder_image)
                }
                desc.text = newsApi.title

                var isLiked = viewModel.getIsLiked(newsApi.publishedAt)

                if (isLiked == 1) {
                    bookmarkImage.setImageResource(R.drawable.ic_bookmark_active)
                }

                bookmarkImage.setOnClickListener {
                    isLiked = if (isLiked == 0) {
                        viewModel.saveNews(newsApi)
                        bookmarkImage.setImageResource(R.drawable.ic_bookmark_active)
                        1
                    } else {
                        viewModel.deleteNews(newsApi.publishedAt)
                        bookmarkImage.setImageResource(R.drawable.ic_bookmark_white)
                        0
                    }
                    notifyDataSetChanged()
                }
            }
            itemView.setOnClickListener {
                listener!!.invoke(newsApi)
            }
        }
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        NewsHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    fun setOnClickListener(block: SingleBlock<NewsApi>) {
        listener = block
    }

}