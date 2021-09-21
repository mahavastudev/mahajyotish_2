/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.telemune.astro;
package com.telemune.mobileAstro;

/**
 *
 * @author SUCCESS
 */
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.telemune.util.Constants;
public class FinalKundli {
	
	
	static Logger logger = Logger.getLogger(FinalKundli.class);

	//***********************************Set The Setter Getter To generate Rectangle******************************//
	float width = 200;
	float height = 100;
	float xCoordinate = 70;
	float yCoordinate = 100;
	float xXCordinate = 330;
	float tableHeadingfont=8;
	float tableDatafont=7;
	float Headingfont=12;
	float  semiHeading=10;
	String tableHeadingColor="RED";
	String tableDataColor="Black";
	String headingColor="RED";
	String tableHeadingBgcolor="LIGHTGRAY";
	String tableDataBgcolor="White";
	byte flag=0;
	String[] planets = {"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};
	String[] cuspName = {"You","Possessions","Presentation","Foundation","Joy","Sell","Buy","Pain","Dharma","Karma","Profits","Expenses"};
	String[] planetsWithLagna = {"Lagna","Moon","Mars","Rahu","Jupiter","Saturn","Mercury","Ketu","Venus","Sun"};
	String[]  addHouse;//to fill two-D Array
	
	String[] aspectLifeContextsign = { "Ar#Aries", "Ta#Taurus", "Ge#Gemini", "Cn#Cancer", "Le#Leo", "Vi#Virgo",
			"Li#Libra", "Sc#Scorpio", "Sg#Sagittarius", "Cp#Capricorn", "Aq#Aquarius", "Pi#Pisces" };
	String[] romanNumber = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" };
	
	Font boldFont =  new Font(FontFamily.HELVETICA, this.getTableDatafont()+1, Font.BOLD);
	Font normalFont = new Font(FontFamily.HELVETICA, this.getTableDatafont(),Font.NORMAL);
	String[] mon = { "Jan","Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
	
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public float getxXCordinate() {
		return xXCordinate;
	}

	public void setxXCordinate(float xXCordinate) {
		this.xXCordinate = xXCordinate;
	}

	public float getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public float getHeadingfont() {
		return Headingfont;
	}

	public void setHeadingfont(float Headingfont) {
		this.Headingfont = Headingfont;
	}

	public float getTableDatafont() {
		return tableDatafont;
	}

	public void setTableDatafont(float tableDatafonr) {
		this.tableDatafont = tableDatafonr;
	}

	public String getHeadingColor() {
		return headingColor;
	}

	public void setHeadingColor(String headingColor) {
		this.headingColor = headingColor;
	}

	public String getTableDataColor() {
		return tableDataColor;
	}

	public void setTableDataColor(String tableDataColor) {
		this.tableDataColor = tableDataColor;
	}

	public String getTableHeadingColor() {
		return tableHeadingColor;
	}

	public void setTableHeadingColor(String tableHeadingColor) {
		this.tableHeadingColor = tableHeadingColor;
	}

	public float getTableHeadingfont() {
		return tableHeadingfont;
	}

	public void setTableHeadingfont(float tableHeadingfont) {
		this.tableHeadingfont = tableHeadingfont;
	}

	public float getSemiHeading() {
		return semiHeading;
	}

	public void setSemiHeading(float semiHeading) {
		this.semiHeading = semiHeading;
	}

	public String getTableHeadingBgcolor() {
		return tableHeadingBgcolor;
	}

	public void setTableHeadingBgcolor(String tableHeadingBgcolor) {
		this.tableHeadingBgcolor = tableHeadingBgcolor;
	}

	public String getTableDataBgcolor() {
		return tableDataBgcolor;
	}

	public void setTableDataBgcolor(String tableDataBgcolor) {
		this.tableDataBgcolor = tableDataBgcolor;
	}









	public float toXYCord(float y) {
		return (float) (842 - y);
	}
	Document document = new Document(PageSize.A4, 2, 2, 2, 2);
	PdfWriter writer = null;
	Map<String, String> planetOcuupant = new HashMap();
	public void Kundli(AstroBean astrobean,AstroBean transitAstrobean ,String FileName,boolean dasha,boolean houseDetail ,boolean circle,String circleImage, String listImage,boolean aspectChartWidoutHouse,boolean nakshatraPadam,boolean cuspName,boolean aspectScore, String transitCircleImage,int isTransit,HashMap<Integer, String> transitHouseDetailHashTable) 
	{
		
    

		try {
			String proPath = Constants.PROPERTIES_PATH;
			//proPath = proPath + "/kundliHttpserver_IP.properties";
			proPath = proPath + "/kundliHttpserverNew.properties";
			Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);			
			logger.info(" dasha ["+dasha+"] houseDetail ["+houseDetail+"] circle ["+circle+"] aspectChartWidoutHouse ["+aspectChartWidoutHouse+"] nakshatraPadam ["+nakshatraPadam+"] cuspName ["+cuspName+"] aspectScore ["+aspectScore+"]");
			ColorElement mycolor=new ColorElement();
			writer = PdfWriter.getInstance(document, new FileOutputStream(FileName));
			document.open();
			document.addTitle("MahaDasha");           		 
			logger.info("transitCircleImage>> "+transitCircleImage+" circleImage= "+circleImage+ "  circle= "+circle+" isTransit= "+isTransit+" transitHouseDetailHashTable> "+transitHouseDetailHashTable.size()+"  :: "+transitHouseDetailHashTable);
			
			
			/**
			 * 
			 * 
			 */
			PdfContentByte canvas = writer.getDirectContent();
			Font fontHead1=null;
			Kundli birthChartDetail=null;
			Kundli cuspChartDetail=null;
			String[][] tabulardata=null;
			String[][] tabulardata_1=null;
			Vector<DashaStrength> obj1=null;
			DashaStrength dashaBean_1 = null;
			PdfPTable Planettable1=null;
			PdfPTable rahuKetutable=null;
			PdfPTable bhavaTable=null;
			PdfPTable krishanTable =null;
			Font fontHead =null;
			fontHead1 = null;
			PdfPTable PersonalTable=null;
			Font fontKrishna=null;
			
			if((dasha==false && houseDetail==false)) {
				/** code comment BY gaurav Sharma to hide first page of Normal kundli 12 Aug 2020 , Requirement by BD Sir */
				/*FinalKundliSpecialPdf finalKundliSpecialPdf=new FinalKundliSpecialPdf();
				canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);
				canvas.fillStroke();
				canvas.closePath();
				fontHead = new Font();
				fontHead.setSize(finalKundliSpecialPdf.getTableHeadingfont());
				fontHead.setColor(mycolor.fillColor(finalKundliSpecialPdf.getTableHeadingColor()));
				fontHead1 = new Font();
				fontHead1.setSize(finalKundliSpecialPdf.getHeadingfont());
				fontHead1.setColor(mycolor.fillColor(finalKundliSpecialPdf.getTableDataColor()));
	
				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
						new Phrase(properties.getOrDefault("PDF_HEADER", "MahaJyotish"), fontHead1), 290, 842 - 40, 0);
	
				birthChartDetail = astrobean.getBirthKundli();
				cuspChartDetail = astrobean.getCuspKundli();
				canvas.setLineWidth(1f);
				canvas.setLineWidth(1f);
				FinalKundliSpecialPdf.drawRectangle(canvas, this.xCoordinate, finalKundliSpecialPdf.toXYCord(this.yCoordinate) - this.height, this.width, this.height);// Generate
				FinalKundliSpecialPdf.drawLine(canvas, this.xCoordinate, finalKundliSpecialPdf.toXYCord(this.yCoordinate), width, height);
				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase("Cusp Kundli", fontHead), 160, 842 - 95,
						0);
				finalKundliSpecialPdf.fillHouse(cuspChartDetail, canvas, this.xCoordinate, finalKundliSpecialPdf.toXYCord(this.yCoordinate), this.width, this.height,
						mycolor);
				canvas.stroke();
				PersonalTable = finalKundliSpecialPdf.fillPersonaldetail(astrobean, mycolor);
				PersonalTable.setTotalWidth(510);
				PersonalTable.writeSelectedRows(0, -1, 50, (842 - 60), writer.getDirectContent());
				tabulardata = new String[9][7];
				tabulardata_1 = new String[9][7];
				tabulardata = astrobean.getNatalStrengthChart();
				addHouse = new String[9];
				obj1 = astrobean.getDashaStrength();
				
				if (obj1.size() != 0) {
					for (int j = 0; j < obj1.size(); j++) {
						dashaBean_1 = (DashaStrength) obj1.get(j);
						if (dashaBean_1.getCurrentFlag()) {
							tabulardata_1 = dashaBean_1.getNatalStrengthChart();
							break;
						}
					}
				}
				fontKrishna = new Font();
				fontKrishna.setSize(finalKundliSpecialPdf.getTableHeadingfont());
				fontKrishna.setColor(mycolor.fillColor(finalKundliSpecialPdf.getTableDataColor()));
				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase("Time Line", fontKrishna), 425,
						842 - 95, 0);
				Planettable1 = finalKundliSpecialPdf.fillPanetTable_first(tabulardata, mycolor, flag, addHouse, tabulardata_1);
				logger.info("planetOcuupant " + planetOcuupant.toString());
				Planettable1.setTotalWidth(494 / 2);
				Planettable1.writeSelectedRows(0, -1, 305, (842 - 98), writer.getDirectContent());
				rahuKetutable = finalKundliSpecialPdf.fillAspecttable(astrobean, mycolor);
				rahuKetutable.setTotalWidth(510 / 2);
				rahuKetutable.writeSelectedRows(0, -1, 305, (842 - 235), writer.getDirectContent());
				bhavaTable = finalKundliSpecialPdf.fillBhavaTable(astrobean, mycolor, nakshatraPadam, cuspName);
				krishanTable = finalKundliSpecialPdf.krishanKundliTable(astrobean, mycolor, nakshatraPadam);
				List<AstroLifeContexts> astroLifeContextList = finalKundliSpecialPdf.collectAstroLifeContextTableData(astrobean,properties);
				PdfPTable astroLifeTable = finalKundliSpecialPdf.createPDFAstroLife(astroLifeContextList,mycolor,properties);
				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase("Astro Script", fontKrishna), 290,
						842 - 310, 0);
				astroLifeTable.setTotalWidth(510);
				astroLifeTable.writeSelectedRows(0, -1, 50, (842 - 316), writer.getDirectContent());
				
				Font fontFooter = new Font();
				fontFooter.setSize(10);
				fontFooter.setColor(mycolor.fillColor(getTableDataColor()));

				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
						new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
						842 - 800, 0);
				
				ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
						new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
						842 - 810, 0);
				
				canvas.stroke();*/
			}
			/**
			 * 
			 * 
			 * 
			 */

			this.document.newPage();
			//*************************************To Draw Outer Rectangle***********************************************//
			//canvas = writer.getDirectContent();
			// bharti
			//canvas.saveState();
			canvas.setLineWidth(3);
			canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);

            canvas.rectangle(35, toXYCord(10) - 765, 540, 760);
			canvas.fillStroke();
			canvas.closePath();
	//by bharti		canvas.restoreState();
			fontHead=new Font();
			fontHead.setSize(getTableHeadingfont());
			fontHead.setColor(mycolor.fillColor(getTableHeadingColor()));
			fontHead1=new Font();
			fontHead1.setSize(getHeadingfont());
			fontHead1.setColor(mycolor.fillColor(getTableDataColor()));

			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Natal Strength" , fontHead1), 290, 842 - 40, 0);

			birthChartDetail = astrobean.getBirthKundli();
			cuspChartDetail = astrobean.getCuspKundli();
			
		//	logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+birthChartDetail+" >>>>>>>>>>>  "+cuspChartDetail);
			//******************************To draw rectangle and fill data in them**************************************//
			canvas.setLineWidth(1f);
		//bharti	canvas.saveState();
			canvas.setLineWidth(1f);
		//bharti	canvas.saveState();
			drawRectangle(canvas, this.xCoordinate, toXYCord(this.yCoordinate) - this.height, this.width, this.height);//Generate Birth Kundli
			drawLine(canvas, this.xCoordinate, toXYCord(this.yCoordinate), this.width, this.height);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Birth Kundli" , fontHead), 160, 842 - 98, 0);
			drawRectangle(canvas, this.xXCordinate, toXYCord(this.yCoordinate) - this.height, this.width, this.height);//Generate Cusp Kundli
			drawLine(canvas, this.xXCordinate, toXYCord(this.yCoordinate), width, height);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Cusp Kundli" , fontHead), 410, 842 - 98, 0);

			fillHouse(birthChartDetail, canvas, this.xCoordinate, toXYCord(this.yCoordinate), this.width, this.height,mycolor);
			fillHouse(cuspChartDetail, canvas, this.xXCordinate, toXYCord(this.yCoordinate), this.width, this.height,mycolor);

			canvas.stroke();
			canvas.stroke();

			//**************************************To Fill Personal detail on first page***************************************//

			PersonalTable = fillPersonaldetail(astrobean,mycolor);
			PersonalTable.setTotalWidth(510);
			PersonalTable.writeSelectedRows(0, -1, 50, (842 - 60), writer.getDirectContent());


			//*****************************************To Fill Planet Detail On first page***************************************//
			/*String[][] tabulardata = new String[9][7];
			String[][] tabulardata_1 = new String[9][7];
			*/
			tabulardata = astrobean.getNatalStrengthChart();
//			
//			for(int i=0;i<9;i++)
//			{
//				tabulardata[i][6]=tabulardata[i][6].split(" ")[0];
//			}
			
               addHouse=new String[9];
               obj1 = astrobean.getDashaStrength();
			    dashaBean_1=null;
				     if (obj1.size() != 0) {
					     for (int j = 0; j < obj1.size(); j++) {



						     dashaBean_1 = (DashaStrength) obj1.get(j);
						     if (dashaBean_1.getCurrentFlag())
						     {
                                 tabulardata_1= dashaBean_1.getNatalStrengthChart();
							     break;
						     }
					     }
				     }
             
				   
			///add Heading here
			fontKrishna=new Font();
            fontKrishna.setSize(getSemiHeading());
            fontKrishna.setColor(mycolor.fillColor(getTableDataColor()));
 
		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("MahaJyotish Script" , fontKrishna), 290, 842 - 226, 0);

			Planettable1 = fillPanetTable_first(tabulardata,mycolor,flag,addHouse,tabulardata_1);
			Planettable1.setTotalWidth(510);
			Planettable1.writeSelectedRows(0, -1, 50, (842 - 235), writer.getDirectContent());

			//****************************To Fill Rahu Ketu Aspect On first Page******************************************//

			rahuKetutable = fillAspecttable(astrobean,mycolor);
			rahuKetutable.setTotalWidth(510);
			rahuKetutable.writeSelectedRows(0, -1, 50, (842 - 365), writer.getDirectContent());

			//******************************************To Fill Krishan Paddti on first page*************************************//


		//	Font fontKrishna=new Font();
		//	fontKrishna.setSize(getSemiHeading());
		//	fontKrishna.setColor(mycolor.fillColor(getTableDataColor()));
			krishanTable = krishanKundliTable(astrobean,mycolor,nakshatraPadam);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Planet Details" , fontKrishna), 280, 842 - 450, 0);
			krishanTable.setTotalWidth(510);
			krishanTable.writeSelectedRows(0, -1, 50, (842 - 461), writer.getDirectContent());


			//*************************************To fill Bhava Detail on first page*********************************************//
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Cusp Details" , fontKrishna), 290, 842 - 605, 0);
			bhavaTable = fillBhavaTable(astrobean,mycolor,nakshatraPadam,cuspName);
			bhavaTable.setTotalWidth(510);
			bhavaTable.writeSelectedRows(0, -1, 50, (842 - 621), writer.getDirectContent());

			Font fontFooter = new Font();
			fontFooter.setSize(10);
			fontFooter.setColor(mycolor.fillColor(getTableDataColor()));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
					842 - 790, 0);
			
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
					842 - 800, 0);

			
			//************************************To call The function of Next page**********************************************//
			if(dasha)
			{
				DashaStrength(canvas, astrobean,mycolor);
			}
			
			mahaDashaAndSooksham(canvas, canvas, astrobean,mycolor);

	//		houseDetailings(canvas,astrobean,mycolor);

			if(houseDetail || aspectChartWidoutHouse)
			{
				houseDetailing(canvas,astrobean,mycolor,writer,houseDetail,aspectChartWidoutHouse,aspectScore);
			}

			if(circle)
			{
			this.document.newPage();
		//bharti	canvas.saveState();	
		//by bharti	canvas.restoreState();
		


			Font font2 = new Font();
                        font2.setSize(getTableHeadingfont());
                        font2.setColor(mycolor.fillColor(getTableHeadingColor()));
                        font2.setSize(12);
             logger.info("cicleImage "+circleImage);
			Image image = Image.getInstance(circleImage);
			image.scaleAbsolute(595f,595f);
			image.setAbsolutePosition(0f,120f);
		       	canvas.addImage(image);
			

         		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
         				new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
         				842 - 790, 0);
         		
         		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
         				new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
         				842 - 800, 0);

			}
