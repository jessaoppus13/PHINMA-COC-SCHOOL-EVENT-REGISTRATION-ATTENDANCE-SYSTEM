package event_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class staffeventspanel extends javax.swing.JPanel {

private DefaultTableModel tableModel;
private boolean showingArchivedEvents = false;

    public staffeventspanel() {
        initComponents();
        setupEventManagement();
        styleEventManagement();
    }

    private void setupEventManagement() {
        String[] columns = {
            "Event ID",
            "Event Name",
            "Description",
            "Event Date",
            "Start Time",
            "End Time",
            "Venue",
            "Participant Limit",
            "Registration Deadline",
            "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblEvents.setModel(tableModel);
        tblEvents.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
        tblEvents.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
        );

        for (int i = 0; i < tblEvents.getColumnCount(); i++) {
            javax.swing.table.TableColumn column =
                tblEvents.getColumnModel().getColumn(i);

            javax.swing.table.TableCellRenderer renderer =
                tblEvents.getTableHeader().getDefaultRenderer();

            java.awt.Component component =
                renderer.getTableCellRendererComponent(
                    tblEvents,
                    column.getHeaderValue(),
                    false,
                    false,
                    -1,
                    i
                );

            int headerWidth = component.getPreferredSize().width + 25;

            column.setPreferredWidth(headerWidth);
        }
        
        tblEvents.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(150);  // Participant Limit

        tblEvents.getColumnModel()
                .getColumn(8)
                .setPreferredWidth(180);  // Registration Deadline

        
        tblEvents.setRowHeight(32);
        tblEvents.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));

        tblEvents.getTableHeader().setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );

        tblEvents.getTableHeader().setOpaque(true);
        tblEvents.getTableHeader().setBackground(
            new java.awt.Color(18, 107, 69)
        );
        tblEvents.getTableHeader().setForeground(
            java.awt.Color.WHITE
        );

        tblEvents.getTableHeader().setDefaultRenderer(
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
                    label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                    label.setOpaque(true);

                    return label;
                }
            }
        );

        tblEvents.setSelectionBackground(new java.awt.Color(240, 215, 143));
        tblEvents.setSelectionForeground(new java.awt.Color(25, 75, 53));

        tblEvents.setGridColor(new java.awt.Color(220, 225, 220));
        tblEvents.setShowGrid(true);
        tblEvents.setFillsViewportHeight(true);

        loadEvents();
    }
    
    
    private void styleEventManagement() {
        java.awt.Color green = new java.awt.Color(0, 83, 32);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);
        java.awt.Color white = java.awt.Color.WHITE;

        // Panel background
        setBackground(new java.awt.Color(244, 247, 244));

        // Main title
        lblTitle.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 24)
        );
        lblTitle.setForeground(green);
        lblTitle.setHorizontalAlignment(
            javax.swing.SwingConstants.LEFT
        );

        // Subtitle
        lblSubtitle.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 16)
        );
        lblSubtitle.setForeground(darkGreen);

        // Primary action buttons
        styleButton(btnAdd, green, white);
        styleButton(btnEdit, green, white);
        styleButton(btnRefresh, green, white);
        styleButton(btnChangeStatus, green, white);

        // Other actions
        styleButton(btnArchiveEvent, gold, darkGreen);
        styleButton(btnArchivedEvents, gold, darkGreen);
        styleButton(btnRestoreEvent, gold, darkGreen);
        styleButton(btnDelete, new java.awt.Color(180, 55, 55), white);

        // Table and scroll pane
        scrollPaneEvents.setBorder(
            javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(220, 230, 223)
            )
        );
        scrollPaneEvents.getViewport().setBackground(white);
    }

    private void styleButton(
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

    private void loadEvents() {
        tableModel.setRowCount(0);

        String sql = "SELECT id, event_name, description, event_date, "
                + "start_time, end_time, venue, participant_limit, "
                + "registration_deadline, status "
                + "FROM tbl_events "
                + "WHERE is_archived = ? "
                + "ORDER BY event_date ASC";

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Unable to connect to the database."
                );
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, showingArchivedEvents ? 1 : 0);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        tableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("event_name"),
                            rs.getString("description"),
                            rs.getDate("event_date"),
                            formatTimeForDisplay(
                                rs.getTime("start_time") == null
                                    ? null
                                    : rs.getTime("start_time").toString()
                            ),
                            formatTimeForDisplay(
                                rs.getTime("end_time") == null
                                    ? null
                                    : rs.getTime("end_time").toString()
                            ),
                            rs.getString("venue"),
                            rs.getObject("participant_limit"),
                            rs.getDate("registration_deadline"),
                            rs.getString("status")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this, "Failed to load events: " + ex.getMessage()
            );
        }

        // Enable actions appropriate to the current view.
        btnArchiveEvent.setEnabled(!showingArchivedEvents);
        btnRestoreEvent.setEnabled(showingArchivedEvents);
    }

       
    private void addEvent() {
        // Create the fields only once so their values remain intact
        // whenever validation fails.
        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JDateChooser eventDateChooser = new JDateChooser();

        javax.swing.JComboBox<String> startTimeField =
            new javax.swing.JComboBox<>(createTimeOptions());

        javax.swing.JComboBox<String> endTimeField =
            new javax.swing.JComboBox<>(createTimeOptions());

        JTextField venueField = new JTextField(20);
        JTextField participantLimitField = new JTextField(20);
        JDateChooser deadlineChooser = new JDateChooser();

        eventDateChooser.setDateFormatString("yyyy-MM-dd");
        deadlineChooser.setDateFormatString("yyyy-MM-dd");

        Object[] fields = {
            "Event Name:", nameField,
            "Description:", descriptionField,
            "Event Date:", eventDateChooser,
            "Start Time:", startTimeField,
            "End Time:", endTimeField,
            "Venue:", venueField,
            "Participant Limit:", participantLimitField,
            "Registration Deadline:", deadlineChooser
        };

        // Repeat the dialog until the event is saved or the user cancels.
        while (true) {
            int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add New Event",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // Cancel button or X button: discard the unsaved data.
            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();

            String startText = startTimeField.getSelectedItem() == null
                ? ""
                : startTimeField.getSelectedItem().toString();

            String endText = endTimeField.getSelectedItem() == null
                ? ""
                : endTimeField.getSelectedItem().toString();

            String venue = venueField.getText().trim();
            String limitText = participantLimitField.getText().trim();

            java.util.Date selectedEventDate = eventDateChooser.getDate();
            java.util.Date selectedDeadline = deadlineChooser.getDate();

            // Validate required fields.
            if (name.isEmpty()
                    || venue.isEmpty()
                    || selectedEventDate == null
                    || selectedDeadline == null
                    || startText.isEmpty()
                    || endText.isEmpty()
                    || limitText.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Please complete all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
                );

                continue;
            }

            try {
                java.sql.Date eventDate = new java.sql.Date(
                    selectedEventDate.getTime()
                );

                java.sql.Date registrationDeadline = new java.sql.Date(
                    selectedDeadline.getTime()
                );

                java.sql.Time startTime = convertToSqlTime(startText);
                java.sql.Time endTime = convertToSqlTime(endText);

                int participantLimit = Integer.parseInt(limitText);

                // Validate participant limit.
                if (participantLimit <= 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Participant Limit must be greater than zero.",
                        "Invalid Participant Limit",
                        JOptionPane.WARNING_MESSAGE
                    );

                    continue;
                }

                // Validate registration deadline.
                if (registrationDeadline.after(eventDate)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Registration Deadline cannot be after the Event Date.",
                        "Invalid Registration Deadline",
                        JOptionPane.WARNING_MESSAGE
                    );

                    continue;
                }

                // Validate event times.
                if (!endTime.after(startTime)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "End Time must be later than Start Time.",
                        "Invalid Event Time",
                        JOptionPane.WARNING_MESSAGE
                    );

                    continue;
                }

                String sql = "INSERT INTO tbl_events "
                        + "(event_name, description, event_date, start_time, "
                        + "end_time, venue, participant_limit, "
                        + "registration_deadline, status) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Open')";

                try (Connection conn = DBConnection.connect()) {
                    if (conn == null) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Unable to connect to the database.",
                            "Connection Error",
                            JOptionPane.ERROR_MESSAGE
                        );

                        continue;
                    }

                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, name);
                        ps.setString(2, description);
                        ps.setDate(3, eventDate);
                        ps.setTime(4, startTime);
                        ps.setTime(5, endTime);
                        ps.setString(6, venue);
                        ps.setInt(7, participantLimit);
                        ps.setDate(8, registrationDeadline);

                        ps.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(
                    this,
                    "Event added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                loadEvents();
                return;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Participant Limit must be a valid whole number.",
                    "Invalid Participant Limit",
                    JOptionPane.WARNING_MESSAGE
                );

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid time. Please select a valid start and end time.",
                    "Invalid Event Time",
                    JOptionPane.WARNING_MESSAGE
                );

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Failed to add event: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    private String normalizeTime(String timeText) {
        if (timeText.matches("\\d{1,2}:\\d{2}")) {
            return timeText + ":00";
        }

        return timeText;
    }
    

    private void editEvent() {
        int row = tblEvents.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an event to edit."
            );
            return;
        }

        int modelRow = tblEvents.convertRowIndexToModel(row);

        int eventId = Integer.parseInt(
            String.valueOf(tableModel.getValueAt(modelRow, 0))
        );

        String name = String.valueOf(
            tableModel.getValueAt(modelRow, 1)
        );
        String description = String.valueOf(
            tableModel.getValueAt(modelRow, 2)
        );
        String venue = String.valueOf(
            tableModel.getValueAt(modelRow, 6)
        );

        JTextField nameField = new JTextField(name);
        JTextField descriptionField = new JTextField(description);
        JDateChooser eventDateChooser = new JDateChooser();

        javax.swing.JComboBox<String> startTimeField =
            new javax.swing.JComboBox<>(createTimeOptions());

        javax.swing.JComboBox<String> endTimeField =
            new javax.swing.JComboBox<>(createTimeOptions());

        Object startValue = tableModel.getValueAt(modelRow, 4);
        Object endValue = tableModel.getValueAt(modelRow, 5);

        if (startValue != null) {
            startTimeField.setSelectedItem(
                formatTimeForDisplay(startValue.toString())
            );
        }

        if (endValue != null) {
            endTimeField.setSelectedItem(
                formatTimeForDisplay(endValue.toString())
            );
        }

        JTextField venueField = new JTextField(venue);
        JTextField participantLimitField = new JTextField(
            String.valueOf(tableModel.getValueAt(modelRow, 7))
        );
        
        JDateChooser deadlineChooser = new JDateChooser();

        eventDateChooser.setDateFormatString("yyyy-MM-dd");
        deadlineChooser.setDateFormatString("yyyy-MM-dd");

        Object eventDateValue = tableModel.getValueAt(modelRow, 3);
        if (eventDateValue instanceof java.sql.Date) {
            eventDateChooser.setDate(
                new java.util.Date(
                    ((java.sql.Date) eventDateValue).getTime()
                )
            );
        }

        Object deadlineValue = tableModel.getValueAt(modelRow, 8);
        if (deadlineValue instanceof java.sql.Date) {
            deadlineChooser.setDate(
                new java.util.Date(
                    ((java.sql.Date) deadlineValue).getTime()
                )
            );
        }
  
        Object[] fields = {
            "Event Name:", nameField,
            "Description:", descriptionField,
            "Event Date:", eventDateChooser,
            "Start Time (HH:mm):", startTimeField,
            "End Time (HH:mm):", endTimeField,
            "Venue:", venueField,
            "Participant Limit:", participantLimitField,
            "Registration Deadline:", deadlineChooser
        };

        int result = JOptionPane.showConfirmDialog(
            this, fields, "Edit Event",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        name = nameField.getText().trim();
        description = descriptionField.getText().trim();
        
        String startText = (String) startTimeField.getSelectedItem();
        String endText = (String) endTimeField.getSelectedItem();
        
        venue = venueField.getText().trim();
        String limitText = participantLimitField.getText().trim();

        java.util.Date selectedEventDate = eventDateChooser.getDate();
        java.util.Date selectedDeadline = deadlineChooser.getDate();

        if (name.isEmpty() || venue.isEmpty()
                || selectedEventDate == null
                || selectedDeadline == null
                || startText.isEmpty() || endText.isEmpty()
                || limitText.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please complete all required fields."
            );
            return;
        }

        try {
            java.sql.Date eventDate = new java.sql.Date(
                selectedEventDate.getTime()
            );

            java.sql.Date registrationDeadline = new java.sql.Date(
                selectedDeadline.getTime()
            );

            java.sql.Time startTime = convertToSqlTime(startText);
            java.sql.Time endTime = convertToSqlTime(endText);

            int participantLimit = Integer.parseInt(limitText);

            if (participantLimit <= 0) {
                JOptionPane.showMessageDialog(
                    this, "Participant Limit must be greater than zero."
                );
                return;
            }

            if (registrationDeadline.after(eventDate)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Registration Deadline cannot be after the Event Date."
                );
                return;
            }

            if (!endTime.after(startTime)) {
                JOptionPane.showMessageDialog(
                    this, "End Time must be later than Start Time."
                );
                return;
            }

            String sql = "UPDATE tbl_events SET event_name = ?, "
                    + "description = ?, event_date = ?, start_time = ?, "
                    + "end_time = ?, venue = ?, participant_limit = ?, "
                    + "registration_deadline = ? WHERE id = ?";

            try (Connection conn = DBConnection.connect()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(
                        this, "Unable to connect to the database."
                    );
                    return;
                }

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, description);
                    ps.setDate(3, eventDate);
                    ps.setTime(4, startTime);
                    ps.setTime(5, endTime);
                    ps.setString(6, venue);
                    ps.setInt(7, participantLimit);
                    ps.setDate(8, registrationDeadline);
                    ps.setInt(9, eventId);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(
                        this, "Event updated successfully!"
                    );

                    loadEvents();
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Participant Limit must be a valid whole number."
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Invalid time. Enter time like 09:00 or 17:30."
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Failed to update event: " + ex.getMessage()
            );
        }
    }


    private void changeEventStatus() {
        int row = tblEvents.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an event first."
            );
            return;
        }

        int modelRow = tblEvents.convertRowIndexToModel(row);
        int eventId = Integer.parseInt(
            String.valueOf(tableModel.getValueAt(modelRow, 0))
        );

        String currentStatus = String.valueOf(
            tableModel.getValueAt(modelRow, 9)
        );

        String newStatus = currentStatus.equalsIgnoreCase("Open")
                ? "Closed" : "Open";

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Change event status from " + currentStatus
                + " to " + newStatus + "?",
            "Confirm Status Change",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "UPDATE tbl_events SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(
                    this, "Unable to connect to the database."
                );
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newStatus);
                ps.setInt(2, eventId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Event status changed to " + newStatus + ".",
                        "Status Updated",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    loadEvents();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "No event was updated. Please refresh and try again.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Failed to change status: " + ex.getMessage()
            );
        }
    }

    private void deleteEvent() {
        int row = tblEvents.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an event to delete."
            );
            return;
        }

        int modelRow = tblEvents.convertRowIndexToModel(row);

        int eventId = Integer.parseInt(
            String.valueOf(tableModel.getValueAt(modelRow, 0))
        );

        String eventName = String.valueOf(
            tableModel.getValueAt(modelRow, 1)
        );

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete event \"" + eventName + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(
                    this, "Unable to connect to the database."
                );
                return;
            }

            String checkSql =
                "SELECT COUNT(*) FROM tbl_registrations WHERE event_id = ?";

            try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                check.setInt(1, eventId);

                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(
                            this,
                            "This event has student registrations. "
                            + "Close the event instead to preserve records."
                        );
                        return;
                    }
                }
            }

            String deleteSql = "DELETE FROM tbl_events WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, eventId);
                int deleted = ps.executeUpdate();

                if (deleted > 0) {
                    JOptionPane.showMessageDialog(
                        this, "Event deleted successfully!"
                    );
                }

                loadEvents();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this, "Failed to delete event: " + ex.getMessage()
            );
        }
    }
    
    private String[] createTimeOptions() {
        String[] times = new String[48];
        int index = 0;

        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                int displayHour = hour % 12;

                if (displayHour == 0) {
                    displayHour = 12;
                }

                String period = hour < 12 ? "AM" : "PM";

                times[index++] = String.format(
                    "%02d:%02d %s",
                    displayHour,
                    minute,
                    period
                );
            }
        }

        return times;
    }

    private java.sql.Time convertToSqlTime(String timeText) {
        try {
            java.text.SimpleDateFormat format =
                new java.text.SimpleDateFormat("hh:mm a");

            format.setLenient(false);

            java.util.Date parsed = format.parse(timeText);

            return new java.sql.Time(parsed.getTime());

        } catch (java.text.ParseException ex) {
            throw new IllegalArgumentException("Invalid time.");
        }
    }
    
    private String formatTimeForDisplay(String timeText) {
        if (timeText == null || timeText.trim().isEmpty()) {
            return "";
        }

        try {
            java.text.SimpleDateFormat inputFormat =
                new java.text.SimpleDateFormat("HH:mm:ss");

            inputFormat.setLenient(false);

            java.util.Date time = inputFormat.parse(timeText);

            java.text.SimpleDateFormat outputFormat =
                new java.text.SimpleDateFormat("hh:mm a");

            return outputFormat.format(time);

        } catch (java.text.ParseException ex) {
            return timeText;
        }
    }
    
    private void archiveSelectedEvent() {

        int selectedRow = tblEvents.getSelectedRow();

        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select an event to archive.",
                    "No Event Selected",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Convert selected row in case the table uses sorting
        selectedRow = tblEvents.convertRowIndexToModel(selectedRow);

        int eventId = Integer.parseInt(
                tblEvents.getModel()
                        .getValueAt(selectedRow, 0).toString()
        );

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to archive this event?\n"
                + "Its registration and attendance history will be preserved.",
                "Confirm Archive",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "UPDATE tbl_events SET is_archived = 1 WHERE id = ?";

        try (
            java.sql.Connection conn = DBConnection.connect();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setInt(1, eventId);

            int updated = pst.executeUpdate();

            if (updated > 0) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Event archived successfully.",
                        "Success",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                // Reload the event table
                loadEvents();
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "The selected event could not be found.",
                        "Archive Failed",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to archive event: " + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void toggleArchivedEvents() {
        showingArchivedEvents = !showingArchivedEvents;

        if (showingArchivedEvents) {
            btnArchivedEvents.setText("View Active Events");
            lblSubtitle.setText("Archived Events:");
        } else {
            btnArchivedEvents.setText("Archived Events");
            lblSubtitle.setText("Events:");
        }

        loadEvents();
    }
    
    private void restoreSelectedEvent() {
        if (!showingArchivedEvents) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please open Archived Events first.",
                    "Not in Archive View",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int row = tblEvents.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an archived event to restore.",
                    "No Event Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = tblEvents.convertRowIndexToModel(row);

        int eventId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 0).toString()
        );

        String eventName = tableModel.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Restore \"" + eventName + "\"?\n"
                + "The event will return to the active events list.",
                "Confirm Restore",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "UPDATE tbl_events SET is_archived = 0 WHERE id = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Unable to connect to the database."
                );
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, eventId);

                int updated = ps.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Event restored successfully!",
                            "Restore Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    loadEvents();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "The event could not be restored.",
                            "Restore Failed",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to restore event: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnChangeStatus = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        scrollPaneEvents = new javax.swing.JScrollPane();
        tblEvents = new javax.swing.JTable();
        btnArchiveEvent = new javax.swing.JButton();
        btnArchivedEvents = new javax.swing.JButton();
        btnRestoreEvent = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTitle.setText("MANAGE EVENTS");

        lblSubtitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle.setText("Events:");

        btnAdd.setText("Add Event");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnChangeStatus.setText("Change Status");
        btnChangeStatus.addActionListener(this::btnChangeStatusActionPerformed);

        btnEdit.setText("Edit Event");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        btnDelete.setText("Delete Event");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        scrollPaneEvents.setPreferredSize(new java.awt.Dimension(800, 350));

        tblEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Event Name", "Description", "Event Date", "Venue", "Status"
            }
        ));
        tblEvents.setPreferredSize(new java.awt.Dimension(800, 350));
        scrollPaneEvents.setViewportView(tblEvents);

        btnArchiveEvent.setText("Archive Event");
        btnArchiveEvent.addActionListener(this::btnArchiveEventActionPerformed);

        btnArchivedEvents.setText("Archived Events");
        btnArchivedEvents.addActionListener(this::btnArchivedEventsActionPerformed);

        btnRestoreEvent.setText("Restore Event");
        btnRestoreEvent.addActionListener(this::btnRestoreEventActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(lblSubtitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnChangeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnArchiveEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(scrollPaneEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 983, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(btnArchivedEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnRestoreEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(544, 544, 544)
                        .addComponent(lblTitle)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lblSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(scrollPaneEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChangeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnArchiveEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnArchivedEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestoreEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addEvent();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnChangeStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeStatusActionPerformed
        changeEventStatus();
    }//GEN-LAST:event_btnChangeStatusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editEvent();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadEvents();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteEvent();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnArchiveEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveEventActionPerformed
        archiveSelectedEvent();
    }//GEN-LAST:event_btnArchiveEventActionPerformed

    private void btnArchivedEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivedEventsActionPerformed
        toggleArchivedEvents();
    }//GEN-LAST:event_btnArchivedEventsActionPerformed

    private void btnRestoreEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreEventActionPerformed
        restoreSelectedEvent();
    }//GEN-LAST:event_btnRestoreEventActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnArchiveEvent;
    private javax.swing.JButton btnArchivedEvents;
    private javax.swing.JButton btnChangeStatus;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRestoreEvent;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollPaneEvents;
    private javax.swing.JTable tblEvents;
    // End of variables declaration//GEN-END:variables
}
