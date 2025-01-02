package com.tayler.valushopping.ui.home

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityHomeBinding
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.admin.AdminFragment
import com.tayler.valushopping.ui.home.category.CategoryFragment
import com.tayler.valushopping.ui.home.init.InitFragment
import com.tayler.valushopping.ui.home.product.ProductFragment

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var initFragment   : InitFragment
    private lateinit var productFragment : ProductFragment
    private lateinit var categoryFragment   : CategoryFragment
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
        initFragment = InitFragment.newInstance()
        productFragment = ProductFragment.newInstance()
        categoryFragment = CategoryFragment.newInstance()
        adminFragment = AdminFragment.newInstance()
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

                R.id.bottom_nav_category -> {
                    addToNavigation(categoryFragment)
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
                selectedItemMenu(item,true)
            }

            R.id.navClient -> {
                selectedItemMenu(item,false)
            }

            R.id.navMap -> {
                selectedItemMenu(item,false)

            }

            R.id.navFacebook -> {
                selectedItemMenu(item, false)
            }

            R.id.navOther ->{
                selectedItemMenu(item,false)
            }

            R.id.navLogout -> {
                selectedItemMenu(item,false)
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
    }

    override fun getViewModel(): BaseViewModel? = null
}