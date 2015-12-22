package com.github.janisz.ecs

import com.google.common.collect.Lists

data class Elevator(val id: Int, var currentFloor: Int, var currentDierection: Direction) {

    val queue: MutableList<FloorPickup> = Lists.newArrayList(FloorPickup(currentFloor, currentDierection))

    /**
     * @return Number of steps required to get to specified [floor]
     */
    fun distance(floor: Int, direction: Direction): Int {
        return Math.abs(floor - currentFloor)
    }

    fun update(goalFloorNumber: Int) {
        val direction = if (goalFloorNumber > currentFloor) {
            Direction.UP
        } else {
            Direction.DOWN
        }
        queue.add(FloorPickup(goalFloorNumber, direction))
    }

    fun pickup(floor: Int, direction: Direction) {
        queue.add(FloorPickup(floor, direction))
    }

    fun step() {
        if (currentFloor != queue.first().goalFloorNumber) {
            when (currentDierection) {
                Direction.UP -> currentFloor++
                Direction.DOWN -> currentFloor--
            }
        } else {
            if (queue.size > 1) {
                queue.removeAt(0)
                if (queue.first().goalFloorNumber > currentFloor) {
                    currentDierection = Direction.UP
                } else {
                    currentDierection = Direction.DOWN
                }
            }
        }
    }
}

data class FloorPickup(val goalFloorNumber: Int, val direction: Direction)