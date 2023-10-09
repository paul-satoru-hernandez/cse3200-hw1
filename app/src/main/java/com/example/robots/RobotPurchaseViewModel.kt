package com.example.robots

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotPurchaseViewModel"

class RobotPurchaseViewModel : ViewModel() {
    init {
        Log.d("TAG", "RobotPurchaseViewModel Created")
    }

    private var reward_list = BooleanArray(7)

    private var is_created = false

    private var robot_energy = 0

    private var rewards = mutableListOf(
        Reward(R.string.reward_a, 1, 1, false), // Reward A
        Reward(R.string.reward_b, 2, 2, false), // Reward B
        Reward(R.string.reward_c,3, 3, false), // Reward C
        Reward(R.string.reward_d,3, 4, false), // Reward D
        Reward(R.string.reward_e,4, 5, false), // Reward E
        Reward(R.string.reward_f,4, 6, false), // Reward F
        Reward(R.string.reward_g,7, 7, false) // Reward G
    )

    fun setRewardList(list : BooleanArray) {
        reward_list = list
    }

    fun create() {
        is_created = true
    }

    fun setEnergy(energy : Int) {
        robot_energy = energy
    }
    val getRewards : MutableList<Reward>
        get() = rewards

    val getRewardList : BooleanArray
        get() = reward_list

    val created : Boolean
        get() = is_created

    val energy : Int
        get() = robot_energy
}