package com.example.robots

data class Robot(
    val robotMessageResource : Int,
    var myTurn : Boolean,
    val largeRobot : Int,
    val smallRobot : Int,
    var energyCount : Int,
    var recentPurchase : String)
