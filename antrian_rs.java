import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;

class PasienRS {
    int nomor;
    String nama;

    PasienRS(int nomor, String nama) {
        this.nomor = nomor;
        this.nama = nama;
    }

    @Override
    public String toString() {
        return String.format("  #%03d  |  %s", nomor, nama);
    }
}

public class antrian_rs extends JFrame {
    private Queue<PasienRS> antrian = new LinkedList<>();
    private int counter = 1;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> listUI = new JList<>(listModel);
    private JTextField txtNama = new JTextField();

    public antrian_rs() {
        setTitle("Sistem Antrian RS - Voice Java");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // Header
        JLabel lblHeader = new JLabel("ANTRIAN RUMAH SAKIT", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 22));
        lblHeader.setForeground(new Color(44, 62, 80));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        txtNama.setFont(new Font("Helvetica", Font.PLAIN, 16));
        txtNama.setMaximumSize(new Dimension(350, 45));
        txtNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnAmbil = createStyledButton("AMBIL NOMOR", new Color(52, 152, 219));
        JButton btnPanggil = createStyledButton("PANGGIL PASIEN", new Color(46, 204, 113));

        listUI.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(listUI);
        scrollPane.setPreferredSize(new Dimension(350, 200));

        centerPanel.add(new JLabel("Masukkan Nama Pasien:"));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(txtNama);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(btnAmbil);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(btnPanggil);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(new JLabel("Daftar Antrian Aktif:"));
        centerPanel.add(scrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Logika Ambil Antrian
        btnAmbil.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            if (!nama.isEmpty()) {
                PasienRS p = new PasienRS(counter++, nama);
                antrian.add(p);
                listModel.addElement(p.toString());
                txtNama.setText("");
            }
        });

        // Logika Panggil Antrian dengan SUARA (Persis Python)
        btnPanggil.addActionListener(e -> {
            if (!antrian.isEmpty()) {
                PasienRS p = antrian.poll();
                listModel.remove(0);

                String pesan = "Nomor antrian " + p.nomor + ", " + p.nama + ", silakan ke loket";

                // Menjalankan suara di Thread terpisah agar UI tidak macet
                new Thread(() -> {
                    panggilSuaraMac(pesan);
                }).start();

                JOptionPane.showMessageDialog(this, pesan);
            } else {
                JOptionPane.showMessageDialog(this, "Antrian kosong.");
            }
        });
    }

    // Fungsi khusus untuk memanggil perintah 'say' di macOS
    private void panggilSuaraMac(String teks) {
        try {
            // Perintah 'say' di Mac dengan suara Damayanti (Bahasa Indonesia)
            // Jika suara Damayanti belum ada, Mac akan otomatis menggunakan suara default
            String[] cmd = {"say", "-v", "Damayanti", teks};
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.err.println("Gagal memanggil suara: " + e.getMessage());
        }
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(350, 50));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFont(new Font("Helvetica", Font.BOLD, 13));
        return b;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new antrian_rs().setVisible(true));
    }
}