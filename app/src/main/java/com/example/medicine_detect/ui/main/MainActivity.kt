package com.example.medicine_detect.ui.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.medicine_detect.R
import com.example.medicine_detect.base.BaseActivity
import com.example.medicine_detect.base.BaseFragment
import com.example.medicine_detect.comon.Constants.*
import com.example.medicine_detect.databinding.ActivityMainBinding
import com.example.medicine_detect.extentions.getStateView
import com.example.medicine_detect.ui.main.detectoffline.DetectionOfflineFragment
import com.example.medicine_detect.ui.main.home.HomeFragment
import com.example.medicine_detect.ui.main.livedetect.DetectionLiveFragment

class MainActivity : BaseActivity(){

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var mPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarLight()
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            setSupportActionBar(toolbarMain)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
            }

            mPagerAdapter = PagerAdapter(getFragment(), this@MainActivity)
            viewpager2.apply {
                adapter = mPagerAdapter
                isUserInputEnabled = false
                offscreenPageLimit = 3
            }

            viewpager2.setCurrentItem(TAB_HOME, false)

            bottomNav.apply {
                selectedItemId = R.id.itemHome
                val colorTab = ContextCompat.getColor(this@MainActivity, R.color.color_main)
                bottomNav.itemTextColor = getStateView(colorTab)
                bottomNav.itemIconTintList = getStateView(colorTab)

                setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.detectOffline -> {
                            txtTitleToolbar.text = context.getString(R.string.detection_offline)
                            viewpager2.currentItem = TAB_OFFLINE_DETECT
                            return@setOnItemSelectedListener true
                        }
                        R.id.itemHome -> {
                            txtTitleToolbar.text = context.getString(R.string.home)
                            viewpager2.currentItem = TAB_HOME
                            return@setOnItemSelectedListener true
                        }
                        R.id.detectOnline -> {
                            txtTitleToolbar.text = context.getString(R.string.live_detect)
                            viewpager2.currentItem = TAB_LIVE_DETECT
                            return@setOnItemSelectedListener true
                        }
                        else -> {
                            return@setOnItemSelectedListener false
                        }
                    }
                }
            }
        }
    }

    private fun getFragment(): List<BaseFragment> {
        val fragments = ArrayList<BaseFragment>()
        fragments.add(DetectionOfflineFragment.newInstance())
        fragments.add(HomeFragment.newInstance())
        fragments.add(DetectionLiveFragment.newInstance())
        return fragments
    }
}
