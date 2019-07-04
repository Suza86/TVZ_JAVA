package hr.tvz.programiranje.java.glavna;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.tvz.programiranje.java.iznimke.StorniranjeOnemogucenoException;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Glavna
{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String ime, prezime, oib;
        BigDecimal stanje = null;
        boolean error;

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

        TekuciRacun tekuciRacun = new TekuciRacun(new Osoba(ime, prezime, oib), stanje, broj);

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

        String valuta = null;
        do
        {
            error = false;
            System.out.print("Unesite valutu deviznog računa: ");
            try
            {
                valuta = scanner.next();
                DeviznaTransakcija.provjeriValutu(valuta);
            }
            catch(NepodrzanaValutaException ex)
            {
                error = true;
                logger.error("Unesena neispravna valuta deviznog računa: " + valuta, ex);
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

        DevizniRacun devizniRacun = new DevizniRacun(new Osoba(ime, prezime, oib), stanje, iban, valuta);

        // ---------------------------------------------------------------------------------- //

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
        
        System.out.print("Želite li stornirati transakciju? (da/ne)");
        String storniranje = scanner.next();

        if (storniranje.equals("da"))
        {
            try
            {
                transakcija.stornirajTransakciju();
            }
            catch(StorniranjeOnemogucenoException ex)
            {
                System.out.println(ex.getMessage());
                logger.error(ex.getMessage());
            }
        }
    }
}