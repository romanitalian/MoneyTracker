package net.romanitalian.moneytrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transactions> {
    List<Transactions> transactions;

    public TransactionAdapter(Context context, List<Transactions> transactions) {
        super(context, 0, transactions);
        this.transactions = transactions;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Transactions transactions = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title_id);
        TextView sum = (TextView) convertView.findViewById(R.id.sum_id);

        title.setText(transactions.getTitle());
        sum.setText(Integer.toString(transactions.getSum()));
        return convertView;
    }
}
