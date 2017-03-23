package gmsis;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
//import com.toedter.calendar.JCalendar;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.*;

/**
 *
 * @author pr305
 */
public class bookings extends JPanel {

    private JLabel BookingID;
    private JLabel BookingType;
    private JLabel Bill;
    private JLabel BookingDate;
    private JLabel CurrentDate;
    private JLabel CustomerAccountNumber;
    private JLabel Baynum;
    private JLabel StartTime;
    private JLabel Duration;
    private JLabel MechanicID;
    private JLabel HourlyRate;
    private JLabel PerHour;
    private JLabel HoursWorked;

    private JComboBox Baynumber;
    private JComboBox mechanicID;

    private String[] durationoptions = {"1 Hour", "2 Hour"};
    private JComboBox timeDuration;

    private String[] startoptions = {"9.00", "10.00", "11.00", "12.00", "13.00", "14.00", "15.00", "16.00"};
    private JComboBox starttime;

    private String[] bookingtypeoptions = {"Diagnosis & Repair", "Scheduled Maintaince"};
    private JComboBox bookingtype;

    private JTextField bookingID;
    private JTextField bookingType;
    private JTextField bill;
    private JFormattedTextField bookingDate;
    private JTextField currentDate;
    private JTextField customerAccountNumber;
    private JTextField baynum;
    private JTextField startTime;
    private JTextField duration;
    private JTextField mechanic_id;
    private JTextField hourlyrate;
    private JTextField hoursworked;

    private DefaultTableModel model;
    private Connection conn = null;
    private JButton insert;
    private JButton remove;
    private JButton edit;
    private JButton Availibility;
    JTable table;

    private JDatePickerImpl datePicker;
    private Calendar test;

