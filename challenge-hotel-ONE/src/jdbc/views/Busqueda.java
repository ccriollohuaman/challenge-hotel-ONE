package jdbc.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import jdbc.controller.HuespedController;
import jdbc.controller.ReservaController;
import jdbc.model.Huesped;
import jdbc.model.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTabbedPane panel;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modeloReserva;
	private DefaultTableModel modeloHuesped;
	private JLabel labeLogoHotel;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservaController reservaController;
	private HuespedController huespedController;

	/**
	 * Create the frame.
	 */
	public Busqueda() {

		this.huespedController = new HuespedController();
		this.reservaController = new ReservaController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (txtBuscar.getText().isEmpty()) {
					limpiarTablaReserva();
					limpiarTablaHuesped();
					cargarTablaReserva();
					cargarTablaHuesped();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(310, 62, 289, 32);
		contentPane.add(lblNewLabel_4);

		panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 && column != 3 && column != 4;
			}
		};
		tbReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbReservas.getSelectedRow();
				int col = tbReservas.getSelectedColumn();
				if (!tbReservas.isCellEditable(row, col)) {
					JOptionPane.showMessageDialog(null, "Esta celda no es editable");
				}
			}
		});
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Dialog", Font.PLAIN, 14));

		modeloReserva = (DefaultTableModel) tbReservas.getModel();
		modeloReserva.addColumn("Numero de Reserva");
		modeloReserva.addColumn("Fecha Check In");
		modeloReserva.addColumn("Fecha Check Out");
		modeloReserva.addColumn("Valor");
		modeloReserva.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scroll_tableReservas = new JScrollPane(tbReservas);
		scroll_tableReservas.setFont(new Font("Dialog", Font.PLAIN, 13));
		tbReservas.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				tbReservas.removeAncestorListener(this);

			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			private boolean tablaResCargada = false;

			@Override
			public void ancestorAdded(AncestorEvent event) {
				if (!tablaResCargada) {
					panel.setSelectedIndex(0);
					limpiarTablaReserva();
					cargarTablaReserva();
					tablaResCargada = true;
				}

			}
		});
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")),
				scroll_tableReservas, null);

		tbHuespedes = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 && column != 6;
			}
		};
		tbHuespedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbHuespedes.getSelectedRow();
				int col = tbHuespedes.getSelectedColumn();
				if (!tbHuespedes.isCellEditable(row, col)) {
					JOptionPane.showMessageDialog(null, "Esta celda no es editable");
				}
			}
		});
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Dialog", Font.PLAIN, 14));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		tbHuespedes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		scroll_tableHuespedes.setFont(new Font("Dialog", Font.PLAIN, 13));
		tbHuespedes.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				tbHuespedes.removeAncestorListener(this);

			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			private boolean tablaHuesCargada = false;

			@Override
			public void ancestorAdded(AncestorEvent event) {
				if (!tablaHuesCargada) {
					limpiarTablaHuesped();
					cargarTablaHuesped();
					tablaHuesCargada = true;
				}
			}
		});
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		labeLogoHotel = new JLabel("");
		labeLogoHotel.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		labeLogoHotel.setBounds(56, 51, 104, 107);
		contentPane.add(labeLogoHotel);

		JPanel header = new JPanel();
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
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

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
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtBuscar.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							String.format("Ingresa el ID de la reserva o el Apellido del huésped a buscar"));
				} else {
					limpiarTablas();
					buscarReservaHuesped(txtBuscar.getText());
				}
			}

		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editarDatos();
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eliminarDatos();
			}
		});
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	private void limpiarTablaReserva() {
		modeloReserva.getDataVector().clear();
	}

	private void limpiarTablaHuesped() {
		modeloHuesped.getDataVector().clear();
	}

	private void limpiarTablas() {
		modeloReserva.setRowCount(0);
		modeloHuesped.setRowCount(0);
	}

	private void cargarTablaReserva() {
		var reservas = this.reservaController.cargarTablaReservas();
		tablaReserva(reservas);
	}

	private void cargarTablaHuesped() {
		var huespedes = this.huespedController.cargarTablaHuesped();
		tablaHuesped(huespedes);
	}

	private void buscarReservaHuesped(String textoIngresado) {
		if (!textoIngresado.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+")) {
			JOptionPane.showMessageDialog(null, String.format("No se aceptan valores especiales"));
			return;
		}

		if (!textoIngresado.isEmpty()) {
			boolean isNumber = true;
			for (int i = 0; i < textoIngresado.length(); i++) {
				if (!Character.isDigit(textoIngresado.charAt(i))) {
					isNumber = false;
					break;
				}
			}
			if (isNumber) {
				buscarReservaHuespedPorId(textoIngresado);
			} else {
				buscarReservaHuespedPorApellido(textoIngresado);
			}
		}
	}

	private void buscarReservaHuespedPorId(String idReserva) {
		Integer idReservaBuscar = Integer.valueOf(idReserva);

		var reservas = this.reservaController.buscarReservaPorId(idReservaBuscar);
		tablaReserva(reservas);

		var huespedes = this.huespedController.buscarHuespedPorIdReserva(idReservaBuscar);
		tablaHuesped(huespedes);
	}

	private void buscarReservaHuespedPorApellido(String apellidoHuespedBuscar) {
		var reservas = this.reservaController.buscarReservaPorApellidoHuesped(apellidoHuespedBuscar);
		tablaReserva(reservas);

		var huespedes = this.huespedController.buscarHuespedPorApellido(apellidoHuespedBuscar);
		tablaHuesped(huespedes);
	}

	private void editarDatos() {
		if (panel.getSelectedIndex() == 0) {
			editarDatosReserva();
		} else {
			editarDatosHuesped();
		}
	}

	private void eliminarDatos() {

		if (panel.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "Dirigete a la tabla de huespedes para eliminar el registro");
		} else {
			Object[] options = { "Sí", "No" };
			int opcion = JOptionPane.showOptionDialog(null,
					"<html><div style='text-align: center;'>¿Quieres eliminar el huésped seleccionado?<br><br>Esto es irrevercible</div></html>",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (opcion == JOptionPane.YES_OPTION) {
				eliminarDesdeHuesped();
				panel.setSelectedIndex(0);

			}
		}
	}

	private void editarDatosReserva() {

		Optional.ofNullable(modeloReserva.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {

					Integer id = Integer.valueOf(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 0).toString());
					String fechaE = String.valueOf(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 1));
					Date fechaEntrada = Date.valueOf(fechaE);
					String fechaS = String.valueOf(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 2));
					Date fechaSalida = Date.valueOf(fechaS);
					String formaPago = (String) modeloReserva.getValueAt(tbReservas.getSelectedRow(), 4).toString();
					int filasModificadas;
					filasModificadas = this.reservaController.editarDatosReserva(id, fechaEntrada, fechaSalida,
							formaPago);
					JOptionPane.showMessageDialog(this,
							String.format("%d ítem modificado con éxito", filasModificadas));

					Integer ubicacion = tbReservas.getSelectedRow();
					limpiarTablaReserva();
					cargarTablaReserva();
					tbReservas.setRowSelectionInterval(ubicacion, ubicacion);

				}, () -> JOptionPane.showMessageDialog(this, "Por favor, selecciona un ítem a editar"));

	}

	private void editarDatosHuesped() {

		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
					String nombre = String.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1));
					String apellido = String.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2));
					String fechaNacimientoString = String
							.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3));
					Date fechaNacimiento = Date.valueOf(fechaNacimientoString);
					String nacionalidad = String.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4));
					String telefono = String.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5));
					Integer reservaId = Integer
							.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());

					int filasModificadas;

					filasModificadas = this.huespedController.editarDatosHuesped(id, nombre, apellido, fechaNacimiento,
							nacionalidad, telefono, reservaId);
					JOptionPane.showMessageDialog(this,
							String.format("%d ítem modificado con éxito", filasModificadas));
					Integer ubicacion = tbHuespedes.getSelectedRow();

					tbHuespedes.setRowSelectionInterval(ubicacion, ubicacion);
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, selecciona un ítem a editar"));
	}

	private void eliminarDesdeHuesped() {

		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					Integer idReserva = Integer
							.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
					System.out.println("estoy en huesped " + idReserva);
					int cantidadEliminada;

					cantidadEliminada = this.huespedController.eliminarDesdeHuesped(idReserva);
					modeloReserva.removeRow(tbHuespedes.getSelectedRow());
					limpiarTablaHuesped();
					cargarTablaHuesped();

					cantidadEliminada = this.reservaController.eliminarDesdeReserva(idReserva);

					modeloReserva.removeRow(tbReservas.getSelectedRow());
					limpiarTablaReserva();
					cargarTablaReserva();

					JOptionPane.showMessageDialog(this, cantidadEliminada + " item eliminado con éxito!");
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	}
	
	private void tablaReserva(List<Reserva> reservas) {
		try {
			reservas.forEach(reserva -> modeloReserva.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
					reserva.getFechaSalida(), reserva.getValor(), reserva.getFormaPago() }));
			tbReservas.setRowSelectionInterval(0, 0);
		} catch (Exception e) {
			throw e;
		}
	}

	private void tablaHuesped(List<Huesped> huespedes) {
		try {
			huespedes.forEach(huesped -> modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(),
					huesped.getApellido(), huesped.getFechaNacimiento(), huesped.getNacionalidad(),
					huesped.getTelefono(), huesped.getReservaId() }));
			tbHuespedes.setRowSelectionInterval(0, 0);
		} catch (Exception e) {
			throw e;
		}
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
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
