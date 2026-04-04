import tkinter as tk
from tkinter import messagebox
# Import Button saja dari tkmacosx untuk warna solid di Mac
from tkmacosx import Button 
from gtts import gTTS
import os
import platform
import threading # PENTING: Menghilangkan delay

def proses_suara(teks):
    """Fungsi ini berjalan di 'jalur' berbeda (Thread) agar UI tidak freeze"""
    try:
        tts = gTTS(text=teks, lang='id')
        file_audio = "panggilan.mp3"
        tts.save(file_audio)
        
        if platform.system() == "Darwin": # macOS
            # Menggunakan afplay tanpa '&' di sini karena sudah di dalam Thread
            os.system(f"afplay {file_audio}")
        elif platform.system() == "Windows":
            os.system(f"start /min {file_audio}")
    except Exception as e:
        print(f"Error suara: {e}")

class AppAntrian:
    def __init__(self, root):
        self.root = root
        self.root.title("RS Queue - No Delay Mac")
        self.root.geometry("450x600")
        
        # Paksa background putih agar tidak hitam/gelap di Mac
        self.root.configure(bg="#FFFFFF")
        
        self.antrian = []
        self.nomor_urut = 1

        # --- Judul ---
        tk.Label(root, text="ANTRIAN RUMAH SAKIT", font=("Helvetica", 20, "bold"), 
                 bg="#FFFFFF", fg="#2C3E50").pack(pady=(40, 20))
        
        # --- Input Pasien ---
        tk.Label(root, text="Masukkan Nama Pasien:", font=("Helvetica", 12), 
                 bg="#FFFFFF", fg="#7F8C8D").pack(pady=(10, 0))
        
        self.entry_nama = tk.Entry(root, font=("Helvetica", 14), width=25, 
                                   bg="#FFFFFF", fg="#000000", 
                                   insertbackground="#000000", 
                                   highlightthickness=1, highlightbackground="#DCDDE1", bd=0)
        self.entry_nama.pack(pady=10, ipady=10)

        # --- Tombol Ambil (Biru) ---
        self.btn_ambil = Button(
            root, text="AMBIL NOMOR", command=self.tambah, 
            bg="#3498db", fg="#FFFFFF", borderless=1,
            font=("Helvetica", 12, "bold"), focuscolor='',
            width=220, height=50, cursor="hand2"
        )
        self.btn_ambil.pack(pady=10)

        # --- Tombol Panggil (Hijau) ---
        self.btn_panggil = Button(
            root, text="PANGGIL PASIEN", command=self.panggil, 
            bg="#2ecc71", fg="#FFFFFF", borderless=1,
            font=("Helvetica", 12, "bold"), focuscolor='',
            width=220, height=50, cursor="hand2"
        )
        self.btn_panggil.pack(pady=5)

        # --- Daftar Antrian ---
        tk.Label(root, text="Daftar Antrian Aktif:", font=("Helvetica", 11), 
                 bg="#FFFFFF", fg="#7F8C8D").pack(pady=(25, 5))
        
        self.listbox = tk.Listbox(root, width=40, height=10, font=("Helvetica", 12), 
                                  bg="#FFFFFF", fg="#000000", 
                                  highlightthickness=1, highlightbackground="#DCDDE1", 
                                  selectbackground="#3498db", bd=0)
        self.listbox.pack(pady=5, padx=30)

    def tambah(self):
        nama = self.entry_nama.get()
        if nama.strip():
            self.antrian.append({"no": self.nomor_urut, "nama": nama})
            self.listbox.insert(tk.END, f"  #{self.nomor_urut:03d}  |  {nama}")
            self.nomor_urut += 1
            self.entry_nama.delete(0, tk.END)
        else:
            messagebox.showwarning("Peringatan", "Nama pasien tidak boleh kosong!")

    def panggil(self):
        if self.antrian:
            pasien = self.antrian.pop(0)
            self.listbox.delete(0)
            
            pesan = f"Nomor antrian {pasien['no']}, {pasien['nama']}, silakan ke loket"
            
            # --- TEKNIK NO DELAY ---
            # Kita buat thread baru khusus untuk memproses gTTS & Audio
            # sehingga perintah 'messagebox' di bawahnya langsung jalan tanpa nunggu suara selesai.
            threading.Thread(target=proses_suara, args=(pesan,), daemon=True).start()
            
            # Muncul seketika bersamaan dengan dimulainya proses suara
            messagebox.showinfo("Panggilan Loket", pesan)
        else:
            messagebox.showinfo("Informasi", "Antrian sudah kosong.")

if __name__ == "__main__":
    root = tk.Tk()
    app = AppAntrian(root)
    root.mainloop()