package com.example.freeapp.model

class Model() {

    var level = 0
    var maze: Maze = Levels.all[level]
        private set
    var character: Character = Character(maze)

    var arrayObstacles: ArrayList<Obstacles> = fillArrayObstacles()

    private fun fillArrayObstacles(): ArrayList<Obstacles> {
        val arrayAux = ArrayList<Obstacles>()
        for (i in maze.obstaclesOrigins.indices) {
            arrayAux.add(Obstacles(maze, i))
        }
        return arrayAux
    }




    fun update(deltaTime: Float) {
        character.update(deltaTime)

    }

    fun move(direction: Direction) {
        character.move(direction)
    }


}