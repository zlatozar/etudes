package ComputerSimulation.computer

enum OpsKind {

    RR([OpsCode.LR, OpsCode.LNR, OpsCode.STR, OpsCode.SWAPR, OpsCode.ANDR, OpsCode.ORR, OpsCode.XORR, OpsCode.NOTR,
        OpsCode.BCSR, OpsCode.BCRR, OpsCode.BALR, OpsCode.SACR, OpsCode.CR, OpsCode.CCS, OpsCode.MCS, OpsCode.AR,
        OpsCode.SR, OpsCode.RSR, OpsCode.MR, OpsCode.DR, OpsCode.RDR, OpsCode.REMR, OpsCode.RREMR, OpsCode.FAR,
        OpsCode.FSR, OpsCode.RFSR, OpsCode.FMR, OpsCode.FDR, OpsCode.RFDR, OpsCode.FLOATR, OpsCode.FIXR]),

    RS([OpsCode.L, OpsCode.LN, OpsCode.ST, OpsCode.SWAP, OpsCode.AND, OpsCode.OR, OpsCode.XOR, OpsCode.NOT, OpsCode.BCS,
        OpsCode.BCR, OpsCode.BAL, OpsCode.SAC, OpsCode.C, OpsCode.SVC, OpsCode.EX, OpsCode.LA, OpsCode.LM, OpsCode.STM,
        OpsCode.A, OpsCode.S, OpsCode.RS, OpsCode.M, OpsCode.D, OpsCode.RD, OpsCode.REM, OpsCode.RREM, OpsCode.FA,
        OpsCode.FS, OpsCode.RFS, OpsCode.FM, OpsCode.FD, OpsCode.RFD, OpsCode.FLOAT, OpsCode.FIX, OpsCode.FLOOR,
        OpsCode.CEIL, OpsCode.MIN, OpsCode.MAX, OpsCode.SHIFTL, OpsCode.SHIFTC, OpsCode.SHIFTA, OpsCode.SHIFTR]),

    IM([OpsCode.LI, OpsCode.LNI, OpsCode.ANDI, OpsCode.ORI, OpsCode.XORI, OpsCode.NOTI, OpsCode.CI, OpsCode.AI,
        OpsCode.SI, OpsCode.RSI, OpsCode.MI, OpsCode.DI, OpsCode.RDI, OpsCode.REMI, OpsCode.RREMI, OpsCode.FAI,
        OpsCode.FSI, OpsCode.RFSI, OpsCode.FMI, OpsCode.FDI, OpsCode.RFDI, OpsCode.FLOATI, OpsCode.FIXI]),

    CH([OpsCode.LC, OpsCode.LNC, OpsCode.STC, OpsCode.SWAPC, OpsCode.ANDC, OpsCode.ORC, OpsCode.XORC, OpsCode.NOTC,
        OpsCode.SACC, OpsCode.CC, OpsCode.AC, OpsCode.SC, OpsCode.RSC, OpsCode.MC, OpsCode.DC, OpsCode.RDC, OpsCode.REMC,
        OpsCode.RREMC ])

    private List<OpsCode> codes

    private static final Map<OpsKind, List<OpsCode>> lookup = new HashMap<>();

    static {
        for (OpsKind opsFormat : values()) {
            lookup.put(opsFormat, opsFormat.codes)
        }
    }

    private OpsKind(List<OpsCode> codes) {
        this.codes = codes
    }

    static def infer(OpsCode opsCode) {
        for (def opsFormat : lookup.entrySet()) {
            if (opsFormat.getValue().contains(opsCode)) {
                switch (opsFormat.getKey()) {
                    case 'RR' : return OpsKind.RR
                    case 'RS' : return OpsKind.RS
                    case 'IM' : return OpsKind.IM
                    case 'CH' : return OpsKind.CH
                }
            }
        }
    }

    // Helper

    protected static List<OpsCode> getRR() {
        return lookup.get(RR)
    }

    protected static List<OpsCode> getRS() {
        return lookup.get(RS)
    }

    protected static List<OpsCode> getIM() {
        return lookup.get(IM)
    }

    protected static List<OpsCode> getCH() {
        return lookup.get(CH)
    }
}
