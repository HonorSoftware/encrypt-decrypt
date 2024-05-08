public enum MainMenuItems {
    ENCRYPT("Нажмите \"1\", чтобы выполнить действие \"Зашифровать файл с помощью ключа.\"", 1, "Введите ключ для ШИФРОВАНИЯ файла (любое целое число, кроме 0). Или 0 для выхода", "Введите путь и имя файла для ШИФРОВАНИЯ (файл д/б в формате TXT):", "ведите путь и имя ЗАШИФРОВАННОГО файла (файл д/б в формате TXT):"),
    DECRYPT("Нажмите \"2\", чтобы выполнить действие \"Расшифровать файл с помощью ключа.\"", 2, "Введите ключ для РАСШИФРОВКИ файла (нужен ключ, который был использован ранее для шифрования файла)", "Введите путь и имя файла для РАСШИФРОВКИ (файл д/б в формате TXT):", "Введите путь и имя РАСШИФРОВАННОГО файла (файл д/б в формате TXT):"),
    DECRYPT_BF("Нажмите \"3\", чтобы выполнить действие \"Расшифровать файл брутофорсом.\"", 3, null, "Введите путь и имя файла для РАСШИФРОВКИ в режиме брутфорс (файл д/б в формате TXT):", "Введите путь и имя РАСШИФРОВАННОГО в режиме брутфорс файла (файл д/б в формате TXT):"),
    EXIT("Нажмите \"0\", чтобы выполнить действие \"Выйти.\"", 0, null, "", "")

    ;
    @Override
    public String toString() {
        return "MainMenuItems{" +
                "messageItem='" + messageItem + '\'' +
                ", numberItem=" + numberItem +
                ", messageForKeyItem='" + messageForKeyItem + '\'' +
                ", messageForReceiveSrcFile='" + messageForReceiveSrcFile + '\'' +
                ", messageForReceiveDstFile='" + messageForReceiveDstFile + '\'' +
                '}';
    }
    private final String messageItem;
    private final int numberItem;
    private final String messageForKeyItem;
    private final String messageForReceiveSrcFile;
    private final String messageForReceiveDstFile;

    public String getMessageItem() {
        return messageItem;
    }
    public int getNumberItem() {
        return numberItem;
    }
    public String getMessageForKeyItem() {
        return messageForKeyItem;
    }
    public String getMessageForReceiveSrcFile() {
        return messageForReceiveSrcFile;
    }
    public String getMessageForReceiveDstFile() {
        return messageForReceiveDstFile;
    }

    MainMenuItems(String messageItem, int numberItem, String messageForKeyItem, String messageForReceiveSrcFile, String messageForReceiveDstFile) {
        this.messageItem = messageItem;
        this.numberItem = numberItem;
        this.messageForKeyItem = messageForKeyItem;
        this.messageForReceiveSrcFile = messageForReceiveSrcFile;
        this.messageForReceiveDstFile = messageForReceiveDstFile;
    }

}
