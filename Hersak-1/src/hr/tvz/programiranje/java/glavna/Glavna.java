package hr.tvz.programiranje.java.glavna;

import hr.tvz.programiranje.java.banka.Racun;
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

        System.out.print("Unesite ime vlasnika prvog računa: ");
        ime = scanner.next();
        System.out.print("Unesite prezime vlasnika prvog računa: ");
        prezime = scanner.next();
        System.out.print("Unesite OIB vlasnika prvog računa: ");
        oib = scanner.next();
        System.out.print("Unesite stanje prvog računa: ");
        stanje = scanner.nextBigDecimal();
        Racun prviRacun = new Racun(new Osoba(ime, prezime, oib), stanje);

        System.out.print("Unesite ime vlasnika drugog računa: ");
        ime = scanner.next();
        System.out.print("Unesite prezime vlasnika drugog računa: ");
        prezime = scanner.next();
        System.out.print("Unesite OIB vlasnika drugog računa: ");
        oib = scanner.next();
        System.out.print("Unesite stanje drugog računa: ");
        stanje = scanner.nextBigDecimal();
        Racun drugiRacun = new Racun(new Osoba(ime, prezime, oib), stanje);

        System.out.print("Unesite iznos transakcije s prvog na drugi račun: ");
        BigDecimal iznos = scanner.nextBigDecimal();

        Transakcija transakcija = new Transakcija(prviRacun, drugiRacun, iznos);
        transakcija.provediTransakciju();

        System.out.println("Stanje prvog računa nakon transkacije: " + prviRacun.getStanje());
        System.out.println("Stanje drugog računa nakon transkacije: " + drugiRacun.getStanje());
    }
}