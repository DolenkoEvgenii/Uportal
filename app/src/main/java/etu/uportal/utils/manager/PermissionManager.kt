package etu.uportal.utils.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.FragmentEvent
import etu.uportal.ui.activity.base.BaseRxFragmentActivity
import etu.uportal.ui.fragment.BaseRxFragment
import io.reactivex.Observable
import io.reactivex.functions.Consumer

object PermissionManager {
    @SuppressLint("CheckResult")
    fun isPermissionsGranted(context: Context?, vararg permissions: String): Boolean {
        context ?: return false

        return Observable.fromIterable(permissions.toList())
                .map { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
                .all { isGranted -> isGranted }
                .blockingGet()
    }

    private fun requestPermissions(rxPermissions: RxPermissions, transformer: LifecycleTransformer<Boolean>, permissions: Array<String>, consumer: Consumer<Boolean>) {
        val res = rxPermissions.request(*permissions)
                .compose(transformer)
                .subscribe(consumer)
    }

    fun requestPermissions(activity: BaseRxFragmentActivity, permission: Array<String>, consumer: Consumer<Boolean>) {
        requestPermissions(RxPermissions(activity), RxLifecycle.bindUntilEvent(activity.lifecycle(), ActivityEvent.DESTROY), permission, consumer)
    }

    fun requestPermissions(fragment: BaseRxFragment, permission: Array<String>, consumer: Consumer<Boolean>) {
        requestPermissions(RxPermissions(fragment), RxLifecycle.bindUntilEvent(fragment.lifecycle(), FragmentEvent.DESTROY), permission, consumer)
    }
}