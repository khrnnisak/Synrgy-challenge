package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    static Scanner input = new Scanner(System.in);
    static ArrayList<String> menuList = new ArrayList<>();
    static ArrayList<Integer> priceList = new ArrayList<>();
    static ArrayList<Integer> qtyList = new ArrayList<>();
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static void main(String[] args) throws InterruptedException {
        mainMenu();
    }

    public static void mainMenu() throws InterruptedException {
        int pilih;
        do {
            System.out.println(getHeader("Selamat Datang di BinarFud"));
            System.out.println("Silakan pilih Makanan : ");
            System.out.println("1. Nasi Goreng \t\t | 15.000");
            System.out.println("2. Mie Goreng \t\t | 13.000");
            System.out.println("3. Nasi + Ayam \t\t | 18.000");
            System.out.println("4. Es teh manis \t | 3.000");
            System.out.println("5. Es Jeruk \t\t | 5.000");
            System.out.println("99. Pesan dan Bayar");
            System.out.println("0. Keluar aplikasi");
            System.out.print("=> ");
            pilih = input.nextInt();
            TimeUnit.SECONDS.sleep(1);
            if (pilih >= 1 && pilih <= 5) {
                setQuantity(pilih);
            } else if (pilih == 99) {
                if (menuList.isEmpty()) {
                    System.out.println(RED+"Anda belum memesan makanan"+RESET);
                }else{
                    showStruk();
                }
            }
        } while (pilih != 0);
    }

    public static void setQuantity(int menu) throws InterruptedException {
        String namaMenu = "";
        int harga = 0;
        int inputQty = 0;
        switch (menu) {
            case 1:
                namaMenu = "Nasi Goreng";
                harga = 15000;
                break;
            case 2:
                namaMenu = "Mie Goreng";
                harga = 13000;
                break;
            case 3:
                namaMenu = "Nasi + Ayam";
                harga = 18000;
                break;
            case 4:
                namaMenu = "Es Teh Manis";
                harga = 3000;
                break;
            case 5:
                namaMenu = "Es Jeruk";
                harga = 5000;
                break;
            case 99:
                namaMenu = "Es Jeruk";
                harga = 5000;
                break;
            case 0:
                namaMenu = "Es Jeruk";
                harga = 5000;
                break;
            default:
                break;
        }
        System.out.println(getHeader("Berapa Pesanan Anda"));
        System.out.println(namaMenu + "\t | " + formatPrice(harga));
        System.out.println("(input 0 untuk kembali)");
        System.out.print("qty =>");
        inputQty = input.nextInt();
        if (inputQty > 0) {
            if (menuList.contains(namaMenu)) {
                int indexMenu = menuList.indexOf(namaMenu);
                qtyList.set(indexMenu, (qtyList.get(indexMenu) + inputQty));
            } else {
                menuList.add(namaMenu);
                priceList.add(harga);
                qtyList.add(inputQty);
            }
        }
    }

    public static void showStruk() throws InterruptedException {
        int total = 0;
        int subTotal = 0;
        int totalQty = 0;
        int pilih = 0;
        StringBuilder listMenu = new StringBuilder();
        System.out.println(getHeader("Konfirmasi & Pembayaran"));
        for (int i = 0; i < menuList.size(); i++) {
            subTotal = priceList.get(i) * qtyList.get(i);
            total += subTotal;
            totalQty += qtyList.get(i);
            listMenu.append(menuList.get(i)).append("\t\t").append(qtyList.get(i)).append("\t\t").append(formatPrice(subTotal))
                    .append("\n");
        }
        listMenu.append("------------------------------------------------+\n");
        listMenu.append("Total\t\t\t").append(totalQty).append("\t\t").append(formatPrice(total));
        System.out.println(listMenu.toString());
        System.out.println("");
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("=>");
        pilih = input.nextInt();
        switch (pilih) {
            case 1:
                TimeUnit.SECONDS.sleep(1);
                String payment = getPembayaran();
                getNota(listMenu.toString(), payment);
                System.exit(0);
                break;
            case 2:
                mainMenu();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                break;
        }
    }
    public static void getNota(String listMenu, String pembayaran) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        StringBuilder nota = new StringBuilder();
        nota.append(getHeader("BinarFud")).append("\nTerima kasih sudah memesan di BinarFud\n\nDi bawah ini adalah pesanan Anda\n");
        nota.append(listMenu.toString()).append("\n\nPembayaran\t\t\t: ").append(pembayaran).append("\nTanggal Transaksi\t\t: ")
                .append(dtf.format(now));
        nota.append(getHeader("Simpan struk ini sebagai bukti pembayaran"));

        String fileName = getFileName();
        String PATH = "E:/Kumpulankoding/java/Synrgy-challenge/F-BJV24001115-synergy7-ind-binfood-ch1/" + fileName;
        File file = new File(PATH);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(PATH);
                fileWriter.write(nota.toString());
                fileWriter.close();
                System.out.println("");
                System.out.println(GREEN+"Nota Berhasil dicetak"+RESET);
            } else {
                System.out.println(RED+"File already exists"+RESET);
            }
        } catch (IOException e) {
            System.out.println(RED+"Terjadi error"+RESET);
            throw new RuntimeException(e);
        }
    }

    public static String getFileName() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstu1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 12;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString + ".txt";
    }

    public static String getHeader(String teks) {
        StringBuilder header = new StringBuilder();
        header.append("\n=========================================================\n").append(teks)
                .append("\n=========================================================\n");
        String result = header.toString();
        return result;
    }

    public static String getPembayaran() throws InterruptedException {
        String pembayaran = "";
        getHeader("Pilih Metode Pembayaran");
        System.out.println("1. Binar Cash");
        System.out.println("2. BinarPay");
        System.out.println("3. Pinjam Dulu Seratus");
        System.out.println("0. Kembali");
        int kode = input.nextInt();
        switch (kode) {
            case 1:
                pembayaran = "Binar Cash";
                break;
            case 2:
                pembayaran = "BinarPay";
                break;
            case 3:
                pembayaran = "Pinjam Dulu Seratus";
                break;
            case 0:
                showStruk();
                break;
            default:
                break;
        }
        return pembayaran;
    }
    public static String formatPrice(int harga){

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }
}