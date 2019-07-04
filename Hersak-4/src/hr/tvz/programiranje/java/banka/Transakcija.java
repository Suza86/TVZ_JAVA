package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import java.math.BigDecimal;

/**
 * Sadrži podatke o transakciji između dva računa.
 *
 * @author Vjekoslav
 */
public class Transakcija
{
    protected Racun _polazniRacun;
    protected Racun _dolazniRacun;
    protected BigDecimal _iznos;

    /**
     * Inicijalizira podatak o polaznom računu, dolaznom računu i iznos transakcije.
     *
     * @param polazniRacun podatak o polaznom računu
     * @param dolazniRacun podatak o dolaznom računu
     * @param iznos podatak o iznosu transakcije
     */
    public Transakcija(Racun polazniRacun, Racun dolazniRacun, BigDecimal iznos)
    {
        _polazniRacun = polazniRacun;
        _dolazniRacun = dolazniRacun;
        _iznos = iznos;
    }

    /**
     * Umanjuje iznos polaznog računa i za isti iznos uvećava dolazni račun.
     *
     * @exception NedozvoljenoStanjeRacunaException ukoliko nema dovoljno sredstava na polaznom računu
     */
    public void provediTransakciju() throws NedozvoljenoStanjeRacunaException
    {
        _polazniRacun.isplatiSRacuna(_iznos);
        _dolazniRacun.uplatiNaRacun(_iznos);
    }

    /**
     * Vraća iznos transakcije.
     *
     * @return iznos transakcije
     */
    public BigDecimal getIznos() { return _iznos; }
}