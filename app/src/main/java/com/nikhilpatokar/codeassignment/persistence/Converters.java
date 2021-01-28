package com.nikhilpatokar.codeassignment.persistence;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nikhilpatokar.codeassignment.models.Dob;
import com.nikhilpatokar.codeassignment.models.Id;
import com.nikhilpatokar.codeassignment.models.Location;
import com.nikhilpatokar.codeassignment.models.Login;
import com.nikhilpatokar.codeassignment.models.Name;
import com.nikhilpatokar.codeassignment.models.Picture;
import com.nikhilpatokar.codeassignment.models.Registered;
import com.nikhilpatokar.codeassignment.models.Street;
import com.nikhilpatokar.codeassignment.models.Timezone;

import java.lang.reflect.Type;

public class Converters {

    @TypeConverter
    public static Name fromNameString(String value){
        Type listType = new TypeToken<Name>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromName(Name name){
        Gson gson = new Gson();
        String json = gson.toJson(name);
        return json;
    }

    @TypeConverter
    public static Location fromLocationString(String value){
        Type listType = new TypeToken<Location>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromLocation(Location location){
        Gson gson = new Gson();
        String json = gson.toJson(location);
        return json;
    }

    @TypeConverter
    public static Login fromLoginString(String value){
        Type listType = new TypeToken<Login>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromLogin(Login login){
        Gson gson = new Gson();
        String json = gson.toJson(login);
        return json;
    }

    @TypeConverter
    public static Dob fromDobString(String value){
        Type listType = new TypeToken<Dob>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromDob(Dob dob){
        Gson gson = new Gson();
        String json = gson.toJson(dob);
        return json;
    }

    @TypeConverter
    public static Registered fromRegisteredString(String value){
        Type listType = new TypeToken<Registered>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRegistered(Registered registered){
        Gson gson = new Gson();
        String json = gson.toJson(registered);
        return json;
    }

    @TypeConverter
    public static Street fromStreetString(String value){
        Type listType = new TypeToken<Street>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromStreet(Street street){
        Gson gson = new Gson();
        String json = gson.toJson(street);
        return json;
    }

    @TypeConverter
    public static Timezone fromTimeZoneString(String value){
        Type listType = new TypeToken<Timezone>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromTimeZone(Timezone timezone){
        Gson gson = new Gson();
        String json = gson.toJson(timezone);
        return json;
    }

    @TypeConverter
    public static Id fromIdString(String value){
        Type listType = new TypeToken<Id>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromId(Id id){
        Gson gson = new Gson();
        String json = gson.toJson(id);
        return json;
    }

    @TypeConverter
    public static Picture fromPictureString(String value){
        Type listType = new TypeToken<Picture>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPicture(Picture picture){
        Gson gson = new Gson();
        String json = gson.toJson(picture);
        return json;
    }
}














