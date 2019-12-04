package eisproject.currencyexchange;

import eisproject.currencyexchange.input.Currency;
import eisproject.currencyexchange.input.Date;
import org.decimal4j.util.DoubleRounder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An application to show currency exchange rate and change by date from bank of Lithuania
 *
 * @author Tomas
 */

public class Main {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        System.out.println("\nThis application will show EUR to foreign currency exchange rate and change by date\n");

        //Setting currency code
        Currency currency = Currency.setCurrency();
        String currencyCode = currency.getCode().toUpperCase();

        //Setting dates
        Date date = Date.setDates();
        String startingdate = date.getStartingDate();
        String endingDate = date.getEndingDate();

        //Taking data from HTTP Get method
        URL url = new URL("https://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=EU&ccy="
                + currencyCode + "&dtFrom=" + startingdate + "&dtTo=" + endingDate);

        URLConnection conn = url.openConnection();

        InputStream in = conn.getInputStream();

        BufferedInputStream buff = new BufferedInputStream(in);

        //Putting data into string
        String data = new String(buff.readAllBytes());

        buff.close();
        in.close();

        //Writing string to a file
        FileWriter fw = new java.io.FileWriter("Currency-Exchange.xml");
        fw.write(data);
        fw.close();

        //Parsing XML file
        File xmlFile = new File("Currency-Exchange.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        //Creating node object FxRate
        NodeList nodeList = doc.getElementsByTagName("FxRate");

        System.out.println("<><><><><><><><><><>");

        //Creating list for double elements
        List<Double> doublesArray = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                /* Just to check if data is present
				System.out.println("Date: " + element.getElementsByTagName("Dt").item(0).getTextContent());
				System.out.println("Currency code and rate to Euro: " + element.getElementsByTagName("CcyAmt").item(1).getTextContent()); */

                String str = element.getElementsByTagName("CcyAmt").item(1).getTextContent();

                //Getting double out of string
                Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+))");
                Matcher matcher = pattern.matcher(str);

                while (matcher.find()) {
                    Double doub = Double.parseDouble(matcher.group(1));
                    doublesArray.add(doub);
                }
            }
        }

        //Displaying exchange rate by ending date
        System.out.println("EUR to " + currencyCode + " exchange rate by ending date is: " + doublesArray.get(0));

        //Getting average change from the list and displaying to user
        Double average = DoubleRounder.round(doublesArray.get(doublesArray.size() - 1), 4) - DoubleRounder.round(doublesArray.get(0), 4);
        BigDecimal bigDecimal = BigDecimal.valueOf(average);
        MathContext mc = new MathContext(4);
        bigDecimal = bigDecimal.round(mc);

        System.out.println("EUR to " + currencyCode + " exchange rate changed by " + bigDecimal + " points (from " + startingdate + " to " + endingDate + ")");
    }
}
