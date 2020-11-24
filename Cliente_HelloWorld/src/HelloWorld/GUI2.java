package HelloWorld;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI2 {

	int id = 0;
	private JFrame frame;
	private JTextField textFieldAc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI2 window = new GUI2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAdicionarAcionista = new JLabel("ADICIONAR ACIONISTA");
		lblAdicionarAcionista.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblAdicionarAcionista.setBounds(126, 60, 197, 26);
		frame.getContentPane().add(lblAdicionarAcionista);
		
		textFieldAc = new JTextField();
		textFieldAc.setBounds(136, 97, 155, 20);
		frame.getContentPane().add(textFieldAc);
		textFieldAc.setColumns(10);
		
		JButton btnAdicionarAc = new JButton("Adicionar");
		btnAdicionarAc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI wind = new GUI(textFieldAc.getText(), id);
				wind.frame.setVisible(true);
				id++;
			}
		});
		btnAdicionarAc.setBounds(170, 128, 89, 23);
		frame.getContentPane().add(btnAdicionarAc);
	}

}
