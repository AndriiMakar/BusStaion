package mainPac;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.sql.*;
//import java.security.*;

public class LogInNorm {

	Connection connection=null;
	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField LogInField;
	private JLabel MesLabel;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(
				 UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInNorm window = new LogInNorm();
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
	public LogInNorm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connection = MySQLConnection.dbConnector();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("\u0410\u0432\u0442\u043E\u0440\u0438\u0437\u0430\u0446\u0456\u044F");
		frame.setBounds(100, 100, 259, 162);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 3, 253, 73);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u041B\u043E\u0433\u0456\u043D");
		lblNewLabel.setBounds(10, 17, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C");
		lblNewLabel_1.setBounds(10, 42, 46, 14);
		panel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('™');
		passwordField.setBounds(56, 39, 149, 20);
		panel.add(passwordField);
		
		LogInField = new JTextField();
		LogInField.setBounds(56, 11, 149, 20);
		panel.add(LogInField);
		LogInField.setColumns(10);
		
		JButton button = new JButton("\u0412\u0432\u0456\u0439\u0442\u0438");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				LogIn();
			}
		});
		button.setBounds(77, 103, 89, 23);
		frame.getContentPane().add(button);
		
		MesLabel = new JLabel("");
		MesLabel.setBounds(0, 78, 246, 14);
		frame.getContentPane().add(MesLabel);
	}
	
	
	public String MD5(String md5)
	{
		try{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array=md.digest(md5.getBytes());
			StringBuffer sb= new StringBuffer();
			for(int i=0; i<array.length;++i)
			{
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
			
		}catch(java.security.NoSuchAlgorithmException e){
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public void LogIn()
	{
		String name="";
		String phone="";
		String em="";
		String add="";
		String log="";
		String pas="";
		
		
		MesLabel.setText("");
		
		//String Pas="";
		try{
			String query="SELECT * FROM user WHERE Login=?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, LogInField.getText());
			//pst.setString(2, MD5(passwordField.getText()));
		
			ResultSet rs = pst.executeQuery();
			
			int Role=-1;
			
			if(rs.next())
			{
				name=rs.getString("Initials");
				phone=rs.getString("Phone");
				em=rs.getString("E_mail");
				add=rs.getString("Address");
				log=rs.getString("Login");
						
				pas = rs.getString("Password_");
				Role=rs.getInt("Role");
				
				if(!MD5(passwordField.getText()).equals(pas)){
					
					MesLabel.setText("Неправильний пароль");
					return;
				}
							
			}	
				
			if(Role==1){
				frame.dispose();
			
				AdminNorm af = new AdminNorm(name, em);
				af.setVisible(true);
				
				}
			
			else if(Role==0){
				frame.dispose();
				CashierNorm cf=new CashierNorm(name, phone, em, add, log, pas);
				cf.setVisible(true);
			}
			
			rs.close();
			pst.close();
		}
		
		catch(Exception e)
		
		{
		JOptionPane.showMessageDialog(null, e);
	
		}
	}
}
