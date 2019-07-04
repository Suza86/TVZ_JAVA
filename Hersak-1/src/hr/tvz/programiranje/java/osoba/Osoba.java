package hr.tvz.programiranje.java.osoba;

public class Osoba
{
    private String _ime;
    private String _prezime;
    private String _oib;
    private String _drzavaStanovanja;

    public Osoba(String ime, String prezime, String oib)
    {
        _ime = ime;
        _prezime = prezime;
        _oib = oib;
    }

    public String getIme() { return _ime; }

    public String getPrezime() { return _prezime; }

    public String getOib() { return _oib; }

    public String getDrzavaStanovanja() { return _drzavaStanovanja; }
}