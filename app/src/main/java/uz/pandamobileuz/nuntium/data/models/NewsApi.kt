package uz.pandamobileuz.nuntium.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class NewsApi(
    val author: String?,
    val content: String?,
    val description: String?,
    @PrimaryKey
    val publishedAt: String,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):java.io.Serializable