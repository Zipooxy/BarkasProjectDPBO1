/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.Scanner;
/**
 *
 * @author ASUS
 */
public class AppUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static int inputInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    public static String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    public static String getStringInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        try {
            input = br.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
        return input;
    }

    // Method untuk membaca input integer
    public static int getIntInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = 0;
        while (true) {
            try {
                input = Integer.parseInt(br.readLine());
                break;
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer:");
            }
        }
        return input;
    }
    // Method untuk membaca input double
    public static double getDoubleInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double input = 0.0;
        while (true) {
            try {
                input = Double.parseDouble(br.readLine());
                break;
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid double:");
            }
        }
        return input;
    }

}


