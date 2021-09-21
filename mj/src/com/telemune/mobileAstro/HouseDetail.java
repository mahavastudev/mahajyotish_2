package com.telemune.mobileAstro;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.*;


public class HouseDetail
{
Logger logger = Logger.getLogger(HouseDetail.class); 
String[] sign={"Aries","Taurus","Gemini","Cancer","Leo","Virgo","Libra","Scorpio","Sagittarius","Capricorn","Aquarius","Pisces"};
//String[] planets={"Sun","Moon","Mercury","Venus","Mars","Jupiter","Saturn","Rahu","Ketu"};

ArrayList<String> signList=new ArrayList<String>(Arrays.asList(sign));

int[] angles={0,30,45,60,90,120,135,150,180};

ArrayList<Range> planetList=null;
HashMap<String,HashSet<String>> occupantMap=null;

public void getHouseDetails(AstroBean astrobean)
{

LinkedHashMap<String, HouseDetailBean> HouseDetailHashTable = null;
HouseDetailHashTable = astrobean.getHouseDetailHashTable();

LinkedHashMap<String,PlanetDetailBean> planetHousesHashTable = astrobean.getPlanetDetailHashTable();


HouseDetailBean signbean=null;
HouseDetailBean starbean=null;
HouseDetailBean subLordbean=null;
HouseDetailBean aspectbean=null;



LinkedHashMap<String, ArrayList<HouseDetailBean>> houseOccupantHashTable=new LinkedHashMap<String, ArrayList<HouseDetailBean>>();
LinkedHashMap<String, HouseDetailBean> houseSubLordHashTable=new LinkedHashMap<String, HouseDetailBean>();
LinkedHashMap<String, HouseDetailBean> houseSignDetailHashTable=new LinkedHashMap<String, HouseDetailBean>();

LinkedHashMap<String, HouseDetailBean> houseStarDetailHashTable=new LinkedHashMap<String, HouseDetailBean>();
LinkedHashMap<String, HashSet<String>> houseAspectHashTable=new LinkedHashMap<String, HashSet<String>>();
LinkedHashMap<String, HashMap<String,HashSet<String>>> houseoccAspectHashTable=new LinkedHashMap<String,  HashMap<String,HashSet<String>>>();



LinkedHashMap<String, HashMap<String,String>> planetHouseAspectDetails=new LinkedHashMap<String, HashMap<String,String>>();
LinkedHashMap<String, HashMap<String,String>> cuspHouseAspectDetails=new LinkedHashMap<String, HashMap<String,String>>();

String[][] tabulardata=astrobean.getNatalStrengthChart();
HashSet<String> aspectList= null;
ArrayList<HouseDetailBean> occList=null;

 HashMap<String,String> planetMap=null;
 HashMap<String,String> cuspMap=null;

long degree=0;
String minSec="";





for (int k = 1; k <=12; k++) {


if (HouseDetailHashTable.containsKey(Integer.toString(k))) {

occList=new ArrayList<HouseDetailBean>();

for (Map.Entry<String, KundliHouseBean> entry : astrobean.getCuspPlanetHashTable().entrySet())
{
        if(entry.getValue().getHouseNumber().equals(""+k))
        {
        	if(logger.isDebugEnabled()){
        		logger.debug("Occupant planet    "+entry.getKey());
        	}                
		HouseDetailBean bean= new HouseDetailBean();
		bean.setPlanetName(entry.getKey());
		occList.add(bean);
        }
}
		if(logger.isDebugEnabled()){
			logger.debug(" house cusp >>>>>  "+HouseDetailHashTable.get(Integer.toString(k)).getPlanetName());
		}
		signbean= new HouseDetailBean();
		signbean.setPlanetName(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName());
		signbean.setSS(HouseDetailHashTable.get(Integer.toString(k)).getRL());//signLord
		signbean.setSignName(HouseDetailHashTable.get(Integer.toString(k)).getSignName());
		

		starbean= new HouseDetailBean();
		starbean.setPlanetName(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName());
 //               starbean.setSS(signbean.getNL()); //signLord

		subLordbean = new HouseDetailBean();	
		subLordbean.setPlanetName(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName());		 
		subLordbean.setSS(HouseDetailHashTable.get(Integer.toString(k)).getSL());

	for (int outer = 0; outer < tabulardata.length; outer++) {

			if(tabulardata[outer][0].equalsIgnoreCase(signbean.getSS()))
			{
				signbean.setSS(signbean.getSS()+" "+tabulardata[outer][1]);
				signbean.setNL(tabulardata[outer][2]);
				signbean.setSL(tabulardata[outer][4]);
				signbean.setNLSL(tabulardata[outer][5]);	
			}
		
                        if(tabulardata[outer][0].equalsIgnoreCase(subLordbean.getSS()))
                        {	
				subLordbean.setSS(subLordbean.getSS()+tabulardata[outer][1]);
                                subLordbean.setNL(tabulardata[outer][2]);
                                subLordbean.setSL(tabulardata[outer][4]);
                                subLordbean.setNLSL(tabulardata[outer][5]);
                        }


	for (int i=0;i<occList.size();i++)
	{
			HouseDetailBean bean=occList.get(i);
       
		if(tabulardata[outer][0].equalsIgnoreCase(bean.getPlanetName()))
		{
			bean.setSS(bean.getPlanetName()+" "+tabulardata[outer][1]);
			bean.setNL(tabulardata[outer][2]);
			bean.setSL(tabulardata[outer][4]);
			bean.setNLSL(tabulardata[outer][5]);
			occList.remove(i);
			occList.add(bean);
		
		}
	}
	}
	

		starbean.setSS(HouseDetailHashTable.get(Integer.toString(k)).getNL()); //signLord


	 for (int outer = 0; outer < tabulardata.length; outer++) {

		 if(tabulardata[outer][0].equalsIgnoreCase(starbean.getSS()))
                        {
				starbean.setSS(starbean.getSS()+" "+tabulardata[outer][1]);
                                starbean.setNL(tabulardata[outer][2]);
                                starbean.setSL(tabulardata[outer][4]);
                                starbean.setNLSL(tabulardata[outer][5]);
                        }


	}

	 if(logger.isDebugEnabled()){
		 logger.debug("Sign:::  "+signbean);
		 logger.debug("Star:::  "+starbean);
		 logger.debug("Sublord:::  "+subLordbean);
	 }
		houseSignDetailHashTable.put(signbean.getPlanetName(),signbean);
//		houseStarDetailHashTable.put(starbean.getPlanetName(),starbean);
//		houseSubLordHashTable.put(subLordbean.getPlanetName(),subLordbean);

houseOccupantHashTable.put(signbean.getPlanetName(),occList);






Iterator it=planetHousesHashTable.values().iterator();
                        while(it.hasNext())
                        {
		
		
                 PlanetDetailBean planetDetail=(PlanetDetailBean)it.next();
		for(int i=0;i<occList.size();i++)
		{	
		 HouseDetailBean bean=occList.get(i);

                if(planetDetail.getPlanetName().equalsIgnoreCase(bean.getPlanetName()))
                {
		bean.setSignName(planetDetail.getSignName());
		bean.setDegree(planetDetail.getDegree());
                        occList.remove(i);
                        occList.add(bean);

                }



		}

			String[] tmp= starbean.getSS().split(" ");
			if(tmp[0].equalsIgnoreCase(planetDetail.getPlanetName()))
			{
				starbean.setSS(starbean.getSS()+" "+planetDetail.getSignName()+" "+planetDetail.getDegree());
			}

			 tmp= subLordbean.getSS().split(" ");
                        if(tmp[0].equalsIgnoreCase(planetDetail.getPlanetName()))
                        {
                                subLordbean.setSS(subLordbean.getSS()+" "+planetDetail.getSignName()+" "+planetDetail.getDegree());
                        }
			

			tmp= starbean.getNL().split(" ");
                        if(tmp[0].equalsIgnoreCase(planetDetail.getPlanetName()))
                        {
                                starbean.setNL(starbean.getNL()+" "+planetDetail.getSignName()+" "+planetDetail.getDegree());
                        }

                         tmp= subLordbean.getNL().split(" ");
                        if(tmp[0].equalsIgnoreCase(planetDetail.getPlanetName()))
                        {
                                subLordbean.setNL(subLordbean.getNL()+" "+planetDetail.getSignName()+" "+planetDetail.getDegree());
                        }


}


houseStarDetailHashTable.put(starbean.getPlanetName(),starbean);
                houseSubLordHashTable.put(subLordbean.getPlanetName(),subLordbean);




minSec=HouseDetailHashTable.get(Integer.toString(k)).getDegree();
String[] temp=minSec.split(":");
degree=(Integer.parseInt(temp[0])*60*60)+(Integer.parseInt(temp[1])*60)+Integer.parseInt(temp[2]);
if(logger.isDebugEnabled()){
	logger.debug("degree >>>>>>"+degree);
}
minSec=temp[1]+":"+temp[2];
long indexB=0,indexF=0,deg=0,pdeg=0,pdegree=0;
int n=0;
String element=signbean.getSignName();
PlanetDetailBean planetDetail = null;
long strtF=0,endF=0,strtB=0,endB=0;

Range range=null;

cuspMap =new HashMap<String,String>();
n=signList.indexOf(element);
if(logger.isDebugEnabled()){
logger.debug("Index of "+element+" is : "+n);
}
aspectList= new HashSet<String>();



for(int i=0;i<angles.length;i++)
{

deg=checkRange((n*30*60*60)+degree);
indexB=checkRange(deg-(angles[i]*60*60));
indexF=checkRange(deg+(angles[i]*60*60));
//n=signList.indexOf(signbean.getSignName());
//logger.info("Angle is ["+angles[i]);

//indexF=indexF+n;
//indexB=indexB-n;
planetList=new ArrayList<Range>();
//logger.info("back "+indexB+"  forward  "+indexF);
//while(indexF >= signList.size())
//{
//	indexF=indexF-signList.size();
//}
//while(indexB<0)
//{
//	 indexB=indexB+signList.size();
//}

//logger.info("back "+indexB+"  forward  "+indexF);
if(logger.isDebugEnabled()){
logger.debug(deg+"]  degree after angle add  "+indexF+"  sub  "+indexB);
}
strtF=checkRange(indexF-(5*60*60));
endF=checkRange(indexF+(5*60*60));
strtB=checkRange(indexB-(5*60*60));
endB=checkRange(indexB+(5*60*60));
if(logger.isDebugEnabled()){
logger.debug(strtF+"   "+endF+"   "+strtB+"    "+endB);
}

int rs=(int)(strtF/(30*60*60));
int re=(int)(endF/(30*60*60));
/*logger.info("HERE BHARTI>> rs>> re>>= "+rs+", "+re);
if(rs==re)
{
range=new Range();
range.setStart(strtF-(rs*30*60*60));
range.setEnd(endF-(rs*30*60*60));
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(true);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}

else
{
range=new Range();
range.setStart(strtF-(rs*30*60*60));
range.setEnd(30*60*60);
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(true);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}


range=new Range();
range.setStart(0);
range.setEnd(endF-(re*30*60*60));
if(re!=n)
{
range.setName(signList.get(re));
range.setForward(true);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}

}
logger.info("HERE BHARTI>> F range>> "+range);
*/


 rs=(int)(strtB/(30*60*60));
 re=(int)(endB/(30*60*60));
if(rs==re)
{
range=new Range();
range.setStart(strtB-(rs*30*60*60));
range.setEnd(endB-(rs*30*60*60));
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(false);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}

else
{
range=new Range();
range.setStart(strtB-(rs*30*60*60));
range.setEnd(30*60*60);
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(false);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}

range=new Range();
range.setStart(0);
range.setEnd(endB-(re*30*60*60));
if(re!=n)
{
range.setName(signList.get(re));
range.setForward(false);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}


//logger.info("Planet list for aspect ::::"+planetList);




for (int p=0;p<planetList.size();p++)
{
	it=planetHousesHashTable.values().iterator();
       	while(it.hasNext())
       	{
                planetDetail=(PlanetDetailBean)it.next();
	//	logger.info(" planetDetail=   "+ planetDetail+"   planetList name  "+planetList.get(p).getName());
		if(!planetDetail.getPlanetName().equalsIgnoreCase(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName()) && !planetDetail.getPlanetName().equalsIgnoreCase("Lagna"))
		{
        	if(planetDetail.getSignName().equalsIgnoreCase(planetList.get(p).getName()))
        	{
                minSec=planetDetail.getDegree();
                String[] tmp=minSec.split(":");
                pdegree=(60*60*Integer.parseInt(tmp[0]))+(60*Integer.parseInt(tmp[1]))+Integer.parseInt(tmp[2]);
		String aspect="";
		if(logger.isDebugEnabled()){
		logger.debug("RC  :: "+planetDetail.getRC()  +"  "+ planetList.get(p).isForward()+" "+planetDetail.getPlanetName());
		}
		logger.debug("RC  :: "+planetDetail.getRC()  +"  "+ planetList.get(p).isForward()+" "+planetDetail.getPlanetName());
		//if((planetDetail.getRC().indexOf("R") >= 0 && planetList.get(p).isForward()) || (planetDetail.getRC().indexOf("R") < 0 && !(planetList.get(p).isForward())) || (planetDetail.getRC().indexOf("R") < 0 && planetList.get(p).isForward() && (planetDetail.getPlanetName().equalsIgnoreCase("Rahu") || planetDetail.getPlanetName().equalsIgnoreCase("Ketu"))))
	//BY BHARTI	if((planetDetail.getRC().indexOf("R") >= 0 && planetList.get(p).isForward()) || (planetDetail.getRC().indexOf("R") < 0 && !(planetList.get(p).isForward())) || (planetList.get(p).isForward() && (planetDetail.getPlanetName().equalsIgnoreCase("Rahu") || planetDetail.getPlanetName().equalsIgnoreCase("Ketu"))))
		//if((planetDetail.getRC().indexOf("R") >= 0 && planetList.get(p).isForward()) || (planetDetail.getRC().indexOf("R") < 0 && !(planetList.get(p).isForward())) || (planetDetail.getPlanetName().equalsIgnoreCase("Rahu") && planetList.get(p).isForward()) || (planetDetail.getPlanetName().equalsIgnoreCase("Ketu") && planetList.get(p).isForward()) )
	/*	if((planetDetail.getRC().indexOf("R") >= 0 && planetList.get(p).isForward()) || (planetDetail.getRC().indexOf("R") < 0 && !(planetList.get(p).isForward())) || (planetList.get(p).isForward() && (planetDetail.getPlanetName().equalsIgnoreCase("Rahu") || planetDetail.getPlanetName().equalsIgnoreCase("Ketu"))))
		{*/
                if(pdegree<=(planetList.get(p)).getEnd() && pdegree>=(planetList.get(p)).getStart())
		{
//			aspectbean=new HouseDetailBean();
//			aspectbean.setSignName(minSec);
//			aspectbean.setSS(planetList.get(p).getName());
//			aspectbean.setNL(""+angles[i]);
//			aspectbean.setSL(" house");
					//aspect=minSec+"_"+planetList.get(p).getName()+"_"+angles[i]+planetDetail.getHouseNumber();
	String house=" ";
		if(astrobean.getCuspPlanetHashTable().get(planetDetail.getPlanetName())!=null)
		{
			house=(astrobean.getCuspPlanetHashTable().get(planetDetail.getPlanetName())).getHouseNumber()+" house";
		}
		
					aspect=element+" "+HouseDetailHashTable.get(Integer.toString(k)).getDegree()+"_"+planetDetail.getPlanetName()+"_"+house+"_"+planetDetail.getSignName()+"_"+minSec+"_"+angles[i];
			cuspMap.put(planetDetail.getPlanetName(),angles[i]+"");
		if(logger.isDebugEnabled()){
		logger.debug("cuspppppppp map   "+cuspMap);
		logger.debug("aspect   "+aspect);
		}
		logger.debug("aspect   "+aspect);
		logger.info("cuspppppppp map   "+cuspMap);
			aspectList.add(aspect);
        	}
//	}HEREEE


                }
		}

		}
		

}

planetList=null;

}
cuspHouseAspectDetails.put(k+"",cuspMap);
 houseAspectHashTable.put(signbean.getPlanetName(),aspectList);
 if(logger.isDebugEnabled()){
                logger.debug(">>>>>>>>>>>  " +houseAspectHashTable);
                logger.debug(">>>>>>>>>>>  " +cuspHouseAspectDetails);
 }
                logger.info("cuspHouseAspectDetails>>>>>>>>>>>  " +cuspHouseAspectDetails);






//occupant aspects list

//aspectList = new HashSet<String>();

occupantMap=new HashMap<String,HashSet<String>>();
logger.info("occList.size()>>> HERE BHARTI> = "+occList.size());
for(int o=0;o<occList.size();o++)
{

planetMap =new HashMap<String,String>();
minSec=occList.get(o).getDegree();
temp=minSec.split(":");
degree=(Integer.parseInt(temp[0])*60*60)+(Integer.parseInt(temp[1])*60)+Integer.parseInt(temp[2]);
if(logger.isDebugEnabled()){
logger.debug("degree >>>>>>"+degree);
}
minSec=temp[1]+":"+temp[2];
indexB=0;indexF=0;n=0;deg=0;pdeg=0;pdegree=0;
 element=occList.get(o).getSignName();
planetDetail = null;
strtF=0;endF=0;strtB=0;endB=0;

 range=null;


n=signList.indexOf(element);
if(logger.isDebugEnabled()){
logger.debug("Index of "+element+" is : "+n);
}



for(int i=0;i<angles.length;i++)
{

deg=checkRange((n*30*60*60)+degree);
indexB=checkRange(deg-(angles[i]*60*60));
indexF=checkRange(deg+(angles[i]*60*60));
//n=signList.indexOf(signbean.getSignName());
//logger.info("Angle is ["+angles[i]);

//indexF=indexF+n;
//indexB=indexB-n;
planetList=new ArrayList<Range>();
if(logger.isDebugEnabled()){
logger.debug(deg+"]  degree after angle add  "+indexF+"  sub  "+indexB);
}
strtF=checkRange(indexF-(5*60*60));
endF=checkRange(indexF+(5*60*60));
strtB=checkRange(indexB-(5*60*60));
endB=checkRange(indexB+(5*60*60));
if(logger.isDebugEnabled()){
logger.debug(strtF+"   "+endF+"   "+strtB+"    "+endB);
}

int rs=(int)(strtF/(30*60*60));
int re=(int)(endF/(30*60*60));
/*
if(rs==re)
{
range=new Range();
range.setStart(strtF-(rs*30*60*60));
range.setEnd(endF-(rs*30*60*60));
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(true);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}

else
{
range=new Range();
range.setStart(strtF-(rs*30*60*60));
range.setEnd(30*60*60);
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(true);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}

range=new Range();
range.setStart(0);
range.setEnd(endF-(re*30*60*60));
if(re!=n)
{
range.setName(signList.get(re));
range.setForward(true);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}

*/
//BY BHARTI ON 31/12/2018
 rs=(int)(strtB/(30*60*60));
 re=(int)(endB/(30*60*60));

if(rs==re)
{
range=new Range();
range.setStart(strtB-(rs*30*60*60));
range.setEnd(endB-(rs*30*60*60));
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(false);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}

else
{
range=new Range();
range.setStart(strtB-(rs*30*60*60));
range.setEnd(30*60*60);
if(rs!=n)
{
range.setName(signList.get(rs));
range.setForward(false);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}

range=new Range();
range.setStart(0);
range.setEnd(endB-(re*30*60*60));
if(rs!=n)
{
range.setName(signList.get(re));
range.setForward(false);
if(logger.isDebugEnabled()){
logger.debug("range "+range);
}
planetList.add(range);
}
}
//BY BHARTI ENDS


if(logger.isDebugEnabled()){
logger.debug("Planet list for aspect ::::"+planetList);
}




for (int p=0;p<planetList.size();p++)
{

it=planetHousesHashTable.values().iterator();
                        while(it.hasNext())
                        {

                 planetDetail=(PlanetDetailBean)it.next();

//logger.info(" planetDetail=   "+ planetDetail);
	if(!planetDetail.getPlanetName().equalsIgnoreCase(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName()) && !planetDetail.getPlanetName().equalsIgnoreCase("Lagna"))
	{
        if(planetDetail.getSignName().equalsIgnoreCase(planetList.get(p).getName()))
        {
                minSec=planetDetail.getDegree();
              String[]  tmp=minSec.split(":");
                pdegree=(Integer.parseInt(tmp[0])*60*60)+(Integer.parseInt(tmp[1])*60)+Integer.parseInt(tmp[2]);
            String    aspect="";
            if(logger.isDebugEnabled()){
		logger.debug("RC  :: "+planetDetail.getRC()  +"  "+ planetList.get(p).isForward()+" "+planetDetail.getPlanetName());
            }
		logger.debug("  BY BHARTI> RC  :: "+planetDetail.getRC()  +"  "+ planetList.get(p).isForward()+" "+planetDetail.getPlanetName());
  //              logger.info("RC  :: "+planetDetail.getRC()  +"  "+ planetList.get(p).isForward());
//                if((planetDetail.getRC().equalsIgnoreCase("R") && planetList.get(p).isForward()) || (!planetDetail.getRC().equalsIgnoreCase("R") && !planetList.get(p).isForward()))
		//if((planetDetail.getRC().indexOf("R") >= 0 && planetList.get(p).isForward()) || (planetDetail.getRC().indexOf("R") < 0 && !(planetList.get(p).isForward())) || (planetDetail.getRC().indexOf("R") < 0 && planetList.get(p).isForward() && (planetDetail.getPlanetName().equalsIgnoreCase("Rahu") || planetDetail.getPlanetName().equalsIgnoreCase("Ketu"))))
		


//BY BHARTI ON 31/12/2018


	/*	if((planetDetail.getRC().indexOf("R") >= 0 && planetList.get(p).isForward()) || (planetDetail.getRC().indexOf("R") < 0 && !(planetList.get(p).isForward())) || (planetList.get(p).isForward() && (planetDetail.getPlanetName().equalsIgnoreCase("Rahu") || planetDetail.getPlanetName().equalsIgnoreCase("Ketu"))))
                {*/
                if(pdegree<=(planetList.get(p)).getEnd() && pdegree>=(planetList.get(p)).getStart())
                {
//                      aspectbean=new HouseDetailBean();
//                      aspectbean.setSignName(minSec);
//                      aspectbean.setSS(planetList.get(p).getName());
//                      aspectbean.setNL(""+angles[i]);
//                      aspectbean.setSL(" house");
                                        //aspect=minSec+"_"+planetList.get(p).getName()+"_"+angles[i]+planetDetail.getHouseNumber();
        String house=" ";
                if(astrobean.getCuspPlanetHashTable().get(planetDetail.getPlanetName())!=null)
                {
                        house=(astrobean.getCuspPlanetHashTable().get(planetDetail.getPlanetName())).getHouseNumber()+" house";
                }

//             aspect=planetDetail.getPlanetName()+" "+house+planetDetail.getSignName()+"_"+HouseDetailHashTable.get(Integer.toString(k)).getDegree()+"_"+planetDetail.getPlanetName()+"_"+house+"_"+planetDetail.getSignName()+"_"+minSec+"_"+angles[i]+"_";
//             aspect=planetDetail.getPlanetName()+" "+house+"_"+planetDetail.getSignName()+"_"+minSec+"_"+angles[i];

		if(!occList.get(o).getPlanetName().equalsIgnoreCase(planetDetail.getPlanetName()))
		{
                aspect=occList.get(o).getSignName()+" "+occList.get(o).getDegree()+"_"+planetDetail.getPlanetName()+"_"+house+"_"+planetDetail.getSignName()+"_"+minSec+"_"+angles[i];
                if(logger.isDebugEnabled()){
                logger.debug("aspect   "+aspect);
                }
			planetMap.put(planetDetail.getPlanetName(),angles[i]+"");
                        //aspectList.add(aspect);

		if(occupantMap.get(occList.get(o).getPlanetName())!=null)
		{
			occupantMap.get(occList.get(o).getPlanetName()).add(aspect);
		}
		else
		{
			HashSet<String> aspSet=new HashSet<String>();
			aspSet.add(aspect);
			occupantMap.put(occList.get(o).getPlanetName(),aspSet); 		
		}
		}
        }
       // }//By bharti

       }
	}

                }


}

planetList=null;

}




planetHouseAspectDetails.put(occList.get(o).getPlanetName(),planetMap);

}
//logger.info("RASH     "+astrobean.getCuspHouseHashTable().get(k));
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getSignName());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getDegree());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getNakshatra());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getPadam());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getNL());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getSL());
//	logger.info(HouseDetailHashTable.get(Integer.toString(k)).getSS());


 houseoccAspectHashTable.put(""+k,occupantMap);
 if(logger.isDebugEnabled()){
        logger.debug(">>>>>>>>>>>  " +houseoccAspectHashTable);
 }



}
}


//logger.info("RASH     "+astrobean.getCuspPlanetHashTable());





astrobean.setHouseSignDetailHashTable(houseSignDetailHashTable);
astrobean.setHouseStarDetailHashTable(houseStarDetailHashTable);
astrobean.setHouseSubLordHashTable(houseSubLordHashTable);
astrobean.setHouseAspectHashTable(houseAspectHashTable);
astrobean.setHouseOccAspectHashTable(houseoccAspectHashTable);
astrobean.setHouseOccupantHashTable(houseOccupantHashTable);

astrobean.setCuspHouseAspectDetails(cuspHouseAspectDetails);
astrobean.setPlanetHouseAspectDetails(planetHouseAspectDetails);
}

private long  checkRange(long index)
{

	if(logger.isDebugEnabled()){
logger.debug("inside checkRange index :"+index);
	}
if(index >=(360*60*60))
{
//while(index >=(360*60*60))
	index=index-(360*60*60);
}
if(index<0)
{
//while(index<0)
	index=index+(360*60*60);
}

return index;
}
}
