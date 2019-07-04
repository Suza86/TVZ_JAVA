package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

public class Racun
{
    private Osoba _vlasnik;
    private BigDecimal _stanje;

    public Racun(Osoba vlasnik, BigDecimal stanje)
    {
        _vlasnik = vlasnik;
        _stanje = stanje;
    }

    public void uplatiNaRacun(BigDecimal iznos)
    {
        _stanje = _stanje.add(iznos);
    }

    public void isplatiSRacuna(BigDecimal iznos)
    {
        _stanje = _stanje.subtract(iznos);
    }

    public Osoba getVlasnik() { return _vlasnik; }

    public BigDecimal getStanje() { return _stanje; }
}