import java.util.*;

// NAMA CLASS UTAMA: graph (Wajib sama dengan nama file graph.java)
public class graph {

    // --- STRUKTUR DATA GRAPH ---
    private static class GraphStructure {
        Map<String, Set<String>> adjacencyList = new TreeMap<>();

        void addVertex(String v) {
            if (!adjacencyList.containsKey(v)) {
                adjacencyList.put(v, new TreeSet<>());
                System.out.println("✔ Vertex '" + v + "' berhasil ditambahkan.");
            } else {
                System.out.println("⚠ Vertex '" + v + "' sudah ada.");
            }
        }

        void removeVertex(String v) {
            if (adjacencyList.containsKey(v)) {
                for (String neighbor : adjacencyList.get(v)) {
                    adjacencyList.get(neighbor).remove(v);
                }
                adjacencyList.remove(v);
                System.out.println("✔ Vertex '" + v + "' dan semua edge terkait berhasil dihapus.");
            } else {
                System.out.println("✘ Vertex '" + v + "' tidak ditemukan.");
            }
        }

        void addEdge(String u, String v) {
            if (adjacencyList.containsKey(u) && adjacencyList.containsKey(v)) {
                adjacencyList.get(u).add(v);
                adjacencyList.get(v).add(u); 
                System.out.println("✔ Edge antara '" + u + "' dan '" + v + "' berhasil ditambahkan.");
            } else {
                System.out.println("✘ Gagal! Salah satu atau kedua vertex tidak ditemukan. Tambahkan vertex dulu.");
            }
        }

        void removeEdge(String u, String v) {
            if (adjacencyList.containsKey(u) && adjacencyList.containsKey(v)) {
                if (adjacencyList.get(u).contains(v)) {
                    adjacencyList.get(u).remove(v);
                    adjacencyList.get(v).remove(u);
                    System.out.println("✔ Edge antara '" + u + "' dan '" + v + "' berhasil dihapus.");
                } else {
                    System.out.println("⚠ Tidak ada edge antara '" + u + "' dan '" + v + "'.");
                }
            } else {
                System.out.println("✘ Salah satu atau kedua vertex tidak ditemukan.");
            }
        }

        void displayGraph() {
            if (adjacencyList.isEmpty()) {
                System.out.println("Graph kosong.");
                return;
            }

            System.out.println("\n--- [1] ADJACENCY MATRIX ---");
            System.out.print("    ");
            for (String v : adjacencyList.keySet()) {
                System.out.printf("%-3s", v);
            }
            System.out.println();
            System.out.println("────┼" + "───".repeat(adjacencyList.size()));

            for (String v1 : adjacencyList.keySet()) {
                System.out.printf("%-3s │ ", v1);
                for (String v2 : adjacencyList.keySet()) {
                    System.out.printf("%-3d", adjacencyList.get(v1).contains(v2) ? 1 : 0);
                }
                System.out.println();
            }

            System.out.println("\n--- [2] ADJACENCY LIST (HUBUNGAN) ---");
            for (Map.Entry<String, Set<String>> entry : adjacencyList.entrySet()) {
                System.out.print("  " + entry.getKey() + " ───► ");
                if (entry.getValue().isEmpty()) {
                    System.out.println("[Tidak ada tetangga]");
                } else {
                    System.out.println(String.join(", ", entry.getValue()));
                }
            }
        }

        void dfs(String start) {
            if (!adjacencyList.containsKey(start)) {
                System.out.println("✘ Vertex '" + start + "' tidak ditemukan.");
                return;
            }
            List<String> result = new ArrayList<>();
            Set<String> visited = new HashSet<>();
            dfsHelper(start, visited, result);
            System.out.println("Hasil Urutan DFS: " + String.join(" -> ", result));
            animateTraversalConsole(result, "DFS");
        }

        private void dfsHelper(String v, Set<String> visited, List<String> result) {
            visited.add(v);
            result.add(v);
            for (String neighbor : adjacencyList.get(v)) {
                if (!visited.contains(neighbor)) {
                    dfsHelper(neighbor, visited, result);
                }
            }
        }

