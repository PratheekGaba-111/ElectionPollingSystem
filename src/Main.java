public class Main {
    public static void main(String[] args) {
        // Optional: Test DB connection
        try {
            DBConnection.getConnection();
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Launch Login Page
        javax.swing.SwingUtilities.invokeLater(() -> new LoginPage());
    }
}

