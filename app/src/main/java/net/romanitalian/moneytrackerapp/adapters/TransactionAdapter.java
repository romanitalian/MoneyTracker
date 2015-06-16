package net.romanitalian.moneytrackerapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Transaction;
import net.romanitalian.moneytrackerapp.utils.Udate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class TransactionAdapter extends SelectableAdapter<TransactionAdapter.CardViewHolder> {
    List<Transaction> transactions;
    private CardViewHolder.ClickListener clickListener;

    public TransactionAdapter(List<Transaction> transactions, CardViewHolder.ClickListener clickListener) {
        this.transactions = transactions;
        this.clickListener = clickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transactions, parent, false);
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.title.setText(transaction.getTitle());
        holder.sum.setText(String.valueOf(transaction.getSum()));
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        DateFormat df = new SimpleDateFormat(Udate.dateFormat, new Locale("ru"));
        String _date = df.format(transaction.getDate());
        holder.date.setText(_date);
    }

    public void removeItem(int position) {
        transactions.remove(position);
        if (transactions.get(position) != null) {
            transactions.get(position).delete();
        }
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    removeItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            transactions.remove(positionStart);
            if (transactions.get(positionStart) != null) {
                transactions.get(positionStart).delete();
            }
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected TextView title;
        protected TextView sum;
        protected TextView date;
        protected View selectedOverlay;
        private ClickListener clickListener;

        public CardViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.transaction_title);
            sum = (TextView) itemView.findViewById(R.id.transaction_sum);
            date = (TextView) itemView.findViewById(R.id.transaction_date);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClicked(getPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return clickListener != null && clickListener.onItemLongClicked(getPosition());
        }

        public interface ClickListener {
            public void onItemClicked(int position);

            public boolean onItemLongClicked(int position);
        }

    }
}
