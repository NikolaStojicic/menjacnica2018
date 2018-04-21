package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	public static MenjacnicaInterface sistem = new Menjacnica();

	public static MenjacnicaGUI glavniProzor;
	public static JPanel contentPane;
	public static JTable table;
	public static Valuta valuta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKontroler.glavniProzor = new MenjacnicaGUI();
					GUIKontroler.glavniProzor.setVisible(true);
					GUIKontroler.contentPane = (JPanel) GUIKontroler.glavniProzor.getContentPane();
					table = GUIKontroler.glavniProzor.getTable();
					valuta = new Valuta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziSveValute() {
		MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
		model.staviSveValuteUModel(sistem.vratiKursnuListu());

	}

	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(contentPane);
		prozor.setVisible(true);
	}

	public static ObrisiKursGUI obrisiGUI;

	public static void prikaziObrisiKursGUI() {

		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			GUIKontroler.valuta = model.vratiValutu(glavniProzor.getTable().getSelectedRow());
			ObrisiKursGUI prozor = new ObrisiKursGUI();
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
			obrisiGUI = prozor;
		}
	}

	public static void prikaziIzvrsiZamenuGUI() {
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			GUIKontroler.valuta = model.vratiValutu(glavniProzor.getTable().getSelectedRow());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI();
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}

	public static void obrisiValutu() {
		try {
			GUIKontroler.sistem.obrisiValutu(GUIKontroler.valuta);
			GUIKontroler.prikaziSveValute();
			obrisiGUI.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

}
