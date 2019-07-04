package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Tecaj;
import hr.tvz.programiranje.java.banka.Valuta;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Sadrži podatke o tečajevima.
 *
 * @author Vjekoslav
 */
public class Tecajnica
{
    /**
     * Vraća tečajeve.
     *
     * @return tečajeve
     */
    public static List<Tecaj> dohvatiTecajeve()
    {
        List<Tecaj> tecajevi = new ArrayList<>();
        tecajevi.add(new Tecaj(Valuta.EUR, new BigDecimal(7.544488)));
        tecajevi.add(new Tecaj(Valuta.USD, new BigDecimal(5.801221)));
        tecajevi.add(new Tecaj(Valuta.GBP, new BigDecimal(8.930503)));

        return tecajevi;
    }
}