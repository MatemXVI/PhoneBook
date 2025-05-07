package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {
    private final List<Contact> phonebook = new ArrayList<>();
//    private final String URL = "jdbc:mysql://localhost:3306/ksiazka_telefoniczna";
    private final String URL = "jdbc:h2:~/ksiazka_telefoniczna";
    private final String USER = "sa";
    private final String PASSWORD = "";
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    String regex = "^\\d{9}$";
    Pattern pattern = Pattern.compile(regex);

    private void separator() {
        System.out.println();
    }

    private String setName(Scanner scanner) {
        System.out.print("Podaj imię: ");
        String name = scanner.nextLine();
        name = name.toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }


    //6. Aktualizuj dane;,5,4,2,1
    public boolean connect(){
        String nazwa, telefon;
        int id;
        try (Connection conn = getConnection()) {
            String sqlSelect = "SELECT * FROM kontakty";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sqlSelect);
            phonebook.clear();
            while(result.next()){
                id = result.getInt("id");
                nazwa = result.getString("nazwa");
                telefon = result.getString("telefon");
                phonebook.add(new Contact(id, nazwa, telefon));
            }
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void saveToDb(String name, String phoneNumber){
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO `kontakty`(`nazwa`, `telefon`) VALUES ('" + name + "','" + phoneNumber + "')";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            conn.close();
            connect();
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy usuwaniu z bazy danych.");
//            throw new RuntimeException(e);
        }
    }

    public void editToDb(String name, String phoneNumber){
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            String sql = "UPDATE kontakty SET telefon = '" + phoneNumber + "' WHERE nazwa = '" + name + "'";
            statement.executeUpdate(sql);
            conn.close();
            connect();
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy edycji bazy danych.");
//            throw new RuntimeException(e);
        }
    }

    public void deleteToDb(int id){
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            String sql = "DELETE FROM kontakty WHERE id = " + id;
            statement.executeUpdate(sql);
            conn.close();
            connect();
        } catch (SQLException e) {
            System.out.println("Wystąpił błąd przy usuwaniu z bazy danych.");
//            throw new RuntimeException(e);
        }
    }

//1. Dodaj kontakt
    public void addContact(Scanner scanner) {
        String name = setName(scanner);
        for (Contact element : phonebook) {
            if (element.getName().equalsIgnoreCase(name)) {
                System.out.println("Kontakt o tej nazwie już istnieje. Możesz edytować numer telefonu tego kontaktu.");
                separator();
                return;
            }
        }
        System.out.print("Podaj numer telefonu: ");
        String phoneNumber = scanner.nextLine();
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            System.out.println("Format numeru telefonu jest niepoprawny. Numer musi się tylko i wyłącznie z 9 cyfr!");
            separator();
            return;
        }
        phonebook.add(new Contact(name, phoneNumber));
        saveToDb(name,phoneNumber);
        System.out.println("Dodano nowy kontakt");
        separator();
    }

    //2. Wyszukaj kontakt
    public void findContactByName(Scanner scanner) {
        boolean found = false;
        if (!phonebook.isEmpty()) {
            String name = setName(scanner);
            for (Contact element : phonebook) {
                if (element.getName().equalsIgnoreCase(name)) {
                    System.out.println(element.getName() + " - " + element.getPhoneNumber());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Nie znaleziono kontaktu");
            }
        } else {
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }
    //3. Pokaż wszystkie kontakty
    public void printAllContacts() {
        if (!phonebook.isEmpty()) {
            System.out.println("Lista wszystkich kontaktów: ");
            for (Contact element : phonebook) {
                System.out.println(element.getName() + " - " + element.getPhoneNumber());
            }
        } else {
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }
    //4. Edytuj kontakt
    public void editContact(Scanner scanner) {
        if (!phonebook.isEmpty()) {
            System.out.println("Podaj kontakt który chcesz edytować");
            String name = setName(scanner);
            boolean found = false;
            for (Contact element : phonebook) {
                if (element.getName().equalsIgnoreCase(name)) {
                    found = true;
                    System.out.println("Podaj numer telefonu który chcesz zmienić. Obecny numer to: " + element.getPhoneNumber());
                    System.out.println("Jeżeli chcesz wycofać się z edycji wciśnij 0 i enter. ");
                    String phoneNumber = scanner.nextLine();
                    if (!phoneNumber.equals("0")) {
                        Matcher matcher = pattern.matcher(phoneNumber);
                        if (!matcher.matches()) {
                            System.out.println("Nowy numer nie został zapisany. Numer musi składać się z 9 cyfr.");
                        } else {
                            editToDb(name, phoneNumber);
                            element.setPhoneNumber(phoneNumber);
                            System.out.println("Edycja przebiegła pomyślnie");
                        }
                    } else {
                        System.out.println("Nie dokonano edycji");
                    }
                }
            }
            if (!found) {
                System.out.println("Nie znaleziono kontaktu");
            }
        } else {
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }
//   5. Edytuj kontakt
    public void removeContact(Scanner scanner) {
        if (!phonebook.isEmpty()) {
            System.out.println("Podaj kontakt który chcesz usunąć");
            String name = setName(scanner);
            boolean found = false;
            try{
                for (int i = 0; i < phonebook.size(); i++) {
                    if (phonebook.get(i).getName().equalsIgnoreCase(name)) {
                        found = true;
                        deleteToDb(phonebook.get(i).getId());
                        phonebook.remove(i);
                        System.out.println("Kontakt usunięty.");
                        break;
                    }
                }
            }catch(IndexOutOfBoundsException e){
                System.out.println("Usunięto ostatni indeks z bazy.");
            }

            if (!found) {
                System.out.println("Nie znaleziono kontaktu");
            }
        } else {
            System.out.println("Lista kontaktów jest pusta.");
        }
        separator();
    }

}
