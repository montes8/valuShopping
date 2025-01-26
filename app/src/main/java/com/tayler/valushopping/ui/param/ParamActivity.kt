package com.tayler.valushopping.ui.param

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayVisibilityDuo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityParamBinding
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.ui.AppViewModel
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.HomeActivity
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.successDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParamActivity : BaseActivity() {

    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivityParamBinding
    private var youTubePlayerObserver: YouTubePlayer? = null
    private var dataParam = ParamResponse()

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ParamActivity::class.java))
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_param)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        viewModel.loadParam()
        configChangeEdit()
        configAction()
    }
    private fun configAction() {
        binding.btnTestMovie.setOnClickUiTayDelay {
            configMovieId()
        }

        binding.btnSaveParam.setOnClickUiTayDelay {
            loadService()
        }

        binding.tbParam.setOnClickTayBackListener{ onToBack()}
    }

    private fun loadService(){
        mapperData()
        viewModel.updateParam(dataParam)
    }

    private fun configMovieId(){
        if (binding.editIdMovie.uiTayLText.isNotEmpty()) {
            youTubePlayerObserver?.loadVideo(binding.editIdMovie.uiTayLText, 0f)
        }
    }

    override fun observeViewModel() {
        viewModel.successParamLiveData.observe(this) {
            if (it.uid?.isNotEmpty() == true) {
                configParam(it)
            }
        }

        viewModel.successUpdateParamLiveData.observe(this) {data ->
            this.successDialog{
                AppDataVale.paramData = data
                HomeActivity.newInstance(this)
            }
        }

        viewModel.shimmerLiveData.observe(this) {
            binding.shimmerParam.uiTayVisibilityDuo(it, binding.nsvParam)
        }
    }

    private fun configParam(param: ParamResponse) {
        dataParam = param
        binding.editIdMovie.uiTayLText= dataParam.idMovie?: EMPTY_VALE
        binding.editTitleParam.uiTayLText = dataParam.title?: EMPTY_VALE
        binding.editPhoneParam.uiTayLText = dataParam.phone?: EMPTY_VALE
        binding.editDescriptionParam.setText(dataParam.description?: EMPTY_VALE)
        dataParam.enableCategory?.let {
            binding.rbCategoryActive.isChecked = it
            binding.rbCategoryInactive.isChecked = !it
        }

        configMovieId()
        enableBtn()
    }

    private fun configChangeEdit(){
        binding.editIdMovie.setOnChangeTayEditLayoutListener {
            binding.btnTestMovie.tayBtnEnable = it.length > 4
            enableBtn()
        }

        binding.editTitleParam.setOnChangeTayEditLayoutListener { enableBtn()}

        binding.editDescriptionParam.addTextChangedListener{ enableBtn()}

        binding.editIdMovie.setOnChangeTayEditLayoutListener {
            binding.btnTestMovie.tayBtnEnable = it.length > 4
            enableBtn()
        }

        binding.youTubeValeTest.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayerObserver = youTubePlayer
            }
        })
    }



    private fun enableBtn(){
            var flagEnable = 0
            flagEnable  += if(binding.editIdMovie.uiTayLText.length > 4) 0 else 1
            flagEnable += if(binding.editTitleParam.uiTayLText.length > 4) 0 else 1
            flagEnable += if(binding.editDescriptionParam.text.toString().length > 20) 0 else 1
            binding.btnSaveParam.tayBtnEnable = flagEnable == 0
    }

    private fun mapperData(){
        dataParam.idMovie = binding.editIdMovie.uiTayLText
        dataParam.title = binding.editTitleParam.uiTayLText
        dataParam.description = binding.editDescriptionParam.text.toString()
        dataParam.enableCategory = binding.rbCategoryActive.isChecked
    }

    override fun getViewModel(): BaseViewModel = viewModel
}