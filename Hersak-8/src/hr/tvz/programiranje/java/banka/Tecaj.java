package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Valuta;
import java.math.BigDecimal;

/**
 * Sadrži podatke o valuti i tečaju prema kuni.
 *
 * @author Vjekoslav
 */
public class Tecaj
{
    private Valuta _valuta;
    private BigDecimal _tecajPremaKuni;

    /**
     * Inicijalizira podatak o valuti i tečaju prema kuni.
     *
     * @param valuta podatak o valuti
     * @param tecajPremaKuni podatak o tečaju prema kuni
     */
    public Tecaj(Valuta valuta, BigDecimal tecajPremaKuni)
    {
        _valuta = valuta;
        _tecajPremaKuni = tecajPremaKuni;
    }

    /**
     * Vraća valutu tečaja.
     *
     * @return valuta tečaja
     */
    public Valuta getValuta() { return _valuta; }

    /**
     * Vraća tečaj prema kuni.
     *
     * @return tečaj prema kuni
     */
    public BigDecimal getTecajPremaKuni() { return _tecajPremaKuni; }
}