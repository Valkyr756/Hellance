package com.example.freeapp.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import com.example.freeapp.R
import com.example.freeapp.controller.Controller
import com.example.freeapp.model.CellType
import com.example.freeapp.model.Character
import com.example.freeapp.model.Direction
import com.example.freeapp.model.Model
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import kotlin.math.min

class MainActivity :  GameActivity(), IMainView, Character.CharacterSoundPlayer {

    companion object {
        private const val BACKGROUND_COLOR = Color.DKGRAY
    }

    private var xOffset = 0f
    private var yOffset = 0f
    private var standardSize = 0f
    private var width = 0
    private var height = 0

    private lateinit var graphics: Graphics
    private lateinit var soundPool: SoundPool
    private var walkingId = 0
    private var keySoundId = 0
    private var levelCompletedSoundId = 0
    private var characterDeathId = 0
    private var enemyPushedSoundId = 0
    private var pushRockSoundId = 0
    private var destroyEnemySoundId = 0
    private var animation: AnimatedBitmap? = null

    private val model = Model(this)
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
        prepareSoundPool(this)
    }

    private fun prepareSoundPool(context: Context) {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(attributes)
            .build()

        walkingId = soundPool.load(context, R.raw.walking, 0)
        keySoundId = soundPool.load(context, R.raw.key_sound, 0)
        levelCompletedSoundId = soundPool.load(context, R.raw.level_completed, 0)
        characterDeathId = soundPool.load(context, R.raw.character_death, 0)
        enemyPushedSoundId = soundPool.load(context, R.raw.enemy_pushed, 0)
        destroyEnemySoundId = soundPool.load(context, R.raw.destroy_enemy, 0)
        pushRockSoundId = soundPool.load(context, R.raw.push_rock, 0)
    }

    override fun buildGameController() = controller

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height

        standardSizeCalculation()
        Assets.createAssets(this, (standardSize * 1.25f).toInt())
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
        drawMovesAndPoints()
        return graphics.frameBuffer
    }

    override fun drawMaze() {
        for (row in 0 until mazeRows) {
            for (col in 0 until mazeCols) {
                val cell = maze[row, col]
                when (cell.type) {

                    CellType.WALL -> graphics.drawBitmap(Assets.wall, colToX(col), rowToY(row))
                        //graphics.drawRect(colToX(col), rowToY(row), standardSize, standardSize, Color.BLUE)

                    CellType.OBSTACLES -> graphics.drawBitmap(Assets.obstacle, colToX(col), rowToY(row))
                        //graphics.drawRect(colToX(col), rowToY(row), standardSize / 1.25f, standardSize / 1.25f, Color.RED)

                    CellType.ENEMIES -> graphics.drawBitmap(Assets.breakableObstacle, colToX(col), rowToY(row))
                        //graphics.drawRect(colToX(col), rowToY(row), standardSize / 1.25f, standardSize / 1.25f, Color.CYAN)

                    CellType.KEY -> graphics.drawBitmap(Assets.key, colToX(col), rowToY(row))
                        //graphics.drawCircle(colToX(col) + standardSize / 2, rowToY(row) + standardSize / 2, standardSize / 4, Color.YELLOW)

                    CellType.CHEST -> graphics.drawBitmap(Assets.chest, colToX(col), rowToY(row))
                        //graphics.drawRect(colToX(col), rowToY(row), standardSize / 1.25f, standardSize / 1.25f, Color.YELLOW)

                    CellType.TRAPS -> graphics.drawBitmap(Assets.trap, colToX(col), rowToY(row))
                }
            }
        }
    }

    override fun drawCharacter() {
        /*graphics.drawCircle(
            mazeXToScreenX(model.character.coorX),
            mazeYToScreenY(model.character.coorY),
            standardSize / 2.5f,
            Color.GREEN
        )*/
        val bitmap = when (model.bitmapDirection) {
            Direction.RIGHT -> Assets.characterRight
            Direction.LEFT -> Assets.characterLeft
            Direction.UP -> Assets.characterUp
            else -> Assets.characterDown
        }
        graphics.drawBitmap(bitmap, mazeXToScreenX(model.character.coorX) / 1.1f, mazeYToScreenY(model.character.coorY) / 1.25f)
    }

    override fun drawMovesAndPoints() {
        val remainingMoves = controller.getMoves()
        val totalPoints = controller.getPoints()

        graphics.setTextSize(30)
        graphics.setTextColor(Color.WHITE)
        graphics.setTextAlign(Paint.Align.LEFT)
        graphics.drawText((width/32).toFloat(),(height/8).toFloat(), "Moves: $remainingMoves")
        graphics.drawText((width/1.3).toFloat(),(height/8).toFloat(), "Points: $totalPoints")
    }

    override fun update(deltaTime: Float) {
        animation?.update(deltaTime)

        if (model.isPushAnimation){
            animation = Assets.characterPushRightAnimated
            model.isPushAnimation = false
        }
        else
            animation = null
    }

    override fun normalizeX(eventX: Int): Float {
        return eventX / width.toFloat()
    }

    override fun normalizeY(eventY: Int): Float {
        return eventY / height.toFloat()
    }

    override fun playWalk() {
        soundPool.play(walkingId, 0.8f, 0.8f, 0, 0, 1f)    }

    override fun playKey() {
        soundPool.play(keySoundId, 0.8f, 0.8f, 0, 0, 1f)
    }

    override fun playDeath() {
        soundPool.play(characterDeathId, 0.8f, 0.8f, 0, 0, 1f)
    }

    override fun playLevelCompleted() {
        soundPool.play(levelCompletedSoundId, 0.8f, 0.8f, 0, 0, 1f)
    }

    override fun playPushEnemy() {
        soundPool.play(enemyPushedSoundId, 0.8f, 0.8f, 0, 0, 1f)
    }

    override fun playDestroyEnemy() {
        soundPool.play(destroyEnemySoundId, 0.8f, 0.8f, 0, 0, 1f)
    }

    override fun playPushRock() {
        soundPool.play(pushRockSoundId, 0.8f, 0.8f, 0, 0, 1f)
    }
}