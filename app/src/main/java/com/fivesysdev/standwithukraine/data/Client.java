package com.fivesysdev.standwithukraine.data;

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

    public List<DayStatistic> getStatistics() throws IOException {
        ArrayList<DayStatistic> fetchedStatistics = new ArrayList<>();
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://russianwarship.rip/api/v2" + "/statistics").newBuilder();
        httpBuilder.addQueryParameter("date_from", "2023-07-01");
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            try (ResponseBody responseBody = response.body()) {
                JSONArray days = new JSONObject(responseBody.string())
                        .getJSONObject("data")
                        .getJSONArray("records");
                for (int i = 0; i < days.length(); i++) {
                    String date = new JSONObject(days.get(i).toString()).getString("date");
                    JSONObject dayStats = days.getJSONObject(i).getJSONObject("stats");
                    JSONArray names = dayStats.names();
                    ArrayList<Integer> dayStatistics = new ArrayList<>();
                    for (int j = 0; j < names.length(); j++) {
                        Integer quantity = dayStats.getInt(names.getString(j));
                        dayStatistics.add(quantity);
                    }
                    fetchedStatistics.add(new DayStatistic(date, dayStatistics));
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return fetchedStatistics;

    }

}