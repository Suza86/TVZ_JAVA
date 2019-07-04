package hr.tvz.programiranje.java.glavna;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.PosebniDevizniRacun;
import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.tvz.programiranje.java.osoba.Osoba;
import hr.tvz.programiranje.java.sortiranje.SortiranjeTransakcija;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Obavlja postupak izrade tekućeg i deviznog računa te izvršavanje transakcija
 * sa jednog računa na drugi.
 *
 * @author Vjekoslav
 */
public class Glavna
{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    /**
     * Stvara tekući i devizni račun na temelju unesenih podataka, te izvršava
     * transakcije po želji korisnika.
     *
     * @param args ulazni parametri programa
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Racun> racuni = new ArrayList<>();
        boolean pogreskaPriUnosu = false;

        do
        {
            if (pogreskaPriUnosu)
            {
                System.out.println("Oba računa moraju biti tekući ili prvi tekući, " 
                    + "a drugi devizni! Morate ponovno unijeti oba računa.");
                racuni.clear();
            }

            for (int i = 1; i <= 2; i++)
            {
                System.out.print("Unesite vrstu računa (T - tekući; ostalo - devizni): ");
                String vrstaRacuna = scanner.next();
                if (vrstaRacuna.toLowerCase().equals("t"))
                {
                    racuni.add(unosTekucegRacuna(scanner, i));
                }
                else
                {
                    racuni.add(unosDeviznogRacuna(scanner, i));
                }
            }

            pogreskaPriUnosu = kombinacijaRacunaNeispravna(racuni);

        } while (pogreskaPriUnosu);

        SortedSet setTransakcija = new TreeSet(new SortiranjeTransakcija());

        do
        {
            System.out.print("Unesite iznos transakcije (u KN) s prvog na drugi račun: ");
            BigDecimal iznos = scanner.nextBigDecimal();

            Transakcija transakcija = null;
            if (racuni.get(1) instanceof PosebniDevizniRacun)
            {
                transakcija = new DeviznaTransakcija<TekuciRacun, PosebniDevizniRacun>((TekuciRacun)racuni.get(0),
                    (PosebniDevizniRacun)racuni.get(1), iznos);
            }
            else
            {
                transakcija = new Transakcija<TekuciRacun, TekuciRacun>((TekuciRacun)racuni.get(0), 
                    (TekuciRacun)racuni.get(1), iznos);
            }

            try
            {
                transakcija.provediTransakciju();
            }
            catch(NedozvoljenoStanjeRacunaException ex)
            {
                logger.error(ex.getMessage());
            }            

            setTransakcija.add(transakcija);

            System.out.print("Transakcija je provedena. Želite li izvršiti novu transakciju? (da/ne)");

        } while (scanner.next().toLowerCase().equals("da"));
        
        System.out.printf("Stanje 1. računa nakon transkacije: %.2f KN\n", racuni.get(0).getStanje());
        String valutaDrugog = racuni.get(1) instanceof DevizniRacun ?
            ((DevizniRacun)racuni.get(1)).getValuta().toString() : "KN";
        System.out.printf("Stanje 2. računa nakon transkacije: %.2f %s\n", 
            racuni.get(1).getStanje(), valutaDrugog);

        BigDecimal najveciIznos = ((Transakcija)setTransakcija.first()).getIznos();
        System.out.println("Transakcija s najviše sredstava: " + najveciIznos + " KN");
        logger.info("Transakcija s najviše sredstava: " + najveciIznos + " KN");
    }

    /**
     * Stvara tekući račun na temelju unesenih podataka.
     *
     * @param scanner ulaz sa tipkovnice
     * @param redniBroj redni broj računa koji se upisuje
     * @return novostvoreni tekući račun
     */
    private static TekuciRacun unosTekucegRacuna(Scanner scanner, int redniBroj)
    {
        System.out.print("Unesite ime vlasnika " + redniBroj + ". računa: ");
        String ime = scanner.next();
        logger.info("Uneseno ime vlasnika " + redniBroj + ". računa: " + ime);

        System.out.print("Unesite prezime vlasnika " + redniBroj + ". računa: ");
        String prezime = scanner.next();
        logger.info("Uneseno prezime vlasnika " + redniBroj + ". računa: " + prezime);

        System.out.print("Unesite OIB vlasnika " + redniBroj + ". računa: ");
        String oib = scanner.next();
        logger.info("Unesen OIB vlasnika " + redniBroj + ". računa: " + oib);

        System.out.print("Unesite broj " + redniBroj + ". računa: ");
        String broj = scanner.next();
        logger.info("Unesen broj " + redniBroj + ". računa: " + broj);

        BigDecimal stanje = null;
        boolean error;
        do
        {
            error = false;
            System.out.print("Unesite stanje " + redniBroj + ". računa (KN): ");
            try
            {
                stanje = scanner.nextBigDecimal();
            }
            catch(InputMismatchException ex)
            {
                error = true;
                logger.error("Unesen neispravan iznos za stanje " + redniBroj + ". računa: " + stanje, ex);
                scanner.nextLine();
            }
        } while(error);
        logger.info("Uneseno stanje " + redniBroj + ". računa: " + stanje);

        return new TekuciRacun(new Osoba(ime, prezime, oib), stanje, broj);
    }

