package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;

public class PosebniDevizniRacun extends DevizniRacun
{
    private String _nazivDrzave;

    public PosebniDevizniRacun(Osoba vlasnik, BigDecimal stanje,
                               String iban, Valuta valuta, String nazivDrzave)
    {
        super(vlasnik, stanje, iban, valuta);

        _nazivDrzave = nazivDrzave;
    }

    public String getNazivDrzave() { return _nazivDrzave; }
}