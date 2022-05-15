package com.example.freeapp.controller

import com.example.freeapp.model.Model
import com.example.freeapp.view.IMainView
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(private val model: Model, private val view: IMainView): IGameController {

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        TODO("Not yet implemented")
    }

}