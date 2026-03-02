import java.util.Scanner;
import java.io.IOException;

class Node {
    String data;
    Node next, prev;
    Node(String data) { this.data = data; }
}

class NewsTicker {
    Node head = null;
    int size = 0;

    void insertNews(String news) {
        Node newNode = new Node(news);
        if (head == null) {
            head = newNode;
            head.next = head.prev = head;
        } else {
            Node tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
        System.out.println("[Sistem] Berita berhasil ditambahkan.");
    }

    void deleteNews(int pos) {
        if (head == null || pos < 1 || pos > size) {
            System.out.println("[Sistem] Nomor urut tidak valid!");
            return;
        }
        Node curr = head;
        for (int i = 1; i < pos; i++) curr = curr.next;
        if (size == 1) head = null;
        else {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
            if (curr == head) head = curr.next;
        }
        size--;
        System.out.println("[Sistem] Berita berhasil dihapus.");
    }

    // FITUR UTAMA: Berhenti tanpa mematikan program
    void runTicker(boolean forward) {
        if (head == null) {
            System.out.println("[Sistem] Daftar berita kosong.");
            return;
        }
        
        System.out.println("\n=== SIMULASI TEXT BERJALAN ===");
        System.out.println(">>> Tekan ENTER untuk kembali ke menu utama <<<");
        
        Node curr = forward ? head : head.prev;
        
        try {
            // Selama tidak ada tombol yang ditekan (System.in.available == 0)
            while (System.in.available() == 0) {
                System.out.print("\r>>> NEWS: " + curr.data + " <<<          ");
                Thread.sleep(3000); 
                curr = forward ? curr.next : curr.prev;
            }
            // Membersihkan sisa input agar tidak mengganggu menu
            System.in.read(); 
        } catch (Exception e) {
            System.out.println("[Sistem] Terjadi interupsi.");
        }
        System.out.println("\n[Sistem] Berhenti. Kembali ke menu utama...");
    }

    void displaySpecific(int pos) {
        if (head == null || pos < 1 || pos > size) {
            System.out.println("[Sistem] Berita tidak ditemukan!");
            return;
        }
        Node curr = head;
        for (int i = 1; i < pos; i++) curr = curr.next;
        System.out.println("\n[Sistem] Berita No." + pos + ": " + curr.data);
    }
}

public class Circular_Doubly_Linked_List {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NewsTicker nt = new NewsTicker();
        int pilihan = 0;

        while (pilihan != 6) {
            System.out.println("\n===== MENU NEWS TICKER =====");
            System.out.println("1. Insert Berita\n2. Hapus Berita\n3. Tampilkan Forward (Loop)\n4. Tampilkan Backward (Loop)\n5. Berita Spesifik\n6. Exit");
            System.out.print("Pilih Menu: ");
            try {
                pilihan = sc.nextInt();
                sc.nextLine(); 
                switch (pilihan) {
                    case 1: System.out.print("Teks: "); nt.insertNews(sc.nextLine()); break;
                    case 2: System.out.print("Hapus No: "); nt.deleteNews(sc.nextInt()); break;
                    case 3: nt.runTicker(true); break;
                    case 4: nt.runTicker(false); break;
                    case 5: System.out.print("No Berita: "); nt.displaySpecific(sc.nextInt()); break;
                }
            } catch (Exception e) {
                System.out.println("[Error] Input tidak valid.");
                sc.nextLine();
            }
        }
        sc.close();
    }
}