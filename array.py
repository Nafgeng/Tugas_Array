class Mahasiswa:
    def __init__(self, nim, nama):
        self.nim = nim
        self.nama = nama

    def __str__(self):
        return f"[{self.nim}] {self.nama}"

def show_data(arr, size):
    if size == 0:
        print("Array kosong.")
    else:
        for i in range(size):
            print(f"{i+1}. {arr[i]}")

def main():
    capacity = 10
    arr = [None] * capacity
    size = 0

    while True:
        print("\n--- MENU MAHASISWA (Kapasitas: 10) ---")
        print("1. Insert at beginning\n2. Insert at position\n3. Insert at end")
        print("4. Delete from beginning\n5. Delete position\n6. Delete from end")
        print("7. Delete first occurrence (by NIM)\n8. Show data\n9. Exit")
        
        choice = input("Pilih menu: ")

        if choice in ['1', '2', '3']:
            if size >= capacity:
                print("Error: Array Penuh!")
                continue
            nim = input("NIM: ")
            nama = input("Nama: ")
            new_mhs = Mahasiswa(nim, nama)

            if choice == '1':
                for i in range(size, 0, -1):
                    arr[i] = arr[i-1]
                arr[0] = new_mhs
                size += 1
            elif choice == '2':
                pos = int(input(f"Posisi (1-{size+1}): ")) - 1
                if 0 <= pos <= size:
                    for i in range(size, pos, -1):
                        arr[i] = arr[i-1]
                    arr[pos] = new_mhs
                    size += 1
                else: print("Posisi tidak valid!")
            elif choice == '3':
                arr[size] = new_mhs
                size += 1

        elif choice in ['4', '5', '6', '7']:
            if size == 0:
                print("Error: Array Kosong!")
                continue
            
            idx_to_del = -1
            if choice == '4': idx_to_del = 0
            elif choice == '6': idx_to_del = size - 1
            elif choice == '5':
                idx_to_del = int(input(f"Hapus posisi (1-{size}): ")) - 1
            elif choice == '7':
                target = input("Masukkan NIM yang ingin dihapus: ")
                for i in range(size):
                    if arr[i].nim == target:
                        idx_to_del = i
                        break
                if idx_to_del == -1: print("Data tidak ditemukan!")

            if idx_to_del != -1 and 0 <= idx_to_del < size:
                for i in range(idx_to_del, size - 1):
                    arr[i] = arr[i+1]
                arr[size-1] = None
                size -= 1
                print("Data berhasil dihapus.")

        elif choice == '8':
            show_data(arr, size)
        elif choice == '9':
            break

main()