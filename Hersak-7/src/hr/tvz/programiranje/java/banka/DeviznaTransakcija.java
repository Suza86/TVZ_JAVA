package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Devizna;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.banka.Tecaj;
import hr.tvz.programiranje.java.banka.Tecajnica;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.iznimke.NepodrzanaValutaException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Sadrži podatke o transakciji između tekućeg i deviznog računa.
 *
 * @author Vjekoslav
 */
public class DeviznaTransakcija<T extends TekuciRacun, S extends DevizniRacun> 
    extends Transakcija<T, S> implements Devizna
{
    /**
     * Inicijalizira podatak o polaznom računu, dolaznom računu i iznosu transakcije.
     *
     * @param polazniRacun podatak o polaznom računu
     * @param dolazniRacun podatak o dolaznom računu
     * @param iznos podatak o iznosu transakcije
     */
    public DeviznaTransakcija(T polazniRacun, S dolazniRacun, BigDecimal iznos)
    {
        super(polazniRacun, dolazniRacun, iznos);
    }

    @Override
    public BigDecimal mjenjacnica(BigDecimal polazniIznosKN, Valuta valuta)
    {
        for (Tecaj tecaj : Tecajnica.dohvatiTecajeve())
        {
            if(tecaj.getValuta().compareTo(valuta) == 0)
            {
                BigDecimal iznos = polazniIznosKN.divide(tecaj.getTecajPremaKuni(), 
                    2, RoundingMode.HALF_UP);
                return iznos;
            }
        }

        return polazniIznosKN;
    }

    @Override
    public void provediTransakciju() throws NedozvoljenoStanjeRacunaException
    {
        BigDecimal stanjePolaznog = _polazniRacun.getStanje();
        if (stanjePolaznog.compareTo(_iznos) < 0)
        {
            String poruka = String.format("Račun %s nema dovoljno sredstava (%s) za provođenje transkacije (%s)!", 
                _polazniRacun.getBrojRacuna(), stanjePolaznog, _iznos);
            throw new NedozvoljenoStanjeRacunaException(poruka);
        }

        _polazniRacun.isplatiSRacuna(_iznos);

        Valuta valuta = _dolazniRacun.getValuta();
        BigDecimal konvertiraniIznos = mjenjacnica(_iznos, valuta);
        _dolazniRacun.uplatiNaRacun(konvertiraniIznos);
    }
    
    /**
     * Provjerava postoji li određena valuta.
     *
     * @param valuta podatak o mogućoj valuti
     * @return prava valuta
     * @exception NepodrzanaValutaException ukoliko valuta ne postoji
     */
    public static Valuta provjeriValutu(String valuta) throws NepodrzanaValutaException
    {
        try
        {
            return Valuta.valueOf(valuta);
        }
        catch(IllegalArgumentException ex)
        {
            throw new NepodrzanaValutaException("Valuta " + valuta + " nije podržana!", ex);
        }
    }
}