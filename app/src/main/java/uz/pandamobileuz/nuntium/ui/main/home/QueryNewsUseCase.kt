package uz.pandamobileuz.nuntium.ui.main.home

import androidx.paging.PagingSource
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.data.repository.MainRepository
import javax.inject.Inject

class QueryNewsUseCase @Inject constructor(private val repository: MainRepository) {

//    operator fun invoke(query: String): PagingSource<Int, NewsApi> {
//        return repository.getNews(query)
//    }
}