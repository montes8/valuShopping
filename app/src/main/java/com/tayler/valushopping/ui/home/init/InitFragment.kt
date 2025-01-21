package com.tayler.valushopping.ui.home.init

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentInitBinding
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.utils.EMPTY_VALE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InitFragment : BaseFragment() {

    private lateinit var binding: FragmentInitBinding

    companion object {
        fun newInstance() = InitFragment()
    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_init, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        val param = AppDataVale.paramData
        if (param.title?.isNotEmpty() == true) binding.textTitleInit.text = param.title
        if (param.description?.isNotEmpty() == true) binding.textSubTitleInit.text =
            param.description
        val youTubePlayerView: YouTubePlayerView = binding.youTubeVale
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = param.idMovie ?: EMPTY_VALE
                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.pause()
            }
        })
    }

    override fun observeLiveData() {
        //not implement
    }
}