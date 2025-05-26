import javax.swing.*;
import java.sql.*;

public class AdminPage extends JFrame {
    private JLabel bjp, congress, aap, tmc, sp;

    public AdminPage() {
        setTitle("Admin");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel title = new JLabel("Vote Counts");
        title.setBounds(140, 10, 150, 30);
        add(title);

        bjp = new JLabel();
        bjp.setBounds(30, 60, 200, 25);
        add(bjp);

        congress = new JLabel();
        congress.setBounds(30, 100, 200, 25);
        add(congress);

        aap = new JLabel();
        aap.setBounds(30, 140, 200, 25);
        add(aap);

        tmc = new JLabel();
        tmc.setBounds(30, 180, 200, 25);
        add(tmc);

        sp = new JLabel();
        sp.setBounds(30, 220, 200, 25);
        add(sp);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBounds(50, 280, 120, 30);
        add(resetBtn);
        resetBtn.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                con.createStatement().executeUpdate("DELETE FROM votes");
                con.createStatement().executeUpdate("UPDATE voters SET has_voted = 0");
                JOptionPane.showMessageDialog(this, "Reset successful!");
                updateCounts();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(200, 280, 120, 30);
        add(logoutBtn);
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        updateCounts();
        setVisible(true);
    }

    private void updateCounts() {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT party_name, COUNT(v.vote_id) as total FROM parties p " +
                "LEFT JOIN votes v ON p.party_id = v.party_id GROUP BY p.party_id"
            );
            ResultSet rs = ps.executeQuery();
            bjp.setText("BJP: 0");
            congress.setText("Congress: 0");
            aap.setText("AAP: 0");
            tmc.setText("TMC: 0");
            sp.setText("SP: 0");
            while (rs.next()) {
                switch (rs.getString("party_name")) {
                    case "BJP": bjp.setText("BJP: " + rs.getInt("total")); break;
                    case "Congress": congress.setText("Congress: " + rs.getInt("total")); break;
                    case "AAP": aap.setText("AAP: " + rs.getInt("total")); break;
                    case "TMC": tmc.setText("TMC: " + rs.getInt("total")); break;
                    case "SP": sp.setText("SP: " + rs.getInt("total")); break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

