import java.util.ArrayList;
public class TestDriveData {

    private ArrayList<SalesPerson> testPersonsData = new ArrayList<SalesPerson>();

    public TestDriveData (){
        testPersonsData.add(new SalesPerson("David Sydney", 23000, 1150));
        testPersonsData.add(new SalesPerson("Scott Coles", 20000, 1000));
        testPersonsData.add(new SalesPerson("Joshua North", 72000, 4510));
        testPersonsData.add(new SalesPerson("Andrew Earl", 65000, 3950));
        testPersonsData.add(new SalesPerson("Max Andersen", 44000, 2390));
        testPersonsData.add(new SalesPerson("John Smith", 40000, 2150));
        testPersonsData.add(new SalesPerson("Bill Douglas", 60000, 3550));
        testPersonsData.add(new SalesPerson("Andy Bourke", 51000, 2830));
        testPersonsData.add(new SalesPerson("Tony Capella", 32000, 1670));
        testPersonsData.add(new SalesPerson("Shannon Mackay", 35000, 1850));
    }

    public ArrayList<SalesPerson> getTestPersonsData() {
        return testPersonsData;
    }
}
