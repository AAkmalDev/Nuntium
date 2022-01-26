package uz.pandamobileuz.nuntium.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import uz.pandamobileuz.nuntium.R
import uz.pandamobileuz.nuntium.data.storage.LocalStorage
import uz.pandamobileuz.nuntium.databinding.FragmentLoginBinding
import uz.pandamobileuz.nuntium.ui.custom.BaseFragment
import uz.pandamobileuz.nuntium.ui.custom.ProgressDialog
import uz.pandamobileuz.nuntium.utils.TextWatcherWrapper
import uz.pandamobileuz.nuntium.utils.showToast
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val REQUEST_CODE = 10
    private lateinit var auth: FirebaseAuth
    private var email: String? = null
    private var newPassword: String? = null
    private var dialog: ProgressDialog? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var localStorage: LocalStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        dialog = ProgressDialog()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_login))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            emailEditText.addTextChangedListener(TextWatcherWrapper { emailEdit ->
                if (emailEdit!!.isNotEmpty()) {
                    emailEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_active_back)
                    email = emailEdit.toString()
                } else {
                    emailEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_inactive_back)
                    emailEditTextError.text = getString(R.string.not_empty)
                }
            })

            passwordEditText.addTextChangedListener(TextWatcherWrapper { passwordEdit ->
                if (passwordEdit!!.isNotEmpty()) {
                    passwordEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_active_back)
                    newPassword = passwordEdit.toString()
                } else {
                    passwordEditText.background = ContextCompat.getDrawable(requireContext(),
                        R.drawable.edit_text_inactive_back)
                    passwordEditTextError.text = getString(R.string.not_empty)
                }
            })

            btnSignIn.setOnClickListener {
                dialog!!.show(childFragmentManager, "login")
                loginUser()
            }

            signInGoogle.setOnClickListener {
                signIn()
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAGFirebase", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.d("TAG", "Google sign in failed", e)
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    showToast("Succes")
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGSignIn", "signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("TAGSignIn", "signInWithCredential:success")
                    //updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

    }

    private fun loginUser() {
        auth.signInWithEmailAndPassword(email!!, newPassword!!).addOnCompleteListener { body ->
            if (body.isSuccessful) {
                showToast("Successful")
                Log.d("LoginFragment1", "loginUser: ${body.result!!.additionalUserInfo}")
                Log.d("LoginFragment2", "loginUser: ${body.result!!.credential}")
                Log.d("LoginFragment3", "loginUser: ${body.result!!.user}")
                localStorage.userEmail = email!!
                localStorage.isAuth = true
                findNavController().navigate(R.id.categoryAuthFragment)
            } else {
                showToast("Error")
                Log.d("LoginFragment1", "loginUser: ${body.result!!.additionalUserInfo}")
                Log.d("LoginFragment2", "loginUser: ${body.result!!.credential}")
                Log.d("LoginFragment3", "loginUser: ${body.result!!.user}")
            }
            dialog!!.dismiss()
        }
    }

}