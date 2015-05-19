import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalesDataManager {

    public final static int N = 10;

    Scanner scanner;
    ArrayList<SalesPerson> salesPersons;
    static int enteredNumber;

    public SalesDataManager() {
        TestDriveData testDriveData = new TestDriveData();
        scanner = new Scanner(System.in);

      salesPersons = new ArrayList<SalesPerson>();

//        // Get ArrayList with test drive data
//        salesPersons = testDriveData.getTestPersonsData();

        System.out.println("               Salas & Commissions Data Manager");
        System.out.println("              ----------------------------------");
        displayMenu();
    }

    public void displayMenu(){
        System.out.println("1. Input & validate data");
        System.out.println("2. Display");
        System.out.println("3. Sort by name");
        System.out.println("4. Sort by sales");
        System.out.println("5. Search by name");
        System.out.println("6. Search by sales");
        System.out.println("7. Display statistics");
        System.out.println("8. Exit");
        System.out.printf("%nEnter options 1-8: ");
    }

    // This method needed for get information about person (name, sales amount) from keyboard
    public void inputData() {

        String name;
        int salesAmount;
        double commission;

        for (int i = 1; i <= N; i++){

            System.out.printf("%nPlease enter name, then sales amount for a salesperson " + i + ": ");
            name = scanner.nextLine();

            while (true){
                if (!isValidName(name)) {
                System.out.println("Entered incorrect name, try again: ");
                name = scanner.nextLine();
                } else break;
            }

            System.out.printf("%nPlease enter sales amount: ");
            salesAmount = isNumber() * 1000;

            while (true){
                if (salesAmount < 10000 || salesAmount > 100000) {
                    System.out.println("Entered incorrect amount, try again: ");
                    salesAmount = isNumber() * 1000;
                } else break;
            }

            commission = calculateCommission(salesAmount);

            salesPersons.add(new SalesPerson(name, salesAmount, commission));
        }
    }

    // Print table with persons data to console
    public void display(){
        System.out.println("                              Salas and Commissions");
        System.out.println("              =====================================================");
        System.out.println("              Salesperson         Sales Amount         Commissions");
        System.out.println("              -----------------------------------------------------");
        printPersonsData();
    }

    // Sort ArrayList with sales persons data by name in alphabetical order (used bubble sort).
    // A built-in sort should not be used, according to the task.
    public void sortByName() {
        int i = 0;
        while (i < salesPersons.size()){
            int j = 0;
            while (j < salesPersons.size()- i - 1){

                char chr = salesPersons.get(j).getName().charAt(0);
                char nextChr = salesPersons.get(j+1).getName().charAt(0);

                if(chr == nextChr){
                    int k = 0;
                    while (chr == nextChr) {
                        chr = salesPersons.get(j).getName().charAt(k);
                        nextChr = salesPersons.get(j+1).getName().charAt(k);
                        k++;
                    }
                }

                if(chr > nextChr){
                    SalesPerson temp = salesPersons.get(j);
                    salesPersons.remove(j);
                    salesPersons.add(j+1, temp);
                }
                j++;
            }
            i++;
        }
        display();
    }

    // Sort ArrayList with sales persons data by sales amount in ascending order (used bubble sort).
    public void sortBySales() {
        for (int i = 0; i < salesPersons.size(); i++){
            for(int j = 0; j < salesPersons.size() - i - 1; j++){
                if(salesPersons.get(j).getSalesAmount() > salesPersons.get(j+1).getSalesAmount()){
                    SalesPerson temp = salesPersons.get(j);
                    salesPersons.remove(j);
                    salesPersons.add(j+1, temp);
                }
            }
        }
        display();
    }

    // Names search method with ignoring case.
    public void searchByName() {
        String desiredName;
        boolean isNameMatch = false;

        System.out.println("Please, enter a sales person name: ");
        desiredName = scanner.nextLine();

        for(int i = 0; i < salesPersons.size(); i++){
            if(salesPersons.get(i).getName().compareToIgnoreCase(desiredName) == 0){
                System.out.printf(salesPersons.get(i).getName() + " - sales amount: $" + salesPersons.get(i).getSalesAmount() +
                        " With commissions received: $" + salesPersons.get(i).getCommission() + "%n%n");
                isNameMatch = true;
                break;
            }
        }
        if (!isNameMatch) {
            System.out.printf("WARNING: no matches found%n%n");
        }
    }

    // Search sales persons who have sales amount less then entered from keyboard.
    // And print to console name and sales amount of person
    public void searchBySales() {
        int salesAmount;

        System.out.println("Please, enter a specific sales amount: ");
        salesAmount = isNumber() * 1000;

        while (true){
            if (salesAmount < 10000 || salesAmount > 100000) {
                System.out.println("Entered incorrect amount, try again: ");
                salesAmount = isNumber() * 1000;
            } else break;
        }
        System.out.printf("The following sales persons have less then &" + salesAmount + "%n%n");
        for (SalesPerson salesPerson : salesPersons) {
            if (salesPerson.getSalesAmount() < salesAmount) {
                System.out.printf("          %s  $%d%n", salesPerson.getName(), salesPerson.getSalesAmount());
            }
        }
        System.out.println();
    }

    // Print to console a simple statistics (person with lowest and highest sales amount and median sales amount)
    public void displayStatistics() {
        SalesPerson personWithLowestAmount = salesPersons.get(0);
        SalesPerson personWithHighestAmount = salesPersons.get(0);
        int medianSalesAmount;

        for(SalesPerson salesPerson : salesPersons) {
            if (salesPerson.getSalesAmount() < personWithLowestAmount.getSalesAmount()) {
                personWithLowestAmount = salesPerson;
            } if (salesPerson.getSalesAmount() > personWithHighestAmount.getSalesAmount()) {
                personWithHighestAmount = salesPerson;
            }
        }

        medianSalesAmount = (personWithLowestAmount.getSalesAmount() + personWithHighestAmount.getSalesAmount()) / 2;

        System.out.println("                    A simple statistics");
        System.out.printf("                    ===================%n");
        System.out.println("       The sales person has lowest sales amount is " + personWithLowestAmount.getName() +
                           ", $" + personWithLowestAmount.getSalesAmount());
        System.out.println("       The sales person has highest sales amount is " + personWithHighestAmount.getName() +
                ", $" + personWithHighestAmount.getSalesAmount());
        System.out.printf("        The median sales amount is $" + medianSalesAmount + "%n%n");


    }

    // Validate names data. Valid name is assumed only to contain English letters and space between first name and surname.
    // Return "true" if the conditions are met.
    public boolean isValidName(String name) {
        Pattern p =Pattern.compile("^([a-zA-Z]{3,15}) ([a-zA-Z]{3,15})$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    // Calculate commission method. Return commissions amount.
    // if sales amount $10000 - $25000 commission rate = 5%
    // if sales amount $25001 - $50000 commission rate = 6%
    // if sales amount $50001 - $75000 commission rate = 8%
    // if sales amount $75001 - $100000 commission rate = 10%
    public double calculateCommission(int salesAmount) {
        if (salesAmount <= 25000) {
            return salesAmount * 0.05;
        } else if(salesAmount > 25000 && salesAmount <= 50000) {
            return salesAmount * 0.06;
        } else if(salesAmount > 50000 && salesAmount <= 75000) {
            return salesAmount * 0.08;
        } else if (salesAmount > 75000 && salesAmount <= 100000) {
            return salesAmount * 0.1;
        }
        return 0.0;
    }

    // Checking the entered data from keyboard if this is a number, then method returned this number.
    public int isNumber(){
        while (true) {
            try {
                enteredNumber = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException ex){
                System.out.print("Entered incorrect number, try again: ");
                continue;
            }
        }
        return enteredNumber;
    }

    // Print to console information of persons
    public void printPersonsData(){
        for (SalesPerson salesPerson : salesPersons){
            System.out.printf("              %-20s%-20s $%-21.1f%n", salesPerson.getName(), salesPerson.getSalesAmount(), salesPerson.getCommission());
        }
        System.out.printf("%n              Total: " + salesPersons.size() + " data entries%n%n");
    }

    // Run program method
    public static void main(String[] args) {

        SalesDataManager salesDataManager = new SalesDataManager();

        while (salesDataManager.isNumber() != 8){
            switch (enteredNumber){
                case 1:
                    salesDataManager.inputData();
                    salesDataManager.displayMenu();
                    break;
                case 2:
                    salesDataManager.display();
                    salesDataManager.displayMenu();
                    break;
                case 3:
                    salesDataManager.sortByName();
                    salesDataManager.displayMenu();
                    break;
                case 4:
                    salesDataManager.sortBySales();
                    salesDataManager.displayMenu();
                    break;
                case 5:
                    salesDataManager.searchByName();
                    salesDataManager.displayMenu();
                    break;
                case 6:
                    salesDataManager.searchBySales();
                    salesDataManager.displayMenu();
                    break;
                case 7:
                    salesDataManager.displayStatistics();
                    salesDataManager.displayMenu();
                    break;
                default:
                    System.out.print("Entered incorrect options number, try again: ");
                    break;
            }
        }
    }
}
