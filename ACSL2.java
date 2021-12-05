// Name: Daniela Munoz
// Lab: ACSL 2 / Difference Factor (ADF)

import java.util.*;

public class ACSL2
{
   public static void main (String[] args)
   {
      Scanner input = new Scanner(System.in);
      
      String one = new String();
      String two = new String();
      
      for(int x = 0; x < 5; x++)
      {
         System.out.print("First String: ");
      
         one = input.nextLine();
      
         System.out.print("Second String: ");
      
         two = input.nextLine();
      
         for ( int i = 0; i < one.length(); i++) 
         {
            one = one.replaceAll("[^a-zA-Z]", "").toUpperCase();
         }
         
         for ( int i = 0; i < two.length(); i++) 
         {
            two = two.replaceAll("[^a-zA-Z]", "").toUpperCase();
         }
         
         System.out.println(diff(one,two));
      }
   }
   
   public static String sub (String s1, String s2)
   {
      
      int m = s1.length();
      int n = s2.length();
      int max = 0;
      int[][] dp = new int[m][n];
      int endIndex=-1;
      
      for(int i=0; i<m; i++)
      {
         for(int j=0; j<n; j++)
         {
            if(s1.charAt(i) == s2.charAt(j))
            {
            
               if(i==0 || j==0)
               {
                  dp[i][j]=1;
               }
               else
               {
                  dp[i][j] = dp[i-1][j-1]+1;
               }
            
               if(max < dp[i][j])
               {
                  max = dp[i][j];
                  endIndex=i;
               }
               else if (max == dp[i][j])
               {
                  if((s1.substring(endIndex-max+1, endIndex+1)).compareTo(s1.substring(i-dp[i][j]+1, i+1)) > 0)
                  {
                     max = dp[i][j];
                     endIndex=i;
                  }
                  else if((s1.substring(endIndex-max+1, endIndex+1)).compareTo(s1.substring(i-dp[i][j]+1, i+1)) == 0)
                  {
                     if(s1.indexOf(s1.substring(endIndex-max+1, endIndex+1)) > s1.indexOf(s1.substring(i-dp[i][j]+1, i+1)))
                     {
                        max = dp[i][j];
                        endIndex=i;
                     }
                  }
               
                  
               }
            }
         
         }
      }
      return s1.substring(endIndex-max+1,endIndex+1);
   }
   
   public static int diff(String s1, String s2)
   {
      int d = 0;
      String sub = sub(s1,s2); 
      
      if(sub == null)
      {
         return 0;
      }
      else
      {
         d+=sub.length();
      }
      
      ArrayList<String> s = new ArrayList<String>();
      
      if(s1.indexOf(sub) != 0 && ((s1.indexOf(sub)+sub.length()) < s1.length()) && ((s2.indexOf(sub)+sub.length()) < s2.length()))
      {
         s.add(s1.substring(0,s1.indexOf(sub)));
         s.add(s2.substring(0,s2.indexOf(sub)));
         s.add(s1.substring(s1.indexOf(sub)+sub.length()));
         s.add(s2.substring(s2.indexOf(sub)+sub.length()));
      }
      else if(s1.indexOf(sub) == 0)
      {
         s.add(s1.substring(s1.indexOf(sub)+sub.length()));
         s.add(s2.substring(s2.indexOf(sub)+sub.length()));
      }
      else if((s1.indexOf(sub)+sub.length()) == s1.length() && (s2.indexOf(sub)+sub.length()) == s2.length())
      {
         s.add(s1.substring(0,s1.indexOf(sub)));
         s.add(s2.substring(0,s2.indexOf(sub)));
      }
      
      int temp = 2;
      
      for(int x = 0; x < temp-1; x = x+2)
      {
         String substring = sub(s.get(x), s.get(x+1));
         
         s.add(s.get(x).substring(0,s.get(x).indexOf(substring)));
         s.add(s.get(x+1).substring(0,s.get(x+1).indexOf(substring)));
         s.add(s.get(x).substring(s.get(x).indexOf(substring)+substring.length()));
         s.add(s.get(x+1).substring(s.get(x+1).indexOf(substring)+substring.length()));
         
         d+=substring.length();
         
         if(!(substring.equals("")))
         {
            temp +=2;
         }
      }
      return d;
   }
   
}