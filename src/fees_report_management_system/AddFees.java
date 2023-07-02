/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fees_report_management_system;

import collegefeesmanagementsystem.NumberToWordsConverter;
import java.awt.Color;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;


/**
 *
 * @author HP
 */
public class AddFees extends javax.swing.JFrame {

    /**
     * Creates new form AddFees
     */
    public AddFees() {
        initComponents();
        displayCashFirst();
        fillCoursesComboBox();
        int recieptNo = getRecieptNo();
        txt_RecieptNo.setText(Integer.toString(recieptNo));
               
    }
    
    
    
    
    public void displayCashFirst(){
            lbl_DD.setVisible(false);
            lbl_Cheque.setVisible(false);
            lbl_BankName.setVisible(false);
            
            txt_DD.setVisible(false);
            txt_Cheque.setVisible(false);
            txt_BankName.setVisible(false);
    }
    
    
    public boolean FeesAddValidation(){
        if(combo_ModeOfPayment.getSelectedItem().toString().equalsIgnoreCase("Cheque")){
            if(txt_Cheque.getText().equals("")){
                JOptionPane.showMessageDialog(PanelParent, "Please enter Cheque No.");
            return false;
            }
            if(txt_BankName.getText().equals("")){
                JOptionPane.showMessageDialog(PanelParent, "Please enter Bank name");
            return false;
            }
        }
        
        if(combo_ModeOfPayment.getSelectedItem().toString().equalsIgnoreCase("DD")){
            if(txt_DD.getText().equals("")){
                JOptionPane.showMessageDialog(PanelParent, "Please enter DD No.");
            return false;
            }
            if(txt_BankName.getText().equals("")){
                JOptionPane.showMessageDialog(PanelParent, "Please enter Bank name");
            return false;
            }
        }
        
        if(combo_ModeOfPayment.getSelectedItem().toString().equalsIgnoreCase("Card")){
            if(txt_BankName.getText().equals("")){
                JOptionPane.showMessageDialog(PanelParent, "Please enter Bank name");
            return false;
            }
        }
        
        if(date.getDate() == null){
            JOptionPane.showMessageDialog(PanelParent, "Please Select date");
            return false;
        }
      
        if(txt_RecievedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(PanelParent, "Please enter Recieved From Name");
            return false;
        }
        
        if(txt_Amount.getText().equals("") || txt_Amount.getText().matches("[0-9]+") == false){
            JOptionPane.showMessageDialog(PanelParent, "Please enter Correct Amount");
            return false;
        }
        
        return true;
    }
    
    
    public void fillCoursesComboBox(){
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            String conUrl = "jdbc:mysql://localhost:3306/fees_report_management";
            String conUserName = "root";
            String conPassword = "Admin@123";
            Connection con = DriverManager.getConnection(conUrl, conUserName, conPassword);
            String Query = "select Course_Name from Courses";
            PreparedStatement stmt = con.prepareStatement(Query);
            ResultSet rs = stmt.executeQuery(Query);
            while(rs.next()){
                combo_Course.addItem(rs.getString("Course_Name"));
            }
   
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public int getRecieptNo(){
        int recieptNo = 0;
        
        try {
            Connection con1 = ConnectionProvider.getConnection();
            String Query = "select max(reciept_no) from fees_details";
            PreparedStatement pstmt = con1.prepareStatement(Query);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                recieptNo = rs.getInt(1);
            }

        } catch (Exception e) {
        }
        
        return recieptNo+1;
    }
    
