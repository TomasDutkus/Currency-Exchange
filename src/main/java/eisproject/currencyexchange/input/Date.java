package eisproject.currencyexchange.input;

import java.util.Scanner;

public class Date {

    private String startingDate;
    private String endingDate;

    private Date() {
    }

    public Date(String startingDate, String endingDate) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public String getStartingDate() {
        return startingDate;
    }

    private void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    private void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public static Date setDates() {

        System.out.println("Please enter dates from which you want to get data\nUse this format 'YYYY-MM-DD'\n");

        //Scanner
        Scanner scanner = new Scanner(System.in);

        //Date object
        Date date = new Date();

        //Starting date
        System.out.println("Enter starting date:");
        String startingDate = scanner.nextLine();
        while (startingDate.isEmpty() || startingDate.length() > 10 || startingDate.length() < 10) {
            System.out.println("Invalid starting date, please enter correct starting date, use 'YYYY-MM-DD' format");
            startingDate = scanner.nextLine();
        }

        //Ending date
        System.out.println("Enter ending date:");
        String endingDate = scanner.nextLine();
        while (endingDate.isEmpty() || endingDate.length() > 10 || endingDate.length() < 10) {
            System.out.println("Invalid ending date, please enter correct ending date, use 'YYYY-MM-DD' format");
            endingDate = scanner.nextLine();
        }

        //Setting dates
        date.setStartingDate(startingDate);
        date.setEndingDate(endingDate);

        //Closing scanner
        scanner.close();
        return date;
    }
}
