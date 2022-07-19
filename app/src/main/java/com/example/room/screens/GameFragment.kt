package com.example.room.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.room.QuizApplication
import com.example.room.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: RoomViewModel by activityViewModels {
        RoomViewModelFactory(
            (activity?.application as QuizApplication).database
                .quizDataBaseDao
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            roomViewModel = viewModel
            gameFragment = this@GameFragment
        }

        return binding.root
    }

    fun valid() {
        if (!viewModel.valid()) Toast.makeText(
            requireActivity(),
            "no answer selected",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun callExplication() {
        val action = GameFragmentDirections.actionGameFragmentToExplicationFragment()
        findNavController().navigate(action)
    }

    fun colorAnswer(numAnswer: Int, state: Int): Int =
        when {
            state == 0 -> {
                Color.WHITE
            }
            state == 1 -> {
                if (numAnswer == viewModel.answer.value) {
                    0xff77b5fe.toInt()
                } else {
                    Color.WHITE
                }
            }
            else -> {
                if(numAnswer == viewModel.goodAnswer.value) {
                    Color.GREEN
                }else if((numAnswer == viewModel.goodAnswer.value)&&(numAnswer == viewModel.answer.value)){
                    Color.GREEN
                }else if((numAnswer != viewModel.goodAnswer.value)&&(numAnswer == viewModel.answer.value)){
                    Color.RED
                }else{
                    Color.WHITE
                }
            }
    }
}











