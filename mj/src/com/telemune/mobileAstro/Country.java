package com.telemune.mobileAstro;

public class Country {
	  private String countryId;



      private String countryName;

      public Country(String countryId,String countryName)
      {
              this.countryId=countryId;
              this.countryName=countryName;


      }
      public String getCountryId() {
              return countryId;
      }

      public String getCountryName() {
              return countryName;

      }

}
