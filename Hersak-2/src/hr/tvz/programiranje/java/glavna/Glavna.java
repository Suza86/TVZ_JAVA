package hr.tvz.programiranje.java.glavna;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;
import java.util.Scanner;

public class Glavna
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String ime, prezime, oib;
        BigDecimal stanje;

        System.out.print("Unesite ime vlasnika tekućeg računa: ");
        ime = scanner.next();
        System.out.print("Unesite prezime vlasnika tekućeg računa: ");
        prezime = scanner.next();
        System.out.print("Unesite OIB vlasnika tekućeg računa: ");
        oib = scanner.next();
        System.out.print("Unesite broj tekućeg računa: ");
        String broj = scanner.next();
        System.out.print("Unesite stanje tekućeg računa (KN): ");
        stanje = scanner.nextBigDecimal();
        TekuciRacun tekuciRacun = new TekuciRacun(new Osoba(ime, prezime, oib), stanje, broj);

        System.out.print("Unesite ime opunomoćenog korisnika tekućeg računa: ");
        ime = scanner.next();
        System.out.print("Unesite prezime opunomoćenog korisnika tekućeg računa: ");
        prezime = scanner.next();
        System.out.print("Unesite OIB opunomoćenog korisnika tekućeg računa: ");
        oib = scanner.next();
        tekuciRacun.postaviOpunomocenika(new Osoba(ime, prezime, oib));

        System.out.print("Unesite ime vlasnika deviznog računa: ");
        ime = scanner.next();
        System.out.print("Unesite prezime vlasnika deviznog računa: ");
        prezime = scanner.next();
        System.out.print("Unesite OIB vlasnika deviznog računa: ");
        oib = scanner.next();
        System.out.print("Unesite IBAN deviznog računa: ");
        String iban = scanner.next();
        System.out.print("Unesite valutu deviznog računa: ");
        String valuta = scanner.next();
        System.out.print("Unesite stanje deviznog računa: ");
        stanje = scanner.nextBigDecimal();
        DevizniRacun devizniRacun = new DevizniRacun(new Osoba(ime, prezime, oib), stanje, iban, valuta);

        System.out.print("Unesite iznos transakcije (u KN) s prvog na drugi račun: ");
        BigDecimal iznos = scanner.nextBigDecimal();

        Transakcija transakcija = new DeviznaTransakcija(tekuciRacun, devizniRacun, iznos);
        transakcija.provediTransakciju();

        System.out.printf("Stanje tekućeg računa nakon transkacije: %.2f KN\n", tekuciRacun.getStanje());
        System.out.printf("Stanje deviznog računa nakon transkacije: %.2f %s\n", devizniRacun.getStanje(), 
            devizniRacun.getValuta());
    }
}