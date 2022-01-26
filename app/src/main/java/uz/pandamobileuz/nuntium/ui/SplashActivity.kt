package uz.pandamobileuz.nuntium.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.storage.LocalStorage
import uz.pandamobileuz.nuntium.databinding.ActivitySplashBinding
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var localStorage: LocalStorage

    private var _bn: ActivitySplashBinding? = null
    private val bn get() = _bn!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bn = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bn.root)

        lifecycleScope.launchWhenCreated {
            delay(1500)
            if (localStorage.isAuth) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}