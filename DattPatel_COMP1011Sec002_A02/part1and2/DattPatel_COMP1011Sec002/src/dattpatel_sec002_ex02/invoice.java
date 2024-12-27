package dattpatel_sec002_ex02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class invoice {
    private final String partNumber;
    private final String partDescription;
    private int quantity;
    private double pricePerItem;

    // constructor
    public invoice(String partNumber, String partDescription, int quantity, double pricePerItem) {
        if (quantity < 0) { // validate quantity
            throw new IllegalArgumentException("Quantity must be >= 0");
        }

        if (pricePerItem < 0) { // validate pricePerItem
            throw new IllegalArgumentException("Price per each product can be consider as >= 0");
        }

        this.quantity = quantity;
        this.partNumber = partNumber;
        this.partDescription = partDescription;
        this.pricePerItem = pricePerItem;
    }

    // get part number
    public String getPartNumber() {
        return partNumber;
    }

    // get description
    public String getPartDescription() {
        return partDescription;
    }

    // set quantity
    public void setQuantity(int quantity) {
        if (quantity < 0) { // validate quantity
            throw new IllegalArgumentException("Quantity must be >= 0");
        }

        this.quantity = quantity;
    }

    // get quantity
    public int getQuantity() {
        return quantity;
    }

    // set price per item
    public void setPricePerItem(double pricePerItem) {
        if (pricePerItem < 0.0) {// validate pricePerItem
            throw new IllegalArgumentException("Price per item must be >= 0");
        }

        this.pricePerItem = pricePerItem;
    }

    // get price per item
    public double getPricePerItem() {
        return pricePerItem;
    }

    // return String representation of invoice object
    @Override
    public String toString() {
        return String.format("%s: %n%s: %s (%s) %n%s: %d %n%s: $%,.2f",
                "invoice", "part number", getPartNumber(), getPartDescription(),
                "quantity", getQuantity(), "price per item", getPricePerItem());
    }

    // return amount of this invoice
    public double getinvoiceAmount() {
        return getQuantity() * getPricePerItem(); // calculate total cost
    }

    public static void main(String[] args) {
        // Sample data
        invoice[] invoices = {
                new invoice("24", "Power Saw", 18, 99.99),
                new invoice("7", "Sledge Hammer", 11, 21.50),
                new invoice("68", "Screw Driver", 106, 6.99),
                new invoice("39", "Lawn Mower", 3, 79.50)
        };

        // Convert array to list for easier manipulation with streams
        List<invoice> invoiceList = Arrays.asList(invoices);

        // a) Sorting invoices by partDescription
        List<invoice> sortedByPartDescription = invoiceList.stream()
                .sorted(Comparator.comparing(invoice::getPartDescription))
                .collect(Collectors.toList());
        System.out.println("Part Description sepration by catagory:");
        sortedByPartDescription.forEach(System.out::println);

        System.out.println();

        // b) Sorting invoices by pricePerItem
        List<invoice> sortedByPricePerItem = invoiceList.stream()
                .sorted(Comparator.comparing(invoice::getPricePerItem))
                .collect(Collectors.toList());
        System.out.println("Item seperation by price:");
        sortedByPricePerItem.forEach(System.out::println);

        System.out.println();

        // c) Mapping each invoice to its partDescription and quantity, then sorting by quantity
        List<String> partDescAndQuantity = invoiceList.stream()
                .map(invoice -> invoice.getPartDescription() + " - " + invoice.getQuantity())
                .sorted(Comparator.comparing(str -> Integer.parseInt(str.split(" - ")[1])))
                .collect(Collectors.toList());
        System.out.println("Part Description and Quantity, seperated via Quantity:");
        partDescAndQuantity.forEach(System.out::println);

        System.out.println();

        // d) Mapping each invoice to its partDescription and value (quantity * pricePerItem), then sorting by invoice value
        List<String> partDescAndValue = invoiceList.stream()
                .map(invoice -> invoice.getPartDescription() + " - $" + invoice.getinvoiceAmount())
                .sorted(Comparator.comparing(str -> Double.parseDouble(str.split(" - ")[1].substring(1))))
                .collect(Collectors.toList());
        System.out.println("Part Description and Value, sorted by invoice Value:");
        partDescAndValue.forEach(System.out::println);

        System.out.println();

        // e) Filtering invoices with value in range $200 to $500
        List<String> partDescAndValueInRange = invoiceList.stream()
                .filter(invoice -> invoice.getinvoiceAmount() >= 200 && invoice.getinvoiceAmount() <= 500)
                .map(invoice -> invoice.getPartDescription() + " - $" + invoice.getinvoiceAmount())
                .collect(Collectors.toList());
        System.out.println("Part Description and Value in range $200 to $500:");
        partDescAndValueInRange.forEach(System.out::println);

        System.out.println();

        // f) Finding any invoice with partDescription containing the word "saw"
        invoice sawinvoice = invoiceList.stream()
                .filter(invoice -> invoice.getPartDescription().toLowerCase().contains("saw"))
                .findFirst()
                .orElse(null);
        System.out.println("invoice with Part Description containing 'saw':");
        if (sawinvoice != null) {
            System.out.println(sawinvoice);
        } else {
            System.out.println("No invoice found with partDescription containing 'saw'.");
        }
    }
}