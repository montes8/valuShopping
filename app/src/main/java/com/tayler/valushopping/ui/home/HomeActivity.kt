package com.tayler.valushopping.ui.home

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.extra.UITayStyleTbIcon
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.gb.vale.uitaylibrary.utils.uiTayOpenPdfUrl
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch
import com.google.android.material.navigation.NavigationView
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityHomeBinding
import com.tayler.valushopping.databinding.NavHeaderHomeBinding
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.admin.AdminFragment
import com.tayler.valushopping.ui.home.dialog.DeliveryPointsBS
import com.tayler.valushopping.ui.home.init.InitFragment
import com.tayler.valushopping.ui.home.other.OtherFragment
import com.tayler.valushopping.ui.home.product.ProductFragment
import com.tayler.valushopping.ui.login.UserViewModel
import com.tayler.valushopping.ui.profile.ProfileActivity
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.JSON_ITEM_HOME
import com.tayler.valushopping.utils.JSON_ITEM_HOME_TWP
import com.tayler.valushopping.utils.LINK_TERM
import com.tayler.valushopping.utils.getData
import com.tayler.valushopping.utils.goUrlFacebook
import com.tayler.valushopping.utils.openWhatsApp
import com.tayler.valushopping.utils.setDrawableCircle
import com.tayler.valushopping.utils.setImageString
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var bindingH: NavHeaderHomeBinding
    private lateinit var binding: ActivityHomeBinding
    private lateinit var initFragment   : InitFragment
    private lateinit var productFragment : ProductFragment
    private lateinit var otherFragment   : OtherFragment
    private lateinit var adminFragment   : AdminFragment
    private var listItem : ArrayList<ItemModel> = ArrayList()
    private var drawer: DrawerLayout? = null

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this    }

    override fun setUpView() {
        drawer = binding.drawerLayout
        binding.navViewMenu.setNavigationItemSelectedListener(this)
        configInit()
        createItemNavigation()
        initFragment()
        configActionNavigation()
    }

    private fun  configInit(){
        val flagSession = viewModel.loadSession()
        binding.toolBarHome.uiTayStyleTbIcon = if(flagSession) UITayStyleTbIcon.UI_TAY_ICON_TWO else UITayStyleTbIcon.UI_TAY_ICON_START
        binding.toolBarHome.setOnClickTayMenuListener{ onClickDelete()}
    }
    private fun initFragment(){
        val hView = binding.navViewMenu.getHeaderView(0)
        bindingH = NavHeaderHomeBinding.bind(hView)
        initFragment = InitFragment.newInstance()
        productFragment = ProductFragment.newInstance()
        otherFragment = OtherFragment.newInstance()
        adminFragment = AdminFragment.newInstance()
        addToNavigation(initFragment)
    }

    private fun createItemNavigation(){
        listItem = getData(this,if(AppDataVale.paramData.enableCategory == true) JSON_ITEM_HOME_TWP else JSON_ITEM_HOME)
        listItem.forEach {item->
            binding.btnNavigation.menu.add(Menu.NONE, item.id, Menu.NONE, item.title).icon =
                setImageString(item.icon,this)
        }
    }

    private fun configActionNavigation() = with(binding){
        toolBarHome.setOnClickTayBackListener{
            drawerVisible()
        }
        binding.btnNavigation.setOnItemSelectedListener { item ->
             when (item.itemId) {
                1 -> {addToNavigation(initFragment) }
                2 -> {addToNavigation(productFragment)}
                3 ->  { addToNavigation(otherFragment)}
                 else -> {    addToNavigation(adminFragment)}
            }
            drawerGone()
            return@setOnItemSelectedListener true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navProfile -> {
                ProfileActivity.newInstance(this)
                selectedItemMenu(item,true)
            }
            R.id.navClient -> {
                selectedItemMenu(item,false)
            }

            R.id.navMap -> {
                 DeliveryPointsBS.newInstance().show(supportFragmentManager, DeliveryPointsBS::class.simpleName)
                selectedItemMenu(item,false)
            }

            R.id.navFacebook -> {
                goUrlFacebook(AppDataVale.paramData.linkFacebook?: EMPTY_VALE)
                selectedItemMenu(item, false)
            }

            R.id.navSupport -> {
                openWhatsApp(getString(R.string.text_number_support),getString(R.string.text_support))
                selectedItemMenu(item, false)
            }

            R.id.navOther ->{
                selectedItemMenu(item,false)
                uiTayOpenPdfUrl(url = LINK_TERM)

            }

        }
        drawerGone()
        return true
    }

    private fun drawerGone() {
        drawer?.closeDrawer(GravityCompat.START)
    }
    private fun drawerVisible() {
        drawer?.openDrawer(GravityCompat.START)
    }

    private fun selectedItemMenu(item : MenuItem, value : Boolean){
        item.isChecked = value
    }
    override fun observeViewModel() {
       // not implement
    }

    override fun onResume() {
        super.onResume()
        loadUpdateConfigUser()
    }

    private fun onClickDelete(){
        showUiTayDialog(model = UiTayDialogModel(title = "Deseas cerrar sesión",
            subTitle = "Estas seguro que deseas cerrar sesión")
        ){
            if (it){
                viewModel.logout()
                uiTayHandler { newInstance(this) }
            }
        }
    }

    private fun loadUpdateConfigUser(){
        viewModel.loadUser().observe(this){
            uiTayTryCatch {  bindingH.imgProfileHome.setDrawableCircle(it.img)}
            bindingH.txtNameUserMenu.text = it.names
        }
    }

    override fun getViewModel(): BaseViewModel? = null
}