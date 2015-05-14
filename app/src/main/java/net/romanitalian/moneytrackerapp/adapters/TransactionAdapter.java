package net.romanitalian.moneytrackerapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Transaction;

import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.CardViewHolder> {
    List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {

        this.transactions = transactions;
    }

    //TransactionAdapter

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_transactions, parent, false);

        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.title.setText(transaction.getTitle());
        holder.sum.setText(transaction.getSum());

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView sum;

        public CardViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.transaction_title);
            sum = (TextView) itemView.findViewById(R.id.transaction_sum);
        }
    }
}

