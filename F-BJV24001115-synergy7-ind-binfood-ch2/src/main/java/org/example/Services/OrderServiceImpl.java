package org.example.Services;

import java.util.List;

import org.example.Data;
import org.example.Models.Item;
import org.example.Models.Order;
import org.example.Utils.*;

public class OrderServiceImpl implements OrderService {

    @Override
    public void create(Order order) {
        try {
            if (Data.orders.contains(order.getItem())) {
                FormatMessageUtil.duplicateMessage();
            } else {
                Data.orders.add(order);
                FormatMessageUtil.succesToAddMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToAddMessage();
            ;
            System.out.println(e);
        }
    }

    @Override
    public List<Order> getOrderList() {
        return Data.orders;
    }

    @Override
    public int getTotalPrice() {
        int totalPrice = 0;
        for (Order order : getOrderList()) {
            Item item = order.getItem();
            int subTotal = item.getPrice() * order.getQuantity();
            totalPrice += subTotal;
        }
        return totalPrice;
    }

    @Override
    public int getTotalqty() {
        int totalQty = 0;
        for (Order order : getOrderList()) {
            totalQty += order.getQuantity();
        }
        return totalQty;
    }

    @Override
    public String getListToString() {
        StringBuilder receipt = new StringBuilder();
        for (Order order : getOrderList()) {
            Item item = order.getItem();
            int subTotal = item.getPrice() * order.getQuantity();
            receipt.append(item.getName())
                    .append("\t\t")
                    .append(order.getQuantity())
                    .append("\t\t")
                    .append(AdditionalUtil.priceFormat(subTotal))
                    .append("\n");
        }
        receipt.append("-".repeat(20));
        receipt.append("\n");
        receipt.append("Total\t\t\t")
                .append(getTotalqty())
                .append("\t\t")
                .append(AdditionalUtil.priceFormat(getTotalPrice()));
        return receipt.toString();
    }

    @Override
    public String getreceipt(String payment) {
        StringBuilder receipt = new StringBuilder();
        receipt.append(AdditionalUtil.headerFormat("BinarFud"))
                .append("\nTerima kasih sudah memesan di BinarFud\n\nDi bawah ini adalah pesanan Anda\n");
        receipt.append(getListToString()).append("\n\nPembayaran\t\t\t: ").append(payment)
                .append("\nTanggal Transaksi\t\t: ")
                .append(AdditionalUtil.getCurrentDate());
        receipt.append(AdditionalUtil.headerFormat("Simpan struk ini sebagai bukti pembayaran"));

        return receipt.toString();
    }

    @Override
    public void delete(Long id) {
        try {
            if (getOrderById(id) == null) {
                FormatMessageUtil.notFoundMessage();
            } else {
                int index = getOrderList().indexOf(getOrderById(id));
                getOrderList().remove(index);
                FormatMessageUtil.succesToDeleteMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToDeleteMessage();
            System.out.println(e);
        }
    }

    @Override
    public void update(Long id, int quantity) {

        try {
            if (getOrderById(id) == null) {
                FormatMessageUtil.notFoundMessage();
            } else {
                Order order = getOrderById(id);
                order.setQuantity(quantity);
                int index = getOrderList().indexOf(getOrderById(id));
                getOrderList().set(index, order);
                FormatMessageUtil.succesToEditMessage();
            }
        } catch (Exception e) {
            FormatMessageUtil.failedToEditMessage();
            System.out.println(e);
        }
    }

    @Override
    public Order getOrderById(Long id) {
        for (Order order : getOrderList()) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;

    }

    @Override
    public long getOrderByName(String name) {
        for (Order order : getOrderList()) {
            if (name.equalsIgnoreCase(order.getItem().getName())) {
                long id = order.getId();
                return id;
            }
        }
        return 0;
    }

}
