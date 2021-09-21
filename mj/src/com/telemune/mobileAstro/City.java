package com.telemune.mobileAstro;

public class City {

	 private String cityId;
     private String cityName;


     public City(String cityId,String cityName)
     {
             this.cityId=cityId;
             this.cityName=cityName;


     }
     public String getCityId() {
             return cityId;
     }

     public String getCityName() {
             return cityName;
     }


}
