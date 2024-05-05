public enum MainMenuItems {
    ENCRYPT("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"", 1),
    DECRYPT("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"", 2),
    DECRYPT_BF("Нажмите \"3\", чтобы выполнить действие \"Расшифровать файл брутофорсом.\"", 3),
    EXIT("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"", 0)

    ;

    @Override
    public String toString() {
        return "MainMenuItems{" +
                "messageItem='" + messageItem + '\'' +
                ", numberItem=" + numberItem +
                '}';
    }

    private final String messageItem;
    private final int numberItem;

    public String getMessageItem() {
        return messageItem;
    }

    public int getNumberItem() {
        return numberItem;
    }

    MainMenuItems(String messageItem, int numberItem) {
        this.messageItem = messageItem;
        this.numberItem = numberItem;
    }

}
