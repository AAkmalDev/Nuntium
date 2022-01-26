package uz.pandamobileuz.nuntium.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.pandamobileuz.nuntium.data.models.NewsApi

@Dao
interface NewsDao {

    @Insert
    fun saveNews(newsApi: NewsApi)

    @Query("select count(publishedAt) from newsapi where publishedAt like :publishedAt")
    fun getIsLiked(publishedAt: String): Int

    @Query("DELETE FROM newsapi WHERE publishedAt = :publishedAt")
    fun deleteNews(publishedAt: String)

    @Query("select * from newsapi")
    fun getNews(): List<NewsApi>
}