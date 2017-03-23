package gmsis;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Abhishek
 */
public class PartsPanel extends javax.swing.JPanel {

    DBConnection connector1E = new DBConnection();
    ResultSet rs = null;
    Object value;
    DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
    CardLayout card;
    JPanel cardPanel;
    
    public PartsPanel(JPanel cards, CardLayout c) {
        cardPanel = cards;
        card = c;
        initComponents();
        updateSummaryToStock();
        setup();
        popupSetup();
        descArea.setEditable(false);
        
        
   }
    
    
    //updates Orders into the Stock at the appropriate dates
    public void updateSummaryToStock(){     
        Calendar currentDate = Calendar.getInstance();    
        currentDate.setTime(new java.util.Date());
        
        Calendar yesterday = Calendar.getInstance();    
        yesterday.setTime(new java.util.Date());
        yesterday.add(Calendar.DATE,-1); 
        
        Calendar dayBeforeY = Calendar.getInstance();    
        dayBeforeY.setTime(new java.util.Date());
        dayBeforeY.add(Calendar.DATE,-2);
        //weekends + possibly a public holiday
        String date1 = dateFormat.format(currentDate.getTime());
        String date2 = dateFormat.format(yesterday.getTime());
        String date3 = dateFormat.format(dayBeforeY.getTime());
              
        connector1E.openConn();
        connector1E.prepStmt("SELECT part_name,count "
                            + "FROM Part "
                            + "WHERE owner = 'Summary' "
                            + "AND (delivery_date = '"+date1+"'OR delivery_date = '"+date2+"' OR delivery_date = '"+date3+"')");
        rs = connector1E.exQuery();
        try {
            while (rs.next()) {
                String name = rs.getString("part_name");
                int count = rs.getInt("count");
                connector1E.prepStmt("UPDATE Part "
                        + "SET count = count + "+count+" "
                        + "WHERE part_name = '"+name+"' AND owner = 'Stock'");
                connector1E.exUpdate();   
            } } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        connector1E.prepStmt("DELETE "
                            + "FROM Part "
                            + "WHERE owner = 'Summary' "
                            + "AND (delivery_date = '"+date1+"'OR delivery_date = '"+date2+"' OR delivery_date = '"+date3+"')");
        connector1E.exUpdate();
        connector1E.closeConn();
    }
    //set the initial tables
    public void setup()
    {
       setStockTable();
       setSummaryTable();
       setSupplierTable(); 
    }
    //set the initial popups
    public void popupSetup()
    {
        stockTablePopUp();
        summaryTablePopUp();
        supplierTablePopUp();
    }
    
    public void setStockTable()
    {
        
        connector1E.openConn();
        connector1E.prepStmt("SELECT part_id ID,part_name Part,cost_of_part Cost,count Amount "
                + "FROM Part "
                + "WHERE owner = 'Stock'");
        rs = connector1E.exQuery();
        stockTable.setModel(DbUtils.resultSetToTableModel(rs));
        connector1E.closeConn();
    }
    
    public void setSummaryTable()
    {
        connector1E.openConn();
        connector1E.prepStmt("SELECT part_id ID, part_name Part,delivery_date 'Delivery Date', count Amount,supplierssupplier_id Supplier FROM Part where owner = 'Summary'");
        rs = connector1E.exQuery();
        summaryTable.setModel(DbUtils.resultSetToTableModel(rs));
        connector1E.closeConn();
    }
    
