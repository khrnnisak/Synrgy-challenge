package org.example.Services;

import java.util.List;
import java.util.Optional;

import org.example.Data;
import org.example.Models.Item;
import org.example.Models.Order;
import org.example.Utils.*;

public class OrderServiceImpl implements OrderService {

    @Override
    public String create(Order order) {
        try {
            
            Optional<Order> opt_order = Optional.ofNullable(order);
            if (!opt_order.isPresent()) {
                return FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Pesanan");
            }
            Long id = getOrderByName(order.getItem().getName());
            Order getOrder = getOrderById(id);
            if (getOrder != null) {
                return FormatMessageUtil.errorMessageFormat("Pesanan Sudah tersedia, ubah untuk memperbarui pesanan");
            } else {
                Data.orders.add(order);
                return FormatMessageUtil.succesToAddMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToAddMessage() + e;
        }
    }

    @Override
    public List<Order> getOrderList() {
        return Data.orders;
    }

    @Override
    public int getTotalPrice() {
        return getOrderList().stream()
            .mapToInt(order -> order.getItem().getPrice() * order.getQuantity())
            .sum();
    }

    @Override
    public int getTotalqty() {
        return getOrderList().stream()
            .mapToInt(Order::getQuantity)
            .sum();
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
    public String delete(Long id) {
        try {
            if (getOrderById(id) == null) {
                return FormatMessageUtil.notFoundMessage();
            } else {
                int index = getOrderList().indexOf(getOrderById(id));
                getOrderList().remove(index);
                return FormatMessageUtil.succesToDeleteMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToDeleteMessage() + e;
        }
    }

    @Override
    public String update(Long id, int quantity) {

        try {
            if (getOrderById(id) == null) {
                return FormatMessageUtil.notFoundMessage();
            } else {
                Order order = getOrderById(id);
                order.setQuantity(quantity);
                int index = getOrderList().indexOf(getOrderById(id));
                getOrderList().set(index, order);
                return FormatMessageUtil.succesToEditMessage();
            }
        } catch (Exception e) {
            return FormatMessageUtil.failedToEditMessage() + e;
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
