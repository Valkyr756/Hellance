package com.example.freeapp.model

class Obstacles(var maze: Maze, var nObstacles: Int) {
    enum class ObstacleType{
        ENEMY, ROCK
    }

    private var position: Position = maze.obstaclesOrigins[nObstacles]

    var coorX = position.col + 0.5f
    var coorY = position.row + 0.5f
    var direction = Direction.UP
/*
    fun update(deltaTime: Float) {

        coorX += maze
        coorY +=

    }*/



}