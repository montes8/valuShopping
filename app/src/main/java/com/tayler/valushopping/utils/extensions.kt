package com.tayler.valushopping.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tayler.valushopping.R
import com.tayler.valushopping.repository.ERROR_MESSAGE_EXPIRE
import com.tayler.valushopping.repository.ERROR_MESSAGE_GENERAL
import com.tayler.valushopping.repository.ERROR_MESSAGE_NETWORK
import com.tayler.valushopping.repository.ERROR_TITLE_EXPIRE
import com.tayler.valushopping.repository.ERROR_TITLE_GENERAL
import com.tayler.valushopping.repository.ERROR_TITLE_NETWORK
import com.tayler.valushopping.repository.network.exception.ApiException
import com.tayler.valushopping.repository.network.exception.CompleteErrorModel
import com.tayler.valushopping.repository.network.exception.GenericException
import com.tayler.valushopping.repository.network.exception.MyNetworkException
import com.tayler.valushopping.repository.network.exception.UnAuthorizedException
import com.tayler.valushopping.ui.BaseFragment
import okhttp3.ResponseBody
import retrofit2.Response


@SuppressLint("MissingPermission")
fun Context?.isConnected(): Boolean {
    return this?.let {
        val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getNetworkCapabilities(cm.activeNetwork)
            ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
    } ?: false
}

fun Context?.isAirplaneModeActive(): Boolean {
    return this?.let {
        return Settings.Global.getInt(it.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } ?: false
}

fun Throwable.mapperError():Triple<Int,String,String>{
    return when(this){
        is MyNetworkException -> Triple(R.drawable.ic_error_red,ERROR_TITLE_NETWORK,ERROR_MESSAGE_NETWORK)
        is UnAuthorizedException -> Triple(R.drawable.ic_info_error,ERROR_TITLE_EXPIRE,ERROR_MESSAGE_EXPIRE)
        is ApiException -> Triple(R.drawable.ic_info_error, title,messageApi)
        else -> Triple(R.drawable.ic_info_error, ERROR_TITLE_GENERAL, ERROR_MESSAGE_GENERAL)
    }
}

fun AppCompatActivity.addToBaseNavigation(
    fragment: BaseFragment, currentFragment: Fragment?,
    func: ((action: BaseFragment) -> Unit)? = null
) {
    if (currentFragment == fragment) return
    this.supportFragmentManager.let {
        it.addFragmentToNavigation(
            fragment,
            fragment::class.java.name,
            R.id.frameGeneric,
            currentFragment
        )
        func?.invoke(fragment)
    }
}

fun FragmentManager?.addFragmentToNavigation(
    fragment: Fragment,
    tag: String,
    containerId: Int,
    currentFragment: Fragment? = null
) {
    this?.let {
        if (!it.fragmentIsAdded(fragment)) {
            it.beginTransaction().let { transaction ->
                transaction.add(containerId, fragment, tag)
                currentFragment?.let { cFragment -> transaction.hide(cFragment) }
                transaction.addToBackStack(tag)
                transaction.commit()
            }
        } else showExistingFragment(fragment, currentFragment)
    }
}

fun FragmentManager?.showExistingFragment(fragment: Fragment, currentFragment: Fragment? = null) {
    this?.let {
        it.beginTransaction().let { transaction ->
            transaction.show(fragment)
            currentFragment?.let { transaction.hide(currentFragment) }
            transaction.commit()
        }
    }
}

fun FragmentManager?.fragmentIsAdded(fragment: Fragment): Boolean {
    return this?.let { return it.fragments.isNotEmpty() && it.fragments.contains(fragment) }
        ?: false
}

fun AppCompatActivity.onToBaseBack(
    currentFragment: BaseFragment?,
    finish: Boolean = false,
    func: ((action: BaseFragment?) -> Unit)? = null
) {
    if (!backStackFragmentFromNavigationStep()) {
        this.supportFragmentManager.moveBackToFirstFragment(currentFragment)?.let {
            if (it is BaseFragment) func?.invoke(it)
        } ?: if (finish) finish() else moveTaskToBack(true)
    } else {
        func?.invoke(this.supportFragmentManager.fragments.last() as BaseFragment?)
    }
}

fun AppCompatActivity.backStackFragmentFromNavigationStep(): Boolean {
    try {
        this.supportFragmentManager.let {
            return if (!validateOnlyOneItemOnNavigationStep()) {
                it.popBackStackImmediate()
                true
            } else false
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return false
}

fun FragmentManager?.moveBackToFirstFragment(currentFragment: Fragment?): Fragment? {
    this?.let {
        currentFragment?.let { cFragment ->
            if (it.fragments.size > 1 && it.fragments.first() != cFragment) {
                it.beginTransaction().let { transaction ->
                    transaction.show(it.fragments.first())
                    transaction.hide(cFragment)
                    transaction.commit()
                }
                return it.fragments.first()
            }
        }
    }
    return null
}

fun AppCompatActivity.validateOnlyOneItemOnNavigationStep(): Boolean =
    (this.supportFragmentManager.backStackEntryCount <= 1)


inline fun <reified T> getData(context: Context, fileName: String): T {
    val jsonData = Gson()
    val json = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return jsonData.fromJson(json, object : TypeToken<T>() {}.type)
}

fun setImageString(value: String, context: Context): Drawable?{
    val uri = "@drawable/$value"
    val imageResource: Int = context.resources.getIdentifier(uri, null, context.packageName)
    return ContextCompat.getDrawable(context, imageResource)

}

fun <T> Response<T>?.validateBody() : T {
    this?.body()?.let {
        return it
    } ?: throw NullPointerException()
}

fun <T> Response<T>?.validateData():Boolean{
   return this?.isSuccessful == true && this.body() != null
}

fun ResponseBody?.toCompleteErrorModel(code : Int) : Exception {
    return this?.let {
        return  if (code == 407) throw UnAuthorizedException () else Gson().fromJson(it.string(), CompleteErrorModel::class.java)?.getApiException()?:GenericException()
    } ?: GenericException()
}

fun Context.goUrlFacebook(){
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100085287459708"))
        startActivity(intent)
    } catch (e: java.lang.Exception) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.facebook.com/100085287459708")
            )
        )
    }
}

fun Context.goUrlInstagram(){
    val uri = Uri.parse("http://instagram.com/_u/tayler-eddi")
    val likeIng = Intent(Intent.ACTION_VIEW, uri)
    likeIng.setPackage("com.instagram.android")
    try {
        this.startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://instagram.com/tayler-eddi")
            )
        )
    }
}

