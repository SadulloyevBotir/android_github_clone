package com.example.android_github_clone.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.ruyeo.newcut.utils.dialogs.ProgressBarDialog

open class BaseActivity : AppCompatActivity() {

    lateinit var loading: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = ProgressBarDialog(this)
    }


    fun show() {
        loading.show()
    }

    fun hide() {
        loading.dismiss()
    }

    fun callLoginActivity(context: Activity) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun callMainActivity(context: Activity) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}