import os
import networkx as nx
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
from matplotlib.animation import FuncAnimation
from collections import deque

# --- KONSTANTA WARNA VISUALISASI GAMBAR ---
THEME_BG = '#093C5D'
COLOR_NODE = '#6FD1D7'
COLOR_EDGE = '#DFF1F1'
COLOR_TEXT = '#ffffff'
COLOR_HIGHLIGHT = '#ff007f'

class Graph:
    def __init__(self):
        # Menggunakan dictionary untuk menyimpan adjacency list
        self.graph = {}

    def add_vertex(self, v):
        if v not in self.graph:
            self.graph[v] = set()
            print(f"Vertex '{v}' berhasil ditambahkan.")
        else:
            print(f"Vertex '{v}' sudah ada.")

    def remove_vertex(self, v):
        if v in self.graph:
            for neighbor in list(self.graph[v]):
                self.graph[neighbor].remove(v)
            del self.graph[v]
            print(f"Vertex '{v}' dan semua edge terkait berhasil dihapus.")
        else:
            print(f"Vertex '{v}' tidak ditemukan.")

    def add_edge(self, u, v):
        if u in self.graph and v in self.graph:
            self.graph[u].add(v)
            self.graph[v].add(u) # Graf tidak berarah (undirected)
            print(f"Edge antara '{u}' dan '{v}' berhasil ditambahkan.")
        else:
            print("Gagal! Salah satu atau kedua vertex tidak ditemukan.")

    def remove_edge(self, u, v):
        if u in self.graph and v in self.graph:
            if v in self.graph[u]:
                self.graph[u].remove(v)
                self.graph[v].remove(u)
                print(f"Edge antara '{u}' dan '{v}' berhasil dihapus.")
            else:
                print(f"Tidak ada edge antara '{u}' dan '{v}'.")
        else:
            print("Salah satu atau kedua vertex tidak ditemukan.")

    def display_graph(self):
        if not self.graph:
            print("Graph kosong.")
            return

        vertices = sorted(list(self.graph.keys()))
        print("\n--- Adjacency Matrix ---")
        print("   ", " ".join(f"{v:2}" for v in vertices))
        for v1 in vertices:
            row = []
            for v2 in vertices:
                if v2 in self.graph[v1]:
                    row.append("1 ")
                else:
                    row.append("0 ")
            print(f"{v1:2} | {' '.join(row)}")
        
        print("\n[Menampilkan gambar graf di jendela baru...]")
        plt.close('all') 
        
        G = nx.Graph()
        G.add_nodes_from(vertices)
        for u in self.graph:
            for v in self.graph[u]:
                G.add_edge(u, v)
        
        fig, ax = plt.subplots(figsize=(8, 6), facecolor=THEME_BG)
        ax.set_facecolor(THEME_BG)
        plt.title("Visualisasi Graf Terkini", fontsize=14, fontweight='bold', color=COLOR_TEXT, pad=15)
        plt.axis('off')

        pos = nx.spring_layout(G, seed=42, k=1.2)
        
        nx.draw_networkx_edges(G, pos, ax=ax, edge_color=COLOR_EDGE, width=1.5, alpha=0.8)
        nx.draw_networkx_nodes(G, pos, ax=ax, node_size=1100, node_color=COLOR_NODE, alpha=0.2)
        nx.draw_networkx_nodes(G, pos, ax=ax, node_size=800, node_color=COLOR_NODE, edgecolors=COLOR_TEXT, linewidths=1.2)
        nx.draw_networkx_labels(G, pos, ax=ax, font_size=11, font_color=COLOR_TEXT, font_weight='bold')

        legend_patches = [
            mpatches.Patch(color=COLOR_NODE, label='Vertex Standar'),
            mpatches.Patch(color=COLOR_EDGE, label='Edge Standar')
        ]
        legend = plt.legend(handles=legend_patches, loc='lower right', facecolor=THEME_BG, edgecolor=COLOR_EDGE)
        plt.setp(legend.get_texts(), color=COLOR_TEXT)
        plt.show()

    def animate_traversal(self, traversal_steps, traversal_type):
        """Fungsi Animasi yang sudah diperbaiki agar semua garis yang terhubung ikut merah"""
        plt.close('all')
        
        vertices = sorted(list(self.graph.keys()))
        G = nx.Graph()
        G.add_nodes_from(vertices)
        for u in self.graph:
            for v in self.graph[u]:
                G.add_edge(u, v)

        fig, ax = plt.subplots(figsize=(9, 7), facecolor=THEME_BG)
        ax.set_facecolor(THEME_BG)
        plt.axis('off')
        
        pos = nx.spring_layout(G, seed=42, k=1.2)

        def update(frame):
            ax.clear()
            ax.set_facecolor(THEME_BG)
            plt.axis('off')
            
            # Ambil semua node yang sudah dikunjungi sampai frame/langkah saat ini
            visited_nodes = set(traversal_steps[:frame + 1])
            current_node = traversal_steps[frame]
            
            ax.set_title(f"Animasi {traversal_type} Traversal  |  T = {frame} (Aktif: {current_node})", 
                         fontsize=14, fontweight='bold', color=COLOR_TEXT, pad=20)
            
            # 1. Update Warna Node (Aktif/Sudah dikunjungi -> Merah/Pink)
            node_colors = []
            for node in G.nodes():
                if node == current_node or node in visited_nodes:
                    node_colors.append(COLOR_HIGHLIGHT)
                else:
                    node_colors.append(COLOR_NODE)

            # 2. Update Warna Edge (Jika kedua ujungnya sudah dikunjungi -> Merah/Pink)
            edge_colors = []
            edge_widths = []
            for u, v in G.edges():
                if u in visited_nodes and v in visited_nodes:
                    edge_colors.append(COLOR_HIGHLIGHT) # Berubah jadi merah sempurna bejir
                    edge_widths.append(3.5)             # Dipertebal biar kelihatan mantap
                else:
                    edge_colors.append(COLOR_EDGE)      # Tetap abu-abu kalem
                    edge_widths.append(1.5)

            # Render ulang seluruh komponen graf
            nx.draw_networkx_edges(G, pos, ax=ax, edge_color=edge_colors, width=edge_widths, alpha=0.8)
            nx.draw_networkx_nodes(G, pos, ax=ax, node_size=1200, node_color=node_colors, alpha=0.2) # Efek glow
            nx.draw_networkx_nodes(G, pos, ax=ax, node_size=800, node_color=node_colors, edgecolors=COLOR_TEXT, linewidths=1.2)
            nx.draw_networkx_labels(G, pos, ax=ax, font_size=11, font_color=COLOR_TEXT, font_weight='bold')

            # Konfigurasi Legenda Jendela Gambar
            legend_patches = [
                mpatches.Patch(color=COLOR_NODE, label='Belum Dikunjungi'),
                mpatches.Patch(color=COLOR_HIGHLIGHT, label='Sudah/Sedang Dikunjungi'),
                mpatches.Patch(color=COLOR_EDGE, label='Edge Standar')
            ]
            legend = ax.legend(handles=legend_patches, loc='lower right', facecolor=THEME_BG, edgecolor=COLOR_EDGE)
            plt.setp(legend.get_texts(), color=COLOR_TEXT)

        # Menjalankan animasi (1500 ms = 1.5 detik per langkah)
        ani = FuncAnimation(fig, update, frames=len(traversal_steps), interval=1500, repeat=False)
        plt.tight_layout()
        plt.show()

    def dfs(self, start_vertex):
        if start_vertex not in self.graph:
            print(f"Vertex '{start_vertex}' tidak ditemukan.")
            return
        visited, result = set(), []
        def dfs_helper(v):
            visited.add(v)
            result.append(v)
            for neighbor in sorted(self.graph[v]):
                if neighbor not in visited:
                    dfs_helper(neighbor)
        dfs_helper(start_vertex)
        print("Hasil urutan Traversal DFS:", " -> ".join(result))
        print("[Memulai Animasi Langkah DFS...]")
        self.animate_traversal(result, "DFS")

    def bfs(self, start_vertex):
        if start_vertex not in self.graph:
            print(f"Vertex '{start_vertex}' tidak ditemukan.")
            return
        visited, queue, result = set(), deque([start_vertex]), []
        visited.add(start_vertex)
        while queue:
            v = queue.popleft()
            result.append(v)
            for neighbor in sorted(self.graph[v]):
                if neighbor not in visited:
                    visited.add(neighbor)
                    queue.append(neighbor)
        print("Hasil urutan Traversal BFS:", " -> ".join(result))
        print("[Memulai Animasi Langkah BFS...]")
        self.animate_traversal(result, "BFS")


