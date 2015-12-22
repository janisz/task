package com.github.janisz.ecs

interface ElevatorControlSystem {
    fun status(): List<Elevator>
    fun update(elevatorId: Int, to: Int)
    fun pickup(floor: Int, direction: Direction)
    fun step()
}

enum class Direction {
    UP, DOWN
}