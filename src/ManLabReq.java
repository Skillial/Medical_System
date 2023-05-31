import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManLabReq {
    public static void AddLabReq(ArrayList<PatRec> Patients, ArrayList<Serv> Services, ArrayList<LabReq> LabReqs) {
        String Again;
        Scanner Scan = new Scanner(System.in);
        do {
            String PUID, ServCode;
            int PUIDIndex = 0, ServCodeIndex = 0;
            boolean PUIDFlag = false;
            boolean ServCodeFlag = false;
            System.out.print("Patient UID: ");
            PUID = Scan.nextLine();
            System.out.print("Service Code: ");
            ServCode = Scan.nextLine();
            for (int i = 0; i < Patients.size(); i++)
                if (PUID.equals(Patients.get(i).getPUID()) && Patients.get(i).getDelIndicator() != 'D') {
                    PUIDFlag = true;
                    PUIDIndex = i;
                }
            if (PUIDFlag)
                for (int i = 0; i < Services.size(); i++)
                    if (ServCode.equals(Services.get(i).getServiceCode()) && Services.get(i).getDelIndicator() != 'D') {
                        ServCodeFlag = true;
                        ServCodeIndex = i;
                    }
            if (PUIDFlag && ServCodeFlag) {
                Format YMD = new SimpleDateFormat("yyyyMMdd");
                Format time = new SimpleDateFormat("HHmm");
                LabReqs.add(new LabReq(GenReqUID(ServCodeIndex, Services, LabReqs), Patients.get(PUIDIndex).getPUID(), YMD.format(new Date()), time.format(new Date()), "Result"));
                System.out.println("The Laboratory Request " + LabReqs.get(LabReqs.size() - 1).getReqUID() + " has been added to file "+ Services.get(ServCodeIndex).getServiceCode()+"_Requests.txt.");
                FileWriter AddLabReqFile = null;
                try {
                    AddLabReqFile = new FileWriter("data/LabReq/" + ServCode + "_Requests.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<Integer> Indexes = new ArrayList<>();
                for (int i = 0; i < LabReqs.size(); i++)
                    if (LabReqs.get(i).getReqUID().substring(0, 3).equals(ServCode))
                        Indexes.add(i);
                for (Integer index : Indexes) {
                    if (LabReqs.get(index).getDelIndicator() == 'D') {
                        try {
                            if (AddLabReqFile != null) {
                                AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + LabReqs.get(index).getDelIndicator() + ";" + LabReqs.get(index).getDelReason() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (AddLabReqFile != null) {
                                AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (AddLabReqFile != null) {
                        AddLabReqFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                System.out.println("No record found");
            do {
                System.out.print("Do you want to add another laboratory request[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void SearchLabReq(ArrayList<LabReq> LabReqs, ArrayList<Serv> Services) {
        String Again;
        Scanner Scan = new Scanner(System.in);
        String use;
        do {
            do {
                System.out.print("Use for search[(1) request's UID/ (2) patient's UID]: ");
                use = Scan.nextLine();
                if (!use.equals("1") && !use.equals("2"))
                    System.out.println("Invalid Input!\n");
            } while (!use.equals("1") && !use.equals("2"));

            switch (use) {
                case "1" -> {
                    int ServIndex = 0;
                    boolean FoundFlag = true;
                    int Index = 0;
                    do {
                        System.out.print("Request's UID: ");
                        String RUID = Scan.nextLine();
                        for (int i = 0; i < LabReqs.size(); i++)
                            if (RUID.equals(LabReqs.get(i).getReqUID()) && LabReqs.get(i).getDelIndicator() != 'D') {
                                Index = i;
                                FoundFlag = false;
                            }
                        if (FoundFlag)
                            System.out.println("Invalid Input!\n");
                    } while (FoundFlag);
                    for (int i = 0; i < Services.size(); i++)
                        if (LabReqs.get(Index).getReqUID().substring(0, 3).equals(Services.get(i).getServiceCode()))
                            ServIndex = i;
                    System.out.printf("%-40s %-40s %-40s %-40s %n", "Request's UID", "Lab Test Type", "Request Date", "Result");
                    System.out.printf("%-40s %-40s %-40s %-40s %n", LabReqs.get(Index).getReqUID(), Services.get(ServIndex).getDescription(), LabReqs.get(Index).getReqDate(), LabReqs.get(Index).getResult());
                }
                case "2" -> {
                    ArrayList<Integer> Indexes = new ArrayList<>();
                    System.out.print("Patient's UID: ");
                    String PUID = Scan.nextLine();
                    LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqUID)));
                    LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqDate)));
                    for (int i = 0; i < LabReqs.size(); i++)
                        if (PUID.equals(LabReqs.get(i).getPUID()) && LabReqs.get(i).getDelIndicator() != 'D')
                            Indexes.add(i);
                    if (Indexes.size() == 0)
                        System.out.println("No record found");
                    else {
                        int ServIndex = 0;
                        System.out.printf("%-40s %-40s %-40s %-40s %n", "Request's UID", "Lab Test Type", "Request Date", "Result");
                        for (Integer index : Indexes) {
                            for (int j = 0; j < Services.size(); j++)
                                if (LabReqs.get(index).getReqUID().substring(0, 3).equals(Services.get(j).getServiceCode()))
                                    ServIndex = j;
                            System.out.printf("%-40s %-40s %-40s %-40s %n", LabReqs.get(index).getReqUID(), Services.get(ServIndex).getDescription(), LabReqs.get(index).getReqDate(), LabReqs.get(index).getResult());
                        }
                    }
                }
            }
            do {
                System.out.print("Do you want to search another laboratory request[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void EditLabReq(ArrayList<LabReq> LabReqs, ArrayList<Serv> Services) {
        Scanner Scan = new Scanner(System.in);
        String Again;
        String use;
        do {
            do {
                System.out.print("Use for editing[(1) request's UID/ (2) patient's UID]: ");
                use = Scan.nextLine();
                if (!use.equals("1") && !use.equals("2"))
                    System.out.println("Invalid Input!\n");
            } while (!use.equals("1") && !use.equals("2"));

            switch (use) {
                case "1" -> {
                    boolean FoundFlag = true;
                    int Index = 0;
                    do {
                        System.out.print("Request's UID: ");
                        String RUID = Scan.nextLine();
                        for (int i = 0; i < LabReqs.size(); i++)
                            if (RUID.equals(LabReqs.get(i).getReqUID()) && LabReqs.get(i).getDelIndicator() != 'D' && LabReqs.get(i).getResult().equals("Result")) {
                                Index = i;
                                FoundFlag = false;
                            }
                        if (FoundFlag)
                            System.out.println("Invalid Input!\n");
                    } while (FoundFlag);
                    System.out.print("Result: ");
                    LabReqs.get(Index).setResult(Scan.nextLine());
                    System.out.println("The Laboratory Request " + LabReqs.get(Index).getReqUID() + " has been updated.");
                    FileWriter AddLabReqFile = null;
                    try {
                        AddLabReqFile = new FileWriter("data/LabReq/" + LabReqs.get(Index).getReqUID().substring(0, 3) + "_Requests.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Integer> Indexes = new ArrayList<>();
                    for (int i = 0; i < LabReqs.size(); i++)
                        if (LabReqs.get(i).getReqUID().substring(0, 3).equals(LabReqs.get(Index).getReqUID().substring(0, 3)))
                            Indexes.add(i);
                    for (Integer index : Indexes) {
                        if (LabReqs.get(index).getDelIndicator() == 'D') {
                            try {
                                if (AddLabReqFile != null) {
                                    AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + LabReqs.get(index).getDelIndicator() + ";" + LabReqs.get(index).getDelReason() + ";" + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (AddLabReqFile != null) {
                                    AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        if (AddLabReqFile != null) {
                            AddLabReqFile.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "2" -> {
                    int Index = 0;
                    ArrayList<Integer> Indexes = new ArrayList<>();
                    System.out.print("Patient's UID: ");
                    String PUID = Scan.nextLine();
                    LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqUID)));
                    LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqDate)));
                    for (int i = 0; i < LabReqs.size(); i++)
                        if (PUID.equals(LabReqs.get(i).getPUID()) && LabReqs.get(i).getDelIndicator() != 'D' && LabReqs.get(i).getResult().equals("Result"))
                            Indexes.add(i);
                    if (Indexes.size() == 0)
                        System.out.println("No record found");
                    else if (Indexes.size() == 1)
                        Index = Indexes.get(0);
                    else {
                        int ServIndex = 0;
                        System.out.printf("%-40s %-40s %-40s %-40s %n", "Request's UID", "Lab Test Type", "Request Date", "Result");
                        for (Integer index : Indexes) {
                            for (int j = 0; j < Services.size(); j++)
                                if (LabReqs.get(index).getReqUID().substring(0, 3).equals(Services.get(j).getServiceCode()))
                                    ServIndex = j;
                            System.out.printf("%-40s %-40s %-40s %-40s %n", LabReqs.get(index).getReqUID(), Services.get(ServIndex).getDescription(), LabReqs.get(index).getReqDate(), LabReqs.get(index).getResult());
                        }
                        boolean ReqIDFlag;
                        do {
                            ReqIDFlag = true;
                            System.out.print("Request's UID: ");
                            String ReqUID = Scan.nextLine();
                            for (int i = 0; i < LabReqs.size(); i++)
                                if (LabReqs.get(i).getReqUID().equals(ReqUID) && LabReqs.get(i).getDelIndicator() != 'D') {
                                    Index = i;
                                    ReqIDFlag = false;
                                }
                            if (ReqIDFlag)
                                System.out.println("Invalid Input!\n");
                        } while (ReqIDFlag);
                    }
                    if (Indexes.size() > 0) {
                        System.out.print("Result: ");
                        LabReqs.get(Index).setResult(Scan.nextLine());
                        System.out.println("The Laboratory Request " + LabReqs.get(Index).getReqUID() + " has been updated.");
                        FileWriter AddLabReqFile = null;
                        try {
                            AddLabReqFile = new FileWriter("data/LabReq/" + LabReqs.get(Index).getReqUID().substring(0, 3) + "_Requests.txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<Integer> AddIndexes = new ArrayList<>();
                        for (int i = 0; i < LabReqs.size(); i++)
                            if (LabReqs.get(i).getReqUID().substring(0, 3).equals(LabReqs.get(Index).getReqUID().substring(0, 3)))
                                AddIndexes.add(i);
                        for (Integer index : AddIndexes) {
                            if (LabReqs.get(index).getDelIndicator() == 'D') {
                                try {
                                    if (AddLabReqFile != null) {
                                        AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + LabReqs.get(index).getDelIndicator() + ";" + LabReqs.get(index).getDelReason() + ";" + "\n");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    if (AddLabReqFile != null) {
                                        AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + "\n");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            if (AddLabReqFile != null) {
                                AddLabReqFile.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            do {
                System.out.print("Do you want to edit another laboratory request[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void DeleteLabReq(ArrayList<LabReq> LabReqs, ArrayList<Serv> Services) {
        Scanner Scan = new Scanner(System.in);
        String Again;
        String use;
        do {
            do {
                System.out.print("Use for deletion[(1) request's UID/ (2) patient's UID]: ");
                use = Scan.nextLine();
                if (!use.equals("1") && !use.equals("2"))
                    System.out.println("Invalid Input!\n");
            } while (!use.equals("1") && !use.equals("2"));
            switch (use) {
                case "1" -> {
                    boolean FoundFlag = true;
                    int Index = 0;
                    do {
                        System.out.print("Request's UID: ");
                        String RUID = Scan.nextLine();
                        for (int i = 0; i < LabReqs.size(); i++)
                            if (RUID.equals(LabReqs.get(i).getReqUID()) && LabReqs.get(i).getDelIndicator() != 'D') {
                                Index = i;
                                FoundFlag = false;
                            }
                        if (FoundFlag)
                            System.out.println("Invalid Input!\n");
                    } while (FoundFlag);
                    System.out.print("Reason for deletion: ");
                    LabReqs.get(Index).setDelReason(Scan.nextLine());
                    LabReqs.get(Index).setDelIndicator('D');
                    System.out.println(LabReqs.get(Index).getReqUID() + " has been deleted.");
                    FileWriter AddLabReqFile = null;
                    try {
                        AddLabReqFile = new FileWriter("data/LabReq/" + LabReqs.get(Index).getReqUID().substring(0, 3) + "_Requests.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Integer> AddIndexes = new ArrayList<>();
                    for (int i = 0; i < LabReqs.size(); i++)
                        if (LabReqs.get(i).getReqUID().substring(0, 3).equals(LabReqs.get(Index).getReqUID().substring(0, 3)))
                            AddIndexes.add(i);
                    for (Integer index : AddIndexes) {
                        if (LabReqs.get(index).getDelIndicator() == 'D') {
                            try {
                                if (AddLabReqFile != null) {
                                    AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + LabReqs.get(index).getDelIndicator() + ";" + LabReqs.get(index).getDelReason() + ";" + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (AddLabReqFile != null) {
                                    AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        if (AddLabReqFile != null) {
                            AddLabReqFile.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "2" -> {
                    int Index = 0;
                    ArrayList<Integer> Indexes = new ArrayList<>();
                    System.out.print("Patient's UID: ");
                    String PUID = Scan.nextLine();
                    LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqUID)));
                    LabReqs.sort(Collections.reverseOrder(Comparator.comparing(LabReq::getReqDate)));
                    for (int i = 0; i < LabReqs.size(); i++)
                        if (PUID.equals(LabReqs.get(i).getPUID()) && LabReqs.get(i).getDelIndicator() != 'D')
                            Indexes.add(i);
                    if (Indexes.size() == 0)
                        System.out.println("No record found");
                    else if (Indexes.size() == 1)
                        Index = Indexes.get(0);
                    else {
                        int ServIndex = 0;
                        System.out.printf("%-40s %-40s %-40s %-40s %n", "Request's UID", "Lab Test Type", "Request Date", "Result");
                        for (Integer index : Indexes) {
                            for (int j = 0; j < Services.size(); j++)
                                if (LabReqs.get(index).getReqUID().substring(0, 3).equals(Services.get(j).getServiceCode()))
                                    ServIndex = j;
                            System.out.printf("%-40s %-40s %-40s %-40s %n", LabReqs.get(index).getReqUID(), Services.get(ServIndex).getDescription(), LabReqs.get(index).getReqDate(), LabReqs.get(index).getResult());
                        }
                        boolean ReqIDFlag;
                        do {
                            ReqIDFlag = true;
                            System.out.print("Request's UID: ");
                            String ReqUID = Scan.nextLine();
                            for (int i = 0; i < LabReqs.size(); i++)
                                if (LabReqs.get(i).getReqUID().equals(ReqUID) && LabReqs.get(i).getDelIndicator() != 'D') {
                                    Index = i;
                                    ReqIDFlag = false;
                                }
                            if (ReqIDFlag)
                                System.out.println("Invalid Input!\n");
                        } while (ReqIDFlag);
                    }
                    if (Indexes.size() > 0) {
                        System.out.print("Reason for deletion: ");
                        LabReqs.get(Index).setDelReason(Scan.nextLine());
                        LabReqs.get(Index).setDelIndicator('D');
                        System.out.println(LabReqs.get(Index).getReqUID() + " has been deleted.");
                        FileWriter AddLabReqFile = null;
                        try {
                            AddLabReqFile = new FileWriter("data/LabReq/" + LabReqs.get(Index).getReqUID().substring(0, 3) + "_Requests.txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<Integer> AddIndexes = new ArrayList<>();
                        for (int i = 0; i < LabReqs.size(); i++)
                            if (LabReqs.get(i).getReqUID().substring(0, 3).equals(LabReqs.get(Index).getReqUID().substring(0, 3)))
                                AddIndexes.add(i);
                        for (Integer index : AddIndexes) {
                            if (LabReqs.get(index).getDelIndicator() == 'D') {
                                try {
                                    if (AddLabReqFile != null) {
                                        AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + LabReqs.get(index).getDelIndicator() + ";" + LabReqs.get(index).getDelReason() + ";" + "\n");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    if (AddLabReqFile != null) {
                                        AddLabReqFile.write(LabReqs.get(index).getReqUID() + ";" + LabReqs.get(index).getPUID() + ";" + LabReqs.get(index).getReqDate() + ";" + LabReqs.get(index).getReqTime() + ";" + LabReqs.get(index).getResult() + ";" + "\n");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            if (AddLabReqFile != null) {
                                AddLabReqFile.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            do {
                System.out.print("Do you want to delete another laboratory request[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static String GenReqUID(int ServCodeIndex, ArrayList<Serv> Services, ArrayList<LabReq> LabReqs) {
        char Second, First;
        String StrNum;
        int num = 0;
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Format YMD = new SimpleDateFormat("yyyyMMdd");
        for (LabReq labReq : LabReqs)
            if (labReq.getReqUID().substring(0, 11).equals(Services.get(ServCodeIndex).getServiceCode()+YMD.format(new Date())))
                num++;
        if (num == 0)
            return Services.get(ServCodeIndex).getServiceCode() + YMD.format(new Date()) + Alphabet.charAt(0) + Alphabet.charAt(0) + "00";
        else {
            First = Alphabet.charAt(num / 2600);
            num = num % 2600;
            Second = Alphabet.charAt(num / 100);
            num = num % 100;
            if (num < 10)
                StrNum = "0" + num;
            else
                StrNum = String.valueOf(num);
            return Services.get(ServCodeIndex).getServiceCode() + YMD.format(new Date()) + First + Second + StrNum;
        }
    }

}
