import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class MedLabInfSys extends ManServ {
    public static void ManagePatientRecords(ArrayList<PatRec> Patients, ArrayList<LabReq> Labreqs, ArrayList<Serv> Services) {
        Scanner Scan = new Scanner(System.in);
        String Transaction;
        do {
            System.out.println("Manage Patient Records");
            System.out.println("[1] Add New Patient");
            System.out.println("[2] Edit Patient Record");
            System.out.println("[3] Delete Patient Record");
            System.out.println("[4] Search Patient Record");
            System.out.println("[X] Return to Main menu\n");
            System.out.print("Select a transaction: ");
            Transaction = Scan.nextLine();
            if (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("4") && !Transaction.equals("X") && !Transaction.equals("x"))
                System.out.println("Invalid Input!\n");
        } while (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("4") && !Transaction.equals("X") && !Transaction.equals("x"));
        switch (Transaction) {
            case "1" -> AddPatient(Patients);
            case "2" -> EditPatient(Patients);
            case "3" -> DeletePatient(Patients);
            case "4" -> SearchPatient(Patients, Labreqs, Services);
        }
        //Scan.close();
    }

    public static void ManageServices(ArrayList<Serv> Services) {
        Scanner Scan = new Scanner(System.in);
        String Transaction;
        do {
            System.out.println("Manage Service Records");
            System.out.println("[1] Add New Service");
            System.out.println("[2] Search Service");
            System.out.println("[3] Delete Service");
            System.out.println("[4] Edit Service");
            System.out.println("[X] Return to Main menu\n");
            System.out.print("Select a transaction: ");
            Transaction = Scan.nextLine();
            if (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("4") && !Transaction.equals("X") && !Transaction.equals("x"))
                System.out.println("Invalid Input!\n");
        } while (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("4") && !Transaction.equals("X") && !Transaction.equals("x"));
        switch (Transaction) {
            case "1" -> AddService(Services, true);
            case "2" -> SearchService(Services);
            case "3" -> DeleteService(Services, true);
            case "4" -> EditService(Services);
        }
        //Scan.close();
    }

    public static void ManageLabResults(ArrayList<PatRec> Patients, ArrayList<Serv> Services, ArrayList<LabReq> LabReqs) {
        Scanner Scan = new Scanner(System.in);
        String Transaction;
        do {
            System.out.println("Manage Laboratory Request");
            System.out.println("[1] Add New Laboratory Request");
            System.out.println("[2] Search Laboratory Request");
            System.out.println("[3] Delete Laboratory Request");
            System.out.println("[4] Edit Laboratory Request");
            System.out.println("[X] Return to Main menu\n");
            System.out.print("Select a transaction: ");
            Transaction = Scan.nextLine();
            if (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("4") && !Transaction.equals("X") && !Transaction.equals("x"))
                System.out.println("Invalid Input!\n");
        } while (!Transaction.equals("1") && !Transaction.equals("2") && !Transaction.equals("3") && !Transaction.equals("4") && !Transaction.equals("X") && !Transaction.equals("x"));
        switch (Transaction) {
            case "1" -> AddLabReq(Patients, Services, LabReqs);
            case "2" -> SearchLabReq(LabReqs, Services);
            case "3" -> DeleteLabReq(LabReqs, Services);
            case "4" -> EditLabReq(LabReqs, Services);
        }
        //Scan.close();
    }

    public static void AddData(ArrayList<PatRec> Patients, ArrayList<Serv> Services, ArrayList<LabReq> LabReqs) {
        ArrayList<String> PatientsData = new ArrayList<>();
        File PatientsFile = new File("data/patients.txt");
        try {
            if (!PatientsFile.createNewFile()) {
                Scanner ScanPatientsFile = new Scanner(PatientsFile);
                while (ScanPatientsFile.hasNextLine())
                    PatientsData.add(ScanPatientsFile.nextLine());
                String[] PatientsInfo;
                for (String patientsDatum : PatientsData) {
                    PatientsInfo = patientsDatum.split(";");
                    if (PatientsInfo.length == 9)
                        Patients.add(new PatRec(PatientsInfo[0], PatientsInfo[1], PatientsInfo[2], PatientsInfo[3], PatientsInfo[4], PatientsInfo[5].charAt(0), PatientsInfo[6], PatientsInfo[7], PatientsInfo[8]));
                    else if (PatientsInfo.length == 11)
                        Patients.add(new PatRec(PatientsInfo[0], PatientsInfo[1], PatientsInfo[2], PatientsInfo[3], PatientsInfo[4], PatientsInfo[5].charAt(0), PatientsInfo[6], PatientsInfo[7], PatientsInfo[8], PatientsInfo[9].charAt(0), PatientsInfo[10]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> ServicesData = new ArrayList<>();
        File ServicesFile = new File("data/services.txt");
        try {
            if (!ServicesFile.createNewFile()) {
                Scanner ScanServicesFile = new Scanner(ServicesFile);
                while (ScanServicesFile.hasNextLine())
                    ServicesData.add(ScanServicesFile.nextLine());
                String[] ServicesInfo;
                for (String servicesDatum : ServicesData) {
                    ServicesInfo = servicesDatum.split(";");
                    if (ServicesInfo.length == 3)
                        Services.add(new Serv(ServicesInfo[0], ServicesInfo[1], Float.parseFloat(ServicesInfo[2])));
                    else if (ServicesInfo.length == 5)
                        Services.add(new Serv(ServicesInfo[0], ServicesInfo[1], Float.parseFloat(ServicesInfo[2]), ServicesInfo[3].charAt(0), ServicesInfo[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File LabReqFolder = new File("data/LabReq");
        File[] ListOfFiles = LabReqFolder.listFiles();
        for (File listoffile : Objects.requireNonNull(ListOfFiles)) {
            ArrayList<String> LabReqData = new ArrayList<>();
            File LabReqFile = new File("data/LabReq/" + listoffile.getName());
            try {
                if (!LabReqFile.createNewFile()) {
                    Scanner ScanLabReqFile = new Scanner(LabReqFile);
                    while (ScanLabReqFile.hasNextLine())
                        LabReqData.add(ScanLabReqFile.nextLine());
                    String[] LabReqInfo;
                    for (String labReqDatum : LabReqData) {
                        LabReqInfo = labReqDatum.split(";");
                        if (LabReqInfo.length == 5)
                            LabReqs.add(new LabReq(LabReqInfo[0], LabReqInfo[1], LabReqInfo[2], LabReqInfo[3], LabReqInfo[4]));
                        else if (LabReqInfo.length == 7)
                            LabReqs.add(new LabReq(LabReqInfo[0], LabReqInfo[1], LabReqInfo[2], LabReqInfo[3], LabReqInfo[4], LabReqInfo[5].charAt(0), LabReqInfo[6]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
