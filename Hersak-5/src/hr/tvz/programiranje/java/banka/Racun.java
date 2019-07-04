package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

/**
 * Sadrži osnovne podatke koje svaki račun mora imati.
 *
 * @author Vjekoslav
 */
public abstract class Racun
{
    private Osoba _vlasnik;
    private BigDecimal _stanje;

    /**
     * Inicijalizira podatak o vlasniku i stanju računa.
     *
     * @param vlasnik podatak o vlasniku računa
     * @param stanje podatak o stanju računa
     */
    public Racun(Osoba vlasnik, BigDecimal stanje)
    {
        _vlasnik = vlasnik;
        _stanje = stanje;
    }

    /**
     * Uvećava iznos na računu.
     *
     * @param iznos podatak o iznosu za koji će račun biti uvećan
     */
    public void uplatiNaRacun(BigDecimal iznos)
    {
        _stanje = _stanje.add(iznos);
    }

    /**
     * Smanjuje iznos na računu.
     *
     * @param iznos podatak o iznosu za koji će račun biti smanjen
     */
    public void isplatiSRacuna(BigDecimal iznos)
    {
        _stanje = _stanje.subtract(iznos);
    }

    /**
     * Vraća vlasnika računa.
     *
     * @return vlasnik računa
     */
    public Osoba getVlasnik() { return _vlasnik; }

    /**
     * Vraća stanje računa. 
     *
     * @return stanje računa
     */
    public BigDecimal getStanje() { return _stanje; }
}