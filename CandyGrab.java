import java.util.*;

public class CandyGrab
{
   public static void main (String[] args)
   {
      Scanner input = new Scanner(System.in);
      
      int candy = 11;
      
      System.out.print("Would you like to choose first? ");
      
      String response = input.next();
      
      if(response.equals("No") || response.equals("no"))
      {
         candy = candy - 2;
         int playerChoice = 0;
         
       
         System.out.println("The computer takes 2.");
         for(int x = 0; x < 3; x++)
         {
            System.out.print("There are " + candy + " on the table. \nHow many candies would you like to take.");
            playerChoice = input.nextInt();
            candy = candy - playerChoice;
         
            System.out.println("There are " + candy + " on the table. \nThe computer takes " + (3 - playerChoice) + ".");
         
            candy = candy - (3 - playerChoice);
         }
         System.out.println("There are " + candy + " on the table.");
         System.out.println("The computer wins.");  
      }
      else if(response.equals("Yes") || response.equals("yes"))
      {
         System.out.print("There are " + candy + " on the table. \nHow many candies would you like to take.");
         int playerChoice = input.nextInt();
         candy = candy - playerChoice;
         if(playerChoice == 1)
         {
            candy = candy - 1;
            System.out.println("The computer takes 1.");
            while(candy > 2)
            {
               System.out.print("There are " + candy + " on the table. \nHow many candies would you like to take.");
               playerChoice = input.nextInt();
               candy = candy - playerChoice;
            
               System.out.println("There are " + candy + " on the table. \nThe computer takes " + (3 - playerChoice) + ".");
            
               candy = candy - (3 - playerChoice);
            }
            System.out.println("There are " + candy + " on the table.");
            System.out.println("The computer wins."); 
         }
         else
         {
            playerChoice = 2;
            System.out.println("There are " + candy + " on the table. \nThe computer takes 2.");
            candy = candy - 2;
            while(candy > 2)
            {
               System.out.print("There are " + candy + " on the table. \nHow many candies would you like to take.");
               playerChoice = input.nextInt();
               candy = candy - playerChoice;
               
               if(candy == 2)
                  playerChoice = 2;
               
               System.out.println("There are " + candy + " on the table. \nThe computer takes " + (playerChoice) + ".");
               
               if(candy - playerChoice >= 0)
                  candy = candy - playerChoice;
               else
                  candy =candy - 1;
            }
            System.out.println("There are " + candy + " on the table.");
            if(candy == 0)
               System.out.println("The computer wins."); 
            else
               System.out.println("You win.");
         }
      
      }
   }
   
   
}