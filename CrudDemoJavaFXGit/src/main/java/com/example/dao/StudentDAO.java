package com.example.dao;

import com.example.connectionjdbc.SingleConnection;
import com.example.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO() {
        connection = SingleConnection.getConnection();
    }

    public void save(Student student){
        try {
            String sql = "insert into student(name,address,email) values(?,?,?) ";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setString(1,student.getName());
            insert.setString(2,student.getAddress());
            insert.setString(3,student.getEmail());
            insert.execute();
            connection.commit();


        }catch (Exception e){
            try{
            connection.rollback();
            } catch (SQLException e1){

                System.out.println("error: " + e1.getMessage());
            }
            System.out.println("error: " + e.getMessage());

        }

    }

    public List<Student> listAll() {
        List<Student> list = new ArrayList<Student>();

        String sql = "select * from student";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {
                Student student = new Student();

                student.setId(the_result.getInt("id"));
                student.setName(the_result.getString("name"));
                student.setAddress(the_result.getString("address"));
                student.setEmail(the_result.getString("email"));

                list.add(student);
            }


        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return list;
    }

    public Student findById(int id) {
        Student student = new Student();

        String sql = "select * from student where id = " + id ;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet the_result = statement.executeQuery();

            while (the_result.next()) {

                student.setId(the_result.getInt("id"));
                student.setName(the_result.getString("name"));
                student.setAddress(the_result.getString("address"));
                student.setEmail(the_result.getString("email"));

            }

        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }

        return student;
    }

    public void update(Student theStudent){
        try {

            String sql = "update student set name= ?, address= ?, email=? where id = " + theStudent.getId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, theStudent.getName());
            statement.setString(2, theStudent.getAddress());
            statement.setString(3, theStudent.getEmail());

            statement.execute();
            connection.commit();
        }catch (Exception e){
            try {
                connection.rollback();
            } catch(SQLException e1){
                System.out.println("errorr: " + e.getMessage());
            }
            System.out.println("error: " +  e.getMessage());
        }


    }
    public void delete(int id){
        try{

            String sql = "delete from student where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            connection.commit();
        }catch (Exception e){
            try{
                connection.rollback();
            }catch (SQLException e1){
                System.out.println("Error: " + e1.getMessage());
            }

            System.out.println("Error: " + e.getMessage());
        }

    }



}
