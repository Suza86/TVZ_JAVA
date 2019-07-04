package hr.tvz.programiranje.java.banka;

import java.math.BigDecimal;

public interface Devizna
{
    BigDecimal mjenjacnica(BigDecimal polazniIznosKN, String valuta);
}