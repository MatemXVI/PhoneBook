package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {
    ArrayList<Contact> phonebook = new ArrayList<>();

    String regex = "^\\d{9}$";
    Pattern pattern = Pattern.compile(regex);

    private String setName(Scanner scanner){
        System.out.print("Podaj imię: ");
        String name = scanner.nextLine();
        name = name.toLowerCase();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        return name;
    }



    public void addContact(Scanner scanner){
        String name = setName(scanner);
        for(Contact element : phonebook){
            if(element.getName().equalsIgnoreCase(name)){
                System.out.println("Kontakt o tej nazwie już istnieje. Możesz edytować numer telefonu tego kontaktu.");
                return;
            }
        }
        System.out.print("Podaj numer telefonu: ");
        String phoneNumber = scanner.nextLine();
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches()){
            System.out.println("Format numeru telefonu jest niepoprawny. Numer musi się tylko i wyłącznie z 9 cyfr!");
            return;
        }
        if(phonebook.add(new Contact(name, phoneNumber)))
            System.out.println("Kontakt został dodany pomyślnie");
        else
            System.out.println("Wystąpił błąd w dodawaniu kontaktu.");
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
    }

//    public void save(Scanner scanner){
//        try {
//            if(!phonebook.isEmpty()){
//                System.out.println("Podaj nazwę pliku. Nie dodawaj formatu .txt ");
//                String fileName = scanner.nextLine()+".txt";
//                FileWriter file = new FileWriter(fileName); //co jeżeli plik istnieje
//                System.out.println("Trwa zapis do pliku " + fileName);
//                for(Contact element : phonebook){
//                    file.write(element.getName() + " - " + element.getPhoneNumber() +"\n");
//                }
//                file.close();
//                System.out.println("Kontakty pomyślnie zapisane do pliku.");
//            }else{
//                System.out.println("Lista kontaktów jest pusta. Brak zapisu do pliku.");
//            }
//        } catch (IOException e) {
//            System.out.println("Błąd w zapisie pliku");
//        }
//    }
//
//    public void load(Scanner scanner){
//        try{
//            System.out.println("Podaj nazwę pliku.");
//            String fileName = scanner.nextLine();
//            File file = new File(fileName);
//            Scanner sc = new Scanner(file);
//            while(sc.hasNextLine()){
//                System.out.println(sc.nextLine());
//            }
//        }catch(IOException e){
//            System.out.println("Błąd ładowania pliku");
//        }
//    }
}
