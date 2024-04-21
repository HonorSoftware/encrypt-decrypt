public enum MainMenuItems {
    ENCRYPT("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"", 1),
    DECRYPT("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"", 2),
    DECRYPT_BF("Нажмите \"3\", чтобы выполнить действие \"Расшифровать файл брутофорсом.\"", 3),
    EXIT("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"", 0)

    ;

    private String nameItem;
    private int numberItem;

    public String getNameItem() {
        return nameItem;
    }

    public int getNumberItem() {
        return numberItem;
    }

    MainMenuItems(String nameItem, int numberItem) {
        this.nameItem = nameItem;
        this.numberItem = numberItem;
    }

}
