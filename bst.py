import pandas as pd

# ======================
# NODE
# ======================
class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.left = None
        self.right = None

# ======================
# BST
# ======================
class BST:
    def __init__(self):
        self.root = None

    def insert(self, root, id, nama):
        if root is None:
            return Node(id, nama)
        if id < root.id:
            root.left = self.insert(root.left, id, nama)
        elif id > root.id:
            root.right = self.insert(root.right, id, nama)
        return root

    def search(self, root, id):
        if root is None:
            return None
        if id == root.id:
            return root
        elif id < root.id:
            return self.search(root.left, id)
        else:
            return self.search(root.right, id)

    def minValue(self, root):
        while root.left:
            root = root.left
        return root

    def delete(self, root, id):
        if root is None:
            return root

        if id < root.id:
            root.left = self.delete(root.left, id)
        elif id > root.id:
            root.right = self.delete(root.right, id)
        else:
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            temp = self.minValue(root.right)
            root.id = temp.id
            root.nama = temp.nama
            root.right = self.delete(root.right, temp.id)

        return root

    def inorder(self, root):
        if root:
            self.inorder(root.left)
            print(root.id, "-", root.nama)
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            print(root.id, "-", root.nama)
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.postorder(root.left)
            self.postorder(root.right)
            print(root.id, "-", root.nama)


# ======================
# LOAD DATA EXCEL
# ======================
def load_excel(bst):
    try:
        data = pd.read_excel("data100.xlsx")

        # normalisasi kolom
        data.columns = data.columns.str.strip().str.lower()

        for _, row in data.iterrows():
            bst.root = bst.insert(bst.root, int(row['id']), str(row['nama']))

        print("✅ Data dari Excel berhasil dimasukkan!")
        print("Jumlah data:", len(data))

    except Exception as e:
        print("❌ Gagal membaca Excel:", e)


# ======================
# MAIN PROGRAM (MENU)
# ======================
bst = BST()

# otomatis load Excel saat program mulai
load_excel(bst)

while True:
    print("\n=== MENU BST ===")
    print("1. Tambah Data")
    print("2. Cari Data")
    print("3. Hapus Data")
    print("4. Inorder")
    print("5. Preorder")
    print("6. Postorder")
    print("7. Tampilkan Semua Data (Inorder)")
    print("0. Keluar")

    pilihan = input("Pilih menu: ")

    if pilihan == "1":
        id = int(input("Masukkan ID: "))
        nama = input("Masukkan Nama: ")
        bst.root = bst.insert(bst.root, id, nama)
        print("✅ Data berhasil ditambahkan!")

    elif pilihan == "2":
        id = int(input("Masukkan ID yang dicari: "))
        hasil = bst.search(bst.root, id)
        if hasil:
            print("✅ Ditemukan:", hasil.id, "-", hasil.nama)
        else:
            print("❌ Data tidak ditemukan")

    elif pilihan == "3":
        id = int(input("Masukkan ID yang dihapus: "))
        bst.root = bst.delete(bst.root, id)
        print("✅ Data berhasil dihapus!")

    elif pilihan == "4":
        print("\nInorder:")
        bst.inorder(bst.root)

    elif pilihan == "5":
        print("\nPreorder:")
        bst.preorder(bst.root)

    elif pilihan == "6":
        print("\nPostorder:")
        bst.postorder(bst.root)

    elif pilihan == "7":
        print("\nSemua Data:")
        bst.inorder(bst.root)

    elif pilihan == "0":
        print("Program selesai")
        break

    else:
        print("❌ Pilihan tidak valid")