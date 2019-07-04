package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

public class TekuciRacun extends Racun implements ImaOpunomocenika
{
    private String _brojRacuna;
    private Osoba _opunomocenik;

    public TekuciRacun(Osoba vlasnik, BigDecimal stanje, String brojRacuna)
    {
        super(vlasnik, stanje);
        _brojRacuna = brojRacuna;
    }

    public void postaviOpunomocenika(Osoba opunomocenik)
    {
        _opunomocenik = opunomocenik;
    }

    public String getBrojRacuna() { return _brojRacuna; }

    public Osoba getOpunomocenik() { return _opunomocenik; }
}