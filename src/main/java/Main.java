import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        PowerShellExecutor powerShellExecutor = new PowerShellExecutor();
//        AtomicTestManager atomicTestManager = new AtomicTestManager(powerShellExecutor);
//        String res = atomicTestManager.showAll();
//        System.out.println(res);
        atomicTesting();
    }

    public static void atomicTesting() throws IOException {
        Scanner scanner = new Scanner(System.in);
        PowerShellExecutor powerShellExecutor = new PowerShellExecutor();
        AtomicTestManager atomicTestManager = new AtomicTestManager(powerShellExecutor);
        while (true){
            System.out.println("*** Atomic Test Runner System ***");
            System.out.println("------------------------------");
            System.out.println("Choose Invoke-Atomic command:");
            System.out.println("1. View detail");
            System.out.println("2. Check prerequisites");
            System.out.println("3. Get prerequisites");
            System.out.println("4. Run Atomic Test");
            System.out.println("5. Cleanup Atomic Test");
            System.out.println("6. List all Atomic test");
            System.out.println("0. Exit");
            System.out.println("------------------------------");
            String respond = scanner.nextLine();


            String ans;
            switch (respond){
                case "0":
                    System.out.println("Exiting...");
                    return;
                case "1":
                    System.out.println("Enter technique ID:");
                    String id = scanner.nextLine();
                    System.out.println("Show full or brief? (F/B)");
                    ans = scanner.nextLine().toLowerCase();
                    if ("b".equals(ans)){
                        System.out.println(atomicTestManager.showDetailsBrief(id));
                    } else {
                        System.out.println(atomicTestManager.showDetails(id));}
                    break;
                case "2":
                    System.out.println("Enter technique ID:");
                    id = scanner.nextLine();
                    System.out.println("Check all testcase or 1 testcase? (A/test number)");
                    ans = scanner.nextLine().toLowerCase();
                    if ("a".equals(ans)){
                        System.out.println(atomicTestManager.checkPrereqs(id));
                    } else {
                        try {
                            int testNumber = Integer.parseInt(ans);
                            System.out.println(atomicTestManager.checkPrereqs(id, testNumber));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid test number.");
                        }
                    }
                    break;
                case "3":
                    System.out.println("Enter technique ID:");
                    id = scanner.nextLine();
                    break;
                case "4":
                    System.out.println("Enter technique ID:");
                    id = scanner.nextLine();
                    System.out.println("Run all or specify tests? (A/test numbers)");
                    ans = scanner.nextLine().toLowerCase();
                    if ("a".equals(ans)){
                        System.out.println(atomicTestManager.runAtomicTest(id));
                    } else {
                        try {
                            System.out.println(atomicTestManager.runAtomicTest(id, ans));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid test number.");
                        }
                    }
                    break;
                case "5":
                    System.out.println("Enter technique ID:");
                    id = scanner.nextLine();
                    System.out.println("Cleanup all or specify tests? (A/test numbers)");
                    ans = scanner.nextLine().toLowerCase();
                    if ("a".equals(ans)){
                        System.out.println(atomicTestManager.cleanupAtomicTest(id));
                    } else {
                        try {
                            System.out.println(atomicTestManager.cleanupAtomicTest(id, ans));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid test number.");
                        }
                    }
                    break;
                case "6":
                    System.out.println("Any OS? (Y/N)");
                    ans = scanner.nextLine().toLowerCase();
                    if ("y".equals(ans)){
                        System.out.println(atomicTestManager.showAllOs());
                    } else {
                        System.out.println(atomicTestManager.showAll());}
                    break;
                default:
                    System.out.println("Enter correct command!");
                    break;
            }

        }

    }
}
