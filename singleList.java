import java.util.Scanner;

// Struktur Node Mahasiswa
class Node {
    String nim, nama;
    Node next;

    Node(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
        this.next = null; // Awalnya tidak menunjuk ke mana pun
    }
}

class LinkedList {
    Node head;        // Penunjuk awal list
    int count = 0;    // NB: Variabel count untuk melacak jumlah data

    // 1. Insert at Beginning
    void insertBeginning(String nim, String nama) {
        Node newNode = new Node(nim, nama);
        newNode.next = head; // Node baru menunjuk ke head yang lama
        head = newNode;      // Head berpindah ke node baru
        count++;             // NB 1: Setiap insert, count +1
    }

    // 2. Insert at Given Position
    void insertAtPos(String nim, String nama, int pos) {
        if (pos < 1 || pos > count + 1) {
            System.out.println("Posisi tidak valid!");
            return;
        }
        if (pos == 1) {
            insertBeginning(nim, nama);
            return;
        }
        Node newNode = new Node(nim, nama);
        Node temp = head;
        // Cari node sebelum posisi target
        for (int i = 1; i < pos - 1; i++) temp = temp.next;
        
        newNode.next = temp.next;
        temp.next = newNode;
        count++; // NB 1: Setiap insert, count +1
    }

    // 3. Insert at End
    void insertEnd(String nim, String nama) {
        Node newNode = new Node(nim, nama);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) temp = temp.next; // Cari ujung list
            temp.next = newNode;
        }
        count++; // NB 1: Setiap insert, count +1
    }

    // 4. Delete from Beginning
    void deleteBeginning() {
        if (head == null) return;
        head = head.next; // Lepaskan node pertama
        count--;          // NB 2: Setiap delete, count -1
    }

    // 5. Delete Given Position
    void deleteAtPos(int pos) {
        if (pos < 1 || pos > count) {
            System.out.println("Posisi tidak valid!");
            return;
        }
        if (pos == 1) {
            deleteBeginning();
            return;
        }
        Node temp = head;
        for (int i = 1; i < pos - 1; i++) temp = temp.next;
        temp.next = temp.next.next; // Melompati node yang dihapus
        count--; // NB 2: Setiap delete, count -1
    }

    // 6. Delete from End
    void deleteEnd() {
        if (head == null) return;
        if (head.next == null) {
            head = null;
        } else {
            Node temp = head;
            while (temp.next.next != null) temp = temp.next;
            temp.next = null;
        }
        count--; // NB 2: Setiap delete, count -1
    }

    // 7. Delete First Occurrence (by NIM)
    void deleteByNim(String nim) {
        Node temp = head, prev = null;
        while (temp != null) {
            if (temp.nim.equals(nim)) {
                if (prev != null) prev.next = temp.next;
                else head = temp.next;
                count--; // NB 2: Setiap delete, count -1
                System.out.println("Data NIM " + nim + " berhasil dihapus.");
                return;
            }
            prev = temp;
            temp = temp.next;
        }
        System.out.println("NIM tidak ditemukan.");
    }

    // 8. Show Data
    void show() {
        Node temp = head;
        System.out.println("\n--- DATA MAHASISWA (Total: " + count + ") ---");
        while (temp != null) {
            System.out.print("[" + temp.nim + " | " + temp.nama + "] -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }
}

public class singleList {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        Scanner sc = new Scanner(System.in);
        int menu;

        do {
            // Menampilkan jumlah data saat ini secara real-time di atas menu
            System.out.println("\n[ Jumlah Mahasiswa Terdaftar: " + list.count + " ]");
            System.out.println("--- MENU LINKED LIST MAHASISWA ---");
            System.out.println("1-3: Insert, 4-6: Delete, 7: Del by NIM, 8: Show, 9: Exit");
            System.out.print("Pilih Menu (1-9): ");
            
            menu = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch(menu) {
                case 1:
                    System.out.print("NIM: "); String nim1 = sc.nextLine();
                    System.out.print("Nama: "); String nama1 = sc.nextLine();
                    list.insertBeginning(nim1, nama1);
                    break;
                case 2:
                    System.out.print("NIM: "); String nim2 = sc.nextLine();
                    System.out.print("Nama: "); String nama2 = sc.nextLine();
                    System.out.print("Posisi: "); int p2 = sc.nextInt();
                    list.insertAtPos(nim2, nama2, p2);
                    break;
                case 3:
                    System.out.print("NIM: "); String nim3 = sc.nextLine();
                    System.out.print("Nama: "); String nama3 = sc.nextLine();
                    list.insertEnd(nim3, nama3);
                    break;
                case 4: list.deleteBeginning(); break;
                case 5: 
                    System.out.print("Hapus posisi ke: "); 
                    list.deleteAtPos(sc.nextInt()); 
                    break;
                case 6: list.deleteEnd(); break;
                case 7: 
                    System.out.print("Masukkan NIM: "); 
                    list.deleteByNim(sc.nextLine()); 
                    break;
                case 8: list.show(); break;
            }
        } while (menu != 9);
        System.out.println("Program Selesai.");
    }
}