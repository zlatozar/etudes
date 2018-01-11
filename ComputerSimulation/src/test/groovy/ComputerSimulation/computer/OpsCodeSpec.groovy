package ComputerSimulation.computer

import spock.lang.Specification
import spock.lang.Unroll

class OpsCodeSpec extends Specification {

    @Unroll
    def "Ops code #ops_enum"() {

        expect:
        assert ops_enum

        where:
         ops     |   ops_enum
        'LR'     | OpsCode.valueOf(ops)
        'L'      | OpsCode.valueOf(ops)
        'LI'     | OpsCode.valueOf(ops)
        'LC'     | OpsCode.valueOf(ops)
        'LNR'    | OpsCode.valueOf(ops)
        'LN'     | OpsCode.valueOf(ops)
        'LNI'    | OpsCode.valueOf(ops)
        'LNC'    | OpsCode.valueOf(ops)
        'STR'    | OpsCode.valueOf(ops)
        'ST'     | OpsCode.valueOf(ops)
        'STC'    | OpsCode.valueOf(ops)
        'SWAPR'  | OpsCode.valueOf(ops)
        'SWAP'   | OpsCode.valueOf(ops)
        'SWAPC'  | OpsCode.valueOf(ops)
        'ANDR'   | OpsCode.valueOf(ops)
        'AND'    | OpsCode.valueOf(ops)
        'ANDI'   | OpsCode.valueOf(ops)
        'ANDC'   | OpsCode.valueOf(ops)
        'ORR'    | OpsCode.valueOf(ops)
        'OR'     | OpsCode.valueOf(ops)
        'ORI'    | OpsCode.valueOf(ops)
        'ORC'    | OpsCode.valueOf(ops)
        'XORR'   | OpsCode.valueOf(ops)
        'XOR'    | OpsCode.valueOf(ops)
        'XORI'   | OpsCode.valueOf(ops)
        'XORC'   | OpsCode.valueOf(ops)
        'NOTR'   | OpsCode.valueOf(ops)
        'NOT'    | OpsCode.valueOf(ops)
        'NOTI'   | OpsCode.valueOf(ops)
        'NOTC'   | OpsCode.valueOf(ops)
        'BCSR'   | OpsCode.valueOf(ops)
        'BCS'    | OpsCode.valueOf(ops)
        'BCRR'   | OpsCode.valueOf(ops)
        'BCR'    | OpsCode.valueOf(ops)
        'BALR'   | OpsCode.valueOf(ops)
        'BAL'    | OpsCode.valueOf(ops)
        'SACR'   | OpsCode.valueOf(ops)
        'SAC'    | OpsCode.valueOf(ops)
        'SACC'   | OpsCode.valueOf(ops)
        'CR'     | OpsCode.valueOf(ops)
        'C'      | OpsCode.valueOf(ops)
        'CI'     | OpsCode.valueOf(ops)
        'CC'     | OpsCode.valueOf(ops)
        'CCS'    | OpsCode.valueOf(ops)
        'MCS'    | OpsCode.valueOf(ops)
        'SVC'    | OpsCode.valueOf(ops)
        'EX'     | OpsCode.valueOf(ops)
        'LA'     | OpsCode.valueOf(ops)
        'LM'     | OpsCode.valueOf(ops)
        'STM'    | OpsCode.valueOf(ops)
        'AR'     | OpsCode.valueOf(ops)
        'A'      | OpsCode.valueOf(ops)
        'AI'     | OpsCode.valueOf(ops)
        'AC'     | OpsCode.valueOf(ops)
        'SR'     | OpsCode.valueOf(ops)
        'S'      | OpsCode.valueOf(ops)
        'SI'     | OpsCode.valueOf(ops)
        'SC'     | OpsCode.valueOf(ops)
        'RSR'    | OpsCode.valueOf(ops)
        'RS'     | OpsCode.valueOf(ops)
        'RSI'    | OpsCode.valueOf(ops)
        'RSC'    | OpsCode.valueOf(ops)
        'MR'     | OpsCode.valueOf(ops)
        'M'      | OpsCode.valueOf(ops)
        'MI'     | OpsCode.valueOf(ops)
        'MC'     | OpsCode.valueOf(ops)
        'DR'     | OpsCode.valueOf(ops)
        'D'      | OpsCode.valueOf(ops)
        'DI'     | OpsCode.valueOf(ops)
        'DC'     | OpsCode.valueOf(ops)
        'RDR'    | OpsCode.valueOf(ops)
        'RD'     | OpsCode.valueOf(ops)
        'RDI'    | OpsCode.valueOf(ops)
        'RDC'    | OpsCode.valueOf(ops)
        'REMR'   | OpsCode.valueOf(ops)
        'REM'    | OpsCode.valueOf(ops)
        'REMI'   | OpsCode.valueOf(ops)
        'REMC'   | OpsCode.valueOf(ops)
        'RREMR'  | OpsCode.valueOf(ops)
        'RREM'   | OpsCode.valueOf(ops)
        'RREMI'  | OpsCode.valueOf(ops)
        'RREMC'  | OpsCode.valueOf(ops)
        'FAR'    | OpsCode.valueOf(ops)
        'FA'     | OpsCode.valueOf(ops)
        'FAI'    | OpsCode.valueOf(ops)
        'FSR'    | OpsCode.valueOf(ops)
        'FS'     | OpsCode.valueOf(ops)
        'FSI'    | OpsCode.valueOf(ops)
        'RFSR'   | OpsCode.valueOf(ops)
        'RFS'    | OpsCode.valueOf(ops)
        'RFSI'   | OpsCode.valueOf(ops)
        'FMR'    | OpsCode.valueOf(ops)
        'FM'     | OpsCode.valueOf(ops)
        'FMI'    | OpsCode.valueOf(ops)
        'FDR'    | OpsCode.valueOf(ops)
        'FD'     | OpsCode.valueOf(ops)
        'FDI'    | OpsCode.valueOf(ops)
        'RFDR'   | OpsCode.valueOf(ops)
        'RFD'    | OpsCode.valueOf(ops)
        'RFDI'   | OpsCode.valueOf(ops)
        'FLOATR' | OpsCode.valueOf(ops)
        'FLOAT'  | OpsCode.valueOf(ops)
        'FLOATI' | OpsCode.valueOf(ops)
        'FIXR'   | OpsCode.valueOf(ops)
        'FIX'    | OpsCode.valueOf(ops)
        'FIXI'   | OpsCode.valueOf(ops)
        'FLOOR'  | OpsCode.valueOf(ops)
        'CEIL'   | OpsCode.valueOf(ops)
        'MIN'    | OpsCode.valueOf(ops)
        'MAX'    | OpsCode.valueOf(ops)
        'SHIFTL' | OpsCode.valueOf(ops)
        'SHIFTC' | OpsCode.valueOf(ops)
        'SHIFTA' | OpsCode.valueOf(ops)
        'SHIFTR' | OpsCode.valueOf(ops)
    }

