package com.example.medicine_detect.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.example.medicine_detect.R
import com.example.medicine_detect.widget.PermissionRationaleDialog

object AppUtils {


    fun isAndroid_Q_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    fun isAndroid_R_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    private fun toSettings(packageName: String, context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    fun showPermissionRationale(context: Context, callback: () -> Unit) {
        PermissionRationaleDialog.with(context)
            .setCancelable(true)
            .setPositiveButtonClick {
                callback.invoke()
            }
            .show()
    }

    fun showDialogNeverAskAgain(context: Context, packageName: String, message: String) {
        PermissionRationaleDialog.with(context)
            .setCancelable(true)
            .setMessage(message)
            .setPositiveLabel(context.getString(R.string.to_setting))
            .setPositiveButtonClick {
                toSettings(packageName, context)
            }
            .show()
    }

}