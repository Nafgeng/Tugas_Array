class Mahasiswa:
    def __init__(self, nim, nama):
        self.nim = nim
        self.nama = nama

    def __str__(self):
        return f"NIM: {self.nim}, Nama: {self.nama}"

def main():
    kapasitas = 10
    # Inisialisasi array dengan kapasitas tetap
    array_mhs = [None] * kapasitas
    count = 0

    while True:
        print(f"\n--- Menu Mahasiswa (Data: {count}/{kapasitas}) ---")
        print("1. Insert at beginning\n2. Insert at given position\n3. Insert at end")
        print("4. Delete from beginning\n5. Delete given position\n6. Delete from end")
        print("7. Delete first occurrence (by NIM)\n8. Show data\n9. Exit")
        
        pilihan = input("Pilih menu: ")

        # --- OPERASI INSERT ---
        if pilihan in ['1', '2', '3']:
            if count >= kapasitas:
                print("Array sudah penuh!")
                continue
            
            nim = input("Masukkan NIM: ")
            nama = input("Masukkan Nama: ")
            mhs_baru = Mahasiswa(nim, nama)

            if pilihan == '1': # Beginning
                for i in range(count, 0, -1):
                    array_mhs[i] = array_mhs[i-1]
                array_mhs[0] = mhs_baru
                count += 1
            
            elif pilihan == '2': # Position
                pos = int(input(f"Pilih posisi (0-{count}): "))
                if 0 <= pos <= count:
                    for i in range(count, pos, -1):
                        array_mhs[i] = array_mhs[i-1]
                    array_mhs[pos] = mhs_baru
                    count += 1
                else:
                    print("Posisi di luar jangkauan!")

            elif pilihan == '3': # End
                array_mhs[count] = mhs_baru
                count += 1

        # --- OPERASI DELETE ---
        elif pilihan in ['4', '5', '6', '7']:
            if count == 0:
                print("Array kosong!")
                continue
            
            idx_hapus = -1
            if pilihan == '4': # Beginning
                idx_hapus = 0
            elif pilihan == '6': # End
                idx_hapus = count - 1
            elif pilihan == '5': # Position
                idx_hapus = int(input(f"Pilih posisi yang dihapus (0-{count-1}): "))
            elif pilihan == '7': # First Occurrence
                target = input("Masukkan NIM yang dicari: ")
                for i in range(count):
                    if array_mhs[i].nim == target:
                        idx_hapus = i
                        break
            
            if 0 <= idx_hapus < count:
                # Shifting ke kiri
                for i in range(idx_hapus, count - 1):
                    array_mhs[i] = array_mhs[i+1]
                array_mhs[count - 1] = None
                count -= 1
                print("Data berhasil dihapus.")
            else:
                print("Indeks tidak ditemukan.")

        elif pilihan == '8':
            if count == 0:
                print("Data kosong.")
            else:
                for i in range(count):
                    print(f"[{i}] {array_mhs[i]}")
        
        elif pilihan == '9':
            break

if __name__ == "__main__":
    main()