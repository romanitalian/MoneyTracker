package net.romanitalian.moneytrackerapp.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.springframework.http.converter.json.GsonHttpMessageConverter;

public class MessageConverter extends GsonHttpMessageConverter {
    public MessageConverter() {
        setGson(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd")
                .create());
    }
}
