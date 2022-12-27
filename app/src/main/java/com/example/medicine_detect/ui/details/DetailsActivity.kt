package com.example.medicine_detect.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.medicine_detect.R
import com.example.medicine_detect.base.BaseActivity
import com.example.medicine_detect.databinding.ActivityDetailsBinding
import com.example.medicine_detect.extentions.observeOnUiThread
import com.example.medicine_detect.utils.apiutils.APIClient

class DetailsActivity : BaseActivity() {

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private var idItem = -1

    companion object {
        const val KEY_ID_ITEM = "id_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarLight()
        setContentView(binding.root)

        inits()
        initWidget()
    }

    private fun inits() {
        val intentResult = intent
        if (intentResult != null) {
            idItem = intentResult.getIntExtra(KEY_ID_ITEM, -1)
            showProgressBar(true)
            loadDataByIDItem(idItem)
        }
    }

    private fun loadDataByIDItem(idItem: Int) {
        APIClient().getMedicinePlantByID(idItem)
            .observeOnUiThread()
            .subscribe { item ->
                runOnUiThread {
                    binding.apply {
                        Glide.with(this@DetailsActivity)
                            .load(item.image_url)
                            .into(binding.imagePreview)

                        txtName.text = item.title
                        txtSubName.text = item.sub_title
                        txtScienceName.text = item.science_name
                        txtPlantFamily.text = item.plant_family
                        txtPlantFunction.text = item.plant_function
                        txtDosageUsage.text = item.dosage_usage
                        showProgressBar(false)
                    }
                }
            }
    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow) {
            binding.progressBar.visibility = View.VISIBLE
            binding.nestedScrollView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.nestedScrollView.visibility = View.VISIBLE
        }
    }

    private fun initWidget() {
        binding.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                title = getString(R.string.details_medicine)
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}