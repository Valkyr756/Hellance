package com.example.freeapp.model

/**
 * An enumeration for representing directions in a [Maze].
 *
 * @author Juan Miguel Vilar Torres (jvilar@uji.es)
 */
enum class Direction(
    /**
     * The value that must be added to a row to move in this direction.
     */
    val row: Int,
    /**
     * The value that must be added to a column to move in this direction.
     */
    val col: Int
) {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    fun turnLeft(): Direction =
        when {
            row == -1 -> LEFT // UP
            row == 1 -> RIGHT // DOWN
            col == -1 -> DOWN // LEFT
            else -> UP // RIGHT
        }

    fun turnRight(): Direction =
        when {
            row == -1 -> RIGHT // UP
            row == 1 -> LEFT // DOWN
            col == -1 -> UP // LEFT
            else -> DOWN // RIGHT
        }

    fun opposite(): Direction =
        when {
            row == -1 -> DOWN // UP
            row == 1 -> UP // DOWN
            col == -1 -> RIGHT // LEFT
            else -> LEFT // RIGHT
        }
}