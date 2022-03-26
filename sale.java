import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;


public class sale {

	private JFrame frame;
	private JTextField txtpcode;
	private JTextField txtpname;
	private JTextField txtprice;
	private JTextField txtamount;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sale window = new sale();
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
	static Connection Connect ()
    {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/mydb";
            Class.forName(driver);
            return DriverManager.getConnection(url,"root","");
        }catch (Exception e){
            System.out.println("Connection failed! "+ e);
        }
        return null;
    }
	public void clock()
	{
		
		Thread clock = new Thread()
				{
			public void run()
			{
				try {
					for(;;) {
					Calendar cal = new GregorianCalendar();
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int month = cal.get(Calendar.MONTH);
					int year = cal.get(Calendar.YEAR);

					int second = cal.get(Calendar.SECOND);
					int minute = cal.get(Calendar.MINUTE);
					int hour = cal.get(Calendar.HOUR);
					txtdate.setText("Time  "+hour+":"
					+minute+":"+second+"  Date "+day+"/"+month+"/"+year);
					sleep(500);}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				};
				clock.start();
		
		
		
		
	}
	public sale() {
		
		initialize();
		table_load();
		table_load1();
		table_load3();
		clock();
		
		
		
		
		
	}
	

	public void Balance()
	{
		int total = Integer.parseInt(txtotal.toString());
        int pay = Integer.parseInt(txtpay.getText());
        int bal = total - pay;
       
        txtbal.setText(String.valueOf(bal));
	}
	Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private JTable table_1;
    private JTable table_2;
    private JTable tb;
    private JTextField txtotal;
    private JTextField txtpay;
    private JTextField txtbal;
    private JTextArea txtbill;
    private JLabel txtdate;
    private JSpinner txtqty;
    private JTextField txtsearch;
    private JButton btnNewButton_5;
	public void baised()
	{
		
	}
    public void table_load()
    {
    	con=Connect();
    	try 
    	{
	    pst = con.prepareStatement("select * from products");
	    rs = pst.executeQuery();
	    table_2.setModel(DbUtils.resultSetToTableModel(rs));
	    SimpleDateFormat dFormat=new SimpleDateFormat("E, MMM dd yyyy     					HH:mm:ss");
		Date date = new Date();
		txtdate.setText(dFormat.format(date));
	} 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
	  } 
    }
    public void table_load1()
    {
    	con=Connect();
    	try 
    	{
	    pst = con.prepareStatement("select * from sales");
	    rs = pst.executeQuery();
	    table_1.setModel(DbUtils.resultSetToTableModel(rs));
	} 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
	  } }
    
    	public void table_load3()
        {
        	con=Connect();
        	try 
        	{
        		
                
                
    	    pst = con.prepareStatement("select * from sales");
    	    rs = pst.executeQuery();
    	    txtbill.setText(txtbill.getText()+"///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////\n\t                 bill\n/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////\n");
    	    txtbill.setText(txtbill.getText()+"     product   price   qty\n\n");
    	    while(rs.next())
    	    {
    	    		txtbill.setText(txtbill.getText()+"     "+rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3)+"\n");
    	    }
    	    pst = con.prepareStatement("select sum(price*qty) from sales");
    	    rs = pst.executeQuery();
    	    rs.next();
    	    txtbill.setText(txtbill.getText()+"\t\tnet price = "+rs.getString(1)+" Bath\n\t\tThank you Come Again \n");
    	} 
        	catch (SQLException e) 
        	 {
        		e.printStackTrace();
    	  } 
    }
	
	
    	/* public void bill()
    	 {
    		 con=Connect();
		    try
		    {
		    	
		    	pst = con.prepareStatement("select * from sales");
	    	    rs = pst.executeQuery();
	    	    while(rs.next())
	    	    {
	    	    		txtbill.setText(txtbill.getText()+rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3)+"\n");
	    	    }
	    	    int sum = 0;
                
		        for(int i = 0; i<table_1.getRowCount(); i++)
		        {
		            sum = sum + Integer.parseInt(tb.getValueAt(i, 1).toString());
		        }
		        
		        txtotal.setText(Integer.toString(sum));
				
				     
				      DefaultTableModel model = new DefaultTableModel();
				    
				    model = (DefaultTableModel)tb.getModel();
				     
				    
					txtbill.setText(txtbill.getText() + "******************************************************\n");
				     txtbill.setText(txtbill.getText() + "           POSBILL                                     \n");
				     txtbill.setText(txtbill.getText() + "*******************************************************\n");
				     
				     //Heading
				      txtbill.setText(txtbill.getText() + "Product" + "\t" + "Price" + "\t" + "Amount" + "\n"  );
				      
				      
				      for(int i = 0; i < model.getRowCount(); i++)
				      {
				          
				          String pname = (String)model.getValueAt(i, 1);
				          String price = (String)model.getValueAt(i, 3);
				          String amount = (String)model.getValueAt(i, 4); 
				          
				          txtbill.setText(txtbill.getText() + pname  + "\t" + price + "\t" + amount  + "\n"  );
				
				      }
				      
				      txtbill.setText(txtbill.getText() + "\n");     
				      
				      txtbill.setText(txtbill.getText() + "\t" + "\t" + "Subtotal :" + total + "\n");
				      txtbill.setText(txtbill.getText() + "\t" + "\t" + "Pay :" + pay + "\n");
				      txtbill.setText(txtbill.getText() + "\t" + "\t" + "Balance :" + bal + "\n");
				      txtbill.setText(txtbill.getText() + "\n");
				      txtbill.setText(txtbill.getText() + "*******************************************************\n");
				      txtbill.setText(txtbill.getText() + "           THANK YOU COME AGIN             \n"); 
	    		 
		    }catch (Exception e) 
       	 {
       		e.printStackTrace();
   	  } 
    		 
    		 
		
		        
		    }*/
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1666, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 6, 600, 198);
		panel.setBackground(new Color(218, 165, 32));
		panel.setBorder(new TitledBorder(null, "Sales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product Code");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 25, 138, 21);
		panel.add(lblNewLabel);
		
		JLabel lblPro = new JLabel("Product Name");
		lblPro.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPro.setBounds(145, 25, 112, 21);
		panel.add(lblPro);
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQty.setBounds(280, 25, 46, 21);
		panel.add(lblQty);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(345, 25, 80, 21);
		panel.add(lblPrice);
		
		JLabel lblTotal = new JLabel("Amount");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotal.setBounds(467, 25, 80, 21);
		panel.add(lblTotal);
		
		txtpcode = new JTextField();
		txtpcode.addKeyListener(new KeyAdapter() {
			
			 @Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
		         {
		             String pcode = txtpcode.getText();   
		             try {
		                 Class.forName("com.mysql.jdbc.Driver");
		                 con = DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","");
		                 pst = con.prepareStatement("select * from products where id = ?");
		                 pst.setString(1, pcode);
		                 rs = pst.executeQuery();
		                 
		                 if(rs.next() == false)
		                 {     
		                     JOptionPane.showMessageDialog(null, "Product Code Not Found");  
		                 }
		                 else
		                 {
		                     String pname = rs.getString("pname");
		                      String price = rs.getString("price");
		                      txtpname.setText(pname.trim());
		                      txtprice.setText(price.trim());
		                 }
		             } catch (ClassNotFoundException ex) {
		                 Logger.getLogger(sale.class.getName()).log(Level.SEVERE, null, ex);
		             } catch (SQLException ex) {
		                 Logger.getLogger(sale.class.getName()).log(Level.SEVERE, null, ex);
		             }
		         }
			}
		});
		txtpcode.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpcode.setBounds(10, 57, 112, 30);
		panel.add(txtpcode);
		txtpcode.setColumns(10);
		
		txtpname = new JTextField();
		txtpname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpname.setColumns(10);
		txtpname.setBounds(145, 57, 125, 30);
		panel.add(txtpname);
		
		txtprice = new JTextField();
		txtprice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtprice.setColumns(10);
		txtprice.setBounds(345, 57, 112, 30);
		panel.add(txtprice);
		
		txtamount = new JTextField();
		txtamount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtamount.setColumns(10);
		txtamount.setBounds(467, 57, 112, 30);
		panel.add(txtamount);
		
		txtqty = new JSpinner();
		txtqty.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				int qty = Integer.parseInt(txtqty.getValue().toString());
		        int price = Integer.parseInt(txtprice.getText());
		        int tot = qty * price;
		        
		        txtamount.setText(String.valueOf(tot));
		        
		        int total = Integer.parseInt(txtotal.toString());
		        int pay = Integer.parseInt(txtpay.getText());
		        int bal = total - pay;
		       
		        txtbal.setText(String.valueOf(bal));
		        
		        
		    }
		});
		txtqty.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtqty.setBounds(280, 57, 55, 30);
		panel.add(txtqty);
		
		
		
		JButton btnNewButton_2 = new JButton("add");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel model = new DefaultTableModel();
			        model = (DefaultTableModel)tb.getModel();
			        model.addRow(new Object[]
			                
			        {
			            txtpcode.getText(),
			            txtpname.getText(),
			            txtqty.getValue().toString(),
			            txtprice.getText(),
			            txtamount.getText(),          
			        });
			        int sum = 0;
                    
			        for(int i = 0; i<tb.getRowCount(); i++)
			        {
			            sum = sum + Integer.parseInt(tb.getValueAt(i, 4).toString());
			        }
			        
			        txtotal.setText(Integer.toString(sum));
			       
			}
		});
		btnNewButton_2.setBounds(10, 161, 89, 23);
		panel.add(btnNewButton_2);
		
		txtbal = new JTextField();
		txtbal.setBounds(249, 162, 86, 20);
		panel.add(txtbal);
		txtbal.setColumns(10);
		
		txtpay = new JTextField();
		txtpay.setBounds(249, 131, 86, 20);
		panel.add(txtpay);
		txtpay.setColumns(10);
		
		txtotal = new JTextField();
		txtotal.setBounds(249, 100, 86, 20);
		panel.add(txtotal);
		txtotal.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("PAY");
		lblNewLabel_2_1.setBounds(203, 128, 36, 23);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel_2 = new JLabel("TOTAL");
		lblNewLabel_2.setBounds(193, 98, 46, 23);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel_2_2 = new JLabel("BALANCE");
		lblNewLabel_2_2.setBounds(170, 162, 69, 23);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 528, 1309, 40);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton billbutton = new JButton("BILL");
		billbutton.setFont(new Font("Monospaced", Font.PLAIN, 18));
		billbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Balance();
			    
			                                         
			}
		});
		billbutton.setBounds(1100, 127, 89, 36);
		panel_1.add(billbutton);
		
		JButton txtadd = new JButton("ADD");
		txtadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = Connect();    //เคล็ดลับคความอร่อย
				int row = table_2.getSelectedRow();
                String idh = table_2.getValueAt(row, 3).toString();
                try {
                    try {
                    	txtbill.setText(null);
                        pst = con.prepareStatement("INSERT into sales(pname,price,qty,id,day) select pname,price,qty,id,day from products where id = "+ idh);
                        pst.executeUpdate();
                        pst = con.prepareStatement("delete from products where id = "+idh);
                        pst.executeUpdate();
                        table_load1(); table_load3();table_load();
                        
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                catch(Exception e1) {
                    System.out.println("error: " + e1);
                }
			}
		});
		txtadd.setBounds(256, 11, 89, 23);
		panel_1.add(txtadd);
		
		JButton btnNewButton_1 = new JButton("delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = Connect();    //เคล็ดลับคความอร่อย
				int row = table_1.getSelectedRow();
                String idh = table_1.getValueAt(row, 3).toString();
                try {
                    try {
                    	
                    	txtbill.setText(null);
                        pst = con.prepareStatement("INSERT into products(pname,price,qty,id,day) select pname,price,qty,id,day from sales where id = "+ idh);
                        pst.executeUpdate();
                        pst = con.prepareStatement("delete from sales where id = "+idh);
                       
                        pst.executeUpdate();
                        table_load1(); table_load3();table_load();
                        
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                catch(Exception e1) {
                    System.out.println("error: " + e1);
                }
			}
		});
		btnNewButton_1.setBounds(983, 11, 89, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("print");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try 
			        {
			        	txtbal.print();
			        } 
			        catch (PrinterException ex) {
			            Logger.getLogger(sale.class.getName()).log(Level.SEVERE, null, ex);
			            
			    }
			
			}
		});
		btnNewButton_3.setBounds(1210, 127, 89, 30);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Balance();
			}
			
		});
		btnNewButton_4.setBounds(943, 137, 89, 23);
		panel_1.add(btnNewButton_4);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(610, 25, 699, 158);
		frame.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setBackground(new Color(211, 211, 211));
		scrollPane.setViewportView(table_1);
		
		JLabel lblNewLabel_1 = new JLabel("Shop");
		lblNewLabel_1.setBounds(10, 190, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("sale");
		lblNewLabel_1_1.setBounds(610, 0, 46, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		txtdate = new JLabel("");
		txtdate.setBounds(1319, 6, 312, 40);
		txtdate.setHorizontalAlignment(SwingConstants.CENTER);
		txtdate.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		frame.getContentPane().add(txtdate);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(1319, 57, 312, 511);
		frame.getContentPane().add(scrollPane_2);
		txtbill = new JTextArea();
		scrollPane_2.setRowHeaderView(txtbill);
		
		tb = new JTable();
		tb.setBounds(687, 215, 603, 242);
		frame.getContentPane().add(tb);
		tb.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tb.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Products Code", "Products Name", "QTY", "Price", "Amount"
			}
		));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 215, 667, 246);
		frame.getContentPane().add(scrollPane_1);
		
		table_2 = new JTable();
		table_2.setBackground(new Color(220, 220, 220));
		scrollPane_1.setViewportView(table_2);
		
		txtsearch = new JTextField();
		txtsearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
	                String id = txtsearch.getText().toString();
	                PreparedStatement pst = con.prepareStatement("SELECT * FROM products where ID = ?");
	                pst.setString(1,id);
	                ResultSet rs = pst.executeQuery();
	                table_2.setModel(DbUtils.resultSetToTableModel(rs));
	                pst.close();


	            } catch (SQLException ex) {
	                 System.out.println(ex.getMessage());
	            }
			}
		});
		txtsearch.setBounds(121, 472, 143, 33);
		frame.getContentPane().add(txtsearch);
		txtsearch.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("SEARCH");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 472, 101, 30);
		frame.getContentPane().add(lblNewLabel_3);
		
		btnNewButton_5 = new JButton("refresh ");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_load();
			}
		});
		btnNewButton_5.setSelectedIcon(new ImageIcon("C:\\Users\\Wey\\Downloads\\refresh.png"));
		btnNewButton_5.setIcon(new ImageIcon("C:\\Users\\Wey\\Downloads\\som1 (1).jpg"));
		btnNewButton_5.setBounds(286, 458, 135, 59);
		frame.getContentPane().add(btnNewButton_5);
		tb.getColumnModel().getColumn(0).setPreferredWidth(96);
		tb.getColumnModel().getColumn(1).setPreferredWidth(108);
	}
}
