package hr.tvz.programiranje.java.banka;

import hr.tvz.programiranje.java.banka.Tecaj;
import hr.tvz.programiranje.java.banka.Valuta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Sadrži podatke o tečajevima.
 *
 * @author Vjekoslav
 */
public class Tecajnica
{
    /**
     * Vraća tečajeve za trenutni datum.
     *
     * @return tečajeve
     */
    public static List<Tecaj> dohvatiTecajeve()
    {
        List<Tecaj> tecajevi = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String date = dateFormat.format(new Date()); 

        try (InputStream inputStream = new URL("http://www.hnb.hr/tecajn/f" + date + ".dat").openStream())
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                String valuta = line.substring(3, 6);

                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                tokenizer.nextToken();
                tokenizer.nextToken();
                String tecajPremaKuni = tokenizer.nextToken().replace(",", ".");

                tecajevi.add(new Tecaj(Valuta.valueOf(valuta), new BigDecimal(tecajPremaKuni)));
            }
        } catch (IOException ex)
        {
            System.err.println(ex);
        }

        return tecajevi;
    }
}