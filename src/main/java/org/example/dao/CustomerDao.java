package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entities.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CustomerDao {
    private Connection connection;

    public List<Customer> findAll() {
        ArrayList<Customer> list = new ArrayList<Customer>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("idCustomer");
                String surname = rs.getString("Surname");
                String name = rs.getString("Name");
                String middleName = rs.getString("MiddleName");
                LocalDate birthday = rs.getDate("Birthday").toLocalDate();
                String address = rs.getString("Adress");
                Long cardNumber = rs.getLong("CardNumber");
                Double cardBalance = rs.getDouble("CardBalance");
                list.add(new Customer(id, surname, name, middleName, birthday, address, cardNumber, cardBalance));
            }
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void add(Customer customer){
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO customer (Surname,customer.Name,MiddleName,Birthday,Adress,CardNumber,CardBalance)"+ "VALUES (?,?,?,?,?,?,?)")) {
            ps.setString(1, customer.getSurname());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getMiddleName());
            ps.setDate(4, Date.valueOf(customer.getBirthday()));
            ps.setString(5,customer.getAddress());
            ps.setLong(6,customer.getCardNumber());
            ps.setDouble(7,customer.getCardBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> findByName(String name) {
        ArrayList<Customer> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer WHERE name = ?")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idCustomer");
                String surname = rs.getString("Surname");
                String middleName = rs.getString("MiddleName");
                LocalDate birthday = rs.getDate("Birthday").toLocalDate();
                String address = rs.getString("Adress");
                Long cardNumber = rs.getLong("CardNumber");
                Double cardBalance = rs.getDouble("CardBalance");
                list.add(new Customer(id, surname, name, middleName, birthday, address, cardNumber, cardBalance));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Customer> findByCardInterval(Long minCard, Long maxCard) {
        ArrayList<Customer> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer WHERE CardNumber BETWEEN ? AND ?")) {

            ps.setLong(1, minCard);
            ps.setLong(2, maxCard);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idCustomer");
                String surname = rs.getString("Surname");
                String name = rs.getString("Name");
                String middleName = rs.getString("MiddleName");
                LocalDate birthday = rs.getDate("Birthday").toLocalDate();
                String address = rs.getString("Adress");
                long cardNumber = rs.getLong("CardNumber");
                double cardBalance = rs.getDouble("CardBalance");
                list.add(new Customer(id, surname, name, middleName, birthday, address, cardNumber, cardBalance));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Customer> findByCardBalance() {
        ArrayList<Customer> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer WHERE CardBalance < 0")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idCustomer");
                String surname = rs.getString("Surname");
                String name = rs.getString("Name");
                String middleName = rs.getString("MiddleName");
                LocalDate birthday = rs.getDate("Birthday").toLocalDate();
                String address = rs.getString("Adress");
                long cardNumber = rs.getLong("CardNumber");
                double cardBalance = rs.getDouble("CardBalance");
                list.add(new Customer(id, surname, name, middleName, birthday, address, cardNumber, cardBalance));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Customer> findByBalanceORCard() {
        ArrayList<Customer> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer ORDER BY CardBalance ASC, CardNumber ASC")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idCustomer");
                String surname = rs.getString("Surname");
                String name = rs.getString("Name");
                String middleName = rs.getString("MiddleName");
                LocalDate birthday = rs.getDate("Birthday").toLocalDate();
                String address = rs.getString("Adress");
                long cardNumber = rs.getLong("CardNumber");
                double cardBalance = rs.getDouble("CardBalance");
                list.add(new Customer(id, surname, name, middleName, birthday, address, cardNumber, cardBalance));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Integer> listYears() {
        List<Integer> resultList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT YEAR(Birthday) AS birth_year FROM customer")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(rs.getInt("birth_year"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public Map<Integer, Customer> mapByYear() {
        Map<Integer, Customer> resultMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("idCustomer"),
                        rs.getString("Surname"),
                        rs.getString("Name"),
                        rs.getString("MiddleName"),
                        rs.getDate("Birthday").toLocalDate(),
                        rs.getString("Adress"),
                        rs.getLong("CardNumber"),
                        rs.getDouble("CardBalance"));
                Integer year = customer.getBirthday().getYear();
                if (!resultMap.containsKey(year) || customer.getCardBalance() > resultMap.get(year).getCardBalance()) {
                    resultMap.put(year, customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultMap;
    }
}