//=================TRANSIT KUNDLI START====================>>
			if(isTransit==1){	
				this.document.newPage();
                                canvas.setLineWidth(3);
                                canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);

                                canvas.rectangle(35, toXYCord(10) - 765, 540, 760);
                                canvas.fillStroke();
                                canvas.closePath();
				/*by bharti      canvas.restoreState();*/
                                fontHead=new Font();
                                fontHead.setSize(getTableHeadingfont());
                                fontHead.setColor(mycolor.fillColor(getTableHeadingColor()));
                                fontHead1=new Font();
                                fontHead1.setSize(getHeadingfont());
                                fontHead1.setColor(mycolor.fillColor(getTableDataColor()));

                                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("Natal Strength(Transit)" , fontHead1), 290, 842 - 40, 0);

                                Kundli transitBirthChartDetail = transitAstrobean.getBirthKundli();
                                Kundli transitCuspChartDetail = transitAstrobean.getCuspKundli();
			//	logger.info("transitCuspChartDetail>> "+transitCuspChartDetail);
                                canvas.setLineWidth(1f);
                        /*bharti        canvas.saveState();*/
                                canvas.setLineWidth(1f);
                       /*bharti         canvas.saveState();*/
                                drawRectangle(canvas, this.xCoordinate, toXYCord(this.yCoordinate) - this.height, this.width, this.height);/*Generate Birth Kundli*/
                                drawLine(canvas, this.xCoordinate, toXYCord(this.yCoordinate), this.width, this.height);
                                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("Birth Kundli" , fontHead), 160, 842 - 98, 0);
                                drawRectangle(canvas, this.xXCordinate, toXYCord(this.yCoordinate) - this.height, this.width, this.height);/*Generate Cusp Kundli*/
                                drawLine(canvas, this.xXCordinate, toXYCord(this.yCoordinate), width, height);
                                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("Cusp Kundli" , fontHead), 410, 842 - 98, 0);

                                fillHouse(transitBirthChartDetail, canvas, this.xCoordinate, toXYCord(this.yCoordinate), this.width, this.height,mycolor);
								//CHANGE HERE
								calculateTransitCusp(astrobean,transitAstrobean,transitHouseDetailHashTable,transitCuspChartDetail);
								//ENDS
                                fillHouse(transitCuspChartDetail, canvas, this.xXCordinate, toXYCord(this.yCoordinate), this.width, this.height,mycolor);

                                canvas.stroke();
                                canvas.stroke();

                                PersonalTable = fillTransistPersonalDetail(transitAstrobean,mycolor);
                                PersonalTable.setTotalWidth(510);
                                PersonalTable.writeSelectedRows(0, -1, 50, (842 - 60), writer.getDirectContent());
                                tabulardata = new String[9][7];
                                tabulardata_1 = new String[9][7];
                                tabulardata = transitAstrobean.getNatalStrengthChart();

				addHouse=new String[9];
                                obj1 = transitAstrobean.getDashaStrength();
                                dashaBean_1=null;
                                if (obj1.size() != 0) {
                                        for (int j = 0; j < obj1.size(); j++)
                                        {
                                                dashaBean_1 = (DashaStrength) obj1.get(j);
                                                if (dashaBean_1.getCurrentFlag()) {
                                                        tabulardata_1= dashaBean_1.getNatalStrengthChart();
                                                        break;
                                                }
                                        }
                                }

                                fontKrishna=new Font();
                                fontKrishna.setSize(getSemiHeading());
                                fontKrishna.setColor(mycolor.fillColor(getTableDataColor()));

                                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("MahaJyotish Script" , fontKrishna), 290, 842 - 226, 0);

                                PdfPTable Planettable2 = fillPanetTable_first(tabulardata,mycolor,flag,addHouse,tabulardata_1);
                                Planettable2.setTotalWidth(510);
                                Planettable2.writeSelectedRows(0, -1, 50, (842 - 235), writer.getDirectContent());

                                PdfPTable transitRahuKetutable = fillAspecttable(transitAstrobean,mycolor);
                                transitRahuKetutable.setTotalWidth(510);
                                transitRahuKetutable.writeSelectedRows(0, -1, 50, (842 - 365), writer.getDirectContent());


                                PdfPTable transitKrishanTable = krishanKundliTable(transitAstrobean,mycolor,nakshatraPadam);
                                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("Planet Details" , fontKrishna), 280, 842 - 450, 0);
                                transitKrishanTable.setTotalWidth(510);
                                transitKrishanTable.writeSelectedRows(0, -1, 50, (842 - 461), writer.getDirectContent());

                                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                        new Phrase("Cusp Details" , fontKrishna), 290, 842 - 605, 0);
                                PdfPTable transitBhavaTable = fillBhavaTable(transitAstrobean,mycolor,nakshatraPadam,cuspName);
                                transitBhavaTable.setTotalWidth(510);
                                transitBhavaTable.writeSelectedRows(0, -1, 50, (842 - 621), writer.getDirectContent());	
		
                    			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
                    					842 - 790, 0);
                    			
                    			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
                    					842 - 800, 0);

                                
				if(circle)
                		{
					this.document.newPage();
                                	Image image = Image.getInstance(transitCircleImage);
                                	image.scaleAbsolute(595f,595f);
                                	image.setAbsolutePosition(0f,120f);
                                	canvas.addImage(image);

                        			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                        					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
                        					842 - 790, 0);
                        			
                        			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                        					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
                        					842 - 800, 0);

				}
			}
//+++++++++++++++++TRANSIT KUNDLI END++++++++++++++++++++++>>


			document.close();


		}//try 
		catch (Exception e) {
			e.printStackTrace();
		}
	}//kundli


 //*******************************To Draw Next Page of One Rectangle********************************************//
        public void houseDetailing(PdfContentByte canvas, AstroBean astrobean,ColorElement mycolor, PdfWriter writer ,boolean houseDetail,boolean aspectChartWidoutHouse,boolean aspectScore) {
	 float width1 = 220;
                float height1 = 110;    //old110
                float xCoordinate1 = 80;
                float yCoordinate1 = 160;   //old 160
	//	logger.info("aspectChartWidoutHouse>> "+aspectChartWidoutHouse+"  houseDetail>> "+houseDetail+"     aspectScore>> "+aspectScore);
		 try {
			LinkedHashMap<String,HouseDetailBean> signList =  astrobean.getHouseSignDetailHashTable();
			LinkedHashMap<String,HouseDetailBean> starList =  astrobean.getHouseStarDetailHashTable();
			LinkedHashMap<String,HouseDetailBean> subLordList =  astrobean.getHouseSubLordHashTable();
			LinkedHashMap<String,HashSet<String>> aspectList =  astrobean.getHouseAspectHashTable();
			LinkedHashMap<String,  HashMap<String,HashSet<String>>> occAspList =  astrobean.getHouseOccAspectHashTable();
			LinkedHashMap<String,ArrayList<HouseDetailBean>> occupantList =  astrobean.getHouseOccupantHashTable();
	 		LinkedHashMap<String,  HashMap<String,String>> cuspHouseAspectDetails= astrobean.getCuspHouseAspectDetails();
			LinkedHashMap<String,  HashMap<String,String>> planetHouseAspectDetails= astrobean.getPlanetHouseAspectDetails();
			//By Bharti (version 4.4)
			HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
			if(aspectScore)
				fillAspectScoringMap(scoreMap);
			//ENDS(version 4.4)
			Font font2 = new Font();
			Font font1 = new Font();
			PdfPTable table=null;
                	Font font=new Font();
			Font fontData=new Font();
	//	float width[]=null;
			if(houseDetail || aspectChartWidoutHouse){
		 		this.document.newPage();
                               //bharti  canvas.saveState();
                                 canvas.setLineWidth(3);
                                 canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);

                                        canvas.rectangle(35, 842 - 750, 530, 690);
                                        canvas.fillStroke();
                                        canvas.closePath();
                                     //by bharti   canvas.restoreState();

                                        canvas.setLineWidth(1f);
			//bharti	 canvas.saveState();

                                       // Font font1 = new Font();
                                        font1.setSize(getHeadingfont());
					font1.setColor(mycolor.fillColor(this.getTableDataColor()));

                                   //     Font font2 = new Font();
                                        font2.setSize(getTableHeadingfont());
                                        font2.setColor(mycolor.fillColor(getTableHeadingColor()));
					font2.setSize(12);

		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                                        new Phrase(astrobean.getName().replaceAll("%20"," " ).replaceAll("\"",""), font2), 35, 842 - 55, 0);

                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,new Phrase("Aspects on Houses", font1), 290, 842 - 90, 0);
                // PdfPTable table=new PdfPTable(13);
                 table=new PdfPTable(13);
                // Font font=new Font();
                      //  Font fontData=new Font();
                        float width[]={30,10,10,10,10,10,10,10,10,10,10,10,10};
                        //width[]={30,10,10,10,10,10,10,10,10,10,10,10,10};
                        table.setWidths(width);
		font.setSize(this.getTableHeadingfont());
                        font.setColor(mycolor.fillColor(this.getTableHeadingColor()));
                        fontData.setSize(this.getTableDatafont());
                        fontData.setColor(mycolor.fillColor(this.getTableDataColor()));
		 table.addCell(new Phrase(new Chunk("Planets",font)));

for(int i=1;i<=12;i++)
{
	table.addCell(new Phrase(new Chunk(""+i,font)));
}
HashMap<String,String> cuspMap= null;
for(int j=0;j<planets.length;j++)
{
	if(!planets[j].equals("Ketu") && !planets[j].equals("Rahu")){
		table.addCell(new Phrase(new Chunk(planets[j],font)));
		for(int count=1;count<=12;count++)
		{
			cuspMap=cuspHouseAspectDetails.get(count+"");
			if(cuspMap.get(planets[j])!=null){
				if(aspectScore){
					if(scoreMap.get(cuspMap.get(planets[j]))!=null){
						if(scoreMap.get(cuspMap.get(planets[j]))>0)
							table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j])+"(+"+scoreMap.get(cuspMap.get(planets[j]))+")",fontData)));
						else
							table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j])+"("+scoreMap.get(cuspMap.get(planets[j]))+")",fontData)));	
					}
					else{
						table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j]),fontData)));
					}
				}
				else{
					table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j]),fontData)));
				}
			}
			else{
				table.addCell(new Phrase(new Chunk("",fontData)));
			}
		}
	}
}
//version 4.4
	int totalScore=0;