        // === FUNGSI BFS YANG SUDAH DIPERBAIKI (ANTI BENTROK TYPE DATA/GENERICS) ===
        void bfs(String start) {
            if (!adjacencyList.containsKey(start)) {
                System.out.println("✘ Vertex '" + start + "' tidak ditemukan.");
                return;
            }

            List<String> result = new ArrayList<>();
            Set<String> visited = new HashSet<>();
            
            // Di sini kita panggil lurus dari packagenya: java.util.Queue & java.util.LinkedList
            java.util.Queue<String> queue = new java.util.LinkedList<>();

            visited.add(start);
            queue.add(start);

            while (!queue.isEmpty()) {
                String v = queue.poll();
                result.add(v);
                for (String neighbor : adjacencyList.get(v)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            
            System.out.println("Hasil Urutan BFS: " + String.join(" -> ", result));
            animateTraversalConsole(result, "BFS");
        }

        private void animateTraversalConsole(List<String> steps, String type) {
            System.out.println("\n[Memulai Simulasi Animasi Langkah " + type + "...]");
            List<String> currentPath = new ArrayList<>();
            Set<String> visitedNodes = new HashSet<>();

            for (int t = 0; t < steps.size(); t++) {
                String currentNode = steps.get(t);
                currentPath.add(currentNode);
                visitedNodes.add(currentNode);

                System.out.println("\n────────────────────────────────────────────────────");
                System.out.println("⏱  Timeline T = " + t + "  |  [Node Aktif Saat Ini: " + currentNode + "]");
                System.out.print("🐾 Jalur yang sudah dilalui: ");
                System.out.println(String.join(" ➔ ", currentPath));
                
                System.out.print("路  Kondisi Jalan Aktif (Merah/Terlewati): ");
                List<String> activeEdges = new ArrayList<>();
                for (String u : adjacencyList.keySet()) {
                    for (String v : adjacencyList.get(u)) {
                        if (u.compareTo(v) < 0 && visitedNodes.contains(u) && visitedNodes.contains(v)) {
                            activeEdges.add(u + "-" + v);
                        }
                    }
                }
                System.out.println(activeEdges.isEmpty() ? "Belum ada" : String.join(", ", activeEdges));

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("\n🏁 Animasi " + type + " Selesai!");
            System.out.println("────────────────────────────────────────────────────");
        }
    }

    public static void main(String[] args) {
        GraphStructure g = new GraphStructure();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==================== MENU GRAPH ====================");
            System.out.println("1. Tambah Vertex");
            System.out.println("2. Hapus Vertex");
            System.out.println("3. Tambah Edge");
            System.out.println("4. Hapus Edge");
            System.out.println("5. Tampilkan graph (bisa berupa gambar atau matrix)");
            System.out.println("6. Traversal DFS");
            System.out.println("7. Traversal BFS");
            System.out.println("8. Quit");
            System.out.println("====================================================");
            System.out.print("Pilih menu (1-8): ");
            
            String pilihan = scanner.nextLine().trim();
            System.out.println("────────────────────────────────────────────────────");

            switch (pilihan) {
                case "1":
                    System.out.print("Masukkan nama vertex baru (misal: A): ");
                    String v1 = scanner.nextLine().trim();
                    if (!v1.isEmpty()) g.addVertex(v1);
                    break;
                case "2":
                    System.out.print("Masukkan nama vertex yang ingin dihapus: ");
                    String v2 = scanner.nextLine().trim();
                    if (!v2.isEmpty()) g.removeVertex(v2);
                    break;
                case "3":
                    System.out.print("Masukkan Vertex Asal: ");
                    String u3 = scanner.nextLine().trim();
                    System.out.print("Masukkan Vertex Tujuan: ");
                    String v3 = scanner.nextLine().trim();
                    if (!u3.isEmpty() && !v3.isEmpty()) g.addEdge(u3, v3);
                    break;
                case "4":
                    System.out.print("Masukkan Vertex Asal: ");
                    String u4 = scanner.nextLine().trim();
                    System.out.print("Masukkan Vertex Tujuan: ");
                    String v4 = scanner.nextLine().trim();
                    if (!u4.isEmpty() && !v4.isEmpty()) g.removeEdge(u4, v4);
                    break;
                case "5":
                    g.displayGraph();
                    break;
                case "6":
                    System.out.print("Masukkan vertex awal untuk DFS: ");
                    String startDFS = scanner.nextLine().trim();
                    if (!startDFS.isEmpty()) g.dfs(startDFS);
                    break;
                case "7":
                    System.out.print("Masukkan vertex awal untuk BFS: ");
                    String startBFS = scanner.nextLine().trim();
                    if (!startBFS.isEmpty()) g.bfs(startBFS);
                    break;
                case "8":
                    System.out.println("Terima kasih! Program selesai.");
                    scanner.close();
                    System.exit(0);
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Masukkan angka 1 sampai 8.");
            }
        }
    }
}