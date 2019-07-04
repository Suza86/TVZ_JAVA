package hr.tvz.programiranje.java.banka;

import java.math.BigDecimal;
import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.osoba.Osoba;

public class Transakcija
{
    private Racun _polazniRacun;
    private Racun _dolazniRacun;
    private BigDecimal _iznos;

    public Transakcija(Racun polazniRacun, Racun dolazniRacun, BigDecimal iznos)
    {
        _polazniRacun = polazniRacun;
        _dolazniRacun = dolazniRacun;
        _iznos = iznos;
    }

    public void provediTransakciju()
    {
        Osoba vlasnikPolaznog = _polazniRacun.getVlasnik();
        Osoba vlasnikDolaznog = _dolazniRacun.getVlasnik();

        if (!vlasnikPolaznog.getDrzavaStanovanja().equals(vlasnikDolaznog.getDrzavaStanovanja()))
        {
            BigDecimal postotak = new BigDecimal(0.01);
            BigDecimal porez = _iznos.multiply(postotak);
            BigDecimal iznosSPorezom = _iznos.add(porez);

            _polazniRacun.isplatiSRacuna(iznosSPorezom);
        }
        else
        {
            _polazniRacun.isplatiSRacuna(_iznos);
        }
        
        _dolazniRacun.uplatiNaRacun(_iznos);
    }
}