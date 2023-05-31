import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ManServ extends ManPatRec {
    public static void AddService(ArrayList<Serv> Services, boolean repeat) {
        String Again = "n";
        Scanner Scan = new Scanner(System.in);
        String ServCode, Desc;
        float Price = 0;
        boolean Checker = false;
        do {
            do {
                if (Checker)
                    System.out.println("3-character Service Code already in use");
                Checker = false;
                do {

                    System.out.print("Unique 3-character Service Code: ");
                    ServCode = Scan.nextLine();
                    if (ServCode.length() != 3)
                        System.out.println("Invalid Input!\n");
                } while (ServCode.length() != 3);

                for (Serv service : Services)
                    if (ServCode.equals(service.getServiceCode())) {
                        Checker = true;
                        break;
                    }
            } while (Checker);
            System.out.print("Description: ");
            Desc = Scan.nextLine();
            boolean PriceFlag = true;
            while (PriceFlag) {
                System.out.print("Price: ");
                try {
                    Price = Scan.nextFloat();
                    PriceFlag = false;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input!\n");
                }
                Scan.nextLine();
            }
            Services.add(new Serv(ServCode, Desc, Price));
            FileWriter AddServicesFile = null;
            try {
                AddServicesFile = new FileWriter("data/services.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Serv service : Services) {
                if (service.getDelIndicator() == 'D') {
                    try {
                        if (AddServicesFile != null) {
                            AddServicesFile.write(service.getServiceCode() + ";" + service.getDescription() + ";" + service.getPrice() + ";" + service.getDelIndicator() + ";" + service.getDelReason() + ";" + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (AddServicesFile != null) {
                            AddServicesFile.write(service.getServiceCode() + ";" + service.getDescription() + ";" + service.getPrice() + ";" + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if (AddServicesFile != null) {
                    AddServicesFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Services.get(Services.size() - 1).getServiceCode() + " " + Services.get(Services.size() - 1).getDescription() + " has been added.");
            if (repeat) {
                do {
                    System.out.print("Do you want to add another service[Y/N]: ");
                    Again = Scan.nextLine();
                    if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                        System.out.println("Invalid Input!\n");
                } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
            }
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void SearchService(ArrayList<Serv> Services) {
        Scanner Scan = new Scanner(System.in);
        String Again;
        do {
            String DYKSC;
            do {
                System.out.print("Do you know the Service Code[Y/N]: ");
                DYKSC = Scan.nextLine();
                if (!DYKSC.equals("Y") && !DYKSC.equals("y") && !DYKSC.equals("N") && !DYKSC.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!DYKSC.equals("Y") && !DYKSC.equals("y") && !DYKSC.equals("N") && !DYKSC.equals("n"));
            if (DYKSC.equals("Y") || DYKSC.equals("y")) {
                int Index = 0;
                boolean flag = false;
                System.out.print("Service Code: ");
                String ServCode = Scan.nextLine();
                for (int i = 0; i < Services.size(); i++)
                    if (Services.get(i).getServiceCode().equals(ServCode) && Services.get(i).getDelIndicator() != 'D') {
                        Index = i;
                        flag = true;
                    }
                if (flag) {
                    System.out.printf("%-40s %-40s %-40s %n", "Service Code", "Description", "Price");
                    System.out.printf("%-40s %-40s %-40s %n", Services.get(Index).getServiceCode(), Services.get(Index).getDescription(), Services.get(Index).getPrice());
                } else
                    System.out.println("No record found.");
            } else {
                ArrayList<Integer> Indexes = new ArrayList<>();
                System.out.print("Keyword: ");
                String KeyWord = Scan.nextLine();
                Services.sort(Comparator.comparing(Serv::getServiceCode));
                for (int i = 0; i < Services.size(); i++) {
                    if (Services.get(i).getDescription().contains(KeyWord) && Services.get(i).getDelIndicator() != 'D')
                        Indexes.add(i);
                }
                if (Indexes.size() == 0)
                    System.out.println("No record found.");
                else {
                    System.out.printf("%-40s %-40s %-40s %n", "Service Code", "Description", "Price");
                    for (Integer index : Indexes)
                        System.out.printf("%-40s %-40s %-40s %n", Services.get(index).getServiceCode(), Services.get(index).getDescription(), Services.get(index).getPrice());
                }
            }
            do {
                System.out.print("Do you want to search another service[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void DeleteService(ArrayList<Serv> Services, boolean repeat) {
        String Again = "n";
        Scanner Scan = new Scanner(System.in);
        int Index;
        String KeyWord;
        do {
            Index = 0;
            String DYKSC;
            do {
                System.out.print("Do you know the Service Code[Y/N]: ");
                DYKSC = Scan.nextLine();
                if (!DYKSC.equals("Y") && !DYKSC.equals("y") && !DYKSC.equals("N") && !DYKSC.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!DYKSC.equals("Y") && !DYKSC.equals("y") && !DYKSC.equals("N") && !DYKSC.equals("n"));
            boolean flag = false;
            if (DYKSC.equals("Y") || DYKSC.equals("y")) {
                System.out.print("Service Code: ");
                String ServCode = Scan.nextLine();
                for (int i = 0; i < Services.size(); i++)
                    if (Services.get(i).getServiceCode().equals(ServCode) && Services.get(i).getDelIndicator() != 'D') {
                        Index = i;
                        flag = true;
                    }
                if (!flag) {
                    System.out.println("No record found.");
                    Again = "y";
                }
            } else {
                ArrayList<Integer> Indexes = new ArrayList<>();
                System.out.print("Keyword: ");
                KeyWord = Scan.nextLine();
                for (int i = 0; i < Services.size(); i++) {
                    if (Services.get(i).getDescription().contains(KeyWord) && Services.get(i).getDelIndicator() != 'D')
                        Indexes.add(i);
                }
                if (Indexes.size() == 0) {
                    Again = "y";
                    System.out.println("No record found.");
                } else if (Indexes.size() == 1) {
                    flag = true;
                    Index = Indexes.get(0);
                } else {
                    for (Integer index : Indexes)
                        System.out.println(Services.get(index).getServiceCode() + " " + Services.get(index).getDescription() + " " + Services.get(index).getPrice());
                    boolean ServCodeFlag = true;
                    do {
                        System.out.print("Service Code: ");
                        String ASC = Scan.nextLine();
                        for (int i = 0; i < Services.size(); i++)
                            if (ASC.equals(Services.get(i).getServiceCode()) && Services.get(i).getDelIndicator() != 'D') {
                                Index = i;
                                ServCodeFlag = false;
                                flag = true;
                            }
                        if (ServCodeFlag)
                            System.out.println("Invalid Input!\n");
                    } while (ServCodeFlag);
                }
            }
            if (flag) {
                System.out.print("Reason for deletion: ");
                Services.get(Index).setDelIndicator('D');
                Services.get(Index).setDelReason(Scan.nextLine());
                System.out.println(Services.get(Index).getServiceCode() + " " + Services.get(Index).getDescription() + " has been deleted.");
                FileWriter AddServicesFile = null;
                try {
                    AddServicesFile = new FileWriter("data/services.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Serv service : Services) {
                    if (service.getDelIndicator() == 'D') {
                        try {
                            if (AddServicesFile != null) {
                                AddServicesFile.write(service.getServiceCode() + ";" + service.getDescription() + ";" + service.getPrice() + ";" + service.getDelIndicator() + ";" + service.getDelReason() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (AddServicesFile != null) {
                                AddServicesFile.write(service.getServiceCode() + ";" + service.getDescription() + ";" + service.getPrice() + ";" + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (AddServicesFile != null) {
                        AddServicesFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Again = "n";
            }
            if (repeat) {
                do {
                    System.out.print("Do you want to delete another service[Y/N]: ");
                    Again = Scan.nextLine();
                    if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                        System.out.println("Invalid Input!\n");
                } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
            }
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }

    public static void EditService(ArrayList<Serv> Services) {
        String Again;
        Scanner Scan = new Scanner(System.in);
        String Proceed;
        do {
            do {
                System.out.print("The services cannot be edited. If you would like to edit an existing service, the service will first be deleted, and new service will be created. Would you like to proceed[Y/N]: ");
                Proceed = Scan.nextLine();
                if (!Proceed.equals("Y") && !Proceed.equals("y") && !Proceed.equals("N") && !Proceed.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Proceed.equals("Y") && !Proceed.equals("y") && !Proceed.equals("N") && !Proceed.equals("n"));
            if (Proceed.equals("Y") || Proceed.equals("y")) {
                DeleteService(Services, false);
                AddService(Services, false);
            }
            do {
                System.out.print("Do you want to edit another service[Y/N]: ");
                Again = Scan.nextLine();
                if (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"))
                    System.out.println("Invalid Input!\n");
            } while (!Again.equals("Y") && !Again.equals("y") && !Again.equals("N") && !Again.equals("n"));
        } while (Again.equals("Y") || Again.equals("y"));
        //Scan.close();
    }
}
