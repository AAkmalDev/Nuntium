package uz.pandamobileuz.nuntium.utils

import android.text.Editable
import android.text.TextWatcher

open class TextWatcherWrapper(val onSuccess:(Editable?)-> Unit) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {
        onSuccess(p0)
    }
}