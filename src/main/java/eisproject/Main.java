package eisproject;

import java.util.Scanner;

/**
 * An application to show currency exchange rates from bank of Lithuania
 *
 * @author Tomas
 */

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        UrlContent urlContent = new UrlContent();

        // Printing menu
        printMenu();

        boolean quit = false;
        while (!quit) {
            String action = scanner.next();
            scanner.nextLine();
            switch (action) {
                case "1":
                    System.out.println("Please enter desired currency(LT/EU)");
                    String currency = scanner.nextLine();
                    System.out.println(urlContent.getURLContent("https://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=" + currency));
                    break;
                case "2":
                    System.out.println("Please enter your currency (LT/EU)");
                    String ourCurrency = scanner.nextLine();
                    System.out.println("Please enter foreign currency (USD)");
                    String foreignCurrency = scanner.nextLine();
                    System.out.println("Please enter starting date (YYYY-MM-DD)");
                    String dateFrom = scanner.nextLine();
                    System.out.println("Please enter ending date (YYYY-MM-DD)");
                    String dateTo = scanner.nextLine();
                    System.out.println(urlContent.getURLContent("https://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=" + ourCurrency + "&ccy=" + foreignCurrency + "&dtFrom=" + dateFrom + "&dtTo=" + dateTo));
                    break;
                case "3":
                    printMenu();
                    break;
                case "4":
                    System.out.println("Thank you for using this application");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Available actions:\nPress");
        System.out.println(
                "1 - to show last available currency exchange rates\n" +
                        "2 - to show exchange rates for specified currency at date interval\n" +
                        "3 - to show menu\n" +
                        "4 - to quit");
    }
}
