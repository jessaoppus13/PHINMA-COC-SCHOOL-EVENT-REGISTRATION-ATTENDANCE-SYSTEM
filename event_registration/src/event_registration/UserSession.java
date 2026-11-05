package event_registration;

public class UserSession {

    private static UserSession instance;

    private int userId;
    private String email;
    private String fullName;
    private String course;
    private String yearLevel;
    private String role;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(int userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public void setUserDetails(
            String fullName,
            String course,
            String yearLevel,
            String role) {

        this.fullName = fullName;
        this.course = course;
        this.yearLevel = yearLevel;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCourse() {
        return course;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public String getRole() {
        return role;
    }

    public boolean isLoggedIn() {
        return userId != 0;
    }

    public void clear() {
        userId = 0;
        email = null;
        fullName = null;
        course = null;
        yearLevel = null;
        role = null;
    }
}
