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
        val nextPos = position.translate(direction)     //Para chekear que la posicion a la que vas a moverse tiene algo
        if (maze[nextPos].type == CellType.CHEST && hasKey) {
            passLevel = true
            //sonido abrir cofre
        }
        else if (maze[nextPos].type == CellType.OBSTACLES) {
            //sonido roca
            System.out.println("roca")
        }
        else if (maze[nextPos].type == CellType.ENEMIES) {
            if (maze[nextPos.translate(direction)].type != CellType.EMPTY){ //Si despues del esqueleto hay una pared donde se vaya a romper
                //sonido esqueleto rompiendose
                System.out.println("adios señor esqueleto")
            }
            else{
                //sonido esqueleto
                System.out.println("skeletor")
            }
        }

        if(maze.canMove(position, direction)){
            position.move(direction)
            toCenter()

            val newPos = position
            if (maze[newPos].type == CellType.KEY) {
                maze[newPos].type = CellType.EMPTY
                hasKey = true
                //sonido llave
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