package com.example.freeapp.model

import android.util.Log
import java.lang.StringBuilder

/**
 * An enumeration for the types of [cells][Cell] in a [Maze].
 */
enum class CellType {
    OBSTACLES, EMPTY, ORIGIN, WALL, CHEST, KEY, ENEMIES
}

/**
 * A data class for representing the cells of the [Maze].
 */
data class Cell(var type: CellType, var used: Boolean, val walls: Int) {
    /**
     * Check whether there is a wall in the given [direction].
     *
     * Returns true if there is a wall in the direction, false otherwise.
     * The cells in the borders do *not* have walls outsize the [Maze].
     */
    fun hasWall(direction: Direction) = walls.hasWall(direction)
}

private fun Int.setWall(direction: Direction): Int =
    this or (1 shl direction.ordinal)

private fun Int.hasWall(direction: Direction): Boolean =
    this and (1 shl direction.ordinal) != 0

/**
 * A class for representing mazes.
 *
 * @author Juan Miguel Vilar Torres (jvilar@uji.es)
 */
class Maze(diagram: Array<String>) {
    private val cells: Array<Array<Cell>>

    /**
     * The [Position] of the origin.
     */
    val origin: Position
    val keyOrigin: Position
    /**
     * The positions of the enemies.
     */
    val obstaclesOrigins: List<Position>
        get() = obstacles

    private val obstacles = ArrayList<Position>()

    val enemiesOrigins: List<Position>
        get() = enemies

    private val enemies = ArrayList<Position>()
    /**
     * The total gold
     */



    /**
     * The number of rows of the maze.
     */
    val nRows: Int = diagram.size

    /**
     * The number of columns of the maze.
     */
    val nCols: Int = diagram[0].length

    /**
     *
     * @constructor
     *
     * The parameter to the constructor is an array of [String] with
     * an string for each of the rows. The characters specified in the
     * [companion] are used for the different elements. An
     * example maze follows:
     * <pre>
     *      Maze(
     *          arrayOf(
     *          "#############################",
     *          "#...........................#",
     *          "#.##.#.###############.#.##.#",
     *          "#.##P#.####### #######.#P##.#",
     *          "#....#.......# #.......#.##.#",
     *          "####.#######.###.#######....#",
     *          "#  #.#.................#.####",
     *          "####.#.###.###D###.###.#.####",
     *          "#......# #.#HH HH#.# #......#",
     *          "#.#.##.###.#######.###.##.#.#",
     *          "#.#.##.................##.#.#",
     *          "#.#.##.#######.#######.##.#.#",
     *          "#.#........#.....#..........#",
     *          "#P########.#.###.#.########P#",
     *          "#.########.#.###.#.########.#",
     *          "#.............O.............#",
     *          "#############################",
     *          )
     *      )
     * </pre>
     * @param diagram a schematic representation of the maze.
     */
    init {
        cells = Array(nRows) { Array(nCols) { Cell(CellType.EMPTY, false, 0) } }
        var origin = Position(0, 0)
        var keyOrigin = Position(0, 0)

        for (row in 0 until nRows) {
            val previous = diagram[if (row > 0) row - 1 else row]
            val current = diagram[row]
            val next = diagram[if (row < nRows - 1) row + 1 else row]
            for (col in 0 until nCols) {
                var walls = 0
                if (row > 0 && previous[col] == WALL) walls = walls.setWall(Direction.UP)
                if (row < nRows - 1 && next[col] == WALL) walls = walls.setWall(Direction.DOWN)
                if (col > 0 && current[col - 1] == WALL) walls = walls.setWall(Direction.LEFT)
                if (col < nCols - 1 && current[col + 1] == WALL) walls =
                    walls.setWall(Direction.RIGHT)

                cells[row][col] = Cell(
                    when (current[col]) {
                        ORIGIN -> CellType.ORIGIN.also { origin = Position(row, col) }
                        OBSTACLES -> CellType.OBSTACLES.also {obstacles.add(Position(row, col))}
                        KEY -> CellType.KEY.also { keyOrigin = Position(row, col) }
                        CHEST -> CellType.CHEST
                        WALL -> CellType.WALL
                        ENEMIES -> CellType.ENEMIES.also {enemies.add(Position(row, col))}
                        else -> CellType.EMPTY
                    },
                    false,
                    walls
                )
            }
        }
        this.origin = origin
        this.keyOrigin = keyOrigin
        Log.i("MAZE", this.toString())
    }

