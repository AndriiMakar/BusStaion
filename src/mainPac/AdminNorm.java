package mainPac;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Checkbox;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Label;

@SuppressWarnings("serial")
public class AdminNorm extends JFrame {

	Connection connection;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxUser;
	private JTextField searchU_textField;
	private JTextField Init_textField;
	private JTextField Phone_textField;
	private JTextField E_mail_textField;
	private JTextField Address_textField;
	private JTextField Login_textField;
	private JTextField Password_textField;
	private JTextField Role_textField;
	private JTable UsersTable;
	private JTable RoutesTable;
	private JTextField startPointField;
	private JTextField endPointField;
	private JTextField disField;
	private JTextField localityField;
	private Checkbox checkbox;
	private JSpinner spinner;
	private JSpinner spinner_1;
	private JSpinner.DateEditor de ;
	private JSpinner.DateEditor de1 ;
	private JSpinner.DateEditor de2 ;
	private JSpinner.DateEditor de3 ;
	private JDateChooser dateChooserF;
	private JDateChooser dateChooserS;
	private Checkbox checkbox_1;
	private JTextField BusNumField;
	private JTextField NumOfTickField;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxStatus;
		private JTextField TickPriceField;
	private Checkbox checkbox_2;
	private JTextField SearchRouteTextField;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxRoute;
	@SuppressWarnings("rawtypes")
	private JComboBox TickComboBox;
	private JSpinner spinner_2;
	private JDateChooser dateChooserSearch;
	
	private JDateChooser dateChooser;
	@SuppressWarnings("rawtypes")
	private JComboBox PlacecomboBox;

	
	private JSpinner spinner_3;
	
