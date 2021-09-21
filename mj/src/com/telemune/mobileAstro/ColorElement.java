package com.telemune.mobileAstro;


import com.itextpdf.text.BaseColor;
import java.awt.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SUCCESS
 */
  public class ColorElement {

      int tableColumnArr[]={30,30,30,0};

    public int[] getTableColumnArr() {
        return tableColumnArr;
    }

    public void setTableColumnArr(int[] tableColumnArr) {
        this.tableColumnArr = tableColumnArr;
    }
      String Color="Black";
      String tableHeaderColor="Black";
      String tableDataColor="Black";
      String textBgColor="White";
      String tableBgColor="White";
      String tableBorderColor="Black";
      String tableDatabgColor="Black";
      String tableHeaderbgColor="White";
     
      Float textsize=12f;

    public String getTableHeaderbgColor() {
        return tableHeaderbgColor;
    }

    public void setTableHeaderbgColor(String tableHeaderbgColor) {
        this.tableHeaderbgColor = tableHeaderbgColor;
    }

     public Float getTextsize() {
        return textsize;
    }

    public void setTextsize(Float textsize) {
        this.textsize = textsize;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getTableBgColor() {
        return tableBgColor;
    }

    public void setTableBgColor(String tableBgColor) {
        this.tableBgColor = tableBgColor;
    }

    public String getTableBorderColor() {
        return tableBorderColor;
    }

    public void setTableBorderColor(String tableBorderColor) {
        this.tableBorderColor = tableBorderColor;
    }

    public String getTableDataColor() {
        return tableDataColor;
    }

    public void setTableDataColor(String tableDataColor) {
        this.tableDataColor = tableDataColor;
    }

    public String getTableDatabgColor() {
        return tableDatabgColor;
    }

    public void setTableDatabgColor(String tableDatabgColor) {
        this.tableDatabgColor = tableDatabgColor;
    }

    public String getTableHeaderColor() {
        return tableHeaderColor;
    }

    public void setTableHeaderColor(String tableHeaderColor) {
        this.tableHeaderColor = tableHeaderColor;
    }

    public String getTextBgColor() {
        return textBgColor;
    }

    public void setTextBgColor(String textBgColor) {
        this.textBgColor = textBgColor;
    }


    public Color lookupColor (String color) {
        String s = color.toLowerCase();
                if (s.equals("black")) {
           return java.awt.Color.black;
       } else if (s.equals("blue")) {
            return java.awt.Color.blue;
        } else if (s.equals("cyan")) {
            return java.awt.Color.cyan;
       } else if (s.equals("darkgray")) {
            return java.awt.Color.darkGray;
        } else if (s.equals("darkgrey")) {
            return java.awt.Color.darkGray;
        } else if (s.equals("gray")) {
            return java.awt.Color.gray;
        } else if (s.equals("grey")) {
            return java.awt.Color.gray;
        } else if (s.equals("green")) {
            return java.awt.Color.green;
        } else if (s.equals("lightgray")) {
            return java.awt.Color.lightGray;
        } else if (s.equals("lightgrey")) {
            return java.awt.Color.lightGray;
        } else if (s.equals("magenta")) {
            return java.awt.Color.magenta;
        } else if (s.equals("orange")) {
            return java.awt.Color.orange;
        } else if (s.equals("pink")) {
            return java.awt.Color.pink;
        } else if (s.equals("red")) {
            return java.awt.Color.red;
        } else if (s.equals("white")) {
            return java.awt.Color.white;
        } else if (s.equals("yellow")) {
            return java.awt.Color.yellow;
        } else {
            return java.awt.Color.black;
        }
    }

public BaseColor fillColor(String basecolor)
{

    String str = basecolor.toUpperCase();
                if (str.equals("BLACK")) {
           return BaseColor.BLACK;
       } else if (str.equals("BLUE")) {
            return BaseColor.BLUE;
        } else if (str.equals("CYAN")) {
            return BaseColor.CYAN;
       } else if (str.equals("DARKGRAY")) {
            return BaseColor.DARK_GRAY;
        } else if (str.equals("GRAY")) {
            return BaseColor.GRAY;
        } else if (str.equals("GREEN")) {
            return BaseColor.GREEN;
        } else if (str.equals("LIGHTGRAY")) {
            return BaseColor.LIGHT_GRAY;
        } else if (str.equals("MAGENTA")) {
            return BaseColor.MAGENTA;
        } else if (str.equals("ORANGE")) {
            return BaseColor.ORANGE;
        } else if (str.equals("PINK")) {
            return BaseColor.PINK;
        } else if (str.equals("RED")) {
            return BaseColor.RED;
        } else if (str.equals("WHITE")) {
            return BaseColor.WHITE;
        } else if (str.equals("YELLOW")) {
            return BaseColor.YELLOW;
        }
        else
        {
        return BaseColor.BLACK;
        }

}

  
}