def main():
    g = Graph()
    while True:
        print("\n" + "="*20 + " MENU GRAPH " + "="*20)
        print("1. Tambah Vertex")
        print("2. Hapus Vertex")
        print("3. Tambah Edge")
        print("4. Hapus Edge")
        print("5. Tampilkan graph (bisa berupa gambar atau matrix)")
        print("6. Traversal DFS")
        print("7. Traversal BFS")
        print("8. Quit")
        print("="*52)
        
        pilihan = input("Pilih menu (1-8): ").strip()
        
        if pilihan == '1':
            v = input("Masukkan nama vertex baru (misal: A): ").strip()
            if v: g.add_vertex(v)
        elif pilihan == '2':
            v = input("Masukkan nama vertex yang ingin dihapus: ").strip()
            if v: g.remove_vertex(v)
        elif pilihan == '3':
            u = input("Masukkan Vertex Asal: ").strip()
            v = input("Masukkan Vertex Tujuan: ").strip()
            if u and v: g.add_edge(u, v)
        elif pilihan == '4':
            u = input("Masukkan Vertex Asal: ").strip()
            v = input("Masukkan Vertex Tujuan: ").strip()
            if u and v: g.remove_edge(u, v)
        elif pilihan == '5':
            g.display_graph()
        elif pilihan == '6':
            start = input("Masukkan vertex awal untuk DFS: ").strip()
            if start: g.dfs(start)
        elif pilihan == '7':
            start = input("Masukkan vertex awal untuk BFS: ").strip()
            if start: g.bfs(start)
        elif pilihan == '8':
            print("Terima kasih! Program selesai.")
            break
        else:
            print("Pilihan tidak valid. Silakan masukkan angka 1 sampai 8.")

if __name__ == "__main__":
    main()