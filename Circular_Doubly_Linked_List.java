import java.util.Scanner;

// Struktur Node untuk menyimpan data berita dan pointer dua arah
class Node {
    String data;
    Node next, prev;

    Node(String data) {
        this.data = data;
    }
}

class NewsTicker {
    Node head = null;
    int size = 0;

    // 1. Insert Berita (Ditambahkan di akhir)
    void insertNews(String news) {
        Node newNode = new Node(news);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
        System.out.println("Berita berhasil ditambahkan!");
    }

    // 2. Hapus Berita (Berdasarkan nomor urut)
    void deleteNews(int pos) {
        if (head == null || pos < 1 || pos > size) {
            System.out.println("Posisi tidak valid!");
            return;
        }
        Node curr = head;
        for (int i = 1; i < pos; i++) curr = curr.next;

        if (size == 1) {
            head = null;
        } else {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
            if (curr == head) head = curr.next;
        }
        size--;
        System.out.println("Berita berhasil dihapus.");
    }

    // 3 & 4. Tampilkan Berita (Forward/Backward dengan delay 3 detik)
    void display(boolean forward) {
        if (head == null) {
            System.out.println("Daftar berita kosong.");
            return;
        }
        Node curr = forward ? head : head.prev;
        System.out.println(forward ? "\n--- MENGALIR KE DEPAN ---" : "\n--- MENGALIR KE BELAKANG ---");
        
        for (int i = 0; i < size; i++) {
            System.out.println(">>> " + curr.data + " <<<");
            try {
                // Delay 3 detik sesuai permintaan soal
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                System.out.println("Terjadi kesalahan pada delay.");
            }
            curr = forward ? curr.next : curr.prev;
        }
    }

    // 5. Tampil Berita Tertentu
    void displaySpecific(int pos) {
        if (head == null || pos < 1 || pos > size) {
            System.out.println("Berita tidak ditemukan!");
            return;
        }
        Node curr = head;
        for (int i = 1; i < pos; i++) curr = curr.next;
        System.out.println("Berita No." + pos + ": " + curr.data);
    }
}

// NAMA CLASS HARUS SAMA DENGAN NAMA FILE
public class Circular_Doubly_Linked_List {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NewsTicker ticker = new NewsTicker();
        int pilihan = 0;

        while (pilihan != 6) {
            System.out.println("\n--- MENU NEWS TICKER ---");
            System.out.println("1. Insert Berita");
            System.out.println("2. Hapus Berita");
            System.out.println("3. Tampilkan Forward");
            System.out.println("4. Tampilkan Backward");
            System.out.println("5. Tampilkan Berita Spesifik");
            System.out.println("6. Exit");
            System.out.print("Pilih menu: ");
            
            try {
                pilihan = sc.nextInt();
                sc.nextLine(); // Membersihkan buffer enter

                switch (pilihan) {
                    case 1:
                        System.out.print("Masukkan teks berita: ");
                        ticker.insertNews(sc.nextLine());
                        break;
                    case 2:
                        System.out.print("Masukkan nomor urut berita yang akan dihapus: ");
                        ticker.deleteNews(sc.nextInt());
                        break;
                    case 3:
                        ticker.display(true);
                        break;
                    case 4:
                        ticker.display(false);
                        break;
                    case 5:
                        System.out.print("Masukkan nomor urut berita: ");
                        ticker.displaySpecific(sc.nextInt());
                        break;
                    case 6:
                        System.out.println("Keluar program...");
                        break;
                    default:
                        System.out.println("Pilihan tidak tersedia.");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                sc.nextLine(); // Reset scanner jika input salah
            }
        }
        sc.close();
    }
}