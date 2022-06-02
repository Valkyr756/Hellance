package com.example.freeapp.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.example.freeapp.R
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val SPRITE_CHARACTER_SIDE = 64
    private const val STANDARD_SPRITE_SIZE = 32
    private const val CHARACTER_FRAMES = 10
    private const val PUSH_DURATION = 0.5f
    private var characterSprites: Bitmap? = null
    private var character: SpriteSheet? = null
    var characterLeft: Bitmap? = null
    var characterRight: Bitmap? = null
    var characterUp: Bitmap? = null
    var characterDown: Bitmap? = null
    var characterPushLeftAnimated: AnimatedBitmap? = null
    var characterPushRightAnimated: AnimatedBitmap? = null
    var characterPushUpAnimated: AnimatedBitmap? = null
    var characterPushDownAnimated: AnimatedBitmap? = null

    var key: Bitmap? = null
    var trap: Bitmap? = null
    var wall: Bitmap? = null
    var obstacle: Bitmap? = null
    private var statues: Bitmap? = null
    private var statuesSpriteSheet: SpriteSheet? = null
    var breakableObstacle: Bitmap? = null
    private var chests: Bitmap? = null
    private var chestsSpriteSheet: SpriteSheet? = null
    var chest: Bitmap? = null

    fun createAssets(context: Context, characterSide: Int) {
        val resource = context.resources
        characterSprites?.recycle()
        characterSprites = BitmapFactory.decodeResource(resource, R.drawable.character)
        character = SpriteSheet(characterSprites, SPRITE_CHARACTER_SIDE, SPRITE_CHARACTER_SIDE).apply { //Las cuatro posiciones del personaje (las 4 direcciones)
            characterUp?.recycle()
            characterUp = getScaledSprite(8, 0, characterSide, characterSide)
            characterLeft?.recycle()
            characterLeft = getScaledSprite(9, 0, characterSide, characterSide)
            characterDown?.recycle()
            characterDown = getScaledSprite(10, 0, characterSide, characterSide)
            characterRight?.recycle()
            characterRight = getScaledSprite(11, 0, characterSide, characterSide)
        }
        //20: animacion de muerte
        characterPushUpAnimated?.recycle()
        characterPushUpAnimated = createAnimation(characterSide, 4)
        characterPushLeftAnimated?.recycle()
        characterPushLeftAnimated = createAnimation(characterSide, 5)
        characterPushDownAnimated?.recycle()
        characterPushDownAnimated = createAnimation(characterSide, 6)
        characterPushRightAnimated?.recycle()
        characterPushRightAnimated = createAnimation(characterSide, 7)
        //Porque no funciona

        key?.recycle()
        key = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resource, R.drawable.key), characterSide, characterSide, true)

        trap?.recycle()
        trap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resource, R.drawable.trap_magical), characterSide, characterSide, true)

        wall?.recycle()
        wall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resource, R.drawable.church_0), characterSide, characterSide, true)

        obstacle?.recycle()
        obstacle = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resource, R.drawable.tortuga), characterSide, characterSide, true)


        statues?.recycle()
        statues = BitmapFactory.decodeResource(resource, R.drawable.buddhas)
        statuesSpriteSheet = SpriteSheet(statues, STANDARD_SPRITE_SIZE, STANDARD_SPRITE_SIZE).apply {
            breakableObstacle?.recycle()
            breakableObstacle = getScaledSprite(0, 0, characterSide, characterSide)
        }

        chests?.recycle()
        chests = BitmapFactory.decodeResource(resource, R.drawable.chest)
        chestsSpriteSheet = SpriteSheet(chests, STANDARD_SPRITE_SIZE, STANDARD_SPRITE_SIZE).apply {
            chest?.recycle()
            chest = getScaledSprite(0, 0, characterSide, characterSide)
        }
    }

    private fun createAnimation(characterSide: Int, row: Int): AnimatedBitmap {
        val frames = character!!.getScaledRow(row, CHARACTER_FRAMES, characterSide, characterSide)
        return AnimatedBitmap(PUSH_DURATION, false, *frames)
    }
}
//Supongo que falta ahora el implementar la animacion en la activity