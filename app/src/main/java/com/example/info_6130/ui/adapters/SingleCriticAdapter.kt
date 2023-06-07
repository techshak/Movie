package com.example.info_6130.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.info_6130.dataModel.CriticDetails
import com.example.info_6130.dataModel.Result
import com.example.info_6130.databinding.SingleCriticCardBinding
import com.squareup.picasso.Picasso

class SingleCriticAdapter(private val actions: SingleCriticActions):RecyclerView.Adapter<SingleCriticAdapter.SingleCriticViewHolder> () {

    inner class SingleCriticViewHolder(private val binding: SingleCriticCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(critic:Result){
            binding.criticName.text = critic.display_name
            binding.criticStatus.text = critic.status
            binding.root.setOnClickListener {
                val image = critic.multimedia?.resource?.src
                actions.onOptionItemClicked(CriticDetails(image,critic.bio,critic.sort_name))
            }
            if(critic.multimedia !== null){

                Picasso.get()
                    .load(critic.multimedia.resource?.src)
                    .into(binding.CriticImage)
            }

        }
    }


    private val differCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.display_name == newItem.display_name
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleCriticViewHolder {
        val inflater = SingleCriticCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  SingleCriticViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SingleCriticViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}


interface SingleCriticActions {
    fun onOptionItemClicked(criticDetails:CriticDetails)
}