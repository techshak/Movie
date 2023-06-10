package com.example.info_6130.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.info_6130.dataModel.CriticDetails
import com.example.info_6130.dataModel.reviews.Result
import com.example.info_6130.databinding.MovieReviewCardBinding
import com.squareup.picasso.Picasso

class SingleMovieAdapter(private val actions: SingleMovieActions) : RecyclerView.Adapter<SingleMovieAdapter.SingleMovieViewHolder>() {
    inner class SingleMovieViewHolder(private val binding: MovieReviewCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result) {
                binding.root.setOnClickListener {
                    actions.onMovieItemClicked(result.link.url)
                }
                if(result.mpaa_rating.isNotEmpty()){ binding.mpaaRating.visibility = View.VISIBLE}
                binding.mpaaRating.text = result.mpaa_rating
                binding.moviePickValue.text = result.critics_pick.toString()
                binding.movieName.text = result.display_title
                Picasso.get()
                    .load(result.multimedia.src)
                    .fit()
                    .into(binding.movieImage)

        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.display_title == newItem.display_title
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleMovieViewHolder {
        val inflater = MovieReviewCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  SingleMovieViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SingleMovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

interface SingleMovieActions {
    fun onMovieItemClicked(link:String)
}