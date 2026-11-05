package event_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class staffattendancepanel extends javax.swing.JPanel {
    
        private DefaultTableModel tableModel;
        
        private static class EventItem {
            private final int id;
            private final String name;

            public EventItem(int id, String name) {
                this.id = id;
                this.name = name;
            }

            public int getId() {
                return id;
            }

            @Override
            public String toString() {
                return name;
            }
        }


    public staffattendancepanel() {
        initComponents();
        setupAttendanceTable();
        styleAttendancePanel();
        loadEvents();
        loadAttendance();
    }


    private void setupAttendanceTable() {
        tableModel = new DefaultTableModel(
            new Object[]{
                "Student ID",
                "Student Name",
                "Event Name",
                "Attendance",
                "Date"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblAttendance.setModel(tableModel);
        tblAttendance.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );
        tblAttendance.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );

        tblAttendance.setRowHeight(32);
        tblAttendance.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)
        );

        tblAttendance.getTableHeader().setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );

        tblAttendance.getTableHeader().setOpaque(true);
        tblAttendance.getTableHeader().setBackground(
            new java.awt.Color(18, 107, 69)
        );
        tblAttendance.getTableHeader().setForeground(
            java.awt.Color.WHITE
        );

        tblAttendance.getTableHeader().setDefaultRenderer(
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

        tblAttendance.setSelectionBackground(
            new java.awt.Color(240, 215, 143)
        );
        tblAttendance.setSelectionForeground(
            new java.awt.Color(25, 75, 53)
        );

        tblAttendance.setGridColor(
            new java.awt.Color(220, 225, 220)
        );
        tblAttendance.setShowGrid(true);
        tblAttendance.setFillsViewportHeight(true);
    }
    
    
    private void styleAttendancePanel() {
        java.awt.Color green = new java.awt.Color(0, 83, 32);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);
        java.awt.Color white = java.awt.Color.WHITE;
        java.awt.Color border = new java.awt.Color(220, 230, 223);

        setBackground(new java.awt.Color(244, 247, 244));

        // Main title
        lblTitle.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 24)
        );
        lblTitle.setForeground(green);

        // Section headings
        lblSubtitleEventAttendance.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 18)
        );
        lblSubtitleEventAttendance.setForeground(green);

        lblSubtitle12.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 13)
        );
        lblSubtitle12.setForeground(new java.awt.Color(85, 85, 85));

        lblSubtitle1.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );
        lblSubtitle1.setForeground(darkGreen);

        lblSubtitle2.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );
        lblSubtitle2.setForeground(darkGreen);

        // Event selector and student ID input
        cmbEvents.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 13)
        );
        txtSearchStudentID.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 13)
        );

        // Action buttons
        styleAttendanceButton(btnMarkPresent, green, white);
        styleAttendanceButton(refreshButton, green, white);
        styleAttendanceButton(btnSearchStudent, green, white);
        styleAttendanceButton(btnClearSearch, gold, darkGreen);
        styleAttendanceButton(
            btnMarkAbsent,
            new java.awt.Color(181, 71, 71),
            white
        );

        // Summary cards
        styleSummaryCard(pnlRegisteredCard);
        styleSummaryCard(pnlPresentCard);
        styleSummaryCard(pnlAbsentCard);
        styleSummaryCard(pnlNotMarkedCard);

        // Table
        tblAttendance.setBackground(java.awt.Color.WHITE);
        tblAttendance.setForeground(new java.awt.Color(25, 75, 53));
        tblAttendance.setGridColor(new java.awt.Color(220, 230, 223));
        tblAttendance.setShowGrid(true);
        tblAttendance.setOpaque(true);
        tblAttendance.setFillsViewportHeight(true);

        // Style the table header
        javax.swing.table.JTableHeader header = tblAttendance.getTableHeader();
        header.setBackground(new java.awt.Color(0, 83, 32));
        header.setForeground(java.awt.Color.WHITE);
        header.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        header.setOpaque(true);

        // Style selected rows
        tblAttendance.setSelectionBackground(new java.awt.Color(240, 215, 143));
        tblAttendance.setSelectionForeground(new java.awt.Color(25, 75, 53));

        // Style the area around the table
        jScrollPane1.setBackground(java.awt.Color.WHITE);
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
            }

    private void styleAttendanceButton(
            javax.swing.JButton button,
            java.awt.Color background,
            java.awt.Color foreground) {

        button.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(
            new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );
    }

    private void styleSummaryCard(javax.swing.JPanel card) {
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                    new java.awt.Color(220, 230, 223)
                ),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        );
    }

    private void loadEvents() {
        cmbEvents.removeAllItems();

        
        String sql = "SELECT id, event_name FROM tbl_events "
                   + "WHERE status = 'Open' "
                   + "AND is_archived = 0 "
                   + "ORDER BY event_date ASC";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                    this, "Unable to connect to the database."
                );
                return;
            }

            try (PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    cmbEvents.addItem(
                        new EventItem(
                            rs.getInt("id"),
                            rs.getString("event_name")
                        )
                    );
                }
            }

            if (cmbEvents.getItemCount() == 0) {
                JOptionPane.showMessageDialog(
                    this, "No open events found."
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error loading events: " + e.getMessage()
            );
        }
    }
    
    
    private void loadAttendance() {

        EventItem selectedEvent =
                (EventItem) cmbEvents.getSelectedItem();

        tableModel.setRowCount(0);

        if (selectedEvent == null) {
            loadAttendanceCount();
            return;
        }

        String sql =
                "SELECT u.student_id, u.full_name, e.event_name, "
                + "COALESCE(a.attendance_status, 'Not Marked') "
                + "AS attendance_status, a.attendance_date "
                + "FROM tbl_registrations r "
                + "JOIN tbl_users u ON r.user_id = u.id "
                + "JOIN tbl_events e ON r.event_id = e.id "
                + "LEFT JOIN tbl_attendance a "
                + "ON a.registration_id = r.id "
                + "WHERE r.event_id = ? "
                + "ORDER BY u.full_name ASC";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Unable to connect to the database."
                );
                return;
            }

            try (PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, selectedEvent.getId());

                try (ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {

                        tableModel.addRow(new Object[]{
                            rs.getString("student_id"),
                            rs.getString("full_name"),
                            rs.getString("event_name"),
                            rs.getString("attendance_status"),
                            rs.getTimestamp("attendance_date")
                        });

                    }
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading participants: " + e.getMessage()
            );
        }

        loadAttendanceCount();
    }

    
    
    private void loadAttendanceCount() {

        EventItem selectedEvent =
                (EventItem) cmbEvents.getSelectedItem();

        if (selectedEvent == null) {
            lblRegisteredCount.setText("0");
            lblAttendanceCount.setText("0");
            lblAbsentCount.setText("0");
            lblNotMarkedCount.setText("0");
            return;
        }

        String sql =
                "SELECT COUNT(DISTINCT r.id) AS registered, "
                + "COUNT(DISTINCT CASE "
                + "WHEN a.attendance_status = 'Present' "
                + "THEN r.id END) AS present_count, "
                + "COUNT(DISTINCT CASE "
                + "WHEN a.attendance_status = 'Absent' "
                + "THEN r.id END) AS absent_count "
                + "FROM tbl_registrations r "
                + "LEFT JOIN tbl_attendance a "
                + "ON a.registration_id = r.id "
                + "WHERE r.event_id = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to connect to the database."
                );
                return;
            }

            try (PreparedStatement pst =
                    conn.prepareStatement(sql)) {

                pst.setInt(1, selectedEvent.getId());

                try (ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {

                        int registered =
                                rs.getInt("registered");

                        int present =
                                rs.getInt("present_count");

                        int absent =
                                rs.getInt("absent_count");

                        int notMarked =
                                registered - present - absent;

                        lblRegisteredCount.setText(
                                String.valueOf(registered)
                        );

                        lblAttendanceCount.setText(
                                String.valueOf(present)
                        );

                        lblAbsentCount.setText(
                                String.valueOf(absent)
                        );

                        lblNotMarkedCount.setText(
                                String.valueOf(notMarked)
                        );
                    }
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading attendance counts: "
                    + e.getMessage()
            );
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSubtitle1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitleEventAttendance = new javax.swing.JLabel();
        lblSubtitle2 = new javax.swing.JLabel();
        cmbEvents = new javax.swing.JComboBox<>();
        txtSearchStudentID = new javax.swing.JTextField();
        btnMarkPresent = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAttendance = new javax.swing.JTable();
        lblSubtitle12 = new javax.swing.JLabel();
        pnlRegisteredCard = new javax.swing.JPanel();
        lblRegisteredCount = new javax.swing.JLabel();
        lblRegisteredTitle = new javax.swing.JLabel();
        pnlAbsentCard = new javax.swing.JPanel();
        lblAbsentCount = new javax.swing.JLabel();
        lblAbsentTitle = new javax.swing.JLabel();
        pnlPresentCard = new javax.swing.JPanel();
        lblAttendanceCount = new javax.swing.JLabel();
        lblPresentTitle = new javax.swing.JLabel();
        pnlNotMarkedCard = new javax.swing.JPanel();
        lblNotMarkedCount = new javax.swing.JLabel();
        lblNotMarkedTitle = new javax.swing.JLabel();
        btnMarkAbsent = new javax.swing.JButton();
        btnSearchStudent = new javax.swing.JButton();
        btnClearSearch = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 247, 244));

        lblSubtitle1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle1.setText("Student ID");

        lblTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTitle.setText("ATTENDANCE MANAGEMENT");

        lblSubtitleEventAttendance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitleEventAttendance.setText("Event Attendance");

        lblSubtitle2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle2.setText("Select Event");

        cmbEvents.addItemListener(this::cmbEventsItemStateChanged);
        cmbEvents.addActionListener(this::cmbEventsActionPerformed);

        txtSearchStudentID.addActionListener(this::txtSearchStudentIDActionPerformed);

        btnMarkPresent.setText("Mark Present");
        btnMarkPresent.addActionListener(this::btnMarkPresentActionPerformed);

        refreshButton.setForeground(new java.awt.Color(0, 102, 0));
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        tblAttendance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Event Name", "Attendance", "Date"
            }
        ));
        jScrollPane1.setViewportView(tblAttendance);

        lblSubtitle12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle12.setText("Select an event and mark student attendance");

        pnlRegisteredCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlRegisteredCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 223)));
        pnlRegisteredCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblRegisteredCount.setFont(new java.awt.Font("Arial", 1, 26)); // NOI18N
        lblRegisteredCount.setForeground(new java.awt.Color(25, 107, 69));
        lblRegisteredCount.setText("0");

        lblRegisteredTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblRegisteredTitle.setForeground(new java.awt.Color(82, 102, 90));
        lblRegisteredTitle.setText("Registered Participants");

        javax.swing.GroupLayout pnlRegisteredCardLayout = new javax.swing.GroupLayout(pnlRegisteredCard);
        pnlRegisteredCard.setLayout(pnlRegisteredCardLayout);
        pnlRegisteredCardLayout.setHorizontalGroup(
            pnlRegisteredCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlRegisteredCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRegisteredTitle)
                    .addComponent(lblRegisteredCount, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        pnlRegisteredCardLayout.setVerticalGroup(
            pnlRegisteredCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblRegisteredTitle)
                .addGap(18, 18, 18)
                .addComponent(lblRegisteredCount)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pnlAbsentCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlAbsentCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 223)));
        pnlAbsentCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblAbsentCount.setFont(new java.awt.Font("Arial", 1, 26)); // NOI18N
        lblAbsentCount.setForeground(new java.awt.Color(181, 71, 71));
        lblAbsentCount.setText("0");

        lblAbsentTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblAbsentTitle.setForeground(new java.awt.Color(82, 102, 90));
        lblAbsentTitle.setText("Absent");

        javax.swing.GroupLayout pnlAbsentCardLayout = new javax.swing.GroupLayout(pnlAbsentCard);
        pnlAbsentCard.setLayout(pnlAbsentCardLayout);
        pnlAbsentCardLayout.setHorizontalGroup(
            pnlAbsentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAbsentCardLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlAbsentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAbsentTitle)
                    .addComponent(lblAbsentCount))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        pnlAbsentCardLayout.setVerticalGroup(
            pnlAbsentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAbsentCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblAbsentTitle)
                .addGap(18, 18, 18)
                .addComponent(lblAbsentCount)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pnlPresentCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlPresentCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 223)));
        pnlPresentCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblAttendanceCount.setFont(new java.awt.Font("Arial", 1, 26)); // NOI18N
        lblAttendanceCount.setForeground(new java.awt.Color(25, 107, 69));
        lblAttendanceCount.setText("0");

        lblPresentTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblPresentTitle.setForeground(new java.awt.Color(82, 102, 90));
        lblPresentTitle.setText("Present");

        javax.swing.GroupLayout pnlPresentCardLayout = new javax.swing.GroupLayout(pnlPresentCard);
        pnlPresentCard.setLayout(pnlPresentCardLayout);
        pnlPresentCardLayout.setHorizontalGroup(
            pnlPresentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPresentCardLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlPresentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPresentTitle)
                    .addComponent(lblAttendanceCount))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        pnlPresentCardLayout.setVerticalGroup(
            pnlPresentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPresentCardLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblPresentTitle)
                .addGap(18, 18, 18)
                .addComponent(lblAttendanceCount)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pnlNotMarkedCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlNotMarkedCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 223)));
        pnlNotMarkedCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblNotMarkedCount.setFont(new java.awt.Font("Arial", 1, 26)); // NOI18N
        lblNotMarkedCount.setForeground(new java.awt.Color(184, 134, 11));
        lblNotMarkedCount.setText("0");

        lblNotMarkedTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNotMarkedTitle.setForeground(new java.awt.Color(82, 102, 90));
        lblNotMarkedTitle.setText("Not Marked");

        javax.swing.GroupLayout pnlNotMarkedCardLayout = new javax.swing.GroupLayout(pnlNotMarkedCard);
        pnlNotMarkedCard.setLayout(pnlNotMarkedCardLayout);
        pnlNotMarkedCardLayout.setHorizontalGroup(
            pnlNotMarkedCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNotMarkedCardLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlNotMarkedCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNotMarkedCount)
                    .addComponent(lblNotMarkedTitle))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        pnlNotMarkedCardLayout.setVerticalGroup(
            pnlNotMarkedCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNotMarkedCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblNotMarkedTitle)
                .addGap(18, 18, 18)
                .addComponent(lblNotMarkedCount)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        btnMarkAbsent.setBackground(new java.awt.Color(181, 71, 71));
        btnMarkAbsent.setForeground(new java.awt.Color(255, 255, 255));
        btnMarkAbsent.setText("Mark Absent");
        btnMarkAbsent.setFocusable(false);
        btnMarkAbsent.addActionListener(this::btnMarkAbsentActionPerformed);

        btnSearchStudent.setForeground(new java.awt.Color(0, 102, 0));
        btnSearchStudent.setText("Search");
        btnSearchStudent.addActionListener(this::btnSearchStudentActionPerformed);

        btnClearSearch.setForeground(new java.awt.Color(0, 102, 0));
        btnClearSearch.setText("Clear");
        btnClearSearch.addActionListener(this::btnClearSearchActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSubtitle12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSubtitleEventAttendance)
                        .addGap(435, 1146, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSubtitle2)
                                    .addComponent(lblSubtitle1))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnMarkPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnMarkAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtSearchStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSearchStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlAbsentCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlRegisteredCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlPresentCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlNotMarkedCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(436, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(394, 394, 394)
                .addComponent(lblTitle)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblSubtitleEventAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(lblSubtitle12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlRegisteredCard, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlPresentCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlAbsentCard, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlNotMarkedCard, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMarkPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMarkAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEventsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEventsItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            loadAttendance();
        }
    }//GEN-LAST:event_cmbEventsItemStateChanged

    private void txtSearchStudentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchStudentIDActionPerformed
        btnMarkPresent.doClick();
    }//GEN-LAST:event_txtSearchStudentIDActionPerformed

    private void btnMarkPresentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkPresentActionPerformed
        EventItem selectedEvent =
            (EventItem) cmbEvents.getSelectedItem();

        String studentID = txtSearchStudentID.getText().trim();

        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(
                    this, "Please select an event.");
            return;
        }

        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter the Student ID.");
            txtSearchStudentID.requestFocus();
            return;
        }

        String findRegistration =
                "SELECT r.id FROM tbl_registrations r "
                + "JOIN tbl_users u ON r.user_id = u.id "
                + "WHERE u.student_id = ? AND r.event_id = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Unable to connect to the database.");
                return;
            }

            int registrationID = -1;

            try (PreparedStatement pst =
                    conn.prepareStatement(findRegistration)) {

                pst.setString(1, studentID);
                pst.setInt(2, selectedEvent.getId());

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        registrationID = rs.getInt("id");
                    }
                }
            }

            if (registrationID == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "This student is not registered for the selected event.");
                return;
            }

            boolean recordExists = false;

            String checkAttendance =
                    "SELECT id FROM tbl_attendance "
                    + "WHERE registration_id = ?";

            try (PreparedStatement pst =
                    conn.prepareStatement(checkAttendance)) {

                pst.setInt(1, registrationID);

                try (ResultSet rs = pst.executeQuery()) {
                    recordExists = rs.next();
                }
            }

            if (recordExists) {

                String updateAttendance =
                        "UPDATE tbl_attendance "
                        + "SET attendance_status = 'Present', "
                        + "attendance_date = CURRENT_TIMESTAMP "
                        + "WHERE registration_id = ?";

                try (PreparedStatement pst =
                        conn.prepareStatement(updateAttendance)) {

                    pst.setInt(1, registrationID);
                    pst.executeUpdate();
                }

            } else {

                String insertAttendance =
                        "INSERT INTO tbl_attendance "
                        + "(registration_id, attendance_status, attendance_date) "
                        + "VALUES (?, 'Present', CURRENT_TIMESTAMP)";

                try (PreparedStatement pst =
                        conn.prepareStatement(insertAttendance)) {

                    pst.setInt(1, registrationID);
                    pst.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(
                    this, "Student marked Present successfully!");

            txtSearchStudentID.setText("");
            txtSearchStudentID.requestFocus();

            loadAttendance();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error recording attendance: " + e.getMessage());
        }
    }//GEN-LAST:event_btnMarkPresentActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadEvents();
        loadAttendance();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void cmbEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEventsActionPerformed
    }//GEN-LAST:event_cmbEventsActionPerformed

    private void btnMarkAbsentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkAbsentActionPerformed
    
        EventItem selectedEvent =
                (EventItem) cmbEvents.getSelectedItem();

        String studentID = txtSearchStudentID.getText().trim();

        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(
                    this, "Please select an event.");
            return;
        }

        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter a Student ID.");
            txtSearchStudentID.requestFocus();
            return;
        }

        String findRegistrationSQL =
                "SELECT r.id "
                + "FROM tbl_registrations r "
                + "JOIN tbl_users u ON r.user_id = u.id "
                + "WHERE u.student_id = ? AND r.event_id = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Unable to connect to the database.");
                return;
            }

            conn.setAutoCommit(false);

            try {
                int registrationID = -1;

                try (PreparedStatement pst =
                        conn.prepareStatement(findRegistrationSQL)) {

                    pst.setString(1, studentID);
                    pst.setInt(2, selectedEvent.getId());

                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            registrationID = rs.getInt("id");
                        }
                    }
                }

                if (registrationID == -1) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(
                            this,
                            "This student is not registered for the selected event.");
                    return;
                }

                boolean recordExists = false;

                String checkSQL =
                        "SELECT id FROM tbl_attendance "
                        + "WHERE registration_id = ?";

                try (PreparedStatement pst =
                        conn.prepareStatement(checkSQL)) {

                    pst.setInt(1, registrationID);

                    try (ResultSet rs = pst.executeQuery()) {
                        recordExists = rs.next();
                    }
                }

                if (recordExists) {

                    String updateSQL =
                            "UPDATE tbl_attendance "
                            + "SET attendance_status = 'Absent', "
                            + "attendance_date = CURRENT_TIMESTAMP "
                            + "WHERE registration_id = ?";

                    try (PreparedStatement pst =
                            conn.prepareStatement(updateSQL)) {

                        pst.setInt(1, registrationID);
                        pst.executeUpdate();
                    }

                } else {

                    String insertSQL =
                            "INSERT INTO tbl_attendance "
                            + "(registration_id, attendance_status, attendance_date) "
                            + "VALUES (?, 'Absent', CURRENT_TIMESTAMP)";

                    try (PreparedStatement pst =
                            conn.prepareStatement(insertSQL)) {

                        pst.setInt(1, registrationID);
                        pst.executeUpdate();
                    }
                }

                conn.commit();

                JOptionPane.showMessageDialog(
                        this, "Attendance marked as Absent.");

                txtSearchStudentID.setText("");
                loadAttendance();
                txtSearchStudentID.requestFocus();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error recording attendance: " + e.getMessage());
        }

    }//GEN-LAST:event_btnMarkAbsentActionPerformed

    private void btnSearchStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchStudentActionPerformed
        String searchID = txtSearchStudentID.getText().trim();

        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter a Student ID.");
            txtSearchStudentID.requestFocus();
            return;
        }

        javax.swing.table.TableRowSorter<DefaultTableModel> sorter =
                new javax.swing.table.TableRowSorter<>(tableModel);

        tblAttendance.setRowSorter(sorter);

        sorter.setRowFilter(
                javax.swing.RowFilter.regexFilter(
                        "(?i)^" + java.util.regex.Pattern.quote(searchID) + "$",
                        0
                )
        );

        if (tblAttendance.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No participant found with Student ID: " + searchID);
        }
    }//GEN-LAST:event_btnSearchStudentActionPerformed

    private void btnClearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchActionPerformed
        txtSearchStudentID.setText("");
        tblAttendance.setRowSorter(null);
        txtSearchStudentID.requestFocus();
    }//GEN-LAST:event_btnClearSearchActionPerformed

    
    private void styleAttendanceTable() {
        java.awt.Color green = new java.awt.Color(8, 107, 58);
        java.awt.Color lightGreen = new java.awt.Color(198, 230, 207);
        java.awt.Color selectedGreen = new java.awt.Color(8, 70, 38);
        java.awt.Color gridColor = new java.awt.Color(220, 230, 223);

        // Table body
        tblAttendance.setBackground(java.awt.Color.WHITE);
        tblAttendance.setForeground(new java.awt.Color(51, 65, 85));
        tblAttendance.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        tblAttendance.setRowHeight(30);
        tblAttendance.setGridColor(gridColor);
        tblAttendance.setShowGrid(true);
        tblAttendance.setFillsViewportHeight(true);
        tblAttendance.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        // Selected row
        tblAttendance.setSelectionBackground(lightGreen);
        tblAttendance.setSelectionForeground(selectedGreen);

        // Green table header
        javax.swing.table.JTableHeader header =
            tblAttendance.getTableHeader();

        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                javax.swing.JLabel label = (javax.swing.JLabel)
                    super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setBackground(green);
                label.setForeground(java.awt.Color.WHITE);
                label.setFont(new java.awt.Font(
                    "Arial", java.awt.Font.BOLD, 13));
                label.setHorizontalAlignment(
                    javax.swing.SwingConstants.CENTER);
                label.setOpaque(true);

                return label;
            }
        });

        header.setPreferredSize(new java.awt.Dimension(0, 35));
        header.setReorderingAllowed(false);

        // Scroll pane
        jScrollPane1.setBorder(
            javax.swing.BorderFactory.createLineBorder(gridColor)
        );
        jScrollPane1.getViewport().setBackground(
            new java.awt.Color(245, 249, 246)
        );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnMarkAbsent;
    private javax.swing.JButton btnMarkPresent;
    private javax.swing.JButton btnSearchStudent;
    private javax.swing.JComboBox<EventItem> cmbEvents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAbsentCount;
    private javax.swing.JLabel lblAbsentTitle;
    private javax.swing.JLabel lblAttendanceCount;
    private javax.swing.JLabel lblNotMarkedCount;
    private javax.swing.JLabel lblNotMarkedTitle;
    private javax.swing.JLabel lblPresentTitle;
    private javax.swing.JLabel lblRegisteredCount;
    private javax.swing.JLabel lblRegisteredTitle;
    private javax.swing.JLabel lblSubtitle1;
    private javax.swing.JLabel lblSubtitle12;
    private javax.swing.JLabel lblSubtitle2;
    private javax.swing.JLabel lblSubtitleEventAttendance;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlAbsentCard;
    private javax.swing.JPanel pnlNotMarkedCard;
    private javax.swing.JPanel pnlPresentCard;
    private javax.swing.JPanel pnlRegisteredCard;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable tblAttendance;
    private javax.swing.JTextField txtSearchStudentID;
    // End of variables declaration//GEN-END:variables
}
