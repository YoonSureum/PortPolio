public class Card {
    private String pattern;
    private String number;
    
    Card(String pattern, String number) {
        this.pattern = pattern;
        this.number = number;
    }
    
    @Override
	public String toString() {
		return pattern + number;
	}

	int getValue() {
        if (number.equals("A")) {
            return 11;
        } else if (number.equals("K") || number.equals("Q") || number.equals("J")) {
            return 10;
        } else {
            return Integer.parseInt(number);
        }
    }
}