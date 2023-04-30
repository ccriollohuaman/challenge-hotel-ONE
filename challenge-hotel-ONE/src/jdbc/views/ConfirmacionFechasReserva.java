package jdbc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import jdbc.controller.ReservaController;
import jdbc.model.Reserva;

@SuppressWarnings("serial")
public class ConfirmacionFechasReserva extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ReservaController reservaController;
	private RegistroReserva reservasView;

	public ConfirmacionFechasReserva(Date fechaEntrada, Date fechaSalida, String valor, String formaPago,
			RegistroReserva reservasView) {

		this.reservasView = reservasView;
		this.reservaController = new ReservaController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Exito.class.getResource("/imagenes/aH-40px.png")));
		setBounds(100, 100, 400, 295);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.control);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);

		JLabel lblTitulo = new JLabel("Revisa los datos ingresados");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 17));
		lblTitulo.setBounds(78, 10, 230, 21);
		contentPanel.add(lblTitulo);

		JTextArea txtInfo = new JTextArea();
		txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtInfo.setMargin(new Insets(20, 20, 5, 18));
		txtInfo.setEditable(false);
		txtInfo.setLineWrap(true);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setText(String.format(
				"Los datos para tu reserva son:\n\nFecha entrada: %s\nFecha salida: %s\nTotal a pagar (USD):  %s\nMétodo de pago: %s",
				fechaEntrada.toString(), fechaSalida.toString(), valor, formaPago));
		txtInfo.setBounds(23, 41, 340, 185);
		txtInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtInfo.setAlignmentY(Component.CENTER_ALIGNMENT);
		contentPanel.add(txtInfo);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(new Rectangle(0, 0, 0, 0));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Continuar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarIdReserva(fechaEntrada, fechaSalida, valor, formaPago);

			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Regresar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void generarIdReserva(Date fechaEntrada, Date fechaSalida, String valor, String formaPago) {
		Float valorReserva = Float.parseFloat(valor);
		System.out.println("Estoy en confimación reserva " + valorReserva);
		var reserva = new Reserva(fechaEntrada, fechaSalida, Float.parseFloat(valor), formaPago);
		this.reservaController.generarIdReserva(reserva);
		JOptionPane.showMessageDialog(this,
				String.format("Tu número de reserva es %d, lo verás a continuación", reserva.getId()));
		reservasView.dispose();
		dispose();
		RegistroHuesped registroHuesped = new RegistroHuesped();
		registroHuesped.setVisible(true);

	}

}
