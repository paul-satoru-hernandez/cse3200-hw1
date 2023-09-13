package com.example.robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var yellowImg : ImageView
    private lateinit var rotateCounter : ImageButton
    private lateinit var rotateClock : ImageButton

    private var turnCount = -10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redImg = findViewById(R.id.redRobot)
        whiteImg = findViewById(R.id.whiteRobot)
        yellowImg = findViewById(R.id.yellowRobot)

        rotateClock = findViewById(R.id.rotateClock)
        rotateCounter = findViewById(R.id.rotateCounter)

        // Has the click listener change turnCount instead of the function
        rotateClock.setOnClickListener {
            turnCount++;
            toggleImage(true)
        }
        rotateCounter.setOnClickListener{
            turnCount--;
            toggleImage(false)
        }
    }

    private fun toggleImage(vararg rotation: Boolean) {
        Log.d("Test", "value = $turnCount")

        if (turnCount == -9 || turnCount == -11) {
            turnCount = 1;
        }
        if (turnCount > 2) {
            turnCount = 0
        } else if (turnCount < 0) {
            turnCount = 2;
        }
        redImg.setImageResource(R.drawable.king_of_detroit_robot_red_small)
        whiteImg.setImageResource(R.drawable.king_of_detroit_robot_white_small)
        yellowImg.setImageResource(R.drawable.king_of_detroit_robot_yellow_small)

        when (turnCount) {
            1 -> redImg.setImageResource(R.drawable.king_of_detroit_robot_red_large);
            2 -> yellowImg.setImageResource(R.drawable.king_of_detroit_robot_yellow_large);
            0 -> whiteImg.setImageResource(R.drawable.king_of_detroit_robot_white_large);
        }
    }

}