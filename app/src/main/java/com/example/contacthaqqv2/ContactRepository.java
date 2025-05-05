package com.example.contacthaqqv2; // Sesuaikan package

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ContactRepository {

    private ContactDao mContactDao;
    private LiveData<List<ContactModel>> mAllContacts;

    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }

    public LiveData<List<ContactModel>> getAllContacts() {
        return mAllContacts;
    }


    public LiveData<ContactModel> getContactById(int contactId) {
        return mContactDao.getContactById(contactId);
    }


    public void insert(ContactModel contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            mContactDao.insert(contact);
        });
    }

    public void delete(ContactModel contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            mContactDao.delete(contact);
        });
    }
}