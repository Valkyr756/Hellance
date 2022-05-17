package com.example.freeapp.view

interface IMainView {
    fun standardSizeCalculation()
    fun drawMaze()
    fun drawCharacter()
    fun normalizeX(eventX: Int): Float
    fun normalizeY(eventY: Int): Float
}