package hr.tvz.programiranje.java.gui;

import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Valuta;
import hr.tvz.programiranje.java.banka.VrstaRacuna;
import hr.tvz.programiranje.java.gui.GlavniEkran;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UnosenjeNovogRacunaFrame extends JFrame
{
    /**
     * Inicijalizira podatak o listi osoba i listi računa, te stvara ekran za unos novog računa.
     *
     * @param listaOsoba podatak o listi osoba
     * @param listaRacuna podatak o listi računa
     */
    public UnosenjeNovogRacunaFrame(final List<Osoba> listaOsoba, final List<Racun> listaRacuna)
    {
        super("Unošenje novog računa");
        this.setSize(450, 170);
        this.setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);

        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0, 0};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        layout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(layout);
        
        JLabel labelVrstaRacuna = new JLabel("Vrsta računa:");
        GridBagConstraints constraintsLabelVrstaRacuna = new GridBagConstraints();
        constraintsLabelVrstaRacuna.anchor = GridBagConstraints.EAST;
        constraintsLabelVrstaRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsLabelVrstaRacuna.gridx = 0;
        constraintsLabelVrstaRacuna.gridy = 0;
        this.getContentPane().add(labelVrstaRacuna, constraintsLabelVrstaRacuna);
        
        final JComboBox<VrstaRacuna> comboBoxVrstaRacuna = new JComboBox<>();
        GridBagConstraints constraintsComboBoxVrstaRacuna = new GridBagConstraints();
        constraintsComboBoxVrstaRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsComboBoxVrstaRacuna.fill = GridBagConstraints.HORIZONTAL;
        constraintsComboBoxVrstaRacuna.gridx = 1;
        constraintsComboBoxVrstaRacuna.gridy = 0;
        this.getContentPane().add(comboBoxVrstaRacuna, constraintsComboBoxVrstaRacuna);

        for(VrstaRacuna vrstaRacuna : VrstaRacuna.values()) comboBoxVrstaRacuna.addItem(vrstaRacuna);
        
        JLabel labelVlasnikRacuna = new JLabel("Vlasnik računa:");
        GridBagConstraints constraintsLabelVlasnikRacuna = new GridBagConstraints();
        constraintsLabelVlasnikRacuna.anchor = GridBagConstraints.EAST;
        constraintsLabelVlasnikRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsLabelVlasnikRacuna.gridx = 0;
        constraintsLabelVlasnikRacuna.gridy = 1;
        this.getContentPane().add(labelVlasnikRacuna, constraintsLabelVlasnikRacuna);
        
        final JComboBox<Osoba> comboBoxVlasnikRacuna = new JComboBox<>();
        GridBagConstraints constraintsComboBoxVlasnikRacuna = new GridBagConstraints();
        constraintsComboBoxVlasnikRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsComboBoxVlasnikRacuna.fill = GridBagConstraints.HORIZONTAL;
        constraintsComboBoxVlasnikRacuna.gridx = 1;
        constraintsComboBoxVlasnikRacuna.gridy = 1;
        this.getContentPane().add(comboBoxVlasnikRacuna, constraintsComboBoxVlasnikRacuna);

        for(Osoba osoba : listaOsoba) comboBoxVlasnikRacuna.addItem(osoba);
        
        final JLabel labelStanjeRacuna = new JLabel("Stanje računa:");
        GridBagConstraints constraintsLabelStanjeRacuna = new GridBagConstraints();
        constraintsLabelStanjeRacuna.anchor = GridBagConstraints.EAST;
        constraintsLabelStanjeRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsLabelStanjeRacuna.gridx = 0;
        constraintsLabelStanjeRacuna.gridy = 2;
        this.getContentPane().add(labelStanjeRacuna, constraintsLabelStanjeRacuna);
        
        final JTextField textFieldStanjeRacuna = new JTextField();
        GridBagConstraints constraintsTextFieldStanjeRacuna = new GridBagConstraints();
        constraintsTextFieldStanjeRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsTextFieldStanjeRacuna.fill = GridBagConstraints.HORIZONTAL;
        constraintsTextFieldStanjeRacuna.gridx = 1;
        constraintsTextFieldStanjeRacuna.gridy = 2;
        this.getContentPane().add(textFieldStanjeRacuna, constraintsTextFieldStanjeRacuna);
        textFieldStanjeRacuna.setColumns(10);
        
        final JComboBox<Valuta> comboBoxValutaRacuna = new JComboBox<>();
        GridBagConstraints constraintsComboBoxStanjeRacuna = new GridBagConstraints();
        constraintsComboBoxStanjeRacuna.insets = new Insets(0, 0, 5, 0);
        constraintsComboBoxStanjeRacuna.fill = GridBagConstraints.HORIZONTAL;
        constraintsComboBoxStanjeRacuna.gridx = 2;
        constraintsComboBoxStanjeRacuna.gridy = 2;
        this.getContentPane().add(comboBoxValutaRacuna, constraintsComboBoxStanjeRacuna);

        for(Valuta valuta : Valuta.values()) comboBoxValutaRacuna.addItem(valuta);
        comboBoxValutaRacuna.setVisible(false);
        
        final JLabel labelBrojRacunaIban = new JLabel("Broj računa:");
        GridBagConstraints constraintsLabelBrojRacuna = new GridBagConstraints();
        constraintsLabelBrojRacuna.anchor = GridBagConstraints.EAST;
        constraintsLabelBrojRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsLabelBrojRacuna.gridx = 0;
        constraintsLabelBrojRacuna.gridy = 3;
        this.getContentPane().add(labelBrojRacunaIban, constraintsLabelBrojRacuna);
        
        final JTextField textFieldBrojRacunaIban = new JTextField();
        GridBagConstraints constraintsTextFieldBrojRacuna = new GridBagConstraints();
        constraintsTextFieldBrojRacuna.insets = new Insets(0, 0, 5, 5);
        constraintsTextFieldBrojRacuna.fill = GridBagConstraints.HORIZONTAL;
        constraintsTextFieldBrojRacuna.gridx = 1;
        constraintsTextFieldBrojRacuna.gridy = 3;
        this.getContentPane().add(textFieldBrojRacunaIban, constraintsTextFieldBrojRacuna);
        textFieldBrojRacunaIban.setColumns(10);
        
        comboBoxVrstaRacuna.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                VrstaRacuna odabranaVrstaRacuna = (VrstaRacuna) comboBoxVrstaRacuna.getSelectedItem();
                if(odabranaVrstaRacuna.equals(VrstaRacuna.DEVIZNI))
                {
                    comboBoxValutaRacuna.setVisible(true);
                    labelBrojRacunaIban.setText("IBAN:");
                    labelStanjeRacuna.setText("Stanje računa:");
                }
                else
                {
                    comboBoxValutaRacuna.setVisible(false);
                    labelBrojRacunaIban.setText("Broj računa:");
                    labelStanjeRacuna.setText("Stanje računa (KN):");
                }
            }
        });

        JButton buttonSpremi = new JButton("Spremi račun");
        buttonSpremi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                String brojRacunaIban = textFieldBrojRacunaIban.getText();
                String stanjeRacunaText = textFieldStanjeRacuna.getText();
                Osoba vlasnikRacuna = (Osoba) comboBoxVlasnikRacuna.getSelectedItem();

                if (!podaciIspravnoUneseni(brojRacunaIban, stanjeRacunaText, vlasnikRacuna))
                {
                    JOptionPane.showMessageDialog(null, "Neispravno uneseni podaci.");
                    return;
                }

                VrstaRacuna odabranaVrstaRacuna = (VrstaRacuna) comboBoxVrstaRacuna.getSelectedItem();
                if(odabranaVrstaRacuna.equals(VrstaRacuna.DEVIZNI))
                {
                    BigDecimal stanjeRacuna = new BigDecimal(stanjeRacunaText);
                    Valuta valuta = (Valuta) comboBoxValutaRacuna.getSelectedItem();
                    DevizniRacun devizni = new DevizniRacun(vlasnikRacuna, stanjeRacuna, brojRacunaIban, valuta);
                    listaRacuna.add(devizni);
                    JOptionPane.showMessageDialog(null, "Uspješno ste dodali novi devizni račun: " + brojRacunaIban);
                    GlavniEkran.logger.info("Uspješno dodan novi devizni račun: " + brojRacunaIban);
                }
                else
                {
                    BigDecimal stanjeRacuna = new BigDecimal(stanjeRacunaText);
                    TekuciRacun tekuci = new TekuciRacun(vlasnikRacuna, stanjeRacuna, brojRacunaIban);
                    listaRacuna.add(tekuci);
                    JOptionPane.showMessageDialog(null, "Uspješno ste dodali novi tekući račun: " + brojRacunaIban);
                    GlavniEkran.logger.info("Uspješno dodan novi tekući račun: " + brojRacunaIban);
                }

                dispose();
            }
        });

        GridBagConstraints constraintsButtonSpremiRacun = new GridBagConstraints();
        constraintsButtonSpremiRacun.insets = new Insets(0, 0, 0, 5);
        constraintsButtonSpremiRacun.gridx = 1;
        constraintsButtonSpremiRacun.gridy = 4;
        this.getContentPane().add(buttonSpremi, constraintsButtonSpremiRacun);
    }

    /**
     * Provjerava ispravnost unesenih podataka.
     *
     * @param brojRacunaIban podatak o broju ili ibanu računa
     * @param stanjeRacuna podatak o stanju računa
     * @param osoba podatak o vlasniku računa
     * @return true ako su podaci ispravno uneseni, inače false
     */
    private Boolean podaciIspravnoUneseni(String brojRacunaIban, String stanjeRacuna, Osoba osoba)
    {
        return brojRacunaIban.length() > 0 && stanjeRacuna.length() > 0 && osoba != null;
    }

    /**
     * Otvara novostvoreni ekran za unos novog računa.
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