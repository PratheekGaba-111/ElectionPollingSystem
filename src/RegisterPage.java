import javax.swing.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    public RegisterPage() {
        setTitle("Register");
        setSize(350, 300);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel idLabel = new JLabel("Voter ID:");
        idLabel.setBounds(30, 30, 100, 25);
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(130, 30, 160, 25);
        add(idField);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 70, 100, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(130, 70, 160, 25);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 110, 100, 25);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 110, 160, 25);
        add(passField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(50, 160, 120, 30);
        add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(190, 160, 120, 30);
        add(backBtn);

        registerBtn.addActionListener(e -> {
            String voterId = idField.getText();
            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            if (voterId.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {
                String query = "INSERT INTO voters (voter_id, username, password, has_voted) VALUES (?, ?, ?, 0)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(voterId));
                ps.setString(2, username);
                ps.setString(3, password);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Registered Successfully");
                dispose();
                new LoginPage();
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(this, "Voter ID or Username already exists");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }
}

