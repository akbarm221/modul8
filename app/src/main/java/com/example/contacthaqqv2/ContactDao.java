package com.example.contacthaqqv2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ContactModel contact);

    @Delete
    void delete(ContactModel contact);

    @Query("SELECT * FROM contact_table ORDER BY contact_name ASC")
    LiveData<List<ContactModel>> getAllContacts();
}