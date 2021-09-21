
package com.telemune.mobileAstro;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ReadPropertyFile
{
public static Hashtable<String,String> readPropery(String path)
        {
                FileInputStream fis1 =null;
                Hashtable<String,String> propList=new Hashtable<String,String>();
                Properties properties = new Properties();
                try
                {
                        String key="NA",value="NA";
                        fis1 = new FileInputStream(path);
                        properties.load(fis1);
                        Set KEYS=properties.keySet();
                        Iterator it=KEYS.iterator();
                        while(it.hasNext())
                        {
                                key=(String)it.next();
                                value=properties.getProperty(key);
                                //System.out.println(key+"=="+value);
                                propList.put(key,value);
                        }
                        return propList;
                }
                catch(Exception e)
                {
                        System.out.println("Exception in readProperty "+e.toString());
                        return null;
                }
                finally
                {
                        try{
                                fis1.close();
                        }
                        catch(Exception fin)
                        {
                                System.out.println("Exception in closing fis1 "+fin.toString());
                        }
                }

        }
}
