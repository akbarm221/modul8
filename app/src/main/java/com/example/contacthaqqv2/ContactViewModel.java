package com.example.contacthaqqv2; // Sesuaikan package

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;


public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;
    private final LiveData<List<ContactModel>> mAllContacts;

    public ContactViewModel(Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getAllContacts();
    }


    public LiveData<List<ContactModel>> getAllContacts() {
        return mAllContacts;
    }


    public LiveData<ContactModel> getContactById(int contactId) {
        return mRepository.getContactById(contactId);
    }


    public void insert(ContactModel contact) {
        mRepository.insert(contact);
    }


    public void delete(ContactModel contact) {
        mRepository.delete(contact);
    }


}