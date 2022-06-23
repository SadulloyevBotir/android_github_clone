package com.example.android_github_clone.activity

import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import com.example.android_github_clone.R
import com.example.android_github_clone.database.PrefsManager
import com.example.android_github_clone.databinding.ActivityLoginBinding
import com.example.android_github_clone.utils.ApiConstans.clientID
import com.example.android_github_clone.utils.ApiConstans.oauthLoginURL
import com.example.android_github_clone.utils.Extensions.fireToast
import com.example.android_github_clone.utils.Logger
import com.example.android_github_clone.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    companion object {
        private val TAG: String = LoginActivity::class.java.simpleName.toString()
    }

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        initView()
    }

    private fun initView() {
        binding.apply {
            tvBySigning.setMovementMethod(LinkMovementMethod.getInstance())
            bnSignInWithGithub.setOnClickListener {
                processLogin()
            }

            viewModel.accessToken.observe(this@LoginActivity) { accessToken ->
                sharedPrefs.accessToken = accessToken.accessToken
                Logger.d(TAG, "onCreate: access token: $accessToken")
                Logger.d(TAG, "shared: access token: ${sharedPrefs.accessToken}")
                callMainActivity()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }


        private fun getAccessToken() {
        val uri: Uri? = intent?.data
        if (uri != null) {
            val code = uri.getQueryParameter(getString(R.string.str_code))
            if (code != null) {
                show()
                viewModel.getAccessToken(code)
                fireToast(getString(R.string.str_login_success))
            } else {
                val error = uri.getQueryParameter(getString(R.string.str_something_went_wrong))
                if (error != null) {
                    Logger.d(TAG, "error: $error")
                    fireToast(getString(R.string.str_something_went_wrong))
                }
            }
        }
    }

    private fun processLogin() {
        show()
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("$oauthLoginURL?client_id=$clientID&scope=repo"))
        startActivity(intent)
    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}