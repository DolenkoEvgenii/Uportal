package etu.uportal.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.appbar.AppBarLayout
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import io.reactivex.functions.Consumer
import org.greenrobot.eventbus.EventBus
import etu.uportal.BuildConfig
import etu.uportal.R
import etu.uportal.model.event.ToolbarUpdatedEvent
import etu.uportal.ui.activity.base.BaseMvpFragmentActivity
import etu.uportal.ui.activity.base.SimpleMvpRootActivity
import etu.uportal.utils.Glide4Engine
import etu.uportal.utils.helpers.click
import etu.uportal.utils.helpers.find
import etu.uportal.utils.manager.PermissionManager
import etu.uportal.utils.provider.LoadingDialogProvider
import java.io.File

abstract class BaseMvpFragment : BaseRxFragment() {
    private var sweetAlertDialog: SweetAlertDialog? = null
    private var toolbar: Toolbar? = null
    private var appBar: AppBarLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoadingDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    override fun onResume() {
        super.onResume()

        if (activity is BaseMvpFragmentActivity) {
            appBar = (activity as BaseMvpFragmentActivity).provideAppBar()
            if (appBar == null) {
                return
            }

            appBar?.run {
                removeAllViewsInLayout()
                toolbar = setupToolbar(this)
                if (toolbar != null) {
                    EventBus.getDefault().postSticky(ToolbarUpdatedEvent())
                }
            }
        } else {
            throw RuntimeException("wrong base activity")
        }

        hideKeyboard(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImages = Matisse.obtainPathResult(data).map { File(it) }
            onImagesPicked(selectedImages)
        }
    }

    protected open fun onImagesPicked(files: List<File>) {

    }

    protected fun showImagePicker() {
        val permissions = arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)

        if (PermissionManager.isPermissionsGranted(context, *permissions)) {
            val cacheFolder = File(context?.cacheDir?.absolutePath, "photo")
            cacheFolder.mkdirs()

            Matisse.from(this)
                    .choose(MimeType.ofImage())
                    .countable(false)
                    .maxSelectable(10)
                    .thumbnailScale(0.8f)
                    .capture(true)
                    .captureStrategy(CaptureStrategy(false, BuildConfig.APPLICATION_ID))
                    .imageEngine(Glide4Engine())
                    .forResult(PHOTO_REQUEST_CODE)
        } else {
            PermissionManager.requestPermissions(this, permissions, Consumer { result ->
                if (result) {
                    showImagePicker()
                }
            })
        }
    }

    abstract fun setupToolbar(appBar: AppBarLayout): Toolbar?

    protected fun inflateToolbar(layoutId: Int, appBar: AppBarLayout): Toolbar {
        return (layoutInflater.inflate(layoutId, appBar, false) as Toolbar).apply {
            findViewById<View>(R.id.btBack)?.let { backArrow ->
                backArrow.click(this@BaseMvpFragment) { activity?.onBackPressed() }
            }
        }
    }

    @JvmOverloads
    fun provideSimpleToolbar(title: String, appBar: AppBarLayout, backArrowDefaultVisible: Boolean = false): Toolbar {
        return inflateToolbar(R.layout.toolbar_simple, appBar).apply {
            activity?.let { activity ->
                val backBtn = find<View>(R.id.btBack)
                when {
                    backArrowDefaultVisible -> backBtn.visibility = View.VISIBLE
                    activity.supportFragmentManager.backStackEntryCount > 0 -> backBtn.visibility = View.VISIBLE
                    activity is SimpleMvpRootActivity -> backBtn.visibility = View.VISIBLE
                    else -> backBtn.visibility = View.GONE
                }
                backBtn.click(this@BaseMvpFragment) { activity.onBackPressed() }
            }
            findViewById<TextView>(R.id.tvTitle).text = title
        }
    }

    private fun initLoadingDialog() {
        sweetAlertDialog = LoadingDialogProvider.getProgressDialog(context)
    }

    open fun showLoadingDialog() {
        if (sweetAlertDialog == null) {
            initLoadingDialog()
        }

        sweetAlertDialog?.let { dialog ->
            if (!dialog.isShowing && isAdded) {
                dialog.show()
            }
        }
    }

    open fun closeLoadingDialog() {
        sweetAlertDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismissWithAnimation()
                Handler().postDelayed({
                    try {
                        dialog.dismiss()
                        sweetAlertDialog = null
                    } catch (exc: Exception) {
                        exc.printStackTrace()
                    }
                }, 100)
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(view: View) {
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideKeyboard(view)
                return@setOnTouchListener false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun hideKeyboard(v: View?) {
        Handler().postDelayed({
            try {
                var view = v
                if (view != null) {
                    if (view.findFocus() != null) {
                        view = view.findFocus()
                    }
                }

                if (view == null) {
                    view = activity?.currentFocus
                }

                if (view == null) {
                    view = getView()
                }

                if (view != null) {
                    view.clearFocus()
                    val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                } else {
                    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                }
            } catch (ignored: Exception) {

            }
        }, 130)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(null)
    }

    override fun onStop() {
        super.onStop()
        closeLoadingDialog()
        appBar = null
        toolbar = null
    }

    companion object {
        const val PHOTO_REQUEST_CODE = 6824
    }
}
