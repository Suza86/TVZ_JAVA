package hr.tvz.programiranje.java.iznimke;

/**
 * Sadrži podatke o korištenju nedozvoljene valute.
 *
 * @author Vjekoslav
 */
public class NepodrzanaValutaException extends Exception
{
    /**
     * Inicijalizira poruku o korištenju nedozvoljene valute.
     *
     * @param poruka poruka o korištenju nedozvoljenu valute
     */
    public NepodrzanaValutaException(String poruka)
    {
        super(poruka);
    }
    
    /**
     * Inicijalizira poruku o korištenju nedozvoljene valute i podatak o uzroku.
     *
     * @param poruka poruka o korištenju nedozvoljene valute
     * @param uzrok podatak o uzroku korištenja nedozvoljene valute
     */
    public NepodrzanaValutaException(String poruka, Throwable uzrok)
    {
        super(poruka, uzrok);
    }
}