package com.example.robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView

    var turnCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redImg = findViewById(R.id.redRobot)
        whiteImg = findViewById(R.id.whiteRobot)
        yellowImg = findViewById(R.id.yellowRobot)

        redImg.setOnClickListener{ toggleImage() }
        whiteImg.setOnClickListener{ toggleImage() }
        yellowImg.setOnClickListener{ toggleImage() }
    }

    private fun toggleImage() {
        turnCount++
        if(turnCount > 2) {
            turnCount = 0
        }
        redImg.setImageResource(R.drawable.king_of_detroit_robot_red_small)
        whiteImg.setImageResource(R.drawable.king_of_detroit_robot_white_small)
        yellowImg.setImageResource(R.drawable.king_of_detroit_robot_yellow_small)

        when (turnCount) {
            1 -> redImg.setImageResource(R.drawable.king_of_detroit_robot_red_large);
            2 -> whiteImg.setImageResource(R.drawable.king_of_detroit_robot_white_large);
            else -> {
                yellowImg.setImageResource(R.drawable.king_of_detroit_robot_yellow_large);
            }
        }

    }

}