package HelloWorld;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


class clientes {
	InterfaceCli refCli;
	String nome;
	int id;
	
	clientes (InterfaceCli refCli, String nome, int id){
		this.refCli = refCli;
		this.nome = nome;
		this.id = id;
	}
}

/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
public class GUI {

	private InterfaceServ refServ;
	public JFrame frame;
	CliImpl refCliente;
	
	int id;
	String name;
	private JTextField textFieldInteresse1;
	private JTextField textFieldDesinteresse1;
	private JTextField textFieldNotificacao1;
	private JTextField textFieldNotificacao2;
	private JTextField textFieldNotificacao3;
	private JTextField textFieldCompra1;
	private JTextField textFieldCompra2;
	private JTextField textFieldCompra3;
	private JTextField textFieldCompra4;
	private JTextField textFieldVenda1;
	private JTextField textFieldVenda2;
	private JTextField textFieldVenda3;
	private JTextField textFieldVenda4;
	private JLabel lblEmpresas;
	private JTextArea txtrExxonmobilPetrobrasOi;
	private JLabel lblnomestring;
	private JLabel lblempresastring;
	private JLabel lblempresastring_1;
	private JLabel lblempresastring_2;
	private JLabel lbllimiteint;
	private JLabel lbltempoint;
	private JLabel lblqtdeint;
	private JLabel lblprecomaxint;
	private JLabel lblempresastring_3;
	private JLabel lbltempoint_1;
	private JLabel lblqtdeint_1;
	private JLabel lblprecominint;
	private JLabel lblempresastring_4;
	private JLabel lbltempoint_2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//GUI window = new GUI("guilherme", 0);
					//GUI window2 = new GUI("luiza", 1);
					//window.frame.setVisible(true);
					//window2.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public GUI(String name, int id) {
		try {
			this.name = name;
			this.id = id;
			Registry referenciaServicoNomes;
	        referenciaServicoNomes = LocateRegistry.getRegistry();
	        InterfaceServ referenciaServidor = (InterfaceServ) referenciaServicoNomes.lookup("HelloWorld");
	        this.refServ = referenciaServidor;
	        this.refCliente = new CliImpl(name, id, referenciaServidor, this);
		}catch (Exception e) {}
		initialize();
		
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultListModel dlm = new DefaultListModel();
		
		textFieldInteresse1 = new JTextField();
		textFieldInteresse1.setBounds(312, 49, 86, 20);
		frame.getContentPane().add(textFieldInteresse1);
		textFieldInteresse1.setColumns(10);
		
