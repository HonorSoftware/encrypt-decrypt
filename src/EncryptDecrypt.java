import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class EncryptDecrypt {

    public static void main(String[] args){

        int menuItem = showMenuAndMakeChoice();

        Path srcFile = receiveSrcFile(menuItem);
        if (srcFile == null) {
            System.out.println("Работа программы завершена некорректно, ни один путь к файлу не может быть null");
            return;
        }

        Path dstFile = receiveDstFile(menuItem);
        if (dstFile == null) {
            System.out.println("Работа программы завершена некорректно, ни один путь к файлу не может быть null");
            return;
        }
        try {
            makeOperation(menuItem, srcFile, dstFile);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Работа программы завершена некорректно, посмотрите лог работы");
        }
    }

    public static int showMenuAndMakeChoice() {
        System.out.println("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"");
        System.out.println("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"");
        System.out.println("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"");
        Scanner console = new Scanner(System.in);
        int choiceNumber = -1;
        boolean isCorrectChoice = false;
        while (!isCorrectChoice) {
            while (!console.hasNextInt()) {
                System.out.println("Введите целое число от 0 до 2");
                console.next();
            }
            choiceNumber = console.nextInt();
            if (choiceNumber == 0 || choiceNumber == 1 || choiceNumber == 2) {
                isCorrectChoice = true;
            } else {
                System.out.println("Введите целое число от 0 до 2");
            }
        }
        return choiceNumber;
    }

    public static boolean checkFileExpansionTxt(String fileNameAddress) {

        if (fileNameAddress.indexOf('.') != -1) {
            return "txt".equalsIgnoreCase(fileNameAddress.substring(fileNameAddress.indexOf('.') + 1, fileNameAddress.length()));
        }
        return false;
    }

    public static Path receiveSrcFile(int number) {
        if (number == 1) {
            System.out.println("Введите путь и имя файла для ШИФРОВАНИЯ (файл д/б в формате TXT):");
        } else if (number == 2) {
            System.out.println("Введите путь и имя файла для РАСШИФРОВКИ (файл д/б в формате TXT):");
        }
        Scanner console = new Scanner(System.in);
        String srcFileAddress = null;
        Path srcFile;
        boolean isCorrectSrcString = false;
        while (!isCorrectSrcString) {
            srcFileAddress = console.nextLine();
            if (srcFileAddress.equals("exit")) break;
            srcFile = Path.of(srcFileAddress);
            if ((Files.isRegularFile(srcFile)) && (Files.exists(srcFile)) && checkFileExpansionTxt(srcFileAddress)) {
                isCorrectSrcString = true;
                return srcFile;
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
        return null;
    }

    public static Path receiveDstFile(int number) {
        if (number == 1) {
            System.out.println("Введите путь и имя ЗАШИФРОВАННОГО файла (файл д/б в формате TXT):");
        } else if (number == 2) {
            System.out.println("Введите путь и имя РАСШИФРОВАННОГО файла (файл д/б в формате TXT):");
        }
        Scanner console = new Scanner(System.in);
        String dstFileAddress = null;
        Path dstFile;
        boolean isCorrectDstString = false;
        while (!isCorrectDstString) {
            dstFileAddress = console.nextLine();
            if (dstFileAddress.equals("exit")) break;
            if (checkFileExpansionTxt(dstFileAddress)) {
                dstFile = Path.of(dstFileAddress);

                try {
                    Files.createFile(dstFile);
                    isCorrectDstString = true;
                    return dstFile;
                } catch (IOException e) {
                    System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
                }
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
        return null;
    }

    public static void makeOperation(int menuItem, Path srcFile, Path dstFile) throws IOException {
        List<String> srcFileStrings;
        if (menuItem == 1) {
            srcFileStrings = Files.readAllLines(srcFile);
            Files.write(dstFile, srcFileStrings, StandardOpenOption.APPEND);
        } else if (menuItem == 2) {
            return;
        } else {
            return;
        }
    }
}
