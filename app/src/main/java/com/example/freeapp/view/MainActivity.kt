package com.example.freeapp.view

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freeapp.controller.Controller
import com.example.freeapp.model.Model
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity :  GameActivity(), IMainView {

    private lateinit var graphics: Graphics
    private val model = Model()
    private val controller = Controller(model, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        TODO("Not yet implemented")
    }

    override fun buildGameController(): IGameController {
        TODO("Not yet implemented")
    }
}