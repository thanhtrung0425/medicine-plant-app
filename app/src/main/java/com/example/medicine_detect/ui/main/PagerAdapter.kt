package com.example.medicine_detect.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.medicine_detect.base.BaseFragment

class PagerAdapter(private var fragments: List<BaseFragment>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): BaseFragment {
        return fragments[position]
    }
}
