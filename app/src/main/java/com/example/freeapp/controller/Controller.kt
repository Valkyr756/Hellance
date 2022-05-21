package com.example.freeapp.controller

import com.example.freeapp.model.Model
import com.example.freeapp.view.IMainView
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(private val model: Model, private val view: IMainView): IGameController {
    private val gestureDetector = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {

        val dTime =if(deltaTime>0.5)  0.02f else deltaTime


        for (event in touchEvents) {
            if (event.type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(view.normalizeX(event.x), view.normalizeY(event.y))

            if (event.type == TouchHandler.TouchType.TOUCH_UP) {
                var touchUp = gestureDetector.onTouchUp(view.normalizeX(event.x), view.normalizeY(event.y))
                if (touchUp == GestureDetector.Gestures.SWIPE)
                    model.move(gestureDetector.direction)

            }
        }

        model.update(dTime)

        if(model.mazeChanged) {
            view.standardSizeCalculation()
            model.mazeChanged = false
        }

    }

}