import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class EncryptDecrypt {

    final HashSet<String> dictionary = new HashSet<>();

    public static void main(String[] args) {

        final char[] ARRAY_ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
                'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
                'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ', '\n'};

        final HashMap<Character, Integer> MAP_ALPHABET = new HashMap<>();

        for (int i = 0; i < ARRAY_ALPHABET.length; i++) {
            MAP_ALPHABET.put(ARRAY_ALPHABET[i], i);
        }

        final HashSet<String> dictionary = new HashSet<>();

        int[] countCorrectWordForKey = new int[ARRAY_ALPHABET.length];

        int menuItem = 0;

        Path srcFile = null;

        Path dstFile = null;

        try {
            menuItem = showMenuAndMakeChoice();
            if (menuItem == 0) {
                throw new Exception("Stop on menuItem");
            }
            srcFile = receiveSrcFile(menuItem);
            if ((srcFile == null) || !Files.exists(srcFile)) {
                throw new Exception("Stop on srcFile");
            }
            dstFile = receiveDstFile(menuItem);
            if ((dstFile == null) || !Files.exists(dstFile)) {
                throw new Exception("Stop on dstFile");
            }
            makeOperation(menuItem, srcFile, dstFile, ARRAY_ALPHABET, MAP_ALPHABET, dictionary, countCorrectWordForKey);
        } catch (Exception e) {
            System.out.println("Работа программы завершена. Просмотрите лог.");
        }
    }

    public static int showMenuAndMakeChoice() {
        System.out.println("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"");
        System.out.println("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"");
        System.out.println("Нажмите \"3\", чтобы выполнить действие \"Расшифровать файл брутофорсом.\"");
        System.out.println("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"");
        int choiceNumber = -1;
        boolean isCorrectChoice = false;
        Scanner consoleForMenu = new Scanner(System.in);
        while (!isCorrectChoice) {
            while (!consoleForMenu.hasNextInt()) {
                System.out.println("Введите целое число от 0 до 3. 0 для выхода");
                consoleForMenu.next();
            }
            choiceNumber = consoleForMenu.nextInt();
            if (choiceNumber == 0 || choiceNumber == 1 || choiceNumber == 2 || choiceNumber == 3) {
                isCorrectChoice = true;
            } else {
                System.out.println("Введите целое число от 0 до 3. 0 для выхода");
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
        } else if (number == 3) {
            System.out.println("Введите путь и имя файла для РАСШИФРОВКИ в режиме брутфорс (файл д/б в формате TXT):");
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
        } else if (number == 3) {
            System.out.println("Введите путь и имя РАСШИФРОВАННОГО в режиме брутфорс файла (файл д/б в формате TXT):");
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

    public static void makeOperation(int menuItem, Path srcFile, Path dstFile, char[] arrayAlphabet, HashMap<Character, Integer> mapAlphabet, HashSet<String> dict, int[] correctWordsCountForKey) throws IOException {

        if ((menuItem == 1) || (menuItem == 2)) {

            int shift = receiveKey(menuItem);

            if (shift == 0) {
                System.out.println("Работа программы завершена некорректно, ключ не м/б равен 0, т.к. шифрования/расшифрования НЕ произойдет!");
                return;
            }

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
        } else if (menuItem == 3) {
            Path dictFile = receiveDictFile();
            receiveDict(dictFile, dict);
            for (int key = 0; key < arrayAlphabet.length; key++) {
                FileReader reader = new FileReader(srcFile.toRealPath().toFile());
                int countCorrectWords = 0;
                StringBuilder wordForCheck = new StringBuilder();
                char tempChar = '^';
                while (reader.ready()) {
                    int myCharInt = reader.read();
                    if (mapAlphabet.containsKey((char) myCharInt)) {
                        int currentPositionInArrayAlphabet = mapAlphabet.get((char) myCharInt);
                        int newPositionInArrayAlphabet = receiveNewPositionInAlphabetArray(currentPositionInArrayAlphabet, key, arrayAlphabet.length);
                        tempChar = arrayAlphabet[newPositionInArrayAlphabet];
                        // '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
                        // тут конечно метод нужен отдельный
                        if (!((tempChar == '.')
                                || (tempChar == ',')
                                || (tempChar == '«')
                                || (tempChar == '»')
                                || (tempChar == '"')
                                || (tempChar == '\'')
                                || (tempChar == ':')
                                || (tempChar == '!')
                                || (tempChar == '?')
                                || (tempChar == '\n')
                                || (tempChar == ' '))) {
                            wordForCheck.append(tempChar);
                        } else {
                            if (wordForCheck.length() < 3) {
                                wordForCheck.delete(0, wordForCheck.length());
                            } else {
                                if (!dict.contains(wordForCheck.toString())) {
                                    wordForCheck.delete(0, wordForCheck.length());
                                } else {
                                    countCorrectWords++;
                                    wordForCheck.delete(0, wordForCheck.length());
                                }
                            }
                        }
                    }
                }
                correctWordsCountForKey[key] = countCorrectWords;
                reader.close();
            }
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
                        int newPositionInArrayAlphabet = receiveNewPositionInAlphabetArray(currentPositionInArrayAlphabet, findMaxIndexOfArray(correctWordsCountForKey), arrayAlphabet.length);
                        writer.write(arrayAlphabet[newPositionInArrayAlphabet]);
                    }
                }
            }
        } else {
            return;
        }
    }

    public static int receivePositiveShift(int shift, int length) {
        return shift >= 0 ? shift : length - Math.abs(shift) % length;
    }

    public static int receiveNewPositionInAlphabetArray(int currentPositionInArrayAlphabet, int shift, int length) {
        return (currentPositionInArrayAlphabet + receivePositiveShift(shift, length)) % length;
    }

    public static Path receiveDictFile() {
        System.out.println("Введите путь и имя файла СЛОВАРЯ (файл д/б в формате TXT):");
        Scanner consoleForDict = new Scanner(System.in);
        String dictFileAddress = null;
        Path dictFile = null;
        boolean isCorrectDictString = false;
        while (!isCorrectDictString) {
            dictFileAddress = consoleForDict.nextLine();
            if (dictFileAddress.equals("exit")) break;
            dictFile = Path.of(dictFileAddress);
            if ((Files.isRegularFile(dictFile)) && (Files.exists(dictFile)) && checkFileExpansionTxt(dictFileAddress)) {
                isCorrectDictString = true;
                return dictFile;
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
        consoleForDict.close();
        return dictFile;
    }

    public static void receiveDict(Path file, HashSet dict) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file.toRealPath().toFile()))
        ) {
            while (reader.ready()) {
                dict.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findMaxIndexOfArray(int[] array) {
        int max = array[0];
        int indexForMax = 0;
        for (int i = 0; i < array.length; i++) {
            int score = array[i];
            if (max < score) {
                max = score;
                indexForMax = i;
            }
        }
        return indexForMax;
    }
}
