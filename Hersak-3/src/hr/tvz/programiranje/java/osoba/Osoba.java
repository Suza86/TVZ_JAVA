package hr.tvz.programiranje.java.osoba;

public class Osoba
{
    private String _ime;
    private String _prezime;
    private String _oib;

    public Osoba(String ime, String prezime, String oib)
    {
        _ime = ime;
        _prezime = prezime;
        _oib = oib;
    }

    public String getIme() { return _ime; }

    public String getPrezime() { return _prezime; }

    public String getOib() { return _oib; }
}