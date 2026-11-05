package event_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class studentstatuspanel extends javax.swing.JPanel {

    
    public studentstatuspanel() {
        initComponents();

        // Main panel background
        setBackground(java.awt.Color.WHITE);

        // Title and subtitle
        lblTitle.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 20));
        lblTitle.setForeground(new java.awt.Color(0, 83, 32));

        lblSubtitle.setFont(new java.awt.Font(
                "Arial", java.awt.Font.PLAIN, 13));
        lblSubtitle.setForeground(new java.awt.Color(85, 85, 85));

        // Refresh button
        refreshButton.setBackground(new java.awt.Color(8, 107, 58));
        refreshButton.setForeground(java.awt.Color.WHITE);
        refreshButton.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);

        // Table styling
        styleStatusTable();

        // Load registration data
        loadStatus();
    }


    private void loadStatus() {
        DefaultTableModel model =
                (DefaultTableModel) statusTable.getModel();

        model.setRowCount(0);

        UserSession session = UserSession.getInstance();

        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(
                    this, "Please log in to view your registrations.");
            return;
        }

        String sql =
                "SELECT e.event_name, e.event_date, e.venue, r.status "
              + "FROM tbl_registrations r "
              + "JOIN tbl_events e ON r.event_id = e.id "
              + "WHERE r.user_id = ? "
              + "ORDER BY e.event_date ASC";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Could not connect to the database.");
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, session.getUserId());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("event_name"),
                            rs.getDate("event_date"),
                            rs.getString("venue"),
                            rs.getString("status")
                        });
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading registration status: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    

    private void styleStatusTable() {

        java.awt.Color green = new java.awt.Color(0, 83, 32);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color white = java.awt.Color.WHITE;
        java.awt.Color lightBackground =
                new java.awt.Color(244, 247, 244);
        java.awt.Color borderColor =
                new java.awt.Color(220, 230, 223);

        // TABLE HEADER
        javax.swing.table.JTableHeader header =
                statusTable.getTableHeader();

        header.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 13));
        header.setBackground(green);
        header.setForeground(white);
        header.setOpaque(true);
        header.setPreferredSize(
                new java.awt.Dimension(0, 38));
        header.setReorderingAllowed(false);

        javax.swing.table.DefaultTableCellRenderer headerRenderer =
                new javax.swing.table.DefaultTableCellRenderer();

        headerRenderer.setBackground(green);
        headerRenderer.setForeground(white);
        headerRenderer.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 13));
        headerRenderer.setHorizontalAlignment(
                javax.swing.SwingConstants.CENTER);
        headerRenderer.setOpaque(true);

        for (int i = 0;
                i < statusTable.getColumnModel().getColumnCount();
                i++) {

            statusTable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // TABLE BODY
        statusTable.setFont(new java.awt.Font(
                "Arial", java.awt.Font.PLAIN, 13));
        statusTable.setRowHeight(36);
        statusTable.setBackground(white);
        statusTable.setForeground(darkGreen);

        statusTable.setSelectionBackground(gold);
        statusTable.setSelectionForeground(darkGreen);

        statusTable.setGridColor(borderColor);
        statusTable.setShowGrid(true);
        statusTable.setFillsViewportHeight(true);
        statusTable.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);

        statusTable.setIntercellSpacing(
                new java.awt.Dimension(0, 1));

        // MAKE TABLE COLUMNS FIT THE SCROLL PANE
        statusTable.setAutoResizeMode(
                javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        // SCROLL PANE
        jScrollPane1.getViewport().setBackground(white);
        jScrollPane1.setBackground(white);
        jScrollPane1.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        borderColor, 1));

        // REFRESH BUTTON
        refreshButton.setBackground(green);
        refreshButton.setForeground(white);
        refreshButton.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR));

        header.repaint();
        statusTable.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        statusTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTitle.setText("MY REGISTRATION STATUS");

        lblSubtitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle.setText("View your registered school events");

        statusTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Event Name", "Event Date", "Venue", "Registration Status"
            }
        ));
        jScrollPane1.setViewportView(statusTable);

        refreshButton.setForeground(new java.awt.Color(0, 102, 0));
        refreshButton.setText("Refresh Events");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblSubtitle)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addGap(226, 226, 226)))
                .addContainerGap(333, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton)
                .addContainerGap(89, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadStatus();
    }//GEN-LAST:event_refreshButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable statusTable;
    // End of variables declaration//GEN-END:variables
}
