package model.deliver;

import java.util.List;

/**
 * Created by lining on 15-7-15.
 */
public class Table {


    private List<String> head;

    private List<Integer> countProd;

    private String userName;


    public List<String> getHead() {
        return head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

    public List<Integer> getCountProd() {
        return countProd;
    }

    public void setCountProd(List<Integer> countProd) {
        this.countProd = countProd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
