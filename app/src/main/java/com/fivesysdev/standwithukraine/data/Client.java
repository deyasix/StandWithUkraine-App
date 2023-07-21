package com.fivesysdev.standwithukraine.data;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Client {

    private final OkHttpClient httpClient = new OkHttpClient();
    private static final String URL = "https://russianwarship.rip/api/v2";

    public List<DayStatistic> getStatisticsFromDate(String date) throws IOException, JSONException {
        List<DayStatistic> fetchedStatistics = new ArrayList<>();
        HttpUrl.Builder httpBuilder = HttpUrl.parse(URL + "/statistics").newBuilder();
        httpBuilder.addQueryParameter("date_from", date);
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            try (ResponseBody responseBody = response.body()) {
                if (responseBody != null) fetchedStatistics = putStatisticsToList(responseBody);
            }
        }
        return fetchedStatistics;
    }

    private List<DayStatistic> putStatisticsToList(ResponseBody body) throws IOException, JSONException {
        ArrayList<DayStatistic> fetchedStatistics = new ArrayList<>();
        JSONArray days = new JSONObject(body.string())
                .getJSONObject("data")
                .getJSONArray("records");
        for (int i = 0; i < days.length(); i++) {
            String dayDate = new JSONObject(days.get(i).toString()).getString("date");
            JSONObject dayStats = days.getJSONObject(i).getJSONObject("stats");
            JSONObject increaseStats = days.getJSONObject(i).getJSONObject("increase");
            JSONArray names = dayStats.names();
            ArrayList<Pair<Integer, Integer>> dayStatistics = new ArrayList<>();
            for (int j = 0; j < names.length(); j++) {
                int quantity = dayStats.getInt(names.getString(j));
                int increaseQuantity = increaseStats.getInt(names.getString(j));
                dayStatistics.add(new Pair<>(quantity, increaseQuantity));
            }
            fetchedStatistics.add(new DayStatistic(dayDate, dayStatistics));
        }
        return fetchedStatistics;
    }

}