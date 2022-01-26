package uz.pandamobileuz.nuntium.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.databinding.NewsSmallItemBinding
import uz.pandamobileuz.nuntium.utils.SingleBlock

class SmallNewsAdapter:PagingDataAdapter<NewsApi,SmallNewsAdapter.VHolder>(NewsDiffCallBack()) {

    private var listener: SingleBlock<NewsApi>? = null

    inner class VHolder(private val smallItemBinding: NewsSmallItemBinding) :
        RecyclerView.ViewHolder(smallItemBinding.root){
            fun bind(news: NewsApi){
                smallItemBinding.apply {
                    imageNews.load(news.urlToImage){
                        crossfade(true)
                        crossfade(500)
                        placeholder(R.drawable.placeholder_image)
                        error(R.drawable.placeholder_image)
                    }
                    title.text = news.title

                    category.text = news.author

                }

                itemView.setOnClickListener {
                    listener!!.invoke(news)
                }

            }
        }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        NewsSmallItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    fun setOnClickListener(block: SingleBlock<NewsApi>){
        listener = block
    }

}