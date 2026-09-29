package com.smartpantry.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    public interface OnIngredientActionListener {
        void onEdit(Ingredient ingredient);

        void onDelete(Ingredient ingredient);
    }

    private final List<Ingredient> ingredientList;
    private final OnIngredientActionListener listener;

    public IngredientAdapter(
            List<Ingredient> ingredientList,
            OnIngredientActionListener listener) {

        this.ingredientList = ingredientList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_ingredient,
                        parent,
                        false
                );

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder,
            int position) {

        Ingredient ingredient =
                ingredientList.get(position);

        holder.tvName.setText(
                ingredient.getName()
        );

        holder.tvQuantity.setText(
                ingredient.getQuantity()
                        + " "
                        + ingredient.getUnit()
        );

        String category = ingredient.getCategory();

        holder.tvCategory.setText(
                category == null || category.isEmpty()
                        ? "No category"
                        : category
        );

        holder.btnEdit.setOnClickListener(v ->
                listener.onEdit(ingredient)
        );

        holder.btnDelete.setOnClickListener(v ->
                listener.onDelete(ingredient)
        );
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvQuantity;
        TextView tvCategory;

        Button btnEdit;
        Button btnDelete;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            tvName =
                    itemView.findViewById(
                            R.id.tvIngredientName
                    );

            tvQuantity =
                    itemView.findViewById(
                            R.id.tvIngredientQuantity
                    );

            tvCategory =
                    itemView.findViewById(
                            R.id.tvIngredientCategory
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEditIngredient
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDeleteIngredient
                    );
        }
    }
}