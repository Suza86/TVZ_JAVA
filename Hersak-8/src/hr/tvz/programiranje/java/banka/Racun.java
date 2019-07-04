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
    private Integer _id;
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
     * Inicijalizira podatak o id-u, vlasniku i stanju računa.
     *
     * @param id podatak o id-u koji se koristi za spremanje i dohvaćanje računa iz baze podataka
     * @param vlasnik podatak o vlasniku računa
     * @param stanje podatak o stanju računa
     */
    public Racun(Integer id, Osoba vlasnik, BigDecimal stanje)
    {
        this(vlasnik, stanje);
        _id = id;
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
     * Vraća id računa.
     *
     * @return id računa
     */
    public Integer getId() { return _id; }

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