import re

def get_precedence(op):
    # Menentukan prioritas operator (Higher = Stronger)
    return {'+': 1, '-': 1, '*': 2, '/': 2}.get(op, 0)

def main():
    while True:
        print("\n" + "="*50)
        infix = input("Masukkan ekspresi aritmatika (atau ketik 'exit' untuk keluar): ")
        
        if infix.lower() == 'exit':
            break
            
        # Regex untuk memisahkan angka (multi-digit) dan operator
        tokens = re.findall(r'\d+|[+\-*/()]', infix)
        
        stack = []
        postfix = []

        print(f"\n--- TAHAP 1: Konversi Infix ke Postfix (Shunting-yard) ---")
        print(f"{'Token':<10} | {'Stack':<20} | {'Postfix List'}")
        print("-" * 55)

        for token in tokens:
            if token.isdigit():
                postfix.append(token)
            elif token == '(':
                stack.append(token)
            elif token == ')':
                while stack and stack[-1] != '(':
                    postfix.append(stack.pop())
                    print(f"{' ':10} | {str(stack):20} | {' '.join(postfix)} (Pop karena ')')")
                if stack: stack.pop() # Buang '('
            else:
                while stack and stack[-1] != '(' and get_precedence(stack[-1]) >= get_precedence(token):
                    op = stack.pop()
                    postfix.append(op)
                    print(f"{' ':10} | {str(stack):20} | {' '.join(postfix)} (Pop '{op}' prioritas)")
                stack.append(token)
            
            print(f"{token:<10} | {str(stack):20} | {' '.join(postfix)}")

        # Proses Cleanup: Menguras sisa stack
        while stack:
            op = stack.pop()
            postfix.append(op)
            print(f"{'Cleanup':<10} | {str(stack):20} | {' '.join(postfix)} (Pop sisa '{op}')")
        
        print(f"\n>> Postfix Final: {' '.join(postfix)}")

        # TAHAP 2: EVALUASI
        eval_stack = []
        print(f"\n--- TAHAP 2: Evaluasi Perhitungan (Stack) ---")
        try:
            for token in postfix:
                if token.isdigit():
                    eval_stack.append(float(token))
                    print(f"Push {token:4} | Stack: {eval_stack}")
                else:
                    v2 = eval_stack.pop() # Operan kanan
                    v1 = eval_stack.pop() # Operan kiri
                    
                    if token == '+': res = v1 + v2
                    elif token == '-': res = v1 - v2
                    elif token == '*': res = v1 * v2
                    elif token == '/': res = v1 / v2
                    
                    eval_stack.append(res)
                    print(f"Hitung {v1} {token} {v2} | Hasil: {res} | Stack: {eval_stack}")
            
            print(f"\nHASIL AKHIR: {eval_stack[0]}")
        except Exception:
            print("Error: Ekspresi tidak valid!")

        if input("\nHitung lagi? (y/n): ").lower() != 'y':
            print("Program Berhenti.")
            break

if __name__ == "__main__":
    main()