package hr.tvz.programiranje.java.osoba;

/**
 * Sadrži osobne podatke neke osobe koji su potrebni za svaki račun.
 *
 * @author Vjekoslav
 */
public class Osoba
{
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
}