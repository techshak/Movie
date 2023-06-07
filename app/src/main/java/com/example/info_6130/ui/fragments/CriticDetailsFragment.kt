package com.example.info_6130.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.info_6130.R
import com.example.info_6130.dataModel.CriticDetails
import com.example.info_6130.databinding.FragmentCriticDetailsBinding
import com.example.info_6130.databinding.FragmentHomeBinding
import com.example.info_6130.network.RetrofitInstance
import com.example.info_6130.repository.BaseRepository
import com.example.info_6130.ui.ReviewViewModel
import com.example.info_6130.ui.ReviewViewModelProviderFactory
import com.example.info_6130.ui.adapters.SingleCriticAdapter
import com.example.info_6130.ui.adapters.SingleMovieAdapter
import com.example.info_6130.utils.Resource
import com.squareup.picasso.Picasso
import retrofit2.Retrofit

class CriticDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCriticDetailsBinding
    private val args: CriticDetailsFragmentArgs by navArgs()
    private lateinit var  criticDetails :CriticDetails
    private lateinit var viewModel: ReviewViewModel
    private lateinit var retrofit: Retrofit
    private lateinit var singleMovieAdapter: SingleMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCriticDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        criticDetails = args.critic

        binding.criticDetailsName.text = criticDetails.display_name
        if (criticDetails.bio.isNotEmpty()){binding.criticBio.text = Html.fromHtml(criticDetails.bio, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()}
        Picasso.get()
            .load(criticDetails.src)
            .fit()
            .into(binding.criticDetailsImage)

        retrofit = RetrofitInstance.instance
        val baseRepository = BaseRepository(retrofit)
        val reviewViewModelProviderFactory = ReviewViewModelProviderFactory(baseRepository)
        viewModel = ViewModelProvider(this,reviewViewModelProviderFactory)[ReviewViewModel::class.java]
        viewModel.getCriticReviews(criticDetails.display_name)

        viewModel.allCriticMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { movies ->
                        binding.getMoviesProgressBar.visibility = View.INVISIBLE
                        // submit the fetched critics to the differ in the
                        setupRecyclerView()
                        singleMovieAdapter.differ.submitList(movies.results)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.getMoviesProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(),"Fetch error:$message",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    //show the user the progress bar so they know the call is being made
                    binding.getMoviesProgressBar.visibility = View.VISIBLE
                }
            }
        }


    }

    private fun setupRecyclerView(){
        singleMovieAdapter = SingleMovieAdapter()
        binding.reviewedMovies.apply {
            adapter = singleMovieAdapter
        }
    }

}