package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

/**
 * Sadrži podatke o tekućem računu korisnika.
 *
 * @author Vjekoslav
 */
public class TekuciRacun extends Racun implements ImaOpunomocenika
{
    private String _brojRacuna;
    private Osoba _opunomocenik;

    /**
     * Inicijalizira podatak o vlasniku, stanju i broju računa.
     *
     * @param vlasnik podatak o vlasniku računa
     * @param stanje podatak o stanju računa
     * @param brojRacuna podatak o broju računa
     */
    public TekuciRacun(Osoba vlasnik, BigDecimal stanje, String brojRacuna)
    {
        super(vlasnik, stanje);
        _brojRacuna = brojRacuna;
    }

    /**
     * Inicijalizira podatak o id-u, vlasniku, stanju i broju računa.
     *
     * @param id podatak o id-u koji se koristi za spremanje i dohvaćanje računa iz baze podataka
     * @param vlasnik podatak o vlasniku računa
     * @param stanje podatak o stanju računa
     * @param brojRacuna podatak o broju računa
     */
    public TekuciRacun(Integer id, Osoba vlasnik, BigDecimal stanje, String brojRacuna)
    {
        super(id, vlasnik, stanje);
        _brojRacuna = brojRacuna;
    }

    /**
     * Postavlja osobu za opunomoćenika.
     *
     * @param opunomocenik podatak o opunomoćeniku
     */
    public void postaviOpunomocenika(Osoba opunomocenik)
    {
        _opunomocenik = opunomocenik;
    }

    /**
     * Vraća broj računa.
     *
     * @return broj računa
     */
    public String getBrojRacuna() { return _brojRacuna; }

    /**
     * Vraća opunomoćenika.
     *
     * @return opunomoćenik
     */
    public Osoba getOpunomocenik() { return _opunomocenik; }

    @Override
    public String toString()
    {
        return _brojRacuna;
    }
}