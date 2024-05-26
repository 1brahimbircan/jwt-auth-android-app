package com.example.jwtauth.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.jwtauth.R

class LoadingDialog(private val context: Context) {
    private lateinit var dialog: AlertDialog

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog() {
        if (this::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
