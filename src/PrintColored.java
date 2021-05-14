public class PrintColored {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        public static final String ANSI_GREY_BACKGROUND = "\u001B[40;1m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_BLUE = "\u001B[34m";
    public static String printCardColors(Card card) {
//        String cardToPrint = ANSI_GREY_BACKGROUND;
        String cardToPrint = new String();
        String suite = card.getSuite();
        String rank = card.getRank();

        if(suite=="h") {
            cardToPrint+=ANSI_RED;
        }
        if(suite=="s") {
            cardToPrint+=ANSI_BLACK;
        }
        if(suite=="c") {
            cardToPrint+=ANSI_GREEN;
        }
        if(suite=="d") {
            cardToPrint+=ANSI_BLUE;
        }
        cardToPrint+=suite;
        cardToPrint+=rank;
        cardToPrint+=ANSI_RESET;
        return cardToPrint;

    }
}
