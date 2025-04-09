package com.sundhar.github.utils

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.sundhar.github.R

object GoogleSignInHelper {
    private lateinit var googleSignInClient: GoogleSignInClient

    fun initGoogleSignIn(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signOut(onSignOut: () -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            onSignOut()
        }
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>, onSuccess: (GoogleSignInAccount) -> Unit, onFailure: (String) -> Unit) {
        try {
            val account = task.getResult(ApiException::class.java)
            onSuccess(account)
        } catch (e: ApiException) {
            onFailure(e.message ?: "Sign-In Failed")
        }
    }
}