package uz.pandamobileuz.nuntium.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.pandamobileuz.nuntium.data.api.ApiService
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.data.repository.MainRepository
import uz.pandamobileuz.nuntium.utils.UiStateList
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    fun getNews(query: String): Flow<PagingData<NewsApi>> {
        val flow = Pager(PagingConfig(20)) {
            NewsPagingSource(repository, query)
        }.flow.cachedIn(viewModelScope)
        return flow
    }

    fun getFullNews(query: String, from: String): Flow<PagingData<NewsApi>> {
        val flow = Pager(PagingConfig(20)) {
            FullNewsPagingSource(repository, query, from)
        }.flow.cachedIn(viewModelScope)
        return flow
    }


}