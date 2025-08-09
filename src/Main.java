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
                int googlebotCount = 0;
                int yandexbotCount = 0;

                while ((line = reader.readLine()) != null) {
                    totalLines++;

                    if (line.length() > 1024) {
                        throw new RuntimeException("Встречена строка длиной более 1024 символов.");
                    }

                    String userAgent = extractUserAgent(line);
                    if (userAgent != null) {
                        String program = extractProgramFromUserAgent(userAgent);
                        if ("Googlebot".equalsIgnoreCase(program)) {
                            googlebotCount++;
                        } else if ("YandexBot".equalsIgnoreCase(program)) {
                            yandexbotCount++;
                        }
                    }
                }

                if (totalLines > 0) {
                    double googlebotPercentage = ((double) googlebotCount / totalLines) * 100;
                    double yandexbotPercentage = ((double) yandexbotCount / totalLines) * 100;

                    System.out.println("Доля запросов от Googlebot: " + googlebotPercentage + "%");
                    System.out.println("Доля запросов от YandexBot: " + yandexbotPercentage + "%");
                } else {
                    System.out.println("В файле нет строк.");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private static String extractUserAgent(String line) {
        int startIndex = line.indexOf("Mozilla/5.0");
        if (startIndex == -1) {
            return null;
        }

        int endIndex = line.indexOf(" ", startIndex);
        if (endIndex == -1) {
            endIndex = line.length();
        }

        return line.substring(startIndex + "User-Agent:".length()).trim();
    }

    private static String extractProgramFromUserAgent(String userAgent) {
        int firstBracketIndex = userAgent.indexOf('(');
        int lastBracketIndex = userAgent.indexOf(')', firstBracketIndex);

        if (firstBracketIndex == -1 || lastBracketIndex == -1) {
            return null;
        }

        String firstBrackets = userAgent.substring(firstBracketIndex + 1, lastBracketIndex);
        String[] parts = firstBrackets.split(";");

        if (parts.length < 2) {
            return null;
        }

        String fragment = parts[1].trim();
        String[] subParts = fragment.split("/");

        if (subParts.length < 1) {
            return null;
        }

        return subParts[0].trim();
    }
}