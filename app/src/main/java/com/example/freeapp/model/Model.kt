package com.example.freeapp.model

class Model(private var soundPlayer: Character.CharacterSoundPlayer) {

    var level = 3
    var maze: Maze = Levels.all[level]
        private set
    var character: Character = Character(maze, soundPlayer, level)
    var bitmapDirection: Direction = Direction.RIGHT
    var isPushAnimation: Boolean = false
    var points = 0

    //var arrayObstacles: ArrayList<Obstacles> = fillArrayObstacles()

    var mazeChanged: Boolean = false
    var isGameOver: Boolean = false

    /*private fun fillArrayObstacles(): ArrayList<Obstacles> {
        val arrayAux = ArrayList<Obstacles>()
        for (i in maze.obstaclesOrigins.indices) {
            arrayAux.add(Obstacles(maze, i))
        }
        return arrayAux
    }*/




    fun update(deltaTime: Float) {
        character.update(deltaTime)

        if (character.passLevel){
            mazeChanged = true
            level += 1

            points += character.currentMoves

            if (level == 4) { //Ultimo nivel alcanzado
                isGameOver = true
                level = 0
            }

            maze = Levels.all[level]
            character = Character(maze, soundPlayer, level)


            //arrayObstacles = fillArrayObstacles()
        }
    }

    fun move(direction: Direction) {
        character.move(direction)
        bitmapDirection = direction
        isPushAnimation = character.animBool
    }

    fun restartGame() {
        points = 0
        isGameOver = false
    }
}