package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {
    ArrayList<Contact> phonebook = new ArrayList<>();
    private String currentFileName = null;

    String regex = "^\\d{9}$";
    Pattern pattern = Pattern.compile(regex);

    private void separator(){
        System.out.println();
    }

    private String setName(Scanner scanner){
        System.out.print("Podaj imię: ");
        String name = scanner.nextLine();
        name = name.toLowerCase();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        return name;
    }

    public boolean load(Scanner scanner){
        try{
            System.out.println("Podaj nazwę pliku.");
            String fileName = scanner.nextLine();
            if(fileName.equals("0")){
                System.out.println("Nie załadowano pliku");
                System.out.println("Możesz później zapisać dane do pliku.");
                return true;
            }
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            boolean notEmpty = false;
            String line;
            String[] values;
            phonebook.clear();
            while(sc.hasNextLine()){
                line = sc.nextLine();
                values = line.split(" - ");
                phonebook.add(new Contact(values[0], values[1]));
                notEmpty = true;
            }
            currentFileName = fileName;
            if(notEmpty){
                System.out.println("Plik " + fileName + " pomyślnie załadowany");
            }else{
                System.out.println("Brak kontaktów w podanym pliku");
            }
            return true;
        } catch(FileNotFoundException e) {
            System.out.println("Podany plik nie istnieje. Jeżeli chcesz przejść do programu wpisz jako nazwę pliku '0'.");
            return false;
        }
    }

    public void addContact(Scanner scanner){
        String name = setName(scanner);
        for(Contact element : phonebook){
            if(element.getName().equalsIgnoreCase(name)){
                System.out.println("Kontakt o tej nazwie już istnieje. Możesz edytować numer telefonu tego kontaktu.");
                separator();
                return;
            }
        }
        System.out.print("Podaj numer telefonu: ");
        String phoneNumber = scanner.nextLine();
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches()){
            System.out.println("Format numeru telefonu jest niepoprawny. Numer musi się tylko i wyłącznie z 9 cyfr!");
            separator();
            return;
        }
        if(phonebook.add(new Contact(name, phoneNumber)))
            System.out.println("Kontakt został dodany pomyślnie");
        else
            System.out.println("Wystąpił błąd w dodawaniu kontaktu.");
        separator();
    }

    public void findContactByName(Scanner scanner){
        boolean found = false;
        if(!phonebook.isEmpty()){
            String name = setName(scanner);
            for(Contact element : phonebook){
                if(element.getName().equalsIgnoreCase(name)){
                    System.out.println(element.getName() + " - " + element.getPhoneNumber());
                    found = true;
                }
            }
            if(!found){
                System.out.println("Nie znaleziono kontaktu");
            }
        }else{
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }

    public void printAllContacts(){
        if(!phonebook.isEmpty()){
            System.out.println("Lista wszystkich kontaktów: ");
            for(Contact element : phonebook){
                System.out.println(element.getName() + " - " + element.getPhoneNumber());
            }
        }else{
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }

    public void editContact(Scanner scanner){
        if(!phonebook.isEmpty()){
            System.out.println("Podaj kontakt który chcesz edytować");
            String name = setName(scanner);
            boolean found = false;
            for(Contact element : phonebook){
                if(element.getName().equalsIgnoreCase(name)){
                    found = true;
                    System.out.println("Podaj numer telefonu który chcesz zmienić. Obecny numer to: " + element.getPhoneNumber());
                    System.out.println("Jeżeli chcesz wycofać się z edycji wciśnij 0 i enter. ");
                    String phoneNumber = scanner.nextLine();
                    if (!phoneNumber.equals("0")) {
                        Matcher matcher = pattern.matcher(phoneNumber);
                        if (!matcher.matches()) {
                            System.out.println("Nowy numer nie został zapisany. Numer musi składać się z 9 cyfr.");
                        } else {
                            element.setPhoneNumber(phoneNumber);
                            System.out.println("Edycja przebiegła pomyślnie");
                        }
                    }else{
                        System.out.println("Nie dokonano edycji");
                    }
                }
            }
            if(!found){
                System.out.println("Nie znaleziono kontaktu");
            }
        }else{
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }

    public void removeContact(Scanner scanner){
        if(!phonebook.isEmpty()){
            System.out.println("Podaj kontakt który chcesz usunąć");
            String name = setName(scanner);
            boolean found = false;
            for(int i = 0; i < phonebook.size(); i++){
                if(phonebook.get(i).getName().equalsIgnoreCase(name)){
                    found = true;
                    phonebook.remove(i);
                    System.out.println("Kontakt usunięty.");
                    break;
                }
            }
            if(!found){
                System.out.println("Nie znaleziono kontaktu");
            }
        }else{
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }

    public void save(Scanner scanner){
        try {
            if(!phonebook.isEmpty()){
                String choice, fileName;
                if(currentFileName != null){
                    System.out.println("Czy chcesz zapisać dane do obecnie załadowanego pliku " + currentFileName + "?(T/N)");
                    choice = scanner.nextLine();
                    if(choice.equalsIgnoreCase("T")){
                        fileName = currentFileName;
                    }else{
                        System.out.println("Podaj nazwę pliku. Rozszerzenie '.txt' zostanie dodane automatycznie. ");
                        fileName = scanner.nextLine()+".txt";
                    }
                }else{
                    System.out.println("Podaj nazwę pliku. Rozszerzenie '.txt' zostanie dodane automatycznie. ");
                    fileName = scanner.nextLine()+".txt";
                }
                File f = new File(fileName);
                if (f.exists() && !currentFileName.equals(fileName)) {
                    System.out.println("Plik już istnieje. Czy chcesz go nadpisać? (T/N)");
                    choice = scanner.nextLine();
                    if (!choice.equalsIgnoreCase("T")) {
                        System.out.println("Zapis anulowany.");
                        separator();
                        return;
                    }
                }
                FileWriter file = new FileWriter(fileName);
                System.out.println("Trwa zapis do pliku " + fileName);
                for(Contact element : phonebook){
                    file.write(element.getName() + " - " + element.getPhoneNumber() +"\n");
                }
                file.close();
                System.out.println("Kontakty pomyślnie zapisane do pliku.");
            }else{
                System.out.println("Lista kontaktów jest pusta. Brak zapisu do pliku.");
            }
        } catch (IOException e) {
            System.out.println("Błąd w zapisie pliku");
        }
        separator();
    }
}
