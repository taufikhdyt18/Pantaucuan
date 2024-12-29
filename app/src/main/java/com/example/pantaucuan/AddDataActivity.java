package com.example.pantaucuan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
/**
 * Created by Taufik Hidayat
 ig:@tfkkhdytt_
 on 29-12-2024.
 */

public class AddDataActivity extends AppCompatActivity {
    private EditText etKeterangan, etTanggal, etJumlah;
    private RadioGroup rgTipe;
    private Button btnSimpan;
    private ImageButton btnBack;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        // Initialize views
        etKeterangan = findViewById(R.id.et_keterangan);
        etTanggal = findViewById(R.id.et_tanggal);
        etJumlah = findViewById(R.id.et_jumlah);
        rgTipe = findViewById(R.id.rg_tipe);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnBack = findViewById(R.id.btn_back);

        // Initialize calendar
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        // Setup date picker
        setupDatePicker();

        // Setup back button
        btnBack.setOnClickListener(v -> finish());

        // Setup save button
        btnSimpan.setOnClickListener(v -> saveTransaction());
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };

        etTanggal.setOnClickListener(v -> {
            new DatePickerDialog(AddDataActivity.this, date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateLabel() {
        etTanggal.setText(dateFormatter.format(calendar.getTime()));
    }

    private void saveTransaction() {
        // Validasi input
        String keterangan = etKeterangan.getText().toString().trim();
        String tanggal = etTanggal.getText().toString().trim();
        String jumlahStr = etJumlah.getText().toString().trim();

        if (keterangan.isEmpty() || tanggal.isEmpty() || jumlahStr.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipe = rgTipe.getCheckedRadioButtonId() == R.id.rb_pemasukan ? "pemasukan" : "pengeluaran";

        try {
            double jumlah = Double.parseDouble(jumlahStr);

            // Buat transaksi baru
            Transaction transaction = new Transaction(keterangan, tanggal, jumlah, tipe);

            // Simpan transaksi ke database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            boolean isSaved = dbHelper.insertTransaction(transaction);

            if (isSaved) {
                Toast.makeText(this, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke MainActivity
            } else {
                Toast.makeText(this, "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Jumlah uang tidak valid", Toast.LENGTH_SHORT).show();
        }
    }
}
