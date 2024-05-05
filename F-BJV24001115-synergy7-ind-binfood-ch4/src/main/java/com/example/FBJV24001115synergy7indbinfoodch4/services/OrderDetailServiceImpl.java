package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.Order;
import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.OrderDetailRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public String getListToString(Order order) {
        StringBuilder receipt = new StringBuilder();
        for (OrderDetail orderDetail : getOrderDetailByOrder(order)) {
            Product item = orderDetail.getProduct();
            double subTotal = orderDetail.getTotal_price();
            receipt.append(item.getName())
                    .append("\t\t")
                    .append(orderDetail.getQuantity())
                    .append("\t\t")
                    .append(AdditionalUtil.priceFormat((int) subTotal))
                    .append("\n");
        }
        receipt.append("-".repeat(20));
        receipt.append("\n");
        receipt.append("Total\t\t\t")
                .append(getTotalqty(order))
                .append("\t\t")
                .append(AdditionalUtil.priceFormat((int) getTotal(order)));
        return receipt.toString();
    }


    @Override
    public double getTotalPrice(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
    @Override
    public double getTotal(Order order) {
        return getOrderDetailByOrder(order).stream()
        .mapToDouble(OrderDetail::getTotal_price)
        .sum();
    }

    @Override
    public int getTotalqty(Order order) {
        return getOrderDetailByOrder(order).stream()
            .mapToInt(OrderDetail::getQuantity)
            .sum();
    }

    @Override
    public String getreceipt(String payment, Order order) {
        StringBuilder receipt = new StringBuilder();
        receipt.append(AdditionalUtil.headerFormat("BinarFud"))
                .append("\nTerima kasih sudah memesan di BinarFud\n\nDi bawah ini adalah pesanan Anda\n")
                .append("Pemesan : "+order.getUser().getUsername())
                .append("\nDestinasi Pengiriman : "+order.getDestination() +"\n\n");
        receipt.append(getListToString(order)).append("\n\nPembayaran\t\t\t: ").append(payment)
                .append("\nTanggal Transaksi\t\t: ")
                .append(AdditionalUtil.getCurrentDate());
        receipt.append(AdditionalUtil.headerFormat("Simpan struk ini sebagai bukti pembayaran"));

        return receipt.toString();
    }

    @Override
    public void create(OrderDetail orderDetail) {
        try {
            Optional<OrderDetail> opt_order = Optional.ofNullable(orderDetail);
            if (!opt_order.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Masukan tidak boleh kosong"));
            }
            Optional<OrderDetail> getOrder = Optional.ofNullable(orderDetailRepository.findByOrderAndProduct(orderDetail.getOrder(), orderDetail.getProduct()));
            if (getOrder.isPresent()) {
                log.error(FormatMessageUtil.errorMessageFormat("Pesanan Sudah tersedia, ubah untuk memperbarui pesanan"));
            } else {
                orderDetailRepository.save(orderDetail);
                log.info(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage());
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
            if (!orderDetail.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            } else {
                OrderDetail choosenOrderDetail = orderDetail.get();
                orderDetailRepository.delete(choosenOrderDetail);
                System.out.println(FormatMessageUtil.succesToDeleteMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToDeleteMessage());
        }
    }

    @Override
    public void update(UUID id, int quantity) {
        try {
            Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
            if (!orderDetail.isPresent()) {
                System.out.println(FormatMessageUtil.notFoundMessage());
            } else {
                OrderDetail choosenOrderDetail = orderDetail.get();
                choosenOrderDetail.setQuantity(quantity);
                orderDetailRepository.save(choosenOrderDetail);
                System.out.println(FormatMessageUtil.succesToEditMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToEditMessage());
        }
    }

    @Override
    public OrderDetail getProductByOrderAndProduct(Order order, Product product){
        Optional<Order> opt = Optional.ofNullable(order);
        Optional<Product> opt2 = Optional.ofNullable(product);
        if (!opt.isPresent() || !opt2.isPresent()) {
            System.out.println(FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Pesanan"));
        }
        OrderDetail orderDetail = orderDetailRepository.findByOrderAndProduct(order, product);
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrder(Order order) {
        Optional<Order> opt = Optional.ofNullable(order);
        if (!opt.isPresent()) {
            System.out.println(FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Pesanan"));
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        return orderDetails;
    }

}
