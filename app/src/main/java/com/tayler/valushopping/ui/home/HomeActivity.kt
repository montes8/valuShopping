package com.tayler.valushopping.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.gb.vale.uitaylibrary.utils.uiTayOpenPdfUrl
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch
import com.google.android.material.navigation.NavigationView
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityHomeBinding
import com.tayler.valushopping.databinding.NavHeaderHomeBinding
import com.tayler.valushopping.entity.UserModel
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.admin.AdminFragment
import com.tayler.valushopping.ui.home.init.InitFragment
import com.tayler.valushopping.ui.home.other.OtherFragment
import com.tayler.valushopping.ui.home.product.ProductFragment
import com.tayler.valushopping.ui.login.UserViewModel
import com.tayler.valushopping.ui.profile.ProfileActivity
import com.tayler.valushopping.utils.LINK_TERM
import com.tayler.valushopping.utils.goUrlFacebook
import com.tayler.valushopping.utils.goUrlInstagram
import com.tayler.valushopping.utils.setDrawableCircle
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
        initFragment()
        configActionNavigation()
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

    private fun configActionNavigation() = with(binding){
        toolBarHome.setOnClickTayBackListener{
            drawerVisible()
        }

        binding.btnNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    addToNavigation(initFragment)
                }
                R.id.bottom_nav_product -> {
                    addToNavigation(productFragment)
                }

                R.id.bottom_nav_other -> {
                    addToNavigation(otherFragment)
                }
                else -> {
                    addToNavigation(adminFragment)
                }
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
                selectedItemMenu(item,false)

            }

            R.id.navFacebook -> {
                goUrlFacebook()
                selectedItemMenu(item, false)
            }

            R.id.navInstagram -> {
                selectedItemMenu(item, false)
                goUrlInstagram()
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
        viewModel.loadUser().observe(this){
            configUser(it)
        }
    }

    private fun configUser(user : UserModel){
        Log.d("configUser",user.toString())
        uiTayTryCatch {  bindingH.imgProfileHome.setDrawableCircle(user.img)}
        bindingH.txtNameUserMenu.text = user.names
    }

    override fun getViewModel(): BaseViewModel? = null
}