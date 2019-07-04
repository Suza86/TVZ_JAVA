package hr.tvz.programiranje.java.sortiranje;

import hr.tvz.programiranje.java.banka.Transakcija;
import java.util.Comparator;

/**
 * Implementira logiku za sortiranje transakcija po veličini iznosa (od većeg prema manjem).
 *
 * @author Vjekoslav
 */
public class SortiranjeTransakcija implements Comparator<Transakcija>
{
    @Override
    public int compare(Transakcija t1, Transakcija t2)
    {
        return t2.getIznos().compareTo(t1.getIznos());
    }
}