package com.telemune.mobileAstro;

public class State {
	  private String stateId;
      private String stateName;

      public State(String stateId,String stateName)
      {
              this.stateId=stateId;
              this.stateName=stateName;


      }
      public String getStateId() {
              return stateId;
      }

      public String getStateName() {
              return stateName;

      }

}
