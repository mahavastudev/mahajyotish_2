package com.telemune.mobileAstro;
import java.util.*;

public class TreeSorting {

        public static void main(String[] args) {

        Map<String,String> unsortMap = new HashMap<String,String>();
        // ValueComparator bvc =  new ValueComparator(map);
        //TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);

        LinkedHashMap<String, String> linked=new LinkedHashMap<String, String>();

                linked.put("08-05-2012 02:58", "1");
                linked.put("29-05-2012 10:19", "2");
                linked.put("23-06-2012 19:03", "3");
                linked.put("11-07-2012 13:10", "4");
                linked.put("26-08-2012 04:53", "5");
                linked.put("05-10-2012 18:52", "6");
                linked.put("22-11-2012 23:28", "7");
                linked.put("05-01-2013 02:19", "8");
                linked.put("22-01-2013 20:26", "9");
                linked.put("14-03-2013 13:55", "10");
                linked.put("29-03-2013 19:09", "11");
                linked.put("11-04-2013 05:26", "11");


                for(Iterator<String> itr=linked.keySet().iterator();itr.hasNext();)
                {
                        String keyValue=itr.next();
                        //System.out.println("Key Iteration Key Value [ "+keyValue+" ]");

                        //System.out.println("Date Sub String  Date [  "+keyValue.substring(0,10)+" ] Days [ "+keyValue.substring(0,2)+" ] Month [ "+keyValue.substring(3,5)+" ] Year [ "+keyValue.substring(6,10)+" ]");
                        StringBuffer str=new StringBuffer();
                        str.append(keyValue.substring(6,10)+keyValue.substring(3,5)+keyValue.substring(0,2));

                        //System.out.println("My String Buffer Date [ "+str.toString()+" ] Integer Value [ "+Integer.parseInt(str.toString())+" ]");

                        unsortMap.put(str.toString(),keyValue);

                }

                Iterator iterator=unsortMap.entrySet().iterator();

                System.out.println("Unsorted Map Before..................");
                for (Map.Entry entry : unsortMap.entrySet())
                {
                        System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                }

                System.out.println("Sorted Map After ......");
                Map<String,String> sortedMap =  sortByComparator(unsortMap);

                for (Map.Entry entry : sortedMap.entrySet())
                {
                        System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                }
}


        private static Map sortByComparator(Map unsortMap)
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
}

