package event_registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(login.class.getName());

    public login() {
        initComponents();
        
        pnlLogin.setPreferredSize(
        new java.awt.Dimension(600, 700)
        );

        pnlLogin.setMinimumSize(
            new java.awt.Dimension(600, 700)
        );

        pnlLogin.setMaximumSize(
            new java.awt.Dimension(600, 700)
        );

        pnlBackground.revalidate();
        pnlBackground.repaint();

        resizeLogo(150, 150);

        getRootPane().setDefaultButton(loginButton);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void resizeLogo(int width, int height) {
        java.net.URL imageURL =
                getClass().getResource("/event_registration/coc.png");

        if (imageURL == null) {
            return;
        }

        javax.swing.ImageIcon originalIcon =
                new javax.swing.ImageIcon(imageURL);

        java.awt.Image scaledImage =
                originalIcon.getImage().getScaledInstance(
                        width,
                        height,
                        java.awt.Image.SCALE_SMOOTH
                );

        jLabel7.setIcon(new javax.swing.ImageIcon(scaledImage));
        jLabel7.setPreferredSize(new java.awt.Dimension(width, height));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlBackground = new javax.swing.JPanel();
        pnlLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        showpass = new javax.swing.JCheckBox();
        loginButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        signup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(950, 650));

        pnlBackground.setBackground(new java.awt.Color(0, 83, 32));
        pnlBackground.setLayout(new java.awt.GridBagLayout());

        pnlLogin.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogin.setPreferredSize(new java.awt.Dimension(600, 700));
        pnlLogin.setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/event_registration/coc.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(0, 83, 32));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 83, 32));
        jLabel2.setText("   PHINMA COC   ");

        jLabel3.setBackground(new java.awt.Color(25, 75, 53));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(25, 75, 53));
        jLabel3.setText("School Event Registration &");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 83, 32));
        jLabel1.setText("Attendance System");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(25, 75, 53));
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(25, 75, 53));
        jLabel5.setText("Password:");

        email.setFont(new java.awt.Font("Sitka Text", 0, 18)); // NOI18N
        email.addActionListener(this::emailActionPerformed);

        password.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N

        showpass.setBackground(new java.awt.Color(255, 255, 255));
        showpass.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        showpass.setForeground(new java.awt.Color(25, 75, 53));
        showpass.setText("Show Password");
        showpass.addActionListener(this::showpassActionPerformed);

        loginButton.setBackground(new java.awt.Color(0, 83, 32));
        loginButton.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("   LOGIN   ");
        loginButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        loginButton.addActionListener(this::loginButtonActionPerformed);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(25, 75, 53));
        jLabel6.setText("Don't have an account yet?");
        jLabel6.setToolTipText("");
        jLabel6.setOpaque(true);

        signup.setBackground(new java.awt.Color(240, 215, 143));
        signup.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        signup.setForeground(new java.awt.Color(25, 75, 53));
        signup.setText("   SIGNUP   ");
        signup.setActionCommand("");
        signup.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        signup.addActionListener(this::signupActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(showpass)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(email, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(password, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(signup))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(40, 40, 40)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2))
                                .addGap(14, 14, 14))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(325, 325, 325)
                        .addComponent(loginButton)))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(showpass)
                .addGap(11, 11, 11)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(signup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104))
        );

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 115;
        gridBagConstraints.ipady = 128;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(53, 398, 121, 383);
        pnlBackground.add(pnlLogin, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1455, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpassActionPerformed
        if (showpass.isSelected()) {
            password.setEchoChar((char) 0);
        } else {
            password.setEchoChar('•');
        }
    }//GEN-LAST:event_showpassActionPerformed

    private void signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupActionPerformed
        signup su = new signup();
        su.setVisible(true);
        this.dispose();   // closes the current login window
    }//GEN-LAST:event_signupActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String emailValue = email.getText().trim();
        String passwordValue = new String(password.getPassword());

        // Validate empty fields
        if (emailValue.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Email is required.",
                    "Missing Information",
                    JOptionPane.ERROR_MESSAGE);
            email.requestFocus();
            return;
        }

        if (passwordValue.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Password is required.",
                    "Missing Information",
                    JOptionPane.ERROR_MESSAGE);
            password.requestFocus();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/event_registration";
        String dbUser = "root";
        String dbPass = "";

        String query = "SELECT id, full_name, role, password, course, year_level "
        + "FROM tbl_users WHERE email = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con =
                    DriverManager.getConnection(url, dbUser, dbPass);
                 PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, emailValue);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        String storedPassword = rs.getString("password");

                        // For your current plain-text password setup
                        if (passwordValue.equals(storedPassword)) {

                            int userId = rs.getInt("id");
                                String fullName = rs.getString("full_name");
                                String role = rs.getString("role");
                                String course = rs.getString("course");
                                String yearLevel = rs.getString("year_level");

                                UserSession.getInstance().setUser(userId, emailValue);

                                UserSession.getInstance().setUserDetails(
                                        fullName,
                                        course,
                                        yearLevel,
                                        role
                                );

                            JOptionPane.showMessageDialog(this,
                                    "Login successful!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);

                            email.setText("");
                            password.setText("");

                            if ("Staff".equalsIgnoreCase(role)) {

                                staffdashboard staffDashboard =
                                        new staffdashboard();

                                staffDashboard.setVisible(true);

                            } else if ("Student".equalsIgnoreCase(role)) {

                                studentdashboard studentDashboard =
                                        new studentdashboard(fullName);

                                studentDashboard.setVisible(true);

                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "Your account has an invalid role.",
                                        "Login Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            this.dispose();

                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid email or password.",
                                    "Login Failed",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Invalid email or password.",
                                "Login Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "MySQL JDBC Driver not found.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField email;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField password;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JCheckBox showpass;
    private javax.swing.JButton signup;
    // End of variables declaration//GEN-END:variables
}
