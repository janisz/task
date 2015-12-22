package com.github.janisz.ecs

import spock.lang.Specification

import static com.github.janisz.ecs.Direction.UP

class BasicElevatorControlSystemSpec extends Specification {

    def "should be able to handle a few elevators — up to 16"() {
        when:
        new BasicElevatorControlSystem(elevatorsCount)
        then:
        thrown(IllegalArgumentException)

        where:
        elevatorsCount << [-1, 0, 17]
    }

    def "status should return all elevators"() {
        given:
        def ecs = new BasicElevatorControlSystem(5)
        when:
        def elevators = ecs.status()
        then:
        elevators.size() == 5
    }

    def "should schedule elevators"() {
        given:
        def ecs = new BasicElevatorControlSystem(5)
        when:
        ecs.pickup(2, UP)
        (0..2).each {ecs.step()}
        then:
        ecs.status().find { elevator -> elevator.currentFloor == 2}
    }
}
