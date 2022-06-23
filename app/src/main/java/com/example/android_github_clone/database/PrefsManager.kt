package com.example.android_github_clone.database

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private var timer: CountDownTimer? = null

class PrefsManager @Inject constructor(@ApplicationContext context: Context) {
    companion object {
        private const val PREF_NAME = "GithubClone"
        private const val PREF_MODE = Context.MODE_PRIVATE
        private const val PREF_KAY = "ACCESS_TOKEN"
    }

    private var mySharedPref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, PREF_MODE)

    var accessToken: String
        set(value) = mySharedPref.edit().putString(PREF_KAY, value).apply()
        get() = mySharedPref.getString(PREF_KAY, "")!!

}