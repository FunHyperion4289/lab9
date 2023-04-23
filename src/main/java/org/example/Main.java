package org.example;

import lombok.SneakyThrows;
import org.example.dao.CustomerDao;
import org.example.entities.Customer;
import org.example.view.ViewCustomer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private CustomerDao customerDao;
    private ViewCustomer viewCustomer;
    private Scanner scanner;

    public static void main(String[] args) {
       Main main = new Main();
       main.run();
    }
    @SneakyThrows
    private void run(){
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("config.properties"))) {
            props.load(reader);
            Connection connection = DriverManager.getConnection(props.getProperty("url"), props);
            customerDao = new CustomerDao(connection);
            viewCustomer = new ViewCustomer();
        }
        scanner = new Scanner(System.in);
        int m;
        while ((m = menu())!=0){
            switch (m) {
                case 1 -> {
                    showAll();
                }
                case 2 -> {
                    addCustomer();
                }
                case 3 -> {
                    findByName();
                }
                case 4 -> {
                    findByCardInterval();
                }
                case 5 -> {
                    findByCardBalance();
                }
                case 6 -> {
                    filterByBalanceORCard();
                }
                case 7 -> {
                    listYears();
                }
                case 8 -> {
                    mapByYear();
                }
            }
        }
    }



    private void showAll() {
        List<Customer> customer = customerDao.findAll();
        viewCustomer.showList(customer);
    }
    private void addCustomer() {
        Customer c = viewCustomer.readCustomer(scanner);
        customerDao.add(c);

    }
    private void findByName(){
        System.out.println("Введіть ім'я:");
        String name = scanner.next();
        List<Customer> customer = customerDao.findByName(name);
        viewCustomer.showList(customer);
    }
    private void findByCardInterval(){
        System.out.println("Введіть перше значення");
        Long minCard = scanner.nextLong();
        System.out.println("Введіть друге значення");
        Long maxCard = scanner.nextLong();
        List<Customer> customer = customerDao.findByCardInterval(minCard,maxCard);
        viewCustomer.showList(customer);
    }
    private void findByCardBalance(){
        List<Customer> customer = customerDao.findByCardBalance();
        viewCustomer.showList(customer);
    }

    private void filterByBalanceORCard() {
        List<Customer> customer = customerDao.findByBalanceORCard();
        viewCustomer.showList(customer);
    }
    private void listYears() {
        List<Integer> customer = customerDao.listYears();
        viewCustomer.showList2(customer);
    }
    private void mapByYear() {
        Map<Integer, Customer> customer = customerDao.mapByYear();
        ViewCustomer.showList3(customer);
    }



    private int menu(){
        System.out.println("""
                1. Show all
                2. Add Customer
                3. List of buyers, with the specified name
                4. List of buyers whose credit card number is in the given one intervals
                5. List of customers who have debts in the order of increasing indebtedness
                6. List of buyers, sorted by increasing account balance, and in case of equality of balances - by credit card number
                7. List of birth years of buyers registered in the program without repetitions
                8. For each year of birth, determine the buyer with the largest number of money on the card
                0. Exit
                """);
        return scanner.nextInt();
    }
}