import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.Format;
import java.util.*;

public class ManPatRec extends ManLabReq {
    public static void AddPatient(ArrayList<PatRec> Patients) {
        Scanner Scan = new Scanner(System.in);
        System.out.print("First Name: ");
        String FirstName = Scan.nextLine();
        System.out.print("Last Name: ");
        String LastName = Scan.nextLine();
        System.out.print("Middle Name: ");
        String MiddleName = Scan.nextLine();
        String Birthday;
        do {
            System.out.print("Birthday(YYYYMMDD): ");
            Birthday = Scan.nextLine();
            if (Birthday.length() != 8) System.out.println("Invalid Input!\n");
        } while (Birthday.length() != 8);
        String Gender;
        do {
            System.out.print("Gender[M/F]: ");
            Gender = Scan.nextLine();
            if (!Gender.equals("M") && !Gender.equals("m") && !Gender.equals("F") && !Gender.equals("f"))
                System.out.println("Invalid Input!\n");
        } while (!Gender.equals("M") && !Gender.equals("m") && !Gender.equals("F") && !Gender.equals("f"));
        if (Gender.equals("m")) Gender = "M";
        if (Gender.equals("f")) Gender = "F";
        System.out.print("Address: ");
        String Address = Scan.nextLine();
        System.out.print("Phone No.: ");
        String PhoneNO = Scan.nextLine();
        System.out.print("National ID no.: ");
        String NatID = Scan.nextLine();
        String Save;
        do {
            System.out.print("Save Patient Record[Y/N]: ");
            Save = Scan.nextLine();
            if (!Save.equals("Y") && !Save.equals("y") && !Save.equals("N") && !Save.equals("n"))
                System.out.println("Invalid Input!\n");
        } while (!Save.equals("Y") && !Save.equals("y") && !Save.equals("N") && !Save.equals("n"));
        if (Save.equals("Y") || Save.equals("y")) {
            Patients.add(new PatRec(GenPatUID(Patients), LastName, FirstName, MiddleName, Birthday, Gender.charAt(0), Address, PhoneNO, NatID));
            System.out.println("Data of patient " + Patients.get(Patients.size() - 1).getPUID() + " has been added.");
            FileWriter AddPatientsFile = null;
            try {
                AddPatientsFile = new FileWriter("data/patients.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (PatRec patient : Patients) {
                if (patient.getDelIndicator() == 'D') {
                    try {
                        if (AddPatientsFile != null) {
                            AddPatientsFile.write(patient.getPUID() + ";" + patient.getLastName() + ";" + patient.getFirstName() + ";" + patient.getMiddleName() + ";" + patient.getBirthday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNum() + ";" + patient.getNatID() + ";" + patient.getDelIndicator() + ";" + patient.getDelReason() + ";" + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (AddPatientsFile != null) {
                            AddPatientsFile.write(patient.getPUID() + ";" + patient.getLastName() + ";" + patient.getFirstName() + ";" + patient.getMiddleName() + ";" + patient.getBirthday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNum() + ";" + patient.getNatID() + ";" + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if (AddPatientsFile != null) {
                    AddPatientsFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Scan.close();
    }

    public static void EditPatient(ArrayList<PatRec> Patients) {
        String Again;
        Scanner Scan = new Scanner(System.in);
        String DYKUID;
        do {
            boolean FindFlag = false;
            int RealIndex = 0;
            do {
                System.out.print("Do you know the UID[Y/N]: ");
                DYKUID = Scan.nextLine();
                if (!DYKUID.equals("Y") && !DYKUID.equals("y") && !DYKUID.equals("N") && !DYKUID.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!DYKUID.equals("Y") && !DYKUID.equals("y") && !DYKUID.equals("N") && !DYKUID.equals("n"));

            if (DYKUID.equals("Y") || DYKUID.equals("y")) {
                System.out.print("Enter Patient's UID: ");
                String PUID = Scan.nextLine();
                for (int i = 0; i < Patients.size(); i++)
                    if (Patients.get(i).getPUID().equals(PUID) && Patients.get(i).getDelIndicator() != 'D') {
                        FindFlag = true;
                        RealIndex = i;
                    }
            } else {
                String DYKNID;
                do {
                    System.out.print("Do you know the National ID no.[Y/N]: ");
                    DYKNID = Scan.nextLine();
                    if (!DYKNID.equals("Y") && !DYKNID.equals("y") && !DYKNID.equals("N") && !DYKNID.equals("n"))
                        System.out.println("Invalid Input!\n");
                } while (!DYKNID.equals("Y") && !DYKNID.equals("y") && !DYKNID.equals("N") && !DYKNID.equals("n"));

                if (DYKNID.equals("Y") || DYKNID.equals("y")) {
                    System.out.print("Enter National ID no.: ");
                    String NatID = Scan.nextLine();
                    for (int i = 0; i < Patients.size(); i++)
                        if (Patients.get(i).getNatID().equals(NatID) && Patients.get(i).getDelIndicator() != 'D') {
                            FindFlag = true;
                            RealIndex = i;
                        }
                } else {
                    ArrayList<Integer> Index = new ArrayList<>();
                    System.out.print("Last Name: ");
                    String LastName = Scan.nextLine();
                    System.out.print("First Name: ");
                    String FirstName = Scan.nextLine();
                    String Birthday;
                    do {
                        System.out.print("Birthday(YYYYMMDD): ");
                        Birthday = Scan.nextLine();
                        if (Birthday.length() != 8) System.out.println("Invalid Input!\n");
                    } while (Birthday.length() != 8);
                    for (int i = 0; i < Patients.size(); i++)
                        if (Patients.get(i).getLastName().equals(LastName) && Patients.get(i).getFirstName().equals(FirstName) && Patients.get(i).getBirthday().equals(Birthday) && Patients.get(i).getDelIndicator() != 'D') {
                            FindFlag = true;
                            Index.add(i);
                        }
                    if (FindFlag) {
                        if (Index.size() > 1) {
                            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-40s %-20s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID no.");
                            for (Integer index : Index)
                                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-40s %-20s %-20s %n", Patients.get(index).getPUID(), Patients.get(index).getLastName(), Patients.get(index).getFirstName(), Patients.get(index).getMiddleName(), Patients.get(index).getBirthday(), Patients.get(index).getGender(), Patients.get(index).getAddress(), Patients.get(index).getPhoneNum(), Patients.get(index).getNatID());

                            System.out.print("Enter the Patient's UID that you want to update: ");
                            String PUID = Scan.nextLine();
                            for (int i = 0; i < Patients.size(); i++)
                                if (Patients.get(i).getPUID().equals(PUID) && Patients.get(i).getDelIndicator() != 'D')
                                    RealIndex = i;
                        } else RealIndex = Index.get(0);
                    }
                }
            }
            if (FindFlag) {
                String Update;
                do {
                    System.out.print("Which data to update[(1)Address/(2)Phone Number]: ");
                    Update = Scan.nextLine();
                    if (!Update.equals("1") && !Update.equals("2")) System.out.println("Invalid Input!\n");
                } while (!Update.equals("1") && !Update.equals("2"));
                switch (Update) {
                    case "1" -> {
                        System.out.print("Patient's new address: ");
                        String NewAddress = Scan.nextLine();
                        Patients.get(RealIndex).setAddress(NewAddress);
                        System.out.println("The Address of patient " + Patients.get(RealIndex).getPUID() + " has been updated.");
                    }
                    case "2" -> {
                        System.out.print("Patient's new phone number: ");
                        String NewPhoneNum = Scan.nextLine();
                        Patients.get(RealIndex).setPhoneNum(NewPhoneNum);
                        System.out.println("The phone number of patient " + Patients.get(RealIndex).getPUID() + " has been updated.");
                    }
                }
                FileWriter AddPatientsFile = null;
                try {
                    AddPatientsFile = new FileWriter("data/patients.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (PatRec patient : Patients) {
                    if (patient.getDelIndicator() == 'D') {
                        try {
                            if (AddPatientsFile != null) {
                                AddPatientsFile.write(patient.getPUID() + ";" + patient.getLastName() + ";" + patient.getFirstName() + ";" + patient.getMiddleName() + ";" + patient.getBirthday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNum() + ";" + patient.getNatID() + ";" + patient.getDelIndicator() + ";" + patient.getDelReason() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (AddPatientsFile != null) {
                                AddPatientsFile.write(patient.getPUID() + ";" + patient.getLastName() + ";" + patient.getFirstName() + ";" + patient.getMiddleName() + ";" + patient.getBirthday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNum() + ";" + patient.getNatID() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (AddPatientsFile != null) {
                        AddPatientsFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else System.out.println("No record found.");
            do {
                System.out.print("Do you want to edit another patient record[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void DeletePatient(ArrayList<PatRec> Patients) {
        String Again;
        Scanner Scan = new Scanner(System.in);
        String DYKUID;
        do {
            boolean FindFlag = false;
            int RealIndex = 0;
            do {
                System.out.print("Do you know the UID[Y/N]: ");
                DYKUID = Scan.nextLine();
                if (!DYKUID.equals("Y") && !DYKUID.equals("y") && !DYKUID.equals("N") && !DYKUID.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!DYKUID.equals("Y") && !DYKUID.equals("y") && !DYKUID.equals("N") && !DYKUID.equals("n"));

            if (DYKUID.equals("Y") || DYKUID.equals("y")) {
                System.out.print("Enter Patient's UID: ");
                String PUID = Scan.nextLine();
                for (int i = 0; i < Patients.size(); i++)
                    if (Patients.get(i).getPUID().equals(PUID) && Patients.get(i).getDelIndicator() != 'D') {
                        FindFlag = true;
                        RealIndex = i;
                    }
            } else {
                String DYKNID;
                do {
                    System.out.print("Do you know the National ID no.[Y/N]: ");
                    DYKNID = Scan.nextLine();
                    if (!DYKNID.equals("Y") && !DYKNID.equals("y") && !DYKNID.equals("N") && !DYKNID.equals("n"))
                        System.out.println("Invalid Input!\n");
                } while (!DYKNID.equals("Y") && !DYKNID.equals("y") && !DYKNID.equals("N") && !DYKNID.equals("n"));

                if (DYKNID.equals("Y") || DYKNID.equals("y")) {
                    System.out.print("Enter National ID no.: ");
                    String NatID = Scan.nextLine();
                    for (int i = 0; i < Patients.size(); i++)
                        if (Patients.get(i).getNatID().equals(NatID) && Patients.get(i).getDelIndicator() != 'D') {
                            FindFlag = true;
                            RealIndex = i;
                        }
                } else {
                    ArrayList<Integer> Index = new ArrayList<>();
                    System.out.print("Last Name: ");
                    String LastName = Scan.nextLine();
                    System.out.print("First Name: ");
                    String FirstName = Scan.nextLine();
                    String Birthday;
                    do {
                        System.out.print("Birthday(YYYYMMDD): ");
                        Birthday = Scan.nextLine();
                        if (Birthday.length() != 8) System.out.println("Invalid Input!\n");
                    } while (Birthday.length() != 8);

                    for (int i = 0; i < Patients.size(); i++)
                        if (Patients.get(i).getLastName().equals(LastName) && Patients.get(i).getFirstName().equals(FirstName) && Patients.get(i).getBirthday().equals(Birthday) && Patients.get(i).getDelIndicator() != 'D') {
                            FindFlag = true;
                            Index.add(i);
                        }
                    if (FindFlag) {
                        if (Index.size() > 1) {
                            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-40s %-20s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID no.");
                            for (Integer index : Index)
                                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-40s %-20s %-20s %n", Patients.get(index).getPUID(), Patients.get(index).getLastName(), Patients.get(index).getFirstName(), Patients.get(index).getMiddleName(), Patients.get(index).getBirthday(), Patients.get(index).getGender(), Patients.get(index).getAddress(), Patients.get(index).getPhoneNum(), Patients.get(index).getNatID());
                            boolean PUIDFlag = true;
                            do {
                                System.out.print("Enter the Patient's UID that you want to delete: ");
                                String PUID = Scan.nextLine();
                                for (int i = 0; i < Patients.size(); i++)
                                    if (Patients.get(i).getPUID().equals(PUID) && Patients.get(i).getDelIndicator() != 'D') {
                                        RealIndex = i;
                                        PUIDFlag = false;
                                    }
                                if (PUIDFlag) System.out.println("Invalid Input!\n");
                            } while (PUIDFlag);
                        } else RealIndex = Index.get(0);
                    }
                }
            }
            if (FindFlag) {
                System.out.print("Reason for deletion: ");
                Patients.get(RealIndex).setDelIndicator('D');
                Patients.get(RealIndex).setDelReason(Scan.nextLine());
                System.out.println("Data of patient " + Patients.get(RealIndex).getPUID() + " has been deleted");
                FileWriter AddPatientsFile = null;
                try {
                    AddPatientsFile = new FileWriter("data/patients.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (PatRec patient : Patients) {
                    if (patient.getDelIndicator() == 'D') {
                        try {
                            if (AddPatientsFile != null) {
                                AddPatientsFile.write(patient.getPUID() + ";" + patient.getLastName() + ";" + patient.getFirstName() + ";" + patient.getMiddleName() + ";" + patient.getBirthday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNum() + ";" + patient.getNatID() + ";" + patient.getDelIndicator() + ";" + patient.getDelReason() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (AddPatientsFile != null) {
                                AddPatientsFile.write(patient.getPUID() + ";" + patient.getLastName() + ";" + patient.getFirstName() + ";" + patient.getMiddleName() + ";" + patient.getBirthday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNum() + ";" + patient.getNatID() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (AddPatientsFile != null) {
                        AddPatientsFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else System.out.println("No record found.");
            do {
                System.out.print("Do you want to delete another patient record[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void SearchPatient(ArrayList<PatRec> Patients, ArrayList<LabReq> LabReqs, ArrayList<Serv> Services) {
        String Again;
        Scanner Scan = new Scanner(System.in);
        String DYKUID;
        do {
            boolean FindFlag = false;
            int RealIndex = 0;
            do {
                System.out.print("Do you know the UID[Y/N]: ");
                DYKUID = Scan.nextLine();
                if (!DYKUID.equals("Y") && !DYKUID.equals("y") && !DYKUID.equals("N") && !DYKUID.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!DYKUID.equals("Y") && !DYKUID.equals("y") && !DYKUID.equals("N") && !DYKUID.equals("n"));

            if (DYKUID.equals("Y") || DYKUID.equals("y")) {
                System.out.print("Enter Patient's UID: ");
                String PUID = Scan.nextLine();
                for (int i = 0; i < Patients.size(); i++)
                    if (Patients.get(i).getPUID().equals(PUID) && Patients.get(i).getDelIndicator() != 'D') {
                        FindFlag = true;
                        RealIndex = i;
                    }
            } else {
                String DYKNID;
                do {
                    System.out.print("Do you know the National ID no.[Y/N]: ");
                    DYKNID = Scan.nextLine();
                    if (!DYKNID.equals("Y") && !DYKNID.equals("y") && !DYKNID.equals("N") && !DYKNID.equals("n"))
                        System.out.println("Invalid Input!\n");
                } while (!DYKNID.equals("Y") && !DYKNID.equals("y") && !DYKNID.equals("N") && !DYKNID.equals("n"));

                if (DYKNID.equals("Y") || DYKNID.equals("y")) {
                    System.out.print("Enter National ID no.: ");
                    String NatID = Scan.nextLine();
                    for (int i = 0; i < Patients.size(); i++)
                        if (Patients.get(i).getNatID().equals(NatID) && Patients.get(i).getDelIndicator() != 'D') {
                            FindFlag = true;
                            RealIndex = i;
                        }
                } else {
                    ArrayList<Integer> Index = new ArrayList<>();
                    System.out.print("Last Name: ");
                    String LastName = Scan.nextLine();
                    System.out.print("First Name: ");
                    String FirstName = Scan.nextLine();
                    String Birthday;
                    do {
                        System.out.print("Birthday(YYYYMMDD): ");
                        Birthday = Scan.nextLine();
                        if (Birthday.length() != 8) System.out.println("Invalid Input!\n");
                    } while (Birthday.length() != 8);
                    for (int i = 0; i < Patients.size(); i++)
                        if (Patients.get(i).getLastName().equals(LastName) && Patients.get(i).getFirstName().equals(FirstName) && Patients.get(i).getBirthday().equals(Birthday) && Patients.get(i).getDelIndicator() != 'D') {
                            FindFlag = true;
                            Index.add(i);
                        }
                    if (FindFlag) {
                        if (Index.size() > 1) {
                            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-40s %-20s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID no.");
                            for (Integer index : Index)
                                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-40s %-20s %-20s %n", Patients.get(index).getPUID(), Patients.get(index).getLastName(), Patients.get(index).getFirstName(), Patients.get(index).getMiddleName(), Patients.get(index).getBirthday(), Patients.get(index).getGender(), Patients.get(index).getAddress(), Patients.get(index).getPhoneNum(), Patients.get(index).getNatID());
                            System.out.print("Enter the Patient's UID that you want to display: ");
                            String PUID = Scan.nextLine();
                            for (int i = 0; i < Patients.size(); i++)
                                if (Patients.get(i).getPUID().equals(PUID) && Patients.get(i).getDelIndicator() != 'D')
                                    RealIndex = i;
                        } else RealIndex = Index.get(0);
                    }
                }
            }
            if (FindFlag) {
                ArrayList<Integer> LabReqsIndex = new ArrayList<>();
                System.out.printf("%-18s", "Patient's UID:");
                System.out.println(Patients.get(RealIndex).getPUID());
                System.out.printf("%-18s", "Name:");
                System.out.println(Patients.get(RealIndex).getLastName() + ", " + Patients.get(RealIndex).getFirstName() + " " + Patients.get(RealIndex).getMiddleName());
                System.out.printf("%-18s", "Birthday:");
                System.out.println(Patients.get(RealIndex).getBirthday());
                System.out.printf("%-18s", "Address:");
                System.out.println(Patients.get(RealIndex).getAddress());
                System.out.printf("%-18s", "Phone Number:");
                System.out.println(Patients.get(RealIndex).getPhoneNum());
                System.out.printf("%-18s", "National ID no.:");
                System.out.println(Patients.get(RealIndex).getNatID());
                System.out.println();
                System.out.printf("%-40s %-40s %-40s %-40s %n", "Request's UID", "Lab Test Type", "Request Date", "Result");
                LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqUID)));
                LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqDate)));
                for (int i = 0; i < LabReqs.size(); i++)
                    if (Patients.get(RealIndex).getPUID().equals(LabReqs.get(i).getPUID()) && LabReqs.get(i).getDelIndicator() != 'D')
                        LabReqsIndex.add(i);
                if (LabReqsIndex.size() == 0)
                    System.out.printf("%-40s %-40s %-40s %-40s %n", "No data", "No data", "No data", "No data");
                else {
                    for (Integer labReqsIndex : LabReqsIndex) {
                        int ServIndex = 0;
                        for (int j = 0; j < Services.size(); j++)
                            if (LabReqs.get(labReqsIndex).getReqUID().substring(0, 3).equals(Services.get(j).getServiceCode()))
                                ServIndex = j;
                        System.out.printf("%-40s %-40s %-40s %-40s %n", LabReqs.get(labReqsIndex).getReqUID(), Services.get(ServIndex).getDescription(), LabReqs.get(labReqsIndex).getReqDate(), LabReqs.get(labReqsIndex).getResult());
                    }
                    String Print;

                    do {
                        System.out.print("\nDo you want to print a laboratory test result[Y/N]: ");
                        Print = Scan.nextLine();
                        if (!Print.equals("Y") && !Print.equals("y") && !Print.equals("N") && !Print.equals("n"))
                            System.out.println("Invalid Input!\n");
                    } while (!Print.equals("Y") && !Print.equals("y") && !Print.equals("N") && !Print.equals("n"));
                    if (Print.equals("Y") || Print.equals("y")) {
                        boolean RUIDPrintFlag = true;
                        int RUIDindex = 0;
                        do {
                            do {
                                System.out.print("Which Request UID do you want to print: ");
                                String RUIDPrint = Scan.nextLine();
                                for (int i = 0; i < LabReqs.size(); i++)
                                    if (LabReqs.get(i).getReqUID().equals(RUIDPrint) && LabReqs.get(i).getDelIndicator() != 'D' && Patients.get(RealIndex).getPUID().equals(LabReqs.get(i).getPUID())) {
                                        RUIDPrintFlag = false;
                                        RUIDindex = i;
                                    }
                                if (RUIDPrintFlag) System.out.println("Invalid Input!\n");
                            } while (RUIDPrintFlag);

                            try {
                                Document doc = new Document();
                                PdfWriter writer;
                                writer = PdfWriter.getInstance(doc, new FileOutputStream("file/" + Patients.get(RealIndex).getLastName() + "_" + LabReqs.get(RUIDindex).getReqUID() + "_" + LabReqs.get(RUIDindex).getReqDate() + ".pdf"));
                                doc.open();
                                Image logo;
                                logo = Image.getInstance("logo/logo.png");
                                if (logo != null) {
                                    logo.setAlignment(Element.ALIGN_CENTER);
                                }
                                if (logo != null) {
                                    logo.scaleAbsolute(65f, 50f);
                                }
                                doc.add(logo);
                                Paragraph AddNo = new Paragraph("123 Sesame St.\n8123-4567\n\n");
                                AddNo.setAlignment(Element.ALIGN_CENTER);
                                doc.add(AddNo);
                                PdfPTable table1 = new PdfPTable(2);
                                PdfPCell Cell1t1 = new PdfPCell(new Phrase("Name: " + Patients.get(RealIndex).getLastName() + ", " + Patients.get(RealIndex).getFirstName() + " " + Patients.get(RealIndex).getMiddleName()));
                                PdfPCell Cell2t1 = new PdfPCell(new Phrase("Specimen ID: " + LabReqs.get(RUIDindex).getReqUID()));
                                PdfPCell Cell3t1 = new PdfPCell(new Phrase("Patient ID: " + Patients.get(RealIndex).getPUID()));
                                PdfPCell Cell4t1 = new PdfPCell(new Phrase("Collection Date: " + LabReqs.get(RUIDindex).getReqDate()));
                                Format Y = new SimpleDateFormat("yyyy");
                                int Age = Integer.parseInt(Y.format(new Date())) - Integer.parseInt(Patients.get(RealIndex).getBirthday().substring(0, 4));
                                Format M = new SimpleDateFormat("MM");
                                Format D = new SimpleDateFormat("dd");
                                if (Integer.parseInt(M.format(new Date())) < Integer.parseInt(Patients.get(RealIndex).getBirthday().substring(4, 6)))
                                    Age--;
                                else if (Integer.parseInt(M.format(new Date())) == Integer.parseInt(Patients.get(RealIndex).getBirthday().substring(4, 6)) && Integer.parseInt(D.format(new Date())) < Integer.parseInt(Patients.get(RealIndex).getBirthday().substring(6, 8)))
                                    Age--;
                                PdfPCell Cell5t1 = new PdfPCell(new Phrase("Age: " + Age));
                                PdfPCell Cell6t1 = new PdfPCell(new Phrase("Birthday: " + Patients.get(RealIndex).getBirthday()));
                                PdfPCell Cell7t1 = new PdfPCell(new Phrase("Gender: " + Patients.get(RealIndex).getGender()));
                                PdfPCell Cell8t1 = new PdfPCell(new Phrase("Phone Number: " + Patients.get(RealIndex).getPhoneNum()));
                                Cell1t1.setBorder(Rectangle.TOP);
                                Cell2t1.setBorder(Rectangle.TOP);
                                Cell3t1.setBorder(Rectangle.NO_BORDER);
                                Cell4t1.setBorder(Rectangle.NO_BORDER);
                                Cell5t1.setBorder(Rectangle.NO_BORDER);
                                Cell6t1.setBorder(Rectangle.NO_BORDER);
                                Cell7t1.setBorder(Rectangle.BOTTOM);
                                Cell8t1.setBorder(Rectangle.BOTTOM);
                                table1.addCell(Cell1t1);
                                table1.addCell(Cell2t1);
                                table1.addCell(Cell3t1);
                                table1.addCell(Cell4t1);
                                table1.addCell(Cell5t1);
                                table1.addCell(Cell6t1);
                                table1.addCell(Cell7t1);
                                table1.addCell(Cell8t1);
                                doc.add(table1);
                                doc.add(new Paragraph("\n\n"));
                                PdfPTable table2 = new PdfPTable(2);
                                PdfPCell Cell1t2 = new PdfPCell(new Phrase("Test", FontFactory.getFont("Helvetica", 12, Font.BOLD)));
                                PdfPCell Cell2t2 = new PdfPCell(new Phrase("Result", FontFactory.getFont("Helvetica", 12, Font.BOLD)));
                                int ServiceIndex = 0;
                                for (int i = 0; i < Services.size(); i++)
                                    if (Services.get(i).getServiceCode().equals(LabReqs.get(RUIDindex).getReqUID().substring(0, 3)))
                                        ServiceIndex = i;
                                PdfPCell Cell3t2 = new PdfPCell(new Phrase(Services.get(ServiceIndex).getDescription()));
                                PdfPCell Cell4t2 = new PdfPCell(new Phrase(LabReqs.get(RUIDindex).getResult()));
                                table2.addCell(Cell1t2);
                                table2.addCell(Cell2t2);
                                table2.addCell(Cell3t2);
                                table2.addCell(Cell4t2);
                                doc.add(table2);
                                doc.add(new Paragraph("\n\n\n"));
                                PdfPTable table3 = new PdfPTable(2);
                                PdfPCell BlankB = new PdfPCell(new Phrase(" "));
                                PdfPCell Blank = new PdfPCell(new Phrase(" "));
                                PdfPCell Cell1t3 = new PdfPCell(new Phrase("Jean Cabrera"));
                                PdfPCell Cell2t3 = new PdfPCell(new Phrase("Bentley Lu"));
                                PdfPCell Cell3t3 = new PdfPCell(new Phrase("Medical Technologist"));
                                PdfPCell Cell4t3 = new PdfPCell(new Phrase("Oncologist"));
                                PdfPCell Cell5t3 = new PdfPCell(new Phrase("Lic. # 123456789"));
                                PdfPCell Cell6t3 = new PdfPCell(new Phrase("Lic. # 987654321"));
                                BlankB.setBorder(Rectangle.TOP);
                                Blank.setBorder(Rectangle.NO_BORDER);
                                Cell1t3.setBorder(Rectangle.NO_BORDER);
                                Cell2t3.setBorder(Rectangle.NO_BORDER);
                                Cell3t3.setBorder(Rectangle.NO_BORDER);
                                Cell4t3.setBorder(Rectangle.NO_BORDER);
                                Cell5t3.setBorder(Rectangle.NO_BORDER);
                                Cell6t3.setBorder(Rectangle.NO_BORDER);
                                table3.addCell(BlankB);
                                table3.addCell(BlankB);
                                table3.addCell(Blank);
                                table3.addCell(Blank);
                                table3.addCell(Cell1t3);
                                table3.addCell(Cell2t3);
                                table3.addCell(Cell3t3);
                                table3.addCell(Cell4t3);
                                table3.addCell(Cell5t3);
                                table3.addCell(Cell6t3);
                                table3.addCell(Blank);
                                table3.addCell(Blank);
                                doc.add(table3);
                                doc.close();
                                writer.close();
                                System.out.println(Patients.get(RealIndex).getLastName() + "_" + LabReqs.get(RUIDindex).getReqUID() + "_" + LabReqs.get(RUIDindex).getReqDate() + ".pdf has been saved to " + System.getProperty("user.dir") + "\\file" + "\n");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            do {
                                System.out.print("Do you want to print a laboratory test result again[Y/N]: ");
                                Print = Scan.nextLine();
                                if (!Print.equals("Y") && !Print.equals("y") && !Print.equals("N") && !Print.equals("n"))
                                    System.out.println("Invalid Input!\n");
                            } while (!Print.equals("Y") && !Print.equals("y") && !Print.equals("N") && !Print.equals("n"));
                        } while (Print.equals("Y") || Print.equals("y"));
                    }
                }


            } else System.out.println("No record found.");
            do {
                System.out.print("Do you want to search another patient record[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }


    public static String GenPatUID(ArrayList<PatRec> Patients) {
        char First, Second, Third;
        String StrNum;
        int num = 0;
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Format M = new SimpleDateFormat("MM");
        Format Y = new SimpleDateFormat("yyyy");
        for (PatRec patient : Patients)
            if (patient.getPUID().substring(1, 7).equals(Y.format(new Date()) + M.format(new Date()))) num++;
        First = Alphabet.charAt(num / 67600);
        num = num % 67600;
        Second = Alphabet.charAt(num / 2600);
        num = num % 2600;
        Third = Alphabet.charAt(num / 100);
        num = num % 100;
        if (num < 10) StrNum = "0" + num;
        else StrNum = String.valueOf(num);
        return "P" + Y.format(new Date()) + M.format(new Date()) + First + Second + Third + StrNum;
    }
}
