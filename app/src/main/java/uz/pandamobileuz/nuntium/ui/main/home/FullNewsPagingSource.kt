package uz.pandamobileuz.nuntium.ui.main.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import uz.pandamobileuz.nuntium.data.models.NewsApi
import uz.pandamobileuz.nuntium.data.repository.MainRepository
import uz.pandamobileuz.nuntium.utils.Constants
import java.util.*

class FullNewsPagingSource(
    private val repository: MainRepository,
    private val query: String,
    private val from: String,
) : PagingSource<Int, NewsApi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsApi> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), null, null)
        }
        try {
            val pageNumber = params.key ?: Constants.INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(Constants.MAX_PAGE_SIZE)
            val response = repository.getFullNews(query, pageNumber, pageSize, from)

            if (response.isSuccessful) {
                val articles = response.body()!!.articles
                val nextPageNumber = if (articles.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(articles, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(response))
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsApi>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}