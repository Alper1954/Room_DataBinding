package com.example.room.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.room.QuizApplication
import com.example.room.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

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
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.welcomeFragment=this

        binding.selectType.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val typeQuestion: Int = binding.selectType.selectedItemPosition
                //Log.i("alain", "spinner index = " + pos.toString())

                viewModel.initListId(typeQuestion)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Log.i("alain", "spinner is empty")
            }
        })

        return binding.root
    }

    fun callQuestionList(){
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToListQuestionFragment()
        findNavController().navigate(action)
    }

    fun nextQuestion(){
        if(viewModel.isListIdEmpty()){
            Toast.makeText(requireActivity(), "catégorie non disponible", Toast.LENGTH_SHORT)
                .show()
        }else{
            viewModel.getNextQuestion()

            val action = WelcomeFragmentDirections.actionWelcomeFragmentToGameFragment()
            findNavController().navigate(action)
        }
    }

}