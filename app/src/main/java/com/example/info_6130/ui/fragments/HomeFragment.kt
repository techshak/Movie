package com.example.info_6130.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.info_6130.MainActivity
import com.example.info_6130.dataModel.CriticDetails
import com.example.info_6130.databinding.FragmentHomeBinding
import com.example.info_6130.network.RetrofitInstance
import com.example.info_6130.repository.BaseRepository
import com.example.info_6130.ui.ReviewViewModel
import com.example.info_6130.ui.ReviewViewModelProviderFactory
import com.example.info_6130.ui.adapters.SingleCriticActions
import com.example.info_6130.ui.adapters.SingleCriticAdapter
import com.example.info_6130.utils.Resource
import retrofit2.Retrofit

class HomeFragment : Fragment(), SingleCriticActions {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ReviewViewModel
    private lateinit var retrofit: Retrofit
    private lateinit var singleCriticAdapter: SingleCriticAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitInstance.instance
        val baseRepository = BaseRepository(retrofit)
        val reviewViewModelProviderFactory = ReviewViewModelProviderFactory(baseRepository)
        viewModel = ViewModelProvider(this,reviewViewModelProviderFactory)[ReviewViewModel::class.java]

        lifecycleScope.launchWhenCreated { viewModel.getCritics() }


        binding.backArrow.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.allCritics.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { critics ->
                        binding.getCriticsProgressBar.visibility = View.INVISIBLE
                        // submit the fetched critics to the differ in the adapter
                        setupRecyclerView()
                        singleCriticAdapter.differ.submitList(critics.results)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        binding.getCriticsProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Fetch error:$message",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    //show the user the progress bar so they know the call is being made
                    binding.getCriticsProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView(){
        singleCriticAdapter = SingleCriticAdapter(this)
        binding.allCriticRecyclerView.apply {
            adapter = singleCriticAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onOptionItemClicked(criticDetails: CriticDetails) {
        val directions = HomeFragmentDirections.actionHomeFragmentToCriticDetailsFragment22(criticDetails)
        findNavController().navigate(directions)
    }

}