package com.pragmatic;

import org.testng.annotations.Test;


 
public class TestForServices {

    @Test
    public void testCustomerService () {
        try{
         String expected = "Null";
         String email =Null;
        
        assertEquals(expected, Customer.findCustomerByEmail(String email));

        System.out.println("pass customer service");
    }catch(Exception e){
        System.out.println(e);
    }

    }

    @Test
    public void testAdminService () {
          try{
         String expected = "Null";
         String email =Null;
        
        assertEquals(expected, Customer.findAdminByEmail(String email));

        System.out.println("pass admin service");
    }catch(Exception e){
        System.out.println(e);
      }


    }

    @Test
    public void testVehicleService () {
 try{
         String expected = "Null";
         String name =Null;
        
        assertEquals(expected, Customer.findVehicleByName(String name,String customer));

        System.out.println("pass vehicle service");
    }catch(Exception e){
        System.out.println(e);
      }


        System.out.println("pass customer service");
    }

    @Test
    public void testPartService () {
        System.out.println("pass customer service");
    }

}
