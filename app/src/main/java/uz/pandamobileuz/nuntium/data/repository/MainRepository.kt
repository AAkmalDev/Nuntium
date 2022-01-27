package uz.pandamobileuz.nuntium.data.repository

import androidx.paging.PagingSource
import uz.pandamobileuz.nuntium.data.api.ApiService
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.data.room.NewsDao
import uz.pandamobileuz.nuntium.ui.main.home.NewsPagingSource
import java.util.*
import javax.inject.Inject

class MainRepository @Inject constructor(val api: ApiService, val dao: NewsDao) {

    suspend fun getNews(query: String, pageNumber: Int, pageSize: Int) =
        api.everything(query, pageNumber, pageSize)

    suspend fun getFullNews(query: String, pageNumber: Int, pageSize: Int, from: String) =
        api.fullNews(query, pageNumber, pageSize, from)

    fun getNews() = dao.getNews()

    fun deleteNews(publishedAt: String) = dao.deleteNews(publishedAt)

    fun getIsLiked(publishedAt: String) = dao.getIsLiked(publishedAt)

    fun saveNews(newsApi: NewsApi) = dao.saveNews(newsApi)

    fun data() = dao.getNews()

}