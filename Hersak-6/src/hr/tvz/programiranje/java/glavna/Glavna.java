package hr.tvz.programiranje.java.glavna;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.tvz.programiranje.java.osoba.Osoba;
import hr.tvz.programiranje.java.sortiranje.SortiranjeTransakcija;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final String FILE_NAME = "uneseniPodaci.txt";
    private static final String DATA_DIRECTORY = "data";

    /**
     * Stvara tekući i devizni račun na temelju unesenih podataka, te izvršava
     * transakcije po želji korisnika.
     *
     * @param args ulazni parametri programa
     */
    public static void main(String[] args)
    {
        FileWriter writer = null;
        try
        {
            Path projectPath = Paths.get(".").toRealPath();
            String separator = FileSystems.getDefault().getSeparator();
            String dataPath = projectPath + separator + DATA_DIRECTORY;
            String filePath = dataPath + separator + FILE_NAME;
    
            File dataDirectory = new File(dataPath);
            if (!dataDirectory.exists()) dataDirectory.mkdir();  

            writer = new FileWriter(filePath, true);
        } catch (IOException ex)
        {
            System.err.println(ex);
        }

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
                    racuni.add(unosTekucegRacuna(scanner, writer, i));
                }
                else
                {
                    racuni.add(unosDeviznogRacuna(scanner, writer, i));
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
            if (racuni.get(1) instanceof DevizniRacun)
            {
                transakcija = new DeviznaTransakcija<TekuciRacun, DevizniRacun>((TekuciRacun)racuni.get(0),
                    (DevizniRacun)racuni.get(1), iznos);
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

        String poruka = String.format("Stanje 1. računa nakon transkacije: %.2f KN",
            racuni.get(0).getStanje());
        System.out.println(poruka);
        zapisiUDatoteku(writer, poruka);

        String valutaDrugog = racuni.get(1) instanceof DevizniRacun ?
            ((DevizniRacun)racuni.get(1)).getValuta().toString() : "KN";
        poruka = String.format("Stanje 2. računa nakon transkacije: %.2f %s", 
            racuni.get(1).getStanje(), valutaDrugog);
        System.out.println(poruka);
        zapisiUDatoteku(writer, poruka);

        BigDecimal najveciIznos = ((Transakcija)setTransakcija.first()).getIznos();
        System.out.println("Transakcija s najviše sredstava: " + najveciIznos + " KN");
        logger.info("Transakcija s najviše sredstava: " + najveciIznos + " KN");

        try
        {
            writer.close();
        } catch (IOException ex)
        {
            System.err.println(ex);
        }
    }

    /**
     * Stvara tekući račun na temelju unesenih podataka.
     *
     * @param scanner ulaz sa tipkovnice
     * @param redniBroj redni broj računa koji se upisuje
     * @return novostvoreni tekući račun
     */
    private static TekuciRacun unosTekucegRacuna(Scanner scanner, FileWriter writer, int redniBroj)
    {
        String poruka;

        System.out.print("Unesite ime vlasnika " + redniBroj + ". računa: ");
        String ime = scanner.next();
        poruka = "Uneseno ime vlasnika " + redniBroj + ". računa: " + ime;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        System.out.print("Unesite prezime vlasnika " + redniBroj + ". računa: ");
        String prezime = scanner.next();
        poruka = "Uneseno prezime vlasnika " + redniBroj + ". računa: " + prezime;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        System.out.print("Unesite OIB vlasnika " + redniBroj + ". računa: ");
        String oib = scanner.next();
        poruka = "Unesen OIB vlasnika " + redniBroj + ". računa: " + oib;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        System.out.print("Unesite broj " + redniBroj + ". računa: ");
        String broj = scanner.next();
        poruka = "Unesen broj " + redniBroj + ". računa: " + broj;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

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
        poruka = "Uneseno stanje " + redniBroj + ". računa: " + stanje;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        return new TekuciRacun(new Osoba(ime, prezime, oib), stanje, broj);
    }

    /**
     * Stvara devizni račun na temelju unesenih podataka.
     *
     * @param scanner ulaz sa tipkovnice
     * @param redniBroj redni broj računa koji se upisuje
     * @return novostvoreni devizni račun
     */
    private static DevizniRacun unosDeviznogRacuna(Scanner scanner, FileWriter writer, int redniBroj)
    {
        String poruka;

        System.out.print("Unesite ime vlasnika " + redniBroj + ". računa: ");
        String ime = scanner.next();
        poruka = "Uneseno ime vlasnika " + redniBroj + ". računa: " + ime;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        System.out.print("Unesite prezime vlasnika " + redniBroj + ". računa: ");
        String prezime = scanner.next();
        poruka = "Uneseno prezime vlasnika " + redniBroj + ". računa: " + prezime;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        System.out.print("Unesite OIB vlasnika " + redniBroj + ". računa: ");
        String oib = scanner.next();
        poruka = "Unesen OIB vlasnika " + redniBroj + ". računa: " + oib;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        System.out.print("Unesite IBAN " + redniBroj + ". računa: ");
        String iban = scanner.next();
        poruka = "Unesen IBAN vlasnika " + redniBroj + ". računa: " + iban;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

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
        poruka = "Unesena valuta " + redniBroj + ". računa: " + valuta;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

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
        poruka = "Uneseno stanje " + redniBroj + ". računa: " + stanje;
        logger.info(poruka);
        zapisiUDatoteku(writer, poruka);

        return new DevizniRacun(new Osoba(ime, prezime, oib), stanje, iban, valuta);
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

    /**
     * Zapisuje poruku unutar datoteke uneseniPodaci.txt.
     *
     * @param writer objekt kojim se zapisuje poruka u datoteku
     * @param poruka poruka koja će biti zapisana u datoteku
     */
    private static void zapisiUDatoteku(FileWriter writer, String poruka)
    {
        try
        {
            writer.write(poruka + "\n");
            writer.flush();
        } catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
}