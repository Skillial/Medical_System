import java.util.ArrayList;
import java.util.Scanner;

public class App extends MedLabInfSys {
    public static void main(String[] args) {
        ArrayList<PatRec> Patients = new ArrayList<>();
        ArrayList<Serv> Services = new ArrayList<>();
        ArrayList<LabReq> LabReqs = new ArrayList<>();
        AddData(Patients, Services, LabReqs);
        Scanner Scan = new Scanner(System.in);
        String Transaction;

        do {
            do {
                System.out.println("Medical Laboratory Information System");
                System.out.println("[1] Manage Patient Records");
                System.out.println("[2] Manage Services");
                System.out.println("[3] Manage Laboratory Results");
                System.out.println("[X] Exit\n");
                System.out.print("Select a transaction: ");
                Transaction = Scan.nextLine();
                if (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("X") && !Transaction.equals("x"))
                    System.out.println("Invalid Input!\n");
            } while (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("X") && !Transaction.equals("x"));
            switch (Transaction) {
                case "1" -> ManagePatientRecords(Patients, LabReqs, Services);
                case "2" -> ManageServices(Services);
                case "3" -> ManageLabResults(Patients, Services, LabReqs);
            }
        } while (!Transaction.equals("x") && !Transaction.equals("X"));
//        Scan.close();
    }


}
