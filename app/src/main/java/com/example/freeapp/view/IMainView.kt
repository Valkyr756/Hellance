package com.example.freeapp.view

interface IMainView {
    fun update(deltaTime: Float)
    fun standardSizeCalculation()
    fun drawMaze()
    fun drawCharacter()
    fun drawMovesAndPoints()
    fun normalizeX(eventX: Int): Float
    fun normalizeY(eventY: Int): Float
}