if(aspectScore){
	table.addCell(new Phrase(new Chunk("",fontData)));	
	for(int count=1;count<=12;count++)
     	{
		for (Map.Entry<String, String> entry : cuspHouseAspectDetails.get(count+"").entrySet())
		{
			if(!entry.getKey().equalsIgnoreCase("Ketu") && !entry.getKey().equalsIgnoreCase("Rahu")) {
				totalScore=totalScore+scoreMap.get(entry.getValue());
			}
		}
		if(totalScore>0)
               		table.addCell(new Phrase(new Chunk("+"+totalScore,fontData)));
            	else if(totalScore<0) 
                	table.addCell(new Phrase(new Chunk(""+totalScore,fontData)));
		else
			table.addCell(new Phrase(new Chunk("N",fontData)));
		totalScore=0;
	}
}
//ENDS HERE	
	table.setTotalWidth(510);
       	table.writeSelectedRows(0, -1, 50, (842 - 100), writer.getDirectContent());







                table=new PdfPTable(10);
                float   wi[]={30,10,10,10,10,10,10,10,10,10};
                        table.setWidths(wi);
                        font.setSize(this.getTableHeadingfont());
                        font.setColor(mycolor.fillColor(this.getTableHeadingColor()));
                        fontData.setSize(this.getTableDatafont());
                        fontData.setColor(mycolor.fillColor(this.getTableDataColor()));
                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                                                new Phrase("Aspects on planets", font1), 290, 842 - 290, 0);
                 table.addCell(new Phrase(new Chunk("Planets",font)));
                 

         		String proPath = Constants.PROPERTIES_PATH;
         		proPath = proPath + "/kundliHttpserverNew.properties";
         		Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);			

         		Font fontFooter = new Font();
         		fontFooter.setSize(10);
         		fontFooter.setColor(mycolor.fillColor(getTableDataColor()));
         		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
         				new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
         				842 - 790, 0);
         		
         		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
         				new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
         				842 - 800, 0);


 		for(int i=0;i<planets.length;i++)
                {
 			table.addCell(new Phrase(new Chunk(planets[i],font)));
		}
		cuspMap= null;
		for(int j=0;j<planets.length;j++)
		{

			if(!planets[j].equals("Ketu") && !planets[j].equals("Rahu")){
				table.addCell(new Phrase(new Chunk(planets[j],font)));
				for(int count=0;count<planets.length;count++)
				{
					cuspMap=planetHouseAspectDetails.get(planets[count]);
					if(cuspMap.get(planets[j])!=null){
						if(aspectScore){
							if(scoreMap.get(cuspMap.get(planets[j]))!=null){
                                       	 			if(scoreMap.get(cuspMap.get(planets[j]))>0)
        								table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j])+"(+"+scoreMap.get(cuspMap.get(planets[j]))+")",fontData)));
								else
									table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j])+"("+scoreMap.get(cuspMap.get(planets[j]))+")",fontData)));
							}
							else{
								table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j]),fontData)));
							}
						}
						else{
							table.addCell(new Phrase(new Chunk(cuspMap.get(planets[j]),fontData)));
						}
					}
					else{
        					table.addCell(new Phrase(new Chunk("",fontData)));
					}
				}
			}
		}
//version 4.4 
if(aspectScore){
	totalScore=0;
        table.addCell(new Phrase(new Chunk("",fontData)));
        for(int count=0;count<planets.length;count++)
        {
                for (Map.Entry<String, String> entry : planetHouseAspectDetails.get(planets[count]).entrySet())
                {
			if(!entry.getKey().equalsIgnoreCase("Ketu") && !entry.getKey().equalsIgnoreCase("Rahu")) {
                        	totalScore=totalScore+scoreMap.get(entry.getValue());
			}
                }
		if(totalScore>0)
                	table.addCell(new Phrase(new Chunk("+"+totalScore,fontData)));
               	else if(totalScore<0)
                	table.addCell(new Phrase(new Chunk(""+totalScore,fontData)));
		else
                        table.addCell(new Phrase(new Chunk("N",fontData)));
                totalScore=0;
        }
}
//ENDS
 table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 300), writer.getDirectContent());


//by bharti canvas.restoreState();
}//end of aspect/house checking

		if(houseDetail){
		for(int i=1;i<=12;i++)
		{
                		this.document.newPage();
			 //by bharti	 canvas.saveState();
                                        canvas.setLineWidth(3);
                                        canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);

                                        canvas.rectangle(35, 842 - 750, 530, 690);
                                        canvas.fillStroke();
                                        canvas.closePath();
                                        //by bharti canvas.restoreState();

                                        canvas.setLineWidth(1f);
                                        
                            			String proPath = Constants.PROPERTIES_PATH;
                            			proPath = proPath + "/kundliHttpserverNew.properties";
                            			Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);			
                            			
                            			Font fontFooter = new Font();
                            			fontFooter.setSize(10);
                            			fontFooter.setColor(mycolor.fillColor(getTableDataColor()));
                            			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                            					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
                            					842 - 790, 0);
                            			
                            			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                            					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
                            					842 - 800, 0);

                                        //bharti canvas.saveState();

                                   //     font1 = new Font();
                                    //    font1.setSize(getHeadingfont());

                                     //   font2 = new Font();
                                     //   font2.setSize(getTableHeadingfont());
                                      //  font2.setColor(mycolor.fillColor(getTableHeadingColor()));
 ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                                        new Phrase(astrobean.getName().replaceAll("%20"," " ).replaceAll("\"",""), font2), 35, 842 - 55, 0);

		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                                                new Phrase((i)+"  House Detailing", font1), 290, 842 - 90, 0);
		table=new PdfPTable(5);
		 font=new Font();
                         fontData=new Font();
                         float wid[]={20,20,20,20,20};
                        table.setWidths(wid);
                        font.setSize(this.getTableHeadingfont());
                        font.setColor(mycolor.fillColor(this.getTableHeadingColor()));
                        fontData.setSize(this.getTableDatafont());
                        fontData.setColor(mycolor.fillColor(this.getTableDataColor()));


                       // table.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
                        table.addCell(new Phrase(new Chunk("Sign",font)));

                        table.addCell(new Phrase(new Chunk("Sign Lord",font)));
                        table.addCell(new Phrase(new Chunk("NL",font)));
                        table.addCell(new Phrase(new Chunk("SL",font)));
                        table.addCell(new Phrase(new Chunk("NL(SL)",font)));

			table.addCell(new Phrase(new Chunk(signList.get(i+"").getSignName(),fontData)));

                        table.addCell(new Phrase(new Chunk(signList.get(i+"").getSS(),fontData)));
                        table.addCell(new Phrase(new Chunk(signList.get(i+"").getNL(),fontData)));
                        table.addCell(new Phrase(new Chunk(signList.get(i+"").getSL(),fontData)));
                        table.addCell(new Phrase(new Chunk(signList.get(i+"").getNLSL(),fontData)));

			table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 100), writer.getDirectContent());

					
			 table=new PdfPTable(5);
		table.setWidths(wid);

//                        table.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
                        table.addCell(new Phrase(new Chunk("Star",font)));

                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));

                        table.addCell(new Phrase(new Chunk("",font)));

                        table.addCell(new Phrase(new Chunk(starList.get(i+"").getSS(),fontData)));
                        table.addCell(new Phrase(new Chunk(starList.get(i+"").getNL(),fontData)));
                        table.addCell(new Phrase(new Chunk(starList.get(i+"").getSL(),fontData)));
                        table.addCell(new Phrase(new Chunk(starList.get(i+"").getNLSL(),fontData)));

                        table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 130), writer.getDirectContent());


     table=new PdfPTable(5);
                        table.setWidths(wid);

//                        table.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
                        table.addCell(new Phrase(new Chunk("SubLord",font)));

                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));

                        table.addCell(new Phrase(new Chunk("",font)));

                        table.addCell(new Phrase(new Chunk(subLordList.get(i+"").getSS(),fontData)));
                        table.addCell(new Phrase(new Chunk(subLordList.get(i+"").getNL(),fontData)));
                        table.addCell(new Phrase(new Chunk(subLordList.get(i+"").getSL(),fontData)));
                        table.addCell(new Phrase(new Chunk(subLordList.get(i+"").getNLSL(),fontData)));

                        table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 160), writer.getDirectContent());

/*
		 canvas.restoreState();
		 this.document.newPage();
                                 canvas.saveState();
                                        canvas.setLineWidth(3);
                                        canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);

                                        canvas.rectangle(35, 842 - 750, 530, 690);
                                        canvas.fillStroke();
                                        canvas.closePath();
                                        canvas.restoreState();

                                        canvas.setLineWidth(1f);
                                        canvas.saveState();

*/

//  ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
  //                                                              new Phrase("Aspects on House", font1), 290, 842 - 200, 0);

	 table=new PdfPTable(6);
                        //table.setWidths(wid);
	  table.addCell(new Phrase(new Chunk("HOUSE",font)));

                        table.addCell(new Phrase(new Chunk("PLANET",font)));
                        table.addCell(new Phrase(new Chunk("HOUSE",font)));
                        table.addCell(new Phrase(new Chunk("SIGN",font)));
                        table.addCell(new Phrase(new Chunk("DEGREE",font)));
                        table.addCell(new Phrase(new Chunk("ASPECT",font)));
	

	   table.addCell(new Phrase(new Chunk(""+i,font)));
         
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));

		


		boolean flg=true;
		Iterator iter = aspectList.get(i+"").iterator();
		if(aspectList.get(i+"").size()>0)
		{	while(iter.hasNext())
			{

			
			String temp[]=((String)iter.next()).split("_");	

			if(flg)
			{
			table.addCell(new Phrase(new Chunk(temp[0],font)));

                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
			flg=false;
			}
			if(!temp[1].equalsIgnoreCase("Ketu") && !temp[1].equalsIgnoreCase("Rahu")){
			 table.addCell(new Phrase(new Chunk("",fontData)));

                        table.addCell(new Phrase(new Chunk(temp[1],fontData)));
                        table.addCell(new Phrase(new Chunk(temp[2],fontData)));
                        table.addCell(new Phrase(new Chunk(temp[3],fontData)));
                        table.addCell(new Phrase(new Chunk(temp[4],fontData)));
                        table.addCell(new Phrase(new Chunk(temp[5],fontData)));
			}

			}
			flg=true;
		 }
                else
                {
                  table.addCell(new Phrase(new Chunk("NA",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
			flg=false;
                }

			if(flg){
		 ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                                                new Phrase("Aspects on House", font1), 290, 842 - 210, 0);

			table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 220), writer.getDirectContent());
			}
	         table=new PdfPTable(5);
               	  table.setWidths(wid);
	          table.addCell(new Phrase(new Chunk("Occupant",font)));

                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
			iter = occupantList.get(i+"").iterator();
		flg=false;
	if(occupantList.get(i+"").size()>0)
		{
                        while(iter.hasNext())
                {
                        HouseDetailBean bean=(HouseDetailBean)iter.next();
                         table.addCell(new Phrase(new Chunk("",font)));

                        table.addCell(new Phrase(new Chunk(bean.getSS(),fontData)));
                        table.addCell(new Phrase(new Chunk(bean.getNL(),fontData)));
                        table.addCell(new Phrase(new Chunk(bean.getSL(),fontData)));
                        table.addCell(new Phrase(new Chunk(bean.getNLSL(),fontData)));
			


                }
		flg=true;
		}
		else
		{
			flg=false;
		}
			if(flg)
			{
			table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 380), writer.getDirectContent());
			}





			  table=new PdfPTable(6);
          table.addCell(new Phrase(new Chunk("OCUPANT",font)));

                        table.addCell(new Phrase(new Chunk("PLANET",font)));
                        table.addCell(new Phrase(new Chunk("HOUSE",font)));
                        table.addCell(new Phrase(new Chunk("SIGN",font)));
                        table.addCell(new Phrase(new Chunk("DEGREE",font)));
                        table.addCell(new Phrase(new Chunk("ASPECT",font)));



