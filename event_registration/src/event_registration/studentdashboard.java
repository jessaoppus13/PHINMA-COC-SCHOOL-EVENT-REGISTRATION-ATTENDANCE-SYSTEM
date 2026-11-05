package event_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.CardLayout;

public class studentdashboard extends javax.swing.JFrame {
    
    private CardLayout cl;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(studentdashboard.class.getName());
    
    public studentdashboard() {
    this("Student");
    }

    
    public studentdashboard(String fullName) {
        initComponents();
        getContentPane().setBackground(java.awt.Color.WHITE);
        getContentPane().setBackground(java.awt.Color.WHITE);
        pnlMain.setBackground(java.awt.Color.WHITE);
        pnlContent.setBackground(java.awt.Color.WHITE);
        pnlDashboard.setBackground(java.awt.Color.WHITE);
        
        lblWelcome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblWelcome.setForeground(new java.awt.Color(0, 83, 32));
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        
        UserSession session = UserSession.getInstance();

        lblCourse.setText("Course: " + 
                (session.getCourse() == null ? "Not set" : session.getCourse()));

        lblYearLevel.setText("Year Level: " + 
                (session.getYearLevel() == null ? "Not set" : session.getYearLevel()));

        styleDashboardComponents();

        cl = (CardLayout) pnlContent.getLayout();

        
        pnlEvents.setLayout(new java.awt.BorderLayout());
        pnlEvents.add(new studenteventspanel(), java.awt.BorderLayout.CENTER);
        
        pnlStatus.setLayout(new java.awt.BorderLayout());
        pnlStatus.add(new studentstatuspanel(), java.awt.BorderLayout.CENTER);
        
        
        pnlAttendance.setLayout(new java.awt.BorderLayout());
        pnlAttendance.add(
                new studentattendancepanel(),
                java.awt.BorderLayout.CENTER);
        
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        loadDashboardSummary();

        String firstName = "Student";

        if (fullName != null && !fullName.trim().isEmpty()) {
            firstName = fullName.trim().split("\\s+")[0];
        }

        lblWelcome.setText("Welcome, " + firstName + "!");
    }
    
    private void loadDashboardSummary() {
        UserSession session = UserSession.getInstance();

        if (!session.isLoggedIn()) {
            return;
        }

        String availableSql =
                "SELECT COUNT(*) FROM tbl_events "
              + "WHERE status = 'Open' "
              + "AND is_archived = 0";

        String registeredSql =
                "SELECT COUNT(*) FROM tbl_registrations WHERE user_id = ?";

        String attendedSql =
                "SELECT COUNT(*) "
              + "FROM tbl_registrations r "
              + "JOIN tbl_attendance a ON a.registration_id = r.id "
              + "WHERE r.user_id = ? "
              + "AND LOWER(a.attendance_status) = 'present'";

        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(
                        this, "Could not connect to the database.");
                return;
            }

            int available = 0;
            int registered = 0;
            int attended = 0;

            try (PreparedStatement ps =
                         conn.prepareStatement(availableSql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    available = rs.getInt(1);
                }
            }

