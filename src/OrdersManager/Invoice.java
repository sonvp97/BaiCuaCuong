package OrdersManager;

import BookManagement.Book;
import BookManagement.BookManagement;
import CustomerManagement.CustomerManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Invoice {
    private String idInvoice;
    private String idCustomer;
    private String date;
    private Map<String, Integer> hashMap;
    private DateFormat date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm");


    CustomerManager customerManager = CustomerManager.getCustomerManager();
    BookManagement bookManagement = BookManagement.getBookManagement();

    public Invoice() {
    }

    public Invoice(String idInvoice, String idCustomer) {
        this.idInvoice = idInvoice;
        this.idCustomer = idCustomer;
        this.date = date1.format(new Date());
        this.hashMap = new HashMap<>();
    }

    public Invoice(String idInvoice, String idCustomer, String date, Map<String, Integer> hashMap) {
        this.idInvoice = idInvoice;
        this.idCustomer = idCustomer;
        this.date = date;
        this.hashMap = hashMap;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Integer> getHashMap() {
        return hashMap;
    }

    public void setHashMap(Map<String, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    public void addBooks(String idBooks, int quantity){
        hashMap.put(idBooks, quantity);
    }

    public double getSubTotal(String idBooks, int quantity){
        Book book = bookManagement.searchById(idBooks);
        double result;
        double priceOfBook = book.getPrice();
        result = priceOfBook * quantity;
        return result;
    }

    public double getTotal(){
        double total = 0;
        for (String key: getHashMap().keySet()){
            total += getSubTotal(key, getHashMap().get(key));
        }
        return total;
    }

    public String getBookInformation(){
        StringBuilder result = new StringBuilder();
        for (String key : hashMap.keySet()){
            String id = bookManagement.searchById(key).getIdBook();
            result.append(id).append(",").append(bookManagement.searchById(id).getNameBook()).append(",")
                    .append(hashMap.get(key)).append(",");
        }
        return result.toString();
    }

    public String stringCreatedDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(getDate());
    }

    public String toFileInvoice(){
        String result = idInvoice + "," + idCustomer + "," + date;
        for(Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            result += ","+entry.getKey()+","+entry.getValue();
        }

        return result;
    }

    @Override
    public String toString() {
        return "-------Invoice-------" + "\n" +
                "ID hoá đơn" + idInvoice + "\n" +
                "Ngày: " + idCustomer + "\n" +
                "Sản phẩm: " + date + "\n" +
                "Tổng hoá đơn: " + hashMap + "\n";
    }
}
