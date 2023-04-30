package jdbc.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import jdbc.controller.UsuarioController;
import jdbc.exception.UsuarioExistenteException;
import jdbc.model.Usuario;

public class Administrador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	private JLabel lblContrasena;
	private JTextField txtApellido;
	private JLabel lblUsuario;
	private JTextField txtNombre;
	private JTextField txtDNI;
	int xMouse, yMouse;
	private JLabel labelExit;

	private UsuarioController usuarioController;
	private JLabel lblApellido;
	private JLabel lblNombre;
	private JLabel lblDNI;
	private JLabel lblTitulo;
	private JLabel lblTipo;
	private JComboBox<Object> cBoxTipo;

	/**
	 * Create the application.
	 */
	public Administrador() {

		this.usuarioController = new UsuarioController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroReserva.class.getResource("/imagenes/aH-40px.png")));
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 560);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JPanel header = new JPanel();
		header.setBounds(0, 0, 600, 36);
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

		JPanel panel = new JPanel();
		panel.setBounds(383, 0, 217, 560);
		panel.setBackground(new Color(12, 138, 199));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel labelTitulo = new JLabel("ADMINISTRACION");
		labelTitulo.setForeground(SystemColor.window);
		labelTitulo.setFont(new Font("Dialog", Font.PLAIN, 18));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setBounds(29, 266, 159, 24);
		panel.add(labelTitulo);

		JLabel lblLogoHotel = new JLabel("");
		lblLogoHotel.setBounds(58, 48, 100, 100);
		panel.add(lblLogoHotel);
		lblLogoHotel.setIcon(new ImageIcon(Login.class.getResource("/imagenes/Ha-100px.png")));

		header.setLayout(null);
		header.setBackground(SystemColor.text);
		header.setOpaque(false);
		contentPane.add(header);

		JPanel btnExit = new JPanel();
		btnExit.setLayout(null);
		btnExit.setBounds(165, 0, 53, 36);
		panel.add(btnExit);

		btnExit.setBackground(new Color(12, 138, 199));
		btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		labelExit = new JLabel("X");
		labelExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(new Color(12, 138, 199));
				labelExit.setForeground(Color.white);
			}
		});
		labelExit.setBounds(0, 0, 53, 36);
		labelExit.setForeground(Color.WHITE);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnExit.add(labelExit);

		lblTitulo = new JLabel("REGISTRO DE USUARIOS");
		lblTitulo.setForeground(SystemColor.textHighlight);
		lblTitulo.setFont(new Font("Roboto Black", Font.PLAIN, 23));
		lblTitulo.setBounds(64, 115, 286, 30);
		contentPane.add(lblTitulo);

		lblDNI = new JLabel("DNI");
		lblDNI.setForeground(SystemColor.textInactiveText);
		lblDNI.setLabelFor(txtDNI);
		lblDNI.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDNI.setBounds(43, 194, 81, 13);
		contentPane.add(lblDNI);

		lblNombre = new JLabel("NOMBRE");
		lblNombre.setForeground(SystemColor.textInactiveText);
		lblNombre.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNombre.setBounds(43, 237, 81, 13);
		contentPane.add(lblNombre);

		lblApellido = new JLabel("APELLIDO");
		lblApellido.setForeground(SystemColor.textInactiveText);
		lblApellido.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblApellido.setBounds(43, 280, 81, 13);
		contentPane.add(lblApellido);

		lblUsuario = new JLabel("USUARIO");
		lblUsuario.setForeground(SystemColor.textInactiveText);
		lblUsuario.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblUsuario.setBounds(43, 323, 81, 13);
		contentPane.add(lblUsuario);
		lblUsuario.setLabelFor(txtUsuario);

		lblContrasena = new JLabel("CONTRASEÑA");
		lblContrasena.setForeground(SystemColor.textInactiveText);
		lblContrasena.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblContrasena.setBounds(43, 366, 114, 13);
		contentPane.add(lblContrasena);

		lblTipo = new JLabel("TIPO");
		lblTipo.setForeground(SystemColor.textInactiveText);
		lblTipo.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblTipo.setBounds(43, 409, 114, 13);
		contentPane.add(lblTipo);

		txtDNI = new JTextField();
		txtDNI.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtDNI.setToolTipText("");
		txtDNI.setHorizontalAlignment(SwingConstants.LEFT);
		txtDNI.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtDNI.setColumns(10);
		txtDNI.setBounds(167, 187, 183, 29);
		contentPane.add(txtDNI);

		txtNombre = new JTextField();
		txtNombre.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblNombre.setLabelFor(txtNombre);
		txtNombre.setToolTipText("");
		txtNombre.setHorizontalAlignment(SwingConstants.LEFT);
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNombre.setColumns(10);
		txtNombre.setBounds(167, 230, 183, 29);
		contentPane.add(txtNombre);

		txtApellido = new JTextField();
		txtApellido.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblApellido.setLabelFor(txtApellido);
		txtApellido.setToolTipText("");
		txtApellido.setHorizontalAlignment(SwingConstants.LEFT);
		txtApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtApellido.setColumns(10);
		txtApellido.setBounds(167, 273, 183, 29);
		contentPane.add(txtApellido);

		txtUsuario = new JTextField();
		txtUsuario.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsuario.setToolTipText("");
		txtUsuario.setBounds(167, 316, 183, 29);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		txtContrasena = new JPasswordField();
		txtContrasena.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblContrasena.setLabelFor(txtContrasena);
		txtContrasena.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtContrasena.setBounds(167, 359, 183, 29);
		contentPane.add(txtContrasena);
		txtContrasena.setColumns(10);

		cBoxTipo = new JComboBox<Object>();
		cBoxTipo.setModel(new DefaultComboBoxModel<Object>(new String[] { "Administrador", "Agente" }));
		cBoxTipo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cBoxTipo.setBorder(new EmptyBorder(0, 0, 0, 0));
		cBoxTipo.setBounds(167, 402, 183, 29);
		contentPane.add(cBoxTipo);

		JSeparator separatorDNI;
		separatorDNI = new JSeparator();
		separatorDNI.setBackground(new Color(0, 120, 215));
		separatorDNI.setBounds(167, 217, 183, 2);
		contentPane.add(separatorDNI);

		JSeparator separatorNombre;
		separatorNombre = new JSeparator();
		separatorNombre.setBackground(new Color(0, 120, 215));
		separatorNombre.setBounds(167, 261, 183, 2);
		contentPane.add(separatorNombre);

		JSeparator separatorApellido = new JSeparator();
		separatorApellido.setBackground(SystemColor.textHighlight);
		separatorApellido.setBounds(167, 304, 183, 2);
		contentPane.add(separatorApellido);

		JSeparator separatorUsuario;
		separatorUsuario = new JSeparator();
		separatorUsuario.setBackground(new Color(0, 120, 215));
		separatorUsuario.setBounds(167, 345, 183, 2);
		contentPane.add(separatorUsuario);

		JSeparator separatorContrasena = new JSeparator();
		separatorContrasena.setBackground(new Color(0, 120, 215));
		separatorContrasena.setBounds(167, 389, 183, 2);
		contentPane.add(separatorContrasena);

		JSeparator separatorTipo = new JSeparator();
		separatorTipo.setBackground(SystemColor.textHighlight);
		separatorTipo.setBounds(167, 433, 183, 2);
		contentPane.add(separatorTipo);

		JPanel btnCrear = new JPanel();
		btnCrear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCrear.setBackground(new Color(0, 156, 223));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCrear.setBackground(SystemColor.textHighlight);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				crear();
			}
		});

		btnCrear.setBackground(SystemColor.textHighlight);
		btnCrear.setBounds(135, 480, 120, 35);
		contentPane.add(btnCrear);
		btnCrear.setLayout(null);
		btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel lblCrear = new JLabel("CREAR");
		lblCrear.setBounds(0, 0, 120, 35);
		btnCrear.add(lblCrear);
		lblCrear.setForeground(SystemColor.controlLtHighlight);
		lblCrear.setHorizontalAlignment(SwingConstants.CENTER);
		lblCrear.setFont(new Font("Roboto", Font.PLAIN, 18));

	}

	private void crear() {

		String dni = txtDNI.getText().trim();

		if (dni.length() != 8 || !dni.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "El campo DNI debe tener 8 dígitos numéricos");
			return;
		}
	
		if (txtUsuario.getText().isEmpty() || txtContrasena.getPassword().length == 0 || txtNombre.getText().isEmpty()
				|| txtApellido.getText().isEmpty() || cBoxTipo.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
			return;
		}

		var usuario = new Usuario(txtDNI.getText(), txtNombre.getText(), txtApellido.getText(), txtUsuario.getText(),
				txtContrasena.getPassword(), cBoxTipo.getSelectedItem().toString());
		try {
			this.usuarioController.guardar(usuario);
			JOptionPane.showMessageDialog(null, "El usuario se creó correctamente.");
			dispose();
			MenuUsuario menuUsuario = new MenuUsuario();
			menuUsuario.setVisible(true);
			limpiarFormulario();
		} catch (UsuarioExistenteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void limpiarFormulario() {
		this.txtDNI.setText("");
		this.txtNombre.setText("");
		this.txtApellido.setText("");
		this.txtUsuario.setText("");
		this.txtContrasena.setText("");
	}

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
