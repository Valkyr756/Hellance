package com.example.freeapp.model

class Model(private var soundPlayer: Character.CharacterSoundPlayer) {

    var level = 0
    val ogMaze: Maze = Levels.all[level]
    var maze: Maze = Levels.all[level]
        private set
    var character: Character = Character(maze, soundPlayer, this)

    //var arrayObstacles: ArrayList<Obstacles> = fillArrayObstacles()

    var mazeChanged: Boolean = false

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
            if (level == 1)
                level = 0
            else
             level += 1


            maze = Levels.all[level]
            character = Character(maze, soundPlayer, this)
            //arrayObstacles = fillArrayObstacles()
        }
    }

    fun restartLevel() {
        character = Character(ogMaze, soundPlayer, this)
    }

    fun move(direction: Direction) {
        character.move(direction)
    }
}