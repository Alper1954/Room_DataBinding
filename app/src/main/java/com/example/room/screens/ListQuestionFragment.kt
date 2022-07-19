package com.example.room.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.QuizApplication
import com.example.room.databinding.FragmentListQuestionBinding
import kotlinx.coroutines.launch

class ListQuestionFragment : Fragment() {

    private lateinit var binding: FragmentListQuestionBinding

    private val viewModel: RoomViewModel by activityViewModels {
        RoomViewModelFactory(
            (activity?.application as QuizApplication).database
                .quizDataBaseDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListQuestionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuestionListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allQuestions().collect(){
                adapter.submitList(it)
            }
        }
    }
}


