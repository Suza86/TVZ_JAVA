package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Valuta;
import java.math.BigDecimal;

/**
 * Definira akcije potrebne za transakcije koje rade sa deviznim računima.
 *
 * @author Vjekoslav
 */
public interface Devizna
{
    /**
     * Vraća konvertirani iznos u određenoj valuti.
     *
     * @param polazniIznosKN podatak o polaznom iznosu u kunama
     * @param valuta podatak o valuti u koju se želi konvertirati iznos
     * @return konvertirani iznos u određenoj valuti
     */
    BigDecimal mjenjacnica(BigDecimal polazniIznosKN, Valuta valuta);
}