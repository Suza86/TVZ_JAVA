package hr.tvz.programiranje.java.glavna;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.tvz.programiranje.java.osoba.Osoba;
import hr.tvz.programiranje.java.sortiranje.SortiranjeTransakcija;
import hr.tvz.programiranje.java.sortiranje.SortiranjeVlasnikaRacuna;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
        String ime, prezime, oib;
        BigDecimal stanje = null;
        boolean error;

        List<Osoba> vlasniciRacuna = new ArrayList<>();

        System.out.print("Unesite ime vlasnika tekućeg računa: ");
        ime = scanner.next();
        logger.info("Uneseno ime vlasnika tekućeg računa: " + ime);

        System.out.print("Unesite prezime vlasnika tekućeg računa: ");
        prezime = scanner.next();
        logger.info("Uneseno prezime vlasnika tekućeg računa: " + prezime);

        System.out.print("Unesite OIB vlasnika tekućeg računa: ");
        oib = scanner.next();
        logger.info("Unesen OIB vlasnika tekućeg računa: " + oib);

        System.out.print("Unesite broj tekućeg računa: ");
        String broj = scanner.next();
        logger.info("Unesen broj tekućeg računa: " + broj);

        do
        {
            error = false;
            System.out.print("Unesite stanje tekućeg računa (KN): ");
            try
            {
                stanje = scanner.nextBigDecimal();
            }
            catch(InputMismatchException ex)
            {
                error = true;
                logger.error("Unesen neispravan iznos za stanje tekućeg računa: " + stanje, ex);
                scanner.nextLine();
            }
        } while(error);
        logger.info("Uneseno stanje tekućeg računa: " + stanje);

        Osoba vlasnikTekuceg = new Osoba(ime, prezime, oib);
        TekuciRacun tekuciRacun = new TekuciRacun(vlasnikTekuceg, stanje, broj);
        vlasniciRacuna.add(vlasnikTekuceg);

        // ---------------------------------------------------------------------------------- //

        System.out.print("Unesite ime opunomoćenog korisnika tekućeg računa: ");
        ime = scanner.next();
        logger.info("Uneseno ime opunomoćenog korisnika tekućeg računa: " + ime);

        System.out.print("Unesite prezime opunomoćenog korisnika tekućeg računa: ");
        prezime = scanner.next();
        logger.info("Uneseno prezime opunomoćenog korisnika tekućeg računa: " + prezime);

        System.out.print("Unesite OIB opunomoćenog korisnika tekućeg računa: ");
        oib = scanner.next();
        logger.info("Unesen OIB opunomoćenog korisnika tekućeg računa: " + oib);

        tekuciRacun.postaviOpunomocenika(new Osoba(ime, prezime, oib));

        // ---------------------------------------------------------------------------------- //

        System.out.print("Unesite ime vlasnika deviznog računa: ");
        ime = scanner.next();
        logger.info("Uneseno ime vlasnika deviznog računa: " + ime);

        System.out.print("Unesite prezime vlasnika deviznog računa: ");
        prezime = scanner.next();
        logger.info("Uneseno prezime vlasnika deviznog računa: " + prezime);

        System.out.print("Unesite OIB vlasnika deviznog računa: ");
        oib = scanner.next();
        logger.info("Unesen OIB vlasnika deviznog računa: " + oib);

        System.out.print("Unesite IBAN deviznog računa: ");
        String iban = scanner.next();
        logger.info("Unesen IBAN vlasnika deviznog računa: " + iban);

        String imeValute = null;
        Valuta valuta = null;
        do
        {
            error = false;
            System.out.print("Unesite valutu deviznog računa: ");
            try
            {
                imeValute = scanner.next();
                valuta = DeviznaTransakcija.provjeriValutu(imeValute);
            }
            catch(NepodrzanaValutaException ex)
            {
                error = true;
                logger.error("Unesena neispravna valuta deviznog računa: " + imeValute, ex);
            }
        } while(error); 
        logger.info("Unesena valuta deviznog računa: " + valuta);

        do
        {
            error = false;
            System.out.print("Unesite stanje deviznog računa: ");
            try
            {
                stanje = scanner.nextBigDecimal();
            }
            catch(InputMismatchException ex)
            {
                error = true;
                logger.error("Unesen neispravan iznos za stanje deviznog računa: " + stanje, ex);
                scanner.nextLine();
            }
        } while(error);
        logger.info("Uneseno stanje deviznog računa: " + stanje);

        Osoba vlasnikDeviznog = new Osoba(ime, prezime, oib);
        DevizniRacun devizniRacun = new DevizniRacun(vlasnikDeviznog, stanje, iban, valuta);
        vlasniciRacuna.add(vlasnikDeviznog);

        // ---------------------------------------------------------------------------------- //

        SortedSet<Transakcija> setTransakcija = new TreeSet<Transakcija>(new SortiranjeTransakcija());

        do
        {
            System.out.print("Unesite iznos transakcije (u KN) s prvog na drugi račun: ");
            BigDecimal iznos = scanner.nextBigDecimal();

            DeviznaTransakcija transakcija = new DeviznaTransakcija(tekuciRacun, devizniRacun, iznos);
            try
            {
                transakcija.provediTransakciju();
            }
            catch(NedozvoljenoStanjeRacunaException ex)
            {
                logger.error(ex.getMessage());
            }

            System.out.printf("Stanje tekućeg računa nakon transkacije: %.2f KN\n", tekuciRacun.getStanje());
            System.out.printf("Stanje deviznog računa nakon transkacije: %.2f %s\n", devizniRacun.getStanje(), 
                devizniRacun.getValuta());

            setTransakcija.add(transakcija);

            System.out.print("Želite li izvršiti novu transakciju? (da/ne)");

        } while (scanner.next().toLowerCase().equals("da"));
        
        BigDecimal najveciIznos = setTransakcija.first().getIznos();
        System.out.println("Transakcija s najviše sredstava: " + najveciIznos + " KN");
        logger.info("Transakcija s najviše sredstava: " + najveciIznos + " KN");

        Collections.sort(vlasniciRacuna, new SortiranjeVlasnikaRacuna());
        System.out.println("Vlasnici računa sortirani po oibu:");
        for (Osoba vlasnikRacuna : vlasniciRacuna)
        {
            System.out.println(vlasnikRacuna);
        }
    }
}