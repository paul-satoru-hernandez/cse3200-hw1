package com.example.robots

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val EXTRA_RECENT_PURCHASE = "com.example.robots.mostRecentPurchase";
private const val EXTRA_CURRENT_ENERGY = "com.example.robots.currentEnergy";

private lateinit var purchaseIntent : Intent;
class RobotPurchase : AppCompatActivity() {

    private lateinit var reward_button_a : Button
    private lateinit var reward_button_b : Button
    private lateinit var reward_button_c : Button
    private lateinit var robot_energy_available : TextView
    private var robot_energy = 0

    private var recentPurchase = 0;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        reward_button_a = findViewById(R.id.reward_a_button)
        reward_button_b = findViewById(R.id.reward_b_button)
        reward_button_c = findViewById(R.id.reward_c_button)

        robot_energy_available = findViewById(R.id.result)
        robot_energy = intent.getIntExtra(EXTRA_CURRENT_ENERGY, 0)
        robot_energy_available.text = robot_energy.toString()


        purchaseIntent = Intent().apply {
            putExtra(EXTRA_RECENT_PURCHASE, recentPurchase)
            putExtra(EXTRA_CURRENT_ENERGY, robot_energy)
        }

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
    companion object {
        fun newIntent(packageContext : Context,
                      recentPurchase : Int,
                      robot_energy : Int) : Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_RECENT_PURCHASE, recentPurchase)
                putExtra(EXTRA_CURRENT_ENERGY, robot_energy)
            }
        }
    }

    private fun makePurchase(costOfPurchase : Int){
        if (robot_energy >= costOfPurchase){
            recentPurchase = costOfPurchase;
            Log.d("makePurchase", recentPurchase.toString())
            var s1 = "";
            s1 = when (costOfPurchase) {
                1 -> getString(R.string.reward_a)
                2 -> getString(R.string.reward_b)
                else -> getString(R.string.reward_c)
            }
            val s2 = getString(R.string.purchased)
            val s3 = "$s1 $s2"
            robot_energy -= costOfPurchase
            robot_energy_available.text = robot_energy.toString()
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()

            purchaseIntent.putExtra(EXTRA_RECENT_PURCHASE, recentPurchase);
            purchaseIntent.putExtra(EXTRA_CURRENT_ENERGY, robot_energy);
            setResult(Activity.RESULT_OK, purchaseIntent);
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}