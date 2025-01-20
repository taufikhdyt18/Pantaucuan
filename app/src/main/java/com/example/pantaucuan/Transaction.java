package com.example.pantaucuan;

public class Transaction {
    private String id;
    private String keterangan;
    private String tanggal;
    private double jumlah;
    private String tipe; // "pemasukan" or "pengeluaran"

    // Default constructor
    public Transaction() {
    }

    // Constructor without ID
    public Transaction(String keterangan, String tanggal, double jumlah, String tipe) {
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.tipe = tipe;
    }

    // Constructor with ID
    public Transaction(String id, String keterangan, String tanggal, double jumlah, String tipe) {
        this.id = id;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.tipe = tipe;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    // Utility methods
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", keterangan='" + keterangan + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", jumlah=" + jumlah +
                ", tipe='" + tipe + '\'' +
                '}';
    }

    public boolean isValid() {
        return (tipe.equals("pemasukan") || tipe.equals("pengeluaran")) && jumlah >= 0;
    }
}
