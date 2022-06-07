package com.example.knowledge.navigationComponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.knowledge.R
import com.example.knowledge.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textView2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goToFirstFragment)
        }

        return view
    }
}