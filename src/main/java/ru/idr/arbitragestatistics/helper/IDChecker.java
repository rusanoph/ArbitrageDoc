package ru.idr.arbitragestatistics.helper;

public class IDChecker {

    // ОГРН
    public static boolean isCorrectOGRN(String ogrn) {

        if (ogrn.length() != 13) {
            return false;
        }

        long ogrnFirst12Digits = Long.parseLong(ogrn.substring(0, ogrn.length() - 1));
        long ogrnLastDigit = ogrn.toCharArray()[ogrn.length() - 1] - '0';

        long controlNumber = ogrnFirst12Digits % 11;
        controlNumber %= 10;

        if (controlNumber != ogrnLastDigit) {
            return false;
        }

        return true;
    } 

    // ОГРН ИП
    public static boolean isCorrectOGRNIP(String ogrnip) {

        if (ogrnip.length() != 15) {
            return false;
        }

        if (!ogrnip.startsWith("3") && !ogrnip.startsWith("4")) {
            return false;
        }

        long ogrnFirst12Digits = Long.parseLong(ogrnip.substring(0, ogrnip.length() - 1));
        long ogrnLastDigit = ogrnip.toCharArray()[ogrnip.length() - 1] - '0';

        long controlNumber = ogrnFirst12Digits % 13;
        controlNumber %= 10;

        if (controlNumber != ogrnLastDigit) {
            return false;
        }

        return true;
    }

    // ИНН Физ. лица
    public static boolean isCorrectIndividualINN(String inn) {

        if (inn.length() != 12) {
            return false;
        }

        char[] innArray = inn.toCharArray();

        int currentControlNum11 = innArray[10] - '0';
        int checkControlNum11 = 0;
        int[] controlCoef11 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};  

        for (int i = 0; i < controlCoef11.length; i++) {
            checkControlNum11 += controlCoef11[i] * (innArray[i] - '0');
        }
        checkControlNum11 %= 11;        
        checkControlNum11 %= 10;

        if (currentControlNum11 != checkControlNum11) {
            return false;
        }


        int currentControlNum12 = innArray[11] - '0';
        int checkControlNum12 = 0;
        int[] controlCoef12 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
        for (int i = 0; i < controlCoef12.length; i++) {
            checkControlNum12 += controlCoef12[i] * (innArray[i] - '0');
        }
        checkControlNum12 %= 11;        
        checkControlNum12 %= 10;

        if (currentControlNum12 != checkControlNum12) {
            return false;
        }

        return true;
    }

    // ИНН Юр. лица
    public static boolean isCorrectLegalEntityINN(String inn) {

        if (inn.length() != 10) {
            return false;
        }

        char[] innArray = inn.toCharArray();

        int currentControlNum = innArray[9] - '0';
        int checkControlNum = 0;
        int[] controlCoef11 = {2, 4, 10, 3, 5, 9, 4, 6, 8};  

        for (int i = 0; i < controlCoef11.length; i++) {
            checkControlNum += controlCoef11[i] * (innArray[i] - '0');
        }
        checkControlNum %= 11;        
        checkControlNum %= 10;

        if (currentControlNum != checkControlNum) {
            return false;
        }

        return true;
    }
    
}
