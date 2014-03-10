package at.htlklu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import at.htlklu.elektronik.schnittstellen.SerielleSchnittstelle;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JButton btnConn;
	/**
	 * @wbp.nonvisual location=316,389
	 */
	private SerielleSchnittstelle com;
	private JLabel lblDisconnected;
	private JLabel lblLedModul;
	private JSlider sliderModul;
	private JLabel lblLed;
	private JSlider sliderLed;
	private JSlider sliderRed;
	private JSlider sliderGreen;
	private JSlider sliderBlue;
	private JSeparator separator;
	private JLabel lblRot;
	private JLabel lblGrn;
	private JLabel lblBlau;
	private JLabel labelDisplayRed;
	private JLabel labeldisplayGreen;
	private JLabel labelDisplayBlue;
	private JButton btnSend;
	private DataGenerator dg;
	private ChangeListener cl = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			sliderRedStateChanged(e);
		}
	};
	private JButton btnDisconnect;
	private JCheckBox chckbxFarbverlauf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnConn = new JButton("Connect");
		btnConn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnActionPerformed(e);
			}
		});

		lblDisconnected = new JLabel("Disconnected");

		lblLedModul = new JLabel("Modul:");

		sliderModul = new JSlider();
		sliderModul.setMajorTickSpacing(1);
		sliderModul.setPaintLabels(true);
		sliderModul.setPaintTicks(true);
		sliderModul.setValue(1);
		sliderModul.setMaximum(5);
		sliderModul.setMinimum(1);

		lblLed = new JLabel("LED:");

		sliderLed = new JSlider();
		sliderLed.setValue(1);
		sliderLed.setPaintTicks(true);
		sliderLed.setPaintLabels(true);
		sliderLed.setMinimum(1);
		sliderLed.setMaximum(5);
		sliderLed.setMajorTickSpacing(1);

		sliderRed = new JSlider();
		sliderRed.setValue(0);
		sliderRed.addChangeListener(cl);
		sliderRed.setPaintTicks(true);
		sliderRed.setPaintLabels(true);
		sliderRed.setMaximum(64);
		sliderRed.setMajorTickSpacing(4);

		sliderGreen = new JSlider();
		sliderGreen.setValue(0);
		sliderGreen.addChangeListener(cl);
		sliderGreen.setPaintTicks(true);
		sliderGreen.setPaintLabels(true);
		sliderGreen.setMaximum(64);
		sliderGreen.setMajorTickSpacing(4);

		sliderBlue = new JSlider();
		sliderBlue.setValue(0);
		sliderBlue.addChangeListener(cl);
		sliderBlue.setPaintTicks(true);
		sliderBlue.setPaintLabels(true);
		sliderBlue.setMaximum(64);
		sliderBlue.setMajorTickSpacing(4);

		separator = new JSeparator();

		lblRot = new JLabel("Rot");

		lblGrn = new JLabel("Gr\u00FCn");

		lblBlau = new JLabel("Blau");

		labelDisplayRed = new JLabel("0");

		labeldisplayGreen = new JLabel("0");

		labelDisplayBlue = new JLabel("0");

		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSendActionPerformed(e);
			}
		});

		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setEnabled(false);
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDisconnectActionPerformed(e);
			}
		});

		chckbxFarbverlauf = new JCheckBox("Farbverlauf");
		chckbxFarbverlauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxFarbverlaufActionPerformed(e);
			}
		});
		chckbxFarbverlauf.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				chckbxFarbverlaufStateChanged(e);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																btnSend,
																GroupLayout.DEFAULT_SIZE,
																487,
																Short.MAX_VALUE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				btnConn)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnDisconnect)
																		.addGap(18)
																		.addComponent(
																				chckbxFarbverlauf)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				144,
																				Short.MAX_VALUE)
																		.addComponent(
																				lblDisconnected))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblLedModul)
																						.addComponent(
																								lblLed))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								sliderLed,
																								GroupLayout.DEFAULT_SIZE,
																								445,
																								Short.MAX_VALUE)
																						.addComponent(
																								sliderModul,
																								GroupLayout.DEFAULT_SIZE,
																								445,
																								Short.MAX_VALUE)))
														.addComponent(
																separator,
																GroupLayout.DEFAULT_SIZE,
																487,
																Short.MAX_VALUE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								lblRot,
																								GroupLayout.DEFAULT_SIZE,
																								41,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblGrn,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblBlau,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								labelDisplayBlue,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								labeldisplayGreen,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								labelDisplayRed,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addGap(18)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								sliderBlue,
																								GroupLayout.DEFAULT_SIZE,
																								428,
																								Short.MAX_VALUE)
																						.addComponent(
																								sliderGreen,
																								GroupLayout.DEFAULT_SIZE,
																								428,
																								Short.MAX_VALUE)
																						.addComponent(
																								sliderRed,
																								GroupLayout.DEFAULT_SIZE,
																								428,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblDisconnected)
														.addComponent(btnConn)
														.addComponent(
																btnDisconnect)
														.addComponent(
																chckbxFarbverlauf))
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblLedModul)
														.addComponent(
																sliderModul,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblLed)
														.addComponent(
																sliderLed,
																GroupLayout.PREFERRED_SIZE,
																45,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addComponent(separator,
												GroupLayout.PREFERRED_SIZE, 2,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblRot)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelDisplayRed))
														.addComponent(
																sliderRed,
																GroupLayout.PREFERRED_SIZE,
																45,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblGrn)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labeldisplayGreen))
														.addComponent(
																sliderGreen,
																GroupLayout.PREFERRED_SIZE,
																45,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblBlau)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelDisplayBlue))
														.addComponent(
																sliderBlue,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(btnSend,
												GroupLayout.DEFAULT_SIZE, 33,
												Short.MAX_VALUE)
										.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}

	protected void btnConnActionPerformed(ActionEvent e) {
		String port = com.listAndSelectPort();
		if (port != null || port != "") {
			com = new SerielleSchnittstelle(port);
			lblDisconnected.setText("Connected");
			btnDisconnect.setEnabled(true);
			btnConn.setEnabled(false);
			btnSend.setEnabled(true);
		}
	}

	protected void sliderRedStateChanged(ChangeEvent e) {

		try {
			labelDisplayRed.setText(Integer.toString(sliderRed.getValue()));
			labeldisplayGreen.setText(Integer.toString(sliderGreen.getValue()));
			labelDisplayBlue.setText(Integer.toString(sliderBlue.getValue()));
		} catch (Exception e2) {
		}

	}

	protected void btnSendActionPerformed(ActionEvent e) {
		if (chckbxFarbverlauf.isSelected()) {
			dg = new DataGenerator(sliderModul.getValue(), sliderLed.getValue(), true);
		} else {
			
			dg = new DataGenerator(sliderModul.getValue(),
				sliderLed.getValue(), sliderRed.getValue(),
			sliderGreen.getValue(), sliderBlue.getValue());
		}
		System.out.println(Integer.toBinaryString(dg.getData()));
		com.sendString(Integer.toString(dg.getData()));
	}

	protected void btnDisconnectActionPerformed(ActionEvent e) {
		btnConn.setEnabled(true);
		btnDisconnect.setEnabled(false);
		btnSend.setEnabled(false);
	}

	protected void chckbxFarbverlaufStateChanged(ChangeEvent e) {

	}

	protected void chckbxFarbverlaufActionPerformed(ActionEvent e) {
		if (chckbxFarbverlauf.isSelected()) {
			sliderRed.setEnabled(false);
			sliderRed.setValue(0);
			sliderGreen.setEnabled(false);
			sliderGreen.setValue(0);
			sliderBlue.setEnabled(false);
			sliderBlue.setValue(0);
		} else {
			sliderRed.setEnabled(true);
			sliderGreen.setEnabled(true);
			sliderBlue.setEnabled(true);
		}
	}
}
