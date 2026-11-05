package event_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class studenteventspanel extends javax.swing.JPanel {

    private javax.swing.JPanel cardsPanel;
    
    public studenteventspanel() {
        initComponents();
        
        lblTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTitle.setForeground(new java.awt.Color(0, 83, 32));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        // Event cards container
        cardsPanel = new javax.swing.JPanel() {
            @Override
            public java.awt.Dimension getPreferredSize() {
                java.awt.Dimension size =
                        super.getPreferredSize();

                int viewportWidth =
                        jScrollPane1.getViewport().getWidth();

                if (viewportWidth > 0) {
                    size.width = viewportWidth;
                }

                return size;
            }
        };

        cardsPanel.setLayout(
            new java.awt.GridLayout(0, 2, 18, 18)
        );

        cardsPanel.setBackground(
            new java.awt.Color(244, 247, 244)
        );


        // Add padding around the cards
        cardsPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(
                15, 15, 15, 15
            )
        );

        // Display the cards inside the existing scroll pane
        jScrollPane1.setViewportView(cardsPanel);

        txtSearchEvent.setText("");
        txtSearchEvent.setToolTipText("Search event or venue");

        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setToolTipText("Select an event date");
        
        // Main panel background
        setBackground(new java.awt.Color(255, 255, 255));

        // Title and subtitle
        lblTitle.setForeground(new java.awt.Color(8, 107, 58));
        lblSubtitle.setForeground(new java.awt.Color(85, 85, 85));

        // Search and date fields
        txtSearchEvent.setBackground(new java.awt.Color(245, 249, 246));
        txtSearchEvent.setForeground(new java.awt.Color(51, 65, 85));

        // Category dropdown
        cmbCategory.setBackground(java.awt.Color.WHITE);
        cmbCategory.setForeground(new java.awt.Color(8, 107, 58));

        // Search button
        btnSearch.setBackground(new java.awt.Color(0, 83, 32));
        btnSearch.setForeground(java.awt.Color.WHITE);
        btnSearch.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 13)
        );
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setOpaque(true);

        // Refresh button
        refreshButton.setBackground(new java.awt.Color(0, 83, 32));
        refreshButton.setForeground(java.awt.Color.WHITE);
        refreshButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setContentAreaFilled(true);
        refreshButton.setOpaque(true);
        refreshButton.setCursor(
            new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)
        );

        // Table styling
        styleEventsTable();

        // Existing dropdown setup
        cmbCategory.setModel(
            new javax.swing.DefaultComboBoxModel<>(
                new String[] {"All Status", "Open", "Closed"}
            )
        );

        btnSearch.addActionListener(e -> loadEvents());
        txtSearchEvent.addActionListener(e -> loadEvents());

        dateChooser.getDateEditor().addPropertyChangeListener(
            "date", e -> loadEvents()
        );

        cmbCategory.addActionListener(e -> loadEvents());

        loadEvents();
    }


    private void loadEvents() {

        cardsPanel.removeAll();

        String search = txtSearchEvent.getText().trim();

        String selectedStatus =
                cmbCategory.getSelectedItem() == null
                ? "All Status"
                : cmbCategory.getSelectedItem().toString();

        java.util.Date selectedDate = dateChooser.getDate();

        java.sql.Date filterDate = selectedDate == null
                ? null
                : new java.sql.Date(selectedDate.getTime());

        
        StringBuilder sql = new StringBuilder(
                "SELECT id, event_name, description, event_date, "
              + "start_time, end_time, venue, participant_limit, "
              + "registration_deadline, status "
              + "FROM tbl_events "
              + "WHERE is_archived = 0 ");


        if (!search.isEmpty()) {
            sql.append("AND (event_name LIKE ? OR venue LIKE ?) ");
        }

        if (!selectedStatus.equals("All Status")) {
            sql.append("AND status = ? ");
        }

        if (filterDate != null) {
            sql.append("AND event_date = ? ");
        }

        sql.append("ORDER BY event_date ASC");

        boolean found = false;

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Could not connect to the database.");
                return;
            }

            try (PreparedStatement ps =
                    conn.prepareStatement(sql.toString())) {

                int parameterIndex = 1;

                if (!search.isEmpty()) {
                    ps.setString(parameterIndex++, "%" + search + "%");
                    ps.setString(parameterIndex++, "%" + search + "%");
                }

                if (!selectedStatus.equals("All Status")) {
                    ps.setString(parameterIndex++, selectedStatus);
                }

                if (filterDate != null) {
                    ps.setDate(parameterIndex++, filterDate);
                }

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        found = true;

                        int eventId = rs.getInt("id");
                        String eventName = rs.getString("event_name");
                        String description = rs.getString("description");
                        java.sql.Date eventDate = rs.getDate("event_date");
                        java.sql.Time startTime = rs.getTime("start_time");
                        java.sql.Time endTime = rs.getTime("end_time");
                        String venue = rs.getString("venue");
                        Object limitValue = rs.getObject("participant_limit");
                        java.sql.Date deadline =
                                rs.getDate("registration_deadline");
                        String status = rs.getString("status");

                        addEventCard(
                                eventId, eventName, description,
                                eventDate, startTime, endTime, venue,
                                limitValue, deadline, status
                        );
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading events: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }

        if (!found) {
            javax.swing.JLabel emptyLabel = new javax.swing.JLabel(
                    "No events match your search."
            );
            emptyLabel.setFont(
                    new java.awt.Font("Arial", java.awt.Font.PLAIN, 14)
            );
            emptyLabel.setForeground(new java.awt.Color(85, 85, 85));
            emptyLabel.setHorizontalAlignment(
                    javax.swing.SwingConstants.CENTER
            );
            cardsPanel.setLayout(new java.awt.BorderLayout());
            cardsPanel.add(emptyLabel, java.awt.BorderLayout.CENTER);
        } else {
            cardsPanel.setLayout(
        new java.awt.GridLayout(0, 2, 18, 18));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    
    private void addEventCard(
            int eventId,
            String eventName,
            String description,
            java.sql.Date eventDate,
            java.sql.Time startTime,
            java.sql.Time endTime,
            String venue,
            Object limitValue,
            java.sql.Date deadline,
            String status) {

        java.awt.Color green = new java.awt.Color(0, 83, 32);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color lightGreen = new java.awt.Color(244, 247, 244);
        java.awt.Color borderColor = new java.awt.Color(220, 230, 223);

        
        javax.swing.JPanel card = new javax.swing.JPanel();

        card.setLayout(new javax.swing.BoxLayout(
                card, javax.swing.BoxLayout.Y_AXIS));

        card.setBackground(java.awt.Color.WHITE);

        card.setBorder(
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                    borderColor, 1
                ),
                javax.swing.BorderFactory.createEmptyBorder(
                    20, 20, 20, 20
                )
            )
        );

        card.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);


        // Event name
        javax.swing.JLabel nameLabel = new javax.swing.JLabel(
                "<html><div style='width:260px;'>"
                + eventName
                + "</div></html>");

        nameLabel.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 17));

        nameLabel.setForeground(darkGreen);

        nameLabel.setAlignmentX(
                java.awt.Component.LEFT_ALIGNMENT);

        nameLabel.setMaximumSize(
                new java.awt.Dimension(
                        Integer.MAX_VALUE, 60));


        // Event status
        javax.swing.JLabel statusLabel = new javax.swing.JLabel(
                "Status: " + (status == null ? "Unknown" : status));
        statusLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        statusLabel.setForeground(
                "Open".equalsIgnoreCase(status)
                        ? green : new java.awt.Color(170, 70, 50));
        statusLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        String dateText = eventDate == null
                ? "Not specified" : eventDate.toString();

        String timeText = "Not specified";
        if (startTime != null || endTime != null) {
            timeText = (startTime == null
                    ? "Not specified" : formatEventTime(startTime.toString()))
                    + " - "
                    + (endTime == null
                    ? "Not specified" : formatEventTime(endTime.toString()));
        }

        String venueText = venue == null || venue.trim().isEmpty()
                ? "Not specified" : venue;

        String limitText = limitValue == null
                ? "No limit specified" : limitValue.toString();

        javax.swing.JLabel dateLabel = new javax.swing.JLabel(
                "Date: " + dateText);
        javax.swing.JLabel timeLabel = new javax.swing.JLabel(
                "Time: " + timeText);
        javax.swing.JLabel venueLabel = new javax.swing.JLabel(
                "Venue: " + venueText);
        javax.swing.JLabel limitLabel = new javax.swing.JLabel(
                "Participants: " + limitText);

        java.awt.Font infoFont =
                new java.awt.Font("Arial", java.awt.Font.PLAIN, 12);

        for (javax.swing.JLabel label : new javax.swing.JLabel[]{
                dateLabel, timeLabel, venueLabel, limitLabel}) {
            label.setFont(infoFont);
            label.setForeground(new java.awt.Color(70, 80, 75));
            label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        }

        // Buttons
        javax.swing.JButton detailsButton =
                new javax.swing.JButton("View Details");
        javax.swing.JButton registerButton =
                new javax.swing.JButton("Register");
        
        detailsButton.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 12));
        detailsButton.setBackground(green);
        detailsButton.setForeground(java.awt.Color.WHITE);
        detailsButton.setFocusPainted(false);
        detailsButton.setBorderPainted(false);
        detailsButton.setOpaque(true);
        detailsButton.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR));
        detailsButton.setPreferredSize(
                new java.awt.Dimension(120, 36));

        
        registerButton.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 12));

        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setOpaque(true);
        registerButton.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR));
        registerButton.setPreferredSize(
                new java.awt.Dimension(140, 36));

        boolean alreadyRegistered = false;

        if (UserSession.getInstance().isLoggedIn()) {
            alreadyRegistered = isStudentRegistered(
                    eventId,
                    UserSession.getInstance().getUserId());
        }

        if (alreadyRegistered) {
            registerButton.setText("Already Registered");
            registerButton.setBackground(
                    new java.awt.Color(225, 240, 229));
            registerButton.setForeground(darkGreen);
            registerButton.setEnabled(false);
        } else {
            registerButton.setText("Register");
            registerButton.setBackground(gold);
            registerButton.setForeground(darkGreen);
            registerButton.setEnabled(true);
        }

        detailsButton.addActionListener(e ->
                showEventDetails(eventId));

        registerButton.addActionListener(e -> {
            UserSession session = UserSession.getInstance();

            if (!session.isLoggedIn()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Your session has expired. Please log in again.",
                        "Session Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!checkRegistrationEligibility(
                    eventId, session.getUserId())) {
                return;
            }

            showRegistrationConfirmation(
                    eventId,
                    eventName,
                    dateText,
                    venueText);
        });

        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        buttonPanel.setBackground(java.awt.Color.WHITE);
        buttonPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        buttonPanel.add(detailsButton);
        buttonPanel.add(registerButton);

        // Add everything to the card
        card.add(nameLabel);
        card.add(javax.swing.Box.createVerticalStrut(8));
        card.add(statusLabel);
        card.add(javax.swing.Box.createVerticalStrut(12));
        card.add(dateLabel);
        card.add(javax.swing.Box.createVerticalStrut(5));
        card.add(timeLabel);
        card.add(javax.swing.Box.createVerticalStrut(5));
        card.add(venueLabel);
        card.add(javax.swing.Box.createVerticalStrut(5));
        card.add(limitLabel);
        card.add(javax.swing.Box.createVerticalStrut(15));
        card.add(buttonPanel);

        card.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        cardsPanel.add(card);
    }

    
    private void showEventDetails(int eventId) {

        String sql =
                "SELECT event_name, description, event_date, "
              + "start_time, end_time, venue, participant_limit, "
              + "registration_deadline, status "
              + "FROM tbl_events WHERE id = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Could not connect to the database.");
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, eventId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                                this, "Event not found.");
                        return;
                    }

                    String description = rs.getString("description");
                    if (description == null || description.trim().isEmpty()) {
                        description = "No description provided.";
                    }

                    java.sql.Time start = rs.getTime("start_time");
                    java.sql.Time end = rs.getTime("end_time");
                    java.sql.Date date = rs.getDate("event_date");
                    java.sql.Date deadline =
                            rs.getDate("registration_deadline");
                    Object limit = rs.getObject("participant_limit");

                    String timeText =
                            (start == null ? "Not specified"
                                    : formatEventTime(start.toString()))
                            + " - "
                            + (end == null ? "Not specified"
                                    : formatEventTime(end.toString()));

                    String details =
                            "EVENT DETAILS\n\n"
                            + "Event Name: " + rs.getString("event_name") + "\n\n"
                            + "Description: " + description + "\n\n"
                            + "Event Date: "
                            + (date == null ? "Not specified" : date) + "\n"
                            + "Time: " + timeText + "\n"
                            + "Venue: " + rs.getString("venue") + "\n"
                            + "Participant Limit: "
                            + (limit == null ? "No limit specified" : limit) + "\n"
                            + "Registration Deadline: "
                            + (deadline == null ? "Not specified" : deadline) + "\n"
                            + "Status: " + rs.getString("status");

                    javax.swing.JTextArea textArea =
                            new javax.swing.JTextArea(details, 16, 40);
                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(
                            new java.awt.Font("Arial", java.awt.Font.PLAIN, 13));
                    textArea.setCaretPosition(0);

                    javax.swing.JScrollPane scroll =
                            new javax.swing.JScrollPane(textArea);
                    scroll.setPreferredSize(new java.awt.Dimension(430, 300));

                    JOptionPane.showMessageDialog(
                            this,
                            scroll,
                            "View Event Details",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading event details: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private String formatEventTime(String timeText) {
        try {
            java.text.SimpleDateFormat inputFormat =
                    new java.text.SimpleDateFormat("HH:mm:ss");

            java.text.SimpleDateFormat outputFormat =
                    new java.text.SimpleDateFormat("hh:mm a");

            java.util.Date time = inputFormat.parse(timeText);
            return outputFormat.format(time);

        } catch (java.text.ParseException e) {
            return timeText;
        }
    }


    private void styleEventsTable() {

        // TABLE HEADER
        javax.swing.table.JTableHeader header =
                eventsTable.getTableHeader();

        header.setFont(new java.awt.Font(
                "Arial", java.awt.Font.BOLD, 13));
        header.setOpaque(true);
        header.setBackground(new java.awt.Color(8, 107, 58));
        header.setForeground(java.awt.Color.WHITE);
        header.setPreferredSize(new java.awt.Dimension(0, 35));

        javax.swing.table.DefaultTableCellRenderer headerRenderer =
                new javax.swing.table.DefaultTableCellRenderer() {

            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                java.awt.Component c =
                        super.getTableCellRendererComponent(
                                table, value, isSelected,
                                hasFocus, row, column);

                c.setBackground(new java.awt.Color(8, 107, 58));
                c.setForeground(java.awt.Color.WHITE);
                c.setFont(new java.awt.Font(
                        "Arial", java.awt.Font.BOLD, 13));

                setHorizontalAlignment(
                        javax.swing.SwingConstants.CENTER);
                setOpaque(true);

                return c;
            }
        };

        for (int i = 0; i < eventsTable.getColumnModel()
                .getColumnCount(); i++) {
            eventsTable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // TABLE BODY
        eventsTable.setFont(new java.awt.Font(
                "Arial", java.awt.Font.PLAIN, 12));

        eventsTable.setRowHeight(30);

        eventsTable.setBackground(java.awt.Color.WHITE);
        eventsTable.setForeground(new java.awt.Color(51, 65, 85));

        eventsTable.setSelectionBackground(
                new java.awt.Color(198, 230, 207));

        eventsTable.setSelectionForeground(
                new java.awt.Color(8, 70, 38));

        eventsTable.setGridColor(
                new java.awt.Color(220, 230, 223));

        eventsTable.setShowGrid(true);
        eventsTable.setFillsViewportHeight(true);

        // TABLE BACKGROUND AROUND THE ROWS
        jScrollPane1.getViewport().setBackground(
                new java.awt.Color(245, 249, 246));

        jScrollPane1.setBackground(
                new java.awt.Color(245, 249, 246));

        jScrollPane1.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(8, 107, 58), 1));

        header.repaint();
        eventsTable.repaint();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        eventsTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        txtSearchEvent = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        cmbCategory = new javax.swing.JComboBox<>();
        lblDate = new javax.swing.JLabel();
        lblEventStatus = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();

        lblTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTitle.setText("SCHOOL EVENTS");

        lblSubtitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSubtitle.setText("Available Events");

        jScrollPane1.setBackground(new java.awt.Color(204, 255, 204));
        jScrollPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        eventsTable.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        eventsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Event ID", "Event Name", "Date", "Venue", "Participant Limit", "Status"
            }
        ));
        jScrollPane1.setViewportView(eventsTable);

        refreshButton.setForeground(new java.awt.Color(0, 102, 0));
        refreshButton.setText("Refresh Events");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        txtSearchEvent.setText("Search event or venue");

        btnSearch.setText("Search");

        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblDate.setText("Date:");

        lblEventStatus.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEventStatus.setText("Event Status:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblSubtitle))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(refreshButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblEventStatus)
                                .addGap(361, 361, 361))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTitle))
                                .addGap(225, 225, 225))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtSearchEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch)
                                .addGap(123, 123, 123)
                                .addComponent(lblDate)
                                .addGap(26, 26, 26)
                                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(774, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(81, 81, 81)
                            .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(88, 88, 88)
                            .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEventStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refreshButton)
                .addContainerGap(67, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        txtSearchEvent.setText("");
        dateChooser.setDate(null);
        cmbCategory.setSelectedIndex(0);

        loadEvents();
    }//GEN-LAST:event_refreshButtonActionPerformed
   
    private void showRegistrationConfirmation(
            int eventId,
            String eventName,
            String eventDate,
            String venue) {

        UserSession session = UserSession.getInstance();

        String message =
                "EVENT REGISTRATION CONFIRMATION\n\n"
                + "Event: " + eventName + "\n"
                + "Date: " + eventDate + "\n"
                + "Venue: " + venue + "\n\n"
                + "Student Name: "
                + (session.getFullName() == null
                        ? "Not available" : session.getFullName()) + "\n"
                + "Email: "
                + (session.getEmail() == null
                        ? "Not available" : session.getEmail()) + "\n"
                + "Course: "
                + (session.getCourse() == null
                        ? "Not available" : session.getCourse()) + "\n"
                + "Year Level: "
                + (session.getYearLevel() == null
                        ? "Not available" : session.getYearLevel())
                + "\n\nConfirm your registration?";

        int choice = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm Event Registration",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            saveEventRegistration(eventId);
        }
    }
    
    private void saveEventRegistration(int eventId) {

        UserSession session = UserSession.getInstance();

        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your session has expired. Please log in again.",
                    "Session Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int userId = session.getUserId();

         
        String checkEventSql =
                "SELECT status, is_archived, event_date, "
              + "registration_deadline "
              + "FROM tbl_events WHERE id = ?";

        String checkRegistrationSql =
                "SELECT id FROM tbl_registrations "
                + "WHERE user_id = ? AND event_id = ?";

        String insertSql =
                "INSERT INTO tbl_registrations "
                + "(user_id, event_id, registration_date, status) "
                + "VALUES (?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not connect to the database.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // 1. Check whether the event exists and is open.
            try (PreparedStatement ps =
                    conn.prepareStatement(checkEventSql)) {

                ps.setInt(1, eventId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                                this, "Event not found.");
                        loadEvents();
                        return;
                    }

        String status = rs.getString("status");
        boolean isArchived = rs.getBoolean("is_archived");
        java.sql.Date deadline = rs.getDate("registration_deadline");

        if (isArchived
                || status == null
                || !status.trim().equalsIgnoreCase("Open")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration for this event is closed.",
                    "Registration Closed",
                    JOptionPane.WARNING_MESSAGE
            );
            loadEvents();
            return;
        }

        if (deadline != null
                && deadline.toLocalDate().isBefore(
                        java.time.LocalDate.now())) {

            JOptionPane.showMessageDialog(
                    this,
                    "The registration deadline has passed.",
                    "Registration Closed",
                    JOptionPane.WARNING_MESSAGE
            );
            loadEvents();
            return;
        }

                    if (isArchived
                            || status == null
                            || !status.trim().equalsIgnoreCase("Open")) {
    
                        JOptionPane.showMessageDialog(
                                this,
                                "Registration for this event is closed.",
                                "Registration Closed",
                                JOptionPane.WARNING_MESSAGE
                        );
                        loadEvents();
                        return;
                    }
                }
            }

            // 2. Check for an existing registration.
            try (PreparedStatement ps =
                    conn.prepareStatement(checkRegistrationSql)) {

                ps.setInt(1, userId);
                ps.setInt(2, eventId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(
                                this,
                                "You are already registered for this event.",
                                "Duplicate Registration",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                }
            }

            // 3. Save the registration.
            try (PreparedStatement ps =
                    conn.prepareStatement(insertSql)) {

                ps.setInt(1, userId);
                ps.setInt(2, eventId);
                ps.setString(3, "Registered");

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "You have successfully registered for this event!",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
                    loadEvents();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
    
    private boolean checkRegistrationEligibility(int eventId, int userId) {

        String sql =
                "SELECT e.status, e.is_archived, e.event_date, "
              + "e.registration_deadline, "
              + "e.participant_limit, "
              + "(SELECT COUNT(*) FROM tbl_registrations r "
              + "WHERE r.event_id = e.id) AS registered_count, "
              + "(SELECT COUNT(*) FROM tbl_registrations r "
              + "WHERE r.event_id = e.id AND r.user_id = ?) "
              + "AS already_registered "
              + "FROM tbl_events e WHERE e.id = ?";
              
        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Could not connect to the database.");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setInt(2, eventId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(
                                this, "Event not found.");
                        return false;
                    }

                    String status = rs.getString("status");
                    
                    boolean isArchived = rs.getBoolean("is_archived");

                        if (isArchived) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "This event has been archived and is no longer available for registration.",
                                    "Event Archived",
                                    JOptionPane.WARNING_MESSAGE);
                            loadEvents();
                            return false;
                        }

                    if (status == null
                            || !status.equalsIgnoreCase("Open")) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Registration for this event is closed.",
                                "Registration Closed",
                                JOptionPane.WARNING_MESSAGE);
                        return false;
                    }

                    if (rs.getInt("already_registered") > 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "You are already registered for this event.",
                                "Already Registered",
                                JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
               
                    
                java.sql.Date eventDate = rs.getDate("event_date");

                if (eventDate != null
                        && eventDate.toLocalDate().isBefore(
                                java.time.LocalDate.now())) {

                    JOptionPane.showMessageDialog(
                            this,
                            "This event has already passed. Registration is no longer available.",
                            "Event Ended",
                            JOptionPane.WARNING_MESSAGE);
                    loadEvents();
                    return false;
                }

                java.sql.Date deadline =
                        rs.getDate("registration_deadline");

                if (deadline != null
                        && deadline.toLocalDate().isBefore(
                                java.time.LocalDate.now())) {

                    JOptionPane.showMessageDialog(
                            this,
                            "The registration deadline has passed.",
                            "Registration Closed",
                            JOptionPane.WARNING_MESSAGE);
                    loadEvents();
                    return false;
                }


                    Object limitValue =
                            rs.getObject("participant_limit");

                    if (limitValue != null) {
                        int limit = rs.getInt("participant_limit");
                        int registeredCount =
                                rs.getInt("registered_count");

                        if (limit > 0 && registeredCount >= limit) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "This event has reached its participant limit.",
                                    "Event Full",
                                    JOptionPane.WARNING_MESSAGE);
                            return false;
                        }
                    }

                    return true;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not check registration eligibility: "
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    
    private boolean isStudentRegistered(int eventId, int userId) {

        String sql =
                "SELECT id FROM tbl_registrations "
              + "WHERE event_id = ? AND user_id = ?";

        try (Connection conn = DBConnection.connect()) {

            if (conn == null) {
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, eventId);
                ps.setInt(2, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not check registration status: "
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbCategory;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JTable eventsTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblEventStatus;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField txtSearchEvent;
    // End of variables declaration//GEN-END:variables
}
