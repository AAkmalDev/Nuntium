package uz.pandamobileuz.nuntium.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

typealias SingleBlock<T> = (T) -> Unit


fun Fragment.showToast(message:String){
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}