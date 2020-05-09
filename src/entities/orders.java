/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author AboOod_shbika99
 */
public class orders {
    private int order_id;
    private int book_id;
    private int borrower_id;
    private Date borrowing_date;
    private Date return_date;

    public orders() {
    }

    public orders(int order_id, int book_id, int borrower_id, Date borrowing_date, Date return_date) {
        this.order_id = order_id;
        this.book_id = book_id;
        this.borrower_id = borrower_id;
        this.borrowing_date = borrowing_date;
        this.return_date = return_date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(int borrower_id) {
        this.borrower_id = borrower_id;
    }

    public Date getBorrowing_date() {
        return borrowing_date;
    }

    public void setBorrowing_date(Date borrowing_date) {
        this.borrowing_date = borrowing_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    @Override
    public String toString() {
        return "orders{" + "order_id=" + order_id + ", book_id=" + book_id + ", borrower_id=" + borrower_id + ", borrowing_date=" + borrowing_date + ", return_date=" + return_date + '}';
    }
    
}
