package hu.nati.appok.todolist.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String todo;
    private String comment;
    private int status;
    private int prior;
   private String date;


    public Item() {
        id=-1;
    }

    public Item(String todo, String comment, int status, int prior, String date) {
        this.todo = todo;
        this.comment = comment;
        this.status = status;
        this.prior = prior;
        this.date = date;
        id=-1;
    }

    public Item(int id, String todo, String comment, int status, int prior, String date) {
        this.id = id;
        this.todo = todo;
        this.comment = comment;
        this.status = status;
        this.prior = prior;
        this.date = date;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrior() {
        return prior;
    }

    public void setPrior(int prior) {
        this.prior = prior;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
