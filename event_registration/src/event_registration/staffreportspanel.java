package event_registration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class staffreportspanel extends javax.swing.JPanel {


    public staffreportspanel() {
        initComponents();
        setupReportTable();
        styleReportPanel();
        loadEvents();
    }

    
    private void setupReportTable() {
        javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(
                new Object[]{
                    "Student ID",
                    "Student Name",
                    "Registration Date",
                    "Registration Status",
                    "Attendance Status",
                    "Attendance Date"
                }, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

        tblReport.setModel(model);

        tblReport.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        tblReport.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );

        tblReport.setRowHeight(32);
        tblReport.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)
        );
        tblReport.setBackground(java.awt.Color.WHITE);
        tblReport.setForeground(new java.awt.Color(25, 75, 53));

        tblReport.getTableHeader().setOpaque(true);
        tblReport.getTableHeader().setBackground(
            new java.awt.Color(18, 107, 69)
        );
        tblReport.getTableHeader().setForeground(
            java.awt.Color.WHITE
        );
        tblReport.getTableHeader().setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );

        tblReport.getTableHeader().setDefaultRenderer(
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
                        (javax.swing.JLabel)
                        super.getTableCellRendererComponent(
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

        tblReport.setSelectionBackground(
            new java.awt.Color(240, 215, 143)
        );
        tblReport.setSelectionForeground(
            new java.awt.Color(25, 75, 53)
        );

        tblReport.setGridColor(new java.awt.Color(220, 225, 220));
        tblReport.setShowGrid(true);
        tblReport.setFillsViewportHeight(true);

        jScrollPane1.setBorder(
            javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(220, 225, 220)
            )
        );
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
    }

    
    private void styleReportPanel() {
        setBackground(new java.awt.Color(244, 247, 244));

        lblTitle.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 22)
        );
        lblTitle.setForeground(new java.awt.Color(18, 107, 69));

        java.awt.Font labelFont =
            new java.awt.Font("Arial", java.awt.Font.BOLD, 13);

        lblEvent.setFont(labelFont);
        lblFromDate.setFont(labelFont);
        lblToDate.setFont(labelFont);

        java.awt.Font controlFont =
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 13);

        cmbEvents.setFont(controlFont);
        dateFrom.getDateEditor().getUiComponent().setFont(controlFont);
        dateTo.getDateEditor().getUiComponent().setFont(controlFont);

        styleReportButton(btnGenerateReport, true);
        styleReportButton(btnExportCSV, false);

        styleReportCard(pnlRegisteredCard);
        styleReportCard(pnlPresentCard);
        styleReportCard(pnlAbsentCard);
        styleReportCard(pnlAttendanceRateCard);

        lblRegisteredCount.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 26)
        );
        lblAttendanceCount.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 26)
        );
        lblAbsentCount.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 26)
        );
        lblAttendanceRate.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 26)
        );

        java.awt.Font cardTitleFont =
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 13);

        lblRegisteredTitle.setFont(cardTitleFont);
        lblPresentTitle.setFont(cardTitleFont);
        lblAbsentTitle.setFont(cardTitleFont);
        lblAttendanceRateTitle.setFont(cardTitleFont);
    }

    private void styleReportButton(
            javax.swing.JButton button, boolean primary) {

        button.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 13)
        );
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(
            new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );

        if (primary) {
            button.setBackground(new java.awt.Color(18, 107, 69));
            button.setForeground(java.awt.Color.WHITE);
            button.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                    8, 16, 8, 16
                )
            );
        } else {
            button.setBackground(new java.awt.Color(240, 215, 143));
            button.setForeground(new java.awt.Color(25, 75, 53));
            button.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                    new java.awt.Color(220, 200, 130)
                )
            );
        }
    }

    private void styleReportCard(javax.swing.JPanel card) {
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                    new java.awt.Color(220, 225, 220)
                ),
                javax.swing.BorderFactory.createEmptyBorder(
                    5, 5, 5, 5
                )
            )
        );
    }

    private void loadEvents() {
        cmbEvents.removeAllItems();

        String sql = "SELECT id, event_name "
                   + "FROM tbl_events "
                   + "ORDER BY event_date DESC";

        try (
            java.sql.Connection conn = DBConnection.connect();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                cmbEvents.addItem(
                    new EventItem(
                        rs.getInt("id"),
                        rs.getString("event_name")
                    )
                );
            }

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Error loading events: " + e.getMessage(),
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    

    private void generateReport() {

        EventItem selectedEvent =
                (EventItem) cmbEvents.getSelectedItem();

        java.util.Date fromDate = dateFrom.getDate();
        java.util.Date toDate = dateTo.getDate();

        // Validate selected event
        if (selectedEvent == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select an event.",
                    "Missing Event",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Validate dates
        if (fromDate == null || toDate == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select both the From Date and To Date.",
                    "Missing Date",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Validate date range
        if (fromDate.after(toDate)) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "From Date cannot be later than To Date.",
                    "Invalid Date Range",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String sql =
                "SELECT "
                + "u.student_id AS student_id, "
                + "u.full_name AS student_name, "
                + "r.registration_date AS registration_date, "
                + "r.status AS registration_status, "
                + "COALESCE(a.attendance_status, 'Not Marked') "
                + "AS attendance_status, "
                + "a.attendance_date AS attendance_date "
                + "FROM tbl_registrations r "
                + "JOIN tbl_users u ON u.id = r.user_id "
                + "LEFT JOIN tbl_attendance a "
                + "ON a.registration_id = r.id "
                + "WHERE r.event_id = ? "
                + "AND DATE(r.registration_date) BETWEEN ? AND ? "
                + "ORDER BY u.full_name ASC";

        try (
            java.sql.Connection conn = DBConnection.connect();
            java.sql.PreparedStatement pst =
                    conn.prepareStatement(sql)
        ) {
            pst.setInt(1, selectedEvent.getId());

            pst.setDate(
                    2, new java.sql.Date(fromDate.getTime())
            );

            pst.setDate(
                    3, new java.sql.Date(toDate.getTime())
            );

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                javax.swing.table.DefaultTableModel model =
                    (javax.swing.table.DefaultTableModel)
                            tblReport.getModel();

            model.setRowCount(0);

            int registered = 0;
            int present = 0;
            int absent = 0;

            while (rs.next()) {

                registered++;

                String studentId = rs.getString("student_id");
                String studentName = rs.getString("student_name");

                java.sql.Date registrationDate =
                        rs.getDate("registration_date");

                String registrationStatus =
                        rs.getString("registration_status");

                String attendanceStatus =
                        rs.getString("attendance_status");

                java.sql.Date attendanceDate =
                        rs.getDate("attendance_date");

                if ("Present".equalsIgnoreCase(attendanceStatus)) {
                    present++;
                } else if ("Absent".equalsIgnoreCase(attendanceStatus)) {
                    absent++;
                }

                model.addRow(new Object[]{
                    studentId,
                    studentName,
                    registrationDate,
                    registrationStatus,
                    attendanceStatus,
                    attendanceDate
                });
            }


            int markedAttendance = present + absent;

            double attendanceRate = 0.0;

            if (markedAttendance > 0) {
                attendanceRate =
                    (present * 100.0) / markedAttendance;
            }

            lblRegisteredCount.setText(String.valueOf(registered));
            lblAttendanceCount.setText(String.valueOf(present));
            lblAbsentCount.setText(String.valueOf(absent));

            lblAttendanceRate.setText(
                    String.format("%.1f%%", attendanceRate)
            );
         }

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate report: " + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
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
    
    private void exportReportToCSV() {

        if (tblReport.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "There is no report data to export. "
                    + "Please generate a report first.",
                    "No Report Data",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Attendance Report");
        fileChooser.setSelectedFile(
                new File("attendance_report.csv")
        );

        int result = fileChooser.showSaveDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        // Prevent accidental overwriting
        if (file.exists()) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "This file already exists. Replace it?",
                    "Confirm Overwrite",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        TableModel model = tblReport.getModel();

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(file))) {

            // Write column headers
            for (int col = 0; col < model.getColumnCount(); col++) {
                if (col > 0) {
                    writer.write(",");
                }

                writer.write(csvEscape(
                        model.getColumnName(col)
                ));
            }

            writer.newLine();

            // Write table rows
            for (int row = 0; row < model.getRowCount(); row++) {

                for (int col = 0; col < model.getColumnCount(); col++) {
                    if (col > 0) {
                        writer.write(",");
                    }

                    Object value = model.getValueAt(row, col);

                    writer.write(csvEscape(
                            value == null ? "" : value.toString()
                    ));
                }

                writer.newLine();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Report exported successfully to:\n"
                    + file.getAbsolutePath(),
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to export report: " + e.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String csvEscape(String value) {
        if (value == null) {
            return "";
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblEvent = new javax.swing.JLabel();
        cmbEvents = new javax.swing.JComboBox<>();
        lblFromDate = new javax.swing.JLabel();
        dateTo = new com.toedter.calendar.JDateChooser();
        lblToDate = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        btnGenerateReport = new javax.swing.JButton();
        pnlRegisteredCard = new javax.swing.JPanel();
        lblRegisteredCount = new javax.swing.JLabel();
        lblRegisteredTitle = new javax.swing.JLabel();
        pnlPresentCard = new javax.swing.JPanel();
        lblAttendanceCount = new javax.swing.JLabel();
        lblPresentTitle = new javax.swing.JLabel();
        pnlAbsentCard = new javax.swing.JPanel();
        lblAbsentCount = new javax.swing.JLabel();
        lblAbsentTitle = new javax.swing.JLabel();
        pnlAttendanceRateCard = new javax.swing.JPanel();
        lblAttendanceRate = new javax.swing.JLabel();
        lblAttendanceRateTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReport = new javax.swing.JTable();
        btnExportCSV = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTitle.setText("Attendance and Event Reports");

        lblEvent.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEvent.setText("Event:");

        cmbEvents.addItemListener(this::cmbEventsItemStateChanged);
        cmbEvents.addActionListener(this::cmbEventsActionPerformed);

        lblFromDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblFromDate.setText("From Date:");

        lblToDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblToDate.setText("To Date:");

        btnGenerateReport.setText("Generate Report");
        btnGenerateReport.addActionListener(this::btnGenerateReportActionPerformed);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegisteredCardLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(lblRegisteredTitle)
                .addGap(35, 35, 35))
            .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(lblRegisteredCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(pnlPresentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPresentCardLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(lblPresentTitle))
                    .addGroup(pnlPresentCardLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(lblAttendanceCount)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        pnlPresentCardLayout.setVerticalGroup(
            pnlPresentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPresentCardLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblPresentTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAttendanceCount)
                .addContainerGap(35, Short.MAX_VALUE))
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
                .addGroup(pnlAbsentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAbsentCardLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(lblAbsentTitle))
                    .addGroup(pnlAbsentCardLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(lblAbsentCount)))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        pnlAbsentCardLayout.setVerticalGroup(
            pnlAbsentCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAbsentCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblAbsentTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAbsentCount)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pnlAttendanceRateCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlAttendanceRateCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 229, 223)));
        pnlAttendanceRateCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblAttendanceRate.setFont(new java.awt.Font("Arial", 1, 26)); // NOI18N
        lblAttendanceRate.setForeground(new java.awt.Color(184, 134, 11));
        lblAttendanceRate.setText("0%");

        lblAttendanceRateTitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblAttendanceRateTitle.setForeground(new java.awt.Color(82, 102, 90));
        lblAttendanceRateTitle.setText("Attendance Rate");

        javax.swing.GroupLayout pnlAttendanceRateCardLayout = new javax.swing.GroupLayout(pnlAttendanceRateCard);
        pnlAttendanceRateCard.setLayout(pnlAttendanceRateCardLayout);
        pnlAttendanceRateCardLayout.setHorizontalGroup(
            pnlAttendanceRateCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAttendanceRateCardLayout.createSequentialGroup()
                .addGroup(pnlAttendanceRateCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAttendanceRateCardLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(lblAttendanceRateTitle))
                    .addGroup(pnlAttendanceRateCardLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(lblAttendanceRate)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        pnlAttendanceRateCardLayout.setVerticalGroup(
            pnlAttendanceRateCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAttendanceRateCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblAttendanceRateTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAttendanceRate)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tblReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Registration Date", "Registration Status", "Attendance Status", "Attendance Date"
            }
        ));
        jScrollPane1.setViewportView(tblReport);

        btnExportCSV.setText("Export CSV");
        btnExportCSV.addActionListener(this::btnExportCSVActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(lblEvent)
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFromDate)
                                .addGap(24, 24, 24)
                                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblToDate)
                                .addGap(43, 43, 43)
                                .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbEvents, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(480, 480, 480)
                        .addComponent(btnGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(470, 470, 470)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(pnlAbsentCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlRegisteredCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlPresentCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlAttendanceRateCard, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(211, 211, 211)
                                .addComponent(btnExportCSV, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(405, 405, 405))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlRegisteredCard, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlPresentCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlAbsentCard, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlAttendanceRateCard, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExportCSV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEventsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEventsItemStateChanged
    }//GEN-LAST:event_cmbEventsItemStateChanged

    private void cmbEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEventsActionPerformed

    }//GEN-LAST:event_cmbEventsActionPerformed

    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
        generateReport();
    }//GEN-LAST:event_btnGenerateReportActionPerformed

    private void btnExportCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportCSVActionPerformed
        exportReportToCSV();
    }//GEN-LAST:event_btnExportCSVActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportCSV;
    private javax.swing.JButton btnGenerateReport;
    private javax.swing.JComboBox<EventItem> cmbEvents;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAbsentCount;
    private javax.swing.JLabel lblAbsentTitle;
    private javax.swing.JLabel lblAttendanceCount;
    private javax.swing.JLabel lblAttendanceRate;
    private javax.swing.JLabel lblAttendanceRateTitle;
    private javax.swing.JLabel lblEvent;
    private javax.swing.JLabel lblFromDate;
    private javax.swing.JLabel lblPresentTitle;
    private javax.swing.JLabel lblRegisteredCount;
    private javax.swing.JLabel lblRegisteredTitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblToDate;
    private javax.swing.JPanel pnlAbsentCard;
    private javax.swing.JPanel pnlAttendanceRateCard;
    private javax.swing.JPanel pnlPresentCard;
    private javax.swing.JPanel pnlRegisteredCard;
    private javax.swing.JTable tblReport;
    // End of variables declaration//GEN-END:variables
}
