package com.example.medicine_detect.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.medicine_detect.base.BaseFragment
import com.example.medicine_detect.databinding.FragmentHomeBinding
import com.example.medicine_detect.model.MedicinePlant
import com.example.medicine_detect.ui.details.DetailsActivity
import com.example.medicine_detect.ui.details.DetailsActivity.Companion.KEY_ID_ITEM
import com.example.medicine_detect.ui.main.MainViewModel

class HomeFragment : BaseFragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val listData = mutableListOf<MedicinePlant>()
    private lateinit var homeAdapter: MedicinePlantAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidget()
        onListener()
        getListData()
    }

    private fun onListener() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no-op
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no-op
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != "") {
                    getListData()
                    filterRecycler(s.toString(), listData)
                }else{
                    getListData()
                }
            }
        })
    }

    private fun filterRecycler(text: String, listFilter: MutableList<MedicinePlant>) {
        val newList = arrayListOf<MedicinePlant>()
        for (item in listFilter) {
            if (item.title.lowercase().startsWith(text.lowercase())) {
                newList.add(item)
            }
        }
        homeAdapter.setListData(newList)
    }

    private fun initWidget() {
        binding.apply {
            homeAdapter = MedicinePlantAdapter(listData) { idItem ->
                val intent = Intent(requireActivity(), DetailsActivity::class.java)
                intent.putExtra(KEY_ID_ITEM, idItem)
                startActivity(intent)
            }

            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = homeAdapter
            }
        }
    }

    private fun getListData() {
        getViewModel().getListData().observe(requireActivity()) { list ->
            listData.clear()
            listData.addAll(list)
            homeAdapter.setListData(list)
        }
    }

    private fun getViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
}