    @Unroll
    def "Check op-code #code"() {

        expect:
        assert valid

        where:
        code | valid
        0x00 | OpsCode.isValid(code)
        0x20 | OpsCode.isValid(code)
        0x40 | OpsCode.isValid(code)
        0x60 | OpsCode.isValid(code)
        0x01 | OpsCode.isValid(code)
        0x21 | OpsCode.isValid(code)
        0x41 | OpsCode.isValid(code)
        0x62 | OpsCode.isValid(code)
        0x02 | OpsCode.isValid(code)
        0x22 | OpsCode.isValid(code)
        0x62 | OpsCode.isValid(code)
        0x03 | OpsCode.isValid(code)
        0x23 | OpsCode.isValid(code)
        0x63 | OpsCode.isValid(code)
        0x04 | OpsCode.isValid(code)
        0x24 | OpsCode.isValid(code)
        0x44 | OpsCode.isValid(code)
        0x64 | OpsCode.isValid(code)
        0x05 | OpsCode.isValid(code)
        0x25 | OpsCode.isValid(code)
        0x45 | OpsCode.isValid(code)
        0x65 | OpsCode.isValid(code)
        0x06 | OpsCode.isValid(code)
        0x26 | OpsCode.isValid(code)
        0x46 | OpsCode.isValid(code)
        0x66 | OpsCode.isValid(code)
        0x07 | OpsCode.isValid(code)
        0x27 | OpsCode.isValid(code)
        0x47 | OpsCode.isValid(code)
        0x67 | OpsCode.isValid(code)
        0x08 | OpsCode.isValid(code)
        0x28 | OpsCode.isValid(code)
        0x09 | OpsCode.isValid(code)
        0x29 | OpsCode.isValid(code)
        0x0A | OpsCode.isValid(code)
        0x2A | OpsCode.isValid(code)
        0x0B | OpsCode.isValid(code)
        0x2B | OpsCode.isValid(code)
        0x6B | OpsCode.isValid(code)
        0x0C | OpsCode.isValid(code)
        0x2C | OpsCode.isValid(code)
        0x4C | OpsCode.isValid(code)
        0x6C | OpsCode.isValid(code)
        0x0E | OpsCode.isValid(code)
        0x0F | OpsCode.isValid(code)
        0x2E | OpsCode.isValid(code)
        0x2F | OpsCode.isValid(code)
        0x4E | OpsCode.isValid(code)
        0x6E | OpsCode.isValid(code)
        0x6F | OpsCode.isValid(code)
        0x10 | OpsCode.isValid(code)
        0x30 | OpsCode.isValid(code)
        0x50 | OpsCode.isValid(code)
        0x70 | OpsCode.isValid(code)
        0x11 | OpsCode.isValid(code)
        0x31 | OpsCode.isValid(code)
        0x51 | OpsCode.isValid(code)
        0x71 | OpsCode.isValid(code)
        0x12 | OpsCode.isValid(code)
        0x32 | OpsCode.isValid(code)
        0x52 | OpsCode.isValid(code)
        0x72 | OpsCode.isValid(code)
        0x13 | OpsCode.isValid(code)
        0x33 | OpsCode.isValid(code)
        0x53 | OpsCode.isValid(code)
        0x73 | OpsCode.isValid(code)
        0x14 | OpsCode.isValid(code)
        0x34 | OpsCode.isValid(code)
        0x54 | OpsCode.isValid(code)
        0x74 | OpsCode.isValid(code)
        0x15 | OpsCode.isValid(code)
        0x35 | OpsCode.isValid(code)
        0x55 | OpsCode.isValid(code)
        0x75 | OpsCode.isValid(code)
        0x16 | OpsCode.isValid(code)
        0x36 | OpsCode.isValid(code)
        0x56 | OpsCode.isValid(code)
        0x76 | OpsCode.isValid(code)
        0x07 | OpsCode.isValid(code)
        0x37 | OpsCode.isValid(code)
        0x57 | OpsCode.isValid(code)
        0x77 | OpsCode.isValid(code)
        0x18 | OpsCode.isValid(code)
        0x38 | OpsCode.isValid(code)
        0x58 | OpsCode.isValid(code)
        0x19 | OpsCode.isValid(code)
        0x39 | OpsCode.isValid(code)
        0x59 | OpsCode.isValid(code)
        0x1A | OpsCode.isValid(code)
        0x3A | OpsCode.isValid(code)
        0x5A | OpsCode.isValid(code)
        0x1B | OpsCode.isValid(code)
        0x3B | OpsCode.isValid(code)
        0x5B | OpsCode.isValid(code)
        0x1C | OpsCode.isValid(code)
        0x3C | OpsCode.isValid(code)
        0x5C | OpsCode.isValid(code)
        0x1D | OpsCode.isValid(code)
        0x3D | OpsCode.isValid(code)
        0x5D | OpsCode.isValid(code)
        0x1E | OpsCode.isValid(code)
        0x3E | OpsCode.isValid(code)
        0x5E | OpsCode.isValid(code)
        0x1F | OpsCode.isValid(code)
        0x3F | OpsCode.isValid(code)
        0x5F | OpsCode.isValid(code)
        0x78 | OpsCode.isValid(code)
        0x79 | OpsCode.isValid(code)
        0x7A | OpsCode.isValid(code)
        0x7B | OpsCode.isValid(code)
        0x7C | OpsCode.isValid(code)
        0x7D | OpsCode.isValid(code)
        0x7E | OpsCode.isValid(code)
        0x7F | OpsCode.isValid(code)
   }

}