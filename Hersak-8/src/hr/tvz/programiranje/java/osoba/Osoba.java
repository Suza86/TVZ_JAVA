package hr.tvz.programiranje.java.osoba;

/**
 * Sadrži osobne podatke neke osobe koji su potrebni za svaki račun.
 *
 * @author Vjekoslav
 */
public class Osoba
{
    private Integer _id;
    private String _ime;
    private String _prezime;
    private String _oib;

    /**
     * Inicijalizira podatak o imenu, prezimenu i oibu osobe.
     *
     * @param ime podatak o imenu osobe
     * @param prezime podatak o prezimenu osobe
     * @param oib podatak o oibu osobe
     */
    public Osoba(String ime, String prezime, String oib)
    {
        _ime = ime;
        _prezime = prezime;
        _oib = oib;
    }

    /**
     * Inicijalizira podatak o id-u, imenu, prezimenu i oibu osobe.
     *
     * @param id podatak o id-u koji se koristi za spremanje i dohvaćanje osoba iz baze podataka
     * @param ime podatak o imenu osobe
     * @param prezime podatak o prezimenu osobe
     * @param oib podatak o oibu osobe
     */
    public Osoba(Integer id, String ime, String prezime, String oib)
    {
        this(ime, prezime, oib);
        _id = id;
    }

    /**
     * Vraća id osobe.
     *
     * @return id osobe
     */
    public Integer getId() { return _id; }

    /**
     * Vraća ime osobe.
     *
     * @return ime osobe
     */
    public String getIme() { return _ime; }

    /**
     * Vraća prezime osobe.
     *
     * @return prezime osobe
     */
    public String getPrezime() { return _prezime; }

    /**
     * Vraća oib osobe.
     *
     * @return oib osobe.
     */
    public String getOib() { return _oib; }

    @Override
    public String toString()
    {
        return String.format("%s %s (%s)", _prezime, _ime, _oib);
    }
}