package com.example.medicine_detect.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.medicine_detect.R

class PermissionRationaleDialog(context: Context) {
    var dialog: AlertDialog? = null
    var builder: AlertDialog.Builder? = null
    var rootView: View? = null
    var isShowing = false
        private set

    companion object {
        fun with(context: Context): PermissionRationaleDialog {
            val sInstance = PermissionRationaleDialog(context)
            sInstance.inflateView()
            return sInstance
        }
    }

    init {
        builder = AlertDialog.Builder(context)
    }

    private fun inflateView() {
        builder?.apply {
            if (rootView == null) {
                rootView = LayoutInflater.from(this.context)
                    .inflate(R.layout.dialog_permission_rationale, null)
                this.setView(rootView)
            }
        }
        rootView?.parent?.let {
            (it as ViewGroup).removeView(rootView)
        }

        rootView?.findViewById<TextView>(R.id.btn_cancel)?.setOnClickListener {
            dismiss()
        }
    }

    fun setCancelable(cancelable: Boolean): PermissionRationaleDialog {
        builder?.setCancelable(cancelable)
        return this
    }

    fun setPositiveLabel(label: String): PermissionRationaleDialog {
        rootView?.findViewById<TextView>(R.id.btn_ok)?.text = label
        return this
    }

    fun setPositiveButtonClick(onClickOkListener: OnClickDialogPermissionListener): PermissionRationaleDialog {
        return setPositiveButtonClick {
            onClickOkListener.onClickOk()
        }
    }

    fun setPositiveButtonClick(onOk: () -> Unit): PermissionRationaleDialog {
        rootView?.findViewById<TextView>(R.id.btn_ok)?.setOnClickListener {
            onOk.invoke()
            dismiss()
        }
        return this
    }

    fun setMessage(message: String): PermissionRationaleDialog {
        rootView?.findViewById<TextView>(R.id.tv_message)?.text = message
        return this
    }

    fun show() {
        try {
            rootView?.parent?.let {
                (it as ViewGroup).removeView(rootView)
            }
            inflateView()
        } catch (e: NullPointerException) {
            inflateView()
        }

        dialog = builder?.create()
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.show()
        isShowing = true
    }

    private fun dismiss() {
        dialog?.let {
            it.dismiss()
            isShowing = false
        }
    }

    interface OnClickDialogPermissionListener {
        fun onClickOk()
    }
}
