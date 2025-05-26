import javax.swing.*;

public class LogoutPage extends JFrame {
    public LogoutPage() {
        setTitle("Thank You");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel message = new JLabel("Thank you for voting!", SwingConstants.CENTER);
        message.setBounds(50, 30, 200, 30);
        add(message);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(90, 80, 100, 30);
        add(logoutBtn);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }
}

