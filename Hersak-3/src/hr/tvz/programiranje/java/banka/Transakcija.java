package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import java.math.BigDecimal;

public class Transakcija
{
    protected Racun _polazniRacun;
    protected Racun _dolazniRacun;
    protected BigDecimal _iznos;

    public Transakcija(Racun polazniRacun, Racun dolazniRacun, BigDecimal iznos)
    {
        _polazniRacun = polazniRacun;
        _dolazniRacun = dolazniRacun;
        _iznos = iznos;
    }

    public void provediTransakciju() throws NedozvoljenoStanjeRacunaException
    {
        _polazniRacun.isplatiSRacuna(_iznos);
        _dolazniRacun.uplatiNaRacun(_iznos);
    }
}