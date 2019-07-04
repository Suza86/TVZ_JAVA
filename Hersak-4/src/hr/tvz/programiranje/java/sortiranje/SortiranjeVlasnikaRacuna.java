package hr.tvz.programiranje.java.sortiranje;

import hr.tvz.programiranje.java.osoba.Osoba;
import java.util.Comparator;

/**
 * Implementira logiku za sortiranje vlasnika po oibu (od najvećeg prema najmanjem).
 *
 * @author Vjekoslav
 */
public class SortiranjeVlasnikaRacuna implements Comparator<Osoba>
{
    @Override
    public int compare(Osoba o1, Osoba o2)
    {
        return o2.getOib().compareTo(o1.getOib());
    }
}