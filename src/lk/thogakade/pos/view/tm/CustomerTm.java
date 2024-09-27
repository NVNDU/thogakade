package lk.thogakade.pos.view.tm;

import javafx.scene.control.Button;

public class CustomerTm {
    private int id;
    private String name;
    private String email;
    private String contact;
    private double salary;
    private Button btnDelete;

    public CustomerTm() {
    }

    public CustomerTm(int id, String name, String email, String contact, double salary, Button btnDelete) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setContact(contact);
        this.setSalary(salary);
        this.btnDelete = btnDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }
}
