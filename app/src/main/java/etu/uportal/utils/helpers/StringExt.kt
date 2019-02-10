package etu.uportal.utils.helpers

import android.content.Context
import android.widget.Toast
import etu.uportal.App
import etu.uportal.utils.PhoneUtil
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Extension method to show toast for String.
 */
fun String.toast(context: Context, isShortToast: Boolean = true) = {
    Toast.makeText(context, this, if (isShortToast) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

/**
 * Extension method to get md5 string.
 */
fun String.md5() = encrypt(this, "MD5")

/**
 * Extension method to get encrypted string.
 */
fun encrypt(string: String?, type: String): String {
    if (string.isNullOrEmpty()) {
        return ""
    }
    val md5: MessageDigest
    return try {
        md5 = MessageDigest.getInstance(type)
        val bytes = md5.digest(string.toByteArray())
        bytes2Hex(bytes)
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

/**
 * Extension method to convert byteArray to String.
 */
fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}

/**
 * Extension method to check if String is Phone Number.
 */
fun String.isPhone(): Boolean {
    val phone = PhoneUtil.formatPhone(this)
    return phone.isNumeric() && phone.length == 11 && (phone.startsWith("7") || phone.startsWith("8"))
}

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$".toRegex(RegexOption.IGNORE_CASE)
    return matches(emailRegex)
}

/**
 * Extension method to check if String is Number.
 */
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}

/**
 * Extension method to check String equalsIgnoreCase
 */
fun String.equalsIgnoreCase(other: String) = this.toLowerCase().contentEquals(other.toLowerCase())

/**
 * Extension method to get Date for String with specified format.
 */
fun String.dateInFormat(format: String): Date? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    var parsedDate: Date? = null
    try {
        parsedDate = dateFormat.parse(this)
    } catch (ignored: ParseException) {
        ignored.printStackTrace()
    }
    return parsedDate
}

fun String.removeSpaces(): String {
    return replace(" ", "")
}

fun String.removeLineBreaks(): String {
    return replace("\n", "").replace("\r", "")
}

fun String?.isEmpty(): Boolean {
    return this.isNullOrEmpty()
}

@ExperimentalContracts
fun String?.isNotEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotEmpty != null)
    }

    return !this.isNullOrEmpty()
}

fun String?.ifEmptySet(placeholder: String): String {
    return if (this.isNullOrEmpty()) {
        placeholder
    } else {
        this
    }
}

fun Any?.getString(stringResId: Int): String {
    return App.component.context().getString(stringResId)
}