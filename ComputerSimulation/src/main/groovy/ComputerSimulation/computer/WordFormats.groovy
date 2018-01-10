package ComputerSimulation.computer

import java.nio.charset.StandardCharsets

class WordFormats {

    private static final int MEMORY_WORD_SIZE = 8

    private static final int EXPONENT_SIZE = 7

    private static final int FRACTION_SIZE = 24

    private static final int IMMEDIATE_FRACTION_SIZE = FRACTION_SIZE / 2

    private final int exponentSize
    private final int fractionSize
    private final int immediateFractionSize

    WordFormats(int exponentSize=EXPONENT_SIZE, int fractionSize=FRACTION_SIZE, int immediateFractionSize=IMMEDIATE_FRACTION_SIZE) {
        this.exponentSize = exponentSize
        this.fractionSize = fractionSize
        this.immediateFractionSize = immediateFractionSize
    }

    List<String> formReal(float num) {
        return formGenericReal(num, exponentSize, fractionSize)
    }

    List<String> formImmediateReal(float num) {
        return formGenericReal(num, exponentSize, immediateFractionSize)
    }

    float displayReal(List<String> binNum) {
        return displayGenericReal(binNum, exponentSize)
    }

    int displayInteger(List<String> binNum) {
        return Integer.parseInt(binNum.join(), 2)
    }

    static List<String> createWord(String word) {

        final List<String> binaryWords = new ArrayList<>()

        // Use standard ASCII
        byte[] bytes = word.getBytes(StandardCharsets.US_ASCII)

        StringBuilder binary = new StringBuilder(MEMORY_WORD_SIZE)
        for (byte b : bytes) {

            int val = b;

            for (int i = 0; i < MEMORY_WORD_SIZE; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }

            binaryWords.add(binary.toString())
            binary = new StringBuilder(MEMORY_WORD_SIZE)
        }

        return binaryWords
    }

    // Helper methods

    private static List<String> formGenericReal(float c, int exponentSize, int fractionSize) {

        List<String> fraction = formFraction(c, fractionSize)
        List<String> exponent = calculateExponent(fraction, exponentSize)
        fraction = normalizeFraction(fraction)

        List<String> fitFraction = fitFraction(fraction, fractionSize)
        List<String> fitExponent = fitExponent(exponent, exponentSize)

        List<String> sign = inferSign(c)

        List<String> binNum = new ArrayList<>()

        binNum.addAll(sign)
        binNum.addAll(fitExponent)
        binNum.addAll(fitFraction)

        return binNum
    }

    private static float displayGenericReal(List<String> binFloat, int exponentSize) {

        String sign = binFloat[0]
        int exponent = extractExponent(binFloat, exponentSize)
        List<String> fraction = extractFraction(binFloat, exponentSize)

        exponent = exponent - getMaxExponent(exponentSize)
        List<String> deNormalized = deNormalize(fraction, exponent)

        return convertToFloat(deNormalized, sign)
    }

    private static float convertToFloat(List<String> deNormalized, String sign) {
        int dotIndex = deNormalized.indexOf('.')
        List<String> base = deNormalized.subList(0, dotIndex)
        List<String> fraction = deNormalized.subList(dotIndex + 1, deNormalized.size())

        float absoluteNum = decodeBase(base) + decodeFraction(fraction)
        return isPositive(sign) ? absoluteNum : -(absoluteNum)
    }

    private static float decodeBase(List<String> base) {
        float baseFloat = 0.0
        int power = 0

        for (int i = base.size() - 1; i >= 0 ; i--) {
            baseFloat = baseFloat + Integer.valueOf(base[i]) * Math.pow(2, power)
            power++
        }

        return baseFloat
    }

    private static float decodeFraction(List<String> fraction) {
        float fractionFloat = 0.0

        for (int i = 0; i < fraction.size(); i++) {
            fractionFloat = fractionFloat + (Integer.valueOf(fraction[i]) * Math.pow(2, -(i+1)))
        }

        return fractionFloat
    }

