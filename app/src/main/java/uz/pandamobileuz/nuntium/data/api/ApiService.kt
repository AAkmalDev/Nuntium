package uz.pandamobileuz.nuntium.data.api

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.pandamobileuz.nuntium.data.models.ResponseList
import uz.pandamobileuz.nuntium.utils.Constants.DEFAULT_PAGE_SIZE
import uz.pandamobileuz.nuntium.utils.Constants.MAX_PAGE_SIZE
import java.util.*

interface ApiService {

    @GET("/v2/everything")
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("from") from: String? = null,
        @Query("sortBy") sortBy: SortBy? = null,
    ): Response<ResponseList>

    @GET("/v2/everything")
    suspend fun fullNews(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("from") from: String? = null,
        @Query("sortBy") sortBy: SortBy? = null,
    ): Response<ResponseList>


    enum class SortBy

}