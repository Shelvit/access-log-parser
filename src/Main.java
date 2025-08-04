import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int validFileCount = 0;

        while (true) {
            System.out.println("Введите путь к файлу (или введите 'Выход' чтобы выйти из программы):");
            String filePath = sc.nextLine();

            if (filePath.equalsIgnoreCase("Выход")) {
                System.out.println("Выход из программы.");
                break;
            }

            File file = new File(filePath);
            boolean fileExists = file.exists();
            boolean isFile = file.isFile();

            if (!fileExists) {
                System.out.println("Файл не существует.");
                continue;
            }

            if (!isFile) {
                System.out.println("Указанный путь является папкой, а не файлом.");
                continue;
            }

            validFileCount++;
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + validFileCount);

            try {
                FileReader fileReader = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                int totalLines = 0;
                int maxLength = Integer.MIN_VALUE;
                int minLength = Integer.MAX_VALUE;

                while ((line = reader.readLine()) != null) {
                    totalLines++;

                    int length = line.length();

                    if (length > 1024) {
                        throw new RuntimeException("Встречена строка длиной более 1024 символов.");
                    }

                    if (length > maxLength) {
                        maxLength = length;
                    }

                    if (length < minLength) {
                        minLength = length;
                    }
                }

                System.out.println("Общее количество строк в файле: " + totalLines);
                System.out.println("Длина самой длинной строки в файле: " + maxLength);
                System.out.println("Длина самой короткой строки в файле: " + minLength);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }
}