    public String insertDataToDB(){
        String status = "";
        
        int recept_no = Integer.parseInt(txt_RecieptNo.getText());
        String student_Name = txt_RecievedFrom.getText();
        String roll_no = txt_RollNo.getText();
        String payment_mode = combo_ModeOfPayment.getSelectedItem().toString();
        String cheque_no = txt_Cheque.getText();
        String bank_name = txt_BankName.getText();
        String dd_No = txt_DD.getText();
        String course_name = txt_CourseName.getText();
        String gstin =txt_GSTNo.getText();
        float total_amount = Float.parseFloat(txt_TotalAmount.getText());
        
        SimpleDateFormat DateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String newDate = DateFormat.format(date.getDate());      
               
        float amount = Float.parseFloat(txt_Amount.getText());
        float cgst = Float.parseFloat(txt_CGST.getText());
        float sgst = Float.parseFloat(txt_SGST.getText());
        String total_in_words = txt_AmountInWords.getText();
        String remark = txt_Remark.getText();
        int year1 = Integer.parseInt(txt_YearFrom.getText());
        int year2 = Integer.parseInt(txt_YearTo.getText());
        
        try {
            Connection con1 = ConnectionProvider.getConnection();
            String Query = "insert into fees_details values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = con1.prepareCall(Query);
            pstmt.setInt(1,recept_no);
            pstmt.setString(2,student_Name);
            pstmt.setString(3, roll_no);
            pstmt.setString(4, payment_mode);
            pstmt.setString(5,cheque_no);
            pstmt.setString(6,bank_name);
            pstmt.setString(7,dd_No);
            pstmt.setString(8,course_name);
            pstmt.setString(9, gstin);
            pstmt.setFloat(10,total_amount);
            pstmt.setString(11, newDate);
            pstmt.setFloat(12, amount);
            pstmt.setFloat(13, cgst);
            pstmt.setFloat(14, sgst);
            pstmt.setString(15, total_in_words);
            pstmt.setString(16,remark);
            pstmt.setInt(17, year1);
            pstmt.setInt(18, year2);
            
            int rowCount = pstmt.executeUpdate();
            if(rowCount == 1){
                status = "DONE";
            }
            else{
                status = "FAIL";
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return status;  
    }
        
        
         

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelSideBar = new javax.swing.JPanel();
        PanelSearchRecord = new javax.swing.JPanel();
        btnSearchRecord = new javax.swing.JLabel();
        PanelEditCourses = new javax.swing.JPanel();
        btnEditCourses = new javax.swing.JLabel();
        PanelCourseList = new javax.swing.JPanel();
        btnCourseList = new javax.swing.JLabel();
        PanelViewAllRecords = new javax.swing.JPanel();
        btnViewAllRecords = new javax.swing.JLabel();
        PanelBack = new javax.swing.JPanel();
        btnBack = new javax.swing.JLabel();
        PanelHome = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        PanelLogout = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();
        PanelParent = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_GSTNo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_DD = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_BankName = new javax.swing.JLabel();
        lbl_Cheque = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_RecieptNo = new javax.swing.JTextField();
        txt_DD = new javax.swing.JTextField();
        txt_Cheque = new javax.swing.JTextField();
        combo_ModeOfPayment = new javax.swing.JComboBox<>();
        txt_BankName = new javax.swing.JTextField();
        date = new com.toedter.calendar.JDateChooser();
        PanelChild = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_YearTo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_RollNo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_TotalAmount = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        combo_Course = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_RecievedFrom = new javax.swing.JTextField();
        txt_YearFrom = new javax.swing.JTextField();
        txt_Amount = new javax.swing.JTextField();
        txt_CGST = new javax.swing.JTextField();
        txt_SGST = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txt_CourseName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_AmountInWords = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Remark = new javax.swing.JTextArea();
        btnPrint = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelSideBar.setBackground(new java.awt.Color(102, 0, 102));
        PanelSideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelSearchRecord.setBackground(new java.awt.Color(102, 0, 102));
        PanelSearchRecord.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelSearchRecord.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearchRecord.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnSearchRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/search2.png"))); // NOI18N
        btnSearchRecord.setText(" Search Record");
        btnSearchRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchRecordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSearchRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSearchRecordMouseExited(evt);
            }
        });
        PanelSearchRecord.add(btnSearchRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, 60));

        PanelSideBar.add(PanelSearchRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 240, 80));

        PanelEditCourses.setBackground(new java.awt.Color(102, 0, 102));
        PanelEditCourses.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelEditCourses.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEditCourses.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnEditCourses.setForeground(new java.awt.Color(255, 255, 255));
        btnEditCourses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/edit2.png"))); // NOI18N
        btnEditCourses.setText("   Edit Courses");
        btnEditCourses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditCoursesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditCoursesMouseExited(evt);
            }
        });
        PanelEditCourses.add(btnEditCourses, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 60));

        PanelSideBar.add(PanelEditCourses, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 240, 80));

        PanelCourseList.setBackground(new java.awt.Color(102, 0, 102));
        PanelCourseList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelCourseList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCourseList.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnCourseList.setForeground(new java.awt.Color(255, 255, 255));
        btnCourseList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/list.png"))); // NOI18N
        btnCourseList.setText(" Course List");
        btnCourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCourseListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCourseListMouseExited(evt);
            }
        });
        PanelCourseList.add(btnCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 60));

        PanelSideBar.add(PanelCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 240, 80));

        PanelViewAllRecords.setBackground(new java.awt.Color(102, 0, 102));
        PanelViewAllRecords.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelViewAllRecords.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnViewAllRecords.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnViewAllRecords.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAllRecords.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/view all record.png"))); // NOI18N
        btnViewAllRecords.setText(" View All Records");
        btnViewAllRecords.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewAllRecordsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewAllRecordsMouseExited(evt);
            }
        });
        PanelViewAllRecords.add(btnViewAllRecords, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 60));

        PanelSideBar.add(PanelViewAllRecords, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 240, 80));

        PanelBack.setBackground(new java.awt.Color(102, 0, 102));
        PanelBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelBack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/back-button.png"))); // NOI18N
        btnBack.setText("       Back ");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBackMouseExited(evt);
            }
        });
        PanelBack.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, 60));

        PanelSideBar.add(PanelBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, 240, 80));

        PanelHome.setBackground(new java.awt.Color(102, 0, 102));
        PanelHome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/home.png"))); // NOI18N
        btnHome.setText("  HOME");
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        PanelHome.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 60));

        PanelSideBar.add(PanelHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 240, 80));

        PanelLogout.setBackground(new java.awt.Color(102, 0, 102));
        PanelLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelLogout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogout.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_report_management_system/Images/logout.png"))); // NOI18N
        btnLogout.setText("      Logout");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });
        PanelLogout.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 60));

        PanelSideBar.add(PanelLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 630, 240, 80));

        getContentPane().add(PanelSideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 800));

        PanelParent.setBackground(new java.awt.Color(153, 0, 153));
        PanelParent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        PanelParent.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 130, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Reciept No. :  ABC-");
        PanelParent.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 130, -1));

        txt_GSTNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_GSTNo.setForeground(new java.awt.Color(255, 255, 255));
        txt_GSTNo.setText("27ABCDEFGHIJKLMO84");
        PanelParent.add(txt_GSTNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 90, 160, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        PanelParent.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 360, -1));

        lbl_DD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_DD.setForeground(new java.awt.Color(255, 255, 255));
        lbl_DD.setText("DD No :");
        PanelParent.add(lbl_DD, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 70, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Date :");
        PanelParent.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, 50, -1));

        lbl_BankName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_BankName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_BankName.setText("Bank Name :");
        PanelParent.add(lbl_BankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 90, -1));

        lbl_Cheque.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_Cheque.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Cheque.setText("Cheque No :");
        PanelParent.add(lbl_Cheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 90, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Mode of Payment :");
        PanelParent.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 130, -1));

        txt_RecieptNo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_RecieptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RecieptNoActionPerformed(evt);
            }
        });
        PanelParent.add(txt_RecieptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 180, -1));

        txt_DD.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_DD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DDActionPerformed(evt);
            }
        });
        PanelParent.add(txt_DD, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 180, -1));

        txt_Cheque.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_Cheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ChequeActionPerformed(evt);
            }
        });
        PanelParent.add(txt_Cheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 180, -1));

        combo_ModeOfPayment.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combo_ModeOfPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_ModeOfPayment.setSelectedIndex(2);
        combo_ModeOfPayment.setToolTipText("");
        combo_ModeOfPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_ModeOfPaymentActionPerformed(evt);
            }
        });
        PanelParent.add(combo_ModeOfPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 180, -1));

        txt_BankName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_BankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_BankNameActionPerformed(evt);
            }
        });
        PanelParent.add(txt_BankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 180, -1));

        date.setMaxSelectableDate(new java.util.Date(2524591860000L));
        PanelParent.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 40, 140, -1));

        PanelChild.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Roll No. :");
        PanelChild.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 50, 70, -1));

        txt_YearTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_YearTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_YearToActionPerformed(evt);
            }
        });
        PanelChild.add(txt_YearTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 90, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("The following payment in the college office for the year :");
        PanelChild.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 360, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Amount");
        PanelChild.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 140, 70, -1));

        txt_RollNo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_RollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RollNoActionPerformed(evt);
            }
        });
        PanelChild.add(txt_RollNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, 70, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("to");
        PanelChild.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 20, -1));

        txt_TotalAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_TotalAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TotalAmountActionPerformed(evt);
            }
        });
        PanelChild.add(txt_TotalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 310, 90, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Recieved From :");
        PanelChild.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 120, -1));

        combo_Course.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combo_Course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_CourseActionPerformed(evt);
            }
        });
        PanelChild.add(combo_Course, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 180, -1));
        PanelChild.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 133, 1080, 20));
        PanelChild.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1080, 10));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Course :");
        PanelChild.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 70, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("TOTAL");
        PanelChild.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 310, 60, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Head ");
        PanelChild.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 50, -1));

        txt_RecievedFrom.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_RecievedFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_RecievedFromActionPerformed(evt);
            }
        });
        PanelChild.add(txt_RecievedFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 180, -1));

        txt_YearFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_YearFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_YearFromActionPerformed(evt);
            }
        });
        PanelChild.add(txt_YearFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 90, -1));

        txt_Amount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_Amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AmountActionPerformed(evt);
            }
        });
        txt_Amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_AmountKeyTyped(evt);
            }
        });
        PanelChild.add(txt_Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 180, 90, -1));

        txt_CGST.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_CGST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CGSTActionPerformed(evt);
            }
        });
        PanelChild.add(txt_CGST, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 220, 90, -1));

        txt_SGST.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_SGST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SGSTActionPerformed(evt);
            }
        });
        PanelChild.add(txt_SGST, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 260, 90, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Recievers Signature");
        PanelChild.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 130, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("CGST 9%");
        PanelChild.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 220, 60, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("SGST 9%");
        PanelChild.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 260, 60, -1));
        PanelChild.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 250, 20));

        txt_CourseName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_CourseName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CourseNameActionPerformed(evt);
            }
        });
        PanelChild.add(txt_CourseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 230, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Sr. No.");
        PanelChild.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 60, -1));
        PanelChild.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 450, 180, 10));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Remark :");
        PanelChild.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 70, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Total in Words :");
        PanelChild.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 100, -1));

        txt_AmountInWords.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_AmountInWords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AmountInWordsActionPerformed(evt);
            }
        });
        PanelChild.add(txt_AmountInWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, 470, -1));

        txt_Remark.setColumns(20);
        txt_Remark.setRows(5);
        jScrollPane1.setViewportView(txt_Remark);

        PanelChild.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 470, -1));

        btnPrint.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        PanelChild.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 510, -1, -1));

        PanelParent.add(PanelChild, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 1130, 580));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("GSTN : ");
        PanelParent.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 90, 50, -1));

        getContentPane().add(PanelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 1130, 770));

        setSize(new java.awt.Dimension(1364, 782));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelHome.setBackground(clr);
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelHome.setBackground(clr);
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelLogout.setBackground(clr);
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelLogout.setBackground(clr);
    }//GEN-LAST:event_btnLogoutMouseExited

    private void btnSearchRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchRecordMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelSearchRecord.setBackground(clr);
        
    }//GEN-LAST:event_btnSearchRecordMouseEntered

    private void btnSearchRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchRecordMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelSearchRecord.setBackground(clr);
    }//GEN-LAST:event_btnSearchRecordMouseExited

    private void btnEditCoursesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelEditCourses.setBackground(clr);
    }//GEN-LAST:event_btnEditCoursesMouseEntered

    private void btnEditCoursesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditCoursesMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelEditCourses.setBackground(clr);
    }//GEN-LAST:event_btnEditCoursesMouseExited

    private void btnCourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelCourseList.setBackground(clr);
    }//GEN-LAST:event_btnCourseListMouseEntered

    private void btnCourseListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelCourseList.setBackground(clr);
    }//GEN-LAST:event_btnCourseListMouseExited

    private void btnViewAllRecordsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordsMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelViewAllRecords.setBackground(clr);
    }//GEN-LAST:event_btnViewAllRecordsMouseEntered

    private void btnViewAllRecordsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordsMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelViewAllRecords.setBackground(clr);
    }//GEN-LAST:event_btnViewAllRecordsMouseExited

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        // TODO add your handling code here:
        Color clr = new Color(153,0,153);
        PanelBack.setBackground(clr);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        // TODO add your handling code here:
        Color clr = new Color(102,0,102);
        PanelBack.setBackground(clr);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        // TODO add your handling code here:
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackMouseClicked

    private void txt_YearToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_YearToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_YearToActionPerformed

    private void txt_RecieptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RecieptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_RecieptNoActionPerformed

    private void txt_DDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DDActionPerformed

    private void txt_ChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ChequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ChequeActionPerformed

    private void txt_BankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_BankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_BankNameActionPerformed

    private void txt_RollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_RollNoActionPerformed

    private void txt_TotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TotalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TotalAmountActionPerformed

    private void txt_RecievedFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_RecievedFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_RecievedFromActionPerformed

    private void txt_YearFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_YearFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_YearFromActionPerformed

    private void txt_AmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AmountActionPerformed
        // TODO add your handling code here:
        
        
        String am = txt_Amount.getText();
        
        float amount = Float.parseFloat(am);
        

        Float CGST = (float)(amount * 0.09);
        txt_CGST.setText(CGST.toString());
        
        Float SGST = CGST;
        txt_SGST.setText(SGST.toString());
        
        float total = (float)(amount + CGST + SGST);
        
        txt_TotalAmount.setText(Float.toString(total));
        
        txt_AmountInWords.setText(NumberToWordsConverter.convert((int)total) + " only");
        
        
        
        
    }//GEN-LAST:event_txt_AmountActionPerformed

    private void txt_CGSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CGSTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CGSTActionPerformed

    private void txt_SGSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SGSTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SGSTActionPerformed

    private void txt_CourseNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CourseNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CourseNameActionPerformed

    private void txt_AmountInWordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AmountInWordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AmountInWordsActionPerformed

    private void combo_ModeOfPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_ModeOfPaymentActionPerformed
        // TODO add your handling code here:
        if(combo_ModeOfPayment.getSelectedIndex() == 0){
            lbl_DD.setVisible(true);
            txt_DD.setVisible(true);
            
            lbl_Cheque.setVisible(false);
            txt_Cheque.setVisible(false);
            
            lbl_BankName.setVisible(true);
            txt_BankName.setVisible(true);
        }
        
         if(combo_ModeOfPayment.getSelectedIndex() == 1){
            lbl_DD.setVisible(false);
            txt_DD.setVisible(false);
            
            lbl_Cheque.setVisible(true);
            txt_Cheque.setVisible(true);
            
            lbl_BankName.setVisible(true);
            txt_BankName.setVisible(true);
        }
         
          if(combo_ModeOfPayment.getSelectedIndex() == 2){
            lbl_DD.setVisible(false);
            txt_DD.setVisible(false);
            
            lbl_Cheque.setVisible(false);
            txt_Cheque.setVisible(false);
            
            lbl_BankName.setVisible(false);
            txt_BankName.setVisible(false);
        }
          
          if(combo_ModeOfPayment.getSelectedIndex() == 3){
            lbl_DD.setVisible(false);
            txt_DD.setVisible(false);
            
            lbl_Cheque.setVisible(false);
            txt_Cheque.setVisible(false);
            
            lbl_BankName.setVisible(true);
            txt_BankName.setVisible(true);
        }
    }//GEN-LAST:event_combo_ModeOfPaymentActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        if(FeesAddValidation()){
            String status = insertDataToDB();
            
            if(status.equals("DONE")){
                JOptionPane.showMessageDialog(PanelParent, "New Record Inserted Succesfully");
                
                PrintReciept pr = new PrintReciept();
                pr.setVisible(true);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(PanelParent, "Record Insertion FAILED");
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void combo_CourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_CourseActionPerformed
        // TODO add your handling code here:
        
        txt_CourseName.setText(combo_Course.getSelectedItem().toString());
    }//GEN-LAST:event_combo_CourseActionPerformed

    private void txt_AmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AmountKeyTyped
        // TODO add your handling code here:
            
    }//GEN-LAST:event_txt_AmountKeyTyped

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // TODO add your handling code here:
        exit(0);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnSearchRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchRecordMouseClicked
        // TODO add your handling code here:
        SearchRecord searchRecords = new SearchRecord();
        searchRecords.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSearchRecordMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        // TODO add your handling code here:
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddFees().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBack;
    private javax.swing.JPanel PanelChild;
    private javax.swing.JPanel PanelCourseList;
    private javax.swing.JPanel PanelEditCourses;
    private javax.swing.JPanel PanelHome;
    private javax.swing.JPanel PanelLogout;
    private javax.swing.JPanel PanelParent;
    private javax.swing.JPanel PanelSearchRecord;
    private javax.swing.JPanel PanelSideBar;
    private javax.swing.JPanel PanelViewAllRecords;
    private javax.swing.JLabel btnBack;
    private javax.swing.JLabel btnCourseList;
    private javax.swing.JLabel btnEditCourses;
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel btnSearchRecord;
    private javax.swing.JLabel btnViewAllRecords;
    private javax.swing.JComboBox<String> combo_Course;
    private javax.swing.JComboBox<String> combo_ModeOfPayment;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_BankName;
    private javax.swing.JLabel lbl_Cheque;
    private javax.swing.JLabel lbl_DD;
    private javax.swing.JTextField txt_Amount;
    private javax.swing.JTextField txt_AmountInWords;
    private javax.swing.JTextField txt_BankName;
    private javax.swing.JTextField txt_CGST;
    private javax.swing.JTextField txt_Cheque;
    private javax.swing.JTextField txt_CourseName;
    private javax.swing.JTextField txt_DD;
    private javax.swing.JLabel txt_GSTNo;
    private javax.swing.JTextField txt_RecieptNo;
    private javax.swing.JTextField txt_RecievedFrom;
    private javax.swing.JTextArea txt_Remark;
    private javax.swing.JTextField txt_RollNo;
    private javax.swing.JTextField txt_SGST;
    private javax.swing.JTextField txt_TotalAmount;
    private javax.swing.JTextField txt_YearFrom;
    private javax.swing.JTextField txt_YearTo;
    // End of variables declaration//GEN-END:variables
}
