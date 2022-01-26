package uz.pandamobileuz.nuntium.ui.main

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.data.storage.LocalStorage
import uz.pandamobileuz.nuntium.databinding.FragmentProfileBinding
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private var auth: FirebaseAuth? = null

    @Inject
    lateinit var localStorage: LocalStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.apply {

            userEmail.text = localStorage.userEmail

            signOutLayout.setOnClickListener {
                auth!!.signOut()
                localStorage.isAuth = false
            }
        }

    }

}