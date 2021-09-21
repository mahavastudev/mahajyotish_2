package com.telemune.model;

public class GenerateKundli {
	private int userId;
    private int requestId=-1;
	private String country="IN";
	private String state="";
	private String city;
	private String tob;
	private String selection;
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}

	private String dob;
	private String name;
	private String gender;
	private String email;
	private String generated;
	private String generateType="D";
	private String latitude="";
	private String longitude="";
	private String timeZone="-5:30";
	private String dst="0";
    private String cityCode;
	private boolean	dasha;
	private boolean	houseDetail;
	private boolean	circle;
	private boolean	saveDetails=false;
	private boolean	aspectChart;
	private boolean	nakshatraPadam=false;
	private boolean	cuspName=false;
	private boolean	aspectScoring=false;
	private String date;
	private String month;
	private String year;
	private String hour;
	private String minute;
	private String second;
    private String isSave="N";
    private String ccCode;
    

		public String getCcCode() {
		return ccCode;
	}
	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}

		/*Transit Kundli*/
        private int isTransitKundli=0;
        private String transitTob="";
        private String transitDob="";
        private String transitDate="";
        private String transitMonth="";
        private String transitYear="";
        private String transitHour="";
        private String transitMinute="";
        private String transitSecond="";
	    private int transitLocation=0;
	    private String transitCountry="IN";
        private String transitState="";
	    private String transitCity;
	    private boolean tranistKundli=false;
        		

		public boolean isTranistKundli() {
			return tranistKundli;
		}
		public void setTranistKundli(boolean tranistKundli) {
			this.tranistKundli = tranistKundli;
		}

		/*   ENDS       */
        private String remarks="";
        private String isBTR="No";
        private String kundliCircleName="";
	    private String kundliData;
        private String scope;
        private String kundliJSON;
        private int eventId;
        private String occupation="";
        private String accountId="";
        private String contactId="";
        public String getTransitLatitude() {
			return transitLatitude;
		}
		public void setTransitLatitude(String transitLatitude) {
			this.transitLatitude = transitLatitude;
		}
		public String getTransitLongitude() {
			return transitLongitude;
		}
		public void setTransitLongitude(String transitLongitude) {
			this.transitLongitude = transitLongitude;
		}
		public String getTransitCityCodes() {
			return transitCityCodes;
		}
		public void setTransitCityCodes(String transitCityCodes) {
			this.transitCityCodes = transitCityCodes;
		}

		private String transitLatitude;
        private String transitLongitude;
        private String transitCityCodes;
        private boolean	specialKundli;
        
        public boolean isSpecialKundli() {
			return specialKundli;
		}
		public void setSpecialKundli(boolean specialKundli) {
			this.specialKundli = specialKundli;
		}
	//BY ANKUR
	public int getEventId() {
                return eventId;
        }
        public void setEventId(int eventId) {
                this.eventId = eventId;
        }


        public String getScope() {
                return scope;
        }
        public void setScope(String scope) {
                this.scope = scope;
        }


        public String getKundliJSON() {
                return kundliJSON;
        }
        public void setKundliJSON(String kundliJSON) {
                this.kundliJSON = kundliJSON;
        }
        public String getKundliData() {
                return this.kundliData;
        }
        public void setKundliData(String kundliData) {
                this.kundliData = kundliData;
        }


