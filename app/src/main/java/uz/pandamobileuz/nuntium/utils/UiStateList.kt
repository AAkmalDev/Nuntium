package uz.pandamobileuz.nuntium.utils

sealed class UiStateList<out T> {
    data class SUCCESS<out T>(val data: List<T>?) : UiStateList<T>()
    data class ERROR(val message: String, var fromServer: Boolean = false,var code:Int = 0) : UiStateList<Nothing>()
    object LOADING : UiStateList<Nothing>()
    object EMPTY : UiStateList<Nothing>()
}