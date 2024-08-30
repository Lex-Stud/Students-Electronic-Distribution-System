package org.example;
import java.io.*;
import java.nio.file.*;

public class Main {
   
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided");
            return;
        }

        Secretariat secretariat = new Secretariat();

        // Deschidere fiser comenzii
        String filename = "src/main/resources/" + args[0] + "/" + args[0] + ".in";
        Path filePath = Paths.get(filename);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            
            while ((line = reader.readLine()) != null) {

                if (line.contains("adauga_student")) {
                    String[] date = line.split(" - ");
                    try {
                        secretariat.addStudent(date[2], date[1]);
                    } catch (Exception e) {
                        String outputFile = "src/main/resources/" + args[0] + "/" + args[0] + ".out";
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                            writer.write("***\n" + e.getMessage() + "\n");
                        } catch (IOException ex) {
                            System.out.println("Error writing to file: " + ex.getMessage());
                        }
                    }
                }

                if (line.contains("citeste_mediile")) {
                    secretariat.citesteMedii(args[0]);
                }

                if (line.contains("posteaza_mediile")) {
                    secretariat.posteazaMedii(args[0]);
                }

                if (line.contains("adauga_curs")) {
                    String[] date = line.split(" - ");
                    secretariat.addCurs(date[1], date[2], Integer.parseInt(date[3]));   
                }

                if (line.contains("adauga_preferinte")) {
                    String[] date = line.split(" - ");
                    secretariat.adaugaPreferinte(date);
                }

                if (line.contains("contestatie")) {
                    String[] date = line.split(" - ");
                    secretariat.contestatie(date[1], date[2]);
                }

                if (line.contains("repartizeaza")) {
                    secretariat.repartizeaza();
                }

                if (line.contains("posteaza_curs")) {
                    String[] date = line.split(" - ");

                    secretariat.posteazaCursuri(args[0], date[1]);
                }

                if (line.contains("posteaza_student")) {
                    String[] date = line.split(" - ");

                    secretariat.afisareStudent(args[0], date[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
