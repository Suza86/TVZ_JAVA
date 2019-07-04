package hr.tvz.programiranje.java.iznimke;

/**
 * Sadrži podatke o nedozvoljenom stanju računa.
 *
 * @author Vjekoslav
 */
public class NedozvoljenoStanjeRacunaException extends Exception
{
    /**
     * Inicijalizira poruku o nedozvoljenom stanju računa.
     *
     * @param poruka poruka o nedozvoljenom stanju računa
     */
    public NedozvoljenoStanjeRacunaException(String poruka)
    {
        super(poruka);
    }

    /**
     * Inicijalizira poruku o nedozvoljenom stanju računa i podatak o uzroku tog stanja.
     *
     * @param poruka poruka o nedozvoljenom stanju računa
     * @param uzrok podatak o uzroku nedozvoljenog stanja
     */
    public NedozvoljenoStanjeRacunaException(String poruka, Throwable uzrok)
    {
        super(poruka, uzrok);
    }
}