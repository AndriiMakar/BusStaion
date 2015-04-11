package mainPac;

import java.awt.EventQueue;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Calendar;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Checkbox;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class CashierNorm extends JFrame {
	Connection connection;
	private JPanel contentPane;
	private JTable table;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JTextField textField;
	
	private JSpinner spinner; 
	private JSpinner spinner_1;
	private JSpinner.DateEditor de ;
	private JSpinner.DateEditor de1 ;
	private JDateChooser dateChooser_2;
	
	private JDateChooser dateChooser_3; 
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JLabel nameLabel;
	private JLabel emailLabel;
	private Checkbox checkbox;
	private JLabel label;
	private JPanel panel;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	@SuppressWarnings("rawtypes")
	private JComboBox PlacecomboBox;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(
				 UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String name="";
					String phone="";
					String em="";
					String add="";
					String log="";
					String pas="";
					
					CashierNorm frame = new CashierNorm(name, phone, em, add, log, pas);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param em 
	 * @param name 
	 */
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void showRoutes()
	{
		try {
			
			String query ="SELECT r.Start_point as 'п.Відправлення',r.Finish_point as 'п. Призначення', r.Distance as 'Відстань', sc.Time_start as 'Час Відправлення', sc.Time_Finish as 'Час прибуття'," 
					+"b.Bus_num as '№ Автобуса ', b.Num_of_tick as'К-сть квитків', Status_b as 'Статус А', t.Ticket_price as 'Вартість квитка', t.Place_num as '№ місця',"
					+"t.Status_t as 'Статис К', l.Name_ as 'н.Пункт', l.Time_FinishL as 'Час прибуття', ttl. Ticket_priceL as 'Вартість квитка', ttl.Place_numL as   '№ місця',"
					+"ttl. Status_tL as 'Статис К' FROM route r left join schedule sc  ON sc.Route_id = r.id left join bus b ON b.Schedule_id = sc.id left join locality l ON l.Bus_id = b.id left join ticket_to_locality ttl ON ttl.Locality_id=l.id left join ticket t ON t.Bus_id = b.id;";
		
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void time_func()
	{
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		SpinnerDateModel sm1 = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);          ////////////TIME FUNC

		
		
		spinner = new JSpinner(sm);
		spinner_1 = new JSpinner(sm1);
	
		
		 de = new JSpinner.DateEditor(spinner, "HH:mm");
		de1 = new JSpinner.DateEditor(spinner_1, "HH:mm");
		
		de.getTextField().setEditable(true);
		de1.getTextField().setEditable(true);
		
		
		spinner.setEditor(de);
		spinner_1.setEditor(de1);
		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void searchRouteTime()
	{
		try
		{
			String query ="SELECT r.Start_point as 'п.Відправлення',r.Finish_point as 'п. Призначення', r.Distance as 'Відстань', sc.Time_start as 'Час Відправлення', sc.Time_Finish as 'Час прибуття'," 
					+"b.Bus_num as '№ Автобуса ', b.Num_of_tick as'К-сть квитків', Status_b as 'Статус А', t.Ticket_price as 'Вартість квитка', t.Place_num as '№ місця',"
					+"t.Status_t as 'Статис К', l.Name_ as 'н.Пункт', l.Time_FinishL as 'Час прибуття', l. Ticket_priceL as 'Вартість квитка', l.Place_numL as   '№ місця',"
					+"l. Status_tL as 'Статис К' FROM route r  left join schedule sc  ON sc.Route_id = r.id left join bus b ON b.Schedule_id = sc.id left join locality l ON l.Bus_id = b.id left join ticket_to_locality ttl ON ttl.Locality_id = l.id left join ticket t ON t.Bus_id = b.id WHERE Time_start BETWEEN ? AND ?";
		
			Date dateFromDateChooser = dateChooser_2.getDate();
			String str = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser);
				
			Date dateFromDateChooser1 = dateChooser_3.getDate();
			String str_1 = String.format("%1$tY-%1$tm-%1$td", dateFromDateChooser1);
					
					String strr = de.getFormat().format(spinner.getValue());
					String str_11 = de1.getFormat().format(spinner_1.getValue());
					
					String tStart = str + " " + strr;
					String tFinish = str_1 + " " + str_11;
			////
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, tStart);
			pst.setString(2, tFinish);
			
			ResultSet rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void searchRoute()
	{
		try
		{
			
			String selItem = (String) comboBox.getSelectedItem();
			if(selItem.equals("ПІП")){selItem="Initials";}
			else if(selItem.equals("п.Відправлення")){selItem="Start_point";}
			else if(selItem.equals("п.Призначення")){selItem="Finish_point";}
			else if(selItem.equals("Вартість квитка")){selItem="Ticket_price";}
			else if(selItem.equals("н.Пункт")){selItem="Name_";}
			
			
			String query ="SELECT r.Start_point as 'п.Відправлення',r.Finish_point as 'п. Призначення', r.Distance as 'Відстань', sc.Time_start as 'Час Відправлення', sc.Time_Finish as 'Час прибуття', b.Bus_num as '№ Автобуса ', b.Num_of_tick as'К-сть квитків', Status_b as 'Статус А', t.Ticket_price as 'Вартість квитка', t.Place_num as '№ місця',"
			+"t.Status_t as 'Статис К', l.Name_ as 'н.Пункт', l.Time_FinishL as 'Час прибуття', ttl. Ticket_priceL as 'Вартість квитка', ttl.Place_numL as   '№ місця', ttl. Status_tL as 'Статис К' FROM route r left join schedule sc  ON sc.Route_id = r.id left join bus b ON b.Schedule_id = sc.id left join locality l ON l.Bus_id = b.id left join ticket_to_locality ttl ON ttl.Locality_id=l.id left join ticket t ON t.Bus_id = b.id WHERE "+selItem+" LIKE ?";
		
			PreparedStatement pst=connection.prepareStatement(query);
			pst.setString(1, '%'+textField.getText()+'%');
			
			ResultSet rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void sellTick()
	{
		try
		{
			String status="Проданий";
						
			String idd="";
			String idd1="";
			int row = table.getSelectedRow();
			String s_p = (table.getModel().getValueAt(row, 0)).toString();
			String f_p = (table.getModel().getValueAt(row, 1)).toString();
			String dis = (table.getModel().getValueAt(row, 2)).toString();
	
		if(checkbox.getState()==true)
		{
			
			String name = (table.getModel().getValueAt(row, 11)).toString();
						
			String t_s = (table.getModel().getValueAt(row, 3)).toString();
			String t_f = (table.getModel().getValueAt(row, 4)).toString();
			String t_pp = (table.getModel().getValueAt(row, 13)).toString();
		
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
						pst.setString(2, t_pp);
						pst.setString(3, PlacecomboBox.getSelectedItem().toString());
						pst.setString(4, status);
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Успішно");
		        
								pst.close();
							  
								pst1.close();
		        }
		
		else if (checkbox.getState()==false)
		{
			String t_s = (table.getModel().getValueAt(row, 3)).toString();
			String t_f = (table.getModel().getValueAt(row, 4)).toString();
			
			String b_n = (table.getModel().getValueAt(row, 5)).toString();
			String n_t = (table.getModel().getValueAt(row, 6)).toString();
		
			String s_b = (table.getModel().getValueAt(row, 7)).toString();
			String t_ppp = (table.getModel().getValueAt(row, 8)).toString();

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
				pst2.setString(2, t_ppp);	
				pst2.setString(3, PlacecomboBox.getSelectedItem().toString());	
				pst2.setString(4, status);	
				
				pst2.execute();
				JOptionPane.showMessageDialog(null, "Успішно");
				pst1.close();
			
	
			
				pst2.close();
		}
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void backTicket()
	{
		try{
			String idd1="";
			int row = table.getSelectedRow();
			String s_p = (table.getModel().getValueAt(row, 0)).toString();
			String f_p = (table.getModel().getValueAt(row, 1)).toString();
			String dis = (table.getModel().getValueAt(row, 2)).toString();
			String t_s = (table.getModel().getValueAt(row, 3)).toString();
			String t_f = (table.getModel().getValueAt(row, 4)).toString();
			String b_n = (table.getModel().getValueAt(row, 5)).toString();
			
			if(checkbox.getState()==true)
			{
				String name = (table.getModel().getValueAt(row, 11)).toString();
				String t_fl = (table.getModel().getValueAt(row, 12)).toString();
				
				String l1 = (table.getModel().getValueAt(row, 13)).toString();
				String l2 = (table.getModel().getValueAt(row, 14)).toString();
				String l3 = (table.getModel().getValueAt(row, 15)).toString();
				
			
				
				String query1="SELECT r.*, sc.*, b.*, l.*, ttl.* FROM route r join schedule sc ON sc.Route_id = r.id join bus b ON b.Schedule_id=sc.id join locality l ON l.Bus_id=b.id join ticket_to_locality ttl ON ttl.Locality_id=l.id WHERE r.Start_point=? " +
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
					
					idd1 = rs1.getString("ttl.id");
					
					
				}
				
				String query="DELETE FROM ticket_to_locality WHERE ticket_to_locality.id=? AND  ticket_to_locality.Ticket_priceL=? AND ticket_to_locality.Place_numL=? AND ticket_to_locality.Status_tL=?";
				
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, idd1);		
					pst.setString(2, l1);	
					pst.setString(3, l2);	
					pst.setString(4, l3);	
							
							pst.execute();
							JOptionPane.showMessageDialog(null, "Успішно");
			        
									pst.close();
								  
									pst1.close();
				
			}
			
			else if (checkbox.getState()==false)
			{
			
			
			
			String num = (table.getModel().getValueAt(row, 6)).toString();
			String s_b = (table.getModel().getValueAt(row, 7)).toString();
			String t_p = (table.getModel().getValueAt(row, 8)).toString();
			String p_n = (table.getModel().getValueAt(row, 9)).toString();
			String s_t = (table.getModel().getValueAt(row, 10)).toString();
		
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
			
				JOptionPane.showMessageDialog(null, "Успішно");
				
				pst.close();
				pst1.close();
			}
		} catch (Exception e) {
				e.printStackTrace();
			}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void bookTicket()
	{
		try
		{
			String status="Заброньований";
			
			
			String idd="";
			String idd1="";
			//String t_p="";
			int row = table.getSelectedRow();
			String s_p = (table.getModel().getValueAt(row, 0)).toString();
			String f_p = (table.getModel().getValueAt(row, 1)).toString();
			String dis = (table.getModel().getValueAt(row, 2)).toString();
	
		if(checkbox.getState()==true)
		{
			
			String name = (table.getModel().getValueAt(row, 11)).toString();
						
			String t_s = (table.getModel().getValueAt(row, 3)).toString();
			String t_f = (table.getModel().getValueAt(row, 4)).toString();
			String t_pp = (table.getModel().getValueAt(row, 13)).toString();
		
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
						pst.setString(2, t_pp);
						pst.setString(3, PlacecomboBox.getSelectedItem().toString());
						pst.setString(4, status);
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Успішно");
		        
								pst.close();
							  
								pst1.close();
		        }
		
		else if (checkbox.getState()==false)
		{
			String t_s = (table.getModel().getValueAt(row, 3)).toString();
			String t_f = (table.getModel().getValueAt(row, 4)).toString();
			
			String b_n = (table.getModel().getValueAt(row, 5)).toString();
			String n_t = (table.getModel().getValueAt(row, 6)).toString();
		
			String s_b = (table.getModel().getValueAt(row, 7)).toString();
			String t_ppp = (table.getModel().getValueAt(row, 8)).toString();

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
				pst2.setString(2, t_ppp);	
				pst2.setString(3, PlacecomboBox.getSelectedItem().toString());	
				pst2.setString(4, status);	
				
				pst2.execute();
				JOptionPane.showMessageDialog(null, "Успішно");
				pst1.close();
			
	
			
				pst2.close();
		}
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateUser()
	{
		try{

			if(textField_2.getText().equals("") || textField_3.getText().equals("") || textField_4.getText().equals("") || textField_5.getText().equals("") || textField_6.getText().equals("") || textField_7.getText().equals(""))
			{				 
				 JOptionPane.showMessageDialog(null, "Порожнє поле");								
			}
			
			else
			{

		
			
	String query="UPDATE user SET Initials='"+textField_2.getText()+"',Phone='"+textField_3.getText()+"',E_mail='"+textField_4.getText()+"',Address='"+textField_5.getText()+"'" +
",Login='"+textField_6.getText()+"',Password_='"+MD5(textField_7.getText())+"' WHERE E_mail='"+textField_4.getText().toString()+"' AND Login='"+textField_6.getText().toString()+"'";
			
			PreparedStatement pst=connection.prepareStatement(query);

				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Дані оновлено");
				
				pst.close();
			
			}
				
		} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	private void placeT()
	{
		try{
			PlacecomboBox.removeAllItems();
		int t1=0;
		int t2=0;
	int row = table.getSelectedRow();
	String s_p = (table.getModel().getValueAt(row, 0)).toString();
	String f_p = (table.getModel().getValueAt(row, 1)).toString();
	String dis = (table.getModel().getValueAt(row, 2)).toString();

	String t_s = (table.getModel().getValueAt(row, 3)).toString();
	String t_f = (table.getModel().getValueAt(row, 4)).toString();

	String b_n = (table.getModel().getValueAt(row, 5)).toString();
	String n_t = (table.getModel().getValueAt(row, 6)).toString();

	String s_b = (table.getModel().getValueAt(row, 7)).toString();

	int n_t1 = (int) (table.getModel().getValueAt(row, 6));

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
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CashierNorm(String name, String phone, String em, String add, String log, String pas) {
		setResizable(false);
		
		connection=MySQLConnection.dbConnector();
		time_func();
		setTitle("\u0424\u043E\u0440\u043C\u0430 \u043A\u0430\u0441\u0438\u0440\u0430");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1305, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 236, 1289, 296);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				placeT();
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("\u0414\u0430\u043D\u0456");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showRoutes();
			}
		});
		btnNewButton.setBounds(10, 202, 89, 23);
		contentPane.add(btnNewButton);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u043F.\u0412\u0456\u0434\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u043D\u044F", "\u043F.\u041F\u0440\u0438\u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F", "\u0412\u0430\u0440\u0442\u0456\u0441\u0442\u044C \u043A\u0432\u0438\u0442\u043A\u0430", "\u043D.\u041F\u0443\u043D\u043A\u0442"}));
		comboBox.setBounds(10, 34, 202, 20);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				searchRoute();
			}
		});
		textField.setBounds(10, 65, 202, 20);
		contentPane.add(textField);
		textField.setColumns(10);
				
		
		spinner.setBounds(143, 96, 69, 20);
		contentPane.add(spinner);
		
		
		spinner_1.setBounds(143, 127, 69, 20);
		contentPane.add(spinner_1);
		
		JButton button = new JButton("\u041F\u043E\u0448\u0443\u043A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchRouteTime();
			}
		});
		button.setBounds(10, 169, 89, 23);
		contentPane.add(button);
		
		dateChooser_2 = new JDateChooser();
		dateChooser_2.setDateFormatString("yyyy-MM-d");
		dateChooser_2.setBounds(10, 96, 123, 20);
		contentPane.add(dateChooser_2);
		
		dateChooser_3 = new JDateChooser();
		dateChooser_3.setDateFormatString("yyyy-MM-d");
		dateChooser_3.setBounds(10, 127, 123, 20);
		contentPane.add(dateChooser_3);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(238, 34, 304, 136);
		contentPane.add(panel);
		panel.setLayout(null);
		
		button_1 = new JButton("\u041F\u0440\u043E\u0434\u0430\u0442\u0438");
		button_1.setBounds(10, 8, 115, 23);
		panel.add(button_1);
		
		button_2 = new JButton("\u0417\u0430\u0431\u0440\u043E\u043D\u044E\u0432\u0430\u0442\u0438");
		button_2.setBounds(10, 42, 115, 23);
		panel.add(button_2);
		
		button_3 = new JButton("\u041E\u0444\u043E\u0440\u043C\u0438\u0442\u0438 \u043F\u043E\u0432\u0435\u0440\u043D\u0435\u043D\u043D\u044F");
		button_3.setBounds(135, 8, 159, 23);
		panel.add(button_3);
		
		button_4 = new JButton("\u0412\u0456\u0434\u043C\u0456\u043D\u0438\u0442\u0438 \u0431\u0440\u043E\u043D\u044C");
		button_4.setBounds(135, 42, 158, 23);
		panel.add(button_4);
		
		checkbox = new Checkbox("\u041D\u0430\u0441\u0435\u043B\u0435\u043D\u0438\u0439 \u043F\u0443\u043D\u043A\u0442");
		checkbox.setBounds(10, 83, 123, 22);
		panel.add(checkbox);
		
		label = new JLabel("\u2116 \u041C\u0456\u0441\u0446\u044F");
		label.setBounds(10, 111, 46, 14);
		panel.add(label);
		
		PlacecomboBox = new JComboBox();
		PlacecomboBox.setBounds(58, 108, 103, 20);
		panel.add(PlacecomboBox);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backTicket();
			}
		});
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backTicket();
			}
		});
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookTicket();
			}
		});
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sellTick();
			}
		});
		
		nameLabel = new JLabel("");
		nameLabel.setBounds(1, 0, 194, 20);
		contentPane.add(nameLabel);
		
		emailLabel = new JLabel("");
		emailLabel.setBounds(192, 0, 182, 21);
		contentPane.add(emailLabel);
		nameLabel.setText(name);
		emailLabel.setText(em);
		
		JLabel label_1 = new JLabel("\u041F\u0406\u041F");
		label_1.setFont(new Font("Georgia", Font.BOLD, 11));
		label_1.setBounds(603, 14, 46, 14);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("\u0422\u0435\u043B\u0435\u0444\u043E\u043D");
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel.setBounds(603, 48, 76, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail");
		lblNewLabel_1.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_1.setBounds(603, 79, 56, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u0410\u0434\u0440\u0435\u0441\u0430");
		lblNewLabel_2.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_2.setBounds(603, 105, 76, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u041B\u043E\u0433\u0456\u043D");
		lblNewLabel_3.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_3.setBounds(603, 138, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C");
		lblNewLabel_4.setFont(new Font("Georgia", Font.BOLD, 11));
		lblNewLabel_4.setBounds(603, 169, 76, 14);
		contentPane.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setBounds(690, 11, 148, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(690, 42, 148, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(690, 73, 148, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(690, 104, 148, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(690, 135, 148, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(690, 166, 148, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		JButton button_5 = new JButton("\u041E\u043D\u043E\u0432\u0438\u0442\u0438 \u0434\u0430\u043D\u0456");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateUser();
			}
		});
		button_5.setBounds(603, 202, 129, 23);
		contentPane.add(button_5);
		textField_2.setText(name);
		textField_3.setText(phone);
		textField_4.setText(em);
		textField_5.setText(add);
		textField_6.setText(log);
		textField_7.setText(pas);
	}
}
