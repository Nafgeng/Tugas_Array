import heapq
import pandas as pd
import os

class HeapDataStructure:
    def __init__(self):
        # Inisialisasi struktur data heap
        self.min_heap = []
        self.max_heap = []

    # (1) FITUR: Tambah data ke dalam min-heap dan max-heap sekaligus
    def fitur1_tambah_data(self, item_id, nama):
        heapq.heappush(self.min_heap, (item_id, nama))
        heapq.heappush(self.max_heap, (-item_id, nama))

    # (2) FITUR: Tampilkan urut ascending menggunakan Min-Heap
    def fitur2_tampilkan_ascending(self):
        if not self.min_heap:
            print("\n[!] Data kosong.")
            return
        print("\n--- [Fitur 2] Urutan Ascending (Min-Heap) ---")
        temp = list(self.min_heap)
        heapq.heapify(temp)
        while temp:
            id_val, nama = heapq.heappop(temp)
            print(f"ID: {id_val:<5} | Nama: {nama}")

    # (3) FITUR: Tampilkan urut descending menggunakan Max-Heap
    def fitur3_tampilkan_descending(self):
        if not self.max_heap:
            print("\n[!] Data kosong.")
            return
        print("\n--- [Fitur 3] Urutan Descending (Max-Heap) ---")
        temp = list(self.max_heap)
        heapq.heapify(temp)
        while temp:
            neg_id, nama = heapq.heappop(temp)
            print(f"ID: {-neg_id:<5} | Nama: {nama}")

    # (4) FITUR: Hapus data terkecil (Root Min-Heap)
    def fitur4_hapus_min(self):
        if self.min_heap:
            # Ambil data terkecil
            removed = heapq.heappop(self.min_heap)
            # Sinkronisasi: Hapus juga dari max_heap supaya beneran ilang
            self.max_heap = [item for item in self.max_heap if -item[0] != removed[0]]
            heapq.heapify(self.max_heap)
            print(f"\n[Fitur 4] Berhasil menghapus Root Min-Heap: {removed}")
        else:
            print("\n[!] Min-Heap sudah kosong.")

    # (5) FITUR: Hapus data terbesar (Root Max-Heap)
    def fitur5_hapus_max(self):
        if self.max_heap:
            # Ambil data terbesar
            neg_id, nama = heapq.heappop(self.max_heap)
            target_id = -neg_id
            # Sinkronisasi: Hapus juga dari min_heap supaya beneran ilang
            self.min_heap = [item for item in self.min_heap if item[0] != target_id]
            heapq.heapify(self.min_heap)
            print(f"\n[Fitur 5] Berhasil menghapus Root Max-Heap: ({target_id}, {nama})")
        else:
            print("\n[!] Max-Heap sudah kosong.")

    # (6) FITUR: Starting data awal dari file EXCEL
    def fitur6_load_excel(self, file_path):
        if os.path.exists(file_path):
            try:
                # Membaca file excel (asumsi kolom 'id' dan 'nama') [cite: 1, 2, 3]
                df = pd.read_excel(file_path)
                for _, row in df.iterrows():
                    self.fitur1_tambah_data(int(row['id']), str(row['nama']))
                print(f"\n>>> Fitur 6: Berhasil memuat {len(df)} data dari {file_path}")
            except Exception as e:
                print(f"\n[!] Error baca Excel: {e}")
        else:
            print(f"\n[!] File {file_path} tidak ditemukan.")

# --- Program Utama ---
if __name__ == "__main__":
    app = HeapDataStructure()
    
    # 6. Load data awal dari excel [cite: 1, 2, 3]
    app.fitur6_load_excel('data100.xlsx') 

    while True:
        print("\n" + "="*35)
        print("   MENU HEAP STRUCTURE (EXCEL)   ")
        print("="*35)
        print("1. Tambah Data (Manual)")
        print("2. Tampilkan Ascending (Min-Heap)")
        print("3. Tampilkan Descending (Max-Heap)")
        print("4. Hapus Root Min-Heap (Terkecil)")
        print("5. Hapus Root Max-Heap (Terbesar)")
        print("0. Keluar")
        
        pilih = input("Pilih Menu: ")
        
        if pilih == '1':
            try:
                id_input = int(input("Masukkan ID: "))
                nama_input = input("Masukkan Nama: ")
                app.fitur1_tambah_data(id_input, nama_input)
                print("Data berhasil ditambahkan ke Min & Max Heap.")
            except ValueError:
                print("ID harus angka!")
        elif pilih == '2': app.fitur2_tampilkan_ascending()
        elif pilih == '3': app.fitur3_tampilkan_descending()
        elif pilih == '4': app.fitur4_hapus_min()
        elif pilih == '5': app.fitur5_hapus_max()
        elif pilih == '0':
            print("Program selesai.")
            break
        else:
            print("Pilihan salah!")