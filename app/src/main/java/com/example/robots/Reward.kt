package com.example.robots

data class Reward(
    val rewardName : Int,
    val rewardCost : Int,
    var index : Int,
    var purchased : Boolean)
