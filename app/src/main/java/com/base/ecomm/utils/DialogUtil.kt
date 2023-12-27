package com.base.ecomm.utils

import android.content.Context
import android.os.Handler
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog


class DialogUtil(internal var context: Context) {

    interface OnDialogButtonClickListener {
        fun onButtonClick()

        fun onButton2Click()

    }

    private lateinit var toast: QMUITipDialog

    fun showDialog(
        title: String,
        msg: String,
        btn: String = "OK",
        listener: OnDialogButtonClickListener? = null
    ) {
        QMUIDialog.MessageDialogBuilder(context)
            .setTitle(title)
            .setMessage(msg)
            .addAction(btn) { dialog, index ->
                listener?.onButtonClick()
                dialog.dismiss()
            }
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .create().show()
    }

    fun showDialog2(
        title: String,
        msg: String,
        btn: String = "OK",
        btn2: String = "Cancel",
        listener: OnDialogButtonClickListener
    ) {
        QMUIDialog.MessageDialogBuilder(context)
            .setTitle(title)
            .setMessage(msg)
            .addAction(btn2) { dialog, index ->
                listener.onButton2Click()
                dialog.dismiss()
            }
            .addAction(btn) { dialog, index ->
                listener.onButtonClick()
                dialog.dismiss()
            }
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .create().show()
    }

    fun showToastDialog(msg: String) {
        toast = QMUITipDialog.Builder(context)
            .setTipWord(msg)
            .create()
        toast.show()

        Handler().postDelayed({ toast.dismiss() }, 1500)
    }

    fun showFailDialog(msg: String) {
        toast = QMUITipDialog.Builder(context)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
            .setTipWord(msg)
            .create()
        toast.show()

        Handler().postDelayed({ toast.dismiss() }, 1500)
    }

    fun showSuccessDialog(msg: String) {
        toast = QMUITipDialog.Builder(context)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
            .setTipWord(msg)
            .create(false)
        toast.show()

        Handler().postDelayed({ toast.dismiss() }, 1500)
    }

    fun showLoadingDialog(msg: String) {
        toast = QMUITipDialog.Builder(context)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord(msg)
            .create(false)
        toast.show()
    }

    fun dismissLoadingDialog() {
        if (::toast.isInitialized) toast.dismiss()
    }

}
