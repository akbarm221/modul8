package com.example.contacthaqqv2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailContactActivity extends AppCompatActivity {


    public static final String EXTRA_CONTACT_ID = "com.example.contacthaqqv2.CONTACT_ID";

    public static final int INVALID_CONTACT_ID = -1;


    private TextView tvName, tvNumber, tvInstagram, tvGroup, back_link;
    private TextView btnCall, btnMessage, btnInstagram;
    private LinearLayout layWhatsapp;


    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);


        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        back_link = findViewById(R.id.back_link);
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        tvInstagram = findViewById(R.id.tv_instagram);
        tvGroup = findViewById(R.id.tv_group);
        btnCall = findViewById(R.id.btn_call);
        btnMessage = findViewById(R.id.btn_message);
        btnInstagram = findViewById(R.id.btn_instagram);
        layWhatsapp = findViewById(R.id.layout_whatsapp);


        Intent intent = getIntent();
        int contactId = intent.getIntExtra(EXTRA_CONTACT_ID, INVALID_CONTACT_ID);


        if (contactId == INVALID_CONTACT_ID) {
            Toast.makeText(this, "Error: Kontak tidak ditemukan.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        contactViewModel.getContactById(contactId).observe(this, new Observer<ContactModel>() {
            @Override
            public void onChanged(ContactModel contact) {

                if (contact != null) {

                    populateUI(contact);

                    setupButtonClickListeners(contact);
                } else {

                    Toast.makeText(DetailContactActivity.this, "Kontak tidak lagi tersedia.", Toast.LENGTH_SHORT).show();

                }
            }
        });


        back_link.setOnClickListener(v -> finish());
    }


    private void populateUI(ContactModel contact) {
        tvName.setText(contact.getName());
        tvNumber.setText(contact.getNumber());
        tvInstagram.setText(contact.getInstagram() != null ? contact.getInstagram() : "-");
        tvGroup.setText(contact.getGroup() != null ? contact.getGroup() : "-");


        btnInstagram.setVisibility(contact.getInstagram() != null && !contact.getInstagram().isEmpty() ? View.VISIBLE : View.GONE);

    }


    private void setupButtonClickListeners(final ContactModel contact) {
        btnCall.setOnClickListener(v -> {
            if (contact.getNumber() != null && !contact.getNumber().isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact.getNumber()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                try {
                    startActivity(callIntent);
                } catch (SecurityException e) {
                    Toast.makeText(this, "Izin menelepon ditolak.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Nomor telepon tidak valid.", Toast.LENGTH_SHORT).show();
            }
        });

        btnMessage.setOnClickListener(v -> {
            if (contact.getNumber() != null && !contact.getNumber().isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("sms:" + contact.getNumber()));
                startActivity(smsIntent);
            } else {
                Toast.makeText(this, "Nomor telepon tidak valid.", Toast.LENGTH_SHORT).show();
            }
        });

        layWhatsapp.setOnClickListener(v -> {
            if (contact.getNumber() != null && !contact.getNumber().isEmpty()) {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String numberForWa = contact.getNumber().startsWith("+") ? contact.getNumber() : "+62" + contact.getNumber().substring(1); // Contoh penanganan kode negara
                String url = "https://api.whatsapp.com/send?phone=" + numberForWa + "&text=";
                sendIntent.setData(Uri.parse(url));
                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "WhatsApp tidak terinstall.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Nomor telepon tidak valid.", Toast.LENGTH_SHORT).show();
            }
        });

        btnInstagram.setOnClickListener(v -> {
            if (contact.getInstagram() != null && !contact.getInstagram().isEmpty()) {
                String username = contact.getInstagram();
                try {

                    Intent igIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/" + username));
                    igIntent.setPackage("com.instagram.android");
                    startActivity(igIntent);
                } catch (android.content.ActivityNotFoundException e) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + username)));
                }
            }

        });
    }
}