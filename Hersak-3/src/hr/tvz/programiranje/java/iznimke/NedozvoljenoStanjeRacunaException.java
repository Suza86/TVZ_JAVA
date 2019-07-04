package hr.tvz.programiranje.java.iznimke;

public class NedozvoljenoStanjeRacunaException extends Exception
{
    public NedozvoljenoStanjeRacunaException(String poruka)
    {
        super(poruka);
    }

    public NedozvoljenoStanjeRacunaException(String poruka, Throwable uzrok)
    {
        super(poruka, uzrok);
    }
}