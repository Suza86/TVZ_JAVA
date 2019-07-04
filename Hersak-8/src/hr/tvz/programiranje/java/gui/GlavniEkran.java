package hr.tvz.programiranje.java.gui;

import hr.tvz.programiranje.java.banka.DeviznaTransakcija;
import hr.tvz.programiranje.java.banka.DevizniRacun;
import hr.tvz.programiranje.java.banka.Racun;
import hr.tvz.programiranje.java.banka.TekuciRacun;
import hr.tvz.programiranje.java.banka.Transakcija;
import hr.tvz.programiranje.java.baza.BazaPodataka;
import hr.tvz.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.tvz.programiranje.java.osoba.Osoba;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlavniEkran extends JFrame
{
    public static final Logger logger = LoggerFactory.getLogger(GlavniEkran.class);

    private JComboBox<Racun> _comboBoxPrviRacun;
    private JComboBox<Racun> _comboBoxDrugiRacun;
    private JTable _tablePopisTransakcija;

    /**
     * Kreira glavni ekran aplikacije.
     */
    public GlavniEkran()
    {
        super("Bankarstvo");
        this.setSize(500, 300);
        this.setLocationByPlatform(true);
        this.setResizable(false);        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMenuBar();
        createMainWindow();

        BazaPodataka.ispisiDatumSNajviseTransakcija();
    }

    /**
     * Dodaje opcije za dodavanje novih osoba i računa unutar menu bara.
     */
    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        JMenu menuOsobe = new JMenu("Osobe");
        menuBar.add(menuOsobe);
        
        GlavniEkran self = this;

        JMenuItem itemDodajOsobu = new JMenuItem("Dodaj novu osobu");
        itemDodajOsobu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                UnosenjeNoveOsobeFrame frame = new UnosenjeNoveOsobeFrame();
                frame.prikaziEkran();
            }
        });
        menuOsobe.add(itemDodajOsobu);

        JMenu menuRacuni = new JMenu("Računi");
        menuBar.add(menuRacuni);
        
        JMenuItem itemDodajRacun = new JMenuItem("Dodaj novi račun");
        itemDodajRacun.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                UnosenjeNovogRacunaFrame frame = new UnosenjeNovogRacunaFrame();
                frame.addWindowListener(new WindowAdapter()
                {
                    public void windowClosed(WindowEvent event)
                    {
                        osvjeziPopisRacuna();    
                    }
                });
                frame.prikaziEkran();   
            }
        });
        menuRacuni.add(itemDodajRacun);
    }

    /**
     * Stvara grafičke elemente glavnog ekrana.
     */
    private void createMainWindow()
    {
        JPanel panelRacuni = new JPanel();
        this.getContentPane().add(panelRacuni, BorderLayout.NORTH);
        panelRacuni.setLayout(new BoxLayout(panelRacuni, BoxLayout.X_AXIS));
        
        JPanel panelPrviRacun = new JPanel();
        panelRacuni.add(panelPrviRacun);
        
        TitledBorder borderPrviRacun = new TitledBorder("Polazni račun");
        panelPrviRacun.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
            "Polazni račun", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        panelPrviRacun.setLayout(new BoxLayout(panelPrviRacun, BoxLayout.Y_AXIS));
        
        final JPanel panelStanjePrvogRacuna = new JPanel();
        panelStanjePrvogRacuna.setVisible(false);
        panelPrviRacun.add(panelStanjePrvogRacuna);
        
        JLabel labelStanjePrvogRacunaText = new JLabel("Stanje računa:");
        panelStanjePrvogRacuna.add(labelStanjePrvogRacunaText);
        
        final JLabel labelStanjePrvogRacuna = new JLabel("");
        panelStanjePrvogRacuna.add(labelStanjePrvogRacuna);
        
        final JLabel labelValutaPrvogRacuna = new JLabel("");
        panelStanjePrvogRacuna.add(labelValutaPrvogRacuna);
        
        _comboBoxPrviRacun = new JComboBox<>();
        _comboBoxPrviRacun.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                List<Racun> listaRacuna = BazaPodataka.dohvatiSveRacune();
                Racun prviRacun = (Racun) _comboBoxPrviRacun.getSelectedItem();
                if(prviRacun == null) prviRacun = listaRacuna.get(0);
                
                if(prviRacun instanceof TekuciRacun)
                {
                    labelStanjePrvogRacuna.setText(prviRacun.getStanje().toString());
                    labelValutaPrvogRacuna.setText("KN");
                }
                else
                {
                    labelStanjePrvogRacuna.setText(prviRacun.getStanje().toString());
                    labelValutaPrvogRacuna.setText(((DevizniRacun)prviRacun).getValuta().toString());
                }

                panelStanjePrvogRacuna.setVisible(true);
            }
        });
        panelPrviRacun.add(_comboBoxPrviRacun);
        
        JPanel panelDrugiRacun = new JPanel();
        panelRacuni.add(panelDrugiRacun);
        
        TitledBorder borderDrugiRacun = new TitledBorder("Dolazni račun");
        panelDrugiRacun.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), 
            "Dolazni račun", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        panelDrugiRacun.setLayout(new BoxLayout(panelDrugiRacun, BoxLayout.Y_AXIS));
        
        final JPanel panelStanjeDrugogRacuna = new JPanel();
        panelStanjeDrugogRacuna.setVisible(false);
        panelDrugiRacun.add(panelStanjeDrugogRacuna);
        
        JLabel labelStanjeDrugogRacunaText = new JLabel("Stanje računa:");
        panelStanjeDrugogRacuna.add(labelStanjeDrugogRacunaText);
        
        final JLabel labelStanjeDrugogRacuna = new JLabel("");
        panelStanjeDrugogRacuna.add(labelStanjeDrugogRacuna);
        
        final JLabel labelValutaDrugogRacuna = new JLabel("");
        panelStanjeDrugogRacuna.add(labelValutaDrugogRacuna);
        
        _comboBoxDrugiRacun = new JComboBox<>();
        _comboBoxDrugiRacun.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                List<Racun> listaRacuna = BazaPodataka.dohvatiSveRacune();
                Racun drugiRacun = (Racun) _comboBoxDrugiRacun.getSelectedItem();
                if(drugiRacun == null) drugiRacun = listaRacuna.get(0);
                
                if(drugiRacun instanceof TekuciRacun)
                {
                    labelStanjeDrugogRacuna.setText(drugiRacun.getStanje().toString());
                    labelValutaDrugogRacuna.setText("KN");
                }
                else
                {
                    labelStanjeDrugogRacuna.setText(drugiRacun.getStanje().toString());
                    labelValutaDrugogRacuna.setText(((DevizniRacun)drugiRacun).getValuta().toString());
                }

                panelStanjeDrugogRacuna.setVisible(true);
            }
        });
        panelDrugiRacun.add(_comboBoxDrugiRacun);
        
        osvjeziPopisRacuna();

        JPanel panelIznosTransakcije = new JPanel();
        this.getContentPane().add(panelIznosTransakcije, BorderLayout.CENTER);
        
        JLabel labelIznosTransakcije = new JLabel("Iznos transakcije:");
        panelIznosTransakcije.add(labelIznosTransakcije);
        
        final JTextField textFieldIznosTransakcije = new JTextField();
        panelIznosTransakcije.add(textFieldIznosTransakcije);
        textFieldIznosTransakcije.setColumns(10);
        
        JLabel labelValutaTransakcije = new JLabel("KN");
        panelIznosTransakcije.add(labelValutaTransakcije);
        
        JButton buttonIzvrsiTransakciju = new JButton("Izvrši transakciju");
        buttonIzvrsiTransakciju.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                Racun prviRacun = (Racun) _comboBoxPrviRacun.getSelectedItem();
                Racun drugiRacun = (Racun) _comboBoxDrugiRacun.getSelectedItem();
                if (prviRacun instanceof TekuciRacun && drugiRacun instanceof TekuciRacun)
                {
                    Transakcija<Racun, Racun> transakcija = new Transakcija<>(prviRacun, 
                        drugiRacun, new BigDecimal(textFieldIznosTransakcije.getText()));

                    try 
                    {
                        transakcija.provediTransakciju();
                        BazaPodataka.spremiTransakciju(transakcija);
                        dodajTransakcijuUTablicu(prviRacun, drugiRacun, transakcija.getIznos());
                        labelStanjePrvogRacuna.setText(prviRacun.getStanje().toString());
                        labelStanjeDrugogRacuna.setText(drugiRacun.getStanje().toString());
                    }
                    catch(NedozvoljenoStanjeRacunaException ex)
                    {
                        String message = "Transakcija se nije provela! Nedozvoljeno stanje računa!";
                        System.out.println(message);
                        JOptionPane.showMessageDialog(null, message);
                        logger.error(message, ex);
                    }
                }
                else if (prviRacun instanceof TekuciRacun && drugiRacun instanceof DevizniRacun)
                {
                    DeviznaTransakcija<TekuciRacun, DevizniRacun> transakcija 
                        = new DeviznaTransakcija<>((TekuciRacun)prviRacun, 
                            (DevizniRacun)drugiRacun, new BigDecimal(textFieldIznosTransakcije.getText()));

                    try
                    {
                        transakcija.provediTransakciju();
                        BazaPodataka.spremiTransakciju(transakcija);
                        dodajTransakcijuUTablicu(prviRacun, drugiRacun, transakcija.getIznos());
                        labelStanjePrvogRacuna.setText(prviRacun.getStanje().toString());
                        labelStanjeDrugogRacuna.setText(drugiRacun.getStanje().toString());
                    }
                    catch(NedozvoljenoStanjeRacunaException ex)
                    {
                        String message = "Transakcija se nije provela! Nedozvoljeno stanje računa!";
                        System.out.println(message);
                        JOptionPane.showMessageDialog(null, message);
                        logger.error(message, ex);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Odabrali ste nepodržanu transakciju!");
                }
            }
        });
        panelIznosTransakcije.add(buttonIzvrsiTransakciju);
        
        JPanel panelPopisTransakcija = new JPanel();
        this.getContentPane().add(panelPopisTransakcija, BorderLayout.SOUTH);
        
        _tablePopisTransakcija = new JTable();
        _tablePopisTransakcija.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Rbr.", "Polazni račun", "Dolazni račun", "Iznos", "Valuta"}
        ));
        _tablePopisTransakcija.getColumnModel().getColumn(0).setPreferredWidth(35);
        _tablePopisTransakcija.getColumnModel().getColumn(0).setMinWidth(35);
        _tablePopisTransakcija.getColumnModel().getColumn(4).setPreferredWidth(50);
        _tablePopisTransakcija.setPreferredScrollableViewportSize(new Dimension(450, 70));

        JScrollPane scrollPanePopisTransakcija = new JScrollPane(_tablePopisTransakcija);
        panelPopisTransakcija.add(scrollPanePopisTransakcija);

        List<Transakcija<?, ?>> listaTransakcija = BazaPodataka.dohvatiSveTransakcije();
        for(Transakcija<?, ?> transakcija : listaTransakcija)
        {
            dodajTransakcijuUTablicu(transakcija.getPolazniRacun(), transakcija.getDolazniRacun(), transakcija.getIznos());
        }
    }

    /**
     * Dodaje novoizvršenu transakciju u tablicu koja se prikazuje unutar glavnog ekrana.
     *
     * @param polazni podatak o polaznom računu
     * @param dolazni podatak o dolaznom računu
     * @param iznos podatak o iznosu obrađenom u transakciji
     */
    private void dodajTransakcijuUTablicu(Racun polazni, Racun dolazni, BigDecimal iznos)
    {
        Object[] podaciUTablici = new Object[5];
        podaciUTablici[0] = _tablePopisTransakcija.getRowCount() + 1 + ".";
        podaciUTablici[1] = ((TekuciRacun) polazni).getBrojRacuna();

        if      (dolazni instanceof TekuciRacun)  podaciUTablici[2] = ((TekuciRacun) dolazni).getBrojRacuna();
        else if (dolazni instanceof DevizniRacun) podaciUTablici[2] = ((DevizniRacun) dolazni).getIban();

        podaciUTablici[3] = iznos.toString();
        podaciUTablici[4] = "KN";

        ((DefaultTableModel)_tablePopisTransakcija.getModel()).addRow(podaciUTablici);
    }

    /**
     * Osvježava popis računa koji se prikazuju unutar glavnog ekrana.
     */
    private void osvjeziPopisRacuna()
    {
        _comboBoxPrviRacun.removeAllItems();
        _comboBoxDrugiRacun.removeAllItems();

        List<Racun> listaRacuna = BazaPodataka.dohvatiSveRacune();
        for (Racun racun : listaRacuna)
        {
            _comboBoxPrviRacun.addItem(racun);
            _comboBoxDrugiRacun.addItem(racun);
        }
    }

    /**
     * Inicijalizira novi prozor aplikacije i otvara ga.
     *
     * @param args ulazni parametri programa
     */
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame prozor = new GlavniEkran();
                prozor.setVisible(true);
            }
        });
    }
}