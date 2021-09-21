package com.telemune.mobileAstro;
import org.apache.log4j.Logger;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Attr;
import org.apache.commons.lang.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class PlanetDetails {
	
	static Logger logger = Logger.getLogger(FinalKundli.class);
    
    DocumentBuilderFactory factory=null;
    DocumentBuilder builder;
    Document doc = null;
    XPathExpression expr = null;

    String planetName []={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};
    String rahuAndKetu [] ={"Rahu","Ketu"};
    String planetEffect [] ={"Jupiter","Venus","Mars"};

    Hashtable<String,Object> planetHashTable = new Hashtable<String,Object>();
    Hashtable<String,Object> signHashTable = new Hashtable<String,Object>();
    Hashtable<String,String> staticSignHashTable = new Hashtable<String,String>();
    Hashtable<String,Object> birthPlanetHashTable = new Hashtable<String,Object>();
    Hashtable<String,Object> birthHouseHashTable = new Hashtable<String,Object>();
    Hashtable<String,Object> conjHashTable = new Hashtable<String,Object>();
    Vector<Object> birthChartData=new Vector<Object>();
    Vector<Object> cuspChartData=new Vector<Object>();

    public PlanetDetails() throws ParserConfigurationException, SAXException,IOException, XPathExpressionException
    {

    }
    public PlanetDetails(String url)throws ParserConfigurationException, SAXException,IOException, XPathExpressionException
    {
            try
            {
                String query="";
        	factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		builder = factory.newDocumentBuilder();

                //Read XML file

                doc = builder.parse(url);

                //Insert Static value into staticSignHashTable hash table

                 staticSignHashTable.put("Mars","1-8");
                 staticSignHashTable.put("Venus","2-7");
                 staticSignHashTable.put("Mercury","3-6");
                 staticSignHashTable.put("Moon","4");
                 staticSignHashTable.put("Sun","5");
                 staticSignHashTable.put("Jupiter","9-12");
                 staticSignHashTable.put("Saturn","10-11");


                 
                  fillConjHashTable(conjHashTable); //Fill Planet Details for conjection of planets.

                  query="BIRTHCHART";

                  fillHashTable(query,birthPlanetHashTable,birthHouseHashTable); //Fill BirtChart Details

                  query="CUSPCHART";

                  fillHashTable(query,planetHashTable,signHashTable); //Fill CuspChart Details

                  

            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
               

    }



    public int getNatalStrength()
    {
    	if(logger.isDebugEnabled()){
    		logger.debug("Inside Function getNatalStrength() ");
    	}
        
        try
        {

            Kundli birthKundli =new Kundli("BIRTH");
            
            if(getBirthCuspChartData("BIRTH",birthKundli)<0) //Get Birth Kundli Data
            {
            	if(logger.isDebugEnabled()){
                logger.debug("Error in getting BIRTH data inside function getBirthCuspChartData ");
            	} 
                return -1;
            }
            if(logger.isDebugEnabled()){
            logger.debug("Birth Chart Data || "+birthKundli.toString());
            }
            Kundli cuspKundli =new Kundli("CUSP");

            if(getBirthCuspChartData("CUSP",cuspKundli)<0)   //Get Cusp  Kundli Data
            {
            	if(logger.isDebugEnabled()){
                logger.debug("Error in getting CUSP data inside function getBirthCuspChartData ");
            	}
                return -1;
            }
            if(logger.isDebugEnabled()){
            logger.debug("Cusp Chart Data || "+cuspKundli.toString());
            }
            // Get Tabular Format Data for all 9 planets.



            

             String planetName []={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};

             String tabularData[][] =new String[9][7];

                for(int loop=0;loop<planetName.length;loop++)
                {
                    // For Source Column

                    String sourceValue=searchPlanetLocation(planetName[loop],planetHashTable,signHashTable,staticSignHashTable );

                    // For Result NL column

                    String resultNL=getNLAndSL("NL",planetName[loop]);
                    if(!StringUtils.isBlank(resultNL))
                            resultNL+=searchPlanetLocation(resultNL.trim(),planetHashTable,signHashTable,staticSignHashTable );

                    // for SL column

                    String resultSL=getNLAndSL("SL",planetName[loop]);
                    if(!StringUtils.isBlank(resultSL))
                            resultSL+=searchPlanetLocation(resultSL.trim(),planetHashTable,signHashTable,staticSignHashTable );

                    //for NL of SL NL(SL)

                    String resultNLOfSL=getNLAndSL("SL",planetName[loop]);
                    if(!StringUtils.isBlank(resultNLOfSL))
                        resultNLOfSL=getNLAndSL("NL",resultNLOfSL.trim());

                    if(!StringUtils.isBlank(resultNLOfSL))
                         resultNLOfSL+=searchPlanetLocation(resultNLOfSL.trim(),planetHashTable,signHashTable,staticSignHashTable );

                    String MDLValue=getNLAndSL("MDL", planetName[loop]);

                    tabularData[loop][0]=planetName[loop];
                    tabularData[loop][1]=sourceValue;
                    tabularData[loop][2]=resultNL;
                    tabularData[loop][3]="NA";
                    tabularData[loop][4]=resultSL;
                    tabularData[loop][5]=resultNLOfSL;
                    tabularData[loop][6]=MDLValue;

                    // logger.info(" Planet Name ["+planetName[loop]+"] SourceValue ["+sourceValue+" ]Result(NL) ["+resultNL+"] ResultSL ["+resultSL+"] resultNLOfSL ["+resultNLOfSL+" MDL Value "+MDLValue);
                    
                }
             for(int outer=0;outer<tabularData.length;outer++)
             {
                 for(int inner=0;inner<tabularData[outer].length;inner++)
                 {

                     System.out.print("     "+tabularData[outer][inner]);


                 }
                 if(logger.isDebugEnabled()){
                 logger.debug("**********************");
                 }
             }


               

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return -1;

        }
        return 0;

    }


    public int getBirthCuspChartData(String query,Kundli kundliData) throws ParserConfigurationException, SAXException,
                        IOException, XPathExpressionException
    {
        try
        {
              // Create a XPathFactory
                XPathFactory xFactory = XPathFactory.newInstance();

                // Create a XPath object
                XPath xpath = xFactory.newXPath();

                // Compile the XPath expression
                //expr = xpath.compile("//person[firstname='Rajendra']/lastname/@*");

                
                if(query.equalsIgnoreCase("BIRTH"))
                       expr = xpath.compile("/ASTROCHART/BIRTHCHART/HOUSE//@NAME");
                else if(query.equalsIgnoreCase("CUSP"))
                       expr = xpath.compile("/ASTROCHART/CUSPCHART/HOUSE//@NAME");
                else
                	if(logger.isDebugEnabled()){
                    logger.debug("Please select Birth Or Cusp");
                	}
                
                Object result = expr.evaluate(doc, XPathConstants.NODESET);

                // Cast the result to a DOM NodeList
                NodeList nodes = (NodeList) result;
                int counter=1;
                for (int i=0; i<nodes.getLength();){
                  
                       StringBuffer birthData = new StringBuffer();
                       Attr planetAttr = (Attr) nodes.item(i+1);

                        Attr signAttr = (Attr) nodes.item(i+2);
                       birthData.append(signAttr.getValue());
                        birthData.append("_");
                        birthData.append(planetAttr.getValue());
                        if(logger.isDebugEnabled()){
                       logger.debug("Planet Name and   Sign Value  || "+birthData.toString());

                        }
                           kundliData.setHouseData(counter ,birthData.toString());

                       i=i+3;
                       counter++;



                }

        }catch(Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
return 0;

        }









    public String getConjPlanetList(String rahuAndKetu,Hashtable conjHashTable)
    {
        StringBuffer planetList=new StringBuffer();
        int plusDegree=0,minusDegree=0;
        boolean isForword=false,isBackword=false,insertStatus=false;
        if(conjHashTable.containsKey(rahuAndKetu))
        {
        	if(logger.isDebugEnabled()){
            logger.debug("My conjHashTable contain key for planet || "+rahuAndKetu);
        	}
            RashiHouseBean rashiBean=(RashiHouseBean)conjHashTable.get(rahuAndKetu);
            if(logger.isDebugEnabled()){
            logger.debug("Degree Value of planet ["+rahuAndKetu+"] ["+rashiBean.getDegree()+"]");
            }
            plusDegree=Integer.parseInt(rashiBean.getDegree())+4;
            minusDegree=Integer.parseInt(rashiBean.getDegree())-4;
            if(logger.isDebugEnabled()){
            logger.debug(" Before PlusDegree ["+plusDegree+"] MinusDegree ["+minusDegree+"] Rashi Name [ "+rashiBean.getSignNumber());
            }
            if(plusDegree>30)
            {
             isForword=true;
             plusDegree=plusDegree-30;
            }
            if(minusDegree<0)
            {
             isBackword=true;
             minusDegree=30+minusDegree;
            }
            if(logger.isDebugEnabled()){
            logger.debug("After PlusDegree ["+plusDegree+"] MinusDegree ["+minusDegree+"] Forward Flag ["+isForword+"] Backword Flag ["+isBackword+"]");
            }
            //Check Rashi form rashiBean.getSignNumber() is exist with other planet

                Iterator it=conjHashTable.values().iterator();
                while(it.hasNext())
                {
                    insertStatus=false;
                        
                        RashiHouseBean obj=(RashiHouseBean)it.next();

                        if(obj.getSignNumber().equals(rashiBean.getSignNumber()))
                        {
                        	if(logger.isDebugEnabled()){
                    			logger.debug("Sign conjuction aries at planetName "+obj.getPlanetName()+" and sign name "+obj.getSignNumber()+"Both planet name || "+obj.getPlanetName() +" and "+rashiBean.getPlanetName());
                        	}
                    			if(isForword)
                    			{
                                if(minusDegree<=Integer.parseInt(obj.getDegree()))
                                    insertStatus=true;
                                if(logger.isDebugEnabled()){
                                logger.debug("Is Forword flag is true ");
                                }
                            }
                            if(isBackword)
                            {
                                if(plusDegree<=Integer.parseInt(obj.getDegree()))
                                    insertStatus=true;
                                if(logger.isDebugEnabled()){
                                logger.debug("Is Backword flag is true ");
                                }
                            }
                            if(!isForword&&!isBackword)
                            {
                                if((plusDegree<=Integer.parseInt(obj.getDegree()))||(minusDegree<=Integer.parseInt(obj.getDegree())))
                                    insertStatus=true;
                            }


                        }
                       
                        if(insertStatus)
                        {
                            planetList.append(obj.getPlanetName());
                            planetList.append("_");
                        }


                }
                if(logger.isDebugEnabled()){
                logger.debug("Data Insert into planetList "+planetList.toString());
                }
                //if Forword or backword is true
                
                String forBackSignName="";
                int nextPrevSignNumber=-1;
                Rashi myrashi=Rashi.toRashi(rashiBean.getSignNumber()); //My current Rashi
                if(logger.isDebugEnabled()){
                logger.debug("My Previous Rashi Name || "+rashiBean.getSignNumber());
                }
                insertStatus=false;
                if(isForword)
                {
                    nextPrevSignNumber =myrashi.ordinal()+1;
                }
                if(isBackword)
                {
                    nextPrevSignNumber =myrashi.ordinal()-1;
                }
                if(nextPrevSignNumber>=0)
                {
                    forBackSignName= myrashi.fromOrdinal(nextPrevSignNumber).toString();
                    if(logger.isDebugEnabled()){
                    logger.debug("My PreviousORNext  Rashi Name || "+forBackSignName);
                    }
                    insertStatus=true;
                }

                if(insertStatus)
                {
                	if(logger.isDebugEnabled()){
                    logger.debug("Checking for forword/backword sign");
                	}
                    Iterator itr=conjHashTable.values().iterator();
                while(itr.hasNext())
                {
                        insertStatus=false;
                        
                        RashiHouseBean objBen=(RashiHouseBean)itr.next();

                        if(objBen.getSignNumber().equals(rashiBean.getSignNumber()))
                        {
                        	if(logger.isDebugEnabled()){
                            logger.debug("Sign conjuction aries at planetName "+objBen.getPlanetName()+" and sign name "+objBen.getSignNumber());
                        	}
                            if(isForword)
                            {
                                if(minusDegree<=Integer.parseInt(objBen.getDegree()))
                                    insertStatus=true;
                                if(logger.isDebugEnabled()){
                                logger.debug("Is Forword flag is true ");
                                }
                            }
                            if(isBackword)
                            {
                                if(plusDegree<=Integer.parseInt(objBen.getDegree()))
                                    insertStatus=true;
                                if(logger.isDebugEnabled()){
                                logger.debug("Is Backword flag is true ");
                                }
                            }
                        }

                         if(insertStatus)
                        {
                            planetList.append(objBen.getPlanetName());
                            planetList.append("_");
                        }
                }

                }
                else
                {
                	if(logger.isDebugEnabled()){
                    logger.debug("There is no any foword/backword planet for this sign "+rashiBean.getSignNumber());
                	}
                }

        }
        
        
        return planetList.toString();

    }

    public void fillConjHashTable(Hashtable conjHashTable) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException {
                // Create a XPathFactory
            String query="/ASTROCHART/PLANETS//@* ";
            XPathFactory xFactory = XPathFactory.newInstance();

		// Create a XPath object
		XPath xpath = xFactory.newXPath();

                // Compile the XPath expression

                expr = xpath.compile(query);

		// Run the query and get a nodeset
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		// Cast the result to a DOM NodeList
		NodeList nodes = (NodeList) result;
		for (int i=0; i<nodes.getLength();){

                       Attr degreeAttr = (Attr) nodes.item(i);
                       Attr nameAttr = (Attr) nodes.item(i+2);
                       Attr signAttr = (Attr) nodes.item(i+7);
                       
                       RashiHouseBean obj=new RashiHouseBean();
                       obj.setDegree(degreeAttr.getValue().substring(0, 2).trim());
                       obj.setPlanetName(nameAttr.getValue().trim());
                       obj.setSignNumber(signAttr.getValue().trim());
                       if(logger.isDebugEnabled()){
                       logger.debug("Conj Hash Table Degree Value ["+degreeAttr.getValue().substring(0, 2)+"] Name Value ["+nameAttr.getValue()+"] Sign Value "+signAttr.getValue());
                       }
                       conjHashTable.put(nameAttr.getValue().trim(), obj);
                       i=i+10;
                }

    }

    public boolean getJupiterVenusMarsEffect(String planetEffect,String planetName,int first,int second,Hashtable birthPlanetHashTable,Hashtable birthHouseHashTable)
    {
        boolean retVal=false;
        int firstHouse,secondHouse;
        if(birthPlanetHashTable.containsKey(planetEffect))
            {

                RashiHouseBean obj=(RashiHouseBean)birthPlanetHashTable.get(planetEffect);
                if(logger.isDebugEnabled()){
               logger.debug("Planet Name Contains "+planetEffect+"House Number "+obj.getHouseNumber());
                }
               //Logic for jump into next  house
                int houseNumber=Integer.parseInt(obj.getHouseNumber());
                firstHouse=houseNumber+first;
                secondHouse=houseNumber+second;
                
                if(firstHouse>12)
                        firstHouse-=12;
                if(secondHouse>12)
                        secondHouse-=12;
                if(logger.isDebugEnabled()){
                logger.debug("My New House Numbers FirstHouse "+firstHouse+" SecondHouse "+secondHouse);
                }
                String houseArray[]={Integer.toString(firstHouse),Integer.toString(secondHouse)};
                for(int loop=0;loop<houseArray.length;loop++)
                {
                    if(birthHouseHashTable.containsKey(houseArray[loop]))
                    {
                    	if(logger.isDebugEnabled()){
                        logger.debug("My hash Table contain house Number for "+houseArray[loop]);
                    	}
                        RashiHouseBean beanObj=(RashiHouseBean)birthHouseHashTable.get(houseArray[loop]);

                          
                          String str[]=beanObj.getPlanetName().split(" ");
                          if(logger.isDebugEnabled()){
                          logger.debug("Planet Name Contains house number "+houseArray[loop]+"Planet Name"+beanObj.getPlanetName()+" Planet Array "+Arrays.toString(str));
                          }
                          for(String planetNm:str)
                          {
                              if(planetName.equalsIgnoreCase(planetNm.trim()))
                              {
                            	  if(logger.isDebugEnabled()){
                                  logger.debug("Effect of "+planetName+" for planet "+planetEffect);
                            	  }
                                  return true;

                              }
                          }

                    }
                    else
                    {
                        retVal=false;
                    }

                }
                
           }
        else
        {
        	if(logger.isDebugEnabled()){
            logger.debug("Hash Table Does Not contain data || for planet  "+planetEffect);
        	}
            retVal=false;
        }
        
        return retVal;
    }
      public String getRahuKetuEffectPlanet(String rahuAndKetu ,Hashtable birthPlanetHashTable,Hashtable birthHouseHashTable)
        {
            
            if(birthPlanetHashTable.containsKey(rahuAndKetu))
            {

                RashiHouseBean obj=(RashiHouseBean)birthPlanetHashTable.get(rahuAndKetu);

//               logger.info("Planet Name Contains "+rahuAndKetu+"House Number "+obj.getHouseNumber());

               // Logic for jump into next 6+ house
                
                int houseNumber=Integer.parseInt(obj.getHouseNumber());

  //              logger.info("My Original HouseNumber for planet "+rahuAndKetu+" house number"+houseNumber);
                
                houseNumber=houseNumber-6;
                if(houseNumber<=0)
                        houseNumber=houseNumber+12; //for negative case

    //            logger.info("After Move 6th position HouseNumber for planet "+rahuAndKetu+" house number"+houseNumber);

                String houseNo=Integer.toString(houseNumber);

                if(birthHouseHashTable.containsKey(houseNo))
                {

      //              logger.info("Birth House Hash Table contain Data for house number "+houseNo);
                    RashiHouseBean objBean=(RashiHouseBean)birthHouseHashTable.get(houseNo);
                    if(logger.isDebugEnabled()){
                    logger.debug("Planet Name for this House Number "+objBean.getPlanetName());
                    }
                    return objBean.getPlanetName();
                    

                }

              }
            else
            {
            	if(logger.isDebugEnabled()){
                logger.debug("Hash Table does not contain this planet "+rahuAndKetu);
            	}
            }
            return "unsuccess";
}


    public String getNLAndSL(String value,String planetName) throws ParserConfigurationException, SAXException,IOException, XPathExpressionException {

		// query="/ASTROCHART/PLANETS/PLANET[@NAME='Mars']/@NL"; //For NL and SL

        String retVal="NA",query="";
       

        if(value.equalsIgnoreCase("NL"))
            query="/ASTROCHART/PLANETS/PLANET[@NAME='"+planetName+"']/@NL";
        else if(value.equalsIgnoreCase("SL"))
            query="/ASTROCHART/PLANETS/PLANET[@NAME='"+planetName+"']/@SL";
        else if(value.equalsIgnoreCase("RL"))
            query="/ASTROCHART/PLANETS/PLANET[@NAME='"+planetName+"']/@RL";
        else if(value.equalsIgnoreCase("MDL"))
            query="/ASTROCHART/DASHAS/DASHA[@NAME='"+planetName+"']/ANTARDASHA/@END[last()]";


        
       // logger.info(" query  Value inside function getNLAndSL  "+query);
         try{
                
		XPathFactory xFactory = XPathFactory.newInstance();
		// Create a XPath object

		XPath xpath = xFactory.newXPath();

                // Compile the XPath expression

                expr = xpath.compile(query);

		// Run the query and get a nodeset
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		// Cast the result to a DOM NodeList
		NodeList nodes = (NodeList) result;

                int loop=0;
                if(value.equalsIgnoreCase("MDL"))  //For Last Node Value
                    loop=nodes.getLength()-1;

                    for (int i=loop; i<nodes.getLength();i++)
                    {


                       Attr houseAttr = (Attr) nodes.item(i);
                       retVal=houseAttr.getValue();

                }
         }catch(Exception e)
         {
             e.printStackTrace();
         }
          //      logger.info("Ret Value from function getNLandSL || "+retVal);
return  retVal;

        }




	public void fillHashTable(String queryFetch,Hashtable planetHashTable,Hashtable signHashTable) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException {
		
                
                // Create a XPathFactory
            String temp=queryFetch;
            String query="";
            if(queryFetch.equalsIgnoreCase("BIRTHCHART"))
                 query="/ASTROCHART/BIRTHCHART/HOUSE//@NAME"; //Load BirthChart Detail
            else
                query="/ASTROCHART/CUSPCHART/HOUSE//@NAME";   //Load CuspChart Detail
		XPathFactory xFactory = XPathFactory.newInstance();
		if(logger.isDebugEnabled()){
                logger.debug("Query For Fetch || "+query);
		}
		// Create a XPath object
		XPath xpath = xFactory.newXPath();

                // Compile the XPath expression
		
                expr = xpath.compile(query);
                
		// Run the query and get a nodeset
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		// Cast the result to a DOM NodeList
		NodeList nodes = (NodeList) result;
		for (int i=0; i<nodes.getLength();){
                    
                
                       String [] planetName;
                       Attr houseAttr = (Attr) nodes.item(i);
                       Attr planetAttr = (Attr) nodes.item(i+1);
                       Attr signAttr = (Attr) nodes.item(i+2);

                // For All case key value planet
                       if(!StringUtils.isBlank(planetAttr.getValue()))
                       {
                          planetName= planetAttr.getValue().split(" ");
                          for(String planet:planetName)
                          {
                              RashiHouseBean obj=new RashiHouseBean();
                              obj.setPlanetName(planet.trim());
                              obj.setHouseNumber(houseAttr.getValue().trim());
                              obj.setSignNumber(signAttr.getValue().trim());
                              planetHashTable.put(planet.trim(),obj);

                            // logger.info(" First Hash Table Planet Value Is not Null  Key "+planet+" House Value "+houseAttr.getValue()+" sign Value "+signAttr.getValue());


                          }
                       }

                       //For House Hash Table house number as a key

                       RashiHouseBean beanObj=new RashiHouseBean();
                       beanObj.setHouseNumber(houseAttr.getValue().trim());
                       beanObj.setSignNumber(signAttr.getValue().trim());

                            if(!StringUtils.isBlank(planetAttr.getValue()))
                            {
                                beanObj.setPlanetName(planetAttr.getValue().trim());
                               // logger.info(" Second Hash Table Planet Value Is not Null  Key "+houseAttr.getValue()+" sign Value "+planetAttr.getValue().trim());
                            }

                    signHashTable.put(houseAttr.getValue().trim(),beanObj);

                  //logger.info(" Sign Value Is  set as Key "+signAttr.getValue()+" House Value "+houseAttr.getValue());                       
                       i=i+3;
                }


        }
        
        public String searchPlanetLocation(String planetName,Hashtable planetHashTable,Hashtable signHashTable,Hashtable staticSignHashTable)
        {
            StringBuffer retObj=new StringBuffer();
            if(planetHashTable.containsKey(planetName))
            {

                RashiHouseBean obj=(RashiHouseBean)planetHashTable.get(planetName);
                retObj.append(obj.getHouseNumber());
                //logger.info("Planet Name Contains "+planetName+"House Number "+obj.getHouseNumber());
                if(staticSignHashTable.containsKey(planetName))
                {

                    String str=(String)staticSignHashTable.get(planetName);
                    String []signStr=str.split("-");
                    retObj.append("/ ");

                   // logger.info("Sign Name || "+signStr.length +"Arrays "+Arrays.toString(signStr));
                    for(String st:signStr)
                    {
                     //   logger.info("Sign Name || "+st);

                        Iterator itr=signHashTable.values().iterator();


                        //if(signHashTable.containsKey(st))
                        while(itr.hasNext())
                        {

                            RashiHouseBean ob=(RashiHouseBean)itr.next();
                            if(ob.getSignNumber().equals(st))
                            {
                                retObj.append(ob.getHouseNumber());
                                retObj.append(",");
                            }
                       //     logger.info("This Table containes data  for sign "+st+"House number || "+ob.getHouseNumber());

                        }
                    }
                    //logger.info("Static hash table containe data for planet  "+planetName+" Result String "+str);
                }

              }
            else
            {
            	if(logger.isDebugEnabled()){
                logger.debug("Hash Table does not contain this planet "+planetName);
            	}
            }

            //logger.info("My Planet Value is return  || "+retObj.toString());
            return retObj.toString();
           // return retObj.toString().substring(0, retObj.toString().length()-1);
        }


	public static void main(String[] args)
        {

            try
            {
                PlanetDetails process = new PlanetDetails();
                 String query="";
                 Hashtable<String,Object> planetHashTable = new Hashtable<String,Object>();
                 Hashtable<String,Object> signHashTable = new Hashtable<String,Object>();
                 Hashtable<String,String> staticSignHashTable = new Hashtable<String,String>();
                 Hashtable<String,Object> birthPlanetHashTable = new Hashtable<String,Object>();
                 Hashtable<String,Object> birthHouseHashTable = new Hashtable<String,Object>();
                 Hashtable<String,Object> conjHashTable = new Hashtable<String,Object>();
             
                 staticSignHashTable.put("Mars","1-8");
                 staticSignHashTable.put("Venus","2-7");
                 staticSignHashTable.put("Mercury","3-6");
                 staticSignHashTable.put("Moon","4");
                 staticSignHashTable.put("Sun","5");
                 staticSignHashTable.put("Jupiter","9-12");
                 staticSignHashTable.put("Saturn","10-11");


                 process.fillConjHashTable(conjHashTable); //Fill Planet Details for conjection of planets.
                 query="BIRTHCHART";
                
               process.fillHashTable(query,birthPlanetHashTable,birthHouseHashTable); //Fill BirtChart Details

                query="CUSPCHART";
                process.fillHashTable(query,planetHashTable,signHashTable); //Fill CuspChart Details

                //logger.info("The elements of Planet Hash Table : " +birthPlanetHashTable+"Sign Hash Table ||"+birthHouseHashTable);

                //String planetName []={"Sun","Mars","Mercury","Moon","Jupiter","Saturn","Ketu","Rahu"};

                String planetName []={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};

              
                for(int loop=0;loop<planetName.length;loop++)
                {
                    // For Source Column

                    String sourceValue=process.searchPlanetLocation(planetName[loop],planetHashTable,signHashTable,staticSignHashTable );

                    // For Result NL column

                    String resultNL=process.getNLAndSL("NL",planetName[loop]);
                    if(!StringUtils.isBlank(resultNL))
                            resultNL+=process.searchPlanetLocation(resultNL.trim(),planetHashTable,signHashTable,staticSignHashTable );

                    // for SL column

                    String resultSL=process.getNLAndSL("SL",planetName[loop]);
                    if(!StringUtils.isBlank(resultSL))
                            resultSL+=process.searchPlanetLocation(resultSL.trim(),planetHashTable,signHashTable,staticSignHashTable );

                    //for NL of SL NL(SL)

                    String resultNLOfSL=process.getNLAndSL("SL",planetName[loop]);
                    if(!StringUtils.isBlank(resultNLOfSL))
                        resultNLOfSL=process.getNLAndSL("NL",resultNLOfSL.trim());

                    if(!StringUtils.isBlank(resultNLOfSL))
                         resultNLOfSL+=process.searchPlanetLocation(resultNLOfSL.trim(),planetHashTable,signHashTable,staticSignHashTable );

                    String MDLValue=process.getNLAndSL("MDL", planetName[loop]);
                    if(logger.isDebugEnabled()){
                    logger.debug(" Planet Name ["+planetName[loop]+"] SourceValue ["+sourceValue+" ]Result(NL) ["+resultNL+"] ResultSL ["+resultSL+"] resultNLOfSL ["+resultNLOfSL+" MDL Value "+MDLValue);
                    }
                }



//For Rahu ketu Effect on planets

                 String rahuAndKetu [] ={"Rahu","Ketu"};

                
                

                for(String str:rahuAndKetu)
                {
                  String  rahuAndKetuData =process.getRahuKetuEffectPlanet(str,birthPlanetHashTable,birthHouseHashTable);
                  if(logger.isDebugEnabled()){
                  logger.debug("Planets Names for "+str+" is == "+rahuAndKetuData);
                  }
                  String atr[]=rahuAndKetuData.split(" ");
                  for(String planetEffec:atr)
                  {
                    if(planetEffec.equalsIgnoreCase("unsuccess")||planetEffec.equalsIgnoreCase("Rahu")||planetEffec.equalsIgnoreCase("Ketu"))
                    {
                    	if(logger.isDebugEnabled()){
                        logger.debug(" Inside unsuccess and Rahu ketu Find Data");
                    	}
                        continue;

                    }
                    else
                    {
                    	if(logger.isDebugEnabled()){
                        logger.debug("Obtain Effect Of this planet "+planetEffec);
                    	}
                        if(!StringUtils.isBlank(planetEffec))
                         planetEffec+=process.searchPlanetLocation(planetEffec.trim(),planetHashTable,signHashTable,staticSignHashTable );


                    }
                    if(logger.isDebugEnabled()){
                    logger.debug("Planet Effect "+planetEffec+"For Planet "+str);
                    }
                  }


  
                }

                // for Aspect Ratio
                String planetEffect [] ={"Jupiter","Venus","Mars"};
               // int rahuKetuEffect[]={5,9,3,10,4,8};

                for(String str:rahuAndKetu)
                {


                //for(int loop=0;loop<planetEffect.length;loop++)
               for(int loop=0;loop<1;loop++)
                {
                    boolean isEffected=false;
                    switch(loop)
                    {
                        //planet House number in sequential order with -1
                        case 0:
                        isEffected=process.getJupiterVenusMarsEffect(planetEffect[loop],str,4,8,birthPlanetHashTable,birthHouseHashTable);
                        break;

                        case 1:
                        isEffected=process.getJupiterVenusMarsEffect(planetEffect[loop],str,2,9,birthPlanetHashTable,birthHouseHashTable);
                        break;

                        case 2:
                        isEffected=process.getJupiterVenusMarsEffect(planetEffect[loop],str,3,7,birthPlanetHashTable,birthHouseHashTable);
                        break;
                    }
                    if(logger.isDebugEnabled()){
                    logger.debug("My Effect Value is "+isEffected +" For planetName "+planetEffect[loop]);
                    }
                }

               //For SignLord and RashiLord
                    String rahuKetuS="",rahuKetuR="";
                    rahuKetuS=process.getNLAndSL("NL",str);
                    if(!StringUtils.isBlank(rahuKetuS))
                            rahuKetuS+=process.searchPlanetLocation(rahuKetuS,planetHashTable,signHashTable,staticSignHashTable );

                    rahuKetuR=process.getNLAndSL("RL",str);
                    if(!StringUtils.isBlank(rahuKetuR))
                            rahuKetuR+=process.searchPlanetLocation(rahuKetuR,planetHashTable,signHashTable,staticSignHashTable );
                    if(logger.isDebugEnabled()){
                    logger.debug("For Planet || "+str+" [S] "+rahuKetuS+" [R] "+rahuKetuR);
                    }
                  // For Conjetion
                    String conjPlanetLists=process.getConjPlanetList(str,conjHashTable);
                    if(logger.isDebugEnabled()){
                    logger.debug("conj Planet List "+conjPlanetLists);
                    }
                    if(!StringUtils.isBlank(conjPlanetLists))
                    {
                        String conj[]=conjPlanetLists.split("_");
                        String rahuKetuConj="";
                        for(String conjPlanet:conj)
                        {
                            rahuKetuConj=conjPlanet;
                            if(logger.isDebugEnabled()){
                            logger.debug("Conj PlanetLists  || "+conjPlanet+" Check For Planet || "+str);
                            }
                            if(!conjPlanet.equalsIgnoreCase(str))
                            {
                                rahuKetuConj+=process.searchPlanetLocation(conjPlanet, planetHashTable, signHashTable, staticSignHashTable);
                                if(logger.isDebugEnabled()){
                                logger.debug(" Rahu Ketu Conjection finally  "+rahuKetuConj);
                                }
                            }

                        }
                          
                    }
                }

                
                }catch(Exception e)
                {
                    e.printStackTrace();
                }

               
                

                

        }
}


