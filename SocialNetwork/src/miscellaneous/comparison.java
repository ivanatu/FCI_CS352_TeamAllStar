package miscellaneous;

import java.util.Comparator;
import java.util.Map;

public class comparison implements Comparator<Map>
{   
   public int compare(Map p1 , Map p2) 
   {
       if(Integer.parseInt((String) p1.get("noOfPosts")) < Integer.parseInt((String) p2.get("noOfPosts")))
           return 1 ;
       return -1 ;
   }   
}
