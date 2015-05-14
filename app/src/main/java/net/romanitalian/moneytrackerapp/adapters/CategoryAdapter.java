package net.romanitalian.moneytrackerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.romanitalian.moneytrackerapp.R;
import net.romanitalian.moneytrackerapp.models.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
        this.categories = categories;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Category categories = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_categories, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.category_title);

        title.setText(categories.getTitle());
        return convertView;
    }
}
