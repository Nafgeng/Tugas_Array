# Definisi struktur Node (Gerbong Data)
class Node:
    def __init__(self, nim, nama):
        self.nim = nim      # Menyimpan data NIM
        self.nama = nama    # Menyimpan data Nama
        self.next = None    # Pointer ke node berikutnya (default: None)

class LinkedList:
    def __init__(self):
        self.head = None    # Awal mula list (kosong)
        self.count = 0      # NB: Variabel pelacak jumlah data

    # 1. Insert at Beginning (Data di depan)
    def insert_beginning(self, nim, nama):
        new_node = Node(nim, nama)
        new_node.next = self.head  # Node baru menunjuk ke head yang lama
        self.head = new_node       # Head pindah ke node baru
        self.count += 1            # NB 1: Insert, count +1

    # 2. Insert at Given Position (Posisi 1 sampai count+1)
    def insert_at_pos(self, nim, nama, pos):
        # Validasi apakah posisi masuk akal
        if pos < 1 or pos > self.count + 1:
            print("Posisi tidak valid!")
            return
        if pos == 1:
            self.insert_beginning(nim, nama)
            return
        
        new_node = Node(nim, nama)
        temp = self.head
        # Berjalan ke node tepat sebelum posisi tujuan
        for _ in range(pos - 2):
            temp = temp.next
        new_node.next = temp.next
        temp.next = new_node
        self.count += 1            # NB 1: Insert, count +1

    # 3. Insert at End (Data di akhir)
    def insert_end(self, nim, nama):
        new_node = Node(nim, nama)
        if not self.head:          # Jika list kosong
            self.head = new_node
        else:
            temp = self.head
            while temp.next:       # Cari node terakhir yang next-nya None
                temp = temp.next
            temp.next = new_node
        self.count += 1            # NB 1: Insert, count +1

    # 4. Delete from Beginning (Hapus depan)
    def delete_beginning(self):
        if not self.head: 
            print("List kosong!")
            return
        self.head = self.head.next # Head pindah ke node setelahnya
        self.count -= 1            # NB 2: Delete, count -1

    # 5. Delete Given Position (Posisi 1 sampai count)
    def delete_at_pos(self, pos):
        if pos < 1 or pos > self.count:
            print("Posisi tidak valid!")
            return
        if pos == 1:
            self.delete_beginning()
            return
        
        temp = self.head
        for _ in range(pos - 2):   # Cari node sebelum posisi target
            temp = temp.next
        temp.next = temp.next.next # Putus hubungan dengan node target
        self.count -= 1            # NB 2: Delete, count -1

    # 6. Delete from End (Hapus terakhir)
    def delete_end(self):
        if not self.head: return
        if not self.head.next:     # Jika hanya ada satu node
            self.head = None
        else:
            temp = self.head
            while temp.next.next:  # Berhenti di node sebelum terakhir
                temp = temp.next
            temp.next = None
        self.count -= 1            # NB 2: Delete, count -1

    # 7. Delete First Occurrence (Berdasarkan NIM)
    def delete_by_nim(self, nim):
        temp = self.head
        prev = None
        while temp:
            if temp.nim == nim:
                if prev:
                    prev.next = temp.next # Hubungkan node sebelumnya ke sesudahnya
                else:
                    self.head = temp.next # Jika yang dihapus adalah head
                self.count -= 1    # NB 2: Delete, count -1
                print(f"Data {nim} berhasil dihapus.")
                return
            prev = temp
            temp = temp.next
        print("NIM tidak ditemukan.")

    # 8. Show Data
    def show(self):
        temp = self.head
        print(f"\n--- DATA MAHASISWA (Jumlah: {self.count}) ---")
        if not temp:
            print("List Kosong")
            return
        while temp:
            print(f"[{temp.nim} | {temp.nama}]", end=" -> ")
            temp = temp.next
        print("None")

# Fungsi Main / Menu
# ... (kode class Node dan LinkedList tetap sama) ...

ll = LinkedList()
while True:
    # Menampilkan jumlah data saat ini secara real-time di atas menu
    print(f"\n[ Jumlah Mahasiswa Terdaftar: {ll.count} ]") 
    print("--- MENU LINKED LIST MAHASISWA ---")
    print("1. Insert Beg  2. Insert Pos  3. Insert End")
    print("4. Del Beg     5. Del Pos     6. Del End")
    print("7. Del by NIM  8. Show Data   9. Exit")
    
    pilihan = input("Pilih Menu (1-9): ")
    # ... (sisanya tetap sama) ...
    
    if pilihan == '1':
        ll.insert_beginning(input("NIM: "), input("Nama: "))
    elif pilihan == '2':
        nim, nama = input("NIM: "), input("Nama: ")
        pos = int(input(f"Posisi (1-{ll.count+1}): "))
        ll.insert_at_pos(nim, nama, pos)
    elif pilihan == '3':
        ll.insert_end(input("NIM: "), input("Nama: "))
    elif pilihan == '4':
        ll.delete_beginning()
    elif pilihan == '5':
        pos = int(input(f"Posisi dihapus (1-{ll.count}): "))
        ll.delete_at_pos(pos)
    elif pilihan == '6':
        ll.delete_end()
    elif pilihan == '7':
        ll.delete_by_nim(input("Masukkan NIM yang ingin dihapus: "))
    elif pilihan == '8':
        ll.show()
    elif pilihan == '9':
        print("Program Selesai.")
        break