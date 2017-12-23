package my.learning

/**
 * Created by tdongsi on 12/23/17.
 */
class NumberHelperSpec extends spock.lang.Specification {
    def "Test division of numbers by 2"() {
        given: "Some number"
        def n = 150

        expect: "Dividing by 2 halves the number"
        assert n / 2 == 75
    }

    def "Test finding postivie numbers"() {
        given: "A number helper"
        def helper = new JavaNumberHelper()

        when: "I find positives in a list of numbers"
        def result = helper.findPositives([1, -1, 10, 0] as int[])

        then: "Only positive numbers are included"
        assert result == [1, 10] as int[]
    }
}
