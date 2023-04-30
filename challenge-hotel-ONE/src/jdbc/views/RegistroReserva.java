package jdbc.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import jdbc.controller.ReservaController;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.text.Format;
import java.util.Calendar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

@SuppressWarnings("serial")
public class RegistroReserva extends JFrame {

	private JPanel contentPane;
	private JLabel labelSignoDólar;
	public static JTextField txtValor;
	public static JDateChooser txtFechaEntrada;
	public static JDateChooser txtFechaSalida;
	public static JComboBox<String> cBoxFormaPago;
	int xMouse, yMouse;
	private JLabel labelExit;
	private JLabel labelAtras;

	private Calendar calendario;
	private Date fechaHoyDate;
	private ReservaController reservaController;
	private boolean fechaEntradaSeleccionada = false;

	/**
	 * Create the frame.
	 */
	public RegistroReserva() {

		super("Reserva");

		this.reservaController = new ReservaController();

		this.calendario = Calendar.getInstance();
		this.fechaHoyDate = new Date(calendario.getTimeInMillis());

		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroReserva.class.getResource("/imagenes/aH-40px.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 560);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 910, 560);
		contentPane.add(panel);
		panel.setLayout(null);

		// Código que crea los elementos de la interfáz gráfica

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(SystemColor.textHighlight);
		separator_1_2.setBounds(68, 195, 289, 2);
		separator_1_2.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_2);

		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setForeground(SystemColor.textHighlight);
		separator_1_3.setBackground(SystemColor.textHighlight);
		separator_1_3.setBounds(68, 453, 289, 2);
		panel.add(separator_1_3);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(SystemColor.textHighlight);
		separator_1_1.setBounds(68, 281, 289, 11);
		separator_1_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_1);

