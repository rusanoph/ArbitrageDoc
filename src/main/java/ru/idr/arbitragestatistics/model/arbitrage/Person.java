package ru.idr.arbitragestatistics.model.arbitrage;

import ru.idr.arbitragestatistics.helper.IDChecker;

public class Person {

    private String ogrn;    
    private String ogrnip;
    private String inn;
    private ArbitrageSide arbitrageSide;

    //#region OGRN Getter/Setter
    public String getOgrn() {
        return ogrn;
    }
    public void setOgrn(String ogrn) {
        if (IDChecker.isCorrectOGRN(ogrn)) {
            this.ogrn = ogrn;
        } else {
            throw new IllegalArgumentException("Illegal argument: incorrect OGRN number;");
        }
    }
    //#endregion

    //#region INN Getter/Setter
    public String getOgrnip() {
        return ogrnip;
    }
    public void setOgrnip(String ogrnip) {
        if (IDChecker.isCorrectOGRNIP(ogrnip)) {
            this.ogrnip = ogrnip;
        } else {
            throw new IllegalArgumentException("Illegal argument: incorrect OGRNIP number;");
        }
    }
    //#endregion

    //#region INN Getter/Setter
    public String getInn() {
        return inn;
    }
    public void setInn(String inn) {
        if (IDChecker.isCorrectIndividualINN(inn) || IDChecker.isCorrectLegalEntityINN(inn)) {
            this.inn = inn;
        } else {
            throw new IllegalArgumentException("Illegal argument: incorrect INN number;");
        }
    }
    //#endregion
    

    public ArbitrageSide getArbitrageSide() {
        return arbitrageSide;
    }
    public void setArbitrageSide(ArbitrageSide arbitrageSide) {
        this.arbitrageSide = arbitrageSide;
    }

    
}
