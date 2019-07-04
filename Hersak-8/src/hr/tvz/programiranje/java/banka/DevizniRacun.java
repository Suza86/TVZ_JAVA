package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

/**
 * Sadrži podatke specifične za devizni račun.
 *
 * @author Vjekoslav
 */
public class DevizniRacun extends Racun
{
    private String _iban;
    private Valuta _valuta;

    /**
     * Inicijalizira podatak o vlasniku, stanju, ibanu i valuti deviznog računa.
     *
     * @param vlasnik podatak o vlasniku računa
     * @param stanje podatak o stanju računa
     * @param iban podatak o ibanu računa
     * @param valuta podatak o valuti računa
     */
    public DevizniRacun(Osoba vlasnik, BigDecimal stanje, String iban, Valuta valuta)
    {
        super(vlasnik, stanje);
        _iban = iban;
        _valuta = valuta;
    }

    /**
     * Inicijalizira podatak o id-u, vlasniku, stanju, ibanu i valuti deviznog računa.
     *
     * @param id podatak o id-u koji se koristi za spremanje i dohvaćanje računa iz baze podataka
     * @param vlasnik podatak o vlasniku računa
     * @param stanje podatak o stanju računa
     * @param iban podatak o ibanu računa
     * @param valuta podatak o valuti računa
     */
    public DevizniRacun(Integer id, Osoba vlasnik, BigDecimal stanje, String iban, Valuta valuta)
    {
        super(id, vlasnik, stanje);
        _iban = iban;
        _valuta = valuta;
    }

    /**
     * Vraća iban računa.
     *
     * @return iban računa.
     */
    public String getIban() { return _iban; }

    /**
     * Vraća valutu računa.
     *
     * @return valuta računa
     */
    public Valuta getValuta() { return _valuta; }

    @Override
    public String toString()
    {
        return _iban;
    }
}