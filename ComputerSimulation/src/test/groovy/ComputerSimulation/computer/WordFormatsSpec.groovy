package ComputerSimulation.computer

import spock.lang.Specification

class WordFormatsSpec extends Specification {

    private final WordFormats wordFormats = new WordFormats(3, 24, 4)

    def 'Convert positive floats to binary'() {

        given: 'Positive float number and converter'
        float floatNum = 2.625

        when: 'Converter is called'
        def immediateReal = wordFormats.formImmediateReal(floatNum)

        then: 'Binary number should be created'
        assert immediateReal == ['0', '1', '0', '0', '0', '1', '0', '1']
    }

    def 'Convert negative floats to binary'() {

        given: 'Negative float number and converter'
        float floatNum = -4.75

        when: 'Converter is called'
        def immediateReal = wordFormats.formImmediateReal(floatNum)

        then: 'Binary number should be created'
        assert immediateReal == ['1', '1', '0', '1', '0', '0', '1', '1']
    }

    def 'Convert float that need to be round'() {

        given: 'A number that could not be presented in 8 bits'
        float floatNum = 1.7

        when: 'Converter is called'
        def immediateReal = wordFormats.formImmediateReal(floatNum)

        then: 'Binary number should be created'
        assert immediateReal == ['0', '0', '1', '1', '1', '0', '1', '1']
    }

    def 'Convert float that need to be round 2'() {

        given: 'A number that could not be presented in 8 bits'
        float floatNum = 1.7

        when: 'Converter is called'
        def real = wordFormats.formReal(floatNum)

        then: 'Binary number should be created'
        assert real == ['0', '0', '1', '1', '1', '0', '1', '1', '0', '0', '1', '1', '0', '0',
                        '1', '1', '0', '0', '1', '1', '0', '0', '1', '1', '0', '0', '1', '1']
    }

    def 'Convert positive binary to float'() {

        given: 'A binary number that could not be displayed as float'
        List<String> binFloat = ['0', '0', '1', '0', '0', '1', '1', '0']

        when: 'Converter is called'
        float immediateReal = wordFormats.displayReal(binFloat)

        then: 'Float number should be created'
        assert immediateReal == 0.6875
    }

    def 'Convert negative binary to float'() {

        given: 'A binary number that could not be displayed as float'
        List<String> binFloat = ['1', '1', '1', '0', '0', '1', '1', '1']

        when: 'Converter is called'
        float immediateReal = wordFormats.displayReal(binFloat)

        then: 'Float number should be created'
        assert immediateReal == -11.5
    }
}