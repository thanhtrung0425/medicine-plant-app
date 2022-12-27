package com.example.medicine_detect.ui.main.livedetect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.medicine_detect.base.BaseFragment
import com.example.medicine_detect.databinding.FragmentDetectLiveBinding

class DetectionLiveFragment : BaseFragment() {

    private val binding by lazy { FragmentDetectLiveBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = DetectionLiveFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