    /**
     * Stvara devizni račun na temelju unesenih podataka.
     *
     * @param scanner ulaz sa tipkovnice
     * @param redniBroj redni broj računa koji se upisuje
     * @return novostvoreni devizni račun
     */
    private static DevizniRacun unosDeviznogRacuna(Scanner scanner, int redniBroj)
    {
        System.out.print("Unesite ime vlasnika " + redniBroj + ". računa: ");
        String ime = scanner.next();
        logger.info("Uneseno ime vlasnika " + redniBroj + ". računa: " + ime);

        System.out.print("Unesite prezime vlasnika " + redniBroj + ". računa: ");
        String prezime = scanner.next();
        logger.info("Uneseno prezime vlasnika " + redniBroj + ". računa: " + prezime);

        System.out.print("Unesite OIB vlasnika " + redniBroj + ". računa: ");
        String oib = scanner.next();
        logger.info("Unesen OIB vlasnika " + redniBroj + ". računa: " + oib);

        System.out.print("Unesite IBAN " + redniBroj + ". računa: ");
        String iban = scanner.next();
        logger.info("Unesen IBAN vlasnika " + redniBroj + ". računa: " + iban);

        String imeValute = null;
        Valuta valuta = null;
        boolean error;
        do
        {
            error = false;
            System.out.print("Unesite valutu " + redniBroj + ". računa: ");
            try
            {
                imeValute = scanner.next();
                valuta = DeviznaTransakcija.provjeriValutu(imeValute);
            }
            catch(NepodrzanaValutaException ex)
            {
                error = true;
                logger.error("Unesena neispravna valuta " + redniBroj + ". računa: " + imeValute, ex);
            }
        } while(error); 
        logger.info("Unesena valuta " + redniBroj + ". računa: " + valuta);

        BigDecimal stanje = null;
        do
        {
            error = false;
            System.out.print("Unesite stanje " + redniBroj + ". računa: ");
            try
            {
                stanje = scanner.nextBigDecimal();
            }
            catch(InputMismatchException ex)
            {
                error = true;
                logger.error("Unesen neispravan iznos za stanje " + redniBroj + ". računa: " + stanje, ex);
                scanner.nextLine();
            }
        } while(error);
        logger.info("Uneseno stanje " + redniBroj + ". računa: " + stanje);

        System.out.print("Unesite naziv države " + redniBroj + ". računa: ");
        String nazivDrzave = scanner.next();
        logger.info("Unesen naziv države " + redniBroj + ". računa: " + nazivDrzave);

        return new PosebniDevizniRacun(new Osoba(ime, prezime, oib), stanje, iban, valuta, nazivDrzave);
    }

    /**
     * Provjerava da li je upisana prava kombinacija računa.
     *
     * @param racuni lista računa koja predstavlja kombinaciju
     * @return true ako je kombinacija neispravna, inače false
     */
    private static boolean kombinacijaRacunaNeispravna(ArrayList<Racun> racuni)
    {
        return !(racuni.get(0) instanceof TekuciRacun);
    }
}