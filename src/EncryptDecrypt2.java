import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class EncryptDecrypt2 {

    final static char[] ARRAY_ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' ', '\n'};

    final static HashMap<Character, Integer> MAP_ALPHABET = new HashMap<>();

    public static void main(String[] args) {
        fillAlphabetMap();
        showMainMenu();
        makeOperation(receiveUserChoiceNumber());
    }

    public static void fillAlphabetMap() {
        for (int i = 0; i < ARRAY_ALPHABET.length; i++) {
            MAP_ALPHABET.put(ARRAY_ALPHABET[i], i);
        }
    }

    public static void showMainMenu() {
        for (MainMenuItems item: MainMenuItems.values()) {
            System.out.println(item.getMessageItem());
        }
    }

    public static int receiveUserChoiceNumber() {
        int userChoiceNumber = -1;
        boolean isCorrectChoice = false;
        Scanner consoleForMenu = new Scanner(System.in);
        while (!isCorrectChoice) {
            while (!consoleForMenu.hasNextInt()) {
                System.out.println("Введите, пожалуйста, целое число, а не набор странных символов\n");
                //System.out.println();
                showMainMenu();
                consoleForMenu.next();
            }
            userChoiceNumber = consoleForMenu.nextInt();
            if (isUserChoiceNumberCorrect(userChoiceNumber)) {
                isCorrectChoice = true;
            } else {
                System.out.println("Введите, пожалуйста, число от 0 до 3, а не какое-то другое\n");
                //System.out.println();
                showMainMenu();
            }
        }
        //consoleForMenu.close(); -- тут непонятно, т.к. если раскомментировать, то строки для ввода адреса файла не работают (((
        return userChoiceNumber;
    }

    public static boolean isUserChoiceNumberCorrect(int userChoiceNumber) {
        for (MainMenuItems item : MainMenuItems.values()) {
            if (item.getNumberItem() == userChoiceNumber) {
                return true;
            }
        }
        return false;
    }

    static MainMenuItems findMenuItem(int number) {
        for (MainMenuItems item: MainMenuItems.values()) {
            if (item.getNumberItem() == number) {
                return item;
            }
        }
        return null;
    }

    public static void makeOperation(int userChoiceNumber) {

        MainMenuItems userChoice = findMenuItem(userChoiceNumber);

        if (userChoice == null) finishEmergency();

        switch (userChoice) {
            case ENCRYPT, DECRYPT -> makeEncryptOrDecryptWithKey(userChoiceNumber);

            case DECRYPT_BF -> makeBrutForceDecryptWithoutKey(userChoiceNumber);

            case EXIT -> finishByUser();

            default -> finishEmergency();
        }
    }

    public static void finishByUser() {
        System.exit(0);
    }

    public static void finishEmergency() {
        System.exit(-1);
    }

    public static void makeEncryptOrDecryptWithKey(int userChoiceNumber) {

        int shift = receiveKey(userChoiceNumber);

        try (
                FileReader reader = new FileReader(receiveSrcFile(userChoiceNumber).toRealPath().toFile());
                FileWriter writer = new FileWriter(receiveDstFile(userChoiceNumber).toRealPath().toFile())
        ) {
            while (reader.ready()) {
                //int myCharInt = reader.read();
                char myChar = (char) reader.read();
                if (!MAP_ALPHABET.containsKey(myChar)) {
                    writer.write(myChar);
                } else {
                    //шифруем и записываем в выходной файл (расшифровка - это фактически шифрование, но с противоположным ключем)
                    int currentPositionInArrayAlphabet = MAP_ALPHABET.get(myChar);
                    int positiveShift = receivePositiveShift(userChoiceNumber == MainMenuItems.ENCRYPT.getNumberItem() ? shift : (-1) * shift, ARRAY_ALPHABET.length);
                    int newPositionInArrayAlphabet = receiveNewPositionInAlphabetArray(currentPositionInArrayAlphabet, positiveShift, ARRAY_ALPHABET.length);
                    writer.write(ARRAY_ALPHABET[newPositionInArrayAlphabet]);
                }
            }
        } catch (IOException e) {
            finishEmergency();
        }
    }

    public static int receivePositiveShift(int shift, int length) {
        return shift >= 0 ? shift : length - Math.abs(shift) % length;
    }

    public static int receiveNewPositionInAlphabetArray(int currentPositionInArrayAlphabet, int shift, int length) {
        return (currentPositionInArrayAlphabet + receivePositiveShift(shift, length)) % length;
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
        //String srcFileAddress;
        Path srcFile;
        while (true) {
            String srcFileAddress = consoleForSrc.nextLine();
            if (srcFileAddress.equals("exit")) finishByUser();
            srcFile = Path.of(srcFileAddress);
            if ((Files.isRegularFile(srcFile)) && (Files.exists(srcFile)) && checkFileExpansionTxt(srcFileAddress)) {
                return srcFile;
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
    }

    public static boolean checkFileExpansionTxt(String fileNameAddress) {
        if (fileNameAddress.indexOf('.') != -1) {
            //return "txt".equalsIgnoreCase(fileNameAddress.substring(fileNameAddress.indexOf('.') + 1, fileNameAddress.length()));
            return "txt".equalsIgnoreCase(fileNameAddress.substring(fileNameAddress.indexOf('.') + 1));
        }
        return false;
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
        //String dstFileAddress;
        Path dstFile;
        while (true) {
            String dstFileAddress = consoleForDst.nextLine();
            if (dstFileAddress.equals("exit")) finishByUser();
            if (checkFileExpansionTxt(dstFileAddress)) {
                dstFile = Path.of(dstFileAddress);
                try {
                    Files.createFile(dstFile);
                    return dstFile;
                } catch (IOException e) {
                    System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
                }
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
    }

    public static int receiveKey(int number) {
        if (number == 1) {
            System.out.println("Введите ключ для ШИФРОВАНИЯ файла (любое целое число, кроме 0). Или 0 для выхода");
        } else if (number == 2) {
            System.out.println("Введите ключ для РАСШИФРОВКИ файла (нужен ключ, который был использован ранее для шифрования файла)");
        } else {
            finishEmergency();
        }
        int keyNumber;
        Scanner consoleForKey = new Scanner(System.in);
        while (true) {
            while (!consoleForKey.hasNextInt()) {
                System.out.println("Введите целое число НЕ равное 0. Или 0 для выхода.");
                consoleForKey.next();
            }
            keyNumber = consoleForKey.nextInt();
            if (keyNumber == 0) {
                finishByUser();
            } else {
                break;
            }
        }
        return keyNumber;
    }

    public static void makeBrutForceDecryptWithoutKey(int userChoiceNumber) {

        HashSet<String> dict = new HashSet<>();
        int[] countCorrectWordForKey = new int[ARRAY_ALPHABET.length];

        Path dictFile = receiveDictFile();
        receiveDict(dictFile, dict);

        Path srcFile = receiveSrcFile(userChoiceNumber);
        Path dstFile = receiveDstFile(userChoiceNumber);

        try {
            for (int key = 0; key < ARRAY_ALPHABET.length; key++) {
                FileReader reader = new FileReader(srcFile.toRealPath().toFile());
                int countCorrectWords = 0;
                StringBuilder wordForCheck = new StringBuilder();
                char tempChar;
                while (reader.ready()) {
                    //int myCharInt = reader.read();
                    char myChar = (char) reader.read();
                    if (MAP_ALPHABET.containsKey(myChar)) {
                        int currentPositionInArrayAlphabet = MAP_ALPHABET.get(myChar);
                        int newPositionInArrayAlphabet = receiveNewPositionInAlphabetArray(currentPositionInArrayAlphabet, key, ARRAY_ALPHABET.length);
                        tempChar = ARRAY_ALPHABET[newPositionInArrayAlphabet];
                        if (Character.isLetter(tempChar)) {
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
                countCorrectWordForKey[key] = countCorrectWords;
                reader.close();
            }
        } catch (Exception e) {
            finishEmergency();
        }

        try (
                FileReader reader = new FileReader(srcFile.toRealPath().toFile());
                FileWriter writer = new FileWriter(dstFile.toRealPath().toFile())
        ) {
            while (reader.ready()) {
                //int myCharInt = reader.read();
                char myChar = (char) reader.read();
                if (!MAP_ALPHABET.containsKey(myChar)) {
                    writer.write(myChar);
                } else {
                    int currentPositionInArrayAlphabet = MAP_ALPHABET.get(myChar);
                    int newPositionInArrayAlphabet = receiveNewPositionInAlphabetArray(currentPositionInArrayAlphabet, findMaxIndexOfArray(countCorrectWordForKey), ARRAY_ALPHABET.length);
                    writer.write(ARRAY_ALPHABET[newPositionInArrayAlphabet]);
                }
            }
        } catch (Exception e) {
            finishEmergency();
        }
        System.out.println("Ключ для расшифровки: " + findMaxIndexOfArray(countCorrectWordForKey));
    }

    public static Path receiveDictFile() {
        System.out.println("Введите путь и имя файла СЛОВАРЯ (файл д/б в формате TXT):");
        Scanner consoleForDict = new Scanner(System.in);
        //String dictFileAddress;
        Path dictFile;
        while (true) {
            String dictFileAddress = consoleForDict.nextLine();
            if (dictFileAddress.equals("exit")) {
                finishByUser();
            }
            dictFile = Path.of(dictFileAddress);
            if ((Files.isRegularFile(dictFile)) && (Files.exists(dictFile)) && checkFileExpansionTxt(dictFileAddress)) {
                return dictFile;
            } else {
                System.out.println("Введите корректные адрес и имя файла или \"exit\" для завершения работы");
            }
        }
    }

    public static void receiveDict(Path file, HashSet<String> dict) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toRealPath().toFile()))) {
            while (reader.ready()) {
                dict.add(reader.readLine());
            }
        } catch (IOException e) {
            finishEmergency();
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