int count=0;
	for (Map.Entry<String, HashSet<String>> entry : occAspList.get(i+"").entrySet())
                {
		
			
			table.addCell(new Phrase(new Chunk(entry.getKey(),fontData)));

                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
                        table.addCell(new Phrase(new Chunk("",font)));
		

			flg=true;
			iter = (entry.getValue()).iterator();
			{
			if((entry.getValue()).size()>0)
			{
			while(iter.hasNext())
			{	
                       	 String temp[]=((String)iter.next()).split("_");
			if(!temp[1].equalsIgnoreCase("Ketu")  && !temp[1].equalsIgnoreCase("Rahu") ){
			if(flg)
			{
				table.addCell(new Phrase(new Chunk(temp[0],font)));
				flg=false;
			}
			else
			{	table.addCell(new Phrase(new Chunk("",font)));
			}
		//	if(!temp[1].equalsIgnoreCase("Ketu")  && !temp[1].equalsIgnoreCase("Rahu") ){	
                         table.addCell(new Phrase(new Chunk(temp[1],fontData)));

                        table.addCell(new Phrase(new Chunk(temp[2],fontData)));
                        table.addCell(new Phrase(new Chunk(temp[3],fontData)));
                        table.addCell(new Phrase(new Chunk(temp[4],fontData)));
                        //table.addCell(new Phrase(new Chunk(aspectList.get(i+"").get(j).getNLSL(),fontData)));
                        table.addCell(new Phrase(new Chunk(temp[5],fontData)));
			count++;
			}
		}

                }
		 
                else
                {
	/*                  table.addCell(new Phrase(new Chunk("NA",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
                          table.addCell(new Phrase(new Chunk("",font)));
*/
                }
		}
	}

		if(count>0)
		{
		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                                                                new Phrase("Aspects on Occupants", font1), 290, 842 - 440, 0);
		table.setTotalWidth(510);
                        table.writeSelectedRows(0, -1, 50, (842 - 450), writer.getDirectContent());
		}




	//by bharti		canvas.restoreState();
		}

}
                } catch (Exception e) {
                        e.printStackTrace();
                }


	}


	//*******************************To Draw Next Page of One Rectangle********************************************//
	public void DashaStrength(PdfContentByte canvas, AstroBean astrobean,ColorElement mycolor) {


		float width1 = 220;
		float height1 = 110;    //old110
		float xCoordinate1 = 80;
		float yCoordinate1 = 160;   //old 160



		this.document.newPage();

		try {

			Vector<DashaStrength> obj = astrobean.getDashaStrength();
		//	logger.info("Size of vector is" + obj.size());
			if (obj.size() != 0) {
				for (int j = 0; j < obj.size(); j++) {



					DashaStrength dashaBean = (DashaStrength) obj.get(j);
					Kundli cuspChartDetail1 = dashaBean.getCuspKundli();

				//bharti	canvas.saveState();
					canvas.setLineWidth(3);
					canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);

					canvas.rectangle(35, 842 - 750, 530, 690);
					canvas.fillStroke();
					canvas.closePath();
				//by bharti	canvas.restoreState();

					canvas.setLineWidth(1f);
					
        			String proPath = Constants.PROPERTIES_PATH;
        			proPath = proPath + "/kundliHttpserverNew.properties";
        			Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);			
        			
        			Font fontFooter = new Font();
        			fontFooter.setSize(10);
        			fontFooter.setColor(mycolor.fillColor(getTableDataColor()));
        			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
        					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
        					842 - 790, 0);
        			
        			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
        					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
        					842 - 800, 0);
					
			//bharti		canvas.saveState();

					Font font1 = new Font();
					font1.setSize(getHeadingfont());

					Font font2 = new Font();
					font2.setSize(getTableHeadingfont());
					font2.setColor(mycolor.fillColor(getTableHeadingColor()));
					if (dashaBean.getCurrentFlag()) {
						ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
								new Phrase("Dasha Strength " + "Current" + "(" + dashaBean.getDashaPlanet() + ")", font1), 290, 842 - 120, 0);
					} else {

						ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
								new Phrase("Dasha Strength " + "(" + dashaBean.getDashaPlanet() + ")", font1), 290, 842 - 120, 0);
					}

					drawRectangle(canvas, xCoordinate1, toXYCord(yCoordinate1) - height1, width1, height1);//Draw Cusp Kundli of second page

					drawLine(canvas, xCoordinate1, toXYCord(yCoordinate1), width1, height1);
					fillHouse(cuspChartDetail1, canvas, xCoordinate1, toXYCord(yCoordinate1), width1, height1,mycolor);
					ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
							new Phrase("Cusp Kundli" , font2), 180, 842 - 155, 0);


					// canvas1.stroke();
					canvas.stroke();
					//**************************To fill Personal Detail**********************************************//
					PdfPTable persondetail =fillPersonalDetail(astrobean,mycolor,dashaBean);
					persondetail.setTotalWidth(230);
					persondetail.getDefaultCell().setBorderWidth(2);
					persondetail.writeSelectedRows(0, -1, 320, (842 - 220), writer.getDirectContent());

					//*****************************************To fill Planet Detail********************************************//
					String[][] tabulardataone = new String[9][7];
					tabulardataone = dashaBean.getNatalStrengthChart();
					flag=1;
					PdfPTable Planettable2 = fillPanetTable(tabulardataone,mycolor,flag,addHouse);
					Planettable2.setTotalWidth(500);
					Planettable2.getDefaultCell().setBorderWidth(1f);
					Planettable2.writeSelectedRows(0, -1, 50, (842 - 300), writer.getDirectContent());

					//*******************************To fill Rahu And Ketu Aspectes**************************************************//

					PdfPTable rahuKetutable1 = fillAspecttable(dashaBean,mycolor);
					rahuKetutable1.setTotalWidth(500);
					rahuKetutable1.writeSelectedRows(0, -1, 50, (842 - 460), writer.getDirectContent());
					document.newPage();


				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//************************************** To create  MahaDasha nad Antardasha Page*************************************//
	public void mahaDashaAndSooksham(PdfContentByte canvas, PdfContentByte content, AstroBean astrobean,ColorElement mycolor) {

		try {


			document.newPage();

			Font font1 = new Font();
			font1.setSize(12);
			font1.setColor(mycolor.fillColor(this.getTableHeadingColor()));
			font1.setFamily("Times New Roman");
			font1.setStyle("Bold");
			Font font2 = new Font();
			font2.setSize(8);
			font2.setColor(mycolor.fillColor(this.getTableDataColor()));

	//bharti		canvas.saveState();
			canvas.setLineWidth(3);
			canvas.setRGBColorFill(0xFF, 0xFF, 0xFF);
			canvas.rectangle(35, 842 - 750, 530, 690);
			canvas.fillStroke();
			canvas.closePath();
	//bharti		canvas.restoreState();
		/*BHARTI*/canvas.setLineWidth(1F);
			fillAntarDashaTable(canvas,astrobean,mycolor);

			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
					new Phrase(astrobean.getName().replaceAll("%20"," " ).replaceAll("\"",""), font1), 35, 842 - 55, 0);
			
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Vimshottari Mahadasha and Antardashas", font1), 300, 842 - 120, 0);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Dasha balance at birth : Ketu 6y 7m 29d", font2), 300, 842 - 135, 0);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase("Dasha at the time of birth : Ke-Ke-Sa-Ra-Su", font2), 300, 842 - 150, 0);
			
			String proPath = Constants.PROPERTIES_PATH;
			proPath = proPath + "/kundliHttpserverNew.properties";
			Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);

			Font fontFooter = new Font();
			fontFooter.setSize(10);
			fontFooter.setColor(mycolor.fillColor(getTableDataColor()));
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
					842 - 790, 0);
			
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
					842 - 800, 0);



			//*****************************For the Sooksham Dasha*********************************************************//
			document.newPage();
		//bharti	content.saveState();
			content.setLineWidth(3);
			content.setRGBColorFill(0xFF, 0xFF, 0xFF);
			content.rectangle(35, 842 - 750, 530, 690);
			content.fillStroke();
			content.closePath();
		//bharti	content.restoreState();
			
			content.setLineWidth(1F);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
					new Phrase(astrobean.getName().replaceAll("%20"," " ).replaceAll("\"",""), font1), 35, 842 - 55, 0);
			
			ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
					new Phrase("Vimshottari Pratyantardasha and Sookshma Dasha", font1), 300, 842 - 120, 0);

			fillSookshmaDashaTable(content, astrobean,mycolor);
			
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(properties.getOrDefault("astro.link", "Observation"), fontFooter), 300,
					842 - 790, 0);
			
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(properties.getOrDefault("astro.observation", "Observation"), fontFooter), 300,
					842 - 800, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void drawRectangle(PdfContentByte content, float x, float y, float width, float height) {
//bharti		content.saveState();


		content.setRGBColorFill(0xFF, 0xFF, 0xFF);
		content.setLineWidth(1);
		content.rectangle(x, y, width, height);
		content.fillStroke();
		content.closePath();
	//bharti	content.restoreState();
	}

	public static void drawLine(PdfContentByte content, float x, float y, float width, float height) {

		content.moveTo((width / 2 + x), (y - height));
		content.lineTo(x, (y - (height / 2)));
		content.moveTo((width / 2 + x), (y - height));
		content.lineTo(width + x, (y - (height / 2)));
		content.moveTo(width + x, (y - (height / 2)));
		content.lineTo(((width / 2) + x), y);
		content.moveTo(x, (y - (height / 2)));
		content.lineTo(((width / 2) + x), y);
		content.moveTo(x, y);
		content.lineTo(x + width, (y - height));
		content.moveTo(x, y - height);
		content.lineTo(x + width, y);

		content.stroke();
		content.closePath();
//bharti		content.restoreState();
	}

	public void fillHouse(Kundli HouseDetail, PdfContentByte content, float x, float y, float width, float height,ColorElement mycolor) {

		String[] PlanetArr = null;
		String[] subPlanetArr = null;
		StringBuffer planetBuffer = new StringBuffer();
		String subPlanet = "";
		int Planetlength = 0;
		String str = "";

		Font font = new Font();
		font.setSize(this.getTableDatafont());
		font.setColor(mycolor.fillColor(this.tableHeadingColor));
		for (int i = 0; i < 12; i++) {
			switch (i) {
				case 0:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {

						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font), (x + (width / 2)), (y - (height / 2) + ((2 * height) / 100)), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");

						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font), (x + (width / 2)), (y - (height / 2) + ((2 * height) / 100)), 0);

						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (width / 2)), (y - (height / 2) + ((20 * height) / 100)), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 1:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {

						ColumnText.showTextAligned(content, Element.ALIGN_CENTER, new Phrase(PlanetArr[0], font),
								(x + (width / 4 + ((1 * width) / 100))), (y - ((22 * height) / 100)), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER, new Phrase(PlanetArr[0], font),
									(x + (width / 4 + ((1 * width) / 100))), (y - ((22 * height) / 100)), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (width / 4)), (y - (height / 4) + ((15 * height) / 100)), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 2:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER, new Phrase(PlanetArr[0], font),
								(x + (width / 4) - ((5 * width)) / 100), (y - ((28 * height) / 100)), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER, new Phrase(PlanetArr[0], font),
									(x + (width / 4) - ((4 * width)) / 100), (y - ((28 * height) / 100)), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER, new Phrase(planetBuffer.toString(), font),
								(x + ((width / 8) - ((4 * width) / 100))), (y - (height / 4)), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 3:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								(x + (width / 2) - ((5 * width) / 100)), (y - ((height / 2) + ((2 * height) / 100))), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");

						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									(x + (width / 2) - ((5 * width) / 100)), (y - ((height / 2) + ((2 * height) / 100))), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (width / 4) - ((1 * x) / 100)), (y - ((height / 2) + ((2 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 4:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								(((x + (width / 4)) - ((4 * width)) / 100)), (y - ((3 * height / 4) + ((3 * height) / 100))), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									(((x + (width / 4)) - ((4 * width)) / 100)), (y - ((3 * height / 4) + ((3 * height) / 100))), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (width / 8) - ((4 * width) / 100)), (y - ((3 * height / 4) - ((1 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 5:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								((x + (width / 4))), (y - ((85 * height) / 100)), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									((x + (width / 4))), (y - ((85 * height) / 100)), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (width / 4)), (y - ((3 * height / 4) + ((20 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 6:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								(x + (width / 2)), (y - ((height / 2) + ((10 * height) / 100))), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");
							//logger.info("Planet in the buffer is:" +planetBuffer.toString());
							// logger.info("After split with spaces data is:"+subPlanet);
							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									(x + (width / 2)), (y - ((height / 2) + ((10 * height) / 100))), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (width / 2)), (y - ((3 * height / 4))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 7:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								(x + ((3 * width) / 4)), (y - (height - ((15 * height) / 100))), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						// logger.info("After split data is:"+subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");
							// logger.info("Planet in the buffer is:" +planetBuffer.toString());
							//  logger.info("After split with spaces data is:"+subPlanet);
							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									(x + ((3 * width) / 4)), (y - (height - ((15 * height) / 100))), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (3 * width / 4)), (y - ((height) - ((5 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 8:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								((x + ((3 * width) / 4)) + ((5 * width)) / 100), (y - ((3 * height) / 4) - ((2 * height) / 100)), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");
							//logger.info("Planet in the buffer is:" +planetBuffer.toString());
							// logger.info("After split with spaces data is:"+subPlanet);
							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									((x + ((3 * width) / 4)) + ((5 * width)) / 100), (y - ((3 * height) / 4) - ((2 * height) / 100)), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (3 * width / 4) + ((15 * width) / 100)), (y - ((height) - ((24 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 9:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								(x + ((width) / 2) + ((5 * width) / 100)), (y - ((height / 2) + ((2 * height) / 100))), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						if(logger.isDebugEnabled())
						logger.debug("After split data is:" + subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");
							// logger.info("Planet in the buffer is:" +planetBuffer.toString());
							// logger.info("After split with spaces data is:"+subPlanet);
							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									(x + ((width) / 2) + ((5 * width) / 100)), (y - ((height / 2) + ((2 * height) / 100))), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (3 * width / 4)), (y - ((height / 2) + ((1 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 10:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								((x + ((3 * width) / 4) + ((4 * width) / 100))), (y - ((height / 4) + ((3 * height) / 100))), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						// logger.info("After split data is:"+subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");
							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									((x + ((3 * width) / 4) + ((4 * width) / 100))), (y - ((height / 4) + ((3 * height) / 100))), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (3 * width / 4) + ((18 * width) / 100)), (y - ((height / 4) + ((1 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

				case 11:
					str = (String) HouseDetail.getHouseData(i + 1);
					PlanetArr = str.split("_");
					if (PlanetArr.length <= 1) {
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(PlanetArr[0], font),
								((x + ((3 * width) / 4)) - ((1 * width)) / 100), (y - ((22 * height) / 100)), 0);
					} else {
						Planetlength = PlanetArr.length;
						subPlanetArr = PlanetArr[1].split(" ");
						//  logger.info("After split data is:"+subPlanetArr[0]);
						for (int j = 0; j < subPlanetArr.length; j++) {
							subPlanet = subPlanetArr[j].substring(0, 2);
							planetBuffer = planetBuffer.append(subPlanet);
							planetBuffer = planetBuffer.append(" ");

							ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
									new Phrase(PlanetArr[0], font),
									((x + ((3 * width) / 4)) - ((1 * width)) / 100), (y - ((22 * height) / 100)), 0);
						}
						ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
								new Phrase(planetBuffer.toString(), font), (x + (3 * width / 4) + ((1 * width) / 100)), (y - ((height / 4) - ((10 * height) / 100))), 0);
					}
					planetBuffer.delete(0, planetBuffer.length());
					break;

			}
		}
	}//Fill Rectangle

	


public PdfPTable fillPanetTable_first(String[][] tabulardata,ColorElement mycolor,byte flag,String[] addHouse, String[][] tabulardata_1) throws DocumentException {
		Font fontplanet = new Font();
		fontplanet.setSize(this.getTableDatafont());
		fontplanet.setColor(mycolor.fillColor(this.getTableDataColor()));

		Font fontplanethead = new Font();
		fontplanethead.setSize(this.getTableHeadingfont());
		fontplanethead.setColor(mycolor.fillColor(this.tableHeadingColor));
		PdfPTable Planettable = null;
		int i=0;
	   
		try {
			Planettable = new PdfPTable(8);
			Planettable.setWidthPercentage(100);
			float wid[] = {14, 14, 18, 10, 18, 18,13,12};
			Planettable.setWidths(wid);
			Planettable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
			Planettable.addCell(new Phrase(new Chunk("Planet", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("Source", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("Result(NL)", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("Adtnl H", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("SL", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("NL (SL)", fontplanethead)));
            Planettable.addCell(new Phrase(new Chunk("ADL upto (Current)", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("MD upto", fontplanethead)));

			for (int outer = 0; outer < tabulardata.length; outer++) {
				//For Source Column
 			for (int inner = 0; inner < tabulardata[outer].length+1; inner++) {
					Planettable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
					
					//logger.info("--------------------------------------> Inner [ "+inner+" ] inner % 3 [ "+inner%3+" ] outer [ "+outer+" ]");
					
					if(flag==0)
					{
				//Planettable.addCell(new Phrase(new Chunk(tabulardata[outer][inner], fontplanet)));
					if(inner==3)
					{
						if(logger.isDebugEnabled())
						logger.debug("Value in the Outer is [ "+outer+" ]");
							//System.out.println("DDDDdddddddddddddddddddddddddddddddd"+tabulardata[outer][3]);
							addHouse[outer]=tabulardata[outer][3];						
					}
					
					}
					
						
					if(flag==1 && inner==3)
					{
						if(logger.isDebugEnabled())
						logger.debug("The Value of of Flag is 1");
						
							logger.info("The Value Of Inner is ["+inner+"] And Outer is ["+outer+"] And the Array is"+addHouse[outer]);


						/*
						 * System.out.println("addHouse==>"+addHouse);
						 * System.out.println("fontplanet==>"+fontplanet);
						 * System.out.println("Planettable==>"+Planettable);
						 * System.out.println("outer==>"+outer);
						 */
							System.out.println("house=====>"+addHouse[outer]);
						 Planettable.addCell(new Phrase(new Chunk(addHouse[outer], fontplanet)));
							
						
					}
					else
					{
					
					if(inner==tabulardata[outer].length-1)
                			{
						String tt=tabulardata_1[outer][6];
						if(tt!=null && tt!="")
							{
							Planettable.addCell(new Phrase(new Chunk(tt.split(" ")[0], fontplanet)));
							}
						else{
							Planettable.addCell(new Phrase(new Chunk("", fontplanet)));
						}
					 }
					else if(inner==tabulardata[outer].length)
						{
						 String tmp=tabulardata[outer][inner-1];
						 if(tmp!=null && tmp!="")
                                                        {
                                                                Planettable.addCell(new Phrase(new Chunk(tmp.split(" ")[0], fontplanet)));
                                                        }
                                                        else
                                                        {
                                                                Planettable.addCell(new Phrase(new Chunk("", fontplanet)));
                                                        }


						}
					else{
						Planettable.addCell(new Phrase(new Chunk(tabulardata[outer][inner], fontplanet)));
					    }
					
					}
					
				}
 			
 			
			}


/*			for (int outer = 0; outer < tabulardata_1.length; outer++) {
				//For Source Column
				for (int inner = 0; inner < tabulardata_1[outer].length; inner++) {
					if(inner==6)
					{
						Planettable.addCell(new Phrase(new Chunk(tabulardata_1[outer][inner], fontplanet)));

					}
				}


			}*/

			for(int j=0;j<addHouse.length;j++)
			{
				//addHouse[j]=tabulardata[outer][3];
				if(logger.isDebugEnabled())
				logger.debug("*Value in the Array is "+addHouse[j]);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Planettable;
	}//Fill Planet Table







public PdfPTable fillPanetTable(String[][] tabulardata,ColorElement mycolor,byte flag,String[] addHouse) throws DocumentException {
		Font fontplanet = new Font();
		fontplanet.setSize(this.getTableDatafont());
		fontplanet.setColor(mycolor.fillColor(this.getTableDataColor()));

		Font fontplanethead = new Font();
		fontplanethead.setSize(this.getTableHeadingfont());
		fontplanethead.setColor(mycolor.fillColor(this.tableHeadingColor));
		PdfPTable Planettable = null;
		int i=0;
	   
		try {
			
			
			Planettable = new PdfPTable(7);
			Planettable.setWidthPercentage(100);
			float wid[] = {15, 15, 20, 10, 20, 20, 15};
			Planettable.setWidths(wid);
			Planettable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
			Planettable.addCell(new Phrase(new Chunk("Planet", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("Source", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("Result(NL)", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("Adtnl H", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("SL", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("NL (SL)", fontplanethead)));
			Planettable.addCell(new Phrase(new Chunk("ADL upto", fontplanethead)));


			for (int outer = 0; outer < tabulardata.length; outer++) {
				//For Source Column
 			for (int inner = 0; inner < tabulardata[outer].length; inner++) {
					Planettable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
					
					//logger.info("--------------------------------------> Inner [ "+inner+" ] inner % 3 [ "+inner%3+" ] outer [ "+outer+" ]");
					
					if(flag==0)
					{
				//Planettable.addCell(new Phrase(new Chunk(tabulardata[outer][inner], fontplanet)));
					if(inner==3)
					{
						if(logger.isDebugEnabled())
						logger.debug("Value in the Outer is [ "+outer+" ]");
							
							addHouse[outer]=tabulardata[outer][3];						
					}
					
					}
					
						
					if(flag==1 && inner==3)
					{
						if(logger.isDebugEnabled())
						logger.debug("The Value of of Flag is 1");
						
						if(logger.isDebugEnabled())
							logger.debug("The Value Of Inner is ["+inner+"] And Outer is ["+outer+"] And the Array is"+addHouse[outer]);
							Planettable.addCell(new Phrase(new Chunk(addHouse[outer], fontplanet)));
						
					}
					else
					{
						if(inner==6)
						{
							String tmp=tabulardata[outer][inner];
							if(tmp!=null && tmp!="")
							{
								Planettable.addCell(new Phrase(new Chunk(tmp.split(" ")[0], fontplanet)));	
							}
							else
							{
								Planettable.addCell(new Phrase(new Chunk("", fontplanet)));
							}
							
						}
						else
						{
							Planettable.addCell(new Phrase(new Chunk(tabulardata[outer][inner], fontplanet)));
						}
					}
					
				}
 			
 			
			}

			for(int j=0;j<addHouse.length;j++)
			{
				//addHouse[j]=tabulardata[outer][3];
				if(logger.isDebugEnabled())
				logger.debug("*Value in the Array is "+addHouse[j]);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Planettable;
	}//Fill Planet Table

	public PdfPTable fillAspecttable(AstroBean astrobean,ColorElement mycolor) {
		PdfPTable rahuAndKetutable = null;
		try {
                        String planetNme[] ={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};
			Font fontaspecthead = new Font();
			fontaspecthead.setSize(this.getTableHeadingfont());
			fontaspecthead.setColor(mycolor.fillColor(this.tableHeadingColor));

			rahuAndKetutable = new PdfPTable(2);
			Font fontaspect = new Font();
			fontaspect.setSize(this.getTableDatafont());
			fontaspect.setColor(mycolor.fillColor(this.getTableDataColor()));

			rahuAndKetutable.getDefaultCell().setBorderWidth(1);
			rahuAndKetutable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
			rahuAndKetutable.addCell(new Phrase(new Chunk("Rahu", fontaspecthead)));

			rahuAndKetutable.getDefaultCell().setBorderWidth(1);

			rahuAndKetutable.addCell(new Phrase(new Chunk("Ketu", fontaspecthead)));
			rahuAndKetutable.completeRow();

			// PdfPCell rahuAndKetucell=new PdfPCell();
			PdfPTable aspectTable = new PdfPTable(2);
			float wid[] = {20, 70};
			aspectTable.setWidths(wid);

			//   aspectTable.getDefaultCell().setBorderWidth(1);
			PdfPTable sourceTableRahu = new PdfPTable(1);
			// sourceTableRahu.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			String RahuValue = astrobean.getRahuSource();
			sourceTableRahu.addCell(new Phrase(new Chunk(RahuValue, fontaspect)));
			aspectTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			aspectTable.addCell(sourceTableRahu);
			PdfPTable sourceTableAspect = new PdfPTable(1);
	       	//	String RahuAspect[] =astrobean.getAspect(Planet.toPlanets("Rahu"));
		//	logger.info("HERE BHARTI ==>> "+RahuAspect[0]);
			//String RahuAspect[] = {"Mars9/10","Mars9/10","Mars9/10","Mars9/10","Mars9/10","Mars9/10","Mars9/10","Mars9/10","Mars9/10"};                 //new String[7];
			
			String RahuAspectstr = "";
			//logger.info("astrobean.aspectRahu>> "+astrobean.getAspectRahu());
                        for(int j=0; j<planetNme.length; j++){
                                for(int num=0; num<planetNme.length; num++)
                                {
                                        HashMap<String, String> hashMap = astrobean.getPlanetHouseAspectDetails().get(planetNme[num]);
                                       // logger.info("RahuAspect hashmap?????"+hashMap);
                                        if(hashMap.get(planetNme[j])!=null){
                                                if(num==5){
                                                        if(planetNme[j]!="Ketu"){
                                                                RahuAspectstr =RahuAspectstr+planetNme[j]+"("+hashMap.get(planetNme[j])+")"+" ";
                                                        }
                                                }
                                        }
                                }
                        }

                        if(RahuAspectstr.isEmpty()){
                                RahuAspectstr = "NA";
                        }

			
		/*	if (RahuAspect != null) {
				
				for (int i = 0; i < RahuAspect.length; i++) {
					
					RahuAspectstr += RahuAspect[i]+"\n";
					
				}
				sourceTableAspect.getDefaultCell().setBackgroundColor(mycolor.fillColor(this.getTableDataBgcolor()));
				
			} else {
				RahuAspectstr="NA";
			}*/

			sourceTableAspect.addCell(new Phrase(new Chunk("A : " + RahuAspectstr, fontaspect)));

			String[] RahuConjuction = astrobean.getConjuction(Planet.toPlanets("Rahu"));
			String RahuConjuctionstr = "";
			if (RahuConjuction != null) {
				

				for (int i = 0; i < RahuConjuction.length; i++) {
					RahuConjuctionstr += RahuConjuction[i]+"\n";

				}
				
			} else {
				RahuConjuctionstr="NA";
				
			}
                        sourceTableAspect.getDefaultCell().setBackgroundColor(mycolor.fillColor(this.getTableDataBgcolor()));

			sourceTableAspect.addCell(new Phrase(new Chunk("C : " + RahuConjuctionstr, fontaspect)));
			
			String RahuSignLoard = astrobean.getSignLoard(Planet.toPlanets("Rahu"));
			String RahuRashiLoard = astrobean.getRashiLoard(Planet.toPlanets("Rahu"));
			if (RahuSignLoard != null) {
				sourceTableAspect.addCell(new Phrase(new Chunk("S : " + RahuSignLoard, fontaspect)));
			} else {
				sourceTableAspect.addCell(new Phrase(new Chunk("S : NA", fontaspect)));
				RahuSignLoard="NA";
			}

			if (RahuRashiLoard != null) {
				sourceTableAspect.addCell(new Phrase(new Chunk("R : " + RahuRashiLoard, fontaspect)));

			} else {
				sourceTableAspect.addCell(new Phrase(new Chunk("R : NA", fontaspect)));
				RahuRashiLoard="NA";
			}


			aspectTable.addCell(sourceTableAspect);
			rahuAndKetutable.addCell(aspectTable);
			//******************************************for the ketu ***************************************************//


			PdfPTable aspectTable2 = new PdfPTable(2);

			aspectTable2.setWidths(wid);
			PdfPTable sourceTableKetu = new PdfPTable(1);
			String KetuValue = astrobean.getKetuSource();
			sourceTableKetu.addCell(new Phrase(new Chunk(KetuValue, fontaspect)));
			aspectTable2.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			aspectTable2.addCell(sourceTableKetu);

			PdfPTable sourceTableKetuAspect = new PdfPTable(1);
		//	String[] KetuAspect = astrobean.getAspect(Planet.toPlanets("Ketu"));
			String KetuAspectstr = "";
                         for(int j=0; j<planetNme.length; j++){
                                for(int num=0; num<planetNme.length; num++)
                                {
                                        HashMap<String, String> hashMap = astrobean.getPlanetHouseAspectDetails().get(planetNme[num]);
                                     //   logger.info("KetuAspect hashmap?????"+hashMap);
                                        if(hashMap.get(planetNme[j])!=null){
                                                if(num==0){
                                                        if(planetNme[j]!="Rahu"){
                                                                KetuAspectstr =KetuAspectstr+planetNme[j]+"("+hashMap.get(planetNme[j])+")"+" ";
                                                        }
                                                }
                                        }
                                }
                        }

                        if(KetuAspectstr.isEmpty()){
                                KetuAspectstr = "NA";
                        }

			
		/*	if (KetuAspect != null) {
				
				for (int i = 0; i < KetuAspect.length; i++) {
					KetuAspectstr +=KetuAspect[i]+"\n";
				}
				sourceTableKetuAspect.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
				
			} else {
				KetuAspectstr="NA";
			}*/
                        sourceTableKetuAspect.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			sourceTableKetuAspect.addCell(new Phrase(new Chunk("A : " + KetuAspectstr, fontaspect)));

			String[] KetuConjuction = astrobean.getConjuction(Planet.toPlanets("Ketu"));
			String KetuConjuctionstr = "";
			
			
			if (KetuConjuction != null) {
				

				for (int i = 0; i < KetuConjuction.length; i++) {
					KetuConjuctionstr += KetuConjuction[i]+"\n";

				}
				
			} else {
				KetuConjuctionstr="NA";
			}
			sourceTableKetuAspect.addCell(new Phrase(new Chunk("C : " + KetuConjuctionstr, fontaspect)));
			
			
			String KetuSignLoard = astrobean.getSignLoard(Planet.toPlanets("Ketu"));
			String KetuRashiLoard = astrobean.getRashiLoard(Planet.toPlanets("Ketu"));


			if (KetuSignLoard != null) {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("S : " + KetuSignLoard, fontaspect)));
			} else {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("S : NA", fontaspect)));
				KetuSignLoard="NA";

			}



			if (KetuRashiLoard != null) {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("R : " + KetuRashiLoard, fontaspect)));

			} else {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("R : NA", fontaspect)));
				KetuRashiLoard="NA";
			}


			aspectTable2.addCell(sourceTableKetuAspect);

			rahuAndKetutable.addCell(aspectTable2);
			rahuAndKetutable.completeRow();
			
			astrobean.setAspectRahu(RahuAspectstr);
			astrobean.setAspectKetu(KetuAspectstr);
			astrobean.setConjucRahu(RahuConjuctionstr);
			astrobean.setConjucKetu(KetuConjuctionstr);
			astrobean.setSignLordRahu(RahuSignLoard);
			astrobean.setSignLordKetu(KetuSignLoard);
			astrobean.setRashiLordRahu(RahuRashiLoard);
			astrobean.setRashiLordKetu(KetuRashiLoard);
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return rahuAndKetutable;


	}//Rahu Ketu Aspects

	private PdfPTable fillTransistPersonalDetail(AstroBean astrobean,ColorElement mycolor) {

		if(logger.isDebugEnabled())
		logger.debug("Inside fillPersonaldetail function ");
		
		logger.info(" astrobean "+astrobean.getName() + " city "+astrobean.getTransitCity() + " astro date " + astrobean.getDOB()  +" Time "+ astrobean.getTOB());
		
		PdfPTable personalDetailTable = new PdfPTable(1);
		try {
			Font fonthead = new Font();
			fonthead.setSize(this.getTableHeadingfont());
			fonthead.setColor(mycolor.fillColor(this.getTableDataColor()));
			fonthead.setStyle("bold");

			Font font = new Font();
			font.setSize(this.getTableHeadingfont());
			font.setColor(mycolor.fillColor(this.getTableDataColor()));


			personalDetailTable.getDefaultCell().setBorderWidth(1);
			PdfPTable subpersonalDetailTable = new PdfPTable(13);
			//float[] wid = {3,5, 8,10, 5, 8,10, 5, 10,10, 6, 7,3};
			float[] wid = {2,5, 30,3, 5, 8,3, 5, 10,3, 6,16,3};
			subpersonalDetailTable.setWidths(wid);

			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space


			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			subpersonalDetailTable.addCell(new Phrase(new Chunk("", fonthead)));
            logger.info("transit city===========>"+astrobean.getTransitCity());
			subpersonalDetailTable.addCell(new Phrase(new Chunk(astrobean.getTransitCity(), font)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//to have a space
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			//logger.info("Check sub string ----------BEFORE -----"); 

			//logger.info("DOB  operations            [ "+astrobean.getDOB()+" ] ");
	//		if(logger.isDebugEnabled())
///			logger.debug("Error -------- [ "+astrobean.getDOB().substring(astrobean.getDOB().lastIndexOf(" "), astrobean.getDOB().length()));
			subpersonalDetailTable.addCell(new Phrase(new Chunk("", fonthead)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			String[] tob=astrobean.getTOB().split(":");
			logger.info("transit tob===========>"+tob[0] +" "+tob[1]);
			subpersonalDetailTable.addCell(new Phrase(new Chunk(tob[0]+":"+tob[1], font)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			String[] DobArr=new String[3];
			String Dob=null;
			//Dob=astrobean.getDOB().substring(0, astrobean.getDOB().lastIndexOf(" "));
			String[] dOb=astrobean.getDOB().split("-");
			if(logger.isDebugEnabled())
			logger.debug("Check sub string ------after ---------"+Arrays.asList(dOb));

			if(dOb!=null && dOb.length!=0)
			{
				// logger.info("DOB BEFORE ERROR [ "+Arrays.toString(DobArr);

				/*
				 * DobArr=Dob.split(" "); DobArr[1]=DobArr[1].substring(0,3);
				 */
				subpersonalDetailTable.addCell(new Phrase(new Chunk("", fonthead)));
				//String[] dob=astrobean.getTOB().split(":");
				//logger.info("transit dob===========>"+dob[0] +" "+dob[1]);
				subpersonalDetailTable.getDefaultCell().setBorderWidth(0);         
				subpersonalDetailTable.addCell(new Phrase(new Chunk(dOb[2]+" "+mon[Integer.parseInt(dOb[1])-1]+" "+dOb[0], font)));
				subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			} 
			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
            logger.info("name : "+astrobean.getName());
			subpersonalDetailTable.addCell(new Phrase(new Chunk("", fonthead)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			//logger.info("Errors if any ============================= "+ astrobean.getName().substring(1, astrobean.getName().length() - 1));
			String name=astrobean.getName();
			name=name.replaceAll("%20"," " );
			subpersonalDetailTable.addCell(new Phrase(new Chunk(name.trim(),font)));

			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space


			personalDetailTable.addCell(subpersonalDetailTable);
			personalDetailTable.completeRow();

		} catch (Exception ex) {
			logger.error("Exception while add transisnt personal detail  ",ex);
			ex.printStackTrace();
		}

		return personalDetailTable;
	
	}
	
	
	public PdfPTable fillPersonaldetail(AstroBean astrobean,ColorElement mycolor) {
		if(logger.isDebugEnabled())
		logger.debug("Inside fillPersonaldetail function at line no 997");
		PdfPTable personalDetailTable = new PdfPTable(1);
		try {
			Font fonthead = new Font();
			fonthead.setSize(this.getTableHeadingfont());
			fonthead.setColor(mycolor.fillColor(this.getTableDataColor()));
			fonthead.setStyle("bold");

			Font font = new Font();
			font.setSize(this.getTableHeadingfont());
			font.setColor(mycolor.fillColor(this.getTableDataColor()));


			personalDetailTable.getDefaultCell().setBorderWidth(1);
			PdfPTable subpersonalDetailTable = new PdfPTable(13);
			//float[] wid = {3,5, 8,10, 5, 8,10, 5, 10,10, 6, 7,3};
			float[] wid = {2,5, 30,3, 5, 8,3, 5, 10,3, 6,16,3};
			subpersonalDetailTable.setWidths(wid);

			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space


			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			subpersonalDetailTable.addCell(new Phrase(new Chunk("POB:", fonthead)));

			subpersonalDetailTable.addCell(new Phrase(new Chunk(astrobean.getPOB(), font)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//to have a space
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			//logger.info("Check sub string ----------BEFORE -----"); 

			//logger.info("DOB  operations            [ "+astrobean.getDOB()+" ] ");
			if(logger.isDebugEnabled())
			logger.debug("Error -------- [ "+astrobean.getDOB().substring(astrobean.getDOB().lastIndexOf(" "), astrobean.getDOB().length()));
			subpersonalDetailTable.addCell(new Phrase(new Chunk("TOB:", fonthead)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.addCell(new Phrase(new Chunk(astrobean.getDOB().substring(astrobean.getDOB().lastIndexOf(" "), astrobean.getDOB().length()), font)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			String[] DobArr=new String[3];
			String Dob=null;
			Dob=astrobean.getDOB().substring(0, astrobean.getDOB().lastIndexOf(" "));

			if(logger.isDebugEnabled())
			logger.debug("Check sub string ------after ---------"+Dob);

			if(!StringUtils.isBlank(Dob))
			{
				// logger.info("DOB BEFORE ERROR [ "+Arrays.toString(DobArr);

				DobArr=Dob.split(" ");
				DobArr[1]=DobArr[1].substring(0,3);
				if(logger.isDebugEnabled())
				logger.debug("******************||||||The first index of Date of Birth|||||||||||||******* [0] [ "+DobArr[0]+"] [1]  "+DobArr[1]);

				subpersonalDetailTable.addCell(new Phrase(new Chunk("DOB:", fonthead)));
				subpersonalDetailTable.getDefaultCell().setBorderWidth(0);         
				subpersonalDetailTable.addCell(new Phrase(new Chunk(DobArr[0]+" "+DobArr[1]+" "+DobArr[2], font)));
				subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			} 
			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			subpersonalDetailTable.addCell(new Phrase(new Chunk("Name:", fonthead)));
			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);

			//logger.info("Errors if any ============================= "+ astrobean.getName().substring(1, astrobean.getName().length() - 1));
			String name=astrobean.getName();
			name=name.replaceAll("%20"," " );
			subpersonalDetailTable.addCell(new Phrase(new Chunk(name.trim(),font)));

			subpersonalDetailTable.getDefaultCell().setBorderWidth(0);
			subpersonalDetailTable.addCell(new Phrase(new Chunk(" ")));//To have a space


			personalDetailTable.addCell(subpersonalDetailTable);
			personalDetailTable.completeRow();

		} catch (DocumentException ex) {
			ex.printStackTrace();
		}

		return personalDetailTable;
	}//Personal detail

	public PdfPTable krishanKundliTable(AstroBean astrobean,ColorElement mycolor,boolean nakshatraPadam)
	{
		PdfPTable krishanTable=null;
		float[] wid;
		try {

			if(nakshatraPadam){
                        	krishanTable = new PdfPTable(10);
				//wid= new float[] {15,10,10,15,10,10,10,10,10};
				wid= new float[] {12,7,10,10,10,9,9,9,9,15};
			}
                	else{
                        	krishanTable = new PdfPTable(7);
		//		wid =new float[] {15,10,15,15,15,15,15};
				wid =new float[] {15,10,12,12,12,12,12};
			}
			Font krishanfont=new Font();
			Font krishanfontData=new Font();
		//	float wid[]={10,10,10,10,20,10,10,10,10,10};
		//	float wid[]={15,10,15,15,15,15,15};
			krishanTable.setWidths(wid);
			krishanfont.setSize(this.getTableHeadingfont());
			krishanfont.setColor(mycolor.fillColor(this.getTableHeadingColor()));
			krishanfontData.setSize(this.getTableDatafont());
			krishanfontData.setColor(mycolor.fillColor(this.getTableDataColor()));
			LinkedHashMap<String,PlanetDetailBean> planetHousesHashTable = null;
			PlanetDetailBean planetDetail=null;


			krishanTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
			krishanTable.addCell(new Phrase(new Chunk("Planet",krishanfont)));

			krishanTable.addCell(new Phrase(new Chunk("R/C",krishanfont)));
			krishanTable.addCell(new Phrase(new Chunk("Sign",krishanfont)));
			krishanTable.addCell(new Phrase(new Chunk("Degree",krishanfont)));
			if(nakshatraPadam){
				krishanTable.addCell(new Phrase(new Chunk("Nakshatra",krishanfont)));
				krishanTable.addCell(new Phrase(new Chunk("Padam",krishanfont)));
			}
			krishanTable.addCell(new Phrase(new Chunk("RL",krishanfont)));
			krishanTable.addCell(new Phrase(new Chunk("NL",krishanfont)));
			krishanTable.addCell(new Phrase(new Chunk("SL",krishanfont)));
			if(nakshatraPadam)
				krishanTable.addCell(new Phrase(new Chunk("Remarks",krishanfont)));
//			krishanTable.addCell(new Phrase(new Chunk("SS",krishanfont)));
			planetHousesHashTable= astrobean.getPlanetDetailHashTable();

		//	logger.info("planetHousesHashTable size>> "+planetHousesHashTable.size());	

//			Iterator it=planetHousesHashTable.values().iterator();
//			while(it.hasNext())
//			{
	//	for(int i=0;i<planets.length;i++)
		for(int i=0;i<planetsWithLagna.length;i++)
		{
			//	planetDetail=(PlanetDetailBean)it.next();
				planetDetail=planetHousesHashTable.get(planetsWithLagna[i]);
	//			logger.info("(planetDetail.getPlanetName()>> "+planetDetail.getPlanetName()+" PLANET> "+planetsWithLagna[i]); 
			//	if(!planetDetail.getPlanetName().equalsIgnoreCase("Lagna"))
			//	{

				krishanTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getPlanetName(),krishanfontData)));

				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getRC(),krishanfontData)));
				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getSignName(),krishanfontData)));
				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getDegree(),krishanfontData)));
				if(nakshatraPadam){
					krishanTable.addCell(new Phrase(new Chunk(planetDetail.getNakshatra(),krishanfontData)));
					krishanTable.addCell(new Phrase(new Chunk(planetDetail.getPadam(),krishanfontData)));
				}
				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getRL(),krishanfontData)));
				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getNL(),krishanfontData)));
				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getSL(),krishanfontData)));

				if(nakshatraPadam){
					if(planetDetail.getKeyword()!=null)
                                		krishanTable.addCell(new Phrase(new Chunk(planetDetail.getKeyword(),krishanfontData)));
					else
						krishanTable.addCell(new Phrase(new Chunk("",krishanfontData)));
				}
//				krishanTable.addCell(new Phrase(new Chunk(planetDetail.getSS(),krishanfontData)));

			//	}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return krishanTable;

	}//Krishan Paddti

	public PdfPTable fillBhavaTable(AstroBean astrobean,ColorElement mycolor,boolean nakshatraPadam,boolean houseName) {
		PdfPTable bhavaTable =null;
		float[] wid;
		try {

			if(nakshatraPadam){
				bhavaTable = new PdfPTable(9);
				//wid = new float[] {15, 10, 15, 15, 15, 15,15,15};
				wid = new float[] {15, 10, 12, 15, 10, 12,12,12,15};
			}
			else{
				bhavaTable = new PdfPTable(6);
				wid =new float[] {15, 10, 15, 15, 15, 15};
			}
			HouseDetailBean houseDetail = null;
			LinkedHashMap<String, HouseDetailBean> HouseDetailHashTable = null;

			Font bhavafont = new Font();
			//float wid[] = {10, 10, 10, 20, 10, 10, 10, 10, 10};
			//float wid[] = {15, 10, 15, 15, 15, 15,15,15};
			bhavaTable.setWidths(wid);
			bhavafont.setSize(this.getTableDatafont());
			bhavafont.setColor(mycolor.fillColor(this.getTableDataColor()));

			Font bhavafontHead = new Font();
			bhavafontHead.setSize(this.getTableHeadingfont());
			bhavafontHead.setColor(mycolor.fillColor(this.getTableHeadingColor()));

			bhavaTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
			bhavaTable.addCell(new Phrase(new Chunk("House Cusp", bhavafontHead)));
			bhavaTable.addCell(new Phrase(new Chunk("Sign", bhavafontHead)));
			bhavaTable.addCell(new Phrase(new Chunk("Degree", bhavafontHead)));
			if(nakshatraPadam){
				bhavaTable.addCell(new Phrase(new Chunk("Nakshatra", bhavafontHead)));
				bhavaTable.addCell(new Phrase(new Chunk("Padam", bhavafontHead)));
			}
			bhavaTable.addCell(new Phrase(new Chunk("RL", bhavafontHead)));
			bhavaTable.addCell(new Phrase(new Chunk("NL", bhavafontHead)));
			bhavaTable.addCell(new Phrase(new Chunk("SL", bhavafontHead)));
			if(nakshatraPadam)
				bhavaTable.addCell(new Phrase(new Chunk("Remarks", bhavafontHead)));
//			bhavaTable.addCell(new Phrase(new Chunk("SS", bhavafontHead)));
			HouseDetailHashTable = astrobean.getHouseDetailHashTable();
			for (int k = 1; k <= 12; k++) {
				if (HouseDetailHashTable.containsKey(Integer.toString(k))) {
					bhavaTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
					if(houseName)
						bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName()+" ("+cuspName[k-1]+")", bhavafont)));
					else
						bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getPlanetName(), bhavafont)));
					
					bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getSignName(), bhavafont)));
					bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getDegree(), bhavafont)));
					if(nakshatraPadam){
						bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getNakshatra(), bhavafont)));
						bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getPadam(), bhavafont)));
					}
					bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getRL(), bhavafont)));
					bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getNL(), bhavafont)));
					bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getSL(), bhavafont)));
