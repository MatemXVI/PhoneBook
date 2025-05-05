package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PhoneBook phonebook = new PhoneBook();
        int option;
        boolean isON = true;
        while(isON){
            System.out.println("-------------KSIĄŻKA TELEFONICZNA-----------------");
            System.out.println("1. Dodaj kontakt");
            System.out.println("2. Wyszukaj kontakt");
            System.out.println("3. Pokaż wszystkie kontakty");
            System.out.println("4. Edytuj kontakt");
            System.out.println("5. Usuń kontakt");
            System.out.println("6. Zapisz do pliku");
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
                case 0:
                    System.out.println("Trwa zamykanie aplikacji...");
                    isON = false;
                    break;
            }

        }
    }
}