package uz.pandamobileuz.nuntium.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _bn: ActivityLoginBinding? = null
    private val binding get() = _bn!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bn = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}