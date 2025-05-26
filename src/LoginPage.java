import javax.swing.*;
import java.sql.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        setTitle("Login");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 100, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(130, 30, 160, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 100, 25);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 70, 160, 25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 120, 120, 30);
        add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(190, 120, 120, 30);
        add(registerBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            if (username.equals("admin") && password.equals("admin123")) {
                // Admin credentials (you can change them)
                dispose();
                new AdminPage();
            } else {
                try (Connection con = DBConnection.getConnection()) {
                    String query = "SELECT voter_id, password, has_voted FROM voters WHERE username = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        String dbPass = rs.getString("password");
                        int voterId = rs.getInt("voter_id");
                        boolean hasVoted = rs.getBoolean("has_voted");

                        if (dbPass.equals(password)) {
                            if (hasVoted) {
                                JOptionPane.showMessageDialog(this, "You have already voted!");
                            } else {
                                dispose();
                                new VotingPage(voterId);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid password");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "User not found");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterPage();
        });

        setVisible(true);
    }
}

