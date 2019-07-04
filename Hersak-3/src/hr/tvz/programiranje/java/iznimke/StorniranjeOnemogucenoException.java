package hr.tvz.programiranje.java.iznimke;

public class StorniranjeOnemogucenoException extends Exception
{
    public StorniranjeOnemogucenoException(String poruka)
    {
        super(poruka);
    }

    public StorniranjeOnemogucenoException(String poruka, Throwable uzrok)
    {
        super(poruka, uzrok);
    }
}