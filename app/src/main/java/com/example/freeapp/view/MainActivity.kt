package com.example.freeapp.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import com.example.freeapp.controller.Controller
import com.example.freeapp.model.CellType
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

        standardSizeCalculation()
    }

    override fun standardSizeCalculation() {
        standardSize = min((this.width / mazeCols), (this.height / mazeRows)).toFloat()
        xOffset = (this.width - mazeCols * standardSize) / 2
        yOffset = (this.height - mazeRows * standardSize) / 2

        graphics = Graphics(width, height)
    }

    private fun rowToY(row: Int): Float = yOffset + row * standardSize

    private fun colToX(col: Int): Float = xOffset + col * standardSize

    private fun mazeXToScreenX(coordX: Float): Float = xOffset + coordX * standardSize

    private fun mazeYToScreenY(coordY: Float): Float = yOffset + coordY * standardSize


    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        drawMaze()
        drawCharacter()

        return graphics.frameBuffer
    }

    override fun drawMaze() {
        for (row in 0 until mazeRows) {
            for (col in 0 until mazeCols) {
                val cell = maze[row, col]
                when (cell.type) {

                    CellType.WALL -> graphics.drawRect(colToX(col), rowToY(row), standardSize, standardSize, Color.BLUE)

                    CellType.OBSTACLES -> graphics.drawRect(colToX(col), rowToY(row), standardSize / 1.25f, standardSize / 1.25f, Color.RED)

                    CellType.KEY ->
                        if (!maze[row, col].used)
                            graphics.drawCircle(colToX(col) + standardSize / 2, rowToY(row) + standardSize / 2, standardSize / 4, Color.YELLOW)

                    CellType.CHEST -> graphics.drawRect(colToX(col), rowToY(row), standardSize / 1.25f, standardSize / 1.25f, Color.YELLOW)
                }
            }
        }
    }

    override fun drawCharacter() {
        graphics.drawCircle(
            mazeXToScreenX(model.character.coorX),
            mazeYToScreenY(model.character.coorY),
            standardSize / 2.5f,
            Color.GREEN
        )
    }

   /* override fun drawObstacles() {
        val arrayColors = arrayOf(Color.CYAN, Color.RED, Color.MAGENTA, Color.WHITE)
        var i: Int = 0
        for (obstacle in model.arrayObstacles) {
            graphics.drawRect(
                mazeXToScreenX(obstacle.coorX) - standardSize / 2,
                mazeYToScreenY(obstacle.coorY) - standardSize / 2,
                standardSize / 1.25f,
                standardSize / 1.25f,
                arrayColors[i]
            )
            i++
        }
    }*/

    override fun normalizeX(eventX: Int): Float {
        return eventX / width.toFloat()
    }

    override fun normalizeY(eventY: Int): Float {
        return eventY / height.toFloat()
    }

}