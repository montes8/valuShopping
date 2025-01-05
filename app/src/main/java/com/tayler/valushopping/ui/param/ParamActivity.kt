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
import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.ui.AppViewModel
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
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
            if (binding.editIdMovie.uiTayLText.isNotEmpty()) {
                youTubePlayerObserver?.loadVideo(binding.editIdMovie.uiTayLText, 0f)
            }
        }

        binding.btnSaveParam.setOnClickUiTayDelay {
            viewModel.updateParam(dataParam)
        }
    }

    override fun observeViewModel() {
        viewModel.successParamLiveData.observe(this) {
            if (it.uid?.isNotEmpty() == true) {
                configParam(it)
            }
        }

        viewModel.shimmerLiveData.observe(this) {
            binding.shimmerParam.uiTayVisibilityDuo(it, binding.nsvParam)
        }
    }

    private fun configParam(param: ParamResponse) {
        dataParam = param
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
        flagEnable += if(binding.editDescriptionParam.text.toString().length > 4) 0 else 1
        binding.btnSaveParam.tayBtnEnable = flagEnable == 0
        configData()
    }

    private fun configData(){
        dataParam.idMovie = binding.editIdMovie.uiTayLText
        dataParam.title = binding.editTitleParam.uiTayLText
        dataParam.description = binding.editDescriptionParam.text.toString()
        dataParam.enableCategory = binding.rbCategoryActive.isChecked
    }

    override fun getViewModel(): BaseViewModel = viewModel
}