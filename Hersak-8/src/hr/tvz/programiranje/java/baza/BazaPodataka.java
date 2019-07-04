package hr.tvz.programiranje.java.baza;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sadrži logiku za komunikaciju sa bazom podataka.
 *
 * @author Vjekoslav
 */
public class BazaPodataka
{
    private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);

    /**
     * Otvara vezu prema bazi podataka.
     *
     * @return vezu prema bazi podataka. 
     */
    private static Connection kreirajVezuSBazom()
    {
        Connection veza = null;
        try
        {
            veza = DriverManager.getConnection("jdbc:h2:~/TVZBankarstvo", "tvz", "tvz");
        } catch (SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spajanja s bazom podataka!");
        }
        return veza;
    }

    /**
     * Zatvara vezu prema bazi podataka.
     *
     * @param veza podatak o vezi koja se zatvara
     */
    private static void odspojiSeOdBaze(Connection veza)
    {
        try
        {
            veza.close();
        } catch (SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod odspajanja s bazom podataka!");
        }
    }

    /**
     * Sprema osobu u bazu podataka.
     *
     * @param osoba podatak o osobi koja se sprema u bazu podataka
     */
    public static void spremiOsobu(Osoba osoba)
    {
        Connection veza = kreirajVezuSBazom();

        try 
        {
            PreparedStatement stmt = veza.prepareStatement("INSERT INTO RAZVOJ.OSOBA (PREZIME, IME, OIB) VALUES(?, ?, ?)");
            stmt.setString(1, osoba.getPrezime());
            stmt.setString(2, osoba.getIme());
            stmt.setString(3, osoba.getOib());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spremanja podataka o osobi!");
        }

        odspojiSeOdBaze(veza);
    }

    /**
     * Dohvaća sve osobe iz baze podataka.
     *
     * @return listu osoba spremljenu u bazi podataka.
     */
    public static List<Osoba> dohvatiSveOsobe()
    {
        Connection veza = kreirajVezuSBazom();
        List<Osoba> listaOsoba = new ArrayList<Osoba>();
        try
        {
            Statement stmt = veza.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.OSOBA");

            while (rs.next())
            {
                int id = rs.getInt("id");
                String prezime = rs.getString("prezime");
                String ime = rs.getString("ime");
                String oib = rs.getString("oib");
                Osoba osoba = new Osoba(id, prezime, ime, oib);
                listaOsoba.add(osoba);
            }
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaćanja svih osoba!");
        }

        odspojiSeOdBaze(veza);
        return listaOsoba;
    }

    /**
     * Dohvaća osobu sa određenim id-em iz baze podataka.
     *
     * @param id podatak o id-u osobe koja se dohvaća iz baze podataka
     * @return osobu sa id-em iz baze podataka
     */
    public static Osoba dohvatiOsobu(Integer id)
    {
        Connection veza = kreirajVezuSBazom();
        Osoba osoba = null;
        try
        {
            PreparedStatement stmt = veza.prepareStatement("SELECT * FROM RAZVOJ.OSOBA WHERE ID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                String prezime = rs.getString("prezime");
                String ime = rs.getString("ime");
                String oib = rs.getString("oib");
                osoba = new Osoba(id, prezime, ime, oib);
            }
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaćanja podataka o osobi!");
        }

        odspojiSeOdBaze(veza);
        return osoba;
    }
     
    /**
     * Sprema račun u bazu podataka.
     *
     * @param racun podatak o računu koji se sprema u bazu podataka
     */
    public static void spremiRacun(Racun racun)
    {
        Connection veza = kreirajVezuSBazom();
        try 
        {
            PreparedStatement stmt = veza.prepareStatement( "INSERT INTO RAZVOJ.RACUN (VLASNIK_ID, STANJE, OZNAKA, VALUTA) VALUES(?, ?, ?, ?)");
            stmt.setInt(1, racun.getVlasnik().getId());
            stmt.setBigDecimal(2, racun.getStanje());

            if(racun instanceof TekuciRacun)
            {
                stmt.setString(3, ((TekuciRacun)racun).getBrojRacuna());
                stmt.setString(4, Valuta.HRK.toString());
            }
            else if (racun instanceof DevizniRacun)
            {
                stmt.setString(3, ((DevizniRacun)racun).getIban());
                stmt.setString(4, ((DevizniRacun)racun).getValuta().toString());
            }

            stmt.executeUpdate();
        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spremanja podataka o računu!");
        }

        odspojiSeOdBaze(veza);
    }

    /**
     * Dohvaća sve račune iz baze podataka.
     *
     * @return listu računa spremljenu u bazi podataka.
     */
    public static List<Racun> dohvatiSveRacune()
    {
        Connection veza = kreirajVezuSBazom();
        List<Racun> listaRacuna = new ArrayList<Racun>();
        try
        {
            Statement stmt = veza.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.RACUN");
            while (rs.next())
            {
                int id = rs.getInt("id");
                int vlasnikId = rs.getInt("vlasnik_id");
                BigDecimal stanje = rs.getBigDecimal("stanje");
                String oznaka = rs.getString("oznaka");
                String valuta = rs.getString("valuta");
                Osoba vlasnik = dohvatiOsobu(vlasnikId);
                if(valuta.equals(Valuta.HRK.toString()))
                {
                    TekuciRacun tekuciRacun = new TekuciRacun(id, vlasnik, stanje, oznaka);

                    listaRacuna.add(tekuciRacun);
                }
                else
                {
                    DevizniRacun devizniRacun = new DevizniRacun(id, vlasnik, stanje, oznaka, Valuta.valueOf(valuta));
                    listaRacuna.add(devizniRacun);
                }
            }
        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaćanja svih računa!");
        }
        
        odspojiSeOdBaze(veza);
        return listaRacuna;
    }
               
    /**
     * Dohvaća račun sa određenim id-em iz baze podataka.
     *
     * @param id podatak o id-u računa koja se dohvaća iz baze podataka
     * @return račun sa id-em iz baze podataka
     */     
    public static Racun dohvatiRacun(Integer id)
    {
        Connection veza = kreirajVezuSBazom();
        Racun racun = null;
        try 
        {
            PreparedStatement stmt = veza.prepareStatement("SELECT * FROM RAZVOJ.RACUN WHERE ID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                int vlasnikId = rs.getInt("vlasnik_id");
                BigDecimal stanje = rs.getBigDecimal("stanje");
                String oznaka = rs.getString("oznaka");
                String valuta = rs.getString("valuta");
                Osoba vlasnik = dohvatiOsobu(vlasnikId);
                    
                if(valuta.equals(Valuta.HRK.toString()))
                {
                    racun = new TekuciRacun(id, vlasnik, stanje, oznaka);
                }
                else
                {
                    racun = new DevizniRacun(id, vlasnik, stanje, oznaka, Valuta.valueOf(valuta));
                }
            }
        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaćanja podataka o računu!");
        }
        
        odspojiSeOdBaze(veza);
        return racun;
    }

    /**
     * Ažurira stanje računa iz baze podataka.
     *
     * @param id podatak o računu čije se stanje ažurira
     */
    public static void azuzirajStanjeRacuna(Racun racun)
    {
        Connection veza = kreirajVezuSBazom();
        try
        {
            PreparedStatement stmt = veza.prepareStatement("UPDATE RAZVOJ.RACUN SET STANJE = ? WHERE ID = ?");
            stmt.setBigDecimal(1, racun.getStanje());
            stmt.setInt(2, racun.getId());
            stmt.executeUpdate();
        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod ažuriranja stanja računa!");
        }

        odspojiSeOdBaze(veza);
    }
       
    /**
     * Sprema transakciju u bazu podataka.
     *
     * @param transakcija podatak o transakciji koja se sprema u bazu podataka
     */     
    public static void spremiTransakciju(Transakcija<?, ?> transakcija)
    {
        Connection veza = kreirajVezuSBazom();
        try
        {
            PreparedStatement stmt = veza.prepareStatement("INSERT INTO RAZVOJ.TRANSAKCIJA(POLAZNI_RACUN_ID, ODLAZNI_RACUN_ID, IZNOS, VALUTA, DATUM) VALUES(?, ?, ?, ?, ?)");
            stmt.setInt(1, transakcija.getPolazniRacun().getId());
            stmt.setInt(2, transakcija.getDolazniRacun().getId());
            stmt.setBigDecimal(3, transakcija.getIznos());

            if(transakcija.getPolazniRacun() instanceof TekuciRacun)
            {
                stmt.setString(4, Valuta.HRK.toString());
            }
            else if (transakcija.getPolazniRacun() instanceof DevizniRacun)
            {
                stmt.setString(4, ((DevizniRacun)transakcija.getPolazniRacun()).getValuta().toString());
            }

            stmt.setDate(5, convertDate(new java.util.Date()));
            stmt.executeUpdate();
        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spremanja podataka o transakciji!");
        }

        BazaPodataka.azuzirajStanjeRacuna(transakcija.getPolazniRacun());
        BazaPodataka.azuzirajStanjeRacuna(transakcija.getDolazniRacun());

        odspojiSeOdBaze(veza);
    }
            
    /**
     * Dohvaća sve transakcije iz baze podataka.
     *
     * @return listu transakcija spremljenu u bazi podataka.
     */
    public static List<Transakcija<?, ?>> dohvatiSveTransakcije()
    {
        Connection veza = kreirajVezuSBazom();
        List<Transakcija<?, ?>> listaRacuna = new ArrayList<Transakcija<?, ?>>();

        try 
        {
            Statement stmt = veza.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.TRANSAKCIJA");
            while (rs.next()) 
            {
                int id = rs.getInt("id");
                int polazniRacunId = rs.getInt("polazni_racun_id");
                int dolazniRacunId = rs.getInt("odlazni_racun_id");
                BigDecimal iznos = rs.getBigDecimal("iznos");
                String valuta = rs.getString("valuta");

                java.sql.Date datumTransakcije = rs.getDate("datum");
                Racun polazniRacun = dohvatiRacun(polazniRacunId);
                Racun dolazniRacun = dohvatiRacun(dolazniRacunId);

                if(polazniRacun instanceof TekuciRacun && dolazniRacun instanceof TekuciRacun)
                {
                    Transakcija<TekuciRacun, TekuciRacun> transakcija = 
                        new Transakcija<>(id, (TekuciRacun)polazniRacun, (TekuciRacun)dolazniRacun, iznos);
                    listaRacuna.add(transakcija);
                }
                else
                {
                    DeviznaTransakcija<TekuciRacun, DevizniRacun> transakcija =
                        new DeviznaTransakcija<>(id, (TekuciRacun)polazniRacun, (DevizniRacun)dolazniRacun, iznos);
                    listaRacuna.add(transakcija);
                }
            }
        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaćanja podataka o transakciji!");
        }

        odspojiSeOdBaze(veza);
        return listaRacuna;
    }

    public static void ispisiDatumSNajviseTransakcija()
    {
        Connection veza = kreirajVezuSBazom();
        HashMap<java.sql.Date, Integer> datumi = new HashMap<>();

        try 
        {
            Statement stmt = veza.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.TRANSAKCIJA");
            while (rs.next()) 
            {
                java.sql.Date datumTransakcije = rs.getDate("datum");
                if (datumi.containsKey(datumTransakcije))
                {
                    datumi.put(datumTransakcije, new Integer(datumi.get(datumTransakcije).intValue() + 1));
                }
                else
                {
                    datumi.put(datumTransakcije, 1);
                }
            }

            if (datumi.size() > 0)
            {
                java.sql.Date datumSNajviseTransakcija = null;
                for (java.sql.Date datum : datumi.keySet())
                {
                    if (datumSNajviseTransakcija == null ||
                        datumi.get(datum).compareTo(datumi.get(datumSNajviseTransakcija)) > 0)
                    {
                        datumSNajviseTransakcija = datum;  
                    }
                }

                JOptionPane.showMessageDialog(null, "Najviše transakcija je obavljeno na datum " 
                    + datumSNajviseTransakcija.toString());
            }

        } catch(SQLException e)
        {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaćanja podataka o transakciji!");
        }

        odspojiSeOdBaze(veza);
    }
                
    /**
     * Pretvara datum u drugi format za zapis u bazu podataka.
     *
     * @param date podatak u datumu koji se pretvara
     * @return novi format datuma
     */
    private static java.sql.Date convertDate(java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }
}