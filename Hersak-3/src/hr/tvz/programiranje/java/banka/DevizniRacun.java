package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

public class DevizniRacun extends Racun
{
    private String _iban;
    private String _valuta;

    public DevizniRacun(Osoba vlasnik, BigDecimal stanje, String iban, String valuta)
    {
        super(vlasnik, stanje);
        _iban = iban;
        _valuta = valuta;
    }

    public String getIban() { return _iban; }

    public String getValuta() { return _valuta; }
}