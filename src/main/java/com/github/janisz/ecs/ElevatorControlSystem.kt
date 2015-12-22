package com.github.janisz.ecs

interface ElevatorControlSystem {
    fun status(): List<Elevator.ElevatorStatus>
    fun update(elevatorId: Int, goalFloorNumber: Int)
    fun pickup(floor: Int, direction: Direction)
    fun step()
}

enum class Direction {
    UP, DOWN
}