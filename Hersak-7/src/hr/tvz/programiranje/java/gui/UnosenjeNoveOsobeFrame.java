package hr.tvz.programiranje.java.gui;

import hr.tvz.programiranje.java.gui.GlavniEkran;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UnosenjeNoveOsobeFrame extends JFrame
{
    /**
     * Inicijalizira podatak o listi osoba i stvara ekran za unos nove osobe.
     *
     * @param listaOsoba podatak o listi osoba
     */
    public UnosenjeNoveOsobeFrame(final List<Osoba> listaOsoba)
    {
        super("Unošenje nove osobe");
        this.setSize(370, 130);
        this.setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);

        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0};
        layout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(layout);

        JLabel labelPrezime = new JLabel("Prezime:");
        GridBagConstraints constraintsLabelPrezime = new GridBagConstraints();
        constraintsLabelPrezime.anchor = GridBagConstraints.EAST;
        constraintsLabelPrezime.insets = new Insets(0, 0, 5, 5);
        constraintsLabelPrezime.gridx = 0;
        constraintsLabelPrezime.gridy = 0;
        this.getContentPane().add(labelPrezime, constraintsLabelPrezime);
        
        final JTextField textFieldPrezime = new JTextField();
        GridBagConstraints constraintsTextFieldPrezime = new GridBagConstraints();
        constraintsTextFieldPrezime.insets = new Insets(0, 0, 5, 0);
        constraintsTextFieldPrezime.fill = GridBagConstraints.HORIZONTAL;
        constraintsTextFieldPrezime.gridx = 1;
        constraintsTextFieldPrezime.gridy = 0;

        this.getContentPane().add(textFieldPrezime, constraintsTextFieldPrezime);
        textFieldPrezime.setColumns(10);
        
        JLabel labelIme = new JLabel("Ime:");
        GridBagConstraints constraintsLabelIme = new GridBagConstraints();
        constraintsLabelIme.anchor = GridBagConstraints.EAST;
        constraintsLabelIme.insets = new Insets(0, 0, 5, 5);
        constraintsLabelIme.gridx = 0;
        constraintsLabelIme.gridy = 1;
        this.getContentPane().add(labelIme, constraintsLabelIme);
        
        final JTextField textFieldIme = new JTextField();
        GridBagConstraints constraintsTextFieldIme = new GridBagConstraints();
        constraintsTextFieldIme.insets = new Insets(0, 0, 5, 0);
        constraintsTextFieldIme.fill = GridBagConstraints.HORIZONTAL;
        constraintsTextFieldIme.gridx = 1;
        constraintsTextFieldIme.gridy = 1;
        this.getContentPane().add(textFieldIme, constraintsTextFieldIme);
        textFieldIme.setColumns(10);
        
        JLabel labelOib = new JLabel("OIB:");
        GridBagConstraints constraintsLabelOib = new GridBagConstraints();
        constraintsLabelOib.anchor = GridBagConstraints.EAST;
        constraintsLabelOib.insets = new Insets(0, 0, 5, 5);
        constraintsLabelOib.gridx = 0;
        constraintsLabelOib.gridy = 2;
        this.getContentPane().add(labelOib, constraintsLabelOib);
        
        final JTextField textFieldOib = new JTextField();
        GridBagConstraints constraintsTextFieldOib = new GridBagConstraints();
        constraintsTextFieldOib.insets = new Insets(0, 0, 5, 0);
        constraintsTextFieldOib.fill = GridBagConstraints.HORIZONTAL;
        constraintsTextFieldOib.gridx = 1;
        constraintsTextFieldOib.gridy = 2;
        this.getContentPane().add(textFieldOib, constraintsTextFieldOib);
        textFieldOib.setColumns(10);
        
        JButton buttonSpremi = new JButton("Spremi osobu");
        buttonSpremi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                String prezime = textFieldPrezime.getText();
                String ime = textFieldIme.getText();
                String oib = textFieldOib.getText();

                if (podaciIspravnoUneseni(prezime, ime, oib))
                {
                    Osoba novaOsoba = new Osoba(prezime, ime, oib);
                    listaOsoba.add(novaOsoba);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Uspješno ste unijeli osobu: " 
                        + prezime + " " + ime);
                    GlavniEkran.logger.info("Uspješno unesena nova osoba: " + novaOsoba);    
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Neispravno uneseni podaci.");
                }
            }
        });

        GridBagConstraints constraintsButtonSpremi = new GridBagConstraints();
        constraintsButtonSpremi.gridx = 1;
        constraintsButtonSpremi.gridy = 3;
        this.getContentPane().add(buttonSpremi, constraintsButtonSpremi);
    }

    /**
     * Provjerava ispravnost unesenih podataka.
     *
     * @param prezime podatak o prezimenu osobe
     * @param ime podatak o imenu osobe
     * @param oib podatak o oibu osobe
     * @return true ako su podaci ispravno uneseni, inače false
     */
    private Boolean podaciIspravnoUneseni(String prezime, String ime, String oib)
    {
        return prezime.length() > 0 && ime.length() > 0 && oib.length() > 0;
    }

    /**
     * Otvara novostvoreni ekran za unos nove osobe.
     */
    public void prikaziEkran()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try 
                {
                    setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}