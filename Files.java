import java.io.*;
import java.util.ArrayList;

public class Files implements Serializable {
    @Serial
    private static final long serialVersionUID = -231079148946252998L;

    // Singleton instance
    private static Files fileInstance;

    // Private constructor
    private Files() {
    }

    // Static method to set or get the admin instance
    public static Files setFile() {
        if (fileInstance == null) {
            fileInstance = new Files();
        } else {
            System.out.println("Admin is already set!");
        }
        return fileInstance;
    }

    // Getter for the Admin instance
    public static Files getFiles() {
        if (fileInstance == null) {
            throw new IllegalStateException("Admin has not been set yet!");
        }
        return fileInstance;
    }

    private void clearFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            // This overwrites the file with no content, effectively clearing it
        } catch (IOException e) {
            System.err.println("Error clearing file: " + file.getName());
            e.printStackTrace();
        }
    }

    public static void read() {
        Files.getFiles().readCaseFiles();
        Files.getFiles().readComplainantFiles();
        Files.getFiles().readCriminalFiles();
        Files.getFiles().readDepartmentFiles();
        Files.getFiles().readEvidenceFiles();
        Files.getFiles().readManagerFiles();
        Files.getFiles().readOfficerFiles();
        Files.getFiles().readSuspectFiles();
        Files.getFiles().readVictimFiles();
        Files.getFiles().readWitnessFiles();
    }

    public static void write() throws IOException {
        Files.getFiles().writeCaseFiles();
        Files.getFiles().writeComplainantFiles();
        Files.getFiles().writeCriminalFiles();
        Files.getFiles().writeDepartmentFiles();
        Files.getFiles().writeEvidenceFiles();
        Files.getFiles().writeManagerFiles();
        Files.getFiles().writeOfficerFiles();
        Files.getFiles().writeSuspectFiles();
        Files.getFiles().writeVictimFiles();
        Files.getFiles().writeWitnessFiles();
    }

    //    Case File definition
    private final File CasesFile = new File("Files\\Cases.txt");
    public ArrayList<Case> MainCasesList = new ArrayList<>();

    private void readCaseFiles() {
        try (ObjectInputStream Casesois = new ObjectInputStream(new FileInputStream(CasesFile))) {
            while (true) {
                try {
                    MainCasesList.add((Case) Casesois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(CasesFile);
    }

    private void writeCaseFiles() {
        try (ObjectOutputStream Casesoos = new ObjectOutputStream(new FileOutputStream(CasesFile))) {
            for (Case aCase : MainCasesList) {
                Casesoos.writeObject(aCase);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Complainants File definition
    private final File ComplainantsFile = new File("Files\\Complainants.txt");
    public ArrayList<SpecialCitizen> MainComplainantsList = new ArrayList<>();

    private void readComplainantFiles() {
        try (ObjectInputStream Complainantsois = new ObjectInputStream(new FileInputStream(ComplainantsFile))) {
            while (true) {
                try {
                    MainComplainantsList.add((SpecialCitizen) Complainantsois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(ComplainantsFile);
    }

    private void writeComplainantFiles() {
        try (ObjectOutputStream Complainantsoos = new ObjectOutputStream(new FileOutputStream(ComplainantsFile))) {
            for (SpecialCitizen specialCitizen : MainComplainantsList) {
                Complainantsoos.writeObject(specialCitizen);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Criminals File definition
    private final File CriminalsFile = new File("Files\\Criminals.txt");
    public ArrayList<Criminal> MainCriminalsList = new ArrayList<>();

    private void readCriminalFiles() {
        try (ObjectInputStream Criminalsois = new ObjectInputStream(new FileInputStream(CriminalsFile))) {
            while (true) {
                try {
                    MainCriminalsList.add((Criminal) Criminalsois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(CriminalsFile);
    }

    private void writeCriminalFiles() {
        try (ObjectOutputStream Criminalsoos = new ObjectOutputStream(new FileOutputStream(CriminalsFile))) {
            for (Criminal criminal : MainCriminalsList) {
                Criminalsoos.writeObject(criminal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //    Departments File definition
    private final File DepartmentsFile = new File("Files\\Departments.txt");
    public ArrayList<Department> MainDepartmentsList = new ArrayList<>();

    private void readDepartmentFiles() {
        try (ObjectInputStream Departmentsois = new ObjectInputStream(new FileInputStream(DepartmentsFile))) {
            while (true) {
                try {
                    MainDepartmentsList.add((Department) Departmentsois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(DepartmentsFile);
    }

    private void writeDepartmentFiles() {
        try (ObjectOutputStream Departmentsoos = new ObjectOutputStream(new FileOutputStream(DepartmentsFile))) {
            for (Department department : MainDepartmentsList) {
                Departmentsoos.writeObject(department);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Evidences File definition
    private final File EvidencesFile = new File("Files\\Evidences.txt");
    public ArrayList<Evidence> MainEvidencesList = new ArrayList<>();

    private void readEvidenceFiles() {
        try (ObjectInputStream Evidencesois = new ObjectInputStream(new FileInputStream(EvidencesFile))) {
            while (true) {
                try {
                    MainEvidencesList.add((Evidence) Evidencesois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(EvidencesFile);
    }

    private void writeEvidenceFiles() {
        try (ObjectOutputStream Evidencesoos = new ObjectOutputStream(new FileOutputStream(EvidencesFile))) {
            for (Evidence evidence : MainEvidencesList) {
                Evidencesoos.writeObject(evidence);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Managers File definition
    private final File ManagersFile = new File("Files\\Managers.txt");
    public ArrayList<Manager> MainManagersList = new ArrayList<>();

    private void readManagerFiles() {
        try (ObjectInputStream Managersois = new ObjectInputStream(new FileInputStream(ManagersFile))) {
            while (true) {
                try {
                    MainManagersList.add((Manager) Managersois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(ManagersFile);
    }

    private void writeManagerFiles() {
        try (ObjectOutputStream Managersoos = new ObjectOutputStream(new FileOutputStream(ManagersFile))) {
            for (Manager manager : MainManagersList) {
                Managersoos.writeObject(manager);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Officers File definition
    private final File OfficersFile = new File("Files\\Officers.txt");
    public ArrayList<Officer> MainOfficersList = new ArrayList<>();

    private void readOfficerFiles() {
        try (ObjectInputStream Officersois = new ObjectInputStream(new FileInputStream(OfficersFile))) {
            while (true) {
                try {
                    MainOfficersList.add((Officer) Officersois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(OfficersFile);
    }

    private void writeOfficerFiles() {
        try (ObjectOutputStream Officersoos = new ObjectOutputStream(new FileOutputStream(OfficersFile))) {
            for (Officer officer : MainOfficersList) {
                Officersoos.writeObject(officer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Suspects File definition
    private final File SuspectsFile = new File("Files\\Suspects.txt");
    public ArrayList<SpecialCitizen> MainSuspectsList = new ArrayList<>();

    private void readSuspectFiles() {
        try (ObjectInputStream Suspectsois = new ObjectInputStream(new FileInputStream(SuspectsFile))) {
            while (true) {
                try {
                    MainSuspectsList.add((SpecialCitizen) Suspectsois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(SuspectsFile);
    }

    private void writeSuspectFiles() {
        try (ObjectOutputStream Suspectsoos = new ObjectOutputStream(new FileOutputStream(SuspectsFile))) {
            for (SpecialCitizen suspect : MainSuspectsList) {
                Suspectsoos.writeObject(suspect);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Victims File definition
    private final File VictimsFile = new File("Files\\Victims.txt");
    public ArrayList<SpecialCitizen> MainVictimsList = new ArrayList<>();

    private void readVictimFiles() {
        try (ObjectInputStream Victimsois = new ObjectInputStream(new FileInputStream(VictimsFile))) {
            while (true) {
                try {
                    MainVictimsList.add((SpecialCitizen) Victimsois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(VictimsFile);
    }

    private void writeVictimFiles() {
        try (ObjectOutputStream Victimsoos = new ObjectOutputStream(new FileOutputStream(VictimsFile))) {
            for (SpecialCitizen victim : MainVictimsList) {
                Victimsoos.writeObject(victim);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    Witnesses File definition
    private final File WitnessesFile = new File("Files\\Witnesses.txt");
    public ArrayList<Witness> MainWitnessesList = new ArrayList<>();

    private void readWitnessFiles() {
        try (ObjectInputStream Witnessesois = new ObjectInputStream(new FileInputStream(WitnessesFile))) {
            while (true) {
                try {
                    MainWitnessesList.add((Witness) Witnessesois.readObject());
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        clearFile(WitnessesFile);
    }

    private void writeWitnessFiles() {
        try (ObjectOutputStream Witnessesoos = new ObjectOutputStream(new FileOutputStream(WitnessesFile))) {
            for (Witness witness : MainWitnessesList) {
                Witnessesoos.writeObject(witness);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
