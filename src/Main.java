import java.util.Scanner;
import java.io.File;

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

            if (!fileExists || !isFile) {
                if (!fileExists) {
                    System.out.println("Файл не существует.");
                }
                if (!isFile) {
                    System.out.println("Указанный путь является папкой, а не файлом.");
                }
                continue;
            }

            validFileCount++;
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + validFileCount);
        }

    }

}
