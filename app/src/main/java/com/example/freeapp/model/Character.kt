package com.example.freeapp.model

import kotlin.math.roundToInt

class Character(var maze: Maze) {


    private val speed: Float = 2f
    var position = maze.origin
    var coorX = position.col + 0.5f
    var coorY = position.row + 0.5f
    var direction = Direction.UP
    var nextDirection = Direction.UP

    var hasKey: Boolean = false

    var gameOver: Boolean = false

    var currentMoves = 5

    fun update(deltaTime: Float) {

    }

    fun move(direction: Direction) {
        //COMPROBAR SI ERA LA CELDA KEY PASAR HASKEY A TRUE Y LA CELDA KEY PASA A USED
        if(maze.canMove(position,direction)){
            position.move(direction)
            toCenter()

            currentMoves--

            if(currentMoves<=0){
                gameOver = true
                System.out.println("death, moves = "+currentMoves)
            }

        }

    }

    private fun toCenter() {
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
    }


}