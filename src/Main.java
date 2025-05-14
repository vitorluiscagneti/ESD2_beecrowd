import java.util.Scanner;
import java.util.Objects;
import java.io.*;


public class Main {
    public static class BSTree {

        public static boolean isExternal(BSTPosition p) {
            return Objects.isNull(p.getLeft()) && Objects.isNull(p.getRight());
        }

        public static boolean isInternal(BSTPosition p) {
            return !isExternal(p);
        }

        public static BSTPosition search(int key, BSTPosition p) {
            if(isExternal(p)) {return p;}
            if(key < p.getKey()) {
                return search(key, p.getLeft());
            }else if(key > p.getKey()) {
                return search(key, p.getRight());
            }
            return p;
        }

        public static String searchPlusResult(int key, BSTPosition p) {
            if(!(search(key, p).getKey() == 0 &&
                    search(key, p).getValue() == 0 &&
                    search(key, p).getLeft() == null &&
                    search(key, p).getRight() == null)) {
                return (char) key + " existe";
            } else {
                return (char) key + " nao existe";
            }
            
        }

        public static void insertAtExternal(BSTPosition p, int key, int value) {
            p.setKey(key);
            p.setValue(value);
            p.setLeft(new BSTPosition());
            p.setRight(new BSTPosition());
        }

        public static BSTPosition insert(int key, int value, BSTPosition p) {
            var w = search(key, p);
            if(isInternal(w)){
                return insert(key, value, w.getLeft());
            }
            insertAtExternal(w, key, value);
            return w;
        }

        @FunctionalInterface
        public interface TraverseMethod {
        void buildString(BSTPosition p, StringBuilder sb);
        }   

        public static String print(BSTPosition p, TraverseMethod method) {
            if (isExternal(p)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            method.buildString(p, sb);
            return sb.toString();
        }
        
        public static void buildPreorderString(BSTPosition p, StringBuilder sb) {
            if (isExternal(p)) {
                return;
            }
            sb.append((char) p.getKey());
            if (isInternal(p.getLeft())) {
                sb.append(" "); //espaço apenas se tem filho
            } 
            buildPreorderString(p.getLeft(), sb);
            if (isInternal(p.getRight())) {
                sb.append(" ");
            }
            buildPreorderString(p.getRight(), sb);
        }

        public static void buildInorderString(BSTPosition p, StringBuilder sb) {
            if (isExternal(p)) {
                return;
            }
            if (isInternal(p.getLeft())) {
                buildInorderString(p.getLeft(), sb);
                sb.append(" ");
            }
            sb.append((char) p.getKey());

            if (isInternal(p.getRight())) {
                sb.append(" ");
                buildInorderString(p.getRight(), sb);
            }
        }

        public static void buildPostOrderString(BSTPosition p, StringBuilder sb) {
            if (isExternal(p)) {
                return;
            }
        
            if (isInternal(p.getLeft())) {
                buildPostOrderString(p.getLeft(), sb);
                sb.append(" ");
            }
        
            if (isInternal(p.getRight())) {
                buildPostOrderString(p.getRight(), sb);
                sb.append(" ");
            }
        
            sb.append((char) p.getKey());
        }

    }

    public static class BSTPosition {

        private int key;
        private int value;
        private BSTPosition left;
        private BSTPosition right;

        public BSTPosition(){}
        public BSTPosition(int key, int value, BSTPosition left, BSTPosition right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public BSTPosition getLeft() {
            return left;
        }

        public void setLeft(BSTPosition left) {
            this.left = left;
        }

        public BSTPosition getRight() {
            return right;
        }

        public void setRight(BSTPosition right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "BSTPosition{" +
                    "key=" + key +
                    ", value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public static void insertInput(BSTPosition root, Scanner scanner) {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.startsWith("I ") && input.length() == 3) {
                char key = input.charAt(2);
                BSTree.insert((int) key, (int) key, root);
            }
        }
    }

    public static void travInput(BSTPosition root, Scanner scanner) {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if ("INFIXA".equalsIgnoreCase(input)) {
                System.out.println(BSTree.print(root, (p, sb) -> BSTree.buildInorderString(p, sb)));
            } else if ("PREFIXA".equalsIgnoreCase(input)) {
                System.out.println(BSTree.print(root, (p, sb) -> BSTree.buildPreorderString(p, sb)));
            } else if ("POSFIXA".equalsIgnoreCase(input)) {
                System.out.println(BSTree.print(root, (p, sb) -> BSTree.buildPostOrderString(p, sb)));
            }
        }
    }

    public static void searchPlusResultInput(BSTPosition root, Scanner scanner) {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.startsWith("P ") && input.length() == 3) {
                char key = input.charAt(2);
                System.out.println(BSTree.searchPlusResult((int) key, root));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input;

        BSTPosition root = null;

        while ((input = br.readLine()) != null) {
            String[] st = input.split(" ");
            char c;

            if (st[0].equals("I")) {
                c = st[1].charAt(0);
                if (root == null) {
                    root = new BSTPosition((int) c, (int) c, new BSTPosition(), new BSTPosition());
                } else {
                    BSTree.insert((int) c, (int) c, root);
                }

            } else if (st[0].equals("PREFIXA")) {
                if (root != null) {
                    bw.append(BSTree.print(root, (p, sb) -> BSTree.buildPreorderString(p, sb))).append("\n");
                    bw.flush();
                }

            } else if (st[0].equals("INFIXA")) {
                if (root != null) {
                    bw.append(BSTree.print(root, (p, sb) -> BSTree.buildInorderString(p, sb))).append("\n");
                    bw.flush();
                }

            } else if (st[0].equals("POSFIXA")) {
                if (root != null) {
                    bw.append(BSTree.print(root, (p, sb) -> BSTree.buildPostOrderString(p, sb))).append("\n");
                    bw.flush();
                }

            } else if (st[0].equals("P")) {
                c = st[1].charAt(0);
                if (root != null) {
                    bw.append((BSTree.searchPlusResult((int) c, root))).append("\n");
                    bw.flush();
                }
            }
        }
        bw.close();
    }

}