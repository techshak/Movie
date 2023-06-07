package com.example.info_6130

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.example.info_6130.databinding.ActivityMainBinding
import com.example.info_6130.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}