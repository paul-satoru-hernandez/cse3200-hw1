package com.example.robots

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.robots.databinding.ActivityRobotPurchaseBinding

private const val EXTRA_CURRENT_ENERGY = "com.example.robots.currentEnergy"
private const val EXTRA_CURRENT_TURN = "com.example.robots.currentTurn"

private const val EXTRA_BOOLEAN_LIST = "com.example.robots.reward_list"

private lateinit var binding : ActivityRobotPurchaseBinding
private lateinit var purchaseIntent : Intent


class RobotPurchase : AppCompatActivity() {
    private var robot_energy = 0
    private var turn_count = 0
    private lateinit var reward_list : BooleanArray
    private lateinit var randomButtons : List<Reward>

    private lateinit var buttonLayouts : List<LinearLayout>
    private lateinit var buttons : List<Button>
    private lateinit var buttonTexts : List<TextView>
    private lateinit var rewards : MutableList<Reward>

    private val robotPurchaseViewModel : RobotPurchaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        rewards = robotPurchaseViewModel.getRewards
        binding = ActivityRobotPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!robotPurchaseViewModel.created) {
            robotPurchaseViewModel.create()
            robotPurchaseViewModel.setEnergy(intent.getIntExtra(EXTRA_CURRENT_ENERGY, 0))
            robotPurchaseViewModel.setRewardList(intent.getBooleanArrayExtra(EXTRA_BOOLEAN_LIST)!!)
        }
        reward_list = robotPurchaseViewModel.getRewardList
        robot_energy = robotPurchaseViewModel.energy

        for (reward in reward_list) {
            Log.d("Reload", reward.toString())
        }

        binding.result.text = robot_energy.toString()

        turn_count = intent.getIntExtra(EXTRA_CURRENT_TURN, 0)
        purchaseIntent = Intent().apply {
            putExtra(EXTRA_CURRENT_ENERGY, robot_energy)
            putExtra(EXTRA_BOOLEAN_LIST, reward_list)
        }
        setResult(Activity.RESULT_OK, purchaseIntent)

        buttonLayouts = listOf(
            binding.reward1Layout,
            binding.reward2Layout,
            binding.reward3Layout
        )

        buttons = listOf(
            binding.rewardAButton,
            binding.rewardBButton,
            binding.rewardCButton
        )

        buttonTexts = listOf(
            binding.rewardAText,
            binding.rewardBText,
            binding.rewardCText
        )

        when (turn_count) {
            1 -> binding.robotImg.setImageResource(R.drawable.king_of_detroit_robot_red_large)
            2 -> binding.robotImg.setImageResource(R.drawable.king_of_detroit_robot_white_large)
            else -> binding.robotImg.setImageResource(R.drawable.king_of_detroit_robot_yellow_large)
        }

        val availableRewards = mutableListOf<Reward>()
        for (reward in rewards) {
            if (reward_list[reward.index - 1]) {
                availableRewards.add(reward)
            }
        }

        rewards.clear()
        rewards.addAll(availableRewards)

        randomButtons = randomizeRewards(rewards)
        updateButtonView(randomButtons)

        binding.rewardAButton.setOnClickListener{
            makePurchase(randomButtons[0])
        }
        binding.rewardBButton.setOnClickListener{
            makePurchase(randomButtons[1])
        }
        binding.rewardCButton.setOnClickListener{
            makePurchase(randomButtons[2])
        }
    }
    companion object {
        fun newIntent(packageContext : Context,
                      robot_energy : Int,
                      reward_list : BooleanArray) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_CURRENT_ENERGY, robot_energy)
                putExtra(EXTRA_BOOLEAN_LIST, reward_list)
            }
        }
    }

    private fun randomizeRewards(rewards : List<Reward>) : List<Reward> {
        if (rewards.size < 3) {
            return rewards.toList()
        }
        return rewards.shuffled().take(3).sortedBy { it.index }
    }

    private fun updateButtonView(rewards: List<Reward>) {
        for (layout in buttonLayouts) {
            layout.visibility = View.INVISIBLE
        }

        for (i in rewards.indices) {
            buttonLayouts[i].visibility = View.VISIBLE
            buttons[i].text = getString(rewards[i].rewardName)
            buttonTexts[i].text = rewards[i].rewardCost.toString()
        }
    }

    private fun makePurchase(reward : Reward){
        if (robot_energy >= reward.rewardCost){
            val s1 = getString(reward.rewardName)
            val s2 = getString(R.string.purchased)
            val s3 = "$s1 $s2"
            robot_energy -= reward.rewardCost
            binding.result.text = robot_energy.toString()
            reward_list[reward.index - 1] = false
            rewards.remove(reward)
            randomButtons = randomizeRewards(rewards)
            updateButtonView(randomButtons)
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()

            purchaseIntent.putExtra(EXTRA_BOOLEAN_LIST, reward_list)
            purchaseIntent.putExtra(EXTRA_CURRENT_ENERGY, robot_energy)

            robotPurchaseViewModel.setEnergy(robot_energy)
            robotPurchaseViewModel.setRewardList((reward_list))

            setResult(Activity.RESULT_OK, purchaseIntent)
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}