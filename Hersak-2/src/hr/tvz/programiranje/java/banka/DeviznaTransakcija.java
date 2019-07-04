package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Devizna;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DeviznaTransakcija extends Transakcija implements Devizna
{
    private static final BigDecimal TECAJ_EUR_KN = new BigDecimal(7.5);

    public DeviznaTransakcija(TekuciRacun polazniRacun, DevizniRacun dolazniRacun, BigDecimal iznos)
    {
        super(polazniRacun, dolazniRacun, iznos);
    }

    @Override
    public BigDecimal mjenjacnica(BigDecimal polazniIznosKN, String valuta)
    {
        if ("EUR".equals(valuta)) return polazniIznosKN.divide(TECAJ_EUR_KN, 2, RoundingMode.HALF_UP);
        else                      return polazniIznosKN;
    }

    @Override
    public void provediTransakciju()
    {
        _polazniRacun.isplatiSRacuna(_iznos);

        String valuta = ((DevizniRacun)_dolazniRacun).getValuta();
        BigDecimal konvertiraniIznos = mjenjacnica(_iznos, valuta);
        _dolazniRacun.uplatiNaRacun(konvertiraniIznos);
    }
}