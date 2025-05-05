package com.example.contacthaqqv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private ContactViewModel contactViewModel;
    private ContactAdapter contactAdapter;

    private RecyclerView recyclerView;
    private TextView option;
    private LinearLayout layAddContact;
    private EditText etName, etNumber, etInstagram, etGroup;
    private Button btnClear, btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        recyclerView = findViewById(R.id.recycle_contact);
        layAddContact = findViewById(R.id.layout_add);
        option = findViewById(R.id.tv_option);
        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);
        etInstagram = findViewById(R.id.et_instagram);
        etGroup = findViewById(R.id.et_group);
        btnClear = findViewById(R.id.btn_clear);
        btnSubmit = findViewById(R.id.btn_submit);

        contactAdapter = new ContactAdapter(new ContactAdapter.ContactDiff());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this, new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contacts) {
                contactAdapter.submitList(contacts);
            }
        });

        option.setOnClickListener(v -> {
            if (recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
                layAddContact.setVisibility(View.VISIBLE);
                clearData();
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                layAddContact.setVisibility(View.GONE);
            }
        });

        btnClear.setOnClickListener(v -> clearData());

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String number = etNumber.getText().toString().trim();
            String group = etGroup.getText().toString().trim();
            String instagram = etInstagram.getText().toString().trim();

            if (name.isEmpty() || number.isEmpty()) {
                Toast.makeText(ContactActivity.this, "Nama dan Nomor tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                ContactModel newContact = new ContactModel(name, number, group, instagram);
                contactViewModel.insert(newContact);

                recyclerView.setVisibility(View.VISIBLE);
                layAddContact.setVisibility(View.GONE);

                Toast.makeText(ContactActivity.this, "Kontak disimpan", Toast.LENGTH_SHORT).show();
            }
        });

        contactAdapter.setOnItemDeleteListener(new ContactAdapter.OnItemDeleteListener() {
            @Override
            public void onDeleteClick(ContactModel contact) {
                contactViewModel.delete(contact);
                Toast.makeText(ContactActivity.this, "Kontak dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearData() {
        etName.setText("");
        etNumber.setText("");
        etInstagram.setText("");
        etGroup.setText("");
    }
}