package com.example.info_6130.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.info_6130.R
import com.example.info_6130.dataModel.CriticDetails
import com.example.info_6130.databinding.FragmentCriticDetailsBinding
import com.example.info_6130.databinding.FragmentHomeBinding
import com.example.info_6130.network.RetrofitInstance
import com.example.info_6130.repository.BaseRepository
import com.example.info_6130.ui.ReviewViewModel
import com.example.info_6130.ui.ReviewViewModelProviderFactory
import com.example.info_6130.ui.adapters.SingleCriticAdapter
import retrofit2.Retrofit

class CriticDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCriticDetailsBinding
    private val args: CriticDetailsFragmentArgs by navArgs()
    private lateinit var  criticDetails :CriticDetails
    private lateinit var viewModel: ReviewViewModel
    private lateinit var retrofit: Retrofit
    private lateinit var singleCriticAdapter: SingleCriticAdapter

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
        binding.criticBio.text = Html.fromHtml(criticDetails.bio, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()


        retrofit = RetrofitInstance.instance
        val baseRepository = BaseRepository(retrofit)
        val reviewViewModelProviderFactory = ReviewViewModelProviderFactory(baseRepository)
        viewModel = ViewModelProvider(this,reviewViewModelProviderFactory)[ReviewViewModel::class.java]
        viewModel.getCriticReviews(criticDetails.display_name)



    }

}