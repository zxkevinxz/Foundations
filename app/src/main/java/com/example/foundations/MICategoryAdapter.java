package com.example.foundations;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MICategoryAdapter extends RecyclerView.Adapter<MICategoryAdapter.MICategoryAdapterViewHolder> {

    private static final String TAG = "miCategoryAdapter";
    private final LayoutInflater inflater;
    private final int currentReportId;
    private List<Category> miCategories;
    private List<SubCategory> miSubcategories;
    private List<ListItem> miListItems;
    MISubcategoryAdapter miSubcategoryAdapter;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    SubcategoryHandler subcategoryHandler;
    MainViewModel mainViewModel;



    @NonNull
    @Override
    public MICategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miCategoryLayoutView = inflater.inflate(R.layout.mi_category_item, parent, false);
        return new MICategoryAdapterViewHolder(miCategoryLayoutView);
    }

//    @Override
//    public void onBindViewHolder(@NonNull MICategoryAdapterViewHolder holder, int position) {
//        if (miCategories != null) {
//            Category currentCategory = miCategories.get(position);
//            Log.d(TAG, "onBindViewHolder: " + currentCategory.getTitle());
//
//            holder.miCategoryItemView.setText(currentCategory.getTitle());
//        }
//    }

    @Override
    public void onBindViewHolder(@NonNull MICategoryAdapterViewHolder holder, int position) {
        if (miCategories != null && miSubcategories != null) {
            Category currentCategory = miCategories.get(position);
            int categoryId = currentCategory.getCategoryId();
            holder.miCategoryItemView.setText(currentCategory.getTitle());
            List<SubCategory> filteredSubcatList = new ArrayList<>();
            List<ListItem> filteredListItemList = new ArrayList<>();
            for (int i = 0; i < miSubcategories.size(); i++) {
                if (miSubcategories.get(i).getCategoryId() == categoryId) {
                    filteredSubcatList.add(miSubcategories.get(i));
                    int subCategoryId = miSubcategories.get(i).getSubCategoryId();
                    for (int y = 0; y < miListItems.size(); y++) {
                        if (miListItems.get(y).getCategoryId() == categoryId && subCategoryId == miListItems.get(y).getSubCategoryId() &&
                                currentReportId == miListItems.get(y).getReportId()) {
                            filteredListItemList.add(miListItems.get(y));
                        }
                    }
                }
            }
            holder.addItem.setOnClickListener(v -> {
                subcategoryHandler.showListItemDialog(mainViewModel, filteredSubcatList);
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.miSubcategoryRecyclerView.getContext());
            miSubcategoryAdapter = new MISubcategoryAdapter(filteredSubcatList, filteredListItemList);
            holder.miSubcategoryRecyclerView.setLayoutManager(layoutManager);
            holder.miSubcategoryRecyclerView.setAdapter(miSubcategoryAdapter);
            holder.miSubcategoryRecyclerView.setRecycledViewPool(viewPool);
        }
    }



    @Override
    public int getItemCount() {
        if (miCategories != null) {
            return miCategories.size();
        } else return 0;
    }

    void setMiCategories(List<Category> categories) {
        this.miCategories = categories;
        subcategoryHandler.setAllCategories(categories);
        notifyDataSetChanged();
    }

    void setMiListItems(List<ListItem> listItems) {
        this.miListItems = listItems;
        notifyDataSetChanged();
    }

    void setMiSubcategories(List<SubCategory> subcategories) {
        this.miSubcategories = subcategories;
        notifyDataSetChanged();
    }

    static class MICategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView miCategoryItemView;
        private final RecyclerView miSubcategoryRecyclerView;
        Button addItem;
        Button editItem;

        public MICategoryAdapterViewHolder(View itemView) {
            super(itemView);
            miCategoryItemView = itemView.findViewById(R.id.mi_category_item_title);
            miSubcategoryRecyclerView = itemView.findViewById(R.id.mi_subcategory_recyclerview);
            addItem = itemView.findViewById(R.id.mi_add_item);
            editItem = itemView.findViewById(R.id.mi_edit_item);
        }
    }

    public MICategoryAdapter(Context context, SubcategoryHandler subcategoryHandler, MainViewModel mainViewModel, int currentReportId) {
        inflater = LayoutInflater.from(context);
        this.mainViewModel = mainViewModel;
        this.subcategoryHandler = subcategoryHandler;
        this.currentReportId = currentReportId;
    }
}