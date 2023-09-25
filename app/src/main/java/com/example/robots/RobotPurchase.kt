package com.example.robots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class RobotPurchase : AppCompatActivity() {

    private lateinit var reward_button_a : Button
    private lateinit var reward_button_b : Button
    private lateinit var reward_button_c : Button
    private lateinit var robot_energy_available : TextView
    private var robot_energy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        reward_button_a = findViewById(R.id.reward_a_button)
        reward_button_b = findViewById(R.id.reward_b_button)
        reward_button_c = findViewById(R.id.reward_c_button)

        robot_energy_available = findViewById(R.id.result)
        robot_energy = 2 // temporary hardcoded for testing
        robot_energy_available.text = robot_energy.toString()

        reward_button_a.setOnClickListener{
            makePurchase(1)
        }
        reward_button_b.setOnClickListener{
            makePurchase(2)
        }
        reward_button_c.setOnClickListener{
            makePurchase(3)
        }
    }

    private fun makePurchase(costOfPurchase : Int){
        if (robot_energy >= costOfPurchase){
            val s1 = getString(R.string.reward_1)
            val s2 = getString(R.string.purchased)
            val s3 = "$s1 $s2"
            robot_energy -= costOfPurchase
            robot_energy_available.text = robot_energy.toString()
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}