package ComputerSimulation.computer

import spock.lang.Specification

/**
 * http://sandbox.mc.edu/~bennet/cs110/flt/dtof.html
 * http://sandbox.mc.edu/~bennet/cs110/flt/ftod.html
 */
class ConvertSpec extends Specification {

    private final Convert convert = new Convert(3, 24, 4)

    def 'Convert positive floats to binary'() {

        given: 'Positive float number and converter'
        float floatNum = 2.625

        when: 'Converter is called'
        def immediateReal = convert.toImmediateBinaryReal(floatNum)

        then: 'Binary number should be created'
        assert immediateReal == ['0', '1', '0', '0', '0', '1', '0', '1']
    }

    def 'Convert negative floats to binary'() {

        given: 'Negative float number and converter'
        float floatNum = -4.75

        when: 'Converter is called'
        def immediateReal = convert.toImmediateBinaryReal(floatNum)

        then: 'Binary number should be created'
        assert immediateReal == ['1', '1', '0', '1', '0', '0', '1', '1']
    }

    def 'Convert float that need to be round'() {

        given: 'A number that could not be presented in 8 bits'
        float floatNum = 1.7

        when: 'Converter is called'
        def immediateReal = convert.toImmediateBinaryReal(floatNum)

        then: 'Binary number should be created'
        assert immediateReal == ['0', '0', '1', '1', '1', '0', '1', '1']
    }

    def 'Convert float that need to be round 2'() {

        given: 'A number that could not be presented in 8 bits'
        float floatNum = 1.7

        when: 'Converter is called'
        def real = convert.toBinaryReal(floatNum)

        then: 'Binary number should be created'
        assert real == ['0', '0', '1', '1', '1', '0', '1', '1', '0', '0', '1', '1', '0', '0',
                        '1', '1', '0', '0', '1', '1', '0', '0', '1', '1', '0', '0', '1', '1']
    }

    def 'Convert positive binary to float'() {

        given: 'A binary number that could not be displayed as float'
        List<String> binFloat = ['0', '0', '1', '0', '0', '1', '1', '0']

        when: 'Converter is called'
        float immediateReal = convert.toReal(binFloat)

        then: 'Float number should be created'
        assert immediateReal == 0.6875
    }

    def 'Convert negative binary to float'() {

        given: 'A binary number that could not be displayed as float'
        List<String> binFloat = ['1', '1', '1', '0', '0', '1', '1', '1']

        when: 'Converter is called'
        float immediateReal = convert.toReal(binFloat)

        then: 'Float number should be created'
        assert immediateReal == -11.5
    }

    def 'Convert positive integer to binary'() {

        given: 'A positive integer number that should be displayed as integer'
        int integer = 42

        when: 'Converter is called'
        def binInt = convert.toBinaryInteger(integer)

        then: 'Binary number with size 8 should be created'
        assert binInt.size() == 32
        assert binInt == ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                          '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '1', '0']
    }

    def 'Convert positive binary to integer'() {

        given: 'A positive binary number that should be displayed as integer'
        def binInt = ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                      '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '1', '0', '1', '0']

        when: 'Converter is called'
        int integer = convert.toInteger(binInt)

        then: 'Integer should be created'
        assert integer == 42
    }

    def 'Convert negative integer to binary'() {

        given: 'A negative integer number that should be displayed as binary'
        int integer = -42

        when: 'Converter is called'
        def binInt = convert.toBinaryInteger(integer)

        then: 'Binary number with size 8 should be created'
        assert binInt.size() == 32
        assert binInt == ['1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                          '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '1', '0']
    }

    def 'Convert negative binary to integer'() {

        given: 'A negative binary number that should be displayed as integer'
        def binInt = ['1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                      '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '1', '0', '1', '1', '0']

        when: 'Converter is called'
        int integer = convert.toInteger(binInt)

        then: 'Integer should be created'
        assert integer == -42
    }

    def 'Convert positive immediate integer to binary'() {

        given: 'A positive immediate number that should be displayed as binary'
        int integer = 42

        when: 'Converter is called'
        def binInt = convert.toImmediateBinaryInteger(integer)

        then: 'Binary number with size 20 should be created'
        assert binInt.size() == 20
        assert binInt == ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                          '0', '0', '0', '0', '1', '0', '1', '0', '1', '0']
    }

    def 'Convert positive immediate binary to integer'() {

        given: 'A positive binary number that should be displayed as integer'
        def binInt = ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                      '0', '0', '0', '0', '1', '0', '1', '0', '1', '0']

        when: 'Converter is called'
        int integer = convert.toInteger(binInt)

        then: 'Integer should be created'
        assert integer == 42
    }

    def 'Convert negative immediate integer to binary'() {

        given: 'A negative immediate number that should be displayed as integer'
        int integer = -42

        when: 'Converter is called'
        def binInt = convert.toImmediateBinaryInteger(integer)

        then: 'Binary number with size 20 should be created'
        assert binInt.size() == 20
        assert binInt == ['1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                          '1', '1', '1', '1', '0', '1', '0', '1', '1', '0']
    }

    def 'Convert negative immediate binary to integer'() {

        given: 'A negative binary number that should be displayed as integer'
        def binInt = ['1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                      '1', '1', '1', '1', '0', '1', '0', '1', '1', '0']

        when: 'Converter is called'
        int integer = convert.toInteger(binInt)

        then: 'Integer should be created'
        assert integer == -42
    }

    def 'Convert string literal to machine words'() {

        given: 'A string literal is given. In ASCII: 97, 98 and 99'
        String binInteger = "abc"

        when: 'Converter is called'
        def machineWord = Convert.toBinaryString(binInteger)

        then: 'Array of machine words(size 8) should be created'
        assert convert.toInteger(machineWord.get(0)) == 97
        assert machineWord.get(0).size() == 8

        assert convert.toInteger(machineWord.get(1)) == 98
        assert machineWord.get(1).size() == 8

        assert convert.toInteger(machineWord.get(2)) == 99
        assert machineWord.get(2).size() == 8
    }

    def 'Convert ASCII code to string'() {

        given: 'An ASCII code 97'
        int a = 97

        when: 'Converter is called'
        char asciiSym = Convert.toChar(a)

        then: 'Symbol should be displayed'
        assert asciiSym == 'a'
    }

    def 'Max positive immediate integer'() {

        given: 'Max is 2^19 - 1'
        long a = 524287

        when: 'Converter is called'
        List<String> aBinary = convert.toImmediateBinaryInteger(a)

        then: 'Conversion should be possible'
        assert aBinary == ['0', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                           '1', '1', '1', '1', '1', '1', '1', '1', '1', '1']
    }

    def 'Overflow max positive immediate integer'() {

        given: 'Max is 2^19 - 1'
        long a = 524287 + 1

        when: 'Converter is called'
        convert.toImmediateBinaryInteger(a)

        then: thrown NumberFormatException
    }

    def 'Max negative immediate integer'() {

        given: 'Max is 2^19'
        long a = -524288

        when: 'Converter is called'
        List<String> aBinary = convert.toImmediateBinaryInteger(a)

        then: 'Conversion should be possible'
        assert aBinary == ['1', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                           '0', '0', '0', '0', '0', '0', '0', '0', '0', '0']
    }

    def 'Overflow max negative immediate integer'() {

        given: 'Max is 2^19'
        long a = -524288 + (-1)

        when: 'Converter is called'
        convert.toImmediateBinaryInteger(a)

        then: thrown NumberFormatException
    }
}