    private static List<String> deNormalize(List<String> fraction, int exponent) {
        List<String> deNormalized = new ArrayList<>()

        if (exponent > 0) {
            deNormalized.add('1')
            deNormalized.addAll(fraction)
            deNormalized.add(exponent + 1, '.')

        } else {
            deNormalized.add('0')
            deNormalized.add('.')

            for (int i = 0; i < exponent - 1; i++) {
               deNormalized.add('0')
            }
            deNormalized.add('1')
            deNormalized.addAll(fraction)
        }

        return deNormalized
    }

    private static int extractExponent(List<String> binFloat, int exponentSize) {
        return asDigit(binFloat.subList(1, exponentSize + 1))
    }

    private static int getMaxExponent(int exponentSize) {
        return Math.pow(2, exponentSize - 1) - 1
    }

    private static int asDigit(List<String> binNum) {
        StringBuilder sb = new StringBuilder(binNum.size())
        for (String bit : binNum) {
            sb.append(bit)
        }

        return new BigInteger(sb.toString(), 2).intValue()
    }

    private static List<String> extractFraction(List<String> binFloat, int exponentSize) {
        return binFloat.subList(exponentSize + 1, binFloat.size())
    }

    private static List<String> normalizeFraction(List<String> fraction) {
        List<String> normalized = new ArrayList<>()

        int firstOneIdx = 0
        for (int i = 0; i < fraction.size(); i++) {

            if (fraction[i] == '1') {
                firstOneIdx = i + 1
                break
            }
        }

        for(String bit : fraction.subList(firstOneIdx, fraction.size())) {
            if (bit != '.') {
                normalized.add(bit)
            }
        }

        return normalized
    }

    private static List<String> inferSign(float num) {
        return num > 0 ? ['0'] : ['1']
    }

    private static boolean isPositive(String num) {
        return num == '0'
    }

    private static List<String> formFraction(float num, int fractionSize) {

        List<String> fraction = new ArrayList<>()

        fraction.addAll(intToBinaryList(takeBase(num)))
        fraction.add('.')

        float reminder = takeReminder(num)
        float curReminder = reminder

        int curFractionSize = 0
        while(shouldMulCont(curReminder) && curFractionSize < fractionSize) {
            float step = curReminder * 2

            curReminder = takeReminder(step)
            fraction.add(step >= 1 ? '1' : '0')
            curFractionSize++
        }

        return fraction
    }

    private static List<String> calculateExponent(List<String> fraction, int exponentSize) {

        int dist = getDistance(fraction)

        List<String> exponent = Integer.toBinaryString(dist + getMaxExponent(exponentSize)).toCharArray() as List

        if (exponent.size() > exponentSize) {
            throw new IllegalArgumentException('Exponent overflow')
        }

        return exponent
    }

    private static int getDistance(List<String> fraction) {

        // start before first digit
        int dist = -1

        boolean start = false

        for (String bit : fraction) {
            if (bit == '1') {
                start = true
            }

            if (start) {

                if (bit != '.') {
                    dist++

                } else {
                    break
                }
            }
        }

        return dist
    }

    private static List<String> fitFraction(List<String> fraction, int fractionSize) {
        List<String> fitFraction = new ArrayList<>(fractionSize)

        int diff = fractionSize - fraction.size()

        for (int i = 0; i < diff; i++) {
            fitFraction.add('0')
        }

        for (String bit : fraction) {
            if (bit != '.') {
                fitFraction.add(bit)
            }
        }

        return fitFraction
    }

    private static List<String> fitExponent(List<String> exponent, int exponentSize) {
        List<String> fitExponent = new ArrayList<>(exponentSize)

        int diff = exponentSize - exponent.size()

        for (int i = 0; i < diff; i++) {
            fitExponent.add('0')
        }

        fitExponent.addAll(exponent)

        return fitExponent
    }

    private static float takeReminder(float num) {
        return Float.valueOf('0.' + "$num".split('\\.')[1])
    }

    private static int takeBase(float num) {
        return Integer.valueOf("$num".split('\\.')[0])
    }

    private static boolean shouldMulCont(float curStep) {
        return Integer.valueOf("$curStep".split('\\.')[1]) > 0
    }

    private static List<String> intToBinaryList(int num) {
        return Integer.toBinaryString(Math.abs(num)).toCharArray() as List
    }

}
