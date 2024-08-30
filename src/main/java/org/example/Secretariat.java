package org.example;
import java.io.*;
import java.util.*;

public class Secretariat {
    private ArrayList<StudentMaster> studentiMaster = new ArrayList<StudentMaster>();
    private ArrayList<StudentLicienta> studentiLicienta = new ArrayList<StudentLicienta>();
    private ArrayList<Curs> cursuri = new ArrayList<Curs>();
    private ArrayList<Student> studenti = new ArrayList<Student>();

    public Secretariat() {}

    private boolean findStudent(String name) {
        for (StudentLicienta student : studentiLicienta) {
            if (student.getName().equals(name)) {
                return true;
            }
        }

        for (StudentMaster student : studentiMaster) {
            if (student.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void addStudent(String name, String type) throws Exception {
        
        if (findStudent(name)) {
            throw new Exception("Student duplicat: " + name);
        }

        if (type.equals("master")) {
            StudentMaster student = new StudentMaster(name);
            student.setInvatamant(type);
            studentiMaster.add(student);

        } else if (type.equals("licenta")) {
            StudentLicienta student = new StudentLicienta(name);
            student.setInvatamant(type);
            studentiLicienta.add(student);
        } else {
            System.out.println("Tipul studentului nu este valid");
        }
    }

    public void citesteMedii(String file) {
        int nr_fis = 1;
        File date = new File("src/main/resources/" + file + "/note_" + nr_fis + ".txt");
        
        while (date.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(date))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] date_student = line.split(" - ");
                    int ok = 0;

                    for (StudentLicienta student : studentiLicienta) {
                        if (student.getName().equals(date_student[0])) {
                            student.setMedie(Double.parseDouble(date_student[1]));
                            ok = 1;
                        }
                    }

                    if (ok == 0) {
                        for (StudentMaster student : studentiMaster) {
                            if (student.getName().equals(date_student[0])) {
                                student.setMedie(Double.parseDouble(date_student[1]));
                            }
                        }
                    }
                    
                    ok = 0;
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

            nr_fis++;
            date = new File("src/main/resources/" + file + "/note_" + nr_fis + ".txt");
        }
    }

    public void posteazaMedii(String file) {
        File date = new File("src/main/resources/" + file + "/" + file + ".out");

        if (studenti.size() == 0) {
            for (StudentLicienta student : studentiLicienta) {
                studenti.add(student);
            }
            for (StudentMaster student : studentiMaster) {
                studenti.add(student);
            }
        }
        Collections.sort(studenti, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1.getMedie() < s2.getMedie()) {
                    return 1;
                } else if (s1.getMedie() > s2.getMedie()) {
                    return -1;
                } else {
                    return s1.getName().compareTo(s2.getName());
                }
            }
        });

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(date, true))) {
            writer.write("***\n");
            
            
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }
    

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(date, true))) { // Open the file in append mode
            for (Student student : studenti) {
                writer.write(student.getName() + " - " + student.getMedie() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void contestatie(String Nume, String medie) {
        
        for (Student student : studenti) {
            if (student.getName().equals(Nume)) {
                student.setMedie(Double.parseDouble(medie));
                break;
            }
        }
    }

    public void addCurs(String tip, String nume, int capacitate) {
        Curs curs = new Curs(tip, nume, capacitate);
        cursuri.add(curs);
    }

    public void adaugaPreferinte(String[] date) {
        for (Student student : studenti) {
            if (student.getName().equals(date[1])) {
                for (int i = 2; i < date.length; i++) {
                    student.addPreferinta(date[i]);
                }
            }
        }
    }

    public void repartizeaza() {
        
        if (studentiLicienta.size() > 0)
        Collections.sort(studentiLicienta, new Comparator<StudentLicienta>() {
            @Override
            public int compare(StudentLicienta s1, StudentLicienta s2) {
                if (s1.getMedie() < s2.getMedie()) {
                    return 1;
                } else if (s1.getMedie() > s2.getMedie()) {
                    return -1;
                } else {
                    return s1.getName().compareTo(s2.getName());
                }
            }
        });

        if (studentiMaster.size() > 0)
        Collections.sort(studentiMaster, new Comparator<StudentMaster>() {
            @Override
            public int compare(StudentMaster s1, StudentMaster s2) {
                if (s1.getMedie() < s2.getMedie()) {
                    return 1;
                } else if (s1.getMedie() > s2.getMedie()) {
                    return -1;
                } else {
                    return s1.getName().compareTo(s2.getName());
                }
            }
        });

        for (StudentLicienta student : studentiLicienta) {
            int ok = 0;
            for (String preferinta : student.getPreferinte()) {
                for (Curs curs : cursuri) {
                    if (curs.getNume().equals(preferinta)) {
                        if (curs.getCapacitate() > curs.getNrStudenti()) {
                            curs.getStudenti().add(student);
                            student.setCurs(preferinta);
                            ok = 1;
                            break;
                        } else if (student.getMedie() == curs.getStudenti().get(curs.getStudenti().size() - 1).getMedie()) {
                            curs.getStudenti().add(student);
                            student.setCurs(preferinta);
                            ok = 1;
                            break;
                            
                        }
                    }
                }
                if (ok == 1) {
                    break;
                }
            }
        }

        for (StudentMaster student : studentiMaster) {
            int ok = 0;
            for (String preferinta : student.getPreferinte()) {
                for (Curs curs : cursuri) {
                    if (curs.getNume().equals(preferinta)) {
                        if (curs.getCapacitate() > curs.getNrStudenti()) {
                            curs.getStudenti().add(student);
                            student.setCurs(preferinta);
                            ok = 1;
                            break;
                        } else if (student.getMedie() == curs.getStudenti().get(curs.getStudenti().size() - 1).getMedie()) {
                            curs.getStudenti().add(student);
                            student.setCurs(preferinta);
                            ok = 1;
                            break;
                            
                        }
                    }
                }
                if (ok == 1) {
                    break;
                }
            }
        }
        
    }

    public void posteazaCursuri(String file, String curs) {
        File date = new File("src/main/resources/" + file + "/" + file + ".out");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(date, true))) {
            writer.write("***\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        for (Curs c : cursuri) {
            if (c.getNume().equals(curs)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(date, true))) { // Open the file in append mode
                    writer.write(c.getNume() + " (" + c.getCapacitate() + ")" + "\n");
                    List<Student> students = c.getStudenti();
                    Collections.sort(students, new Comparator<Student>() {
                        @Override
                        public int compare(Student s1, Student s2) {
                            return s1.getName().compareTo(s2.getName());
                        }
                    });

                    for (Student student : students) {
                        writer.write(student.getName() + " - " + student.getMedie() + "\n");
                    }
                
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }
        }
    }

    public void afisareStudent(String file, String nume) {
        File date = new File("src/main/resources/" + file + "/" + file + ".out");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(date, true))) {
            writer.write("***\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        for (Student student : studenti) {
            if (student.getName().equals(nume)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(date, true))) {
                    String input = student.getInvatamant();
                    writer.write("Student " + input.substring(0, 1).toUpperCase() + input.substring(1) + ": " +
                        student.getName() + " - " + student.getMedie() + " - " + student.getCurs() + "\n");

                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }
        }
    }
}
