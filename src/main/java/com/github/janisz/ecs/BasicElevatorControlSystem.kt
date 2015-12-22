package com.github.janisz.ecs

import com.github.janisz.ecs.Direction.*

class BasicElevatorControlSystem(elevatorsCount: Int): ElevatorControlSystem {

    private val elevators: Array<Elevator>

    init {
        if (elevatorsCount < 1 || elevatorsCount > 16) {
            throw IllegalArgumentException("Invalid elevators count " + elevatorsCount)
        }
        elevators = Array(elevatorsCount, {i -> Elevator(i, 0, UP) })
    }

    override fun status(): List<Elevator> {
        return elevators.toArrayList();
    }

    override fun update(elevatorId: Int, to: Int) {
        elevators[elevatorId].update(to)
    }

    override fun pickup(floor: Int, direction: Direction) {
        val nearestElevator = elevators.minBy { elevator -> elevator.distance(floor, direction) }
        nearestElevator?.pickup(floor, direction)
    }

    override fun step() {
        for (elevator in elevators) {
            elevator.step()
        }
    }

}