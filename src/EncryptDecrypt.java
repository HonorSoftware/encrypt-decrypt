import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EncryptDecrypt {

    public static void main(String[] args) {

        final char[] ARRAY_ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
                'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
                'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

        final HashMap<Character, Integer> MAP_ALPHABET = new HashMap<>();

        for (int i = 0; i < ARRAY_ALPHABET.length; i++) {
            MAP_ALPHABET.put(ARRAY_ALPHABET[i], i);
        }

        int menuItem = showMenuAndMakeChoice();

        Path srcFile = null;
        try {
            srcFile = receiveSrcFile(menuItem);
            if (srcFile == null) {
                System.out.println("Работа программы завершена некорректно, ни один путь к файлу не может быть null");
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Работа программы завершена некорректно, посмотрите лог работы");
            return;
        }

        Path dstFile = null;
        try {
            dstFile = receiveDstFile(menuItem);
            if (dstFile == null) {
                System.out.println("Работа программы завершена некорректно, ни один путь к файлу не может быть null");
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Работа программы завершена некорректно, посмотрите лог работы");
            return;
        }

        int key = receiveKey(menuItem);

        if (key == 0) {
            System.out.println("Работа программы завершена некорректно, ключ не м/б равен 0, т.к. шифрования/расшифрования НЕ произойдет!");
            return;
        }

        try {
            makeOperation(menuItem, srcFile, dstFile, ARRAY_ALPHABET, MAP_ALPHABET, key);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Работа программы завершена некорректно, посмотрите лог работы");
        }
    }

    public static int showMenuAndMakeChoice() {
        System.out.println("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"");
        System.out.println("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"");
        System.out.println("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"");
        int choiceNumber = -1;
        boolean isCorrectChoice = false;
        Scanner consoleForMenu = new Scanner(System.in);
        while (!isCorrectChoice) {
            while (!consoleForMenu.hasNextInt()) {
                System.out.println("Введите целое число от 0 до 2");
                consoleForMenu.next();
            }
            choiceNumber = consoleForMenu.nextInt();
            if (choiceNumber == 0 || choiceNumber == 1 || choiceNumber == 2) {
                isCorrectChoice = true;
            } else {
                System.out.println("Введите целое число от 0 до 2");
            }
        }
        //consoleForMenu.close(); -- тут непонятно, т.к. если раскомментировать, то строки для ввода адреса файла не работают (((
        return choiceNumber;
    }

    public static boolean checkFileExpansionTxt(String fileNameAddress) {
        if (fileNameAddress.indexOf('.') != -1) {
            return "txt".equalsIgnoreCase(fileNameAddress.substring(fileNameAddress.indexOf('.') + 1, fileNameAddress.length()));
        }
        return false;
    }

    public static int receiveKey(int number) {
        if (number == 1) {
            System.out.println("Введите ключ для ШИФРОВАНИЯ файла (любое целое число):");
        } else if (number == 2) {
            System.out.println("Введите ключ для РАСШИФРОВКИ файла (нужен ключ, который был использован ранее для шифрования файла):");
        }
        int keyNumber = 0;
        boolean isCorrectKey = false;
        Scanner consoleForKey = new Scanner(System.in);
        while (!isCorrectKey) {
            while (!consoleForKey.hasNextInt()) {
                System.out.println("Введите целое число");
                consoleForKey.next();
            }
            keyNumber = consoleForKey.nextInt();
            return keyNumber;
        }
        consoleForKey.close();
        return keyNumber;
    }

    public static Path receiveSrcFile(int number) {
        if (number == 1) {
            System.out.println("Введите путь и имя файла для ШИФРОВАНИЯ (файл д/б в формате TXT):");
        } else if (number == 2) {
            System.out.println("Введите путь и имя файла для РАСШИФРОВКИ (файл д/б в формате TXT):");
        }
        Scanner consoleForSrc = new Scanner(System.in);
        String srcFileAddress = null;
        Path srcFile = null;
        boolean isCorrectSrcString = false;
        while (!isCorrectSrcString) {
            srcFileAddress = consoleForSrc.nextLine();
            if (srcFileAddress.equals("exit")) break;
            srcFile = Path.of(srcFileAddress);
            if ((Files.isRegularFile(srcFile)) && (Files.exists(srcFile)) && checkFileExpansionTxt(srcFileAddress)) {
                isCorrectSrcString = true;
                return srcFile;
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
        consoleForSrc.close();
        return srcFile;
    }

    public static Path receiveDstFile(int number) {
        if (number == 1) {
            System.out.println("Введите путь и имя ЗАШИФРОВАННОГО файла (файл д/б в формате TXT):");
        } else if (number == 2) {
            System.out.println("Введите путь и имя РАСШИФРОВАННОГО файла (файл д/б в формате TXT):");
        }
        Scanner consoleForDst = new Scanner(System.in);
        String dstFileAddress = null;
        Path dstFile = null;
        boolean isCorrectDstString = false;
        while (!isCorrectDstString) {
            dstFileAddress = consoleForDst.nextLine();
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
        consoleForDst.close();
        return dstFile;
    }

    public static void makeOperation(int menuItem, Path srcFile, Path dstFile, char[] arrayAlphabet, HashMap<Character, Integer> mapAlphabet, int shift) throws IOException {
        if ((menuItem == 1) || (menuItem == 2)) {
            try (
                    FileReader reader = new FileReader(srcFile.toRealPath().toFile());
                    FileWriter writer = new FileWriter(dstFile.toRealPath().toFile())
            ) {
                while (reader.ready()) {
                    int myCharInt = reader.read();
                    if (!mapAlphabet.containsKey((char) myCharInt)) {
                        writer.write((char) myCharInt);
                    } else {
                        //шифруем и записываем в выходной файл (расшифровка - это фактически шифрование, но с противоположным ключем)
                        int currentPositionInArrayAlphabet = mapAlphabet.get((char) myCharInt);
                        int positiveShift = receivePositiveShift(menuItem == 1 ? shift : (-1) * shift, arrayAlphabet.length);
                        int newPositionInArrayAlphabet = receiveNewPositionInAlphabetArray(currentPositionInArrayAlphabet, positiveShift, arrayAlphabet.length);
                        writer.write(arrayAlphabet[newPositionInArrayAlphabet]);
                    }
                }
            }
        }
        else {
            return;
        }
    }

    public static int receivePositiveShift(int shift, int length) {
        return shift >= 0 ? shift : length - Math.abs(shift) % length;
    }

    public static int receiveNewPositionInAlphabetArray(int currentPositionInArrayAlphabet, int shift, int length) {
        return (currentPositionInArrayAlphabet + receivePositiveShift(shift, length)) % length;
    }

}
