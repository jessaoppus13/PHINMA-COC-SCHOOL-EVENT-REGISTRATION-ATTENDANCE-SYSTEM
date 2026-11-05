package event_registration;

public class staffdashboard extends javax.swing.JFrame {
    
    private java.awt.CardLayout cl;

    private staffrecordspanel recordsPanel;
    private staffeventspanel eventsPanel;
    private staffattendancepanel attendancePanel;
    private staffreportspanel reportsPanel;
    
    
    private void highlightButton(javax.swing.JButton selectedButton) {
        java.awt.Color green = new java.awt.Color(18, 107, 69);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);

        javax.swing.JButton[] buttons = {
            dashboard, events, records, attendance, btnReports
        };

        for (javax.swing.JButton button : buttons) {
            button.setBackground(green);
            button.setForeground(java.awt.Color.WHITE);
        }

        selectedButton.setBackground(gold);
        selectedButton.setForeground(darkGreen);
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(staffdashboard.class.getName());

    public staffdashboard() {
        initComponents();
        styleStaffDashboard();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

        cl = (java.awt.CardLayout) pnlContent.getLayout();

        // Add the Records panel
        recordsPanel = new staffrecordspanel();
        pnlContent.add(recordsPanel, "records");

        // Add the Events panel
        eventsPanel = new staffeventspanel();
        pnlContent.add(eventsPanel, "events");

        // Add the Attendance panel
        attendancePanel = new staffattendancepanel();
        pnlContent.add(attendancePanel, "attendance");
        
        // Add the Reports panel
        reportsPanel = new staffreportspanel();
        pnlContent.add(reportsPanel, "reports");

        // Show the Dashboard initially
        cl.show(pnlContent, "card2");

        loadDashboardSummary();
    }
    
