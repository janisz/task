package com.github.janisz.ecs

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.janisz.ecs.Direction.*
import static com.github.janisz.ecs.Direction.UP

class ElevatorSpec extends Specification {

    @Unroll
    def "distance should return steps required to pickup at #floor #direction "() {
        when:
        def elevator = new Elevator(0, startFloor, startDirection)
        then:
        distance == elevator.distance(floor, direction)

        where:
        startFloor | startDirection | floor | direction | distance
        0          | UP             | 1     | UP        | 1
        0          | UP             | 0     | UP        | 0
        5          | UP             | 0     | DOWN      | 5

    }

    def "pickup should schedule floor to stop"() {
        given:
        def elevator = new Elevator(0, 0, UP)
        when:
        elevator.pickup(5, DOWN)
        (0..4).each { elevator.step() }
        then:
        4 == elevator.currentFloor
        when:
        elevator.step()
        then:
        5 == elevator.currentFloor
        when:
        elevator.step()
        then:
        5 == elevator.currentFloor
    }

    def "update should schedule floor to stop"() {
        given:
        def elevator = new Elevator(0, 0, UP)
        when:
        elevator.pickup(5, DOWN)
        (0..5).each { elevator.step() }
        then:
        5 == elevator.currentFloor
        when:
        elevator.update(3)
        (0..2).each { elevator.step() }
        then:
        elevator.currentFloor == 3
        when:
        elevator.update(2)
        elevator.update(4)
        (0..2).each { elevator.step() }
        then:
        elevator.currentFloor == 2
        when:
        (0..1).each { elevator.step() }
        then:
        4 == elevator.currentFloor
    }
}
