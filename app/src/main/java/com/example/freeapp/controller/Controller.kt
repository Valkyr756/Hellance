package com.example.freeapp.controller

import com.example.freeapp.model.Model
import com.example.freeapp.view.IMainView
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(private val model: Model, private val view: IMainView): IGameController {
    private val gestureDetector = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {

        val dTime = if(deltaTime>0.5)  0.02f else deltaTime

        if (model.isGameOver)
            view.changeGameOverState(true)

        for (event in touchEvents) {
            val x = view.normalizeX(event.x)
            val y = view.normalizeY(event.y)
            if (event.type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(x, y)

            if (event.type == TouchHandler.TouchType.TOUCH_UP) {
                var touchUp = gestureDetector.onTouchUp(x, y)
                if (touchUp == GestureDetector.Gestures.SWIPE && !model.isGameOver)
                    model.move(gestureDetector.direction)
                else if (touchUp == GestureDetector.Gestures.CLICK && model.isGameOver){
                    view.changeGameOverState(false)
                    model.restartGame()
                }
                /*else if (view.restartBoxClicked(x, y))
                    System.out.println("hehehehhe")*/
            }
        }
        model.update(dTime)

        if(model.mazeChanged) {
            view.standardSizeCalculation()
            model.mazeChanged = false
        }
        view.update(dTime)
    }

    fun getMoves(): Int {
        return model.character.currentMoves
    }

    fun getPoints(): Int {
        return model.points
    }
}