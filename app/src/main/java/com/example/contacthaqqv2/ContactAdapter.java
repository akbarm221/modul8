package com.example.contacthaqqv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends ListAdapter<ContactModel, ContactAdapter.ContactViewHolder> {

    private OnItemDeleteListener deleteListener;


    public ContactAdapter(@NonNull DiffUtil.ItemCallback<ContactModel> diffCallback) {
        super(diffCallback);
    }


    public interface OnItemDeleteListener {
        void onDeleteClick(ContactModel contact);
    }


    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new ContactViewHolder(view, deleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        ContactModel currentContact = getItem(position);

        holder.bind(currentContact);
    }

    // Kelas ViewHolder (Inner class, NON-STATIC)
    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout contactLayout;
        private final TextView tvName;
        private final TextView tvNumber;
        private final TextView tvDelete;

        public ContactViewHolder(@NonNull View itemView, OnItemDeleteListener listener) {
            super(itemView);


            contactLayout = itemView.findViewById(R.id.contact_layout);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvDelete = itemView.findViewById(R.id.tv_delete);



            tvDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {

                    listener.onDeleteClick(getItem(position));
                }
            });


            contactLayout.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {

                    ContactModel contact = getItem(position);
                    Context context = itemView.getContext();

                    Intent intent = new Intent(context, DetailContactActivity.class);

                    intent.putExtra(DetailContactActivity.EXTRA_CONTACT_ID, contact.getId());

                    context.startActivity(intent);
                }
            });


        }


        public void bind(ContactModel contact) {
            if (contact != null) {
                tvName.setText(contact.getName());
                tvNumber.setText(contact.getNumber());
                // Set teks atau properti view lain jika ada
            }
        }
    }


    public static class ContactDiff extends DiffUtil.ItemCallback<ContactModel> {
        @Override
        public boolean areItemsTheSame(@NonNull ContactModel oldItem, @NonNull ContactModel newItem) {

            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ContactModel oldItem, @NonNull ContactModel newItem) {

            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getNumber().equals(newItem.getNumber()) &&
                    oldItem.getGroup().equals(newItem.getGroup()) &&
                    oldItem.getInstagram().equals(newItem.getInstagram());

        }
    }
}