    /**
     * The cell at a given [position].
     */
    operator fun get(position: Position): Cell = cells[position.row][position.col]


    operator fun set(position: Position, newPosition: Position) {
        val aux = cells[position.row][position.col]
        cells[position.row][position.col] = cells[newPosition.row][newPosition.col]
        cells[newPosition.row][newPosition.col] = aux
    }

    /**
     * The cell at the given [row] and [col].
     */
    operator fun get(row: Int, col: Int): Cell = cells[row][col]



    /**
     * The representation of the [Maze] as a [String].
     */
    override fun toString(): String {
        val position = Position(0, 0)
        return StringBuilder().run {
            for (row in 0 until nRows) {
                for (col in 0 until nCols) {
                    position.col = col
                    append(
                        when (cells[row][col].type) {
                            CellType.OBSTACLES -> OBSTACLES
                            CellType.EMPTY -> EMPTY
                            CellType.ORIGIN -> ORIGIN
                            CellType.WALL -> WALL
                            CellType.CHEST -> CHEST
                            CellType.KEY -> KEY
                            CellType.ENEMIES -> ENEMIES
                        }
                    )
                }
                append('\n')
            }
            toString()
        }
    }

    /**
     * Check for the existence of a wall in the given [Direction] in
     * a position determined by a row and a column.
     * @param row the row.
     * @param col the column.
     * @param direction the direction.
     * @return `true` if there is a wall.
     */
    fun hasWall(row: Int, col: Int, direction: Direction) = get(row, col).hasWall(direction)

    /**
     * Check for the existence of a wall in a given [Position] and [Direction].
     * @param position the position
     * @param direction the direction
     * @return `true` if there is a wall.
     */
    fun hasWall(position: Position, direction: Direction) =
        hasWall(position.row, position.col, direction)

    /**
     * Mark all cells as not used.
     */
    fun reset() {
        var obstacleCreator = 0
        var enemyCreator = 0
        var position: Position

        for (row in 0 until nRows){
            for (col in 0 until nCols) {
                position = Position(row, col)
                val cell = cells[row][col]
                /*if (cells[position.row][position.col].type == CellType.OBSTACLES && position != obstaclesOrigins[obstacleTest]){
                    cells[position.row][position.col].type = CellType.EMPTY
                    obstacleTest++
                }*/
                if (cell.type != CellType.WALL && cell.type != CellType.CHEST)  //Everything empty except the ones that never move
                    cell.type = CellType.EMPTY

                if (obstacleCreator < obstaclesOrigins.size && obstaclesOrigins[obstacleCreator] == position){   //Creates the obstacles in their original positions
                    cell.type = CellType.OBSTACLES
                    obstacleCreator++
                }
                else if (enemyCreator < enemiesOrigins.size && enemiesOrigins[enemyCreator] == position){   //Same thing with the enemies
                    cell.type = CellType.ENEMIES
                    enemyCreator++
                }
                else if (keyOrigin == position) //And with the key
                    cell.type = CellType.KEY
            }
        }
    }

    fun canMove(position: Position, direction: Direction): Boolean {
        val nextCell = position.translate(direction)
        val secondNext = nextCell.translate(direction)

        when(get(nextCell).type){

            CellType.OBSTACLES -> {
                if(get(secondNext).type == CellType.EMPTY)
                    set(nextCell, secondNext)
                return false
            }
            CellType.EMPTY -> return true
            CellType.ORIGIN -> return true
            CellType.WALL -> return false
            CellType.CHEST -> return false
            CellType.KEY -> return true
            CellType.ENEMIES -> {
                if(get(secondNext).type == CellType.EMPTY)
                    set(nextCell, secondNext)
                else if (get(secondNext).type != CellType.EMPTY){
                    cells[nextCell.row][nextCell.col].type = CellType.EMPTY
                }
                return false
            }
        }
    }

    companion object {
        /**
         * The [char] used for representing the origin.
         */
        const val ORIGIN = 'O'

        /**
         * The [char] used for representing the potions.
         */
        const val OBSTACLES = 'R'

        /**
         * The [char] used for representing the walls.
         */
        const val WALL = '#'


        /**
         * The [char] used for representing empty cells.
         */
        const val EMPTY = ' '

        const val CHEST = 'C'

        const val KEY = 'K'

        const val ENEMIES = 'E'
    }
}