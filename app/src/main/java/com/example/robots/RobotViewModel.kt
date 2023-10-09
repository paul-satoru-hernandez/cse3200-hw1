package com.example.robots

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel() {
    init {
        Log.d(TAG, "instance of RobotViewModel created.")
    }

    private val robots = listOf(
        Robot(R.string.red_turn, false,
            R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small,
            0, ""),
        Robot(R.string.white_turn, false,
            R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small,
            0, ""),
        Robot(R.string.yellow_turn, false,
            R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small,
            0, "")
    )

    private var turnCount = 0

    fun advanceTurn() {
        turnCount++
        if (turnCount > 3) {
            turnCount = 1
        }
    }

    val turnCounter : Int
        get() = turnCount

    val getRobots : List<Robot>
        get() = robots

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "instance of RobotViewModel about to be destroyed.")
    }
}