		JButton btnInserirInteresse = new JButton("Inserir Interesse");
		btnInserirInteresse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refServ.inserirCotacaoInteressante(textFieldInteresse1.getText(), id);
					textFieldInteresse1.setText("");
				} catch (RemoteException e1) {} 
			}
		});
		btnInserirInteresse.setBounds(167, 48, 135, 23);
		frame.getContentPane().add(btnInserirInteresse);
		
		textFieldDesinteresse1 = new JTextField();
		textFieldDesinteresse1.setBounds(312, 104, 86, 20);
		frame.getContentPane().add(textFieldDesinteresse1);
		textFieldDesinteresse1.setColumns(10);
		
		JButton btnRemoverInteresse = new JButton("Remover Interesse");
		btnRemoverInteresse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refServ.removerCotacaoDesinteressante(textFieldDesinteresse1.getText(), id);
					textFieldInteresse1.setText("");
				} catch (RemoteException e1) {} 
			}
		});
		btnRemoverInteresse.setBounds(167, 103, 135, 23);
		frame.getContentPane().add(btnRemoverInteresse);
		
		textFieldNotificacao1 = new JTextField();
		textFieldNotificacao1.setBounds(312, 153, 86, 20);
		frame.getContentPane().add(textFieldNotificacao1);
		textFieldNotificacao1.setColumns(10);
		
		textFieldNotificacao2 = new JTextField();
		textFieldNotificacao2.setBounds(408, 153, 45, 20);
		frame.getContentPane().add(textFieldNotificacao2);
		textFieldNotificacao2.setColumns(10);
		
		textFieldNotificacao3 = new JTextField();
		textFieldNotificacao3.setToolTipText("ganho/perda");
		textFieldNotificacao3.setBounds(463, 153, 72, 20);
		frame.getContentPane().add(textFieldNotificacao3);
		textFieldNotificacao3.setColumns(10);
		
		JButton btnNotificao = new JButton("Notifica\u00E7\u00E3o");
		btnNotificao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refServ.inserirInteresseNotificacao(id, textFieldNotificacao1.getText(), Integer.parseInt(textFieldNotificacao2.getText()), textFieldNotificacao3.getText());
					textFieldNotificacao1.setText("");
					textFieldNotificacao2.setText("");
					textFieldNotificacao3.setText("");
				} catch (NumberFormatException e1) {
					
				} catch (RemoteException e1) {}
			}
		});
		btnNotificao.setBounds(167, 152, 135, 23);
		frame.getContentPane().add(btnNotificao);
		
		textFieldCompra1 = new JTextField();
		textFieldCompra1.setBounds(266, 214, 44, 20);
		frame.getContentPane().add(textFieldCompra1);
		textFieldCompra1.setColumns(10);
		
		textFieldCompra2 = new JTextField();
		textFieldCompra2.setBounds(326, 214, 72, 20);
		frame.getContentPane().add(textFieldCompra2);
		textFieldCompra2.setColumns(10);
		
		textFieldCompra3 = new JTextField();
		textFieldCompra3.setBounds(406, 214, 86, 20);
		frame.getContentPane().add(textFieldCompra3);
		textFieldCompra3.setColumns(10);
		
		textFieldCompra4 = new JTextField();
		textFieldCompra4.setBounds(502, 214, 72, 20);
		frame.getContentPane().add(textFieldCompra4);
		textFieldCompra4.setColumns(10);
		
		JButton btnCompra = new JButton("Compra");
		btnCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refServ.pedidoCompra(Integer.parseInt(textFieldCompra1.getText()), Integer.parseInt(textFieldCompra2.getText()), textFieldCompra3.getText(), Integer.parseInt(textFieldCompra4.getText()), id);
					textFieldCompra1.setText("");
					textFieldCompra2.setText("");
					textFieldCompra3.setText("");
					textFieldCompra4.setText("");
				} catch (NumberFormatException e1) {
				} catch (RemoteException e1) {}
			}
		});
		btnCompra.setBounds(167, 211, 89, 23);
		frame.getContentPane().add(btnCompra);
		
		JButton btnVenda_1 = new JButton("Venda");
		btnVenda_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refServ.pedidoVenda(Integer.parseInt(textFieldVenda1.getText()), Integer.parseInt(textFieldVenda2.getText()), textFieldVenda3.getText(), Integer.parseInt(textFieldVenda4.getText()), id);
					textFieldVenda1.setText("");
					textFieldVenda2.setText("");
					textFieldVenda3.setText("");
					textFieldVenda4.setText("");
				} catch (NumberFormatException e1) {
				} catch (RemoteException e1) {}
			}
		});
		btnVenda_1.setBounds(167, 270, 89, 23);
		frame.getContentPane().add(btnVenda_1);
		
		textFieldVenda1 = new JTextField();
		textFieldVenda1.setColumns(10);
		textFieldVenda1.setBounds(266, 271, 44, 20);
		frame.getContentPane().add(textFieldVenda1);
		
		textFieldVenda2 = new JTextField();
		textFieldVenda2.setColumns(10);
		textFieldVenda2.setBounds(326, 271, 72, 20);
		frame.getContentPane().add(textFieldVenda2);
		
		textFieldVenda3 = new JTextField();
		textFieldVenda3.setColumns(10);
		textFieldVenda3.setBounds(406, 271, 86, 20);
		frame.getContentPane().add(textFieldVenda3);
		
		textFieldVenda4 = new JTextField();
		textFieldVenda4.setColumns(10);
		textFieldVenda4.setBounds(502, 271, 72, 20);
		frame.getContentPane().add(textFieldVenda4);
		
		JButton btnObterCotao = new JButton("Obter Cota\u00E7\u00E3o");
		btnObterCotao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refServ.obterCotacoes(id);
				} catch (RemoteException e1) {}
			}
		});
		btnObterCotao.setBounds(272, 316, 126, 23);
		frame.getContentPane().add(btnObterCotao);
		
		lblEmpresas = new JLabel("Empresas");
		lblEmpresas.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		lblEmpresas.setBounds(58, 220, 72, 14);
		frame.getContentPane().add(lblEmpresas);
		
		txtrExxonmobilPetrobrasOi = new JTextArea();
		txtrExxonmobilPetrobrasOi.setText("exxonmobil\r\npetrobras\r\noi\r\nazul\r\nfacebook");
		txtrExxonmobilPetrobrasOi.setBounds(26, 256, 131, 83);
		frame.getContentPane().add(txtrExxonmobilPetrobrasOi);
		
		lblnomestring = new JLabel("(nome):string");
		lblnomestring.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblnomestring.setBounds(26, 32, 65, 14);
		frame.getContentPane().add(lblnomestring);
		
		lblempresastring = new JLabel("(empresa):string");
		lblempresastring.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblempresastring.setBounds(312, 32, 86, 14);
		frame.getContentPane().add(lblempresastring);
		
		lblempresastring_1 = new JLabel("(empresa):string");
		lblempresastring_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblempresastring_1.setBounds(314, 85, 86, 14);
		frame.getContentPane().add(lblempresastring_1);
		
		lblempresastring_2 = new JLabel("(empresa):string");
		lblempresastring_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblempresastring_2.setBounds(312, 135, 86, 14);
		frame.getContentPane().add(lblempresastring_2);
		
		lbllimiteint = new JLabel("(limite):int");
		lbllimiteint.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbllimiteint.setBounds(406, 135, 53, 14);
		frame.getContentPane().add(lbllimiteint);
		
		lbltempoint = new JLabel("(tipo):string");
		lbltempoint.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbltempoint.setBounds(463, 135, 72, 14);
		frame.getContentPane().add(lbltempoint);
		
		lblqtdeint = new JLabel("(qtde):int");
		lblqtdeint.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblqtdeint.setBounds(266, 194, 53, 14);
		frame.getContentPane().add(lblqtdeint);
		
		lblprecomaxint = new JLabel("(precoMax):int");
		lblprecomaxint.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprecomaxint.setBounds(326, 194, 72, 14);
		frame.getContentPane().add(lblprecomaxint);
		
		lblempresastring_3 = new JLabel("(empresa):string");
		lblempresastring_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblempresastring_3.setBounds(406, 194, 86, 14);
		frame.getContentPane().add(lblempresastring_3);
		
		lbltempoint_1 = new JLabel("(tempo):int");
		lbltempoint_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbltempoint_1.setBounds(502, 194, 65, 14);
		frame.getContentPane().add(lbltempoint_1);
		
		lblqtdeint_1 = new JLabel("(qtde):int");
		lblqtdeint_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblqtdeint_1.setBounds(265, 252, 53, 14);
		frame.getContentPane().add(lblqtdeint_1);
		
		lblprecominint = new JLabel("(precoMin):int");
		lblprecominint.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprecominint.setBounds(327, 253, 71, 14);
		frame.getContentPane().add(lblprecominint);
		
		lblempresastring_4 = new JLabel("(empresa):string");
		lblempresastring_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblempresastring_4.setBounds(408, 254, 84, 14);
		frame.getContentPane().add(lblempresastring_4);
		
		lbltempoint_2 = new JLabel("(tempo):int");
		lbltempoint_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbltempoint_2.setBounds(502, 255, 65, 14);
		frame.getContentPane().add(lbltempoint_2);
		
		JButton btnObterInfos = new JButton("Obter Infos");
		btnObterInfos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					refServ.verInfosAcionista(id);
				}catch(RemoteException e) {}
			}
		});
		btnObterInfos.setBounds(432, 316, 103, 23);
		frame.getContentPane().add(btnObterInfos);
		
		JTextPane textPaneName = new JTextPane();
		textPaneName.setBounds(26, 49, 103, 23);
		frame.getContentPane().add(textPaneName);
		textPaneName.setText(this.name);
		
		JButton btnAcceptDeal = new JButton("Accept");
		JButton btnDenyDeal = new JButton("Deny");
		
		this.refCliente.setButtons(btnAcceptDeal, btnDenyDeal);
		
		btnAcceptDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//envia resposta para coord
					refServ.actionResponse(id, 1);
					
					//deixo invisivel os 2 botoes
					btnAcceptDeal.setVisible(false);
					btnDenyDeal.setVisible(false);
				}catch (Exception e) {}
			}
		});
		btnAcceptDeal.setBounds(456, 28, 89, 23);
		frame.getContentPane().add(btnAcceptDeal);
		
		btnDenyDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//envia resposta para coord
					refServ.actionResponse(id, 0);
					
					//deixo invisivel os 2 botoes
					btnAcceptDeal.setVisible(false);
					btnDenyDeal.setVisible(false);
				}catch(Exception e) {}
			}
		});
		btnDenyDeal.setBounds(456, 62, 89, 23);
		frame.getContentPane().add(btnDenyDeal);
	}
}
