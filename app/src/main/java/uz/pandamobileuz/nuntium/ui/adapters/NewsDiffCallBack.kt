package uz.pandamobileuz.nuntium.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import uz.pandamobileuz.nuntium.data.models.NewsApi

class NewsDiffCallBack : DiffUtil.ItemCallback<NewsApi>() {
        override fun areItemsTheSame(oldItem: NewsApi, newItem: NewsApi): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsApi, newItem: NewsApi): Boolean {
            return oldItem == newItem
        }
    }