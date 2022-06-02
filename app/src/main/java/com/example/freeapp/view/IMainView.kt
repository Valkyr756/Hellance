package com.example.freeapp.view

interface IMainView {
    fun update(deltaTime: Float)
    fun standardSizeCalculation()
    fun drawMaze()
    fun drawCharacter()
    fun drawMovesAndPoints()
    fun drawGameOverScreen()
    //fun drawRestartButton()
    fun normalizeX(eventX: Int): Float
    fun normalizeY(eventY: Int): Float
    fun changeGameOverState(stateGameOver: Boolean)
    //fun restartBoxClicked(x: Float, y: Float): Boolean
}