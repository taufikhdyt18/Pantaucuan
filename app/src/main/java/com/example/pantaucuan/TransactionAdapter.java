package com.example.pantaucuan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    private Context context;
    private NumberFormat currencyFormat;
    private OnTransactionLongClickListener longClickListener;

    public interface OnTransactionLongClickListener {
        void onTransactionLongClick(Transaction transaction, int position);
    }

    public void setOnTransactionLongClickListener(OnTransactionLongClickListener listener) {
        this.longClickListener = listener;
    }

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.tvKeterangan.setText(transaction.getKeterangan());
        holder.tvTanggal.setText(transaction.getTanggal());

        String amount = currencyFormat.format(transaction.getJumlah());
        if (transaction.getTipe().equals("pengeluaran")) {
            amount = "- " + amount;
            holder.tvJumlah.setTextColor(context.getResources().getColor(android.R.color.black));
        } else {
            amount = "+ " + amount;
            holder.tvJumlah.setTextColor(context.getResources().getColor(android.R.color.black));
        }
        holder.tvJumlah.setText(amount);

        // Long click listener for delete
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onTransactionLongClick(transaction, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void updateData(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        transactions.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKeterangan, tvTanggal, tvJumlah;

        ViewHolder(View itemView) {
            super(itemView);
            tvKeterangan = itemView.findViewById(R.id.tv_keterangan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvJumlah = itemView.findViewById(R.id.tv_jumlah);
        }
    }
}
