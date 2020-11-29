package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

/*
  Fragment where the game is played
 */
class GameFragment : Fragment() {

    lateinit var viewModel:GameModel


 private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        viewModel=ViewModelProvider(this).get(GameModel::class.java)
        binding.gameModel=viewModel
        binding.lifecycleOwner = this

        viewModel.hasFinished.observe(viewLifecycleOwner, Observer {hasFinished ->
           if (hasFinished){
            gameFinished()}

        })


        return binding.root

    }

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)


    }



}
