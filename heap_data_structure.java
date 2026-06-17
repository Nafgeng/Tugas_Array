import java.io.File;
import java.util.*;

class Barang implements Comparable<Barang> {
    int id;
    String nama;

    public Barang(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    @Override
    public int compareTo(Barang other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Nama: %s", id, nama);
    }
}

public class heap_data_structure {
    private PriorityQueue<Barang> minHeap = new PriorityQueue<>();
    private PriorityQueue<Barang> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    // (1) Tambah data ke kedua heap sekaligus
    public void fitur1_tambahData(int id, String nama) {
        Barang baru = new Barang(id, nama);
        minHeap.add(baru);
        maxHeap.add(baru);
    }

    // (2) Tampil Ascending
    public void fitur2_tampilkanAscending() {
        if(minHeap.isEmpty()) { System.out.println("Heap kosong!"); return; }
        PriorityQueue<Barang> temp = new PriorityQueue<>(minHeap);
        while (!temp.isEmpty()) System.out.println(temp.poll());
    }

    // (3) Tampil Descending
    public void fitur3_tampilkanDescending() {
        if(maxHeap.isEmpty()) { System.out.println("Heap kosong!"); return; }
        PriorityQueue<Barang> temp = new PriorityQueue<>(maxHeap);
        while (!temp.isEmpty()) System.out.println(temp.poll());
    }

    // (4) Hapus Root Min-Heap
    public void fitur4_hapusMin() {
        if (!minHeap.isEmpty()) {
            Barang b = minHeap.poll();
            maxHeap.remove(b); // Biar sinkron
            System.out.println("Berhasil hapus Min-Heap: " + b);
        } else System.out.println("Min-Heap kosong!");
    }

    // (5) Hapus Root Max-Heap
    public void fitur5_hapusMax() {
        if (!maxHeap.isEmpty()) {
            Barang b = maxHeap.poll();
            minHeap.remove(b); // Biar sinkron
            System.out.println("Berhasil hapus Max-Heap: " + b);
        } else System.out.println("Max-Heap kosong!");
    }

    // (6) Load data awal (Source 1, 2, 3)
    public void fitur6_loadData(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p.length >= 2) {
                    fitur1_tambahData(Integer.parseInt(p[0].replace("\"", "").trim()), p[1].replace("\"", "").trim());
                }
            }
            System.out.println("Sistem: Data awal berhasil dimuat.");
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        heap_data_structure app = new heap_data_structure();
        app.fitur6_loadData("data100.csv");
        Scanner input = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== MENU HEAP JAVA ===");
            System.out.println("1. Tambah Data (Manual)");
            System.out.println("2. Tampil Ascending (Min-Heap)");
            System.out.println("3. Tampil Descending (Max-Heap)");
            System.out.println("4. Hapus Min-Heap (Root)");
            System.out.println("5. Hapus Max-Heap (Root)");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            int pil = input.nextInt();

            if (pil == 1) {
                System.out.print("ID: "); int id = input.nextInt();
                System.out.print("Nama: "); String nm = input.next();
                app.fitur1_tambahData(id, nm);
            } else if (pil == 2) app.fitur2_tampilkanAscending();
            else if (pil == 3) app.fitur3_tampilkanDescending();
            else if (pil == 4) app.fitur4_hapusMin();
            else if (pil == 5) app.fitur5_hapusMax();
            else if (pil == 0) break;
        }
    }
}