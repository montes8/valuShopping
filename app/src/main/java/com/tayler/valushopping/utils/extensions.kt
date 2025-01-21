package com.tayler.valushopping.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gb.vale.uitaylibrary.utils.uiTaySaveImg
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tayler.valushopping.BuildConfig
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date


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

fun Context.goUrlFacebook(idProfile:String){
    if (idProfile.isNotEmpty()){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/$idProfile"))
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.facebook.com/$idProfile")
                )
            )
        }
    }else{
        uiTayShowToast(getString(R.string.text_not_config))
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

fun getUriFromConstancyOneView(context: Context?, viewToShare: View, viewContainer: View): Uri? {
    val widthSpec = View.MeasureSpec.makeMeasureSpec(viewContainer.width, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    viewToShare.measure(widthSpec, heightSpec)
    viewToShare.layout(0, 0, viewToShare.measuredWidth, viewToShare.measuredHeight)
    val bitmap = getBitmapFromView(viewToShare)
    val file = context?.let { saveBitmap(it, bitmap) }

    return context?.let {
        file?.let { it1 ->
            FileProvider.getUriForFile(
                it,
                "${BuildConfig.APPLICATION_ID}.provider",
                it1
            )
        }
    }
}

fun getBitmapFromView(view: View): Bitmap? {
    val returnedBitmap =
        Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(returnedBitmap)
    val bgDrawable = view.background
    if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
    view.draw(canvas)
    return returnedBitmap
}

fun saveBitmap(context: Context, bmp: Bitmap?): File? {
    val outStream: OutputStream?
    val file: File? = getFileShared(context)
    try {
        outStream = FileOutputStream(file)
        bmp?.setHasAlpha(true)
        bmp?.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    return file
}

@SuppressLint("SimpleDateFormat")
private fun getFileShared(context: Context?): File? {
    val timeStamp =
        "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    return getFileSharedTwo(context!!, timeStamp)
}

private fun getFileSharedTwo(
    context: Context,
    url: String
): File? {
    // For a more secure solution, use EncryptedFile from the Security library
    // instead of File.
    var file: File? = null
    try {
        val fileName = Uri.parse(url).lastPathSegment
        file = fileName?.let { File.createTempFile(it, ".jpeg", context.cacheDir) }
    } catch (e: IOException) {
        // Error while creating file
    }
    return file
}

fun Context.sharedImageView(view: View){
    try {
        val uri = createBitmapFromView(view)
        val fileShared = uiCreatePictureFile()
        val url = uiTaySaveImg(fileShared,uri,"productshared")
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this,"${BuildConfig.APPLICATION_ID}.provider",File(url)))
        intent.setType("image/*")
        this.startActivity(Intent.createChooser(intent,"Compartir producto"))
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun createBitmapFromView(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun Context.uiCreatePictureFile(nameFile: String = "imgSave"): File {
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val newPath = File("$storageDir$nameFile")
    if (!newPath.exists()) {
        newPath.mkdirs()
    }
    return newPath
}

fun Context.openWhatsApp(phone: String, text: String) {
    if (existWhatsAppInDevice(this)){
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("$URL_WHATS_APP_CUSTOM+51$phone&text=$text")
            )
        )
    } else{
        Toast.makeText(this, getString(R.string.whatsapp_not_found), Toast.LENGTH_SHORT)
            .show()
    }
}

fun existWhatsAppInDevice(context: Context): Boolean {
    return existApplicationInDevice(context, PACKAGE_APP_WHATS_APP)
            || existApplicationInDevice(context, PACKAGE_APP_WHATS_APP_BUSINESS)
}

private fun existApplicationInDevice(context: Context, name: String): Boolean {
    val apps = context.packageManager.getInstalledPackages(0)
    for (app in apps) {
        if (app.packageName == name) {
            return true
        }
    }
    return false
}
