package uz.pandamobileuz.nuntium.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.storage.LocalStorage
import uz.pandamobileuz.nuntium.databinding.FragmentRegisterBinding
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment
import uz.pandamobileuz.nuntium.ui.custom.ProgressDialog
import uz.pandamobileuz.nuntium.utils.TextWatcherWrapper
import uz.pandamobileuz.nuntium.utils.showToast
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var auth: FirebaseAuth
    private var userName: String? = null
    private var email: String? = null
    private var newPassword: String? = null
    private var passwordRepeat: String? = null
    private var dialog: ProgressDialog? = null

    @Inject
    lateinit var localStorage: LocalStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        dialog = ProgressDialog()

        binding.apply {
            usernameEditText.addTextChangedListener(TextWatcherWrapper { userNameEdit ->
                if (userNameEdit!!.isNotEmpty()) {
                    usernameEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_active_back)
                    userName = userNameEdit.toString()
                } else {
                    usernameEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_inactive_back)
                }
            })

            emailEditText.addTextChangedListener(TextWatcherWrapper { emailEdit ->
                if (emailEdit!!.isNotEmpty()) {
                    emailEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_active_back)
                    email = emailEdit.toString()
                } else {
                    emailEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_inactive_back)
                }
            })

            newPasswordEditText.addTextChangedListener(TextWatcherWrapper { newPasswordEdit ->
                if (newPasswordEdit!!.isNotEmpty()) {
                    newPasswordEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_active_back)
                    newPassword = newPasswordEdit.toString()
                } else {
                    newPasswordEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_inactive_back)
                }

                if (newPasswordEdit.length < 6) {
                    newPasswordEditTextError.text = "password min length 6"
                    newPasswordEditTextError.isVisible = true
                } else {
                    newPasswordEditTextError.isVisible = false
                }

            })

            repeatEditText.addTextChangedListener(TextWatcherWrapper { repeatPasswordEdit ->
                if (repeatPasswordEdit!!.isNotEmpty()) {
                    repeatEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_active_back)
                    passwordRepeat = repeatPasswordEdit.toString()
                } else {
                    repeatEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_inactive_back)
                }

                if (repeatPasswordEdit.length < 6) {
                    repeatEditTextError.text = "password min length 6"
                    repeatEditTextError.isVisible = true
                } else {
                    repeatEditTextError.isVisible = false
                }

            })

            btnSignUp.setOnClickListener {
                dialog!!.show(childFragmentManager, "show")
                if (newPassword != passwordRepeat) {
                    showToast("bir xil emas")
                } else {
                    registerUser()
                }
            }
        }
    }

    private fun registerUser() {
        if (userName!!.isNotEmpty() && email!!.isNotEmpty() && newPassword!!.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email!!, passwordRepeat!!)
                .addOnCompleteListener { body ->
                    if (body.isSuccessful) {
                        showToast("Ajoyib")
                        localStorage.userName = userName!!
                        localStorage.userEmail = email!!
                        localStorage.isAuth = true
                        findNavController().navigate(R.id.categoryAuthFragment)
                    } else {
                        Log.d("TagRegisterException", "registerUser: ${body.exception}")
                        showToast("Xato")
                    }
                    Log.d("TagRegisterUser", "registerUser: ${body.result!!.additionalUserInfo}")
                    Log.d("TagRegisterUser", "registerUser: ${body.result!!.credential}")
                    Log.d("TagRegisterUser", "registerUser: ${body.result!!.user}")
                    Log.d("TagRegisterUser", "registerUser: ${body.result!!.toString()}")
                    dialog!!.dismiss()
                }
        } else {
            showToast("bo'sh maydonlarni to'ldiring")
        }
    }

}