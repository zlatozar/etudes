package MapColoring

/**
 * Computers look at the map. The only crucial information
 * is a list of which regions are adjacent to each other.
 *
 * NOTE: state : [ all adjacent ]
 */
class GeographicMaps {

    class Australia implements Territory {

        private String WA  = 'WesternAustralia'
        private String NT  = 'NorthwestTerritories'
        private String SA  = 'SouthernAustralia'
        private String Q   = 'Queensland'
        private String NSW = 'NewSouthWales'
        private String V   = 'Victoria'
        private String T   = 'Tasmania'

        Map<String, Set<String>> borders() {
            return [
                    (T)  : [V],
                    (WA) : [NT, SA],
                    (NT) : [WA, Q, SA],
                    (SA) : [WA, NT, Q, NSW, V],
                    (Q)  : [NT, SA, NSW],
                    (NSW): [Q, SA, V],
                    (V)  : [SA, NSW, T]
                    ]
        }
    }

    public static class America implements Territory {

        private String AL = 'Alabama'
        private String AK = 'Alaska'
        private String AZ = 'Arizona'
        private String AR = 'Arkansas'
        private String CA = 'California'
        private String CO = 'Colorado'
        private String CT = 'Connecticut'
        private String DC = 'WashingtonDC'
        private String DE = 'Delaware'
        private String FL = 'Florida'
        private String GA = 'Georgia'
        private String HI = 'Hawaii'
        private String ID = 'Idaho'
        private String IL = 'Illinois'
        private String IN = 'Indiana'
        private String IA = 'Iowa'
        private String KA = 'Kansas'
        private String KY = 'Kentucky'
        private String LA = 'Louisiana'
        private String ME = 'Maine'
        private String MD = 'Maryland'
        private String MA = 'Massachusetts'
        private String MI = 'Michigan'
        private String MN = 'Minnesota'
        private String MS = 'Mississippi'
        private String MO = 'Missouri'
        private String MT = 'Montana'
        private String NE = 'Nebraska'
        private String NV = 'Nevada'
        private String NH = 'NewHampshire'
        private String NJ = 'NewJersey'
        private String NM = 'NewMexico'
        private String NY = 'NewYork'
        private String NC = 'NorthCarolina'
        private String ND = 'NorthDakota'
        private String OH = 'Ohio'
        private String OK = 'Oklahoma'
        private String OR = 'Oregon'
        private String PA = 'Pennsylvania'
        private String RI = 'RhodeIsland'
        private String SC = 'SouthCarolina'
        private String SD = 'SouthDakota'
        private String TN = 'Tennessee'
        private String TX = 'Texas'
        private String UT = 'Utah'
        private String VT = 'Vermont'
        private String VA = 'Virginia'
        private String WA = 'Washington'
        private String WV = 'WestVirginia'
        private String WI = 'Wisconsin'
        private String WY = 'Wyoming'

        Map<String, Set<String>> borders() {
            return [
                    (AK) : [],
                    (HI) : [],

                    // Starting from the upper left of a book illustration
                    (WA) : [OR, ID],
                    (ID) : [WA, OR, NV, UT, WY, MT],
                    (MT) : [ID, WY, SD, ND],
                    (ND) : [MT, SD, MN],
                    (MN) : [ND, SD, IA, WI],
                    (WI) : [MN, IA, IL, MI],
                    (MI) : [WI, IN, OH],
                    (NY) : [PA, NJ, CT, MA, VT],
                    (VT) : [NY, MA, NH],
                    (ME) : [NH],

                    (OR) : [WA, ID, CA, NV],
                    (NV) : [ID, OR, CA, AZ, UT],
                    (UT) : [ID, NV, AZ, CO, NY],
                    (WY) : [MT, ID, UT, CO, NE, SD],
                    (SD) : [MN, ND, MT, WY, NE, IA],
                    (IA) : [MN, SD, NE, MO, WI, IL],
                    (IL) : [WI, IA, MO, IN],
                    (OH) : [MI, IN, KY, WV, PA],
                    (PA) : [NY, OH, WV, MD, DE, NJ],
                    (NJ) : [NY, PA, DE],
                    (MA) : [VT, NY, CT, RI, NH],
                    (NH) : [ME, VT, MA],

                    (CA) : [OR, NV, AZ],
                    (AZ) : [UT, NV, CA, NM],
                    (CO) : [WY, UT, NM, OK, KA, NE],
                    (NE) : [IA, SD, WY, CO, KA, MO],
                    (MO) : [IL, IA, NE, KA, OK, AR, TN, KY],
                    (IN) : [OH, MI, IL, KY],
                    (WV) : [PA, OH, KY, VA, MD],
                    (DE) : [NJ, PA, MD],
                    (CT) : [MA, NY, RI],
                    (RI) : [MA, CT],

                    (NM) : [CO, AZ, TX, OK],
                    (KA) : [NE, CO, OK, MO],
                    (AR) : [MO, OK, TX, LA, MS, TN],
                    (KY) : [WV, OH, IN, MO, TN, VA],
                    (VA) : [WV, KY, TN, NC, DC, MD],
                    (MD) : [DE, PA, WV, VA, DE],
                    (OK) : [KA, CO, NM, TX, AR, MO],
                    (LA) : [AR, TX, MS],
                    (MS) : [AR, LA, AL, TN],
                    (TN) : [VA, KY, MO, AR, MS, AL, GA, NC],
                    (NC) : [VA, TN, GA, SC],
                    (DC) : [MD, VA],

                    (TX) : [LA, AR, OK, NM],
                    (AL) : [TN, MS, FL, GA],
                    (GA) : [NC, TN, AL, FL, SC],
                    (SC) : [NC, GA],
                    (FL) : [GA, AL],

                    // 49 states in the picture + Alaska and Hawaii
                    ]
        }
    }
}
