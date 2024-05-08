public enum MainMenuItems {
    ENCRYPT("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"", 1, "Введите ключ для ШИФРОВАНИЯ файла (любое целое число, кроме 0). Или 0 для выхода"),
    DECRYPT("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"", 2, "Введите ключ для РАСШИФРОВКИ файла (нужен ключ, который был использован ранее для шифрования файла)"),
    DECRYPT_BF("Нажмите \"3\", чтобы выполнить действие \"Расшифровать файл брутофорсом.\"", 3, null),
    EXIT("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"", 0, null)

    ;

    @Override
    public String toString() {
        return "MainMenuItems{" +
                "messageItem='" + messageItem + '\'' +
                ", numberItem=" + numberItem +
                ", messageForKeyItem='" + messageForKeyItem + '\'' +
                '}';
    }

    private final String messageItem;
    private final int numberItem;
    private final String messageForKeyItem;

    public String getMessageItem() {
        return messageItem;
    }

    public int getNumberItem() {
        return numberItem;
    }

    public String getMessageForKeyItem() {
        return messageForKeyItem;
    }

    MainMenuItems(String messageItem, int numberItem, String messageForKeyItem) {
        this.messageItem = messageItem;
        this.numberItem = numberItem;
        this.messageForKeyItem = messageForKeyItem;
    }

}
