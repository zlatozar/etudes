package ComputerSimulation.computer

import spock.lang.Specification

class OpsKindSpec extends Specification {

    private static final int NUM_OF_OPS_CODES = 114

    def 'Check groups consistency'() {

        given: 'All ops formats'

        when: 'All ops codes in formats are count'
        def opsCodesRR = OpsKind.getRR()
        def opsCodesRS = OpsKind.getRS()
        def opsCodesCH = OpsKind.getCH()
        def opsCodesIM = OpsKind.getIM()

        then: 'They number should be equal to the number of ops codes'
        assert opsCodesRR.size() + opsCodesRS.size() + opsCodesCH.size() + opsCodesIM.size() == NUM_OF_OPS_CODES
    }

    def 'Infer ops code kind'() {

        given: 'An ops code'
        OpsCode opsCode = OpsCode.A

        when: 'Kind of given code is needed'

        then: 'Should be possible to infer it'
        assert OpsKind.infer(opsCode) == OpsKind.RS
    }


}