	public static void main(String[] args)throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(
				 UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String name="";
					String emm="";
					AdminNorm frame = new AdminNorm(name, emm);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private void showUsers()
	{
		try {
			String query="CAll `show_u`()";
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			UsersTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////
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
	///////////////////////////////////////////////////////////////////////////////////////////
	private void addUser()
	{
		
		
		int rs=-1;
		try {
			
			if(Init_textField.getText().equals("") || Phone_textField.getText().equals("") || E_mail_textField.getText().equals("") || Address_textField.getText().equals("") || Login_textField.getText().equals("") || Password_textField.getText().equals("") || Role_textField.getText().equals(""))
			{
				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");
			
			}
			
			else
			{
				
			
			String query1="CALL `insertInU`('"+Init_textField.getText()+"','"+Phone_textField.getText()+"', '"+E_mail_textField.getText()+"', '"+Address_textField.getText()+"', '"+Login_textField.getText()+"','"+MD5(Password_textField.getText())+"', '"+Role_textField.getText()+"', @rs);";
			
			PreparedStatement pst1=connection.prepareStatement(query1);
		
			ResultSet rs1=pst1.executeQuery();
			while(rs1.next()){
				
				rs=rs1.getInt("@rs");
			}
			
		 if (rs==1){
				
				JOptionPane.showMessageDialog(null, "Такий користувач вже існує");
				} 
	
			}		
		} catch (Exception e) {
			e.printStackTrace();

		}
		showUsers();
	}
	
	
	private void from_jtableUsers()
	{
		try
		{
			int row = UsersTable.getSelectedRow();
			String Init = (UsersTable.getModel().getValueAt(row, 0)).toString();
			String Phone = (UsersTable.getModel().getValueAt(row, 1)).toString();
			String E_mail = (UsersTable.getModel().getValueAt(row, 2)).toString();
			String Add = (UsersTable.getModel().getValueAt(row, 3)).toString();
			String Log = (UsersTable.getModel().getValueAt(row, 4)).toString();
			String Pas = (UsersTable.getModel().getValueAt(row, 5)).toString();
			String Role = (UsersTable.getModel().getValueAt(row, 6)).toString();
			
				Init_textField.setText(Init);
				Phone_textField.setText(Phone);
				E_mail_textField.setText(E_mail);
				Address_textField.setText(Add);
				Login_textField.setText(Log);
				Password_textField.setText(Pas);
				Role_textField.setText(Role);					
				
			
		}catch(Exception e){e.printStackTrace();}
			
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void deleteUser()
	{
		try{
			int row = UsersTable.getSelectedRow();
			String Init = (UsersTable.getModel().getValueAt(row, 0)).toString();
		
			String E_mail = (UsersTable.getModel().getValueAt(row, 2)).toString();
			
			
			String query="CALL `deleteUser`('"+Init+"', '"+E_mail+"');";
			PreparedStatement pst=connection.prepareStatement(query);
			
			
				pst.execute();
				
				
				JOptionPane.showMessageDialog(null, "Дані видалено");
				
				pst.close();
				
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		showUsers();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void updateUser()
	{
		try{

			if(Init_textField.getText().equals("") || Phone_textField.getText().equals("") || E_mail_textField.getText().equals("") || Address_textField.getText().equals("") || Login_textField.getText().equals("") || Password_textField.getText().equals("") || Role_textField.getText().equals(""))
			{				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");								
			}
			
			else
			{
int row = UsersTable.getSelectedRow();
String E_mail = (UsersTable.getModel().getValueAt(row, 2)).toString();
String Log = (UsersTable.getModel().getValueAt(row, 4)).toString();
		
String query="CALL `updateUser`('"+Init_textField.getText()+"', '"+Phone_textField.getText()+"','"+E_mail+"','"+Address_textField.getText()+"','"+Log+"','"+MD5(Password_textField.getText())+"','"+Role_textField.getText()+"');";
//	String query="UPDATE user SET Initials='"+Init_textField.getText()+"',Phone='"+Phone_textField.getText()+"',E_mail='"+E_mail_textField.getText()+"',Address='"+Address_textField.getText()+"'" +
//",Login='"+Login_textField.getText()+"',Password_='"+MD5(Password_textField.getText())+"', Role='"+Role_textField.getText()+"' WHERE E_mail='"+E_mail+"' AND Login='"+Log+"'";
//			
			PreparedStatement pst=connection.prepareStatement(query);

				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Дані оновлено");
				
				pst.close();
			
			}
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		
		showUsers();
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void cleanTextboxes()
	{
		
		Init_textField.setText(null);
		Phone_textField.setText(null);
		E_mail_textField.setText(null);
		Address_textField.setText(null);
		Login_textField.setText(null);
		Password_textField.setText(null);
		Role_textField.setText(null);	
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void searchUser()
	{
		try
		{
			String selItem = (String) comboBoxUser.getSelectedItem();
			if(selItem.equals("ПІП")){selItem="Initials";}
			else if(selItem.equals("Телефон")){selItem="Phone";}
			else if(selItem.equals("E-mail")){selItem="E_mail";}
			else if(selItem.equals("Адреса")){selItem="Address";}
			else if(selItem.equals("Логін")){selItem="Login";}
			else if(selItem.equals("Пароль")){selItem="Password_";}
			else if(selItem.equals("Роль")){selItem="Role";}			
			
						
			String query="SELECT Initials, Phone, E_mail, Address, Login, Password_, Role  FROM user  WHERE "+selItem+" LIKE ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, '%'+searchU_textField.getText()+'%');
			ResultSet rs = pst.executeQuery();
			UsersTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void showRoutes()
	{
		try {
			
			String query ="SELECT r.Start_point as 'п.Відправлення',r.Finish_point as 'п. Призначення', r.Distance as 'Відстань', sc.Time_start as 'Час Відправлення', sc.Time_Finish as 'Час прибуття'," 
					+"b.Bus_num as '№ Автобуса ', b.Num_of_tick as'К-сть квитків', Status_b as 'Статус А', t.Ticket_price as 'Вартість квитка', t.Place_num as '№ місця',"
					+"t.Status_t as 'Статис К', l.Name_ as 'н.Пункт', l.Time_FinishL as 'Час прибуття', ttl. Ticket_priceL as 'Вартість квитка', ttl.Place_numL as   '№ місця',"
					+"ttl. Status_tL as 'Статис К' FROM route r left join schedule sc  ON sc.Route_id = r.id left join bus b ON b.Schedule_id = sc.id left join locality l ON l.Bus_id = b.id left join ticket_to_locality ttl ON ttl.Locality_id=l.id left join ticket t ON t.Bus_id = b.id;";
		
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			RoutesTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static List<String> getWords(String input) {
        List<String> outputList;
        outputList = Arrays.asList(input.split(", "));
        return outputList;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addRoute()
	{
		try {
			
			if(startPointField.getText().equals("") || endPointField.getText().equals("") ||disField.getText().equals(""))
			
			{
				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");
			}
			else
			{
			
			int idd4=0;
		
				
				if(checkbox.getState()==true)
				{
					if(localityField.getText().equals(""))
						
					{
						 
						 JOptionPane.showMessageDialog(null, "Порожнє поле");
					}
					
					else
					{
					int row = RoutesTable.getSelectedRow();
					String stPoint = (RoutesTable.getModel().getValueAt(row, 0)).toString();
					String finPoint = (RoutesTable.getModel().getValueAt(row, 1)).toString();
					String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
					String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
					String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
					String num = (RoutesTable.getModel().getValueAt(row, 5)).toString();
					String query="SELECT * FROM route r left join schedule sc ON sc.Route_id=r.id left join bus b ON b.Schedule_id=sc.id WHERE r.Start_point=? AND r.Finish_point=? AND r.Distance=? AND Time_start=?" +
							"And Time_Finish=? AND Bus_num=?;";
					
					JOptionPane.showMessageDialog(null, "true");

					PreparedStatement pst1 = connection.prepareStatement(query);
					pst1.setString(1, stPoint);
					pst1.setString(2, finPoint);
					pst1.setString(3, dis);
					pst1.setString(4, t_s);
					pst1.setString(5, t_f);
					pst1.setString(6, num);
					ResultSet rs1 = pst1.executeQuery();
										
					while(rs1.next())
					{
						idd4=rs1.getInt("b.id");						
						
					}
					JOptionPane.showMessageDialog(null, idd4);
					
					String input = localityField.getText().toString();
			        List<String> cities = getWords(input);
			        for (String city : cities) {
			        
			            
					String query2="INSERT INTO locality (Name_, Bus_id) VALUES (?,?)";
					PreparedStatement pst2=connection.prepareStatement(query2);
					
							
							
							pst2.setString(1, city);
							pst2.setInt(2, idd4);
							
							pst2.execute();
							JOptionPane.showMessageDialog(null, "Додано");
			        
							pst1.close();
			        }
			       
					}
				}
				
				else if(checkbox.getState()==false)
				{
					String query1="SELECT Start_point, Finish_point, Distance FROM route WHERE  " +
							"Start_point=? AND Finish_point=? AND Distance=?";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1, startPointField.getText());
					pst1.setString(2, endPointField.getText());
					pst1.setString(3, disField.getText());
					
					ResultSet rs1=pst1.executeQuery();
					
					if (rs1.next()) {
						JOptionPane.showMessageDialog(null, "Такий маршрут вже існує");
						} else {
							String query="INSERT INTO route (Start_point, Finish_point, Distance) VALUES (?,?,?)";
							PreparedStatement pst=connection.prepareStatement(query);
					
							pst.setString(1, startPointField.getText());
							pst.setString(2, endPointField.getText());
							pst.setString(3, disField.getText());
							
							
							
							if(startPointField.getText().equals("") || endPointField.getText().equals("") || disField.getText().equals(""))
							{
								 
								 JOptionPane.showMessageDialog(null, "Порожнє поле");													
							}
							
							pst.execute();
							JOptionPane.showMessageDialog(null, "Додано");
							pst.close();
						}
				
					pst1.close();
				}
			}
						
		} catch (Exception e) {
			e.printStackTrace();

		}
		showRoutes();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void from_jtableRoutes()
	{
		try
		{
			int row = RoutesTable.getSelectedRow();
			String stPoint = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String finPoint = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();

			
			startPointField.setText(stPoint);
			endPointField.setText(finPoint);
			disField.setText(dis);
			
			placeT();

			
			
			

		}catch(Exception e){e.printStackTrace();}
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void deleteRoute()
	{
		String idd="";
		String idd2="";
		try{
			
			if(checkbox.getState()==true)
			{
				int row = RoutesTable.getSelectedRow();
				String stPoint = (RoutesTable.getModel().getValueAt(row, 0)).toString();
				String finPoint = (RoutesTable.getModel().getValueAt(row, 1)).toString();
				String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
				String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
				String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
				String num = (RoutesTable.getModel().getValueAt(row, 5)).toString();
				String query1="SELECT * FROM route r left join schedule sc ON sc.Route_id=r.id left join bus b ON b.Schedule_id=sc.id left join locality l ON l.Bus_id=b.id WHERE r.Start_point=? AND r.Finish_point=? AND r.Distance=? AND Time_start=?" +
						"And Time_Finish=? AND Bus_num=? AND Name_=?;";
				
				
				
				String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
				
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, stPoint);
				pst1.setString(2, finPoint);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				pst1.setString(6, num);
				pst1.setString(7, name);
				
				ResultSet rs1 = pst1.executeQuery();
				
				
				while(rs1.next())
				{
					idd = rs1.getString("l.id");
					idd2= rs1.getString("b.id");
					
					
				}
				
				String query="DELETE locality FROM bus join locality ON locality.Bus_id=bus.id WHERE locality.Name_=? AND locality.id=? AND bus.id=?";
				PreparedStatement pst=connection.prepareStatement(query);

				pst.setString(1, name);
				pst.setString(2, idd);
				pst.setString(3, idd2);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Видалено");

					pst.close();
					
			}
	
			
			
				else if(checkbox.getState()==false)
				{
				String query="DELETE  FROM route WHERE Start_point='"+startPointField.getText()+"'AND Finish_point='"+endPointField.getText()+"' AND  Distance='"+disField.getText()+"'";
			PreparedStatement pst=connection.prepareStatement(query);
			
				pst.execute();
				JOptionPane.showMessageDialog(null, "Видалено");
				pst.close();
				
				}
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateRoute()
	{
		int idd=0;
		int idd1=0;
		int idd2=0;
		
	
		try{
			if(startPointField.getText().equals("") || endPointField.getText().equals("") ||disField.getText().equals("") ||localityField.getText().equals(""))
				
			{
				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");
			}
			else
			{
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
						
			if(checkbox.getState()==true)
			{
				
				String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
				String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
				String num = (RoutesTable.getModel().getValueAt(row, 5)).toString();
				String query1="SELECT * FROM route r left join schedule sc ON sc.Route_id=r.id left join bus b ON b.Schedule_id=sc.id left join locality l ON l.Bus_id=b.id WHERE r.Start_point=? AND r.Finish_point=? AND r.Distance=? AND Time_start=?" +
						"And Time_Finish=? AND Bus_num=? AND Name_=?;";
		
				
				String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
			
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				pst1.setString(6, num);
				pst1.setString(7, name);
				
				ResultSet rs1 = pst1.executeQuery();
				
				
				while(rs1.next())
				{
					idd = rs1.getInt("l.id");
					idd2 = rs1.getInt("b.id");
				}
				JOptionPane.showMessageDialog(null, idd);
				
				String query="UPDATE locality l SET Name_='"+localityField.getText()+"' WHERE l.id=? AND l.Bus_id=?; ";
				
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setInt(1, idd);
				pst.setInt(2, idd2);
			
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Дані оновлено");
					pst.close();
					
			}
			
			else if(checkbox.getState()==false){
				
				String query1="SELECT * FROM route r WHERE Start_point=? AND Finish_point=? AND Distance=?;";
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				ResultSet rs1 = pst1.executeQuery();
				
				
				while(rs1.next())
				{
					idd1 = rs1.getInt("r.id");
					
				}
				JOptionPane.showMessageDialog(null,idd);
		
				
				String query="UPDATE route SET Start_point='"+startPointField.getText()+"', Finish_point='"+endPointField.getText()+"', Distance='"+disField.getText()+"' WHERE id = ?; ";
				
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setInt(1, idd1);
				
				if(startPointField.getText().equals("") || endPointField.getText().equals("") || disField.getText().equals(""))
				{
					 
					 JOptionPane.showMessageDialog(null, "Порожнє поле");
			
				}
					pst.executeUpdate();
				
					
					JOptionPane.showMessageDialog(null, "Оновлено");
					
					pst.close();
			}
			
			}
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void time_func()
	{
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		SpinnerDateModel sm1 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);          ////////////TIME FUNC
		SpinnerDateModel sm2 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		SpinnerDateModel sm3 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY); 
		
		
		spinner = new JSpinner(sm);
		spinner_1 = new JSpinner(sm1);
		spinner_2 = new JSpinner(sm2);
		spinner_3 = new JSpinner(sm3);
		
		 de = new JSpinner.DateEditor(spinner, "HH:mm");
		de1 = new JSpinner.DateEditor(spinner_1, "HH:mm");
		de2 = new JSpinner.DateEditor(spinner_2, "HH:mm");
		de3 = new JSpinner.DateEditor(spinner_3, "HH:mm");
		
		de.getTextField().setEditable(true);
		de1.getTextField().setEditable(true);
		de2.getTextField().setEditable(true);
		de3.getTextField().setEditable(true);
		
		spinner.setEditor(de);
		spinner_1.setEditor(de1);
		spinner_2.setEditor(de2);
		spinner_3.setEditor(de3);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addSC()
	{
		int idd=0;
		int idd1=0;
		int idd2=0;
		
		int row = RoutesTable.getSelectedRow();
		String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
		String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
		String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();	
		
		try{
			
			if(checkbox_1.getState()==true)
			{
				String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
				String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
				String num = (RoutesTable.getModel().getValueAt(row, 5)).toString();
				String query1="SELECT * FROM route r left join schedule sc ON sc.Route_id=r.id left join bus b ON b.Schedule_id=sc.id left join locality l ON l.Bus_id=b.id WHERE r.Start_point=? AND r.Finish_point=? AND r.Distance=? AND Time_start=?" +
						"And Time_Finish=? AND Bus_num=? AND Name_=?;";
				String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
				
				
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				pst1.setString(6, num);
				pst1.setString(7, name);
				ResultSet rs1 = pst1.executeQuery();
				
				
				while(rs1.next())
				{
					
					idd1 = rs1.getInt("l.id");
					idd2 = rs1.getInt("b.id");
					
					
				}
			

				Date dateFromDateChooser1 = dateChooserS.getDate();
				String str_1 = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser1);
						
						String str_11 = de1.getFormat().format(spinner_1.getValue());
						
						String tFinish = str_1 + " " + str_11;        
				
						String query="UPDATE locality SET Time_FinishL=?  WHERE locality.id=? AND locality.Bus_id=?";// AND schedule.Time_start=? AND schedule.Time_Finish=? ;";
					//String query="INSERT INTO locality(Bus_id, Name_, Time_FinishL) VALUES(?,?,?)";
						PreparedStatement pst=connection.prepareStatement(query);
												
							pst.setString(1, tFinish);
							pst.setInt(2, idd1);
							pst.setInt(3, idd2);
							pst.execute();
							JOptionPane.showMessageDialog(null, "Додано");
			        
									pst.close();
								  
									pst1.close();
			        }

			else if(checkbox_1.getState()==false)
			{
				
				String query="SELECT * FROM route r WHERE Start_point =? AND Finish_point=? AND Distance=?;";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, s_p);
				pst.setString(2, f_p);
				pst.setString(3, dis);
				ResultSet rs = pst.executeQuery();
				
				
				while(rs.next())
				{
					
					idd = rs.getInt("r.id");
					
					
				}
				
				String query2="INSERT INTO schedule (Route_id, Time_start, Time_Finish) VALUES (?,?,?)";
				PreparedStatement pst2=connection.prepareStatement(query2);
				
				Date dateFromDateChooser = dateChooserF.getDate();
				String str = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser);
					
				Date dateFromDateChooser1 = dateChooserS.getDate();
				String str_1 = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser1);
						
						String strr = de.getFormat().format(spinner.getValue());
						String str_11 = de1.getFormat().format(spinner_1.getValue());
						
						String tStart = str + " " + strr;
						String tFinish = str_1 + " " + str_11;
						
						
						pst2.setInt(1, idd);
						pst2.setString(2, tStart);
						pst2.setString(3, tFinish);
						
						pst2.execute();
						JOptionPane.showMessageDialog(null, "Додано");
						pst2.close();
					
				
			}
	
		} catch (Exception e) {
			e.printStackTrace();

		}
		showRoutes();
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateSC()
	{
		int idd=0;
		int idd1=0;
		int idd2=0;
		int row = RoutesTable.getSelectedRow();
		String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
		String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
		String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
		String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
		String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
		String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
		
		try
		{

			if(checkbox_1.getState()==true)
			{
				
				String num = (RoutesTable.getModel().getValueAt(row, 5)).toString();
				String query1="SELECT * FROM route r left join schedule sc ON sc.Route_id=r.id left join bus b ON b.Schedule_id=sc.id left join locality l ON l.Bus_id=b.id WHERE r.Start_point=? AND r.Finish_point=? AND r.Distance=? AND Time_start=?" +
						"And Time_Finish=? AND Bus_num=? AND Name_=?;";
				
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				pst1.setString(6, num);
				pst1.setString(7, name);
				ResultSet rs1 = pst1.executeQuery();
				
				
				while(rs1.next())
				{
					
					idd1 = rs1.getInt("l.id");
					idd2 = rs1.getInt("b.id");
					
					
				}
				

				Date dateFromDateChooser1 = dateChooserS.getDate();
				String str_1 = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser1);
						
						String str_11 = de1.getFormat().format(spinner_1.getValue());
						
						
						String tFinish = str_1 + " " + str_11;        
			
						String query="UPDATE locality SET Time_FinishL=?  WHERE locality.id=? AND locality.Bus_id=?";// AND schedule.Time_start=? AND schedule.Time_Finish=? ;";
					PreparedStatement pst=connection.prepareStatement(query);
												
							pst.setString(1, tFinish);
							pst.setInt(2, idd1);
							pst.setInt(3, idd2);
							pst.execute();
							JOptionPane.showMessageDialog(null, "Дані оновлено");
			        
									pst.close();
								  
									pst1.close();
			
			
	    
			}
			
			else if(checkbox_1.getState()==false){
			Date dateFromDateChooser = dateChooserF.getDate();
			String str = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser);
				
			Date dateFromDateChooser1 = dateChooserS.getDate();
			String str_1 = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser1);
			
			String strr = de.getFormat().format(spinner.getValue());
			String str_11 = de1.getFormat().format(spinner_1.getValue());
			
			String tStart = str + " " + strr;
			String tFinish = str_1 + " " + str_11;
			
			String query1="SELECT * FROM route r, schedule sc  WHERE r.Start_point=? AND r.Finish_point =? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND sc.Route_id = r.id;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getInt("sc.id");
				
			}
			
			
			String query="UPDATE schedule sc SET Time_start='"+tStart+"', Time_Finish='"+tFinish+"' WHERE sc.id=?; ";
			
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setInt(1, idd);
		
			if(dateChooserF.getDate().equals(null) || dateChooserS.getDate().equals(null) || de.getFormat().format(spinner.getValue()).equals(null))
			{
				 
				 JOptionPane.showMessageDialog(null, "Пусте поле");
				
				
			}
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Оновлено");
				
				pst.close();
				
			}
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void deleteSC()
	{
		
		String idd="";
		int idd3=0;
		//int idd4=0;
		
		int row = RoutesTable.getSelectedRow();
		String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
		String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
		String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
		String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
		String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
		
		
		try{
				
				if(checkbox_1.getState()==true)
				{
					String num = (RoutesTable.getModel().getValueAt(row, 5)).toString();
					String query1="SELECT * FROM route r left join schedule sc ON sc.Route_id=r.id left join bus b ON b.Schedule_id=sc.id left join locality l ON l.Bus_id=b.id left join ticket_to_locality ttl ON ttl.Locality_id=l.id WHERE r.Start_point=? AND r.Finish_point=? AND r.Distance=? AND Time_start=?" +
							"And Time_Finish=? AND Bus_num=? AND Name_=? AND Time_FinishL=?;";
					String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
					String t_f1 = (RoutesTable.getModel().getValueAt(row, 12)).toString();
					
					
					PreparedStatement pst1 = connection.prepareStatement(query1);
					pst1.setString(1, s_p);
					pst1.setString(2, f_p);
					pst1.setString(3, dis);
					pst1.setString(4, t_s);
					pst1.setString(5, t_f);
					pst1.setString(6, num);
					pst1.setString(7, name);
					pst1.setString(8, t_f1);
					ResultSet rs1 = pst1.executeQuery();
					
					
					while(rs1.next())
					{
						
						idd3 = rs1.getInt("l.id");
											
					}
				
						String query="DELETE FROM locality WHERE locality.id=? AND locality.Time_FinishL=?";
							PreparedStatement pst=connection.prepareStatement(query);
													
								pst.setInt(1, idd3);
								pst.setString(2, t_f1);
								
								pst.execute();
								JOptionPane.showMessageDialog(null, "Видалено");
				        
										pst.close();
									  
										pst1.close();	
				}
				
					
				else if (checkbox_1.getState()==false)
				{
				String query1="SELECT * FROM route r, schedule sc WHERE r.Start_point=? AND r.Finish_point =? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND sc.Route_id = r.id;";
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				
					ResultSet rs1 = pst1.executeQuery();
					
					
					while(rs1.next())
					{
						idd = rs1.getString("sc.id");
						
					}
					
				String query="DELETE FROM schedule WHERE Time_start='"+t_s+"' AND Time_finish='"+t_f+"' AND schedule.id =?;"; // OR OR OR
				PreparedStatement pst=connection.prepareStatement(query);
			
				pst.setString(1, idd);
					pst.execute();
					
					
					JOptionPane.showMessageDialog(null, "Видалено");
					
					pst.close();
				}
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addBus()
	{
		try{
			if(BusNumField.getText().equals("") || NumOfTickField.getText().equals(""))
				
			{
				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");
			}
			else
			{
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
			
		
			String idd="";
			String query1="SELECT r.*, sc.* FROM route r join schedule sc ON sc.Route_id = r.id  WHERE r.Start_point=? " +
					"AND r.Finish_point=? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=?;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getString("sc.id");
				
			}
			
						
			String query2="INSERT INTO bus (Schedule_id, Bus_num, Num_of_tick, Status_b) VALUES (?,?,?,?)";
			PreparedStatement pst2=connection.prepareStatement(query2);
					
					pst2.setString(1, idd);
					pst2.setString(2, BusNumField.getText());	
					pst2.setString(3, NumOfTickField.getText());	
					pst2.setString(4, comboBoxStatus.getSelectedItem().toString());	
					
					pst2.execute();
					JOptionPane.showMessageDialog(null, "Додано");
					pst1.close();
				
		
				
					pst2.close();
			}
						
		} catch (Exception e) {
			e.printStackTrace();

		}
		showRoutes();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void deleteBus()
	{
		try{
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
			
			String b_n = (RoutesTable.getModel().getValueAt(row, 5)).toString();
			String n_t = (RoutesTable.getModel().getValueAt(row, 6)).toString();
			String s_b = (RoutesTable.getModel().getValueAt(row, 7)).toString();
		
			String idd="";
			String query1="SELECT r.*, sc.*, b.* FROM route r join schedule sc ON sc.Route_id = r.id  join bus b ON b.Schedule_id = sc.id WHERE r.Start_point=? " +
					"AND r.Finish_point=? AND Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND  b.Bus_num=? AND b.Num_of_tick=? AND b.Status_b=?;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			pst1.setString(6, b_n);
			pst1.setString(7, n_t);
			pst1.setString(8, s_b);
			
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getString("b.id");
				
			}
			String query="DELETE FROM bus WHERE Bus_num=? AND Num_of_tick=? AND Status_b= ? AND bus.id=?"; // OR OR OR
			PreparedStatement pst=connection.prepareStatement(query);
			
			
			pst.setString(1,b_n);
			pst.setString(2, n_t);
			pst.setString(3, s_b);
			pst.setString(4, idd);
			
				pst.execute();
			
				
				JOptionPane.showMessageDialog(null, "Видалено");
				
				pst.close();
				pst1.close();
				
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateBus()
	{

		try
		{
			if(BusNumField.getText().equals("") || NumOfTickField.getText().equals(""))
				
			{
				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");
			}
			else
			{
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
			
			String b_n = (RoutesTable.getModel().getValueAt(row, 5)).toString();
			String n_t = (RoutesTable.getModel().getValueAt(row, 6)).toString();
			String s_b = (RoutesTable.getModel().getValueAt(row, 7)).toString();
		
			String idd="";
			String query1="SELECT r.*, sc.*, b.* FROM route r join schedule sc ON sc.Route_id = r.id  join bus b ON b.Schedule_id = sc.id WHERE r.Start_point=? " +
					"AND r.Finish_point=? AND Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND  b.Bus_num=? AND b. Num_of_tick=? AND b.Status_b=?;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			pst1.setString(6, b_n);
			pst1.setString(7, n_t);
			pst1.setString(8, s_b);
			
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getString("b.id");
				
			}
			
			String query="UPDATE bus b, schedule sc SET Bus_num='"+BusNumField.getText()+"', Num_of_tick='"+NumOfTickField.getText()+"', Status_b='"+comboBoxStatus.getSelectedItem().toString()+"'  WHERE b.id=?; ";
			
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, idd);
		
				pst.executeUpdate();
			
				
				JOptionPane.showMessageDialog(null, "Оновлено");
				
				pst.close();
			
			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addTick()
	{
		try
		{
if(TickPriceField.getText().equals(""))
				
			{
				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");
			}
			else
			{
			
			String idd="";
			String idd1="";
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
	
		if(checkbox_2.getState()==true)
		{
			String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
						
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
		
			String query1="SELECT r.*, sc.*, b.*, l.* FROM route r join schedule sc ON sc.Route_id = r.id join bus b ON b.Schedule_id=sc.id join locality l ON l.Bus_id=b.id  WHERE r.Start_point=? " +
					"AND r.Finish_point=? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND Name_=?;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			pst1.setString(6, name);
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				
				idd1 = rs1.getString("l.id");
				
				
			}
						String query="INSERT INTO ticket_to_locality(Locality_id, Ticket_priceL, Place_numL, Status_tL) VALUES(?,?,?,?);";
				
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setString(1, idd1);		
						pst.setString(2, TickPriceField.getText());
						pst.setString(3, PlacecomboBox.getSelectedItem().toString());
						pst.setString(4, TickComboBox.getSelectedItem().toString());
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Додано");
		        
								pst.close();
							  
								pst1.close();
		        }
		
		else if (checkbox_2.getState()==false)
		{
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
			
			String b_n = (RoutesTable.getModel().getValueAt(row, 5)).toString();
			String n_t = (RoutesTable.getModel().getValueAt(row, 6)).toString();
		
			String s_b = (RoutesTable.getModel().getValueAt(row, 7)).toString();

		String query1="SELECT r.*, sc.*, b.* FROM route r join schedule sc ON sc.Route_id = r.id  join bus b ON b.Schedule_id=sc.id WHERE r.Start_point=? " +
				"AND r.Finish_point=? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND b.Bus_num=? AND b.Num_of_tick=? AND b.Status_b=?;";
		PreparedStatement pst1 = connection.prepareStatement(query1);
		pst1.setString(1, s_p);
		pst1.setString(2, f_p);
		pst1.setString(3, dis);
		pst1.setString(4, t_s);
		pst1.setString(5, t_f);
		pst1.setString(6, b_n);
		pst1.setString(7, n_t);
		pst1.setString(8, s_b);
		
		ResultSet rs1 = pst1.executeQuery();
		
		
		while(rs1.next())
		{
			idd = rs1.getString("b.id");
			
		}
		
					
		String query2="INSERT INTO ticket (Bus_id, Ticket_price, Place_num, Status_t) VALUES (?,?,?,?)";
		PreparedStatement pst2=connection.prepareStatement(query2);
				
				pst2.setString(1, idd);
				pst2.setString(2, TickPriceField.getText());	
				pst2.setString(3, PlacecomboBox.getSelectedItem().toString());	
				pst2.setString(4, TickComboBox.getSelectedItem().toString());	
				
				pst2.execute();
				JOptionPane.showMessageDialog(null, "Додано");
				pst1.close();
			
	
			
				pst2.close();
		}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void deleteTick()
	{
		try{
			String idd1="";
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
			String b_n = (RoutesTable.getModel().getValueAt(row, 5)).toString();
			if(checkbox_2.getState()==true)
			{
				String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
				String t_fl = (RoutesTable.getModel().getValueAt(row, 12)).toString();
				
				String l1 = (RoutesTable.getModel().getValueAt(row, 13)).toString();
				String l2 = (RoutesTable.getModel().getValueAt(row, 14)).toString();
				String l3 = (RoutesTable.getModel().getValueAt(row, 15)).toString();
				
			
				
				String query1="SELECT r.*, sc.*, b.*, l.* FROM route r join schedule sc ON sc.Route_id = r.id join bus b ON b.Schedule_id=sc.id join locality l ON l.Bus_id=b.id join ticket_to_locality ttl ON ttl.Locality_id=l.id WHERE r.Start_point=? " +
						"AND r.Finish_point=? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND b.Bus_num=? AND l.Name_=? AND l.Time_FinishL=?;";
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				pst1.setString(6, b_n);
				pst1.setString(7, name);
				pst1.setString(8, t_fl);
				ResultSet rs1 = pst1.executeQuery();
				
				
				while(rs1.next())
				{
					
					idd1 = rs1.getString("l.id");
					
					
				}
				
				String query="DELETE FROM ticket_to_locality WHERE ticket_to_locality.Locality_id=? AND  ticket_to_locality.Ticket_priceL=? AND ticket_to_locality.Place_numL=? AND ticket_to_locality.Status_tL=?";
				
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, idd1);		
					pst.setString(2, l1);	
					pst.setString(3, l2);	
					pst.setString(4, l3);	
							
							pst.execute();
							JOptionPane.showMessageDialog(null, "Видалено");
			        
									pst.close();
								  
									pst1.close();
				
			}
			
			else if (checkbox_2.getState()==false)
			{
			
			
			
			String num = (RoutesTable.getModel().getValueAt(row, 6)).toString();
			String s_b = (RoutesTable.getModel().getValueAt(row, 7)).toString();
			String t_p = (RoutesTable.getModel().getValueAt(row, 8)).toString();
			String p_n = (RoutesTable.getModel().getValueAt(row, 9)).toString();
			String s_t = (RoutesTable.getModel().getValueAt(row, 10)).toString();
		
			String idd="";
			String query1="SELECT r.*, sc.*, b.*, t.* FROM route r join schedule sc ON sc.Route_id = r.id  join bus b ON b.Schedule_id = sc.id join ticket t ON t.Bus_id=b.id WHERE r.Start_point=? " +
					"AND r.Finish_point=? AND Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND  b.Bus_num=? AND b.Num_of_tick=? AND b.Status_b=? AND Ticket_price=? AND Place_num=? AND Status_t=?;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			
			pst1.setString(6, b_n);
			pst1.setString(7, num);
			pst1.setString(8, s_b);
			
			pst1.setString(9, t_p);
			pst1.setString(10, p_n);
			pst1.setString(11, s_t);
			
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getString("t.id");
				
			}
			
			String query="DELETE FROM ticket WHERE Ticket_price=? AND Place_num=? AND Status_t=? AND ticket.id=?"; // OR OR OR
			PreparedStatement pst=connection.prepareStatement(query);
		
			pst.setString(1, t_p);
			pst.setString(2, p_n);
			pst.setString(3, s_t);
			pst.setString(4, idd);
			
				pst.execute();
			
				JOptionPane.showMessageDialog(null, "Видалено");
				
				pst.close();
				pst1.close();
			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void updateTick()
	{
		try
		{
			String idd="";
			
			int row = RoutesTable.getSelectedRow();
			String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
			String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
			String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();
			String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
			String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();
			String b_n = (RoutesTable.getModel().getValueAt(row, 5)).toString();
			String num = (RoutesTable.getModel().getValueAt(row, 6)).toString();
			String s_b = (RoutesTable.getModel().getValueAt(row, 7)).toString();
			String t_p = (RoutesTable.getModel().getValueAt(row, 8)).toString();
			String p_n = (RoutesTable.getModel().getValueAt(row, 9)).toString();
		
			if(checkbox_2.getState()==true)
			{
				String name = (RoutesTable.getModel().getValueAt(row, 11)).toString();
				String t_fl = (RoutesTable.getModel().getValueAt(row, 12)).toString();
				
				String l1 = (RoutesTable.getModel().getValueAt(row, 13)).toString();
				String l2 = (RoutesTable.getModel().getValueAt(row, 14)).toString();
				String l3 = (RoutesTable.getModel().getValueAt(row, 15)).toString();
				
			
				
				String query1="SELECT r.*, sc.*, b.*, l.* FROM route r join schedule sc ON sc.Route_id = r.id join bus b ON b.Schedule_id=sc.id join locality l ON l.Bus_id=b.id join ticket_to_locality ttl ON ttl.Locality_id=l.id WHERE r.Start_point=? " +
						"AND r.Finish_point=? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND b.Bus_num=? AND l.Name_=? AND l.Time_FinishL=?;";
				PreparedStatement pst1 = connection.prepareStatement(query1);
				pst1.setString(1, s_p);
				pst1.setString(2, f_p);
				pst1.setString(3, dis);
				pst1.setString(4, t_s);
				pst1.setString(5, t_f);
				pst1.setString(6, b_n);
				pst1.setString(7, name);
				pst1.setString(8, t_fl);
			
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getString("l.id");
				
				
			}
		
								
				String query2="UPDATE ticket_to_locality ttl SET Ticket_priceL=?, Place_numL=?, Status_tL=? WHERE ttl.Locality_id=? AND  ttl.Ticket_priceL=? AND ttl.Place_numL=? AND ttl.Status_tL=?";
				PreparedStatement pst2=connection.prepareStatement(query2);
				
				pst2.setString(1, TickPriceField.getText());
				pst2.setString(2, PlacecomboBox.getSelectedItem().toString());
				pst2.setString(3, TickComboBox.getSelectedItem().toString());
				pst2.setString(4, idd);
				pst2.setString(5, l1);
				pst2.setString(6, l2);
				pst2.setString(7, l3);
				
				
				
					pst2.execute();
				JOptionPane.showMessageDialog(null, "Оновлено");
							
				pst2.close();
				pst2.close();
				
			}
			
			
			else if (checkbox_2.getState()==false){
				String s_t1 = (RoutesTable.getModel().getValueAt(row, 10)).toString();
		
			String query1="SELECT r.*, sc.*, b.*, t.* FROM route r join schedule sc ON sc.Route_id = r.id  join bus b ON b.Schedule_id = sc.id join ticket t ON t.Bus_id=b.id WHERE r.Start_point=? " +
					"AND r.Finish_point=? AND Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND  b.Bus_num=? AND b.Num_of_tick=? AND b.Status_b=? AND Ticket_price=? AND Place_num=? AND Status_t=?;";
			PreparedStatement pst1 = connection.prepareStatement(query1);
			pst1.setString(1, s_p);
			pst1.setString(2, f_p);
			pst1.setString(3, dis);
			pst1.setString(4, t_s);
			pst1.setString(5, t_f);
			pst1.setString(6, b_n);
			pst1.setString(7, num);
			pst1.setString(8, s_b);
			pst1.setString(9, t_p);
			pst1.setString(10, p_n);
			pst1.setString(11, s_t1);
			
			ResultSet rs1 = pst1.executeQuery();
			
			
			while(rs1.next())
			{
				idd = rs1.getString("t.id");
				
			}
			
		
			String query="UPDATE ticket t, bus b SET Ticket_price='"+TickPriceField.getText()+"', Place_num='"+PlacecomboBox.getSelectedItem().toString()+"', Status_t='"+TickComboBox.getSelectedItem().toString()+"'  WHERE t.id=?; ";
			
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, idd);
	
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Оновлено");
				
				pst.close();
			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		showRoutes();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void searchRoute()
	{
		try
		{
			
			String selItem = (String) comboBoxRoute.getSelectedItem();
			if(selItem.equals("ПІП")){selItem="Initials";}
			else if(selItem.equals("п.Відправлення")){selItem="Start_point";}
			else if(selItem.equals("п.Призначення")){selItem="Finish_point";}
		
			else if(selItem.equals("Вартість квитка")){selItem="Ticket_price";}
			else if(selItem.equals("н.Пункт")){selItem="Name_";}
			
			
			String query ="SELECT r.Start_point as 'п.Відправлення',r.Finish_point as 'п. Призначення', r.Distance as 'Відстань', sc.Time_start as 'Час Відправлення', sc.Time_Finish as 'Час прибуття', b.Bus_num as '№ Автобуса ', b.Num_of_tick as'К-сть квитків', Status_b as 'Статус А', t.Ticket_price as 'Вартість квитка', t.Place_num as '№ місця',"
					+"t.Status_t as 'Статис К', l.Name_ as 'н.Пункт', l.Time_FinishL as 'Час прибуття', ttl. Ticket_priceL as 'Вартість квитка', ttl.Place_numL as   '№ місця', ttl. Status_tL as 'Статис К' FROM route r left join schedule sc  ON sc.Route_id = r.id left join bus b ON b.Schedule_id = sc.id left join locality l ON l.Bus_id = b.id left join ticket_to_locality ttl ON ttl.Locality_id=l.id left join ticket t ON t.Bus_id = b.id WHERE "+selItem+" LIKE ?";
				
		
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, '%'+SearchRouteTextField.getText()+'%');
			
			ResultSet rs = pst.executeQuery();
		    RoutesTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void searchRouteTime()
	{
		try
		{
			String query ="SELECT r.Start_point as 'п.Відправлення',r.Finish_point as 'п. Призначення', r.Distance as 'Відстань', sc.Time_start as 'Час Відправлення', sc.Time_Finish as 'Час прибуття'," 
					+"b.Bus_num as '№ Автобуса ', b.Num_of_tick as'К-сть квитків', Status_b as 'Статус А', t.Ticket_price as 'Вартість квитка', t.Place_num as '№ місця',"
					+"t.Status_t as 'Статис К', l.Name_ as 'н.Пункт', l.Time_FinishL as 'Час прибуття', l. Ticket_priceL as 'Вартість квитка', l.Place_numL as   '№ місця',"
					+"l. Status_tL as 'Статис К' FROM route r  left join schedule sc  ON sc.Route_id = r.id left join bus b ON b.Schedule_id = sc.id left join locality l ON l.Bus_id = b.id left join ticket_to_locality ttl ON ttl.Locality_id = l.id left join ticket t ON t.Bus_id = b.id WHERE Time_start BETWEEN ? AND ?";
		
			Date dateFromDateChooser = dateChooserSearch.getDate();
			String str = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser);
				
			Date dateFromDateChooser1 = dateChooser.getDate();
			String str_1 = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser1);
					
					String strr = de.getFormat().format(spinner_2.getValue());
					String str_11 = de1.getFormat().format(spinner_3.getValue());
					
					String tStart = str + " " + strr;
					String tFinish = str_1 + " " + str_11;
			////
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, tStart);
			pst.setString(2, tFinish);
			
			ResultSet rs = pst.executeQuery();
		    RoutesTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@SuppressWarnings("unchecked")
private void placeT()
{
	try{
		PlacecomboBox.removeAllItems();
	int t1=0;
	int t2=0;
int row = RoutesTable.getSelectedRow();
String s_p = (RoutesTable.getModel().getValueAt(row, 0)).toString();
String f_p = (RoutesTable.getModel().getValueAt(row, 1)).toString();
String dis = (RoutesTable.getModel().getValueAt(row, 2)).toString();

String t_s = (RoutesTable.getModel().getValueAt(row, 3)).toString();
String t_f = (RoutesTable.getModel().getValueAt(row, 4)).toString();

String b_n = (RoutesTable.getModel().getValueAt(row, 5)).toString();
String n_t = (RoutesTable.getModel().getValueAt(row, 6)).toString();

String s_b = (RoutesTable.getModel().getValueAt(row, 7)).toString();

int n_t1 = (int) (RoutesTable.getModel().getValueAt(row, 6));

	for(int i=1; i<n_t1+1;i++)
	{
		PlacecomboBox.addItem(i);	
		}


		String query1="SELECT r.*, sc.*, b.*, t.*, ttl.* FROM route r left join schedule sc ON sc.Route_id = r.id  left join bus b ON b.Schedule_id=sc.id left join locality l ON l.Bus_id =b.id left join ticket_to_locality ttl ON ttl.Locality_id=l.id left join ticket t ON t.Bus_id=b.id WHERE r.Start_point=? " +
		
	"AND r.Finish_point=? AND r.Distance = ? AND sc.Time_start=? AND sc.Time_finish=? AND b.Bus_num=? AND b.Num_of_tick=? AND b.Status_b=?;";
PreparedStatement pst1 = connection.prepareStatement(query1);
pst1.setString(1, s_p);
pst1.setString(2, f_p);
pst1.setString(3, dis);
pst1.setString(4, t_s);
pst1.setString(5, t_f);
pst1.setString(6, b_n);
pst1.setString(7, n_t);
pst1.setString(8, s_b);


ResultSet rs1 = pst1.executeQuery();

while(rs1.next())
{
	t1=rs1.getInt("t.Place_num");PlacecomboBox.removeItem(t1);
	t2=rs1.getInt("ttl.Place_numL");PlacecomboBox.removeItem(t2);
	
}
	
	}catch (Exception e) {
		e.printStackTrace();
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Create the frame.
	 * @param em 
	 * @param name 
	 * @param name12 
	 * @param  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminNorm(String name1, String em) {
		time_func();
		
		connection=MySQLConnection.dbConnector();
		
		
		setTitle("\u0410\u0434\u043C\u0456\u043D \u043F\u0430\u043D\u0435\u043B\u044C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1303, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tabbedPane.setBounds(0, 24, 523, 211);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("\u041A\u043E\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0456", null, panel, null);
		panel.setLayout(null);
		
		comboBoxUser = new JComboBox();
		comboBoxUser.setFont(new Font("Calibri", Font.PLAIN, 14));
		comboBoxUser.setModel(new DefaultComboBoxModel(new String[] {"\u041F\u0406\u041F", "\u0422\u0435\u043B\u0435\u0444\u043E\u043D", "E-mail", "\u0410\u0434\u0440\u0435\u0441\u0430", "\u041B\u043E\u0433\u0456\u043D", "\u041F\u0430\u0440\u043E\u043B\u044C", "\u0420\u043E\u043B\u044C"}));
		comboBoxUser.setBounds(10, 11, 115, 20);
		panel.add(comboBoxUser);
		
		JLabel Init_label = new JLabel("\u041F\u0406\u041F");
		Init_label.setFont(new Font("Georgia", Font.BOLD, 11));
		Init_label.setBounds(10, 42, 46, 14);
		panel.add(Init_label);
		
		JLabel Phone_label = new JLabel("\u0422\u0435\u043B\u0435\u0444\u043E\u043D");
		Phone_label.setFont(new Font("Georgia", Font.BOLD, 11));
		Phone_label.setBounds(10, 67, 60, 14);
		panel.add(Phone_label);
		
		JLabel E_mail_label = new JLabel("E-mail");
		E_mail_label.setFont(new Font("Georgia", Font.BOLD, 11));
		E_mail_label.setBounds(10, 92, 46, 14);
		panel.add(E_mail_label);
		
		JLabel Adress_label = new JLabel("\u0410\u0434\u0440\u0435\u0441\u0430");
		Adress_label.setFont(new Font("Georgia", Font.BOLD, 11));
		Adress_label.setBounds(10, 117, 60, 14);
		panel.add(Adress_label);
		
		JLabel Login_label = new JLabel("\u041B\u043E\u0433\u0456\u043D");
		Login_label.setFont(new Font("Georgia", Font.BOLD, 11));
		Login_label.setBounds(317, 14, 46, 14);
		panel.add(Login_label);
		
		JLabel Pass_label = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C");
		Pass_label.setFont(new Font("Georgia", Font.BOLD, 11));
		Pass_label.setBounds(317, 38, 60, 14);
		panel.add(Pass_label);
		
		JLabel Role_label = new JLabel("\u0420\u043E\u043B\u044C");
		Role_label.setFont(new Font("Georgia", Font.BOLD, 11));
		Role_label.setBounds(317, 63, 46, 14);
		panel.add(Role_label);
		
		searchU_textField = new JTextField();
		searchU_textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				searchUser();
			}
		});
		searchU_textField.setBounds(134, 11, 125, 20);
		panel.add(searchU_textField);
		searchU_textField.setColumns(10);
		
		Init_textField = new JTextField();
		Init_textField.setBounds(76, 42, 183, 20);
		panel.add(Init_textField);
		Init_textField.setColumns(10);
		
		Phone_textField = new JTextField();
		Phone_textField.setBounds(76, 64, 183, 20);
		panel.add(Phone_textField);
		Phone_textField.setColumns(10);
		
		E_mail_textField = new JTextField();
		E_mail_textField.setBounds(76, 89, 183, 20);
		panel.add(E_mail_textField);
		E_mail_textField.setColumns(10);
		
		Address_textField = new JTextField();
		Address_textField.setBounds(76, 114, 183, 20);
		panel.add(Address_textField);
		Address_textField.setColumns(10);
		
		Login_textField = new JTextField();
		Login_textField.setBounds(383, 11, 125, 20);
		panel.add(Login_textField);
		Login_textField.setColumns(10);
		
		Password_textField = new JTextField();
		Password_textField.setBounds(383, 35, 125, 20);
		panel.add(Password_textField);
		Password_textField.setColumns(10);
		
		Role_textField = new JTextField();
		Role_textField.setBounds(383, 58, 125, 20);
		panel.add(Role_textField);
		Role_textField.setColumns(10);
		
		JButton DeleteButton = new JButton("\u0412\u0438\u0434\u0430\u043B\u0438\u0442\u0438 \u043A\u043E\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0430");
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteUser();
			}
		});
		DeleteButton.setBounds(317, 114, 161, 20);
		panel.add(DeleteButton);
		
		JButton UpdateButton = new JButton("\u041E\u043D\u043E\u0432\u0438\u0442\u0438 \u0456-\u044E");
		UpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateUser();
			}
		});
		UpdateButton.setBounds(317, 143, 161, 27);
		panel.add(UpdateButton);
		
		JButton ShowDataButton = new JButton("\u0414\u0430\u043D\u0456");
		ShowDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showUsers();                 
			}
		});
		ShowDataButton.setBounds(10, 142, 147, 28);
		panel.add(ShowDataButton);
		
		JButton ClearButton = new JButton("\u041E\u0447\u0438\u0441\u0442\u0438\u0442\u0438");
		ClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanTextboxes();
			}
		});
		ClearButton.setBounds(160, 143, 147, 27);
		panel.add(ClearButton);
		
		JButton AddButton = new JButton("\u0414\u043E\u0434\u0430\u0442\u0438\r\n \u043A\u043E\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0430");
		AddButton.setBounds(317, 92, 161, 21);
		panel.add(AddButton);
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addUser();
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u041C\u0430\u0440\u0448\u0440\u0443\u0442\u0438", null, panel_1, null);
		tabbedPane.setEnabledAt(1, true);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u041F\u0443\u043D\u043A\u0442 \u0432\u0456\u0434\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u043D\u044F");
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 11, 154, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u041F\u0443\u043D\u043A\u0442 \u043F\u0440\u0438\u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		lblNewLabel_1.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 42, 138, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u0412\u0456\u0434\u0441\u0442\u0430\u043D\u044C");
		lblNewLabel_2.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 67, 112, 14);
		panel_1.add(lblNewLabel_2);
		
		startPointField = new JTextField();
		startPointField.setBounds(164, 8, 118, 20);
		panel_1.add(startPointField);
		startPointField.setColumns(10);
		
		endPointField = new JTextField();
		endPointField.setBounds(164, 36, 118, 20);
		panel_1.add(endPointField);
		endPointField.setColumns(10);
		
		disField = new JTextField();
		disField.setBounds(164, 64, 118, 20);
		panel_1.add(disField);
		disField.setColumns(10);
		
		localityField = new JTextField();
		localityField.setBounds(164, 94, 118, 20);
		panel_1.add(localityField);
		localityField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u0414\u0430\u043D\u0456");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRoutes();
			}
		});
		btnNewButton.setBounds(10, 149, 89, 23);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u0414\u043E\u0434\u0430\u0442\u0438");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addRoute();
			}
		});
		btnNewButton_1.setBounds(118, 149, 89, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\u0412\u0438\u0434\u0430\u043B\u0438\u0442\u0438");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRoute();
			}
		});
		btnNewButton_2.setBounds(217, 149, 89, 23);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("\u041E\u043D\u043E\u0432\u0438\u0442\u0438");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRoute();
			}
		});
		btnNewButton_3.setBounds(316, 149, 89, 23);
		panel_1.add(btnNewButton_3);
		
		checkbox = new Checkbox("\u041F\u0440\u043E\u043C\u0456\u0436\u043D\u0456 \u043F\u0443\u043D\u043A\u0442\u0438");
		checkbox.setFont(new Font("Georgia", Font.PLAIN, 11));
		checkbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(checkbox.getState()==true)
				{
					
					startPointField.setEnabled(false);
					endPointField.setEnabled(false);
					disField.setEnabled(false);
				}

				
				else if(checkbox.getState()==false)
				{
					startPointField.setEnabled(true);
					endPointField.setEnabled(true);
					disField.setEnabled(true);
				}
			}
		});
		checkbox.setBounds(10, 92, 102, 22);
		panel_1.add(checkbox);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u0420\u043E\u0437\u043A\u043B\u0430\u0434", null, panel_2, null);
		panel_2.setLayout(null);
		
		dateChooserF = new JDateChooser();
		dateChooserF.setDateFormatString("yyyy-MM-d");
		dateChooserF.setBounds(148, 21, 110, 20);
		panel_2.add(dateChooserF);
		
		dateChooserS = new JDateChooser();
		dateChooserS.setDateFormatString("yyyy-MM-d");
		dateChooserS.setBounds(148, 52, 110, 20);
		panel_2.add(dateChooserS);
		
	
		spinner.setModel(new SpinnerDateModel(new Date(1417125600000L), null, null, Calendar.HOUR_OF_DAY));
		spinner.setBounds(265, 21, 79, 20);
		panel_2.add(spinner);
		
	
		spinner_1.setBounds(268, 52, 79, 20);
		panel_2.add(spinner_1);
		
		JLabel lblNewLabel_3 = new JLabel("\u0427\u0430\u0441 \u0432\u0456\u0434\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u043D\u044F");
		lblNewLabel_3.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 21, 128, 14);
		panel_2.add(lblNewLabel_3);
		
		JLabel label = new JLabel("\u0427\u0430\u0441 \u043F\u0440\u0438\u0431\u0443\u0442\u0442\u044F");
		label.setFont(new Font("Georgia", Font.BOLD, 11));
		label.setBounds(10, 52, 128, 14);
		panel_2.add(label);
		
		JButton button = new JButton("\u0414\u043E\u0434\u0430\u0442\u0438");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addSC();
			}
		});
		button.setBounds(109, 122, 117, 23);
		panel_2.add(button);
		
		checkbox_1 = new Checkbox("\u041D\u0430\u0441\u0435\u043B\u0435\u043D\u0438\u0439 \u043F\u0443\u043D\u043A\u0442");
		checkbox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(checkbox_1.getState()==true)
				{
					dateChooserF.setEnabled(false);
					spinner.setEnabled(false);
				}

				
				else if(checkbox_1.getState()==false)
				{
					dateChooserF.setEnabled(true);
					spinner.setEnabled(true);
				}
			}
		});
		checkbox_1.setBounds(10, 94, 115, 22);
		panel_2.add(checkbox_1);
		
		JButton button_1 = new JButton("\u041E\u043D\u043E\u0432\u0438\u0442\u0438");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSC();
			}
		});
		button_1.setBounds(236, 122, 89, 23);
		panel_2.add(button_1);
		
		JButton button_2 = new JButton("\u0414\u0430\u043D\u0456");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showRoutes();
			}
		});
		button_2.setBounds(10, 122, 89, 23);
		panel_2.add(button_2);
		
		JButton button_3 = new JButton("\u0412\u0438\u0434\u0430\u043B\u0438\u0442\u0438");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteSC();
			}
		});
		button_3.setBounds(330, 122, 89, 23);
		panel_2.add(button_3);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("\u0410\u0432\u0442\u043E\u0431\u0443\u0441\u0438", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("\u2116 \u0410\u0432\u0442\u043E\u0431\u0443\u0441\u0430");
		lblNewLabel_4.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_4.setBounds(20, 30, 94, 14);
		panel_5.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u041A-\u0441\u0442\u044C \u043A\u0432\u0438\u0442\u043A\u0456\u0432");
		lblNewLabel_5.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_5.setBounds(20, 57, 94, 14);
		panel_5.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\u0421\u0442\u0430\u0442\u0443\u0441");
		lblNewLabel_6.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_6.setBounds(20, 86, 46, 14);
		panel_5.add(lblNewLabel_6);
		
		BusNumField = new JTextField();
		BusNumField.setBounds(124, 27, 86, 20);
		panel_5.add(BusNumField);
		BusNumField.setColumns(10);
		
		NumOfTickField = new JTextField();
		NumOfTickField.setBounds(124, 54, 86, 20);
		panel_5.add(NumOfTickField);
		NumOfTickField.setColumns(10);
		
		comboBoxStatus = new JComboBox();
		comboBoxStatus.setFont(new Font("Calibri", Font.PLAIN, 14));
		comboBoxStatus.setModel(new DefaultComboBoxModel(new String[] {"\u041E\u0447\u0456\u043A\u0443\u0454\u0442\u044C\u0441\u044F", "\u0412\u0456\u0434\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u0438\u0439"}));
		comboBoxStatus.setBounds(123, 83, 102, 20);
		panel_5.add(comboBoxStatus);
		
		JButton btnNewButton_4 = new JButton("\u0414\u0430\u043D\u0456");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRoutes();
			}
		});
		btnNewButton_4.setBounds(10, 137, 89, 23);
		panel_5.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("\u0414\u043E\u0434\u0430\u0442\u0438");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBus();
			}
		});
		btnNewButton_5.setBounds(121, 137, 89, 23);
		panel_5.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("\u0412\u0438\u0434\u0430\u043B\u0438\u0442\u0438");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBus();
			}
		});
		btnNewButton_6.setBounds(217, 137, 89, 23);
		panel_5.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("\u041E\u043D\u043E\u0432\u0438\u0442\u0438");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			updateBus();
			}
		});
		btnNewButton_7.setBounds(316, 137, 89, 23);
		panel_5.add(btnNewButton_7);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("\u041A\u0432\u0438\u0442\u043E\u043A", null, panel_6, null);
		panel_6.setLayout(null);
		
		JLabel label_1 = new JLabel("\u0412\u0430\u0440\u0442\u0456\u0441\u0442\u044C \u043A\u0432\u0438\u0442\u043A\u0430");
		label_1.setFont(new Font("Georgia", Font.BOLD, 11));
		label_1.setBounds(24, 22, 139, 14);
		panel_6.add(label_1);
		
		JLabel label_2 = new JLabel("\u2116 \u041C\u0456\u0441\u0446\u044F");
		label_2.setFont(new Font("Georgia", Font.BOLD, 11));
		label_2.setBounds(24, 47, 89, 14);
		panel_6.add(label_2);
		
		JLabel label_3 = new JLabel("\u0421\u0442\u0430\u0442\u0443\u0441");
		label_3.setFont(new Font("Georgia", Font.BOLD, 11));
		label_3.setBounds(24, 79, 46, 14);
		panel_6.add(label_3);
		
		TickComboBox = new JComboBox();
		TickComboBox.setFont(new Font("Calibri", Font.PLAIN, 14));
		TickComboBox.setModel(new DefaultComboBoxModel(new String[] {"\u041F\u0440\u043E\u0434\u0430\u043D\u0438\u0439", "\u0417\u0430\u0431\u0440\u043E\u043D\u044C\u043E\u0432\u0430\u043D\u0438\u0439"}));
		TickComboBox.setBounds(173, 76, 89, 20);
		panel_6.add(TickComboBox);
		
		checkbox_2 = new Checkbox("\u041F\u0440\u043E\u043C\u0456\u0436\u043D\u0456 \u043F\u0443\u043D\u043A\u0442\u0438");
		checkbox_2.setBounds(10, 122, 118, 22);
		panel_6.add(checkbox_2);
		
		TickPriceField = new JTextField();
		TickPriceField.setBounds(173, 19, 86, 20);
		panel_6.add(TickPriceField);
		TickPriceField.setColumns(10);
		
		JButton button_4 = new JButton("\u0414\u0430\u043D\u0456");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRoutes();
			}
		});
		button_4.setBounds(10, 150, 89, 23);
		panel_6.add(button_4);
		
		JButton button_5 = new JButton("\u0414\u043E\u0434\u0430\u0442\u0438");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTick();
			}
		});
		button_5.setBounds(109, 150, 89, 23);
		panel_6.add(button_5);
		
		JButton button_6 = new JButton("\u0412\u0438\u0434\u0430\u043B\u0438\u0442\u0438");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTick();
			}
		});
		button_6.setBounds(208, 150, 89, 23);
		panel_6.add(button_6);
		
		JButton button_7 = new JButton("\u041E\u043D\u043E\u0432\u0438\u0442\u0438");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTick();
			}
		});
		button_7.setBounds(316, 150, 89, 23);
		panel_6.add(button_7);
		
		PlacecomboBox = new JComboBox();
	
		PlacecomboBox.setBounds(173, 44, 89, 20);
		panel_6.add(PlacecomboBox);
		
		
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setFont(new Font("Georgia", Font.BOLD, 11));
		tabbedPane_1.setBounds(0, 234, 1287, 390);
		contentPane.add(tabbedPane_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("\u041A\u043E\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0456", null, panel_3, null);
		
		panel_3.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1365, 362);
		panel_3.add(scrollPane);
		
		UsersTable = new JTable();
		UsersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				from_jtableUsers();
			}
		});
		scrollPane.setViewportView(UsersTable);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("\u0420\u0435\u0439\u0441\u0438", null, panel_4, null);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 1282, 362);
		panel_4.add(scrollPane_1);
		
		RoutesTable = new JTable();
		RoutesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				from_jtableRoutes();
			}
		});
		scrollPane_1.setViewportView(RoutesTable);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_7.setBounds(533, 55, 288, 187);
		contentPane.add(panel_7);
		panel_7.setLayout(null);
		
		comboBoxRoute = new JComboBox();
		comboBoxRoute.setFont(new Font("Calibri", Font.PLAIN, 14));
		comboBoxRoute.setBounds(11, 11, 201, 20);
		panel_7.add(comboBoxRoute);
		comboBoxRoute.setModel(new DefaultComboBoxModel(new String[] {"\u043F.\u0412\u0456\u0434\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u043D\u044F", "\u043F.\u041F\u0440\u0438\u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F", "\u0412\u0430\u0440\u0442\u0456\u0441\u0442\u044C \u043A\u0432\u0438\u0442\u043A\u0430", "\u043D.\u041F\u0443\u043D\u043A\u0442"}));
		
		SearchRouteTextField = new JTextField();
		SearchRouteTextField.setBounds(11, 42, 201, 20);
		panel_7.add(SearchRouteTextField);
		SearchRouteTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				searchRoute();
			}
		});
		SearchRouteTextField.setColumns(10);
		
		dateChooserSearch = new JDateChooser();
		dateChooserSearch.setDateFormatString("yyyy-MM-d");
		
		dateChooserSearch.setBounds(11, 101, 118, 20);
		panel_7.add(dateChooserSearch);
		
		//spinner_2 = new JSpinner();
		spinner_2.setBounds(133, 101, 79, 20);
		panel_7.add(spinner_2);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-d");
		dateChooser.setBounds(11, 132, 118, 20);
		panel_7.add(dateChooser);
		
		
		spinner_3.setBounds(133, 132, 79, 20);
		panel_7.add(spinner_3);
		
		JButton button_8 = new JButton("\u041F\u043E\u0448\u0443\u043A");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchRouteTime();
			}
		});
		button_8.setBounds(10, 153, 89, 23);
		panel_7.add(button_8);
		
		JLabel label_4 = new JLabel("\u041F\u043E\u0448\u0443\u043A");
		label_4.setFont(new Font("Georgia", Font.BOLD, 11));
		label_4.setBounds(533, 30, 46, 14);
		contentPane.add(label_4);
		
		Label label_5 = new Label("");
		label_5.setBounds(0, 0, 153, 18);
		contentPane.add(label_5);
		label_5.setText(name1);
		
		Label label_6 = new Label("");
		label_6.setBounds(151, 0, 144, 18);
		contentPane.add(label_6);
		label_6.setText(em);
		
	}
}
