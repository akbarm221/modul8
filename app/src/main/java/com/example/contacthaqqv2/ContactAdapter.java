package com.example.contacthaqqv2;

// ... import lainnya ...
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// ...

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


    public class ContactViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contactLayout;
        TextView tvName, tvNumber, tvDelete;


        public ContactViewHolder(@NonNull View itemView, OnItemDeleteListener listener) { // Terima listener
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
                if(position != RecyclerView.NO_POSITION){

                    ContactModel contact = getItem(position);
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, DetailContactActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cname", contact.getName());
                    bundle.putString("cnumber", contact.getNumber());
                    bundle.putString("cinstagram", contact.getInstagram());
                    bundle.putString("cgroup", contact.getGroup());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }


        public void bind(ContactModel contact) {
            tvName.setText(contact.getName());
            tvNumber.setText(contact.getNumber());
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