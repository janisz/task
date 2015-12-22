import spock.lang.Specification

class TaskTest extends Specification {
    def "GetName"() {
        expect:
        new Task("name").getName() == "name"
    }
}
