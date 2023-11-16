package hci.app.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun isAuthenticated(): Boolean {
        return !loadAuthToken().isNullOrEmpty()
    }

    fun loadAuthToken(): String? {
        return preferences.getString(AUTH_TOKEN, null)
    }

    fun removeAuthToken() {
        with (preferences.edit()) {
            remove(AUTH_TOKEN)
            apply()
        }
    }

    fun saveAuthToken(token: String) {
        with(preferences.edit()) {
            putString(AUTH_TOKEN, token)
            apply()
        }
    }

    companion object {
        const val PREFERENCES_NAME = "my_app"
        const val AUTH_TOKEN = "auth_token"
    }
}