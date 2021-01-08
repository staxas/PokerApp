public class PrintColored {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        public static final String ANSI_GREY_BACKGROUND = "\u001B[40;1m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_BLUE = "\u001B[34m";
    public static String printCardColors(String[] card) {
//        String cardToPrint = ANSI_GREY_BACKGROUND;
        String cardToPrint = new String();
        if(card[1]=="h") {
            cardToPrint+=ANSI_RED;
        }
        if(card[1]=="s") {
            cardToPrint+=ANSI_BLACK;
        }
        if(card[1]=="c") {
            cardToPrint+=ANSI_GREEN;
        }
        if(card[1]=="d") {
            cardToPrint+=ANSI_BLUE;
        }
        cardToPrint+=card[0];
        cardToPrint+=card[1];
        cardToPrint+=ANSI_RESET;
        return cardToPrint;

    }
}