//					bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getSS(), bhavafont)));
					if(nakshatraPadam){
						if(HouseDetailHashTable.get(Integer.toString(k)).getKeyword()!=null)
                                                	bhavaTable.addCell(new Phrase(new Chunk(HouseDetailHashTable.get(Integer.toString(k)).getKeyword(), bhavafont)));
						else
                                                	bhavaTable.addCell(new Phrase(new Chunk("", bhavafont)));

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bhavaTable;

	}//Bhava Detail

	//************************************Function Of next Pages******************************************************//    

	public PdfPTable fillAspecttable(DashaStrength dashaBean,ColorElement mycolor) {
		PdfPTable rahuAndKetutable = null;
		try {

			Font fontaspectHead = new Font();
			fontaspectHead.setSize(this.getTableHeadingfont());
			fontaspectHead.setColor(mycolor.fillColor(this.getTableHeadingColor()));

			Font fontaspect = new Font();
			fontaspect.setSize(this.getTableDatafont());
			fontaspect.setColor(mycolor.fillColor(this.getTableDataColor()));
			rahuAndKetutable = new PdfPTable(2);
			//  rahuAndKetutable.getDefaultCell().setBorderWidth(1);
			rahuAndKetutable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableHeadingBgcolor()));
			rahuAndKetutable.addCell(new Phrase(new Chunk("Rahu", fontaspectHead)));

			rahuAndKetutable.addCell(new Phrase(new Chunk("Ketu", fontaspectHead)));
			rahuAndKetutable.completeRow();


			PdfPTable aspectTable = new PdfPTable(2);
			float wid[] = {20, 70};
			aspectTable.setWidths(wid);

			PdfPTable sourceTableRahu = new PdfPTable(1);
			// sourceTableRahu.getDefaultCell().setBorderWidth(0);
			String RahuValue = dashaBean.getRahuSource();
			sourceTableRahu.addCell(new Phrase(new Chunk(RahuValue, fontaspect)));
			aspectTable.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			aspectTable.addCell(sourceTableRahu);
			PdfPTable sourceTableAspect = new PdfPTable(1);
			String abctest=dashaBean.getAspectRahu();
			logger.debug("HEREEE MEEEEEEEEEE>> "+abctest);
			String[] RahuAspect = dashaBean.getAspect(Planet.toPlanets("Rahu"));
			if (RahuAspect != null) {
				String RahuAspectstr = "";
				logger.debug("HEREEEEEE >>>>>> "+RahuAspect[0]);

				for (int i = 0; i < RahuAspect.length; i++) {
					RahuAspectstr +=RahuAspect[i]+"\n";
				}
				sourceTableAspect.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
				sourceTableAspect.addCell(new Phrase(new Chunk("A : " +RahuAspectstr, fontaspect)));
			} else {
				sourceTableAspect.addCell(new Phrase(new Chunk("A : "+"NA", fontaspect)));
			}


			String[] RahuConjuction = dashaBean.getConjuction(Planet.toPlanets("Rahu"));

			if (RahuConjuction != null) {
				String RahuConjuctionstr = "";

				for (int i = 0; i < RahuConjuction.length; i++) {
					RahuConjuctionstr += RahuConjuction[i]+"\n";

				}
				sourceTableAspect.addCell(new Phrase(new Chunk("C : " + RahuConjuctionstr, fontaspect)));
			} else {
				sourceTableAspect.addCell(new Phrase(new Chunk("C : NA", fontaspect)));
			}
			String RahuSignLoard = dashaBean.getSignLoard(Planet.toPlanets("Rahu"));
			String RahuRashiLoard = dashaBean.getRashiLoard(Planet.toPlanets("Rahu"));
			if (RahuSignLoard != null) {
				sourceTableAspect.addCell(new Phrase(new Chunk("S : " + RahuSignLoard, fontaspect)));
			} else {
				sourceTableAspect.addCell(new Phrase(new Chunk("S : NA", fontaspect)));

			}

			if (RahuRashiLoard != null) {
				sourceTableAspect.addCell(new Phrase(new Chunk("R : " + RahuRashiLoard, fontaspect)));

			} else {
				sourceTableAspect.addCell(new Phrase(new Chunk("R : NA", fontaspect)));
			}


			aspectTable.addCell(sourceTableAspect);
			rahuAndKetutable.addCell(aspectTable);
			//******************************************for the ketu ***************************************************//


			PdfPTable aspectTable2 = new PdfPTable(2);

			aspectTable2.setWidths(wid);
			PdfPTable sourceTableKetu = new PdfPTable(1);
			String KetuValue = dashaBean.getKetuSource();
			sourceTableKetu.addCell(new Phrase(new Chunk(KetuValue, fontaspect)));
			aspectTable2.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
			aspectTable2.addCell(sourceTableKetu);

			PdfPTable sourceTableKetuAspect = new PdfPTable(1);
			String[] KetuAspect = dashaBean.getAspect(Planet.toPlanets("Ketu"));
			if (KetuAspect != null) {
				String KetuAspectstr = "";
				logger.info("HEREEEEEE >>>>>> "+KetuAspect[0]);

				for (int i = 0; i < KetuAspect.length; i++) {
					KetuAspectstr += KetuAspect[i]+"\n";
				}
				sourceTableKetuAspect.getDefaultCell().setBackgroundColor(mycolor.fillColor(getTableDataBgcolor()));
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("A : " + KetuAspectstr, fontaspect)));
			} else {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("A : NA", fontaspect)));
			}


			String[] KetuConjuction = dashaBean.getConjuction(Planet.toPlanets("Ketu"));

			if (KetuConjuction != null) {
				String KetuConjuctionstr = "";

				for (int i = 0; i < KetuConjuction.length; i++) {
					KetuConjuctionstr += KetuConjuction[i]+"\n";

				}
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("C : " + KetuConjuctionstr, fontaspect)));
			} else {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("C : NA", fontaspect)));
			}
			String KetuSignLoard = dashaBean.getSignLoard(Planet.toPlanets("Ketu"));
			String KetuRashiLoard = dashaBean.getRashiLoard(Planet.toPlanets("Ketu"));


			if (KetuSignLoard != null) {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("S : " + KetuSignLoard, fontaspect)));
			} else {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("S : NA", fontaspect)));

			}



			if (KetuRashiLoard != null) {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("R : " + KetuRashiLoard, fontaspect)));

			} else {
				sourceTableKetuAspect.addCell(new Phrase(new Chunk("R : NA", fontaspect)));
			}


			aspectTable2.addCell(sourceTableKetuAspect);

			rahuAndKetutable.addCell(aspectTable2);
			rahuAndKetutable.completeRow();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return rahuAndKetutable;


	}//FillAspectTable

	public PdfPTable fillPersonalDetail(AstroBean dashaAstroBean,ColorElement mycolor,DashaStrength dashaBean) {
		PdfPTable personalTable = null;

		try {

			personalTable = new PdfPTable(2);
			float wid[] = {30, 70};
			String mdlAndAdl=null;
			String[] mdlAdlArr=null;
			String[] mdlArr=null;
			mdlAndAdl=dashaBean.getDashaStrengthData();


			Font fontPersonalHead = new Font();
			fontPersonalHead.setSize(this.getTableHeadingfont());
			fontPersonalHead.setColor(mycolor.fillColor(getTableDataColor()));

			Font fontPersonal = new Font();
			fontPersonal.setSize(this.getTableDatafont());
			fontPersonal.setColor(mycolor.fillColor(getTableDataColor()));

			PdfPTable presonName=new PdfPTable(1);
			String name=dashaAstroBean.getName();
			name=name.replaceAll("%20"," " );
			
			presonName.addCell(new Phrase(new Chunk("Name:"+ "  " +name.trim(), fontPersonalHead)));
			presonName.setTotalWidth(230);
			presonName.writeSelectedRows(0, -1, 320, (842 - 160), writer.getDirectContent());


			PdfPTable tabDOB = new PdfPTable(2);            
			String[] DobArr=new String[2];
			String Dob=null;
			Dob=dashaAstroBean.getDOB().substring(0, dashaAstroBean.getDOB().lastIndexOf(" "));
			if(!StringUtils.isBlank(Dob))
			{

				DobArr=Dob.split(" ");
				DobArr[1]=DobArr[1].substring(0,3);
				//    logger.info("******************||||||The first index of Date of Birth|||||||||||||*******"+DobArr[0]+DobArr[1]);


				tabDOB.addCell(new Phrase(new Chunk("DOB:"+"  "+DobArr[0]+" "+DobArr[1]+" "+DobArr[2], fontPersonalHead)));
				tabDOB.addCell(new Phrase(new Chunk("TOB:"+"  "+dashaAstroBean.getDOB().substring(dashaAstroBean.getDOB().lastIndexOf(" "), dashaAstroBean.getDOB().length()), fontPersonal)));

			}
			tabDOB.setTotalWidth(230);
			tabDOB.writeSelectedRows(0, -1, 320, (842 - 172), writer.getDirectContent());



			PdfPTable pobTable=new PdfPTable(1);
			pobTable.addCell(new Phrase(new Chunk("POB:"+"  "+dashaAstroBean.getPOB(), fontPersonalHead)));         
			pobTable.setTotalWidth(230);
			pobTable.writeSelectedRows(0, -1, 320, (842 - 184), writer.getDirectContent());




			if(!StringUtils.isBlank(mdlAndAdl))
			{

				mdlAdlArr=mdlAndAdl.split("_");

				mdlArr=mdlAdlArr[0].split("#");

				PdfPTable tabMDL = new PdfPTable(2);          
				tabMDL.addCell(new Phrase(new Chunk("MDL:"+ "  "+ mdlArr[0], fontPersonalHead)));
				tabMDL.addCell(new Phrase(new Chunk("Upto:"+"  "+ mdlArr[1], fontPersonal)));                 

				tabMDL.setTotalWidth(230);
				tabMDL.writeSelectedRows(0, -1, 320, (842 - 202), writer.getDirectContent());

				for(int i=1;i< mdlAdlArr.length;i++)
				{
					mdlArr=mdlAdlArr[i].split("#");

					personalTable.addCell(new Phrase(new Chunk("ADL:"+ "  "+mdlArr[0], fontPersonalHead)));
					personalTable.addCell(new Phrase(new Chunk("Upto:"+ "  "+mdlArr[1], fontPersonal)));
				}

			}

			else
			{
				if(logger.isDebugEnabled())
				logger.debug("*************************************String Of MDL And ADL is Blank");

			}


				} catch (Exception e) {
			e.printStackTrace();
		}

		return personalTable;

	}//fill personal detail

	//****************************************Function of Antardasha and sooksham pages***********************************//
	public void generateMahadashaAndAntardashaTable(Vector<MahaDashaBean> dasha, PdfContentByte canvas, String planetname, int tableX, float tableY,ColorElement mycolor) {
		Font font = new Font();
		font.setSize(this.getTableDatafont());
		font.setColor(mycolor.fillColor(getTableDataColor()));
		//Font font2 = new Font();
		//  font2.setSize(this.getTableDatafont());
		//  font2.setStyle("BOLD");
		Font font3 = new Font();
		font3.setSize(this.getSemiHeading());
		font3.setColor(mycolor.fillColor(getTableHeadingColor()));
		// RashiHouseBean rashiBean=null;
		MahaDashaBean mahadashabean = null;
		PdfPTable mahadashaTable = null;

		//	ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,new Phrase(planetname,font3),tableX+10,tableY+15,0);
		mahadashaTable = new PdfPTable(3);
		mahadashaTable.setTotalWidth(140);
		String[] monthArr = new String[2];
		String month = null;
		try {
			mahadashaTable.setWidths(new int[]{40, 50, 50});
		} catch (DocumentException ex) {
			ex.printStackTrace();
		}
		mahadashaTable.getDefaultCell().setBorderWidth(0);
		mahadashaTable.addCell(new Phrase(new Chunk("Antar", font3)));
		mahadashaTable.getDefaultCell().setBorderWidth(0);
		mahadashaTable.addCell(new Phrase(new Chunk("Beginning", font3)));
		mahadashaTable.getDefaultCell().setBorderWidth(0);
		mahadashaTable.addCell(new Phrase(new Chunk("Ending", font3)));


		if (!StringUtils.isBlank(dasha.get(0).getYear())) {
			// logger.info("**************||||||||||||||||||Antardashaaaaaaaaaaaaaaaaaaa||||||||||||||||||" + dasha.get(0).getYear());
			monthArr = dasha.get(0).getYear().split("_");

			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, new Phrase(monthArr[0], font3), tableX + 20, tableY + 15, 0);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(monthArr[1], font), tableX + 35, tableY + 4, 0);

		}


		for (int loop = 0; loop < dasha.size(); loop++) {
			// rashiBean=(RashiHouseBean)dasha.get(loop);
			mahadashabean = (MahaDashaBean) dasha.get(loop);
			drawLine1(canvas, tableX, tableY, tableX + 140, tableY);
			drawLine1(canvas, tableX, (tableY - 15), tableX + 140, (tableY - 15));
			drawLine1(canvas, tableX, (tableY - 125), tableX + 140, (tableY - 125));

			mahadashaTable.getDefaultCell().setBorderWidth(0);
			mahadashaTable.addCell(new Phrase(new Chunk(mahadashabean.getPlanetName(), font)));
			mahadashaTable.getDefaultCell().setBorderWidth(0);
			String tm=mahadashabean.getStartTime();
			if(tm!=null && tm!="")
			mahadashaTable.addCell(new Phrase(new Chunk(tm.split("  ")[0], font)));
			else
				mahadashaTable.addCell(new Phrase(new Chunk("", font)));
			
			mahadashaTable.getDefaultCell().setBorderWidth(0);
			tm=mahadashabean.getEndTime();
				if(tm!=null && tm!="")
				mahadashaTable.addCell(new Phrase(new Chunk(tm.split("  ")[0], font)));
				else
				mahadashaTable.addCell(new Phrase(new Chunk("", font)));
				
			
			
			

		}
		mahadashaTable.writeSelectedRows(0, -1, tableX, tableY, writer.getDirectContent());



	}

	
	// To Sort The date of Sooksham dasha
	
	
	public static Map sortByComparator(Map unsortMap) 
	{

		List list = new LinkedList(unsortMap.entrySet());

		//sort list based on comparator
		Collections.sort(list, new Comparator() 
		{
   public int compare(Object o1, Object o2) 
			{
				// return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
				return ((Comparable) ((Map.Entry) (o1)).getKey()).compareTo(((Map.Entry) (o2)).getKey());
			}
		});

    //put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) 
		{
			Map.Entry entry = (Map.Entry)it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}	
	
	//Sorting close Here
	
	
	
	
	
	
	public void drawLine1(PdfContentByte content, float x, float y, float width, float height) {
	//bharti 	content.saveState();
		content.moveTo(x, y);
		content.lineTo(width, height);
		content.stroke();
		content.closePath();
	//bharti	content.restoreState();
	}

	public void fillAntarDashaTable(PdfContentByte canvas,AstroBean astrobean,ColorElement mycolor) {
		/* Font font=new Font();
		   font.setSize(8);
		   Font font2=new Font();
		   font.setSize(10);*/
		LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = null;



		try {

			antardashaDetailHashTable = astrobean.getAntardashaDetailHashTable();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		Planet[] planetName = Planet.values();
		int j = 0;

		Vector<MahaDashaBean> antardashavector = null;


		Iterator it = antardashaDetailHashTable.values().iterator();
		while (it.hasNext()) {


			antardashavector = (Vector) it.next();
			// logger.info("Plane Name is"+planetName[j].toString());
			PdfPTable mahadashaTable = null;

				
			switch (j) {
					
				case 0:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 50, (toXYCord(210)),mycolor);


					break;

				case 1:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 225, (toXYCord(210)),mycolor);
					break;

				case 2:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 400, (toXYCord(210)),mycolor);
					break;

				case 3:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 50, toXYCord(380),mycolor);
					break;


				case 4:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 225, toXYCord(380),mycolor);
					break;

				case 5:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 400, toXYCord(380),mycolor);
					break;

				case 6:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 50, toXYCord(550),mycolor);
					break;

				case 7:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 225, toXYCord(550),mycolor);
					break;

				case 8:
					generateMahadashaAndAntardashaTable(antardashavector, canvas, antardashavector.get(j).getParent(), 400, toXYCord(550),mycolor);
					break;

			}
			j++;

		}
		//	}
}

