import time

# Representasi satu kotak berita (Node)
class Node:
    def __init__(self, data):
        self.data = data    # Isi berita
        self.next = None    # Penunjuk ke berita selanjutnya
        self.prev = None    # Penunjuk ke berita sebelumnya

class NewsTicker:
    def __init__(self):
        self.head = None    # Titik awal list
        self.size = 0       # Jumlah berita saat ini

    # Fitur 1: Tambah berita di akhir
    def insert_news(self, news):
        new_node = Node(news)
        if not self.head:
            self.head = new_node
            self.head.next = self.head
            self.head.prev = self.head
        else:
            tail = self.head.prev # Karena circular, tail adalah sebelum head
            tail.next = new_node
            new_node.prev = tail
            new_node.next = self.head
            self.head.prev = new_node
        self.size += 1
        print("Berita berhasil ditambahkan!")

    # Fitur 2: Hapus berita berdasarkan nomor urut
    def delete_news(self, pos):
        if not self.head or pos < 1 or pos > self.size:
            print("Nomor urut tidak valid!")
            return
        
        curr = self.head
        for _ in range(pos - 1): # Cari lokasi berita
            curr = curr.next
        
        if self.size == 1:
            self.head = None
        else:
            curr.prev.next = curr.next # Sambungkan node sebelum ke sesudah
            curr.next.prev = curr.prev # Sambungkan node sesudah ke sebelum
            if curr == self.head:
                self.head = curr.next
        
        self.size -= 1
        print(f"Berita '{curr.data}' dihapus.")

    # Fitur 3: Tampil Forward (Depan ke Belakang)
    def display_forward(self):
        if not self.head: return
        curr = self.head
        print("\n--- RUNNING NEWS (FORWARD) ---")
        for _ in range(self.size):
            print(f">>> {curr.data} <<<", flush=True)
            time.sleep(3) # Delay 3 detik
            curr = curr.next

    # Fitur 4: Tampil Backward (Belakang ke Depan)
    def display_backward(self):
        if not self.head: return
        curr = self.head.prev # Mulai dari tail
        print("\n--- RUNNING NEWS (BACKWARD) ---")
        for _ in range(self.size):
            print(f"<<< {curr.data} >>>", flush=True)
            time.sleep(3)
            curr = curr.prev

    # Fitur 5: Tampil berita tertentu
    def display_specific(self, pos):
        if not self.head or pos < 1 or pos > self.size:
            print("Berita tidak ditemukan!")
            return
        curr = self.head
        for _ in range(pos - 1):
            curr = curr.next
        print(f"Berita No.{pos}: {curr.data}")

def main():
    ticker = NewsTicker()
    while True:
        print("--- MENU: ---")
        print("1. Insert Berita")
        print("2. Hapus Berita")
        print("3. Forward Berita")
        print("4. Backward Berita")
        print("5. Berita Spesifik")
        print("6. Exit")
        pilih = input("Pilihan: ")
        if pilih == '1': ticker.insert_news(input("Teks berita: "))
        elif pilih == '2': ticker.delete_news(int(input("Nomor urut: ")))
        elif pilih == '3': ticker.display_forward()
        elif pilih == '4': ticker.display_backward()
        elif pilih == '5': ticker.display_specific(int(input("Nomor urut: ")))
        elif pilih == '6': break

if __name__ == "__main__":
    main()