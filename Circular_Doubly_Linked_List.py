import time
import sys

class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
        self.prev = None

class NewsTicker:
    def __init__(self):
        self.head = None
        self.size = 0

    # 1. Insert Berita (Ditambahkan di akhir)
    def insert_news(self, news):
        new_node = Node(news)
        if not self.head:
            self.head = new_node
            self.head.next = self.head
            self.head.prev = self.head
        else:
            tail = self.head.prev
            tail.next = new_node
            new_node.prev = tail
            new_node.next = self.head
            self.head.prev = new_node
        self.size += 1
        print("[Sistem] Berita berhasil ditambahkan!")

    # 2. Hapus Berita (Berdasarkan nomor urut)
    def delete_news(self, pos):
        if not self.head or pos < 1 or pos > self.size:
            print("[Sistem] Nomor urut tidak valid!")
            return
        
        curr = self.head
        for _ in range(pos - 1):
            curr = curr.next
        
        if self.size == 1:
            self.head = None
        else:
            curr.prev.next = curr.next
            curr.next.prev = curr.prev
            if curr == self.head:
                self.head = curr.next
        
        self.size -= 1
        print(f"[Sistem] Berita '{curr.data}' berhasil dihapus.")

    # 3 & 4. Tampil Berita (Horizontal & Infinite Loop)
    def run_ticker(self, forward=True):
        if not self.head:
            print("[Sistem] Belum ada berita.")
            return

        print("\n=== SIMULASI RUNNING TEXT (Tekan Ctrl+C untuk Berhenti) ===")
        curr = self.head if forward else self.head.prev
        
        try:
            while True:  # Looping selamanya
                # \r mengembalikan kursor ke awal baris agar teks tertimpa
                sys.stdout.write(f"\r>>> BERITA TV: {curr.data} <<<          ")
                sys.stdout.flush()
                time.sleep(3)
                curr = curr.next if forward else curr.prev
        except KeyboardInterrupt:
            print("\n\n[Sistem] Kembali ke menu...")

    # 5. Tampil Berita Tertentu (Berdasarkan nomor urut)
    def display_specific(self, pos):
        if not self.head or pos < 1 or pos > self.size:
            print("[Sistem] Berita tidak ditemukan!")
            return
        curr = self.head
        for _ in range(pos - 1):
            curr = curr.next
        print(f"\n[Sistem] Berita No.{pos}: {curr.data}")

def main():
    ticker = NewsTicker()
    while True:
        print("\n" + "="*25)
        print("   MENU NEWS TICKER")
        print("="*25)
        print("1. Insert Berita")
        print("2. Hapus Berita")
        print("3. Tampil Forward")
        print("4. Tampil Backward")
        print("5. Tampil Berita spesifik")
        print("6. Exit")
        
        pilih = input("Pilih Menu: ")
        
        if pilih == '1':
            ticker.insert_news(input("Teks berita: "))
        elif pilih == '2':
            try:
                ticker.delete_news(int(input("Nomor urut dihapus: ")))
            except ValueError: print("[Error] Masukkan angka!")
        elif pilih == '3':
            ticker.run_ticker(forward=True)
        elif pilih == '4':
            ticker.run_ticker(forward=False)
        elif pilih == '5':
            try:
                ticker.display_specific(int(input("Nomor urut berita: ")))
            except ValueError: print("[Error] Masukkan angka!")
        elif pilih == '6':
            print("Keluar...")
            break

if __name__ == "__main__":
    main()