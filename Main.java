import java.util.Scanner;

class Mahasiswa {
    String nim, nama;
    Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }
    public String toString() {
        return "NIM: " + nim + ", Nama: " + nama;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int kapasitas = 10;
        Mahasiswa[] arrayMhs = new Mahasiswa[kapasitas];
        int count = 0;

        while (true) {
            System.out.println("\n--- MENU (Count: " + count + ") ---");
            System.out.println("1. Insert Beg | 2. Insert Pos | 3. Insert End");
            System.out.println("4. Delete Beg | 5. Delete Pos | 6. Delete End");
            System.out.println("7. Delete First Occ | 8. Show | 9. Exit");
            System.out.print("Pilih: ");
            int menu = sc.nextInt();

            if (menu == 9) break;

            // Logika Insert
            if (menu >= 1 && menu <= 3) {
                if (count >= kapasitas) {
                    System.out.println("Array Penuh!");
                    continue;
                }
                System.out.print("NIM: "); String nim = sc.next();
                System.out.print("Nama: "); String nama = sc.next();
                Mahasiswa m = new Mahasiswa(nim, nama);

                int pos = (menu == 1) ? 0 : (menu == 3) ? count : -1;
                if (menu == 2) {
                    System.out.print("Posisi (0-" + count + "): ");
                    pos = sc.nextInt();
                }

                if (pos >= 0 && pos <= count) {
                    for (int i = count; i > pos; i--) arrayMhs[i] = arrayMhs[i-1];
                    arrayMhs[pos] = m;
                    count++;
                }
            } 
            // Logika Delete
            else if (menu >= 4 && menu <= 7) {
                if (count == 0) {
                    System.out.println("Kosong!");
                    continue;
                }
                int idx = -1;
                if (menu == 4) idx = 0;
                else if (menu == 6) idx = count - 1;
                else if (menu == 5) {
                    System.out.print("Hapus posisi: "); idx = sc.nextInt();
                } else {
                    System.out.print("Cari NIM: "); String target = sc.next();
                    for(int i=0; i<count; i++) {
                        if(arrayMhs[i].nim.equals(target)) { idx = i; break; }
                    }
                }

                if (idx >= 0 && idx < count) {
                    for (int i = idx; i < count - 1; i++) arrayMhs[i] = arrayMhs[i+1];
                    arrayMhs[--count] = null;
                }
            } else if (menu == 8) {
                for (int i = 0; i < count; i++) System.out.println(i + ". " + arrayMhs[i]);
            }
        }
        sc.close();
    }
}