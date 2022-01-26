package uz.pandamobileuz.nuntium.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(val repository: MainRepository) : ViewModel() {

    fun deleteNews(publishedAt: String) = repository.deleteNews(publishedAt)

    fun getIsLiked(publishedAt: String) = repository.getIsLiked(publishedAt)

    fun saveNews(newsApi: NewsApi) = repository.saveNews(newsApi)

    fun getNews() = repository.getNews()
}