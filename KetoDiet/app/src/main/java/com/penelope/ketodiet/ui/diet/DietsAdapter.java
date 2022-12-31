package com.penelope.ketodiet.ui.diet;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.penelope.ketodiet.data.diet.Diet;
import com.penelope.ketodiet.databinding.DietItemBinding;

import java.util.Locale;

public class DietsAdapter extends ListAdapter<Diet, DietsAdapter.DietViewHolder> {

    class DietViewHolder extends RecyclerView.ViewHolder {

        private final DietItemBinding binding;

        public DietViewHolder(DietItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(position);
                }
            });

            binding.imageViewDeleteDiet.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemSelectedListener != null) {
                    onItemSelectedListener.onDeleteClick(position);
                }
            });
        }

        public void bind(Diet model) {

            binding.textViewDietName.setText(model.getName());

            String strNutrition = String.format(Locale.getDefault(),
                    "%dkcal, 탄수화물 %.1fg, 단백질 %.1fg, 지방 %.1f",
                    Math.round(model.getCalories()),
                    model.getCarbohydrates(), model.getProtein(), model.getFat()
            );
            binding.textViewDietNutrition.setText(strNutrition);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);

        void onDeleteClick(int position);
    }

    private OnItemSelectedListener onItemSelectedListener;


    public DietsAdapter() {
        super(new DiffUtilCallback());
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    @NonNull
    @Override
    public DietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DietItemBinding binding = DietItemBinding.inflate(layoutInflater, parent, false);
        return new DietViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DietViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    static class DiffUtilCallback extends DiffUtil.ItemCallback<Diet> {

        @Override
        public boolean areItemsTheSame(@NonNull Diet oldItem, @NonNull Diet newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Diet oldItem, @NonNull Diet newItem) {
            return oldItem.equals(newItem);
        }
    }

}