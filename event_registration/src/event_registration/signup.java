package event_registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;

public class signup extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(signup.class.getName());

    public signup() {
        initComponents();
        resizeLogo();

        getRootPane().setDefaultButton(signupButton);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void resizeLogo() {
        javax.swing.ImageIcon originalIcon =
                new javax.swing.ImageIcon(
                        getClass().getResource("/event_registration/coc.png"));

        java.awt.Image scaledImage = originalIcon.getImage()
                .getScaledInstance(
                        150, 150,
                        java.awt.Image.SCALE_SMOOTH);

        jLabel2.setIcon(new javax.swing.ImageIcon(scaledImage));
        jLabel2.setPreferredSize(new java.awt.Dimension(150, 150));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        studentIDLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        studentID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        fullname = new javax.swing.JTextField();
        cmbYearLevel = new javax.swing.JComboBox<>();
        cmbCourse = new javax.swing.JComboBox<>();
        confirmpassword = new javax.swing.JPasswordField();
        fullnameLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        signupButton = new javax.swing.JButton();
        showpass = new javax.swing.JCheckBox();
        login = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBackground.setBackground(new java.awt.Color(244, 247, 244));
        pnlBackground.setMinimumSize(new java.awt.Dimension(100, 100));
        pnlBackground.setPreferredSize(new java.awt.Dimension(600, 700));
        pnlBackground.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(674, 821));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/event_registration/coc.png"))); // NOI18N
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 83, 32));
        jLabel8.setText("   PHINMA COC   ");
        jLabel8.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 83, 32));
        jLabel3.setText("School Event Registration &");
        jLabel3.setOpaque(true);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 83, 32));
        jLabel1.setText("Attendance System");
        jLabel1.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(25, 75, 53));
        jLabel9.setText("Password:");
        jLabel9.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(25, 75, 53));
        jLabel11.setText("Course:");

        email.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        email.addActionListener(this::emailActionPerformed);

        studentIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        studentIDLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        studentIDLabel.setForeground(new java.awt.Color(25, 75, 53));
        studentIDLabel.setText("Student ID:");
        studentIDLabel.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(25, 75, 53));
        jLabel10.setText("Confirm Password:");
        jLabel10.setOpaque(true);

        studentID.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        studentID.addActionListener(this::studentIDActionPerformed);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(25, 75, 53));
        jLabel7.setText("Email:");

        password.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N

        fullname.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        fullname.addActionListener(this::fullnameActionPerformed);

        cmbYearLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Year Level", "1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year" }));

        cmbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course", "Bachelor of Science in Accountancy", "Bachelor of Science in Business Administration", "Bachelor of Elementary Education", "Bachelor of Secondary Education", "Bachelor of Science in Criminology", "Bachelor of Science in Civil Engineering", "Bachelor of Science in Information Technology", "Bachelor of Science in Nursing", "Bachelor of Science in Psychology" }));

        confirmpassword.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N

        fullnameLabel.setBackground(new java.awt.Color(255, 255, 255));
        fullnameLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        fullnameLabel.setForeground(new java.awt.Color(25, 75, 53));
        fullnameLabel.setText("Full Name:");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(25, 75, 53));
        jLabel12.setText("Year Level:");

        signupButton.setBackground(new java.awt.Color(0, 83, 32));
        signupButton.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        signupButton.setForeground(new java.awt.Color(255, 255, 255));
        signupButton.setText("   SIGNUP   ");
        signupButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        signupButton.addActionListener(this::signupButtonActionPerformed);

        showpass.setBackground(new java.awt.Color(255, 255, 255));
        showpass.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        showpass.setForeground(new java.awt.Color(25, 75, 53));
        showpass.setText("Show Password");
        showpass.addActionListener(this::showpassActionPerformed);

        login.setBackground(new java.awt.Color(240, 215, 143));
        login.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        login.setForeground(new java.awt.Color(25, 75, 53));
        login.setText("   LOGIN   ");
        login.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        login.addActionListener(this::loginActionPerformed);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(25, 75, 53));
        jLabel6.setText("Already have an account?");
        jLabel6.setToolTipText("");
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(112, 112, 112)
                            .addComponent(fullnameLabel)
                            .addGap(70, 70, 70)
                            .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(256, 256, 256)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(279, 279, 279)
                            .addComponent(jLabel1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(63, 63, 63)
                                .addComponent(cmbYearLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(88, 88, 88)
                                .addComponent(cmbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(102, 102, 102)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(studentIDLabel)
                                .addGap(66, 66, 66)
                                .addComponent(studentID, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(66, 66, 66))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(signupButton)
                                        .addGap(89, 89, 89)
                                        .addComponent(showpass))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(login)
                        .addGap(181, 181, 181))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(189, 189, 189))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fullnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbYearLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(showpass))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(signupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(login))
                .addGap(17, 17, 17))
        );

        pnlBackground.add(jPanel1, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1351, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed

    String name = fullname.getText().trim();
    String studentIDValue = studentID.getText().trim();
    String emailValue = email.getText().trim();
    String course = (String) cmbCourse.getSelectedItem();
    String yearLevel = (String) cmbYearLevel.getSelectedItem();
    String passwordValue = new String(password.getPassword());
    String confirmPasswordValue =
            new String(confirmpassword.getPassword());

    // 1. Validate Full Name
    if (name.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Please enter your full name.");
        fullname.requestFocus();
        return;
    }

    if (!name.matches(
            "(?U)[\\p{L}][\\p{L} .'-]*\\s+[\\p{L}][\\p{L} .'-]*")) {
        JOptionPane.showMessageDialog(this,
                "Enter your first name followed by your last name.\n"
                + "Example: Juan Dela Cruz");
        fullname.requestFocus();
        return;
    }

    // 2. Validate Student ID
    if (studentIDValue.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Student ID is required.");
        studentID.requestFocus();
        return;
    }

    // 3. Validate Email
    if (emailValue.isEmpty() || !emailValue.contains("@")) {
        JOptionPane.showMessageDialog(this,
                "Please enter an email address containing @.");
        email.requestFocus();
        return;
    }

    // 4. Validate Course
    if (course == null || course.equals("Select Course")) {
        JOptionPane.showMessageDialog(this,
                "Please select your course.");
        cmbCourse.requestFocus();
        return;
    }

    // 5. Validate Year Level
    if (yearLevel == null ||
            yearLevel.equals("Select Year Level")) {
        JOptionPane.showMessageDialog(this,
                "Please select your year level.");
        cmbYearLevel.requestFocus();
        return;
    }

    // 6. Validate Password
    if (passwordValue.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Password is required.");
        password.requestFocus();
        return;
    }

    // 7. Validate Confirm Password
    if (confirmPasswordValue.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Please confirm your password.");
        confirmpassword.requestFocus();
        return;
    }

    if (!passwordValue.equals(confirmPasswordValue)) {
        JOptionPane.showMessageDialog(this,
                "Passwords do not match. Please try again.");
        confirmpassword.setText("");
        confirmpassword.requestFocus();
        return;
    }

    String role = "Student";

    String url =
            "jdbc:mysql://localhost:3306/event_registration";
    String dbUser = "root";
    String dbPass = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection con =
                DriverManager.getConnection(url, dbUser, dbPass)) {

            // 8. Check for Duplicate Student ID or Email
            String checkSQL =
                    "SELECT student_id, email FROM tbl_users "
                    + "WHERE student_id = ? OR email = ?";

            try (PreparedStatement check =
                    con.prepareStatement(checkSQL)) {

                check.setString(1, studentIDValue);
                check.setString(2, emailValue);

                try (java.sql.ResultSet rs =
                        check.executeQuery()) {

                    while (rs.next()) {
                        String existingID =
                                rs.getString("student_id");
                        String existingEmail =
                                rs.getString("email");

                        if (studentIDValue.equals(existingID)) {
                            JOptionPane.showMessageDialog(this,
                                    "This Student ID is already registered.");
                            studentID.requestFocus();
                            return;
                        }

                        if (existingEmail != null &&
                                emailValue.equalsIgnoreCase(existingEmail)) {
                            JOptionPane.showMessageDialog(this,
                                    "This email is already registered.");
                            email.requestFocus();
                            return;
                        }
                    }
                }
            }

            // 9. Insert Student Account
            String insertSQL =
                    "INSERT INTO tbl_users "
                    + "(full_name, student_id, email, password, "
                    + "role, course, year_level) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(
                    insertSQL,
                    java.sql.Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, name);
                ps.setString(2, studentIDValue);
                ps.setString(3, emailValue);

                // Use a secure password hash before real deployment.
                ps.setString(4, passwordValue);

                ps.setString(5, role);
                ps.setString(6, course);
                ps.setString(7, yearLevel);

                ps.executeUpdate();

                // 10. Initialize the new student's session
                try (java.sql.ResultSet keys =
                        ps.getGeneratedKeys()) {

                    if (keys.next()) {
                        int userId = keys.getInt(1);

                        UserSession.getInstance().setUser(
                                userId, emailValue);
                    } else {
                        throw new SQLException(
                                "Could not retrieve the new student ID.");
                    }
                }
            }
        }

        // 11. Registration Successful
        JOptionPane.showMessageDialog(this,
                "Account created successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        fullname.setText("");
        studentID.setText("");
        email.setText("");
        password.setText("");
        confirmpassword.setText("");
        cmbCourse.setSelectedIndex(0);
        cmbYearLevel.setSelectedIndex(0);

        // 12. Open Student Dashboard
        studentdashboard dashboard = new studentdashboard();
        dashboard.setVisible(true);
        this.dispose();

    } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(this,
                "MySQL JDBC Driver not found.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);

    } catch (SQLException e) {
        if ("23000".equals(e.getSQLState())) {
            JOptionPane.showMessageDialog(this,
                    "This Student ID or email is already registered.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        e.printStackTrace();
        }
    }//GEN-LAST:event_signupButtonActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        login su = new login();
        su.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_loginActionPerformed

    private void showpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpassActionPerformed
        if (showpass.isSelected()) {
        // Show both password fields
        password.setEchoChar((char) 0);
        confirmpassword.setEchoChar((char) 0);
    } else {
        // Hide both password fields
        password.setEchoChar('•');
        confirmpassword.setEchoChar('•');
    }
    }//GEN-LAST:event_showpassActionPerformed

    private void studentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentIDActionPerformed

    private void fullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new signup().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbCourse;
    private javax.swing.JComboBox<String> cmbYearLevel;
    private javax.swing.JPasswordField confirmpassword;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fullname;
    private javax.swing.JLabel fullnameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField password;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JCheckBox showpass;
    private javax.swing.JButton signupButton;
    private javax.swing.JTextField studentID;
    private javax.swing.JLabel studentIDLabel;
    // End of variables declaration//GEN-END:variables
}
