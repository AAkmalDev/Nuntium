package uz.pandamobileuz.nuntium.data.models

data class ResponseList(val status: String, val totalResults: Int, val articles: List<NewsApi>)