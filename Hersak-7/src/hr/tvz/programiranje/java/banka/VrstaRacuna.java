package hr.tvz.programiranje.java.banka;

/**
 * Sadrži podatke o mogućim vrstama računa.
 *
 * @author Vjekoslav
 */
public enum VrstaRacuna
{
    TEKUCI("Tekući"), DEVIZNI("Devizni");

    private String _vrstaRacuna;

    /**
     * Inicijalizira podatak o vrsti računa.
     *
     * @param vrstaRacuna podatak o vrsti računa
     */
    VrstaRacuna(String vrstaRacuna)
    {
        _vrstaRacuna = vrstaRacuna;       
    }

    @Override
    public String toString()
    {
        return _vrstaRacuna;
    }
}