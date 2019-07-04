package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.osoba.Osoba;

/**
 * Definira akcije potrebne za postavljanje opunomoćenika određenom računu.
 *
 * @author Vjekoslav
 */
public interface ImaOpunomocenika
{
    /**
     * Postavlja opunomećenika određenom računu.
     *
     * @param opunomocenik podatak o opunomoćeniku
     */
    void postaviOpunomocenika(Osoba opunomocenik);
}