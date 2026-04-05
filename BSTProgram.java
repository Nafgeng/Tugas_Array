import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// 1. Struktur Node untuk menyimpan data Mahasiswa/User
class Node {
    int id;
    String nama;
    Node left, right;

    public Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        this.left = this.right = null;
    }
}

// 2. Class Binary Search Tree (BST) dengan fitur lengkap
class BST {
    Node root;

    // --- FITUR: TAMBAH DATA ---
    void insert(int id, String nama) {
        root = insertRec(root, id, nama);
    }

    private Node insertRec(Node root, int id, String nama) {
        if (root == null) {
            return new Node(id, nama);
        }
        if (id < root.id)
            root.left = insertRec(root.left, id, nama);
        else if (id > root.id)
            root.right = insertRec(root.right, id, nama);
        return root;
    }

    // --- FITUR: CARI DATA ---
    Node search(int id) {
        return searchRec(root, id);
    }

    private Node searchRec(Node root, int id) {
        if (root == null || root.id == id)
            return root;
        return (id < root.id) ? searchRec(root.left, id) : searchRec(root.right, id);
    }

    // --- FITUR: HAPUS DATA ---
    void delete(int id) {
        root = deleteRec(root, id);
    }

    private Node deleteRec(Node root, int id) {
        if (root == null) return null;

        if (id < root.id)
            root.left = deleteRec(root.left, id);
        else if (id > root.id)
            root.right = deleteRec(root.right, id);
        else {
            // Node dengan 0 atau 1 anak
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Node dengan 2 anak: ambil nilai terkecil dari sub-pohon kanan
            root.id = getMinValue(root.right);
            root.right = deleteRec(root.right, root.id);
        }
        return root;
    }

    private int getMinValue(Node root) {
        int min = root.id;
        while (root.left != null) {
            min = root.left.id;
            root = root.left;
        }
        return min;
    }

    // --- FITUR: TRAVERSAL ---
    void displayTraversal(int type) {
        switch (type) {
            case 1 -> { System.out.println("\n[Inorder]:"); inorder(root); }
            case 2 -> { System.out.println("\n[Preorder]:"); preorder(root); }
            case 3 -> { System.out.println("\n[Postorder]:"); postorder(root); }
        }
        System.out.println();
    }

    private void inorder(Node n) {
        if (n != null) { inorder(n.left); System.out.print("(" + n.id + ":" + n.nama + ") "); inorder(n.right); }
    }

    private void preorder(Node n) {
        if (n != null) { System.out.print("(" + n.id + ":" + n.nama + ") "); preorder(n.left); preorder(n.right); }
    }

    private void postorder(Node n) {
        if (n != null) { postorder(n.left); postorder(n.right); System.out.print("(" + n.id + ":" + n.nama + ") "); }
    }
}

// 3. Main Program
public class BSTProgram {
    public static void main(String[] args) {
        BST tree = new BST();
        Scanner scanner = new Scanner(System.in);
        String fileName = "data100.csv";

        // PROSES IMPORT DATA
        System.out.println("=== MEMBACA DATA DARI " + fileName.toUpperCase() + " ===");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            int count = 0;

            while ((line = br.readLine()) != null) {
                // Menghindari baris kosong
                if (line.trim().isEmpty()) continue;

                // Mengabaikan header jika baris pertama bukan angka
                String[] data = line.split(",");
                try {
                    int id = Integer.parseInt(data[0].trim());
                    String nama = data[1].trim();
                    tree.insert(id, nama);
                    count++;
                } catch (NumberFormatException e) {
                    if (!isFirstLine) System.out.println("Skip baris invalid: " + line);
                }
                isFirstLine = false;
            }
            System.out.println("Berhasil mengimpor " + count + " data ke dalam BST.");
        } catch (IOException e) {
            System.out.println("Error: File " + fileName + " tidak ditemukan!");
            System.out.println("Pastikan file berada di folder yang sama dengan kode ini.");
            return;
        }

        // MENU UTAMA
        while (true) {
            System.out.println("\n--- SISTEM MANAJEMEN DATA BST ---");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data (ID)");
            System.out.println("3. Hapus Data (ID)");
            System.out.println("4. Tampilkan Semua Traversal");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Masukkan ID: "); int id = scanner.nextInt();
                    System.out.print("Masukkan Nama: "); String nama = scanner.next();
                    tree.insert(id, nama);
                    System.out.println("Data berhasil ditambahkan.");
                }
                case 2 -> {
                    System.out.print("Masukkan ID yang dicari: "); int id = scanner.nextInt();
                    Node res = tree.search(id);
                    System.out.println(res != null ? "Ditemukan: " + res.nama : "ID tidak ditemukan.");
                }
                case 3 -> {
                    System.out.print("Masukkan ID yang akan dihapus: "); int id = scanner.nextInt();
                    tree.delete(id);
                    System.out.println("Proses hapus selesai.");
                }
                case 4 -> {
                    tree.displayTraversal(1);
                    tree.displayTraversal(2);
                    tree.displayTraversal(3);
                }
                case 5 -> {
                    System.out.println("Program selesai.");
                    System.exit(0);
                }
                default -> System.out.println("Opsi tidak valid.");
            }
        }
    }
}