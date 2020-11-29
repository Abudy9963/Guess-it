package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameModel :ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        var COUNTDOWN_TIME = 10000L
    }

    private val timer :CountDownTimer
    // The current word
     private val _word = MutableLiveData<String>()
    val word:LiveData<String>
    get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score :LiveData<Int>
    get() = _score

    private  val _hasFinished=MutableLiveData<Boolean>()
    val hasFinished:LiveData<Boolean>
    get() = _hasFinished

    private val _currentTime=MutableLiveData<Long>()
    val currentTime:LiveData<Long>
            get() = _currentTime

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {

        _currentTime.value=COUNTDOWN_TIME
        resetList()
        nextWord()
        _score.value=0
       _hasFinished.value=false

        timer =object :CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value=(millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value= DONE
                _hasFinished.value=true

            }

        }.start()
 //       DateUtils.formatElapsedTime(currentTime.value ?: 0)

    }
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

     fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }


     fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()

        } else {
            _word.value = wordList.removeAt(0)
        }

    }
     fun onSkip() {
         _score.value = (score.value)?.minus(1)
         nextWord()
    }

     fun onCorrect() {
         _score.value = (score.value)?.plus(1)
         nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }






}