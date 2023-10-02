package com.example.robots

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView
    private lateinit var messageBox : TextView
    private lateinit var newActivityButton : Button
    private lateinit var robotImages : MutableList<ImageView>

    private val robots = listOf(
        Robot(R.string.red_turn, false,
            R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small),
        Robot(R.string.white_turn, false,
            R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small),
        Robot(R.string.yellow_turn, false,
            R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small)
    )

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

        toggleImage()

        redImg.setOnClickListener{ robotViewModel.advanceTurn(); toggleImage(); }
        whiteImg.setOnClickListener{ robotViewModel.advanceTurn(); toggleImage();  }
        yellowImg.setOnClickListener{ robotViewModel.advanceTurn(); toggleImage();  }

        newActivityButton.setOnClickListener{
            Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RobotPurchase :: class.java)
            startActivity(intent)
        }
    }
    private fun toggleImage() {
        // Test
        setRobotTurn()
        setRobotImages()
        updateMessageBox()
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
}