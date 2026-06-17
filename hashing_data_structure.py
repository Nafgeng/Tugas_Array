import random

class ChainingHashTable:
    def __init__(self, size=20):
        self.size = size
        self.table = [[] for _ in range(self.size)]
        self.current_elements = 0

    def _hash_function(self, key):
        return key % self.size

    def insert(self, key):
        index = self._hash_function(key)
        if key in self.table[index]:
            return False
        self.table[index].append(key)
        self.current_elements += 1
        return True

    def delete(self, key):
        index = self._hash_function(key)
        if key in self.table[index]:
            self.table[index].remove(key)
            self.current_elements -= 1
            return True
        return False

    def search(self, key):
        index = self._hash_function(key)
        if key in self.table[index]:
            position_in_chain = self.table[index].index(key)
            return index, position_in_chain
        return -1, -1

    def tampilkan_semua_data(self):
        """Fungsi khusus untuk melacak indeks dan menghitung total data"""
        print(f"\n{'='*15} DAFTAR INDEKS & DATA AWAL {'='*15}")
        print(f"{'No Urut':<10} | {'Indeks Utama':<14} | {'Posisi Rantai':<15} | {'Data/Angka':<10}")
        print("-" * 60)
        
        nomor_urut = 1
        for i in range(self.size):
            for posisi, angka in enumerate(self.table[i]):
                print(f"{nomor_urut:<10} | Indeks [{i:02d}]     | Rantai ke-{posisi:<6} | {angka:<10}")
                nomor_urut += 1
        print("-" * 60)
        print(f"Total Data Terinput Saat Ini: {self.current_elements} angka.")
        print(f"{'='*57}\n")


# --- FUNGSI UTAMA ---
def main():
    ht = ChainingHashTable(size=20)

    # Otomatis input 100 angka acak unik di awal
    random_numbers = set()
    while len(random_numbers) < 100:
        random_numbers.add(random.randint(100, 999))

    for num in random_numbers:
        ht.insert(num)
    
    print("=== PROGRAM JALAN ===")
    print("-> Sistem baru saja menginput 100 data acak secara otomatis.")
    print("-> Silakan pilih Menu 4 untuk melihat daftar indeksnya.\n")

    while True:
        print("=== MENU HASH TABLE (SEPARATE CHAINING) ===")
        print("1. INPUT DATA BARU")
        print("2. HAPUS DATA")
        print("3. CARI DATA")
        print("4. LIHAT DAFTAR INDEKS & TOTAL DATA (100 DATA)")
        print("5. KELUAR")
        
        pilihan = input("Pilih menu (1-5): ")

        if pilihan == '1':
            try:
                val = int(input("Masukkan angka numerik baru: "))
                if ht.insert(val):
                    print(f"Berhasil: {val} dimasukkan ke indeks ke-{ht._hash_function(val)}.")
                else:
                    print(f"Gagal: {val} sudah ada di dalam Hash Table.")
            except ValueError:
                print("Input harus berupa angka numerik!")

        elif pilihan == '2':
            try:
                val = int(input("Masukkan angka yang ingin dihapus: "))
                if ht.delete(val):
                    print(f"Berhasil: {val} telah dihapus.")
                else:
                    print(f"Gagal: {val} tidak ditemukan.")
            except ValueError:
                print("Input harus berupa angka numerik!")

        elif pilihan == '3':
            try:
                val = int(input("Masukkan angka yang dicari: "))
                idx, chain_pos = ht.search(val)
                if idx != -1:
                    print(f"Ditemukan: {val} ada di Indeks [{idx}], urutan Rantai ke-{chain_pos}.")
                else:
                    print(f"Tidak Ditemukan: {val} tidak ada di dalam Hash Table.")
            except ValueError:
                print("Input harus berupa angka numerik!")

        elif pilihan == '4':
            # Memanggil fungsi pelacak indeks dan jumlah data
            ht.tampilkan_semua_data()

        elif pilihan == '5':
            print("Terima kasih! Keluar dari program.")
            break
        else:
            print("Pilihan tidak valid.")
        print("-" * 45)

if __name__ == "__main__":
    main()