package com.example.freeapp.model

import java.util.*

/**
 * A class to represent a position in a [Maze]. A position is
 * defined by a row and a column.
 *
 * @author Juan Miguel Vilar Torres (jvilar@uji.es)
 */
class Position
/**
 * The constructor.
 * @param row the row.
 * @param col the column.
 */(
    var row: Int,
    var col: Int
) {
    /**
     * A copy constructor.
     * @param position the other position.
     */
    constructor(position: Position) : this(position.row, position.col)

    /**
     * Copy the other [Position] into this one.
     * @param other the other.
     */
    fun set(other: Position) {
        row = other.row
        col = other.col
    }

    /**
     * Simultaneously set the row and the column.
     * @param row the row.
     * @param col the column.
     */
    fun set(row: Int, col: Int) {
        this.row = row
        this.col = col
    }

    /*
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val position = o as Position
        return row == position.row &&
                col == position.col
    }*/

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        return row == other.row && col == other.col
    }

    /**
     * Check whether the position is equal to the given row and column.
     * @param row the row.
     * @param col the column.
     * @return `true` if the position is equal to `(row, col)`.
     */
    fun equals(row: Int, col: Int) =
        this.row == row && this.col == col

    override fun hashCode() = Objects.hash(row, col)

    /**
     * Move in the given [Direction].
     * @param direction
     */
    fun move(direction: Direction) {
        row += direction.row
        col += direction.col
    }

    fun translate(direction: Direction): Position =
        Position(row + direction.row, col + direction.col)

    override fun toString() = "($row, $col)"
    fun scale(sRow: Int, sCol: Int): Position = Position(row * sRow, col * sCol)
}