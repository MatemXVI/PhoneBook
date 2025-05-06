package org.example;

import lombok.SneakyThrows;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        String applicationName = "PHONEBOOK", version = "1.0", author = "Mateusz Milczarek";
        System.out.println(applicationName);
        System.out.println("Wersja " + version + ", Autor: " + author);
        System.out.println("Witamy w książce telefonicznej!");
        Scanner scanner = new Scanner(System.in);
        PhoneBook phonebook = new PhoneBook();
        int option;
        boolean isON = true;
        String yesOrNo;
        System.out.println("Czy chcesz załadować kontakty z pliku '.txt'? (T/N)");
        yesOrNo = scanner.nextLine();
        if(yesOrNo.equalsIgnoreCase("t")){
            while(!phonebook.load(scanner));
        }
        while(isON){
            System.out.println("---------------MENU---------------");
            System.out.println("1. Dodaj kontakt");
            System.out.println("2. Wyszukaj kontakt");
            System.out.println("3. Pokaż wszystkie kontakty");
            System.out.println("4. Edytuj kontakt");
            System.out.println("5. Usuń kontakt");
            System.out.println("6. Zapisz do pliku");
            System.out.println("7. Załaduj inny plik");
            System.out.println("0. Wyjdź");
            System.out.print("Wybierz opcję: ");
            try{
                option = scanner.nextInt();
                scanner.nextLine();
            }catch(InputMismatchException e){
                System.out.println("Podano niepoprawną cyfrę");
                scanner.nextLine();
                continue;
            }
            switch(option){
                case 1:
                    phonebook.addContact(scanner);
                    break;
                case 2:
                    phonebook.findContactByName(scanner);
                    break;
                case 3:
                    phonebook.printAllContacts();
                    break;
                case 4:
                    phonebook.editContact(scanner);
                    break;
                case 5:
                    phonebook.removeContact(scanner);
                    break;
                case 6:
                    phonebook.save(scanner);
                    break;
                case 7:
                    phonebook.load(scanner);
                    break;
                case 0:
                    System.out.println("Czy na pewno chcesz wyjść z aplikacji? (T/N)");
                    yesOrNo = scanner.nextLine();
                    if(yesOrNo.equalsIgnoreCase("T")){
                        System.out.println("Trwa zamykanie aplikacji...");
                        Thread.sleep(250);
                        isON = false;
                        break;
                    }
                default:
                    System.out.println("Wybrano niepoprawną opcję!");
            }

        }
    }
}