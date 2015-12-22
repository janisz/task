package com.github.janisz.ecs

import com.google.common.collect.Lists
import java.util.*

data class Elevator(val id: Int, var currentFloor: Int, var currentDierection: Direction) {

    /**
     * Priority queue responsible for scheduling incoming floor requests
     * TODO: Change it to priority queue
     */
    val queue: MutableList<FloorPickup> = Lists.newArrayList(FloorPickup(currentFloor, currentDierection))

    data class FloorPickup(val goalFloorNumber: Int, val direction: Direction, val timestamp: Date = Date())
    data class ElevatorStatus(val id: Int, val currentFloor: Int, val direction: Direction)

    fun status(): ElevatorStatus {
        return ElevatorStatus(id, currentFloor, currentDierection)
    }

    /**
     * @return Number of steps required to get to specified [floor]
     */
    fun distance(floor: Int, direction: Direction): Int {
//        TODO: Calculate steps using queue information and direction. Easiest solution is to copy queue, add floor to
//        that copy and based on position in queue calculate how long does it take to get to specific floor.
        return Math.abs(floor - currentFloor)
    }

    /**
     * Somebody just came into elevator and want to get to [goalFloorNumber]
     */
    fun update(goalFloorNumber: Int) {
//        TODO: If Direction don't march picup request schedule it with least priority (it's not his turn)
        val direction = if (goalFloorNumber > currentFloor) {
            Direction.UP
        } else {
            Direction.DOWN
        }
        queue.add(FloorPickup(goalFloorNumber, direction))
    }

    /**
     * Somebody is waiting on [floor] and want to go specific [direction]
     */
    fun pickup(floor: Int, direction: Direction) {
//        TODO: Schdule pickup with less priority than update (serve people inside first) but use  timestamp to monitor
//        waiting time
        queue.add(FloorPickup(floor, direction))
    }

    fun step() {
        if (currentFloor != queue.first().goalFloorNumber) {
            move()
        } else if (queue.size > 1) {
            queue.removeAt(0)
            if (queue.first().goalFloorNumber > currentFloor) {
                currentDierection = Direction.UP
            } else {
                currentDierection = Direction.DOWN
            }
        }
    }

    private fun move() {
        when (currentDierection) {
            Direction.UP -> currentFloor++
            Direction.DOWN -> currentFloor--
        }
    }
}