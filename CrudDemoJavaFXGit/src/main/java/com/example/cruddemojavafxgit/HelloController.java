package com.example.cruddemojavafxgit;

import com.example.dao.StudentDAO;
import com.example.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class HelloController {
    @FXML
    private TableColumn<Student, String> EmailColum;

    @FXML
    private TableColumn<Student, Number> IDColum;

    @FXML
    private TableColumn<Student, String> AddressColum;

    @FXML
    private TableColumn<Student, String> NameColum;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Student> table;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;


    int myIndex, id;

    @FXML
    private void initialize() {
        listAllStudents();
    }

    @FXML
    protected void Add() {


        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student();

        student.setName(txtName.getText());
        student.setAddress(txtAddress.getText());
        student.setEmail(txtEmail.getText());

        studentDAO.save (student);

        listAllStudents();

        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");

    }

    public void listAllStudents(){



        StudentDAO dao = new StudentDAO();

        try{


            IDColum.setCellValueFactory(f ->  f.getValue().idProperty());

            NameColum.setCellValueFactory(f-> f.getValue().nameProperty());

            AddressColum.setCellValueFactory(f-> f.getValue().addressProperty());

            EmailColum.setCellValueFactory(f-> f.getValue().emailProperty());



            List<Student> list = dao.listAll();

            ObservableList<Student> students_obs = FXCollections.observableArrayList(list);


            table.setItems(students_obs);





        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());

        }

        table.setRowFactory( tv -> {
            TableRow<Student> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
                    {
                        if (event.getClickCount () == 1 && (!myRow.isEmpty()))
                        {
                            myIndex = table.getSelectionModel().getSelectedIndex();

                            id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

                            txtName.setText(table.getItems().get(myIndex).getName());

                            txtAddress.setText(table.getItems().get(myIndex).getAddress());

                            txtEmail.setText(table.getItems().get(myIndex).getEmail());

                        }
                    }
            );

            return myRow;
        });



    }

    @FXML
    void Delete(ActionEvent event) {

        try{

            myIndex = table.getSelectionModel().getSelectedIndex();

            id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));


            StudentDAO dao = new StudentDAO();
            dao.delete(id);

            listAllStudents();

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());

        }


    }

    @FXML
    void Update(ActionEvent event) {



        try{
            myIndex = table.getSelectionModel().getSelectedIndex();

            id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));



            StudentDAO dao = new StudentDAO();

            Student theStudent = dao.findById(id);

            theStudent.setName(txtName.getText());
            theStudent.setAddress(txtAddress.getText());
            theStudent.setEmail(txtEmail.getText());

            dao.update(theStudent);

            listAllStudents();

        } catch(Exception e){

            System.out.println("Error: " + e.getMessage());

        }

    }


}

