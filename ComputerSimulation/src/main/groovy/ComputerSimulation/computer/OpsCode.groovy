package ComputerSimulation.computer

enum OpsCode {

    LR(0x00),
    L(0x20),
    LI(0x40),
    LC(0x60),
    LNR(0x01),
    LN(0x21),
    LNI(0x41),
    LNC(0x61),
    STR(0x02),
    ST(0x22),
    STC(0x62),
    SWAPR(0x03),
    SWAP(0x23),
    SWAPC(0x63),
    ANDR(0x04),
    AND(0x24),
    ANDI(0x44),
    ANDC(0x64),
    ORR(0x05),
    OR(0x25),
    ORI(0x45),
    ORC(0x65),
    XORR(0x06),
    XOR(0x26),
    XORI(0x46),
    XORC(0x66),
    NOTR(0x07),
    NOT(0x27),
    NOTI(0x47),
    NOTC(0x67),
    BCSR(0x08),
    BCS(0x28),
    BCRR(0x09),
    BCR(0x29),
    BALR(0x0A),
    BAL(0x2A),
    SACR(0x0B),
    SAC(0x2B),
    SACC(0x6B),
    CR(0x0C),
    C(0x2C),
    CI(0x4C),
    CC(0x6C),
    CCS(0x0E),
    MCS(0x0F),
    SVC(0x2E),
    EX(0x2F),
    LA(0x4E),
    LM(0x6E),
    STM(0x6F),
    AR(0x10),
    A(0x30),
    AI(0x50),
    AC(0x70),
    SR(0x11),
    S(0x31),
    SI(0x51),
    SC(0x71),
    RSR(0x12),
    RS(0x32),
    RSI(0x52),
    RSC(0x72),
    MR(0x13),
    M(0x33),
    MI(0x53),
    MC(0x73),
    DR(0x14),
    D(0x34),
    DI(0x54),
    DC(0x74),
    RDR(0x15),
    RD(0x35),
    RDI(0x55),
    RDC(0x75),
    REMR(0x16),
    REM(0x36),
    REMI(0x56),
    REMC(0x76),
    RREMR(0x17),
    RREM(0x37),
    RREMI(0x57),
    RREMC(0x77),
    FAR(0x18),
    FA(0x38),
    FAI(0x58),
    FSR(0x19),
    FS(0x39),
    FSI(0x59),
    RFSR(0x1A),
    RFS(0x3A),
    RFSI(0x5A),
    FMR(0x1B),
    FM(0x3B),
    FMI(0x5B),
    FDR(0x1C),
    FD(0x3C),
    FDI(0x5C),
    RFDR(0x1D),
    RFD(0x3D),
    RFDI(0x5D),
    FLOATR(0x1E),
    FLOAT(0x3E),
    FLOATI(0x5E),
    FIXR(0x1F),
    FIX(0x3F),
    FIXI(0x5F),
    FLOOR(0x78),
    CEIL(0x79),
    MIN(0x7A),
    MAX(0x7B),
    SHIFTL(0x7C),
    SHIFTC(0x7D),
    SHIFTA(0x7E),
    SHIFTR(0x7F)

    private int instruction

    private OpsCode(int instruction) {
        this.instruction = instruction
    }

    // Reverse-lookup map for getting a OpsCode from an instruction
    private static final Map<Integer, OpsCode> lookup = new HashMap<Integer, OpsCode>();

    static {
        for (OpsCode opsCode : values()) {
            if (lookup.containsKey(opsCode.instruction)) {
                println(Integer.toHexString(opsCode.instruction))
            }
            lookup.put(opsCode.instruction, opsCode)
        }
    }

    static boolean isValid(int instruction) {
        return lookup.containsKey(instruction)
    }

    static String display(int instruction) {
        if (!isValid(instruction)) {
            throw new IllegalArgumentException("$instruction is not an EC-1 ops code")
        }

        return lookup.get(instruction)
    }

    // Helpers

    protected static int getNumberOfOpsCodes() {
        return lookup.size()
    }

    @Override
    String toString() {
        return "${super.toString()}(0x${Integer.toHexString(instruction)})"
    }
}
