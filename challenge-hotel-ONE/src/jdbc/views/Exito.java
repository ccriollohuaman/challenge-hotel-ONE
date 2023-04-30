package jdbc.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Exito extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public Exito(Date fechaEntrada, Date fechaSalida, int idReserva, RegistroHuesped registroHuesped) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Exito.class.getResource("/imagenes/aH-40px.png")));
		setBounds(100, 100, 394, 325);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.control);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Exito.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel.setBounds(140, 11, 100, 100);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Datos guardados satisfactoriamente");
		lblNewLabel_1.setForeground(new Color(12, 138, 199));
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel_1.setBounds(29, 122, 322, 22);
		contentPanel.add(lblNewLabel_1);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();// sirve para cerrar la ventana actual
				registroHuesped.dispose();
				MenuUsuario menuUsuario = new MenuUsuario();
				menuUsuario.setVisible(true);
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JTextArea txtInfo = new JTextArea();
		txtInfo.setEditable(false);
		txtInfo.setMargin(new Insets(10, 20, 2, 2));
		txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtInfo.setText(String.format("Tu número de reserva es: %s\n\nFecha entrada: %s\nFecha salida: %s",
				String.valueOf(idReserva), fechaEntrada.toString(), fechaSalida.toString()));
		txtInfo.setBounds(20, 154, 340, 102);
		txtInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtInfo.setAlignmentY(Component.CENTER_ALIGNMENT);
		contentPanel.add(txtInfo);

	}
}
