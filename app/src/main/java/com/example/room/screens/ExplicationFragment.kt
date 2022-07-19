package com.example.room.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.room.QuizApplication
import com.example.room.databinding.FragmentExplicationBinding

class ExplicationFragment : Fragment() {
    private lateinit var binding: FragmentExplicationBinding

    private val viewModel: RoomViewModel by activityViewModels {
        RoomViewModelFactory(
            (activity?.application as QuizApplication).database
                .quizDataBaseDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentExplicationBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            roomViewModel = viewModel
        }

        return binding.root
    }
}