package com.example.contacthaqqv2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "contact_table")
public class ContactModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "phone_number")
    private String number;

    @ColumnInfo(name = "contact_group")
    private String group;

    @ColumnInfo(name = "instagram_username")
    private String instagram;


    public ContactModel(String name, String number, String group, String instagram) {
        this.name = name;
        this.number = number;
        this.group = group;
        this.instagram = instagram;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }
}