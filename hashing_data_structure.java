import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class hashing_data_structure { 
    
    // Node untuk Separate Chaining (Linked List)
    static class Node {
        int key;
        Node next;
        
        Node(int key) {
            this.key = key;
            this.next = null;
        }
    }
    
    static class HashTable {
        private int size;
        private Node[] table;
        
        public HashTable(int size) {
            this.size = size;
            this.table = new Node[size];
        }
        
        private int hashFunction(int key) {
            return Math.abs(key) % size;
        }
        
        // 1. INPUT DATA
        public boolean insert(int key) {
            int index = hashFunction(key);
            Node current = table[index];
            
            // Cek duplikasi
            while (current != null) {
                if (current.key == key) {
                    return false; 
                }
                current = current.next;
            }
            
            // Insert ke depan (Head)
            Node newNode = new Node(key);
            newNode.next = table[index];
            table[index] = newNode;
            return true;
        }
        
        // 2. CARI DATA
        public boolean search(int key) {
            int index = hashFunction(key);
            Node current = table[index];
            while (current != null) {
                if (current.key == key) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        
        // 3. HAPUS DATA
        public boolean delete(int key) {
            int index = hashFunction(key);
            Node current = table[index];
            Node prev = null;
            
            while (current != null) {
                if (current.key == key) {
                    if (prev != null) {
                        prev.next = current.next;
                    } else {
                        table[index] = current.next;
                    }
                    return true;
                }
                prev = current;
                current = current.next;
            }
            return false;
        }
        
        // 4. LIHAT SEMUA DATA
        public void printAllData() {
            System.out.println("\n================ ISINYA HASH TABLE (100 DATA) ================");
            int totalData = 0;
            for (int i = 0; i < size; i++) {
                if (table[i] != null) { 
                    System.out.print(String.format("Indeks %3d -> ", i));
                    Node current = table[i];
                    while (current != null) {
                        System.out.print("[" + current.key + "] -> ");
                        current = current.next;
                        totalData++;
                    }
                    System.out.println("null");
                }
            }
            System.out.println("=============================================================");
            System.out.println("Total data tersimpan saat ini: " + totalData + " angka.");
        }
    }

    public static void main(String[] args) {
        HashTable ht = new HashTable(150);
        Random rand = new Random();
        HashSet<Integer> randomNumbers = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        
        // Generate 100 angka acak unik di awal program
        while (randomNumbers.size() < 100) {
            int num = rand.nextInt(900) + 100; // Angka kisaran 100 - 999
            randomNumbers.add(num);
        }
        
        // Masukkan semua data acak tersebut ke hash table
        for (int num : randomNumbers) {
            ht.insert(num);
        }
        
        System.out.println("Berhasil menginisialisasi awal dengan " + randomNumbers.size() + " angka random unik.");
        
        while (true) {
            System.out.println("\n=== MENU HASH TABLE ===");
            System.out.println("1. INPUT DATA");
            System.out.println("2. HAPUS DATA");
            System.out.println("3. CARI DATA");
            System.out.println("4. LIHAT SEMUA DATA");
            System.out.println("5. KELUAR");
            System.out.print("Pilih menu (1-5): ");
            
            String pilihan = scanner.nextLine();
            
            if (pilihan.equals("1")) {
                System.out.print("Masukkan angka baru yang ingin diinput: ");
                try {
                    int data = Integer.parseInt(scanner.nextLine());
                    if (ht.insert(data)) {
                        System.out.println("Data " + data + " berhasil ditambahkan.");
                    } else {
                        System.out.println("Data " + data + " sudah ada di dalam Hash Table.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka numerik!");
                }
                
            } else if (pilihan.equals("2")) {
                System.out.print("Masukkan angka yang ingin dihapus: ");
                try {
                    int data = Integer.parseInt(scanner.nextLine());
                    if (ht.delete(data)) {
                        System.out.println("Data " + data + " berhasil dihapus.");
                    } else {
                        System.out.println("Data " + data + " tidak ditemukan.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka numerik!");
                }
                
            } else if (pilihan.equals("3")) {
                System.out.print("Masukkan angka yang ingin dicari: ");
                try {
                    int data = Integer.parseInt(scanner.nextLine());
                    if (ht.search(data)) {
                        System.out.println("Data " + data + " DITEMUKAN di dalam Hash Table.");
                    } else {
                        System.out.println("Data " + data + " TIDAK DITEMUKAN.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka numerik!");
                }
                
            } else if (pilihan.equals("4")) {
                ht.printAllData();
                
            } else if (pilihan.equals("5")) {
                System.out.println("Program selesai. Terima kasih!");
                break;
            } else {
                System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
        scanner.close();
    }
}