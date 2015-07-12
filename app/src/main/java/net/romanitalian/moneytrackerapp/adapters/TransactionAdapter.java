package net.romanitalian.moneytrackerapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Category;
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
    private Context context;
    private int prevPosition = -1;

    public TransactionAdapter(List<Transaction> transactions, Context context, CardViewHolder.ClickListener clickListener) {
        this.transactions = transactions;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transactions, parent, false);
        return new CardViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        Category category = null;
        if (transaction.category_id != -1) {
            category = Category.getAll("").get(transaction.category_id);
        } else {
            Toast.makeText(context, R.string.you_need_to_add_category, Toast.LENGTH_LONG).show();
        }

        holder.title.setText(transaction.comment);
        holder.sum.setText(String.valueOf(transaction.sum));
        holder.categoty.setText(String.valueOf(category != null ? category.toString() : ""));
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        setAnimation(holder.cardView, position);

        DateFormat df = new SimpleDateFormat(Udate.dateFormat, new Locale("ru"));
        String _date = transaction.trDate != null ? df.format(transaction.trDate) : "";
        holder.date.setText(_date);
    }

    private void removeItem_(int position) {
        if (transactions.get(position) != null) {
            transactions.get(position).delete();
            transactions.remove(position);
        }
    }
    public void removeItem(int position) {
        removeItem_(position);
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
        for (int position = 0; position < itemCount; ++position) {
            removeItem_(position);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > prevPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            prevPosition = position;
        }
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected TextView title;
        protected TextView sum;
        protected TextView date;
        protected TextView categoty;
        protected View selectedOverlay;
        private ClickListener clickListener;
        protected CardView cardView;

        public CardViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.transaction_title);
            sum = (TextView) itemView.findViewById(R.id.transaction_sum);
            date = (TextView) itemView.findViewById(R.id.transaction_date);
            categoty = (TextView) itemView.findViewById(R.id.transaction_category_title);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

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
