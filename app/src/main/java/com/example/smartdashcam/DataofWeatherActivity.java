package com.example.smartdashcam;

import org.json.JSONException;
import org.json.JSONObject;

public class DataofWeatherActivity {

    private String temperature, icon, city, weather;
    private int condition;

    public static DataofWeatherActivity fromJson(JSONObject jsonObject)
    {

        try
        {
            DataofWeatherActivity dataofWeatherActivity=new DataofWeatherActivity();
            dataofWeatherActivity.city=jsonObject.getString("name");
            dataofWeatherActivity.condition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            dataofWeatherActivity.weather=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            dataofWeatherActivity.icon=updateWeatherIcon(dataofWeatherActivity.condition);
            //wanna display City
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273;
            int roundedValue=(int)Math.rint(tempResult);
            //Click on list condition code includes the list of id
            dataofWeatherActivity.temperature = Integer.toString(roundedValue);
            return dataofWeatherActivity;
        }


        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "cthunderstrom";
        }
        else if(condition>=300 && condition<=500)
        {
            return "littlerain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "snow";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "cloudy";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "clouds";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "thunderstrom";
        }
        if(condition==903)
        {
            return "snow";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstrom";
        }

        return "dunno";


    }


    public String getTemperature() {
        return temperature+"°C";
    }

    public String getIcon() {
        return icon;
    }

    public String getCity() {
        return city;
    }

    public String getWeather() {
        return weather;
    }
}