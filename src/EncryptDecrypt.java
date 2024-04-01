import java.util.Scanner;

public class EncryptDecrypt {

    public static void main(String[] args) {
        System.out.println("Выбран пункт меню: " + showMenuAndMakeChoice());
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
}
