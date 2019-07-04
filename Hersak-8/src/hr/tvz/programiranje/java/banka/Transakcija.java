package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import java.math.BigDecimal;

/**
 * Sadrži podatke o transakciji između dva računa.
 *
 * @author Vjekoslav
 */
public class Transakcija<T extends Racun, S extends Racun>
{
    protected Integer _id;
    protected T _polazniRacun;
    protected S _dolazniRacun;
    protected BigDecimal _iznos;

    /**
     * Inicijalizira podatak o polaznom računu, dolaznom računu i iznos transakcije.
     *
     * @param polazniRacun podatak o polaznom računu
     * @param dolazniRacun podatak o dolaznom računu
     * @param iznos podatak o iznosu transakcije
     */
    public Transakcija(T polazniRacun, S dolazniRacun, BigDecimal iznos)
    {
        _polazniRacun = polazniRacun;
        _dolazniRacun = dolazniRacun;
        _iznos = iznos;
    }

    /**
     * Inicijalizira podatak o id-u, polaznom računu, dolaznom računu i iznos transakcije.
     *
     * @param id podatak o id-u koji se koristi za spremanje i dohvaćanje transakcija iz baze podataka
     * @param polazniRacun podatak o polaznom računu
     * @param dolazniRacun podatak o dolaznom računu
     * @param iznos podatak o iznosu transakcije
     */
    public Transakcija(Integer id, T polazniRacun, S dolazniRacun, BigDecimal iznos)
    {
        this(polazniRacun, dolazniRacun, iznos);
        _id = id;
    }

    /**
     * Umanjuje iznos polaznog računa i za isti iznos uvećava dolazni račun.
     *
     * @exception NedozvoljenoStanjeRacunaException ukoliko nema dovoljno sredstava na polaznom računu
     */
    public void provediTransakciju() throws NedozvoljenoStanjeRacunaException
    {
        BigDecimal stanjePolaznog = _polazniRacun.getStanje();
        if (stanjePolaznog.compareTo(_iznos) < 0)
        {
            String poruka = String.format("Polazni račun nema dovoljno sredstava (%s) za provođenje transkacije (%s)!", 
                stanjePolaznog, _iznos);
            throw new NedozvoljenoStanjeRacunaException(poruka);
        }

        _polazniRacun.isplatiSRacuna(_iznos);
        _dolazniRacun.uplatiNaRacun(_iznos);
    }

    /**
     * Vraća id transakcije.
     *
     * @return id transakcije
     */
    public Integer getId() { return _id; }

    /**
     * Vraća polazni račun transakcije.
     *
     * @return polazni račun transakcije
     */
    public T getPolazniRacun() { return _polazniRacun; }

    /**
     * Vraća dolazni račun transakcije.
     *
     * @return dolazni račun transakcije
     */
    public S getDolazniRacun() {return _dolazniRacun; }

    /**
     * Vraća iznos transakcije.
     *
     * @return iznos transakcije
     */
    public BigDecimal getIznos() { return _iznos; }
}