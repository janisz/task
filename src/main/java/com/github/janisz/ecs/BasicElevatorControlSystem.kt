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

    /**
     * @return all elevators
     */
    override fun status(): List<Elevator.ElevatorStatus> {
        return elevators.map { e -> e.status() }
    }

    /**
     * Somebody just came into elevator #[elevatorId] and want to go to [goalFloorNumber]
     */
    override fun update(elevatorId: Int, goalFloorNumber: Int) {
        elevators[elevatorId].update(goalFloorNumber)
    }

    /**
     * Schedule nearest elevator (in terms of steps) to pickup people from given [floor] and
     * lift them with specific [direction].
     */
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