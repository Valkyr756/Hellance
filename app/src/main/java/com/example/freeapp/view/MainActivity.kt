package com.example.freeapp.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import com.example.freeapp.controller.Controller
import com.example.freeapp.model.Model
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import kotlin.math.min

class MainActivity :  GameActivity(), IMainView {

    companion object {
        private const val BACKGROUND_COLOR = Color.GRAY
    }

    private var xOffset = 0f
    private var yOffset = 0f
    private var standardSize = 0f
    private var width = 0
    private var height = 0

    private lateinit var graphics: Graphics
    private val model = Model()
    private val controller = Controller(model, this)

    private val maze
        get() = model.maze
    private val mazeRows
        get() = maze.nRows
    private val mazeCols
        get() = maze.nCols

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
    }

    override fun buildGameController() = controller

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    override fun standardSizeCalculation() {
        standardSize = min((this.width / mazeCols), (this.height / mazeRows)).toFloat()
        xOffset = (this.width - mazeCols * standardSize) / 2
        yOffset = (this.height - mazeRows * standardSize) / 2

        graphics = Graphics(width, height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)

        return graphics.frameBuffer
    }
}