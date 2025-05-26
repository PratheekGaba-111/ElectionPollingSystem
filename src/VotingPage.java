import javax.swing.*;
import java.sql.*;

public class VotingPage extends JFrame {
    public VotingPage(int voterId) {
        setTitle("Vote");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Select Party:");
        label.setBounds(30, 30, 100, 25);
        add(label);

        JComboBox<String> partyCombo = new JComboBox<>();
        partyCombo.setBounds(150, 30, 180, 25);
        add(partyCombo);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT party_id, party_name FROM parties");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                partyCombo.addItem(rs.getInt("party_id") + "-" + rs.getString("party_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton voteBtn = new JButton("Vote");
        voteBtn.setBounds(150, 80, 100, 30);
        add(voteBtn);

        voteBtn.addActionListener(e -> {
            String selected = (String) partyCombo.getSelectedItem();
            if (selected == null) return;
            int partyId = Integer.parseInt(selected.split("-")[0]);

            try (Connection con = DBConnection.getConnection()) {
                // Check if already voted
                PreparedStatement check = con.prepareStatement("SELECT has_voted FROM voters WHERE voter_id = ?");
                check.setInt(1, voterId);
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getBoolean("has_voted")) {
                    JOptionPane.showMessageDialog(this, "You have already voted!");
                    return;
                }

                // Insert vote
                PreparedStatement ps = con.prepareStatement("INSERT INTO votes (voter_id, party_id) VALUES (?, ?)");
                ps.setInt(1, voterId);
                ps.setInt(2, partyId);
                ps.executeUpdate();

                // Update has_voted
                PreparedStatement update = con.prepareStatement("UPDATE voters SET has_voted = 1 WHERE voter_id = ?");
                update.setInt(1, voterId);
                update.executeUpdate();

                JOptionPane.showMessageDialog(this, "Vote cast successfully!");
                dispose();
                new LogoutPage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}

