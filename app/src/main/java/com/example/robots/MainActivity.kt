package com.example.robots

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels

private const val TAG = "MainActivity"

private const val EXTRA_CURRENT_ENERGY = "com.example.robots.currentEnergy"
private const val EXTRA_CURRENT_TURN = "com.example.robots.currentTurn"
private const val EXTRA_BOOLEAN_LIST = "com.example.robots.reward_list"

class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView
    private lateinit var messageBox : TextView
    private lateinit var newActivityButton : Button
    private lateinit var reward_list : BooleanArray
    private lateinit var robotImages : MutableList<ImageView>
    private lateinit var robots : List<Robot>

    private val robotViewModel : RobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redImg = findViewById(R.id.redRobot)
        whiteImg = findViewById(R.id.whiteRobot)
        yellowImg = findViewById(R.id.yellowRobot)
        messageBox = findViewById(R.id.messageBox)
        newActivityButton = findViewById(R.id.newActivityButton)
        robotImages = mutableListOf(redImg, whiteImg, yellowImg)
        robots = robotViewModel.getRobots

        toggleImage()

        reward_list = BooleanArray(7) { true }

        redImg.setOnClickListener{ robotViewModel.advanceTurn(); toggleImage(); }
        whiteImg.setOnClickListener{ robotViewModel.advanceTurn(); toggleImage();  }
        yellowImg.setOnClickListener{ robotViewModel.advanceTurn(); toggleImage();  }

        newActivityButton.setOnClickListener{
            Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RobotPurchase::class.java)
            intent.putExtra(EXTRA_CURRENT_ENERGY, robots[robotViewModel.turnCounter - 1].energyCount)
            intent.putExtra(EXTRA_CURRENT_TURN, robotViewModel.turnCounter)
            intent.putExtra(EXTRA_BOOLEAN_LIST, reward_list)
            if (robotViewModel.turnCounter != 0) {
                purchaseLauncher.launch(intent)
            }
        }
    }

    private val purchaseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            robots[robotViewModel.turnCounter - 1].energyCount = result.data?.getIntExtra(EXTRA_CURRENT_ENERGY, 0) ?: 0
            val temp = result.data?.getBooleanArrayExtra(EXTRA_BOOLEAN_LIST)!!
            for (i in temp.indices) {
                if (temp[i] != reward_list[i]) {
                    robots[robotViewModel.turnCounter - 1].purchases += when(i) {
                        0 -> " A";
                        1 -> " B";
                        2 -> " C";
                        3 -> " D";
                        4 -> " E";
                        5 -> " F";
                        else -> " G";
                    }
                }
            }
            reward_list = temp;
        }
    }
    private fun toggleImage() {
        setRobotTurn()
        setRobotImages()
        updateMessageBox()
        createPurchaseToast()
    }

    private fun updateMessageBox() {
        val turnCount = robotViewModel.turnCounter
        if (turnCount != 0) {
            messageBox.setText(robots[turnCount-1].robotMessageResource)
        }
    }
    private fun setRobotTurn() {
        val turnCount = robotViewModel.turnCounter
        if (turnCount != 0) {
            for (robot in robots) {
                robot.myTurn = false
            }
            robots[turnCount - 1].myTurn = true
            robots[turnCount - 1].energyCount++;
        }
    }

    private fun setRobotImages() {
        val turnCount = robotViewModel.turnCounter
        if (turnCount != 0) {
            for (indy in 0..2) {
                if(robots[indy].myTurn) {
                    robotImages[indy].setImageResource(robots[indy].largeRobot)
                } else {
                    robotImages[indy].setImageResource(robots[indy].smallRobot)
                }
            }
        }
    }

    private fun createPurchaseToast() {
        val turnCount = robotViewModel.turnCounter
        if (turnCount != 0) {
            if (robots[turnCount - 1].purchases != "") {
                Toast.makeText(this, "Purchased${robots[turnCount-1].purchases}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}