    public void setSupplierTable()
    {
        connector1E.openConn();
        connector1E.prepStmt("SELECT supplier_id ID,supplier_name Supplier, supplier_contact Contact FROM supplier");
        rs = connector1E.exQuery();
        supplierTable.setModel(DbUtils.resultSetToTableModel(rs));
        connector1E.closeConn();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        supplierPopup = new javax.swing.JPopupMenu();
        stockPopup = new javax.swing.JPopupMenu();
        summaryPopup = new javax.swing.JPopupMenu();
        windowPane = new javax.swing.JTabbedPane();
        partsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jScrollPane1 = new javax.swing.JScrollPane();
        summaryTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jScrollPane3 = new javax.swing.JScrollPane();
        supplierTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jScrollPane4 = new javax.swing.JScrollPane();
        descArea = new javax.swing.JTextArea();
        orderPartButton = new javax.swing.JButton();
        addSupplierButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1250, 800));

        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(stockTable);

        javax.swing.GroupLayout partsPanelLayout = new javax.swing.GroupLayout(partsPanel);
        partsPanel.setLayout(partsPanelLayout);
        partsPanelLayout.setHorizontalGroup(
            partsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, partsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        partsPanelLayout.setVerticalGroup(
            partsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );

        windowPane.addTab("Stock List", partsPanel);

        summaryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        summaryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                summaryTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(summaryTable);

        windowPane.addTab("Order List", jScrollPane1);

        supplierTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        supplierTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supplierTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(supplierTable);

        windowPane.addTab("Supplier List", jScrollPane3);

        descArea.setColumns(20);
        descArea.setRows(5);
        jScrollPane4.setViewportView(descArea);

        orderPartButton.setText("Order Part");
        orderPartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderPartButtonActionPerformed(evt);
            }
        });

        addSupplierButton.setText("Add Supplier");
        addSupplierButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSupplierButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(orderPartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addSupplierButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(refreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(windowPane)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(orderPartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addSupplierButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(windowPane, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void stockTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockTableMouseClicked
        int rowIndex = stockTable.getSelectedRow();
        value = stockTable.getValueAt(rowIndex, 0);
        fillTable("SELECT part_description "
                + "FROM Part "
                + "WHERE part_id = '"+value+"' AND owner = 'Stock'");

    }//GEN-LAST:event_stockTableMouseClicked

    private void summaryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_summaryTableMouseClicked

        int rowIndex = summaryTable.getSelectedRow();
        value = summaryTable.getValueAt(rowIndex, 1);
        fillTable("SELECT part_description "
                + "FROM Part "
                + "WHERE part_name = '"+value+"' AND owner = 'Stock'");

    }//GEN-LAST:event_summaryTableMouseClicked

    private void supplierTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierTableMouseClicked

              
        int rowIndex = supplierTable.getSelectedRow();
        value = supplierTable.getValueAt(rowIndex, 0);
        fillTable("SELECT supplier_address "
                + "FROM Supplier "
                + "WHERE supplier_id ='"+value+"'" );
        
        
    }//GEN-LAST:event_supplierTableMouseClicked

    private void orderPartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderPartButtonActionPerformed
        orderPart();
    }//GEN-LAST:event_orderPartButtonActionPerformed

    private void addSupplierButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSupplierButtonActionPerformed
        addSupplier();
    }//GEN-LAST:event_addSupplierButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        card.show(cardPanel, "Card_menu");
    }//GEN-LAST:event_backButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
       setup();
    }//GEN-LAST:event_refreshButtonActionPerformed
 
    public void orderPart()
    {  
    JComboBox supplierCombo = new JComboBox();
    JComboBox partCombo = new JComboBox();
    JComboBox amountCombo = new JComboBox();
    JTextField dateField;    
        
    JPanel fieldPanel = new JPanel(new GridLayout(4, 2));
    add(fieldPanel, BorderLayout.CENTER);

    // Combobox
    connector1E.openConn(); 
    connector1E.prepStmt("SELECT supplier_name FROM supplier");
    rs = connector1E.exQuery();
        try {
            while (rs.next()) {
                supplierCombo.addItem(rs.getString("supplier_name"));
                
            } } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    connector1E.prepStmt("SELECT part_name FROM part WHERE owner = 'Stock'");
    rs = connector1E.exQuery();
        try {
            while (rs.next()) {
                partCombo.addItem(rs.getString("part_name"));
                
            } } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }  
    connector1E.closeConn();
    
    for (int i = 1;i < 11;i++){
        amountCombo.addItem(i);
    }
    
    
    //create field and labels 
    JLabel supplierLabel = new JLabel("Order from",SwingConstants.CENTER);
    JLabel partLabel = new JLabel("Part Name",SwingConstants.CENTER);
    JLabel amountLabel = new JLabel("Order Amount",SwingConstants.CENTER);
    JLabel dateLabel = new JLabel("Delivery Date",SwingConstants.CENTER);
    dateField = new JTextField();
    dateField.setEditable(false);

    // Add fields
    fieldPanel.add(supplierLabel);
    fieldPanel.add(supplierCombo);
    fieldPanel.add(partLabel);
    fieldPanel.add(partCombo);
    fieldPanel.add(amountLabel);
    fieldPanel.add(amountCombo);
    fieldPanel.add(dateLabel);
    fieldPanel.add(dateField);
    
    DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
    Calendar deliveryDate = Calendar.getInstance();    
    deliveryDate.setTime(new java.util.Date());
    deliveryDate.add(Calendar.DATE, 5); 
    
    dateField.setText(dateFormat.format(deliveryDate.getTime()));
    
    int result = JOptionPane.showOptionDialog(null,
        fieldPanel,
        "Order Part Form",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        new String[]{"Cancel", "Order"}, // this is the array
        "default"); 
    
    if (result == JOptionPane.NO_OPTION)
    {
        String supply = supplierCombo.getSelectedItem().toString();
        String part = partCombo.getSelectedItem().toString();
        String amount = amountCombo.getSelectedItem().toString();
        int amountInt = Integer.parseInt(amount);
        String dDate = dateField.getText();
        
        connector1E.openConn();
        connector1E.prepStmt("INSERT INTO PART (PART_NAME,DELIVERY_DATE,SUPPLIERSSUPPLIER_ID,OWNER,COUNT) " +
        "VALUES ('"+part+"','"+dDate+"','"+supply+"','Summary',"+amountInt+");");
        connector1E.exUpdate();
        connector1E.closeConn();
    }
    setup();    
    }
    private void fillTable(String s)
    {
        descArea.setText(null);
        
        connector1E.openConn();
        connector1E.prepStmt(s);
        rs = connector1E.exQuery();
        try {
            descArea.insert(rs.getString(1), 0);
        } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        connector1E.closeConn();
    
    } 
    
    private void stockTablePopUp(){
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                editStock();

            }
        });
        stockPopup.add(editItem);
        
        stockTable.setComponentPopupMenu(stockPopup); 
    }
    
    private void editStock()
    {
        try {
            connector1E.openConn();
            connector1E.prepStmt("SELECT * FROM part WHERE part_id ="+value+"");
            rs = connector1E.exQuery();

            JTextField field1 = new JTextField(5), field2 = new JTextField(5), field3 = new JTextField(20);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
            myPanel.add(new JLabel("Part Name"));
            myPanel.add(field1);
            myPanel.add(Box.createHorizontalStrut(5));
            myPanel.add(new JLabel("Cost"));
            myPanel.add(field2);
            myPanel.add(Box.createHorizontalStrut(5));
            myPanel.add(new JLabel("Description"));
            myPanel.add(field3);
            myPanel.add(Box.createHorizontalStrut(5));

            int pID = 0;
            String pName = null;
            int pCost;
            String pDesc = null;
            String pCostInput = null;

            while (rs.next()) {
                pID = rs.getInt("part_id");
                pName = rs.getString("part_name");
                pCost = rs.getInt("cost_of_part");
                pDesc = rs.getString("part_description");
                pCostInput = String.valueOf(pCost);

            }

            field1.setText(pName);
            field1.setEditable(false);
            field2.setText(pCostInput);
            field3.setText(pDesc);


            connector1E.closeConn();


            int result = JOptionPane.showOptionDialog(null,
            myPanel,
            "Edit Details",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Cancel", "Update"}, // this is the array
            "default");
            if (result == JOptionPane.NO_OPTION){

            int pCostOutput = Integer.parseInt(field2.getText());

            connector1E.openConn();
            connector1E.prepStmt("UPDATE PART "
            + "SET part_name='"+field1.getText()+"', "
            + "cost_of_part = "+pCostOutput+", "
            + "part_description = '"+ field3.getText()+"'  "
            + "WHERE part_id ='"+value+"';");
            connector1E.exUpdate();
            connector1E.closeConn();

        }
        } catch (SQLException ex) {
        Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setStockTable(); 
    
    }
    
    private void summaryTablePopUp()
    {
        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = summaryTable.getSelectedRow();
                value = summaryTable.getValueAt(rowIndex, 0);
                removeSummary();
            }
        });
        
        summaryPopup.add(removeItem);
        summaryTable.setComponentPopupMenu(summaryPopup); 
    }
    
    public void removeSummary()
    {
        
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this order", 
                                                        "Cancel Order", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          
            connector1E.openConn();
            connector1E.prepStmt("DELETE FROM part WHERE part_ID="+value+"");
            connector1E.exUpdate();
            connector1E.closeConn();
            setSummaryTable();                  
            
        }
    }
    
    private void supplierTablePopUp()
    {           
        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                removeSupplier();
            }
        });
        
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                editSupplier();
            }
        });       
        supplierPopup.add(editItem);
        supplierPopup.add(removeItem);
        
        supplierTable.setComponentPopupMenu(supplierPopup); 
    }
    
    public void addSupplier()
    {
        JTextField field1 = new JTextField(5), field2 = new JTextField(5), field3 = new JTextField(5);
        JTextField field4 = new JTextField(5), field5 = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Supplier Name"));
        myPanel.add(field1);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Contact Number"));
        myPanel.add(field2);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Street"));
        myPanel.add(field3);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("City"));
        myPanel.add(field4);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Country"));
        myPanel.add(field5);
        myPanel.add(Box.createHorizontalStrut(5));

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Add Supplier", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION)
        {
                
            connector1E.openConn();
            connector1E.prepStmt("INSERT INTO SUPPLIER (SUPPLIER_NAME,SUPPLIER_CONTACT,SUPPLIER_ADDRESS) " +
            "VALUES ('"+field1.getText()+"',"+field2.getText()+",'"+field3.getText()+"\n"+field4.getText()+"\n"+field5.getText()+"');");
            connector1E.exUpdate();
            connector1E.closeConn();
            setSupplierTable();

        }
    }
    
    public void removeSupplier()
    {
        
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this supplier", 
                                                        "Remove Supplier", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          
            connector1E.openConn();
            connector1E.prepStmt("DELETE from SUPPLIER where SUPPLIER_ID="+value+"");
            connector1E.exUpdate();
            connector1E.closeConn();
            setSupplierTable();                  
            
        }
    }
    
    public void editSupplier()
    {
        
        try {
            connector1E.openConn();
            connector1E.prepStmt("SELECT * FROM supplier WHERE supplier_id ='"+value+"'");
            rs = connector1E.exQuery();
            
            JTextField field1 = new JTextField(5), field2 = new JTextField(5), field3 = new JTextField(5);
            JTextField field4 = new JTextField(5), field5 = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
            myPanel.add(new JLabel("Supplier Name"));
            myPanel.add(field1);
            myPanel.add(Box.createHorizontalStrut(5));
            myPanel.add(new JLabel("Contact Number"));
            myPanel.add(field2);
            myPanel.add(Box.createHorizontalStrut(5));
            myPanel.add(new JLabel("Street"));
            myPanel.add(field3);
            myPanel.add(Box.createHorizontalStrut(5));
            myPanel.add(new JLabel("City"));
            myPanel.add(field4);
            myPanel.add(Box.createHorizontalStrut(5));
            myPanel.add(new JLabel("Country"));
            myPanel.add(field5);
            myPanel.add(Box.createHorizontalStrut(5));
            
            String sName = null;
            String sContact = null;
              
            String sAddress = null;
            while (rs.next()) {
                int sID = rs.getInt("supplier_id");
                sName = rs.getString("supplier_name");
                sContact = rs.getString("supplier_contact");
                sAddress = rs.getString("supplier_address");                   
                
            } 
                String [] partAddress = sAddress.split("\n");
                String street = partAddress[0];

                field1.setText(sName);
                field2.setText(sContact);
                
                field3.setText(street);
                
                if(partAddress.length > 1 && partAddress[1] != null){
                String city = partAddress[1];
                field4.setText(city);
                }
                if(partAddress.length > 2 && partAddress[2] != null){
                String country = partAddress[2];
                field5.setText(country);
                }               
            connector1E.closeConn();  

            
            int result = JOptionPane.showOptionDialog(null,
            myPanel,
            "Edit Details",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Cancel", "Update"}, // this is the array
            "default"); 
            if (result == JOptionPane.NO_OPTION){       
                connector1E.openConn();
                connector1E.prepStmt("UPDATE SUPPLIER "
                                    + "SET supplier_name='"+field1.getText()+"', "
                                    + "supplier_contact = "+field2.getText()+", "
                                    + "supplier_address = '"+ field3.getText()+"\n"+field4.getText()+"\n"+field5.getText() +"'  "
                                    + "WHERE supplier_id ='"+value+"';");
                connector1E.exUpdate();
                connector1E.closeConn();
                setSupplierTable();

            }
        } catch (SQLException ex) {
            Logger.getLogger(PartsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }   
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PartsPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PartsPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PartsPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartsPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>  
        /*JFrame frame = new JFrame();
        PartsPanel p = new PartsPanel();
        frame.add(p);
        frame.setLayout(new BorderLayout());
        frame.add(p, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //new PartsPanel().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSupplierButton;
    private javax.swing.JButton backButton;
    private javax.swing.JTextArea descArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton orderPartButton;
    private javax.swing.JPanel partsPanel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPopupMenu stockPopup;
    private javax.swing.JTable stockTable;
    private javax.swing.JPopupMenu summaryPopup;
    private javax.swing.JTable summaryTable;
    private javax.swing.JPopupMenu supplierPopup;
    private javax.swing.JTable supplierTable;
    private javax.swing.JTabbedPane windowPane;
    // End of variables declaration//GEN-END:variables




}
