package com.example.freeapp.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.freeapp.R
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private var characterSprites: Bitmap? = null
    private var character: SpriteSheet? = null

    fun createAssets(context: Context, characterSide: Int) {
        val resource = context.resources
        characterSprites?.recycle()
        characterSprites = BitmapFactory.decodeResource(resource, R.drawable.character)
    }
}