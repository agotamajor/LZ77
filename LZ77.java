import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LZ77 {
    public static void main(String[] args) throws IOException {
        // kodolas
        File file = new File("be.txt");
        Scanner scanner = new Scanner(file);
        FileWriter writer = new FileWriter("kiLZ77.txt");
        String string = "";
        while (scanner.hasNextLine()) {
            string += scanner.nextLine();
        }

        String past = "";
        int n = string.length();
        int i = 0;
        while (i < n) {
            char next = string.charAt(i);
            if (past.contains("" + next)) {
                String word = "" + next;
                if (i == n - 1) {
                    int length = word.length() - 1;
                    writer.write("<" + (i - past.indexOf(word.substring(0, length))) + "," + length + "," + word.charAt(length) +">;");
                    i = n;
                    break;
                }
                for (int j = i + 1; j < n; j++) {
                    char current = string.charAt(j);
                    word += current;
                    if (!past.contains("" + word) || j == n - 1) {
                        int length = word.length() - 1;
                        writer.write("<" + (i - past.indexOf(word.substring(0, length))) + "," + length + "," + word.charAt(length) +">;");
                        i = j + 1;
                        past += current;
                        break;
                    }
                    past += current;
                }
            } else {
                writer.write("<0,0," + next + ">;");
                i++;
            }
            past += next;
        }
        writer.close();
        scanner.close();

        // dekodolas
        File decodeFile = new File("kiLZ77.txt");
        Scanner scan = new Scanner(decodeFile);
        String str = "";
        while (scan.hasNextLine()) {
            str += scan.nextLine();
        }
        scan.close();

        String decodeString = "";
        String[] tokens = str.split(";");
        for (String token: tokens) {
            String[] ts = token.split(",");
            int t = Integer.parseInt(ts[0].substring(1, ts[0].length()));
            int h = Integer.parseInt(ts[1]);
            char c = ts[2].charAt(0);
            if (t != 0) {
                int index = decodeString.length() - t;
                for (int j = index; j < index + h; j++) {
                    decodeString += decodeString.charAt(j);
                }
            }
            decodeString += c;
        }
        System.out.println(decodeString);
    }
}