package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Devizna;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.tvz.programiranje.java.iznimke.StorniranjeOnemogucenoException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DeviznaTransakcija extends Transakcija implements Devizna
{
    private static final BigDecimal TECAJ_EUR_KN = new BigDecimal(7.5);
    private static final String VALUTA_EUR = "EUR";

    private long _pocetnoVrijeme;
    private long _zavrsnoVrijeme;

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
    public void provediTransakciju() throws NedozvoljenoStanjeRacunaException
    {
        BigDecimal stanjePolaznog = _polazniRacun.getStanje();
        if (stanjePolaznog.compareTo(_iznos) < 0)
        {
            String poruka = String.format("Račun %s nema dovoljno sredstava (%s) za provođenje transkacije (%s)!", 
                ((TekuciRacun)_polazniRacun).getBrojRacuna(), stanjePolaznog, _iznos);
            throw new NedozvoljenoStanjeRacunaException(poruka);
        }

        _polazniRacun.isplatiSRacuna(_iznos);

        String valuta = ((DevizniRacun)_dolazniRacun).getValuta();
        BigDecimal konvertiraniIznos = mjenjacnica(_iznos, valuta);
        _dolazniRacun.uplatiNaRacun(konvertiraniIznos);

        _pocetnoVrijeme = System.currentTimeMillis();
    }

    public void stornirajTransakciju() throws StorniranjeOnemogucenoException
    {
        long zavrsnoVrijeme = System.currentTimeMillis();
        long prodenoVrijemeUSekundama = (zavrsnoVrijeme - _pocetnoVrijeme) / 1000;

        if (prodenoVrijemeUSekundama > 10)
        {
            throw new StorniranjeOnemogucenoException("Storniranje onemogućeno. Prošlo 10 sekundi.");
        }
        else
        {
            //TODO: Storniranje računa.
        }
    }

    public static void provjeriValutu(String valuta) throws NepodrzanaValutaException
    {
        if (!valuta.equals(VALUTA_EUR))
        {
            throw new NepodrzanaValutaException("Valuta " + valuta + " nije podrzana.");
        }
    }
}