public void generateSookshamDashaTable(PdfContentByte canvas, Vector<MahaDashaBean> sookshamDashaVector, float width, float height,ColorElement mycolor) {
	// logger.info("Height is"+height+"Width is"+width);
	try {
		MahaDashaBean mahadashabean = null;
		String startDate = null;
		String endDate = null;
		Font font = new Font();
		font.setSize(this.getTableDatafont());
		Font fontData = new Font();
		fontData.setSize(this.getTableDatafont());
		fontData.setColor(mycolor.fillColor(getTableDataColor()));
		Font font3 = new Font();
		font3.setSize(this.getSemiHeading());
		font3.setColor(mycolor.fillColor(getTableHeadingColor()));
		PdfPTable sookshamTable = new PdfPTable(2);
		sookshamTable.setTotalWidth(120);
		sookshamTable.setWidths(new int[]{50, 70});
		startDate = sookshamDashaVector.get(0).getStartTime();
		String stDate[] = startDate.split(" ");

		endDate = sookshamDashaVector.get(sookshamDashaVector.size() - 1).getEndTime();
		String enDate[] = endDate.split(" ");
		sookshamTable.getDefaultCell().setBorderWidth(0);
		sookshamTable.addCell(new Phrase(new Chunk("Beginning", font3)));
		sookshamTable.getDefaultCell().setBorderWidth(0);
// changes at 26 dec 2015
//		String tm[]=sookshamDashaVector.get(0).getStartTime().split("  ");
		
		sookshamTable.addCell(new Phrase(new Chunk(sookshamDashaVector.get(0).getStartTime().replace("  "," "), font)));
		sookshamTable.getDefaultCell().setBorderWidth(0);
		sookshamTable.addCell(new Phrase(new Chunk("Ending", font3)));
		sookshamTable.getDefaultCell().setBorderWidth(0);
		sookshamTable.addCell(new Phrase(new Chunk(sookshamDashaVector.get(sookshamDashaVector.size() - 1).getEndTime().replace("  "," "), font)));
		drawLine1(canvas, width, height, width + 120, height);
		drawLine1(canvas, width, (height - 30), width + 120, (height - 30));
		drawLine1(canvas, width, (height - 140), width + 120, (height - 140));
		for (int i = 0; i < sookshamDashaVector.size(); i++) {
			String parent = sookshamDashaVector.get(i).getParent();
			parent = parent.substring(0, 3);
			String child = sookshamDashaVector.get(i).getChild();
			child = child.substring(0, 3);
			String subParent = sookshamDashaVector.get(i).getSubChild();
			subParent = subParent.substring(0, 3);
			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(parent + "-" + child + "-" + subParent, font3), width + 30, height + 5, 0);

			sookshamTable.getDefaultCell().setBorderWidth(0);
			sookshamTable.addCell(new Phrase(new Chunk(sookshamDashaVector.get(i).getPlanetName(), fontData)));
			sookshamTable.getDefaultCell().setBorderWidth(0);
			sookshamTable.addCell(new Phrase(new Chunk(sookshamDashaVector.get(i).getStartTime().replace("  "," "), fontData)));

		}
		// logger.info("Height is"+height+"Width is"+width);
		sookshamTable.writeSelectedRows(0, -1, width, height, writer.getDirectContent());
		
		String proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "/kundliHttpserverNew.properties";
		Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);


	} catch (Exception ex) {
		ex.printStackTrace();

	}
}

