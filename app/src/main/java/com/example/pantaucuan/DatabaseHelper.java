package com.example.pantaucuan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Taufik Hidayat
 ig:@tfkkhdytt_
 on 29-12-2024.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PantauCuan.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KETERANGAN = "keterangan";
    private static final String COLUMN_TANGGAL = "tanggal";
    private static final String COLUMN_JUMLAH = "jumlah";
    private static final String COLUMN_TIPE = "tipe";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create transactions table
        String createTable = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KETERANGAN + " TEXT NOT NULL, " +
                COLUMN_TANGGAL + " TEXT NOT NULL, " +
                COLUMN_JUMLAH + " REAL NOT NULL, " +
                COLUMN_TIPE + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if exists and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    // Insert a new transaction
    public boolean insertTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_KETERANGAN, transaction.getKeterangan());
        values.put(COLUMN_TANGGAL, transaction.getTanggal());
        values.put(COLUMN_JUMLAH, transaction.getJumlah());
        values.put(COLUMN_TIPE, transaction.getTipe());

        long result = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();

        return result != -1; // Return true if the insertion was successful
    }

    // Retrieve all transactions
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRANSACTIONS,
                null, null, null, null, null, COLUMN_TANGGAL + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                transaction.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KETERANGAN)));
                transaction.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGAL)));
                transaction.setJumlah(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_JUMLAH)));
                transaction.setTipe(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPE)));

                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return transactions;
    }

    // Update an existing transaction
    public boolean updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_KETERANGAN, transaction.getKeterangan());
        values.put(COLUMN_TANGGAL, transaction.getTanggal());
        values.put(COLUMN_JUMLAH, transaction.getJumlah());
        values.put(COLUMN_TIPE, transaction.getTipe());

        int rowsUpdated = db.update(TABLE_TRANSACTIONS, values,
                COLUMN_ID + " = ?", new String[]{transaction.getId()});
        db.close();

        return rowsUpdated > 0; // Return true if at least one row was updated
    }

    // Delete a transaction
    public boolean deleteTransaction(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TRANSACTIONS,
                COLUMN_ID + " = ?", new String[]{id});
        db.close();

        return rowsDeleted > 0; // Return true if at least one row was deleted
    }
}
