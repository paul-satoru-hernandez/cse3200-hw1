package com.example.robots

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel() {
    init {
        Log.d(TAG, "instance of RobotViewModel created.")
    }

    private var turnCount = 0
    private var redEnergyVal = 0
    private var whiteEnergyVal = 0
    private var yellowEnergyVal = 0

    fun advanceTurn() {
        turnCount++
        if (turnCount > 3) {
            turnCount = 1
        }
    }

    fun setEnergy(energy : Int) {
         when (turnCount) {
             1 -> redEnergyVal = energy;
             2 -> whiteEnergyVal = energy;
             else -> yellowEnergyVal = energy;
         }
    }

    val redEnergy : Int
        get() = redEnergyVal

    val whiteEnergy : Int
        get() = whiteEnergyVal

    val yellowEnergy : Int
        get() = yellowEnergyVal

    val turnCounter : Int
        get() = turnCount

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "instance of RobotViewModel about to be destroyed.")
    }
}