		JLabel lblCheckIn = new JLabel("FECHA DE CHECK IN");
		lblCheckIn.setForeground(SystemColor.textInactiveText);
		lblCheckIn.setBounds(68, 135, 260, 14);
		lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckIn);

		JLabel lblCheckOut = new JLabel("FECHA DE CHECK OUT");
		lblCheckOut.setForeground(SystemColor.textInactiveText);
		lblCheckOut.setBounds(68, 221, 260, 14);
		lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckOut);

		JLabel lblFormaPago = new JLabel("FORMA DE PAGO");
		lblFormaPago.setForeground(SystemColor.textInactiveText);
		lblFormaPago.setBounds(68, 382, 187, 24);
		lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblFormaPago);

		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setBounds(90, 59, 238, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
		panel.add(lblTitulo);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(428, 0, 482, 560);
		panel_1.setBackground(new Color(12, 138, 199));
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel logo = new JLabel("");
		logo.setBounds(197, 68, 104, 107);
		panel_1.add(logo);
		logo.setIcon(new ImageIcon(RegistroReserva.class.getResource("/imagenes/Ha-100px.png")));

		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 140, 500, 409);
		panel_1.add(imagenFondo);
		imagenFondo.setBackground(Color.WHITE);
		imagenFondo.setIcon(new ImageIcon(RegistroReserva.class.getResource("/imagenes/reservas-img-3.png")));

		JLabel lblValor = new JLabel("VALOR DE LA RESERVA");
		lblValor.setForeground(SystemColor.textInactiveText);
		lblValor.setBounds(72, 303, 256, 14);
		lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblValor);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBounds(68, 362, 289, 2);
		separator_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1);

		// Componentes para dejar la interfaz con estilo Material Design

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal principal = new MenuPrincipal();
				principal.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnexit.setBackground(new Color(12, 138, 199));
				labelExit.setForeground(Color.white);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setBounds(429, 0, 53, 36);
		panel_1.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		panel.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));

		JLabel lblSiguiente = new JLabel("SIGUIENTE");
		lblSiguiente.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguiente.setForeground(Color.WHITE);
		lblSiguiente.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblSiguiente.setBounds(0, 0, 122, 35);

		labelSignoDólar = new JLabel();
		labelSignoDólar.setText("$");
		labelSignoDólar.setBackground(SystemColor.text);
		labelSignoDólar.setHorizontalAlignment(SwingConstants.CENTER);
		labelSignoDólar.setForeground(Color.BLACK);
		labelSignoDólar.setBounds(82, 327, 30, 33);
		labelSignoDólar.setFont(new Font("Roboto Black", Font.BOLD, 17));
		labelSignoDólar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(labelSignoDólar);

		// Campos que guardaremos en la base de datos
		txtFechaEntrada = new JDateChooser();
		txtFechaEntrada.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaEntrada.getCalendarButton()
				.setIcon(new ImageIcon(RegistroReserva.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaEntrada.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
		txtFechaEntrada.setDate(fechaHoyDate);
		txtFechaEntrada.setMinSelectableDate(fechaHoyDate);
		txtFechaEntrada.setBounds(68, 161, 289, 35);
		txtFechaEntrada.getCalendarButton().setBounds(268, 0, 21, 33);
		txtFechaEntrada.setBackground(Color.WHITE);

		txtFechaEntrada.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					if (fechaEntradaSeleccionada) {
						String fechaEntradaString = ((JTextField) txtFechaEntrada.getDateEditor().getUiComponent())
								.getText();
						String fechaSalidaString = ((JTextField) txtFechaSalida.getDateEditor().getUiComponent())
								.getText();
						Date fechaEntradaDate = Date.valueOf(fechaEntradaString);
						Date fechaSalidaDate = Date.valueOf(fechaSalidaString);
						calcularValorReserva(fechaEntradaDate, fechaSalidaDate);
					} else {
						fechaEntradaSeleccionada = true;
					}
				}
			}
		});
		txtFechaEntrada.setBorder(new LineBorder(SystemColor.window));
		txtFechaEntrada.setDateFormatString("yyyy-MM-dd");
		txtFechaEntrada.setFont(new Font("Roboto", Font.PLAIN, 18));

		panel.add(txtFechaEntrada);

		txtFechaSalida = new JDateChooser();
		txtFechaSalida.getCalendarButton()
				.setIcon(new ImageIcon(RegistroReserva.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaSalida.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		txtFechaSalida.setDate(Date.valueOf(fechaHoyDate.toLocalDate().plusDays(1)));
		txtFechaSalida.setMinSelectableDate(Date.valueOf(fechaHoyDate.toLocalDate().plusDays(1)));
		txtFechaSalida.setBounds(68, 246, 289, 35);
		txtFechaSalida.getCalendarButton().setBounds(267, 1, 21, 31);
		txtFechaSalida.setBackground(Color.WHITE);
		txtFechaSalida.setFont(new Font("Roboto", Font.PLAIN, 18));
		txtFechaSalida.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					System.out.println(e.getPropertyName());
					String fechaEntradaString = ((JTextField) txtFechaEntrada.getDateEditor().getUiComponent())
							.getText();
					String fechaSalidaString = ((JTextField) txtFechaSalida.getDateEditor().getUiComponent()).getText();
					Date fechaEntradaDate = Date.valueOf(fechaEntradaString);
					Date fechaSalidaDate = Date.valueOf(fechaSalidaString);
					calcularValorReserva(fechaEntradaDate, fechaSalidaDate);
				}
			}
		});
		txtFechaSalida.setDateFormatString("yyyy-MM-dd");
		txtFechaSalida.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaSalida.setBorder(new LineBorder(new Color(255, 255, 255), 0));
		panel.add(txtFechaSalida);

		txtValor = new JTextField();
		txtValor.setBackground(SystemColor.text);
		txtValor.setHorizontalAlignment(SwingConstants.CENTER);
		txtValor.setForeground(Color.BLACK);
		txtValor.setBounds(128, 327, 200, 33);
		txtValor.setEditable(false);
		txtValor.setFont(new Font("Roboto Black", Font.BOLD, 17));
		txtValor.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(txtValor);
		txtValor.setColumns(10);

		cBoxFormaPago = new JComboBox();
		cBoxFormaPago.setModel(new DefaultComboBoxModel(new String[] { "Selecciona una opción", "Tarjeta de Crédito",
				"Tarjeta de Débito", "Dinero en efectivo" }));
		cBoxFormaPago.setSelectedIndex(0);
		cBoxFormaPago.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				cBoxFormaPago.removeItemAt(0);
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				cBoxFormaPago.insertItemAt("Selecciona una opción", 0);
//		        txtFormaPago.setSelectedIndex(0);
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		cBoxFormaPago.setBounds(68, 417, 289, 38);
		cBoxFormaPago.setBackground(SystemColor.text);
		cBoxFormaPago.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		cBoxFormaPago.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.add(cBoxFormaPago);

		JPanel btnsiguiente = new JPanel();
		btnsiguiente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((txtFechaEntrada.getDate() == null && txtFechaSalida.getDate() == null)
						|| cBoxFormaPago.getSelectedItem().toString() == "Selecciona una opción") {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
				} else {
					generarIdReserva();
				}

			}
		});
		btnsiguiente.setLayout(null);
		btnsiguiente.setBackground(SystemColor.textHighlight);
		btnsiguiente.setBounds(238, 493, 122, 35);
		panel.add(btnsiguiente);
		btnsiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel lblSiguiente_1 = new JLabel("SIGUIENTE");
		lblSiguiente_1.setBounds(0, 0, 122, 35);
		btnsiguiente.add(lblSiguiente_1);
		lblSiguiente_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguiente_1.setForeground(Color.WHITE);
		lblSiguiente_1.setFont(new Font("Dialog", Font.PLAIN, 18));

	}

	private void generarIdReserva() {

		String fechaEntrada = ((JTextField) txtFechaEntrada.getDateEditor().getUiComponent()).getText();
		String fechaSalida = ((JTextField) txtFechaSalida.getDateEditor().getUiComponent()).getText();

		var confimarFechas = new ConfirmacionFechasReserva(Date.valueOf(fechaEntrada), Date.valueOf(fechaSalida),
				txtValor.getText(), cBoxFormaPago.getSelectedItem().toString(), this);

		confimarFechas.setVisible(true);

	}

	private void calcularValorReserva(Date fechaEntrada, Date fechaSalida) {

		if (fechaEntrada.before(fechaSalida)) {
			String valorReserva = reservaController.calcularValorReserva(fechaEntrada, fechaSalida);
			txtValor.setText(valorReserva);
		} else if (fechaEntrada.equals(fechaSalida)) {
			JOptionPane.showMessageDialog(null,
					"Debes seleccionar una fecha de check out diferente a la fecha de check in");
			setearFechas(fechaEntrada);
		} else {
			JOptionPane.showMessageDialog(null, "La fecha de check out no puede ser anterior a la fecha de check in");
			setearFechas(fechaEntrada);
			txtValor.setText(null);
		}
	}

	private void setearFechas(Date fecha) {
		txtFechaEntrada.setDate(fecha);
		txtFechaSalida.setDate(Date.valueOf(fecha.toLocalDate().plusDays(1)));
	}

	// Código que permite mover la ventana por la pantalla según la posición de "x"
	// y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}