public void fillSookshmaDashaTable(PdfContentByte canvas, AstroBean astrobean,ColorElement mycolor) throws ParserConfigurationException {

	LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable = null;

	try {
		Map<String,String> unsortMap = new HashMap<String,String>();
		sookshmadashaDetailHashTable = astrobean.getSookshmadashaDetailHashTable();
		Vector<MahaDashaBean> sookshamDashaVector = new Vector();

	//	Iterator it = sookshmadashaDetailHashTable.values().iterator();


		
		int counter = 0;
		
		//to get Hash map by Date wise
		
		
		for(Iterator<String> itr=sookshmadashaDetailHashTable.keySet().iterator();itr.hasNext();)
		{
			String keyValue=itr.next();
			if(logger.isDebugEnabled())
			logger.debug("Key Iteration Key Value [ "+keyValue+" ]");
			if(keyValue.trim().isEmpty()) {
				continue;
			}
			//logger.info("Date Sub String  Date [  "+keyValue.substring(0,10)+" ] Days [ "+keyValue.substring(0,2)+" ] Month [ "+keyValue.substring(3,5)+" ] Year [ "+keyValue.substring(6,10)+" ]");
			StringBuffer str=new StringBuffer();
			str.append(keyValue.substring(6,10)+keyValue.substring(3,5)+keyValue.substring(0,2));
			
			//logger.info("My String Buffer Date [ "+str.toString()+" ] Integer Value [ "+Integer.parseInt(str.toString())+" ]");
			
			unsortMap.put(str.toString(),keyValue);
			
		}

		Iterator iterator=unsortMap.entrySet().iterator();
		
		if(logger.isDebugEnabled())
		logger.debug("Unsorted Map Before..................");
		
		if(logger.isDebugEnabled())
		logger.debug("Sorted Map After ......");
		Map<String,String> sortedMap =  sortByComparator(unsortMap);
		
		//close hash map here
		
		
		for (Map.Entry entry : sortedMap.entrySet())
		{
			
			sookshamDashaVector = (Vector) sookshmadashaDetailHashTable.get(entry.getValue());
			counter++;

			switch (counter) {
				case 1:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 40, toXYCord(210),mycolor);  //210
					break;
				case 2:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 170, toXYCord(210),mycolor);
					break;
				case 3:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 300, toXYCord(210),mycolor);
					break;
				case 4:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 430, toXYCord(210),mycolor);
					break;
				case 5:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 40, toXYCord(380),mycolor);
					break;
				case 6:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 170, toXYCord(380),mycolor);
					break;
				case 7:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 300, toXYCord(380),mycolor);
					break;
				case 8:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 430, toXYCord(380),mycolor);
					break;
				case 9:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 40, toXYCord(550),mycolor);
					break;
				case 10:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 170, toXYCord(550),mycolor);
					break;
				case 11:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 300, toXYCord(550),mycolor);
					break;
				case 12:
					generateSookshamDashaTable(canvas, sookshamDashaVector, 430, toXYCord(550),mycolor);
					break;
			}
		}


	} catch (Exception ex) {
		ex.printStackTrace();
	}
}



	public void calculateTransitCusp(AstroBean astrobean, AstroBean transitAstrobean, HashMap<Integer, String> transitHouseDetailHashTable,Kundli transitCuspChartDetail) {
		logger.info("Inside calculateTransitCusp of FinalKundli");
        	try {
			String tmp = "";
			String[] planetArr=null;
			for(int i=1;i<=12;i++)
			{
				if(transitHouseDetailHashTable.containsKey(i)){
					tmp =transitHouseDetailHashTable.get(i);
                    		}
                     		else{
					tmp="";
                  		}
				planetArr=transitCuspChartDetail.getHouseData(i).split("_");
				logger.info("temp >>> HERE "+tmp+"   planetArr>> "+planetArr);
				if(planetArr.length>0){
					//tmp=planetArr[0]+"_"+tmp;
					tmp=" "+"_"+tmp;
				}
				transitCuspChartDetail.setHouseData(i,tmp);
			}

		}//try
		catch (Exception ex) {
                	ex.printStackTrace();
        	}//catch
	}//calculateTransitCusp
	






	public void fillAspectScoringMap(HashMap<String, Integer> map){
		logger.info("Inside fillAspectScoringMap");
	
		map.put("0",+9);		
		map.put("30",+3);		
		map.put("45",-3);
		map.put("60",+6);
		map.put("90",-6);
		map.put("120",+9);
		map.put("135",-3);
		map.put("150",+3);
		map.put("180",-9);

	}

    /*public static void main(String args[])
      {
      try {
      GenerateBirthCuspKundli displayNatalStr=new GenerateBirthCuspKundli("D:\\Astro.xml"); // URL String
      AstroBean astrobean= displayNatalStr.getNatalStrength();

      String dashaPlanetName="CURRENT";

      GenerateDashaKundli displayDashaStrength=new GenerateDashaKundli(astrobean,dashaPlanetName);
      AstroBean  dashaAstroBean =displayDashaStrength.getDashaStrength();

      GenerateDashaKundli displayDashaStrength1=new GenerateDashaKundli(astrobean,"Mars");
      AstroBean  dashaAstroBean1 =displayDashaStrength1.getDashaStrength();
      FinalKundli finalkundli = new FinalKundli();
      finalkundli.Kundli(astrobean,"FinalMahaDasha.pdf");
      } catch (Exception ex) {
      ex.printStackTrace();
      }
      }*/
}