//------END HEREEEE-------------------->>


	public String getKundliCircleName() {
                return this.kundliCircleName;
        }
        public void setKundliCircleName(String kundliCircleName) {
                this.kundliCircleName = kundliCircleName;
        }


	public String getIsBTR() {
                return this.isBTR;
        }
        public void setIsBTR(String isBTR) {
                this.isBTR = isBTR;
        }

	public String getRemarks() {
                return this.remarks;
        }
        public void setRemarks(String remarks) {
                this.remarks = remarks;
        }

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGenerated() {
		return generated;
	}
	public void setGenerated(String generated) {
		this.generated = generated;
	}
	public String getGenerateType() {
		return generateType;
	}
	public void setGenerateType(String generateType) {
		this.generateType = generateType;
	}
	public boolean isDasha() {
		return dasha;
	}
	public void setDasha(boolean dasha) {
		this.dasha = dasha;
	}
	
	public boolean isCircle() {
                return circle;
        }
        public void setCircle(boolean circle) {
                this.circle = circle;
        }

	public boolean isAspectChart() {
                return this.aspectChart;
        }
        public void setAspectChart(boolean aspectChart) {
                this.aspectChart = aspectChart;
        }

	public boolean isAspectScoring() {
                return this.aspectScoring;
        }
        public void setAspectScoring(boolean aspectScoring) {
                this.aspectScoring = aspectScoring;
        }

	public boolean isNakshatraPadam() {
                return this.nakshatraPadam;
        }
        public void setNakshatraPadam(boolean nakshatraPadam) {
                this.nakshatraPadam = nakshatraPadam;
        }

	public boolean isCuspName() {
                return this.cuspName;
        }
        public void setCuspName(boolean cuspName) {
                this.cuspName = cuspName;
        }
	
	public boolean isHouseDetail() {
		return houseDetail;
	}
	public void setHouseDetail(boolean houseDetail) {
		this.houseDetail = houseDetail;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getTob() {
		return tob;
	}
	public void setTob(String tob) {
		this.tob = tob;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public boolean isSaveDetails() {
		return saveDetails;
	}
	public void setSaveDetails(boolean saveDetails) {
		this.saveDetails = saveDetails;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}

        public String getCityCode(){
               return cityCode;
        }
        
        public void setCityCode(String cityCode){
               this.cityCode = cityCode;
        }
        
        public void setIsSave(String isSave){
               this.isSave = isSave;
        }
        
        public String getIsSave(){
               return isSave;
        }
       
        public String getTransitDob() {
                return transitDob;
        }
        public void setTransitDob(String transitDob) {
                this.transitDob = transitDob;
        }
        public String getTransitTob() {
                return transitTob;
        }
        public void setTransitTob(String transitTob) {
                this.transitTob = transitTob;
        }
        public String getTransitDate() {
                return transitDate;
        }
        public void setTransitDate(String transitDate) {
                this.transitDate = transitDate;
        }
        public String getTransitMonth() {
                return transitMonth;
        }
        public void setTransitMonth(String transitMonth) {
                this.transitMonth = transitMonth;
        }
        public String getTransitYear() {
                return transitYear;
        }
        public void setTransitYear(String transitYear) {
                this.transitYear = transitYear;
        }
        public String getTransitHour() {
                return transitHour;
        }
        public void setTransitHour(String transitHour) {
                this.transitHour = transitHour;
        }
        public String getTransitMinute() {
                return transitMinute;
        }
        public void setTransitMinute(String transitMinute) {
                this.transitMinute = transitMinute;
        }
        public String getTransitSecond() {
                return transitSecond;
        }
        public void setTransitSecond(String transitSecond) {
                this.transitSecond = transitSecond;
        }

        public int getIsTransitKundli() {
                return isTransitKundli;
        }
        public void setIsTransitKundli(int isTransitKundli) {
                this.isTransitKundli = isTransitKundli;
        }
/*VERSION 4.4*/
	public int getTransitLocation() {
                return transitLocation;
        }
        public void setTransitLocation(int transitLocation) {
                this.transitLocation = transitLocation;
        }	

	public String getTransitCountry() {
                return transitCountry;
        }
        public void setTransitCountry(String transitCountry) {
                this.transitCountry = transitCountry;
        }

	public String getTransitState() {
                return transitState;
        }
        public void setTransitState(String transitState) {
                this.transitState = transitState;
        }

	public String getTransitCity() {
                return transitCity;
        }
        public void setTransitCity(String transitCity) {
                this.transitCity = transitCity;
        }


/*    VERSION 4.4 ENDS  */
	
	public String getOccupation() {
                return this.occupation;
        }
        public void setOccupation(String occupation) {
                this.occupation = occupation;
        }
	
	public String getAccountId() {
                return this.accountId;
        }
        public void setAccountId(String accountId) {
                this.accountId = accountId;
        }
          
	public String getContactId() {
                return this.contactId;
        }
        public void setContactId(String contactId) {
                this.contactId = contactId;
        }
		@Override
		public String toString() {
			return "GenerateKundli [userId=" + userId + ", requestId=" + requestId + ", country=" + country + ", state="
					+ state + ", city=" + city + ", tob=" + tob + ", dob=" + dob + ", name=" + name + ", gender="
					+ gender + ", email=" + email + ", generated=" + generated + ", generateType=" + generateType
					+ ", latitude=" + latitude + ", longitude=" + longitude + ", timeZone=" + timeZone + ", dst=" + dst
					+ ", cityCode=" + cityCode + ", dasha=" + dasha + ", houseDetail=" + houseDetail + ", circle="
					+ circle + ", saveDetails=" + saveDetails + ", aspectChart=" + aspectChart + ", nakshatraPadam="
					+ nakshatraPadam + ", cuspName=" + cuspName + ", aspectScoring=" + aspectScoring + ", date=" + date
					+ ", month=" + month + ", year=" + year + ", hour=" + hour + ", minute=" + minute + ", second="
					+ second + ", isSave=" + isSave + ", isTransitKundli=" + isTransitKundli + ", transitTob="
					+ transitTob + ", transitDob=" + transitDob + ", transitDate=" + transitDate + ", transitMonth="
					+ transitMonth + ", transitYear=" + transitYear + ", transitHour=" + transitHour
					+ ", transitMinute=" + transitMinute + ", transitSecond=" + transitSecond + ", transitLocation="
					+ transitLocation + ", transitCountry=" + transitCountry + ", transitState=" + transitState
					+ ", transitCity=" + transitCity + ", tranistKundli=" + tranistKundli + ", remarks=" + remarks
					+ ", isBTR=" + isBTR + ", kundliCircleName=" + kundliCircleName + ", kundliData=" + kundliData
					+ ", scope=" + scope + ", kundliJSON=" + kundliJSON + ", eventId=" + eventId + ", occupation="
					+ occupation + ", accountId=" + accountId + ", contactId=" + contactId + ", transitLatitude="
					+ transitLatitude + ", transitLongitude=" + transitLongitude + ", transitCityCodes="
					+ transitCityCodes + ", specialKundli=" + specialKundli + "]";
		}
			
}
