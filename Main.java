import java.util.Scanner;

class Mahasiswa {
    String nim, nama;
    Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }
    public String toString() { return "[" + nim + "] " + nama; }
}

public class Main {
    static Mahasiswa[] arr = new Mahasiswa[10];
    static int size = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- MENU (Size: " + size + "/10) ---");
            System.out.print("1-3: Insert (Beg, Pos, End) | 4-7: Delete (Beg, Pos, End, First Occ) | 8: Show | 9: Exit\nPilihan: ");
            int choice = sc.nextInt();
            if (choice == 9) break;

            if (choice >= 1 && choice <= 3) {
                if (size >= 10) { System.out.println("Penuh!"); continue; }
                System.out.print("NIM: "); String nim = sc.next();
                System.out.print("Nama: "); String nama = sc.next();
                Mahasiswa m = new Mahasiswa(nim, nama);

                if (choice == 1) insertAt(0, m);
                else if (choice == 3) insertAt(size, m);
                else {
                    System.out.print("Posisi (1-" + (size+1) + "): ");
                    insertAt(sc.nextInt() - 1, m);
                }
            } else if (choice >= 4 && choice <= 7) {
                if (size == 0) { System.out.println("Kosong!"); continue; }
                int idx = -1;
                if (choice == 4) idx = 0;
                else if (choice == 6) idx = size - 1;
                else if (choice == 5) { System.out.print("Posisi: "); idx = sc.nextInt() - 1; }
                else {
                    System.out.print("Cari NIM: "); String target = sc.next();
                    for(int i=0; i<size; i++) if(arr[i].nim.equals(target)) { idx = i; break; }
                }
                deleteAt(idx);
            } else if (choice == 8) {
                for(int i=0; i<size; i++) System.out.println((i+1) + ". " + arr[i]);
            }
        }
    }

    static void insertAt(int pos, Mahasiswa m) {
        if (pos < 0 || pos > size) return;
        for (int i = size; i > pos; i--) arr[i] = arr[i-1];
        arr[pos] = m;
        size++;
    }

    static void deleteAt(int pos) {
        if (pos < 0 || pos >= size) return;
        for (int i = pos; i < size - 1; i++) arr[i] = arr[i+1];
        arr[--size] = null;
    }
}