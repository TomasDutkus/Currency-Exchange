package eisproject.currencyexchange.input;

import java.util.Scanner;

public class Currency {

    private String code;

    public Currency() {
    }

    public Currency(String currCode) {
        this.code = currCode;
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public static Currency setCurrency() {

        System.out.println("Please enter foreign currency code:");

        //Scanner
        Scanner scanner = new Scanner(System.in);

        //Currency object
        Currency currency = new Currency();

        String currencyCode = scanner.nextLine();
        while (currencyCode.isEmpty() || currencyCode.length() < 3 || currencyCode.length() > 3) {
            System.out.println("Invalid currency code, please enter correct currency code:");
            currencyCode = scanner.nextLine();
        }

		//Setting currency code;
        currency.setCode(currencyCode);

        return currency;
    }
}