    public bookings(JPanel cards, CardLayout c) {

        setLayout(null);

        JButton back = new JButton("Back");
        back.setSize(200, 40);
        back.setLocation(600, 0);
        back.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("back button clicked");
                c.show(cards, "Card_menu");

            }
        }
        );

        add(back);

        insert = new JButton("Insert");
        insert.setBounds(100, 580, 100, 40);
        insert.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                insert();
                insertIntoRepair();
            }
        });

        add(insert);

        remove = new JButton("Delete");
        remove.setBounds(100, 630, 100, 40);
        remove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                delete();

            }
        });

        add(remove);

        edit = new JButton("Edit");
        edit.setBounds(100, 680, 100, 40);
        edit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                edit();

            }
        });

        add(edit);

        Availibility = new JButton("Check Availibility");
        Availibility.setBounds(50, 530, 200, 40);
        Availibility.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("insert button clicked");
                //c.show(cards, "Card_menu");
                checkAvailibility();

            }
        });

        add(Availibility);

        /*BELOW ARE THE LABELS FOR THE BOOKING CLASS */
        BookingID = new JLabel("BookingID:");
        add(BookingID);
        BookingID.setBounds(50, 50, 100, 100);

        BookingType = new JLabel("Booking Type:");
        add(BookingType);
        BookingType.setBounds(50, 100, 100, 100);

        Bill = new JLabel("Bill:");
        add(Bill);
        Bill.setBounds(50, 150, 100, 100);

        BookingDate = new JLabel("Booking Date:");
        add(BookingDate);
        BookingDate.setBounds(50, 200, 100, 100);

        CurrentDate = new JLabel("Current Date:");
        add(CurrentDate);
        CurrentDate.setBounds(50, 250, 100, 100);

        Baynum = new JLabel("Bay Number:");
        add(Baynum);
        Baynum.setBounds(50, 300, 100, 100);

        StartTime = new JLabel("Start Time:");
        add(StartTime);
        StartTime.setBounds(50, 350, 100, 100);

        Duration = new JLabel("Duration:");
        add(Duration);
        Duration.setBounds(50, 400, 100, 100);

        CustomerAccountNumber = new JLabel("Customer Account Number:");
        add(CustomerAccountNumber);
        CustomerAccountNumber.setBounds(50, 450, 100, 100);

        /*Mechanic JLabels*/
        MechanicID = new JLabel("Mechanic ID:");
        add(MechanicID);
        MechanicID.setBounds(400, 400, 100, 100);

        HourlyRate = new JLabel("Hourly Rate:");
        add(HourlyRate);
        HourlyRate.setBounds(400, 450, 100, 100);

        PerHour = new JLabel("P/H");
        add(PerHour);
        PerHour.setBounds(600, 450, 100, 100);

        HoursWorked = new JLabel("Hours Worked:");
        add(HoursWorked);
        HoursWorked.setBounds(400, 500, 100, 100);

        /*BELOW ARE THE CORESSPONDING TEXTFIELDS FOR THE BOOKINGS CLASS */
        bookingID = new JTextField(100);
        add(bookingID);
        bookingID.setBounds(150, 80, 100, 40);

        bookingType = new JTextField(100);
        bookingtype = new JComboBox(bookingtypeoptions);
        add(bookingtype);
        bookingtype.setBounds(150, 130, 150, 40);

        bill = new JTextField(100);
        add(bill);
        bill.setBounds(150, 180, 150, 40);

        bookingDate = new JFormattedTextField();
        add(bookingDate);
        bookingDate.setBounds(150, 230, 100, 40);

        currentDate = new JTextField(new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()), 15); // always displayes the current date. 
        add(currentDate);
        currentDate.setBounds(150, 280, 100, 40);

        /*combo box for baymunber */
        Baynumber = new JComboBox();
        for (int i = 1; i < 7; i++) {
            Baynumber.addItem(i);
        }

        add(Baynumber);
        Baynumber.setBounds(150, 330, 100, 40);

        startTime = new JTextField(100);
        starttime = new JComboBox(startoptions);
        add(starttime);
        starttime.setBounds(150, 380, 100, 40);

        duration = new JTextField(100);
        timeDuration = new JComboBox(durationoptions);
        add(timeDuration);
        timeDuration.setBounds(150, 430, 100, 40);

        customerAccountNumber = new JTextField(100);
        add(customerAccountNumber);
        customerAccountNumber.setBounds(150, 480, 100, 40);

        /*mechanics combo box and textfields */
        mechanicID = new JComboBox();
        for (int i = 1; i < 7; i++) {
            mechanicID.addItem(i);
        }

        add(mechanicID);
        mechanicID.setBounds(500, 430, 100, 40);

        hourlyrate = new JTextField("10.00");
        hourlyrate.setEditable(false);
        add(hourlyrate);
        hourlyrate.setBounds(500, 480, 100, 40);

        hoursworked = new JTextField(10);
        add(hoursworked);
        hoursworked.setBounds(500, 530, 100, 40);

        /*BELOW IS THE CALENDAR IN ORDER TO SET  THE  */
