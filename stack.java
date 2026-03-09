import java.util.*;

public class stack {
    static int precedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String loop;

        do {
            System.out.println("\n========================================");
            System.out.print("Masukkan ekspresi aritmatika: ");
            String input = sc.nextLine();

            // Tokenizing menggunakan regex agar support input tanpa spasi
            String[] tokens = input.replaceAll("([+\\-*/()])", " $1 ").trim().split("\\s+");

            Stack<String> stack = new Stack<>();
            List<String> postfix = new ArrayList<>();

            System.out.println("\n--- Tahap 1: Infix -> Postfix ---");
            for (String t : tokens) {
                if (t.matches("\\d+")) {
                    postfix.add(t);
                } else if (t.equals("(")) {
                    stack.push(t);
                } else if (t.equals(")")) {
                    while (!stack.isEmpty() && !stack.peek().equals("(")) postfix.add(stack.pop());
                    if (!stack.isEmpty()) stack.pop();
                } else {
                    while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(t)) {
                        postfix.add(stack.pop());
                    }
                    stack.push(t);
                }
                System.out.println("Token: " + t + "\t| Stack: " + stack + " | Postfix: " + postfix);
            }

            // Cleanup sisa stack
            while (!stack.isEmpty()) {
                postfix.add(stack.pop());
                System.out.println("Cleanup\t| Stack: " + stack + " | Postfix: " + postfix);
            }

            System.out.println("\n--- Tahap 2: Evaluasi ---");
            Stack<Double> eval = new Stack<>();
            try {
                for (String t : postfix) {
                    if (t.matches("\\d+")) {
                        eval.push(Double.parseDouble(t));
                    } else {
                        double b = eval.pop();
                        double a = eval.pop();
                        double res = 0;
                        switch (t) {
                            case "+": res = a + b; break;
                            case "-": res = a - b; break;
                            case "*": res = a * b; break;
                            case "/": res = a / b; break;
                        }
                        eval.push(res);
                        System.out.println("Operasi " + a + t + b + " = " + res + " | Stack: " + eval);
                    }
                }
                System.out.println("\nHASIL AKHIR: " + eval.peek());
            } catch (Exception e) {
                System.out.println("Input tidak valid!");
            }

            System.out.print("\nHitung lagi? (y/n): ");
            loop = sc.nextLine().toLowerCase();
        } while (loop.equals("y"));

        System.out.println("Selesai.");
        sc.close();
    }
}