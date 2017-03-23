package gmsis;

//import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 * @author Prashant
 */
public class diagnosis extends JPanel {
    JPanel panel = new JPanel();    
    private Connection conn = null;
    private PreparedStatement pst = null;
    private JLabel BookingID;
    private JLabel CarIssue;
    private JLabel Mileage;
    private JLabel VehicleID;
    private JLabel Status;

    private JTextField bookingID;
    private JTextField carIssue;
    private JTextField mileage;
    private JTextField vehicleID;
    private JTextField status;
    
    private String [] carissueoptions = {"test","test","test","test","test","test","test"};
    private JComboBox carissue;
    private JComboBox partCombo;
    
    private String [] vehiclestatus = {"On-going", "Repaired"};
    private JComboBox vehicleStatus;

    private JButton insert;
    private JButton remove;
    private JButton edit;
    private JButton refresh;
    private JButton repair;
    private DefaultTableModel model;
    JTable table;
    DBConnection connector1E = new DBConnection();
    ResultSet rs = null;
    DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

    public diagnosis(JPanel cards, CardLayout c) {

        setLayout(null);
        //setBackground(Color.LIGHT_GRAY);

        JButton back = new JButton("Back");
        back.setSize(100, 40);
        back.setLocation(600, 0);
        back.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("back button clicked");
                c.show(cards, "Card_menu");

            }
        }
        );

        insert = new JButton("Insert");
        insert.setSize(150, 40);
        insert.setLocation(100, 350);
        insert.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                check();
                insert();

            }
        });

        remove = new JButton("Delete");
        remove.setSize(150, 40);
        remove.setLocation(100, 400);
        remove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                delete();

            }
        });

        edit = new JButton("Edit");
        edit.setSize(150, 40);
        edit.setLocation(260, 400);
        edit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                edit();
           }
        });

        refresh = new JButton("refresh");
        refresh.setBounds(180, 450, 150, 40);
        refresh.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("refresh button clicked");
                refresh();

            }
        });
        
        
        repair = new JButton("Repair");
        repair.setBounds(260, 350, 150, 40);
        repair.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               
                repair();
            }
        });
        
        

        add(back);
        add(insert);
        add(remove);
        add(edit);
        add(refresh);
        add(repair);

        BookingID = new JLabel("BookingID:");
        add(BookingID);
        BookingID.setBounds(100, 70, 100, 100);

        CarIssue = new JLabel("Car Issue:");
        add(CarIssue);
        CarIssue.setBounds(100, 120, 100, 100);

        Mileage = new JLabel("Vehicle Mileage:");
        add(Mileage);
        Mileage.setBounds(100, 170, 100, 100);

        VehicleID = new JLabel("Vehicle ID:");
        add(VehicleID);
        VehicleID.setBounds(100, 220, 100, 100);

        Status = new JLabel("Status:");
        add(Status);
        Status.setBounds(100, 270, 100, 100);

        /*text fields and combo boxes below */
        
        bookingID = new JTextField(100);
        add(bookingID);
        bookingID.setBounds(200, 100, 100, 40);

        
        partCombo = new JComboBox();
        connector1E.openConn();
        connector1E.prepStmt("SELECT part_name FROM part WHERE owner = 'Stock'");
        rs = connector1E.exQuery();
        try {
            while (rs.next()) {
                partCombo.addItem(rs.getString("part_name"));
                
            } } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }  
        connector1E.closeConn();        
        partCombo.setBounds(200, 150, 100, 40);
        add(partCombo);


        mileage = new JTextField(100);
        add(mileage);
        mileage.setBounds(200, 200, 100, 40);

        vehicleID = new JTextField(100);
        add(vehicleID);
        vehicleID.setBounds(200, 250, 100, 40);

        status = new JTextField(100);
        vehicleStatus = new JComboBox(vehiclestatus);
        vehicleStatus.setBounds(200, 300, 100, 40);
        add(vehicleStatus);


        
        openTable();

        table = new JTable(model);
        table.setSize(new Dimension(600, 600));

        add(table);

        /**
         * mouse clicked event
         */
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {

                try {

                    int row = table.getSelectedRow();
                    System.out.println(row);
                    String repair_ = (table.getModel().getValueAt(row, 0)).toString();
                    //System.out.println(repair_);
                    String sql = "select * from Diagnosis_And_Repair where Bookingbooking_id='" + repair_ + "'";
                    conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
                    PreparedStatement pst4 = conn.prepareStatement(sql);

                    ResultSet rs1 = pst4.executeQuery();

                    while (rs1.next()) {

                        bookingID.setText(rs1.getString("Bookingbooking_id"));
                        //carIssue.setText(rs1.getString("car_issue"));
                        mileage.setText(rs1.getString("vehicle_mileage"));
                        vehicleID.setText(rs1.getString("Vehiclevehicle_id"));
                        status.setText(rs1.getString("Status"));

                    }
                    pst4.close();

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

        });
        
        
        /*CENTER THE CELLS IN THE COLUMNS  */
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i < table.getColumnCount(); i ++){
        
        table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        
    }
        

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(650, 100, 600, 500);
        add(scrollPane);

        setVisible(true);

    }
    
        public void repair()
    {
    JComboBox bookingCombo = new JComboBox();
    JTextField issueField;

    
    JPanel fieldPanel = new JPanel(new GridLayout(4, 2));
    add(fieldPanel, BorderLayout.CENTER);
    
    // Combobox
    connector1E.openConn();
    connector1E.prepStmt("SELECT * FROM diagnosis_and_repair");
    rs = connector1E.exQuery();
    try {
    while (rs.next()) {
    bookingCombo.addItem(rs.getString("bookingbooking_id"));
    
    } } catch (SQLException ex) {
    Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
    connector1E.closeConn();
    
    //create field and labels
    JLabel bookingLabel = new JLabel("Booking ID",SwingConstants.CENTER);
    JLabel issueLabel = new JLabel("Issue",SwingConstants.CENTER);

    
    issueField = new JTextField();
    issueField.setEditable(false);
    
    // Add fields
    fieldPanel.add(bookingLabel);
    fieldPanel.add(bookingCombo);
    fieldPanel.add(issueLabel);
    fieldPanel.add(issueField);
    
    bookingCombo.setSelectedItem(null);

    
    bookingCombo.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object item = bookingCombo.getSelectedItem();
                    connector1E.openConn();
                    connector1E.prepStmt("SELECT * FROM diagnosis_and_repair WHERE bookingbooking_id = '"+item+"'");
                    rs = connector1E.exQuery();
                    try {
                        while (rs.next()){
                           String issue = rs.getString("car_issue"); 
                           issueField.setText(issue); 
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    connector1E.closeConn();
                }
            });

    
    int result = JOptionPane.showOptionDialog(null,
    fieldPanel,
    "Order Part Form",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.INFORMATION_MESSAGE,
    null,
    new String[]{"Cancel", "Repair"}, // this is the array
    "default"); 
    
    if (result == JOptionPane.NO_OPTION)
    {   
        String fixPart = issueField.getText();
        String availability = "No";
        
        connector1E.openConn();
        connector1E.prepStmt("SELECT * FROM part WHERE part_name = '"+fixPart+"' AND owner = 'Stock'");
        rs = connector1E.exQuery();
        try {
            while (rs.next()){
               int avail = rs.getInt("count"); 
               if (avail>0){
                   availability = "Yes";
               }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        } 
        connector1E.closeConn();
        if (availability.equals("Yes")){
            
            String partID = null;
            String vehID = null;
            double cost = 0;
            
            connector1E.openConn();
            
            //GET part_id from parts INSERT to veh_parts
            connector1E.prepStmt("SELECT * FROM part WHERE part_name = '"+fixPart+"' AND owner = 'Stock'");
            rs = connector1E.exQuery();
            try {
                while (rs.next()){
                   partID = rs.getString("part_id"); 
                   cost = rs.getDouble("cost_of_part");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
            } 
            //GET veh_id FROM diagrepiar INSERT to veh_parts
            connector1E.prepStmt("SELECT * FROM diagnosis_and_repair WHERE bookingbooking_id = '"+bookingCombo.getSelectedItem().toString()+"'");
            rs = connector1E.exQuery();
            try {
                while (rs.next()){
                   vehID = rs.getString("vehiclevehicle_id"); 
                   
                }
            } catch (SQLException ex) {
                Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }                         
            //GET install date (currentdate) INSERT to veh_parts
            Calendar currentDate = Calendar.getInstance();    
            currentDate.setTime(new java.util.Date());
            String date1 = dateFormat.format(currentDate.getTime());
            
            //GET warranty date (1yr from currentdate)INSERT to veh_parts
            Calendar warrantyDate = Calendar.getInstance();    
            warrantyDate.setTime(new java.util.Date());
            warrantyDate.add(Calendar.DATE,+365); 
            String date2 = dateFormat.format(warrantyDate.getTime());
            
            //Pass to Veh_Parts entity
            connector1E.prepStmt("INSERT OR IGNORE INTO vehicle_parts (vehiclevehicle_id,install_date,warranty_date,partpart_id) " +
            "VALUES ('"+vehID+"','"+date1+"','"+date2+"','"+partID+"');");
            connector1E.exUpdate();
            

            //System.out.print(partID+" "+vehID+" "+cost+" "+date1+" "+date2);
            
            //pass cost to bill in booking (which has the same booking id)
            connector1E.prepStmt("UPDATE Booking "
                        + "SET bill = bill + "+cost+" "
                        + "WHERE booking_id = '"+bookingCombo.getSelectedItem().toString()+"'");
            connector1E.exUpdate();
            
            //set status in diag and repair to "Repaired"
            connector1E.prepStmt("UPDATE Diagnosis_and_repair "
                        + "SET status = 'Repaired' "
                        + "WHERE bookingbooking_id = '"+bookingCombo.getSelectedItem().toString()+"'");
            connector1E.exUpdate();            
            
            //delete part from stock list
            connector1E.prepStmt("UPDATE Part "
                        + "SET count = count-1 "
                        + "WHERE part_id = '"+partID+"'");
            connector1E.exUpdate();   
            
            connector1E.closeConn();
        }
        else{
            JOptionPane.showMessageDialog(null,"Part is out of Stock. Go to Parts page to order");
        }
    }   
    //Your refresh button
    refresh();
   }

    public void refresh() {
        try {
            String sql12 = "Select * from Diagnosis_And_Repair";
            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            PreparedStatement pst12 = conn.prepareStatement(sql12);
            ResultSet rs = pst12.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            pst12.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openTable() {

        Vector<Object> columnNames = new Vector<Object>();
        Vector<Object> data = new Vector<Object>();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            //  Read data from a table
            String sql = "Select * from Diagnosis_And_Repair";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

            //  Get row data
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>(columns);

                for (int i = 1; i <= columns; i++) {
                    row.addElement(rs.getObject(i));
                }

                data.addElement(row);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        //  Create table with database data
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };

    }

    public void insert() {

        try {

            //String sql1 = "INSERT OR IGNORE INTO Diagnosis_And_Repair(Bookingbooking_id, car_issue,vehicle_mileage, Vehiclevehicle_id, Status) values (?,?,?,?,?)";
            String sql1 = "INSERT OR IGNORE INTO Diagnosis_And_Repair (Bookingbooking_id, car_issue,vehicle_mileage, Vehiclevehicle_id, Status) values ('"+bookingID.getText()+"','"+partCombo.getSelectedItem().toString()+"','"+mileage.getText()+"','"+vehicleID.getText()+"','"+status.getText()+"')";
            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            PreparedStatement pst = conn.prepareStatement(sql1);            
            pst.execute();

            System.out.println("data inserted");

            pst.close();

        } catch (Exception e2) {

            e2.printStackTrace();
        }

        refresh();
    }

    public void delete() {

        try {

            String sql1 = "delete from Diagnosis_And_Repair where Bookingbooking_id='" + bookingID.getText() + "' ";
            System.out.println(bookingID.getText());
            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            PreparedStatement pst = conn.prepareStatement(sql1);

            pst.execute();

            System.out.println("data removed");

            pst.close();

        } catch (Exception ex) {

            System.out.println("data not removed");
            //ex.printStackTrace();
        }
        refresh();
    }

    public void edit() {

        try {
                

            
            
            String sql1 = "Update Diagnosis_And_Repair set Bookingbooking_id ='" + bookingID.getText() + "',car_issue ='" + partCombo.getSelectedItem().toString()
                    + "',vehicle_mileage ='" + mileage.getText() + "',Vehiclevehicle_id ='" + vehicleID.getText() + "',Status ='" + status.getText() + "' where Bookingbooking_id='" + bookingID.getText() + "' " ;

            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            PreparedStatement pst = conn.prepareStatement(sql1);

            pst.execute();

            System.out.println("data edited");

            pst.close();

        } catch (Exception e2) {

            e2.printStackTrace();
        }
        refresh();
    }
    
    public void check(){
    
        if(vehicleStatus.getSelectedItem().toString().equals("On-going")){
            status.setText("On-going");
            System.out.println(status.getText());
        }
        else{
            status.setText("Repaired");
        }
        
        /*if(carissue.getSelectedItem().toString().equals("testParts")){
            carIssue.setText("testParts");
        
        }*/
    
    }
    
    


}
