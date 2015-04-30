package net.romanitalian.moneytrackerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Transaction;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
        this.transactions = transactions;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction transactions = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title_id);
        TextView sum = (TextView) convertView.findViewById(R.id.sum_id);
        TextView date = (TextView) convertView.findViewById(R.id.date_id);

        title.setText(transactions.getTitle());
        sum.setText(Integer.toString(transactions.getSum()));
        date.setText(transactions.getDate());
        int color = position % 2 == 0 ? getContext().getResources().getColor(R.color.wht) : getContext().getResources().getColor(R.color.grn);
        convertView.setBackgroundColor(color);
        return convertView;
    }
}