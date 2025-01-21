package com.tayler.valushopping.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.manager.camera.UiTayCameraManager
import com.gb.vale.uitaylibrary.utils.converterCircle
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.gb.vale.uitaylibrary.utils.uiTayValidateEmail
import com.gb.vale.uitaylibrary.utils.uiTayValidatePhoneFormat
import com.gb.vale.uitaylibrary.utils.uiTayVisibility
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProfileBinding
import com.tayler.valushopping.entity.UserModel
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.login.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class ProfileActivity : BaseActivity(),UiTayCameraManager.CameraControllerListener {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: UserViewModel by viewModels()
    private var managerCamera : UiTayCameraManager? = null
    private var flagClick = true
    private var typeBanner = true
    private var userModel = UserModel()

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }


    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configAppBar()
        managerCamera = UiTayCameraManager(this,"user",this)
        configChangeEdit()
    }

    override fun observeViewModel() {
        viewModel.loadUser().observe(this){
            configUser(it)
            Log.d("observeViewModel",it.toString())
            enableViewEditDefault(userModel.dataSave)
        }
        viewModel.successUserLiveData.observe(this){
            configUser(it)
            enableViewEdit(false)
        }
    }

    private fun configUser(user :UserModel){
        userModel = user
        binding.user = userModel
    }

    private fun configAppBar(){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        setSupportActionBar(binding.toolbar)
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.imgProfile.uiTayVisibility(abs(verticalOffset) - appBarLayout.totalScrollRange != 0)
            if (flagClick) binding.imgEditProfile.uiTayVisibility(abs(verticalOffset) - appBarLayout.totalScrollRange != 0)
        }
        configAction()
    }
    private fun configAction(){
        binding.imgEditProfile.setOnClickUiTayDelay { enableViewEdit(true) }
        binding.imgBannerProfile.setOnClickUiTayDelay {onClickImage(true)}
        binding.imgProfile.setOnClickUiTayDelay {onClickImage(false)}
        binding.btnSaveProfile.setOnClickTayBtnListener{onClickProfile()}
        binding.toolbar.setNavigationOnClickListener { finish()}
    }

    private fun onClickProfile(){
        mapperProfile()
        viewModel.saveUser(userModel)
        uiTayShowToast(getString(R.string.text_success_profile))
    }

    private fun configChangeEdit(){
        binding.editNameProfile.setOnChangeTayEditLayoutListener{ enableBtn() }
        binding.editLastNameProfile.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editEmailProfile.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editPhoneProfile.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editAddressProfile.setOnChangeTayEditLayoutListener{ enableBtn()}
    }

    private fun mapperProfile(){
        userModel.dataSave = true
        userModel.names = binding.editNameProfile.uiTayLText
        userModel.lastName = binding.editLastNameProfile.uiTayLText
        userModel.document = binding.editDocumentProfile.uiTayLText
        userModel.email = binding.editEmailProfile.uiTayLText
        userModel.phone = binding.editPhoneProfile.uiTayLText
        userModel.addressUser = binding.editAddressProfile.uiTayLText
    }

    private fun onClickImage(value : Boolean){
        typeBanner = value
        managerCamera?.doCamera(if(typeBanner)"userBannerImg" else "userImg")
    }

    private fun enableViewEditDefault(value : Boolean){
        configFocusable(!value)
        binding.btnSaveProfile.uiTayVisibility(!value)
        binding.imgEditProfile.uiTayVisibility(value)
    }

    private fun enableViewEdit(value : Boolean){
        configFocusable(value)
        binding.btnSaveProfile.uiTayVisibility(value)
        binding.imgEditProfile.uiTayVisibility(!value)
        flagClick = !flagClick
    }

    private fun configFocusable(value : Boolean){
        binding.editNameProfile.uiTayLFocusable = value
        binding.editLastNameProfile.uiTayLFocusable = value
        binding.editEmailProfile.uiTayLFocusable = value
        binding.editPhoneProfile.uiTayLFocusable = value
        binding.editAddressProfile.uiTayLFocusable = value
    }

    override fun onCameraPermissionDenied() {
        uiTayShowToast(getString(R.string.error_camera))
    }

    private fun enableBtn(){
        var flagEnable = 0
        flagEnable  += if(binding.editNameProfile.uiTayLText.isNotEmpty()) 0 else 1
        flagEnable += if(binding.editLastNameProfile.uiTayLText.isNotEmpty()) 0 else 1
        flagEnable += if(binding.editDocumentProfile.uiTayLText.length == 8) 0 else 1
        flagEnable  += if(binding.editEmailProfile.uiTayLText.uiTayValidateEmail()) 0 else 1
        flagEnable  += if(binding.editPhoneProfile.uiTayLText.uiTayValidatePhoneFormat()) 0 else 1
        flagEnable  += if(binding.editAddressProfile.uiTayLText.isNotEmpty()) 0 else 1
        binding.btnSaveProfile.tayBtnEnable = flagEnable == 0
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        if (typeBanner){
            userModel.imgBanner =path
            binding.imgImageBanner.setImageBitmap(img)
        }else{
            userModel.img = path
            binding.imgProfile.setImageBitmap(img.converterCircle())
        }
        viewModel.saveUser(userModel)
    }
}