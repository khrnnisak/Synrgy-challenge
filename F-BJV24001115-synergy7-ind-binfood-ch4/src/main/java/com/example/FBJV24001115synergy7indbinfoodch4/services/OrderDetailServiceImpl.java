package com.example.FBJV24001115synergy7indbinfoodch4.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FBJV24001115synergy7indbinfoodch4.models.OrderDetail;
import com.example.FBJV24001115synergy7indbinfoodch4.models.Product;
import com.example.FBJV24001115synergy7indbinfoodch4.repositories.OrderDetailRepository;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.AdditionalUtil;
import com.example.FBJV24001115synergy7indbinfoodch4.utils.FormatMessageUtil;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public String getListToString() {
        StringBuilder receipt = new StringBuilder();
        for (OrderDetail order : getOrderDetail()) {
            Product item = order.getProduct();
            double subTotal = item.getPrice() * order.getQuantity();
            receipt.append(item.getName())
                    .append("\t\t")
                    .append(order.getQuantity())
                    .append("\t\t")
                    .append(AdditionalUtil.priceFormat((int) subTotal))
                    .append("\n");
        }
        receipt.append("-".repeat(20));
        receipt.append("\n");
        receipt.append("Total\t\t\t")
                .append(getTotalqty())
                .append("\t\t")
                .append(AdditionalUtil.priceFormat((int) getTotalPrice()));
        return receipt.toString();
    }


    @Override
    public double getTotalPrice() {
        return getOrderDetail().stream()
        .mapToInt(orderDetail -> {
            Product product = orderDetail.getProduct();
            double quantity = orderDetail.getQuantity();
            return (int) (product.getPrice() * quantity);
        })
        .sum();
    }

    @Override
    public int getTotalqty() {
        return getOrderDetail().stream()
            .mapToInt(OrderDetail::getQuantity)
            .sum();
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
    public UUID getOrderDetailId(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(OrderDetail orderDetail) {
        try {
            Optional<OrderDetail> opt_order = Optional.ofNullable(orderDetail);
            if (!opt_order.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Pesanan"));
            }
            OrderDetail choosenOrderDetail = orderDetailRepository.findByOrderId(orderDetail.getOrder().getId());
            Optional<OrderDetail> getOrder = orderDetailRepository.findById(choosenOrderDetail.getId());
            if (getOrder.isPresent()) {
                System.out.println(FormatMessageUtil.errorMessageFormat("Pesanan Sudah tersedia, ubah untuk memperbarui pesanan"));
            } else {
                orderDetailRepository.save(orderDetail);
                System.out.println(FormatMessageUtil.succesToAddMessage());
            }
        } catch (Exception e) {
            System.out.println(FormatMessageUtil.failedToAddMessage() + e);
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
            System.out.println(FormatMessageUtil.failedToDeleteMessage() + e);
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
            System.out.println(FormatMessageUtil.failedToEditMessage() + e);
        }
    }

}
