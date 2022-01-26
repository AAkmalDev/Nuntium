package uz.pandamobileuz.nuntium.data.storage

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor(@ApplicationContext context: Context) {

    private var sharedPref = context.getSharedPreferences("nuntium", Context.MODE_PRIVATE)

    var isAuth: Boolean
        set(value) = sharedPref.edit { putBoolean(::isAuth.name, value) }
        get() = sharedPref.getBoolean(::isAuth.name, false)

    var selectCategory: String
        set(value) = sharedPref.edit { putString(::selectCategory.name, value) }
        get() = sharedPref.getString(::selectCategory.name, "")!!

    var userEmail: String
        set(value) = sharedPref.edit { putString(::userEmail.name, value) }
        get() = sharedPref.getString(::userEmail.name, "")!!

    var userName: String
        set(value) = sharedPref.edit { putString(::userName.name, value) }
        get() = sharedPref.getString(::userName.name, "")!!

    var userPassword: String
        set(value) = sharedPref.edit { putString(::userPassword.name, value) }
        get() = sharedPref.getString(::userPassword.name, "")!!


}