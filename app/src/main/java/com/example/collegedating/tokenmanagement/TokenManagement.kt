package com.example.collegedating.tokenmanagement

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class TokenManagement(context: Context) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context, "secure_prefs",
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString("saveRefreshJwtToken", token).apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString("saveRefreshJwtToken", null)
    }

    fun newUser(step: Int) {
        sharedPreferences.edit().putInt("step", step).apply()
    }

    fun clearNewUser() {
        sharedPreferences.edit().remove("step").apply()
    }

    fun isNewUser(): Int {
        return sharedPreferences.getInt("step", 0)
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString("accessJwtToken", token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("accessJwtToken", null)
    }


    fun deleteToken() {
        sharedPreferences.edit().remove("accessJwtToken").apply()
        sharedPreferences.edit().remove("refreshJwtToken").apply()
    }

}
