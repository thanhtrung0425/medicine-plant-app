package com.example.medicine_detect.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medicine_detect.R
import com.example.medicine_detect.databinding.ItemMedicinePlantsBinding
import com.example.medicine_detect.model.MedicinePlant

class MedicinePlantAdapter(
    private val listData: MutableList<MedicinePlant>,
    private val onClickItem: (id: Int) -> Unit
) : RecyclerView.Adapter<MedicinePlantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MedicinePlantAdapter.ViewHolder {
        val binding =
            ItemMedicinePlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicinePlantAdapter.ViewHolder, position: Int) {
        val item = listData[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = listData.size

    fun setListData(list: MutableList<MedicinePlant>) {
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMedicinePlantsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: MedicinePlant) {
            binding.txtTitle.text = item.title

            Glide.with(binding.root.context).load(item.image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imgPreview)

            binding.root.setOnClickListener {
                onClickItem.invoke(item.id)
            }
        }
    }
}