//        UtilDateModel model1 = new UtilDateModel();
//
//        Properties p = new Properties();
//        p.put("text.today", "Today");
//        p.put("text.month", "Month");
//        p.put("text.year", "Year");
//        JDatePanelImpl datePanel = new JDatePanelImpl(model1, p);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
//        datePicker.setBounds(300, 240, 40, 28);
//        add(datePicker);
        final JXDatePicker datePicker = new JXDatePicker();
        Calendar calendar = datePicker.getMonthView().getCalendar();
        calendar.setTime(new Date());
        datePicker.getMonthView().setLowerBound(calendar.getTime());

        datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        datePicker.setBounds(250, 230, 35, 40);

        add(datePicker);

        JButton choose = new JButton("Choose");

        choose.setBounds(300, 230, 100, 40);
        add(choose);

        choose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Date selectedDate = (Date) datePicker.getDate();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String reportDate = df.format(selectedDate);

                bookingDate.setText(reportDate);

                /*convert the chosen date in to a char array.  */
                //System.out.println(reportDate.toString());
                String test = reportDate.toString();

                char[] charArray = test.toCharArray();
                int[] x = new int[charArray.length];
                try {
                    for (int i = 0; i < charArray.length; i++) {
                        char c = charArray[i];
                        //System.out.println(c);
                        x[i] = c;
                    }
                } catch (Exception t) {

                }

                for (int i = 0; i < x.length; i++) {
                    if (x[i] == 45) {

                    } else {
                        //System.out.println(x[i] - 48);
                        x[i] = x[i] - 48;
                        System.out.println(x[i]);

                    }
                }

                /**
                 * **********convert current date to ******************
                 */
                String curDate = currentDate.getText().toString();
                //System.out.println(curDate);

                char[] charArray2 = curDate.toCharArray();
                int[] y = new int[charArray2.length];

                try {
                    for (int i = 0; i < charArray2.length; i++) {
                        char b = charArray2[i];
                        //System.out.println(b);
                        y[i] = b;
                    }
                } catch (Exception p) {

                }

                for (int i = 0; i < y.length; i++) {
                    if (y[i] == 45) {

                    } else {
                        //System.out.println(y[i] - 48);
                        y[i] = y[i] - 48;
                        System.out.println(y[i]);
                    }

                }
                /*concatenate the date variables so '0' '3' appear as 03(3rd) */

                int a = Integer.parseInt(Integer.toString(y[0]) + Integer.toString(y[1]));
                System.out.println("\n" + a);

                int b = Integer.parseInt(Integer.toString(y[3]) + Integer.toString(y[4]));
                System.out.println("\n" + b);

                int c = Integer.parseInt(Integer.toString(y[6]) + Integer.toString(y[7]) + Integer.toString(y[8]) + Integer.toString(y[9]));
                System.out.println("\n" + c);

                int d = Integer.parseInt(Integer.toString(x[0]) + Integer.toString(x[1]));
                System.out.println("\n" + d);

                int f = Integer.parseInt(Integer.toString(x[3]) + Integer.toString(x[4]));
                System.out.println("\n" + f);

                int g = Integer.parseInt(Integer.toString(y[6]) + Integer.toString(y[7]) + Integer.toString(y[8]) + Integer.toString(y[9]));
                System.out.println("\n" + g);

                if (a > d || b > f || c > g) {
                    System.out.println("this works");
                    JOptionPane.showMessageDialog(null, "Please choose a valid date");
                    bookingDate.setText(null);
                }

            }
        });

        openTable();

        table = new JTable(model);
        add(table);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {

                try {

                    int row = table.getSelectedRow();
                    String repair_ = (table.getModel().getValueAt(row, 0)).toString();

                    String sql = "select * from Booking where  booking_id='" + repair_ + "'";
                    conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
                    PreparedStatement pst4 = conn.prepareStatement(sql);

                    ResultSet rs1 = pst4.executeQuery();

                    while (rs1.next()) {

                        bookingID.setText(rs1.getString("booking_id"));
                        //bookingType.setText(rs1.getString("booking_type"));
                        //currentDate.setText(rs1.getString("current_date"));
                        //bookingDate.setText(rs1.getString("booking_date"));
                        //baynum.setText(rs1.getString("bay_no"));
                        //startTime.setText(rs1.getString("start_time"));
                        //duration.setText(rs1.getString("duration"));
                        //bill.setText(rs1.getString("bill"));
                        //customerAccountNumber.setText(rs1.getString(" Customer_Accountcustomer_account_id"));

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
        for (int i = 0; i < table.getColumnCount(); i++) {

            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);

        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 100, 900, 300);
        add(scrollPane);

        setVisible(true);

    }// closes constructor

    public void refresh() {
        try {
            String sql12 = "Select * from Booking";
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

//    public void refresh2() {
//        try {
//            String sql12 = "Select Bookingbooking_id from Diagnosis_And_Repair";
//            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
//            PreparedStatement pst12 = conn.prepareStatement(sql12);
//            ResultSet rs = pst12.executeQuery();
//            table.setModel(DbUtils.resultSetToTableModel(rs));
//            pst12.close();
//            rs.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
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
            String sql = "Select * from Booking";
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

            String sql1 = "INSERT OR IGNORE INTO Booking(booking_id, booking_type, current_date, booking_date, bay_no, start_time, duration, bill,  Customer_Accountcustomer_account_id, Mechanicmechanic_id) values (?,?,?,?,?,?,?,?,?,?)";

            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            PreparedStatement pst = conn.prepareStatement(sql1);

            pst.setString(1, bookingID.getText());
            pst.setString(2, bookingType.getText());
            pst.setString(3, currentDate.getText());
            pst.setString(4, bookingDate.getText());
            pst.setString(5, baynum.getText());
            pst.setString(6, startTime.getText());
            pst.setString(7, duration.getText());
            pst.setString(8, bill.getText());
            pst.setString(9, customerAccountNumber.getText());
            pst.setString(10, mechanic_id.getText());

            pst.execute();
            System.out.println("data inserted");
            pst.close();

        } catch (Exception e2) {

            e2.printStackTrace();
        }
        refresh();

    }

    public void insertIntoRepair() {

        try {

            String sql2 = "INSERT OR IGNORE INTO Diagnosis_And_Repair(Bookingbooking_id) values (?)";
            System.out.println(bookingID.getText());
            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            //System.out.println("connected to database");
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            pst2.setString(1, bookingID.getText());
            pst2.execute();
            System.out.println("data inserted");
            pst2.close();

        } catch (Exception e2) {

            e2.printStackTrace();
        }

        //refresh2();
    }

    public void delete() {

        try {

            String sql1 = "Delete from Booking where booking_id='" + bookingID.getText() + "' ";

            conn = DriverManager.getConnection("jdbc:sqlite:src\\gsys.db");
            PreparedStatement pst = conn.prepareStatement(sql1);

            pst.execute();

            System.out.println("data removed");

            pst.close();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        refresh();
    }

    public void edit() {

        try {

            String sql1 = "Update Booking set booking_id ='" + bookingID.getText() + "',booking_type ='" + bookingType.getText()
                    + "',current_date ='" + currentDate.getText() + "',booking_date ='" + bookingDate.getText() + "',bay_no ='"
                    + baynum.getText() + "',start_time ='" + startTime.getText() + "',duration ='" + duration.getText()
                    + "',bill ='" + bill.getText() + "', Customer_Accountcustomer_account_id ='" + customerAccountNumber.getText()
                    + "',Mechanicmechanic_id'" + mechanic_id.getText()
                    + "' where booking_id='" + bookingID.getText() + "' ";

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

    public void checkAvailibility() {
        boolean starttimealreadyselected = false;
        boolean bayalreadyselected = false;
        boolean datealreadyselected = false;

        baynum = new JTextField(1);
        mechanic_id = new JTextField(1);

        if (Baynumber.getSelectedItem().toString().equals("1")) {
            System.out.println(baynum.getText());

            baynum.setText("1");
            System.out.println("baynumber : " + baynum.getText());
        } else if (Baynumber.getSelectedItem().toString().equals("2")) {
            baynum.setText("2");
            System.out.println("baynumber : " + baynum.getText());
        } else if (Baynumber.getSelectedItem().toString().equals("3")) {
            baynum.setText("3");
            System.out.println("baynumber : " + baynum.getText());
        } else if (Baynumber.getSelectedItem().toString().equals("4")) {
            baynum.setText("4");
            System.out.println("baynumber : " + baynum.getText());
        } else if (Baynumber.getSelectedItem().toString().equals("5")) {
            baynum.setText("5");
            System.out.println("baynumber : " + baynum.getText());
        } else {
            baynum.setText("6");
            System.out.println("baynumber : " + baynum.getText());
        }

        if (timeDuration.getSelectedItem().toString().equals("1 Hour")) {
            duration.setText("1 Hour");
        } else {
            duration.setText("30 mins");
            System.out.println(duration.getText());

        }

        if (starttime.getSelectedItem().toString().equals("9.00")) {

            startTime.setText("9.00 AM");
        } else if (starttime.getSelectedItem().toString().equals("10.00")) {

            startTime.setText("10.00 AM");
        } else if (starttime.getSelectedItem().toString().equals("11.00")) {

            startTime.setText("11.00 AM");
        } else if (starttime.getSelectedItem().toString().equals("12.00")) {

            startTime.setText("12.00 PM");
        } else if (starttime.getSelectedItem().toString().equals("13.00")) {

            startTime.setText("13.00 PM");
        } else if (starttime.getSelectedItem().toString().equals("14.00")) {

            startTime.setText("14.00 PM");
        } else if (starttime.getSelectedItem().toString().equals("15.00")) {

            startTime.setText("15.00 PM");
        } else if (starttime.getSelectedItem().toString().equals("16.00")) {

            startTime.setText("16.00 PM");
        }

        if (bookingtype.getSelectedItem().toString().equals("Diagnosis & Repair")) {

            bookingType.setText("Diagnosis & Repair");
        } else {
            bookingType.setText("Scheduled Maintaince");
        }

        if (mechanicID.getSelectedItem().toString().equals("1")) {
            System.out.println(mechanicID);
            mechanic_id.setText("1");
            System.out.println(mechanic_id);
        } else if (mechanicID.getSelectedItem().toString().equals("2")) {
            mechanic_id.setText("2");
        } else if (mechanicID.getSelectedItem().toString().equals("3")) {
            mechanic_id.setText("3");
        } else if (mechanicID.getSelectedItem().toString().equals("4")) {
            mechanic_id.setText("4");
        } else if (mechanicID.getSelectedItem().toString().equals("5")) {
            mechanic_id.setText("5");
        } else if (mechanicID.getSelectedItem().toString().equals("6")) {
            mechanic_id.setText("6");
        }

        /*check for date is sunday */
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2012, Calendar.DECEMBER, 02);
//        if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//        System.out.println("true");
//        } else {
//        System.out.println("FALSE");
//        }
        JOptionPane.showMessageDialog(null, "The start time selected is " + starttime.getSelectedItem()
                + "\nYou have Chosen BAY #: " + baynum.getText() + "\n "
                + "For a duration of : " + duration.getText());

//        
//                for (int i = 0; i < table.getRowCount(); i++) {
//                    System.out.println("going through forl oop");
//                    for (int j = 0; j < table.getColumnCount(); j++) {
//                        System.out.println("going through forl oop2222");
//                        if (table.getModel().getValueAt(i, j).equals(bookingDate.getText())){
//                            if(table.getModel().getValueAt(i, j).equals(startTime.getText())){
//                                
//                            
//                            System.out.println("going through forl oop333333333");
//                            System.out.println("" + i + "," + j);
//        
//       
//                        }}
//                    
//                    }}
//        for (int i = 0; i < table.getRowCount(); i++) {
//            // System.out.println("for loop for first row count");
//            for (int j = 0; j < table.getColumnCount(); j++) {
//                //System.out.println("forloop for first colunmcount");
//                if (table.getModel().getValueAt(i, j).equals(startTime.getText())) {
//
//                    //System.out.println("" + i + "," + j);
//                    starttimealreadyselected = true;
//                    System.out.println("starttimealreadyselected" + starttimealreadyselected);
//
//                }
//            }
//
//        }

        for (int m = 0; m < table.getRowCount(); m++) {
            for (int n = 0; n < table.getColumnCount(); n++) {
                if (table.getModel().getValueAt(m, n).equals(bookingDate.getText())) {
                    System.out.println(bookingDate.getText());
                    //datealreadyselected = true;
                    System.out.println("datealreadyselected" + starttimealreadyselected);
                    
                    if (table.getModel().getValueAt(m, n + 1).equals(baynum.getText())) {
                        System.out.println("bay already selected");

                        if (table.getModel().getValueAt(m, n + 2).equals(startTime.getText())) {
                            System.out.println("start time already seleceted");

                            if (table.getModel().getValueAt(m, n + 5).equals(mechanic_id.getText())) {
                                System.out.println("mechanic already selected");

                                JOptionPane.showMessageDialog(null, "You have already booked this time, Please choose another time, or another Bay");
                            }
                        }
                    }

                }

            }

        }
//        if(starttimealreadyselected == true && bayalreadyselected == true && datealreadyselected == true){
//       
//        }

    }

}// ends class