    private void loadDashboardSummary() {
        String totalEventsQuery =
                "SELECT COUNT(*) FROM tbl_events";

        String openEventsQuery =
                "SELECT COUNT(*) FROM tbl_events "
              + "WHERE status = 'Open' "
              + "AND is_archived = 0";

        String totalRegistrationsQuery =
                "SELECT COUNT(*) FROM tbl_registrations";

        String presentStudentsQuery =
                "SELECT COUNT(*) FROM tbl_attendance "
                + "WHERE attendance_status = 'Present'";

        try (java.sql.Connection con = DBConnection.connect()) {

            try (java.sql.Statement stmt = con.createStatement()) {

                try (java.sql.ResultSet rs =
                        stmt.executeQuery(totalEventsQuery)) {
                    if (rs.next()) {
                        lblTotalEvents.setText(
                                String.valueOf(rs.getInt(1)));
                    }
                }

                try (java.sql.ResultSet rs =
                        stmt.executeQuery(openEventsQuery)) {
                    if (rs.next()) {
                        lblOpenEvents.setText(
                                String.valueOf(rs.getInt(1)));
                    }
                }

                try (java.sql.ResultSet rs =
                        stmt.executeQuery(totalRegistrationsQuery)) {
                    if (rs.next()) {
                        lblTotalRegistrations.setText(
                                String.valueOf(rs.getInt(1)));
                    }
                }

                try (java.sql.ResultSet rs =
                        stmt.executeQuery(presentStudentsQuery)) {
                    if (rs.next()) {
                        lblPresentStudents.setText(
                                String.valueOf(rs.getInt(1)));
                    }
                }
            }

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to load dashboard summary: " + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    private void styleStaffDashboard() {
        // PHINMA COC color palette
        java.awt.Color green = new java.awt.Color(18, 107, 69);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);
        java.awt.Color softGreen = new java.awt.Color(244, 247, 244);
        java.awt.Color white = java.awt.Color.WHITE;
        java.awt.Color mutedText = new java.awt.Color(82, 100, 90);
        java.awt.Color borderColor = new java.awt.Color(220, 230, 223);

        // Main backgrounds
        pnlMain.setBackground(softGreen);
        pnlContent.setBackground(softGreen);
        pnlDashboard.setBackground(softGreen);

        // Sidebar
        pnlSidebar.setBackground(green);
        jLabel2.setBackground(green);
        jLabel2.setForeground(white);

        // Dashboard heading
        lblWelcome.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 24)
        );
        lblWelcome.setForeground(green);

        lblDashboardTitle1.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 18)
        );
        lblDashboardTitle1.setForeground(darkGreen);

        lblSubtitle.setFont(
            new java.awt.Font("Arial", java.awt.Font.PLAIN, 14)
        );
        lblSubtitle.setForeground(mutedText);

        // Summary cards
        javax.swing.JPanel[] cards = {
            pnlTotalEvents,
            pnlOpenEvents,
            pnlTotalRegistrations,
            pnlPresentStudents
        };

        for (javax.swing.JPanel card : cards) {
            card.setBackground(white);
            card.setBorder(
                javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(borderColor),
                    javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4)
                )
            );
        }

        // Large summary numbers
        javax.swing.JLabel[] numbers = {
            lblTotalEvents,
            lblOpenEvents,
            lblTotalRegistrations,
            lblPresentStudents
        };

        for (javax.swing.JLabel number : numbers) {
            number.setFont(
                new java.awt.Font("Arial", java.awt.Font.BOLD, 28)
            );
            number.setForeground(green);
        }

        // Summary card labels
        javax.swing.JLabel[] titles = {
            lblTotalEventsTitle,
            lblOpenEventsTitle,
            lblTotalRegistrationsTitle,
            lblPresentStudentsTitle
        };

        for (javax.swing.JLabel title : titles) {
            title.setFont(
                new java.awt.Font("Arial", java.awt.Font.BOLD, 14)
            );
            title.setForeground(darkGreen);
        }

        // Navigation buttons
        highlightButton(dashboard);

        // Logout button
        logout.setBackground(darkGreen);
        logout.setForeground(white);
        logout.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12)
        );
        logout.setFocusPainted(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        events = new javax.swing.JButton();
        records = new javax.swing.JButton();
        attendance = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        lblDashboardTitle1 = new javax.swing.JLabel();
        pnlTotalEvents = new javax.swing.JPanel();
        lblTotalEvents = new javax.swing.JLabel();
        lblTotalEventsTitle = new javax.swing.JLabel();
        pnlTotalRegistrations = new javax.swing.JPanel();
        lblTotalRegistrationsTitle = new javax.swing.JLabel();
        lblTotalRegistrations = new javax.swing.JLabel();
        pnlOpenEvents = new javax.swing.JPanel();
        lblOpenEventsTitle = new javax.swing.JLabel();
        lblOpenEvents = new javax.swing.JLabel();
        pnlPresentStudents = new javax.swing.JPanel();
        lblPresentStudentsTitle = new javax.swing.JLabel();
        lblPresentStudents = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlMain.setBackground(new java.awt.Color(244, 247, 244));
        pnlMain.setMinimumSize(new java.awt.Dimension(100, 100));
        pnlMain.setPreferredSize(new java.awt.Dimension(469, 494));

        pnlSidebar.setBackground(new java.awt.Color(18, 107, 69));

        jLabel2.setBackground(new java.awt.Color(18, 107, 69));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("STAFF PORTAL");
        jLabel2.setOpaque(true);

        events.setBackground(new java.awt.Color(18, 107, 69));
        events.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        events.setForeground(new java.awt.Color(255, 255, 255));
        events.setText("Manage Events");
        events.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        events.setFocusable(false);
        events.setPreferredSize(new java.awt.Dimension(220, 64));
        events.addActionListener(this::eventsActionPerformed);

        records.setBackground(new java.awt.Color(18, 107, 69));
        records.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        records.setForeground(new java.awt.Color(255, 255, 255));
        records.setText("Student Records");
        records.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        records.setFocusable(false);
        records.setPreferredSize(new java.awt.Dimension(220, 64));
        records.addActionListener(this::recordsActionPerformed);

        attendance.setBackground(new java.awt.Color(18, 107, 69));
        attendance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        attendance.setForeground(new java.awt.Color(255, 255, 255));
        attendance.setText("Attendance");
        attendance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        attendance.setFocusable(false);
        attendance.setPreferredSize(new java.awt.Dimension(220, 64));
        attendance.addActionListener(this::attendanceActionPerformed);

        logout.setBackground(new java.awt.Color(25, 75, 53));
        logout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setText("   Logout   ");
        logout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logout.setPreferredSize(new java.awt.Dimension(100, 36));
        logout.addActionListener(this::logoutActionPerformed);

        dashboard.setBackground(new java.awt.Color(240, 215, 143));
        dashboard.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        dashboard.setForeground(new java.awt.Color(25, 75, 53));
        dashboard.setText("Dashboard");
        dashboard.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dashboard.setFocusable(false);
        dashboard.setMaximumSize(new java.awt.Dimension(220, 64));
        dashboard.setMinimumSize(new java.awt.Dimension(220, 64));
        dashboard.setPreferredSize(new java.awt.Dimension(220, 64));
        dashboard.addActionListener(this::dashboardActionPerformed);

        btnReports.setBackground(new java.awt.Color(18, 107, 69));
        btnReports.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnReports.setForeground(new java.awt.Color(255, 255, 255));
        btnReports.setText("Attendance Reports");
        btnReports.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReports.setFocusable(false);
        btnReports.setPreferredSize(new java.awt.Dimension(220, 64));
        btnReports.addActionListener(this::btnReportsActionPerformed);

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(114, 114, 114))
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addComponent(events, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attendance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(events, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(attendance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(records, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );

        pnlContent.setLayout(new java.awt.CardLayout());

        pnlDashboard.setBackground(new java.awt.Color(244, 247, 244));

        lblWelcome.setBackground(new java.awt.Color(25, 75, 53));
        lblWelcome.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(25, 75, 53));
        lblWelcome.setText("Welcome Staff!");

        lblSubtitle.setBackground(new java.awt.Color(25, 75, 53));
        lblSubtitle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(25, 75, 53));
        lblSubtitle.setText("Here is your event summary.");

        lblDashboardTitle1.setBackground(new java.awt.Color(25, 75, 53));
        lblDashboardTitle1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblDashboardTitle1.setForeground(new java.awt.Color(25, 75, 53));
        lblDashboardTitle1.setText("Dashboard Overview");

        pnlTotalEvents.setBackground(new java.awt.Color(255, 255, 255));
        pnlTotalEvents.setPreferredSize(new java.awt.Dimension(300, 150));

        lblTotalEvents.setBackground(new java.awt.Color(25, 75, 53));
        lblTotalEvents.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblTotalEvents.setForeground(new java.awt.Color(18, 107, 69));
        lblTotalEvents.setText("0");

        lblTotalEventsTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblTotalEventsTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTotalEventsTitle.setForeground(new java.awt.Color(82, 100, 90));
        lblTotalEventsTitle.setText("Total Events");

        javax.swing.GroupLayout pnlTotalEventsLayout = new javax.swing.GroupLayout(pnlTotalEvents);
        pnlTotalEvents.setLayout(pnlTotalEventsLayout);
        pnlTotalEventsLayout.setHorizontalGroup(
            pnlTotalEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalEventsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlTotalEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalEvents)
                    .addComponent(lblTotalEventsTitle))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        pnlTotalEventsLayout.setVerticalGroup(
            pnlTotalEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalEventsLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(lblTotalEvents)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalEventsTitle)
                .addGap(16, 16, 16))
        );

        pnlTotalRegistrations.setBackground(new java.awt.Color(255, 255, 255));
        pnlTotalRegistrations.setPreferredSize(new java.awt.Dimension(300, 150));

        lblTotalRegistrationsTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblTotalRegistrationsTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTotalRegistrationsTitle.setForeground(new java.awt.Color(82, 100, 90));
        lblTotalRegistrationsTitle.setText("Total Registrations");

        lblTotalRegistrations.setBackground(new java.awt.Color(25, 75, 53));
        lblTotalRegistrations.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblTotalRegistrations.setForeground(new java.awt.Color(18, 107, 69));
        lblTotalRegistrations.setText("0");

        javax.swing.GroupLayout pnlTotalRegistrationsLayout = new javax.swing.GroupLayout(pnlTotalRegistrations);
        pnlTotalRegistrations.setLayout(pnlTotalRegistrationsLayout);
        pnlTotalRegistrationsLayout.setHorizontalGroup(
            pnlTotalRegistrationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalRegistrationsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlTotalRegistrationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalRegistrations)
                    .addComponent(lblTotalRegistrationsTitle))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        pnlTotalRegistrationsLayout.setVerticalGroup(
            pnlTotalRegistrationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalRegistrationsLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(lblTotalRegistrations)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalRegistrationsTitle)
                .addGap(16, 16, 16))
        );

        pnlOpenEvents.setBackground(new java.awt.Color(255, 255, 255));
        pnlOpenEvents.setPreferredSize(new java.awt.Dimension(300, 150));

        lblOpenEventsTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblOpenEventsTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblOpenEventsTitle.setForeground(new java.awt.Color(82, 100, 90));
        lblOpenEventsTitle.setText("Open Events");

        lblOpenEvents.setBackground(new java.awt.Color(25, 75, 53));
        lblOpenEvents.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblOpenEvents.setForeground(new java.awt.Color(18, 107, 69));
        lblOpenEvents.setText("0");

        javax.swing.GroupLayout pnlOpenEventsLayout = new javax.swing.GroupLayout(pnlOpenEvents);
        pnlOpenEvents.setLayout(pnlOpenEventsLayout);
        pnlOpenEventsLayout.setHorizontalGroup(
            pnlOpenEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOpenEventsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlOpenEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpenEvents)
                    .addComponent(lblOpenEventsTitle))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        pnlOpenEventsLayout.setVerticalGroup(
            pnlOpenEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOpenEventsLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(lblOpenEvents)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblOpenEventsTitle)
                .addGap(16, 16, 16))
        );

        pnlPresentStudents.setBackground(new java.awt.Color(255, 255, 255));
        pnlPresentStudents.setPreferredSize(new java.awt.Dimension(300, 150));

        lblPresentStudentsTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblPresentStudentsTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblPresentStudentsTitle.setForeground(new java.awt.Color(82, 100, 90));
        lblPresentStudentsTitle.setText("Present Students");

        lblPresentStudents.setBackground(new java.awt.Color(25, 75, 53));
        lblPresentStudents.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblPresentStudents.setForeground(new java.awt.Color(18, 107, 69));
        lblPresentStudents.setText("0");

        javax.swing.GroupLayout pnlPresentStudentsLayout = new javax.swing.GroupLayout(pnlPresentStudents);
        pnlPresentStudents.setLayout(pnlPresentStudentsLayout);
        pnlPresentStudentsLayout.setHorizontalGroup(
            pnlPresentStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPresentStudentsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlPresentStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPresentStudents)
                    .addComponent(lblPresentStudentsTitle))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        pnlPresentStudentsLayout.setVerticalGroup(
            pnlPresentStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPresentStudentsLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(lblPresentStudents)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPresentStudentsTitle)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDashboardLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDashboardTitle1)
                            .addComponent(lblWelcome)))
                    .addGroup(pnlDashboardLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubtitle)
                            .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlDashboardLayout.createSequentialGroup()
                                    .addComponent(pnlTotalRegistrations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlPresentStudents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDashboardLayout.createSequentialGroup()
                                    .addComponent(pnlTotalEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(54, 54, 54)
                                    .addComponent(pnlOpenEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblWelcome)
                .addGap(18, 18, 18)
                .addComponent(lblDashboardTitle1)
                .addGap(90, 90, 90)
                .addComponent(lblSubtitle)
                .addGap(18, 18, 18)
                .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPresentStudents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDashboardLayout.createSequentialGroup()
                        .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTotalEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlOpenEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(pnlTotalRegistrations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        pnlContent.add(pnlDashboard, "card2");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1344, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventsActionPerformed
        cl.show(pnlContent, "events");
        highlightButton(events);

    }//GEN-LAST:event_eventsActionPerformed

    private void recordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordsActionPerformed
        cl.show(pnlContent, "records");
        highlightButton(records);
    }//GEN-LAST:event_recordsActionPerformed

    private void attendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendanceActionPerformed
        cl.show(pnlContent, "attendance");
        highlightButton(attendance);   
    }//GEN-LAST:event_attendanceActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        int choice = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Confirm Logout",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            UserSession.getInstance().clear();

            login log = new login();
            log.setLocationRelativeTo(null);
            log.setVisible(true);

            this.dispose();
        }
    }//GEN-LAST:event_logoutActionPerformed

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        cl.show(pnlContent, "card2");
        highlightButton(dashboard);
        loadDashboardSummary();
    }//GEN-LAST:event_dashboardActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        cl.show(pnlContent, "reports");
        highlightButton(btnReports);
    }//GEN-LAST:event_btnReportsActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new staffdashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attendance;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton dashboard;
    private javax.swing.JButton events;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDashboardTitle1;
    private javax.swing.JLabel lblOpenEvents;
    private javax.swing.JLabel lblOpenEventsTitle;
    private javax.swing.JLabel lblPresentStudents;
    private javax.swing.JLabel lblPresentStudentsTitle;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTotalEvents;
    private javax.swing.JLabel lblTotalEventsTitle;
    private javax.swing.JLabel lblTotalRegistrations;
    private javax.swing.JLabel lblTotalRegistrationsTitle;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JButton logout;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlOpenEvents;
    private javax.swing.JPanel pnlPresentStudents;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlTotalEvents;
    private javax.swing.JPanel pnlTotalRegistrations;
    private javax.swing.JButton records;
    // End of variables declaration//GEN-END:variables
}
