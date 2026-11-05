package event_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class staffrecordspanel extends javax.swing.JPanel {


    public staffrecordspanel() {
        initComponents();
        setupRecordsTable();
        styleRecordsPanel();
        loadRecords();
    }
   
    private DefaultTableModel tableModel;


    private void setupRecordsTable() {
        String[] columns = {
            "Student ID",
            "Student Name",
            "Event Name",
            "Registration Date",
            "Registration Status",
            "Attendance"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblRecords.setModel(tableModel);

        tblRecords.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        tblRecords.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );

        tblRecords.setRowHeight(32);
        tblRecords.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)
        );

        tblRecords.getTableHeader().setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );

        tblRecords.getTableHeader().setOpaque(true);
        tblRecords.getTableHeader().setBackground(
            new java.awt.Color(18, 107, 69)
        );
        tblRecords.getTableHeader().setForeground(
            java.awt.Color.WHITE
        );

        tblRecords.getTableHeader().setDefaultRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table,
                        Object value,
                        boolean isSelected,
                        boolean hasFocus,
                        int row,
                        int column) {

                    javax.swing.JLabel label =
                        (javax.swing.JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column
                        );

                    label.setBackground(new java.awt.Color(18, 107, 69));
                    label.setForeground(java.awt.Color.WHITE);
                    label.setFont(
                        new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
                    );
                    label.setHorizontalAlignment(
                        javax.swing.JLabel.CENTER
                    );
                    label.setOpaque(true);

                    return label;
                }
            }
        );

        tblRecords.setSelectionBackground(
            new java.awt.Color(240, 215, 143)
        );
        tblRecords.setSelectionForeground(
            new java.awt.Color(25, 75, 53)
        );

        tblRecords.setGridColor(
            new java.awt.Color(220, 225, 220)
        );
        tblRecords.setShowGrid(true);
        tblRecords.setFillsViewportHeight(true);

        jScrollPane1.setBorder(
            javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(220, 230, 223)
            )
        );
        jScrollPane1.getViewport().setBackground(
            java.awt.Color.WHITE
        );
    }


    private void styleRecordsPanel() {
        java.awt.Color green = new java.awt.Color(8, 107, 58);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color border = new java.awt.Color(220, 230, 223);
        java.awt.Color background = new java.awt.Color(244, 247, 244);

        setBackground(background);

        // Title
        lblTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTitle.setForeground(green);

        // Subtitle
        lblSubtitle.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        lblSubtitle.setForeground(darkGreen);

        // Refresh button
        refreshButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        refreshButton.setBackground(green);
        refreshButton.setForeground(java.awt.Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.setCursor(new java.awt.Cursor(
            java.awt.Cursor.HAND_CURSOR
        ));

        // Table body
        tblRecords.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        tblRecords.setRowHeight(32);
        tblRecords.setBackground(java.awt.Color.WHITE);
        tblRecords.setForeground(darkGreen);
        tblRecords.setGridColor(border);
        tblRecords.setShowGrid(true);
        tblRecords.setFillsViewportHeight(true);
        tblRecords.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );
        tblRecords.setSelectionBackground(gold);
        tblRecords.setSelectionForeground(darkGreen);
        tblRecords.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );

        // Table header
        javax.swing.table.JTableHeader header = tblRecords.getTableHeader();
        header.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        header.setBackground(green);
        header.setForeground(java.awt.Color.WHITE);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new java.awt.Dimension(0, 35));

        // Scroll pane
        jScrollPane1.setBorder(
            javax.swing.BorderFactory.createLineBorder(border)
        );
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
    }

    
    private void loadRecords() {
        tableModel.setRowCount(0);

        String sql =
            "SELECT u.student_id, u.full_name, e.event_name, " +
            "r.registration_date, r.status, " +
            "COALESCE(a.attendance_status, 'Not Marked') AS attendance " +
            "FROM tbl_registrations r " +
            "JOIN tbl_users u ON r.user_id = u.id " +
            "JOIN tbl_events e ON r.event_id = e.id " +
            "LEFT JOIN tbl_attendance a ON r.id = a.registration_id " +
            "WHERE u.role = 'student' " +
            "ORDER BY r.registration_date DESC";

        try (Connection con = DBConnection.connect()) {
            if (con == null) {
                JOptionPane.showMessageDialog(
                    this, "Database connection failed."
                );
                return;
            }

            try (PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    tableModel.addRow(new Object[] {
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("event_name"),
                        rs.getString("registration_date"),
                        rs.getString("status"),
                        rs.getString("attendance")
                    });
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error loading records: " + e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecords = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTitle.setText("STUDENT RECORDS");

        lblSubtitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle.setText("View student registrations and attendance records");

        tblRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Event Name", "Registration Date", "Registration Status", "Attendance"
            }
        ));
        jScrollPane1.setViewportView(tblRecords);

        refreshButton.setForeground(new java.awt.Color(0, 102, 0));
        refreshButton.setText("Refresh Records");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubtitle)
                            .addComponent(refreshButton)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(600, 600, 600)
                        .addComponent(lblTitle)))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton)
                .addContainerGap(244, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadRecords();
    }//GEN-LAST:event_refreshButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable tblRecords;
    // End of variables declaration//GEN-END:variables
}
