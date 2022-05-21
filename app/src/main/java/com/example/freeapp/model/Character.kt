package com.example.freeapp.model

import kotlin.math.roundToInt

class Character(var maze: Maze) {


    private val speed: Float = 2f
    var position = maze.origin
    var coorX = position.col + 0.5f
    var coorY = position.row + 0.5f
    var direction = Direction.UP
    var nextDirection = Direction.UP

    private var hasKey: Boolean = false
    var passLevel: Boolean = false

    var gameOver: Boolean = false

    var currentMoves = 5

    fun update(deltaTime: Float) {

    }

    fun move(direction: Direction) {
        if (maze[position.translate(direction)].type == CellType.CHEST && hasKey)
            passLevel = true

        else if(maze.canMove(position, direction)){
            position.move(direction)
            toCenter()

            val newPos = position
            if (maze[newPos].type == CellType.KEY && !maze[newPos].used) {
                maze[newPos].used = true
                hasKey = true
            }

            currentMoves--
            if(currentMoves <= 0){
                gameOver = true
                System.out.println("death, moves = "+currentMoves)
            }
            System.out.println(hasKey)
        }
    }

    private fun toCenter() {
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
    }


}