            try (PreparedStatement ps =
                         conn.prepareStatement(registeredSql)) {
                ps.setInt(1, session.getUserId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        registered = rs.getInt(1);
                    }
                }
            }

            try (PreparedStatement ps =
                         conn.prepareStatement(attendedSql)) {
                ps.setInt(1, session.getUserId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        attended = rs.getInt(1);
                    }
                }
            }

            lblAvailableEvents.setText(String.valueOf(available));

            lblRegisteredEvents.setText(String.valueOf(registered));

            lblAttendedEvents.setText(String.valueOf(attended));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading dashboard summary: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    private void highlightButton(javax.swing.JButton selectedButton) {
        java.awt.Color green = new java.awt.Color(0, 83, 32);
        java.awt.Color gold = new java.awt.Color(240, 215, 143);
        java.awt.Color white = java.awt.Color.WHITE;
        java.awt.Color darkGreen = new java.awt.Color(25, 75, 53);

        javax.swing.JButton[] buttons = {
            dashboard, events, status, attendance
        };

        for (javax.swing.JButton button : buttons) {
            button.setBackground(green);
            button.setForeground(white);
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
        }

        selectedButton.setBackground(gold);
        selectedButton.setForeground(darkGreen);
    }


    private void styleDashboardComponents() {

        // PHINMA COC color palette
        java.awt.Color green =
                new java.awt.Color(0, 83, 32);

        java.awt.Color darkGreen =
                new java.awt.Color(25, 75, 53);

        java.awt.Color gold =
                new java.awt.Color(240, 215, 143);

        java.awt.Color white =
                java.awt.Color.WHITE;

        java.awt.Color lightBackground =
                new java.awt.Color(244, 247, 244);

        java.awt.Color subtitleGray =
                new java.awt.Color(85, 85, 85);

        java.awt.Color borderColor =
                new java.awt.Color(220, 230, 223);

        // Main dashboard backgrounds
        pnlMain.setBackground(lightBackground);
        pnlContent.setBackground(lightBackground);
        pnlDashboard.setBackground(java.awt.Color.WHITE);

        // Sidebar
        pnlSidebar.setBackground(green);
        jLabel2.setBackground(green);
        jLabel2.setForeground(white);
        jLabel2.setFont(
                new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
        jLabel2.setOpaque(true);

        // Navigation buttons
        javax.swing.JButton[] navButtons = {
            dashboard, events, status, attendance
        };

        for (javax.swing.JButton button : navButtons) {
            button.setBackground(green);
            button.setForeground(white);
            button.setFont(
                    new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setCursor(
                    new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }

        // Dashboard is selected when the dashboard first opens
        highlightButton(dashboard);

        // Logout button
        logout.setBackground(green);
        logout.setForeground(white);
        logout.setFont(
                new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        logout.setFocusPainted(false);
        logout.setOpaque(true);
        logout.setBorderPainted(false);
        logout.setCursor(
                new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Welcome message
        lblWelcome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblWelcome.setForeground(new java.awt.Color(0, 83, 32));
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        // Summary cards
        java.awt.Color cardBackground =
                new java.awt.Color(244, 247, 244);

        javax.swing.JPanel[] cards = {
            pnlAvailableCard, pnlRegisteredCard, pnlAttendedCard
        };

        for (javax.swing.JPanel card : cards) {
            card.setBackground(cardBackground);
            card.setBorder(
                    javax.swing.BorderFactory.createCompoundBorder(
                            javax.swing.BorderFactory.createLineBorder(
                                    borderColor, 1),
                            javax.swing.BorderFactory.createEmptyBorder(
                                    5, 5, 5, 5)));
        }

        // Summary card titles
        javax.swing.JLabel[] cardTitles = {
            lblAvailableTitle, lblRegisteredTitle, lblAttendedTitle
        };

        for (javax.swing.JLabel title : cardTitles) {
            title.setFont(
                    new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            title.setForeground(green);
            title.setBackground(cardBackground);
            title.setOpaque(true);
        }

        // Summary numbers
        javax.swing.JLabel[] cardNumbers = {
            lblAvailableEvents, lblRegisteredEvents, lblAttendedEvents
        };

        for (javax.swing.JLabel number : cardNumbers) {
            number.setFont(
                    new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
            number.setForeground(darkGreen);
            number.setBackground(cardBackground);
            number.setHorizontalAlignment(
                    javax.swing.SwingConstants.CENTER);
            number.setOpaque(true);
        }

        // Refresh the display
        pnlMain.revalidate();
        pnlMain.repaint();
    }

    private void buildDashboardLayout() {

        // Main frame layout
        pnlMain.removeAll();
        pnlMain.setLayout(new java.awt.BorderLayout());
        pnlMain.setBackground(new java.awt.Color(244, 247, 244));

        // Sidebar layout
        pnlSidebar.removeAll();
        pnlSidebar.setPreferredSize(new java.awt.Dimension(240, 0));
        pnlSidebar.setLayout(
            new javax.swing.BoxLayout(
                pnlSidebar, javax.swing.BoxLayout.Y_AXIS
            )
        );

        // Sidebar title: Syudent Portal
        javax.swing.JLabel sidebarTitle =
            new javax.swing.JLabel("  PHINMA COC");

        sidebarTitle.setFont(
            new java.awt.Font("Arial", java.awt.Font.BOLD, 22)
        );
        sidebarTitle.setForeground(java.awt.Color.WHITE);
        sidebarTitle.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(25, 15, 30, 10)
        );
        sidebarTitle.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        pnlSidebar.add(sidebarTitle);

        // Sidebar navigation labels
        String[] menuItems = {
            "  STUDENT MENU",
            "  Dashboard",
            "  Student Events",
            "  Registration Status",
            "  My Attendance"
        };

        for (String item : menuItems) {
            javax.swing.JLabel menuLabel =
                new javax.swing.JLabel(item);

            menuLabel.setFont(
                new java.awt.Font("Arial", java.awt.Font.PLAIN, 14)
            );
            menuLabel.setForeground(java.awt.Color.WHITE);
            menuLabel.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                    12, 15, 12, 5
                )
            );
            menuLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

            pnlSidebar.add(menuLabel);
        }

        pnlSidebar.add(javax.swing.Box.createVerticalGlue());

        // Main content header
        javax.swing.JPanel headerPanel = new javax.swing.JPanel();
        headerPanel.setLayout(
            new javax.swing.BoxLayout(
                headerPanel, javax.swing.BoxLayout.Y_AXIS
            )
        );
        headerPanel.setBackground(new java.awt.Color(244, 247, 244));
        headerPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(35, 40, 25, 20)
        );

        jLabel2.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        lblWelcome.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        headerPanel.add(jLabel2);
        headerPanel.add(javax.swing.Box.createVerticalStrut(20));
        headerPanel.add(lblWelcome);

        // Existing dashboard buttons
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(
            new javax.swing.BoxLayout(
                buttonPanel, javax.swing.BoxLayout.Y_AXIS
            )
        );
        buttonPanel.setBackground(new java.awt.Color(244, 247, 244));
        buttonPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(10, 40, 30, 40)
        );

        javax.swing.JButton[] buttons = {
            events, status, attendance
        };

        for (javax.swing.JButton button : buttons) {
            button.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
            button.setMaximumSize(
                new java.awt.Dimension(300, 64)
            );
            button.setPreferredSize(
                new java.awt.Dimension(300, 64)
            );

            buttonPanel.add(button);
            buttonPanel.add(javax.swing.Box.createVerticalStrut(18));
        }

        // Logout at the bottom of the main area
        javax.swing.JPanel bottomPanel = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 40, 20)
        );
        bottomPanel.setBackground(new java.awt.Color(244, 247, 244));
        bottomPanel.add(logout);

        // Assemble the dashboard
        javax.swing.JPanel contentPanel = new javax.swing.JPanel(
            new java.awt.BorderLayout()
        );
        contentPanel.setBackground(new java.awt.Color(244, 247, 244));

        contentPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        contentPanel.add(buttonPanel, java.awt.BorderLayout.CENTER);
        contentPanel.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        pnlMain.add(pnlSidebar, java.awt.BorderLayout.WEST);
        pnlMain.add(contentPanel, java.awt.BorderLayout.CENTER);

        pnlMain.revalidate();
        pnlMain.repaint();
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        events = new javax.swing.JButton();
        status = new javax.swing.JButton();
        attendance = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        dashboard = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        pnlAvailableCard = new javax.swing.JPanel();
        lblAvailableTitle = new javax.swing.JLabel();
        lblAvailableEvents = new javax.swing.JLabel();
        pnlRegisteredCard = new javax.swing.JPanel();
        lblRegisteredTitle = new javax.swing.JLabel();
        lblRegisteredEvents = new javax.swing.JLabel();
        pnlAttendedCard = new javax.swing.JPanel();
        lblAttendedTitle = new javax.swing.JLabel();
        lblAttendedEvents = new javax.swing.JLabel();
        lblCourse = new javax.swing.JLabel();
        lblYearLevel = new javax.swing.JLabel();
        pnlEvents = new javax.swing.JPanel();
        pnlStatus = new javax.swing.JPanel();
        pnlAttendance = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        pnlMain.setBackground(new java.awt.Color(244, 247, 244));
        pnlMain.setMinimumSize(new java.awt.Dimension(100, 100));
        pnlMain.setPreferredSize(new java.awt.Dimension(469, 494));

        pnlSidebar.setBackground(new java.awt.Color(18, 107, 69));
        pnlSidebar.setPreferredSize(new java.awt.Dimension(260, 600));

        jLabel2.setBackground(new java.awt.Color(18, 107, 69));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("STUDENT PORTAL");
        jLabel2.setOpaque(true);

        events.setBackground(new java.awt.Color(18, 107, 69));
        events.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        events.setForeground(new java.awt.Color(255, 255, 255));
        events.setText("View Events");
        events.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        events.setFocusable(false);
        events.setMaximumSize(new java.awt.Dimension(220, 64));
        events.setMinimumSize(new java.awt.Dimension(220, 64));
        events.setPreferredSize(new java.awt.Dimension(220, 64));
        events.addActionListener(this::eventsActionPerformed);

        status.setBackground(new java.awt.Color(18, 107, 69));
        status.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setText("View Status");
        status.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        status.setFocusable(false);
        status.setPreferredSize(new java.awt.Dimension(220, 64));
        status.addActionListener(this::statusActionPerformed);

        attendance.setBackground(new java.awt.Color(18, 107, 69));
        attendance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        attendance.setForeground(new java.awt.Color(255, 255, 255));
        attendance.setText("My Attendance");
        attendance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        attendance.setFocusable(false);
        attendance.setPreferredSize(new java.awt.Dimension(220, 64));
        attendance.addActionListener(this::attendanceActionPerformed);

        logout.setBackground(new java.awt.Color(18, 107, 69));
        logout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setText("   Logout   ");
        logout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logout.setFocusable(false);
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

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                            .addComponent(events, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(attendance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlSidebarLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel2)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addGap(63, 63, 63)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(events, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(attendance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        pnlContent.setBackground(new java.awt.Color(244, 247, 244));
        pnlContent.setLayout(new java.awt.CardLayout());

        pnlDashboard.setBackground(new java.awt.Color(244, 247, 244));

        lblWelcome.setBackground(new java.awt.Color(255, 255, 255));
        lblWelcome.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(24, 95, 61));
        lblWelcome.setText("Welcome Student!");

        pnlAvailableCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlAvailableCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlAvailableCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblAvailableTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblAvailableTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblAvailableTitle.setForeground(new java.awt.Color(18, 107, 69));
        lblAvailableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvailableTitle.setText("Available Events");

        lblAvailableEvents.setBackground(new java.awt.Color(244, 247, 244));
        lblAvailableEvents.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblAvailableEvents.setForeground(new java.awt.Color(24, 95, 61));
        lblAvailableEvents.setText("0");

        javax.swing.GroupLayout pnlAvailableCardLayout = new javax.swing.GroupLayout(pnlAvailableCard);
        pnlAvailableCard.setLayout(pnlAvailableCardLayout);
        pnlAvailableCardLayout.setHorizontalGroup(
            pnlAvailableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAvailableCardLayout.createSequentialGroup()
                .addGroup(pnlAvailableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAvailableCardLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(lblAvailableTitle))
                    .addGroup(pnlAvailableCardLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(lblAvailableEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        pnlAvailableCardLayout.setVerticalGroup(
            pnlAvailableCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAvailableCardLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(lblAvailableEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblAvailableTitle)
                .addGap(22, 22, 22))
        );

        pnlRegisteredCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlRegisteredCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlRegisteredCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblRegisteredTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblRegisteredTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblRegisteredTitle.setForeground(new java.awt.Color(18, 107, 69));
        lblRegisteredTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegisteredTitle.setText("My Registered Events");

        lblRegisteredEvents.setBackground(new java.awt.Color(244, 247, 244));
        lblRegisteredEvents.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblRegisteredEvents.setForeground(new java.awt.Color(24, 95, 61));
        lblRegisteredEvents.setText("0");

        javax.swing.GroupLayout pnlRegisteredCardLayout = new javax.swing.GroupLayout(pnlRegisteredCard);
        pnlRegisteredCard.setLayout(pnlRegisteredCardLayout);
        pnlRegisteredCardLayout.setHorizontalGroup(
            pnlRegisteredCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                .addGroup(pnlRegisteredCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblRegisteredTitle))
                    .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(lblRegisteredEvents)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        pnlRegisteredCardLayout.setVerticalGroup(
            pnlRegisteredCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisteredCardLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(lblRegisteredEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRegisteredTitle)
                .addGap(23, 23, 23))
        );

        pnlAttendedCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlAttendedCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlAttendedCard.setPreferredSize(new java.awt.Dimension(300, 150));

        lblAttendedTitle.setBackground(new java.awt.Color(25, 75, 53));
        lblAttendedTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblAttendedTitle.setForeground(new java.awt.Color(18, 107, 69));
        lblAttendedTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAttendedTitle.setText("Attended Events ");

        lblAttendedEvents.setBackground(new java.awt.Color(244, 247, 244));
        lblAttendedEvents.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        lblAttendedEvents.setForeground(new java.awt.Color(24, 95, 61));
        lblAttendedEvents.setText("0");

        javax.swing.GroupLayout pnlAttendedCardLayout = new javax.swing.GroupLayout(pnlAttendedCard);
        pnlAttendedCard.setLayout(pnlAttendedCardLayout);
        pnlAttendedCardLayout.setHorizontalGroup(
            pnlAttendedCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAttendedCardLayout.createSequentialGroup()
                .addContainerGap(248, Short.MAX_VALUE)
                .addGroup(pnlAttendedCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAttendedTitle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAttendedCardLayout.createSequentialGroup()
                        .addComponent(lblAttendedEvents)
                        .addGap(48, 48, 48)))
                .addGap(241, 241, 241))
        );
        pnlAttendedCardLayout.setVerticalGroup(
            pnlAttendedCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAttendedCardLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(lblAttendedEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAttendedTitle)
                .addGap(32, 32, 32))
        );

        lblCourse.setBackground(new java.awt.Color(244, 247, 244));
        lblCourse.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCourse.setForeground(new java.awt.Color(24, 95, 61));
        lblCourse.setText("Course:");

        lblYearLevel.setBackground(new java.awt.Color(244, 247, 244));
        lblYearLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblYearLevel.setForeground(new java.awt.Color(24, 95, 61));
        lblYearLevel.setText("Year Level:");

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDashboardLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlAttendedCard, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlDashboardLayout.createSequentialGroup()
                                .addComponent(pnlAvailableCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlRegisteredCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlDashboardLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCourse)
                            .addComponent(lblYearLevel)
                            .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(183, Short.MAX_VALUE))
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblYearLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAvailableCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlRegisteredCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlAttendedCard, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );

        pnlContent.add(pnlDashboard, "dashboard");

        pnlEvents.setBackground(new java.awt.Color(244, 247, 244));

        javax.swing.GroupLayout pnlEventsLayout = new javax.swing.GroupLayout(pnlEvents);
        pnlEvents.setLayout(pnlEventsLayout);
        pnlEventsLayout.setHorizontalGroup(
            pnlEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 936, Short.MAX_VALUE)
        );
        pnlEventsLayout.setVerticalGroup(
            pnlEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );

        pnlContent.add(pnlEvents, "events");

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 936, Short.MAX_VALUE)
        );
        pnlStatusLayout.setVerticalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );

        pnlContent.add(pnlStatus, "status");

        javax.swing.GroupLayout pnlAttendanceLayout = new javax.swing.GroupLayout(pnlAttendance);
        pnlAttendance.setLayout(pnlAttendanceLayout);
        pnlAttendanceLayout.setHorizontalGroup(
            pnlAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 936, Short.MAX_VALUE)
        );
        pnlAttendanceLayout.setVerticalGroup(
            pnlAttendanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );

        pnlContent.add(pnlAttendance, "attendance");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventsActionPerformed
        highlightButton(events);

        java.awt.CardLayout cl =
                (java.awt.CardLayout) pnlContent.getLayout();

        cl.show(pnlContent, "events");
    }//GEN-LAST:event_eventsActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        int choice = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to log out?",
        "Confirm Logout",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (choice == javax.swing.JOptionPane.YES_OPTION) {
        login loginForm = new login();
        loginForm.setLocationRelativeTo(null);
        loginForm.setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_logoutActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        highlightButton(status);

    java.awt.CardLayout cl =
            (java.awt.CardLayout) pnlContent.getLayout();

    cl.show(pnlContent, "status");
    }//GEN-LAST:event_statusActionPerformed

    private void attendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendanceActionPerformed
        highlightButton(attendance);

        java.awt.CardLayout cl =
                (java.awt.CardLayout) pnlContent.getLayout();

        cl.show(pnlContent, "attendance");
    }//GEN-LAST:event_attendanceActionPerformed

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        cl.show(pnlContent, "dashboard");
        highlightButton(dashboard);
        loadDashboardSummary();
    }//GEN-LAST:event_dashboardActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new studentdashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attendance;
    private javax.swing.JButton dashboard;
    private javax.swing.JButton events;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAttendedEvents;
    private javax.swing.JLabel lblAttendedTitle;
    private javax.swing.JLabel lblAvailableEvents;
    private javax.swing.JLabel lblAvailableTitle;
    private javax.swing.JLabel lblCourse;
    private javax.swing.JLabel lblRegisteredEvents;
    private javax.swing.JLabel lblRegisteredTitle;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel lblYearLevel;
    private javax.swing.JButton logout;
    private javax.swing.JPanel pnlAttendance;
    private javax.swing.JPanel pnlAttendedCard;
    private javax.swing.JPanel pnlAvailableCard;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlEvents;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRegisteredCard;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JButton status;
    // End of variables declaration//GEN-END:variables
}
