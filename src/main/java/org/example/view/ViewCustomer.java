package org.example.view;

import org.example.entities.Customer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ViewCustomer {
    public void showList(List<Customer> customer) {
        System.out.println(" --- All Customers --- ");
        customer.forEach(System.out::println);
        System.out.println(" --------------------- ");
    }
    public void showList2(List<Integer> customer) {
        for (int i = 0; i < customer.size(); i++) {
            System.out.println(customer.get(i));
        }
        System.out.println("--------------------");
    }
    public static void showList3(Map<Integer, Customer> customer){
        for (Map.Entry entry : customer.entrySet()) {
            System.out.println(entry.getKey()+" - "+ entry.getValue());
        }
    }

    public Customer readCustomer(Scanner scanner) {
        System.out.println("Surname");
        String surname = scanner.next();
        System.out.println("Name");
        String name = scanner.next();
        System.out.println("MiddleName");
        String middlename = scanner.next();
        System.out.println("Birthday");
        LocalDate birthday = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Address");
        String adress = scanner.next();
        System.out.println("cardNumber");
        Long cardNumber = scanner.nextLong();
        System.out.println("cardBalance");
        Double cardBalance = scanner.nextDouble();
        return new Customer(0,surname,name,middlename, birthday,adress,cardNumber,cardBalance);
    }
}
