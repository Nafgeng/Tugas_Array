def input_matrix():
    rows = int(input("Masukkan jumlah baris matriks: "))
    cols = int(input("Masukkan jumlah kolom matriks: "))
    matrix = []
    print("Masukkan elemen matriks:")
    for i in range(rows):
        row = []
        for j in range(cols):
            val = int(input(f"matrix[{i}][{j}]: "))
            row.append(val)
        matrix.append(row)
    return matrix
 
def print_matrix(matrix):
    for row in matrix:
        print("  ".join(f"{x:4}" for x in row))
 
# ===================== 1-a: Sort Row-wise =====================
def sort_row_wise(matrix):
    result = [sorted(row) for row in matrix]
    print("Hasil sort row-wise:")
    print_matrix(result)
    return result
 
# ===================== 1-b: Sort Column-wise =====================
def sort_column_wise(matrix):
    rows = len(matrix)
    cols = len(matrix[0])
    result = [row[:] for row in matrix]
    for j in range(cols):
        col = sorted(result[i][j] for i in range(rows))
        for i in range(rows):
            result[i][j] = col[i]
    print("Hasil sort column-wise:")
    print_matrix(result)
    return result
 
# ===================== 2-a: Rotate Clockwise by 1 (shift right) =====================
def rotate_clockwise_by1(matrix):
    rows = len(matrix)
    cols = len(matrix[0])
    flat = [matrix[i][j] for i in range(rows) for j in range(cols)]
    flat = [flat[-1]] + flat[:-1]
    result = [flat[i*cols:(i+1)*cols] for i in range(rows)]
    print("Hasil rotate clockwise by 1:")
    print_matrix(result)
    return result
 
# ===================== 2-b: Rotate Counter-Clockwise by 1 (shift left) =====================
def rotate_ccw_by1(matrix):
    rows = len(matrix)
    cols = len(matrix[0])
    flat = [matrix[i][j] for i in range(rows) for j in range(cols)]
    flat = flat[1:] + [flat[0]]
    result = [flat[i*cols:(i+1)*cols] for i in range(rows)]
    print("Hasil rotate counter-clockwise by 1:")
    print_matrix(result)
    return result
 
# ===================== 2-c: Rotate by 90 Clockwise =====================
def rotate_90(matrix):
    result = [list(row) for row in zip(*matrix[::-1])]
    print("Hasil rotate 90 derajat clockwise:")
    print_matrix(result)
    return result
 
# ===================== 2-d: Rotate by 180 =====================
def rotate_180(matrix):
    result = [row[::-1] for row in matrix[::-1]]
    print("Hasil rotate 180 derajat:")
    print_matrix(result)
    return result
 
# ===================== 3-a: Row-wise Traversal =====================
def row_wise_traversal(matrix):
    flat = [x for row in matrix for x in row]
    print("Row-wise traversal:", flat)
 
# ===================== 3-b: Column-wise Traversal =====================
def col_wise_traversal(matrix):
    rows = len(matrix)
    cols = len(matrix[0])
    flat = [matrix[i][j] for j in range(cols) for i in range(rows)]
    print("Column-wise traversal:", flat)
 
# ===================== 4: Spiral Print =====================
def print_spiral(matrix):
    result = []
    top, bottom = 0, len(matrix) - 1
    left, right = 0, len(matrix[0]) - 1
    while top <= bottom and left <= right:
        result += matrix[top][left:right+1]
        top += 1
        result += [matrix[i][right] for i in range(top, bottom+1)]
        right -= 1
        if top <= bottom:
            result += matrix[bottom][left:right+1][::-1]
            bottom -= 1
        if left <= right:
            result += [matrix[i][left] for i in range(bottom, top-1, -1)]
            left += 1
    print("Spiral form:", result)
 
# ===================== 5: Transpose =====================
def transpose(matrix):
    result = [list(row) for row in zip(*matrix)]
    print("Hasil transpose:")
    print_matrix(result)
    return result
 
# ===================== MAIN MENU =====================
def main():
    matrix = input_matrix()
 
    while True:
        print("\n===== MENU MATRIX =====")
        print("1-a. Sort the matrix row-wise")
        print("1-b. Sort the matrix column-wise")
        print("2-a. Rotate Matrix Clockwise by 1")
        print("2-b. Rotate Matrix Counter-Clockwise by 1")
        print("2-c. Rotate a matrix by 90")
        print("2-d. Rotate a matrix by 180")
        print("3-a. Row-wise traversal of matrix")
        print("3-b. Column-wise traversal of matrix")
        print("4.   Print matrix in spiral form")
        print("5.   Transpose matrix")
        print("6.   Quit")
        choice = input("Pilihan: ").strip()
 
        if   choice == "1-a": matrix = sort_row_wise(matrix)
        elif choice == "1-b": matrix = sort_column_wise(matrix)
        elif choice == "2-a": matrix = rotate_clockwise_by1(matrix)
        elif choice == "2-b": matrix = rotate_ccw_by1(matrix)
        elif choice == "2-c": matrix = rotate_90(matrix)
        elif choice == "2-d": matrix = rotate_180(matrix)
        elif choice == "3-a": row_wise_traversal(matrix)
        elif choice == "3-b": col_wise_traversal(matrix)
        elif choice == "4":   print_spiral(matrix)
        elif choice == "5":   matrix = transpose(matrix)
        elif choice == "6":
            print("Keluar dari program. Sampai jumpa!")
            break
        else:
            print("Pilihan tidak valid! Coba lagi.")
 
if __name__ == "__main__":
    main()