/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author skip
 */
public class TestRobots {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /* Principe du test : On teste chaque robot sur différents terrains avec différentes vitesses.
        * les parties commentées génèrent des exceptions*/
               
        Case foret = new Case(2,2,NatureTerrain.FORET);
        Case eau = new Case(5,3, NatureTerrain.EAU);
        Case libre = new Case(5,5, NatureTerrain.TERRAIN_LIBRE);
        Case roche = new Case(8,5, NatureTerrain.ROCHE);
        Case habitat = new Case(9,0, NatureTerrain.HABITAT);
        
          /*********/
         /* DRONE */
        /*********/
        
        
        Drone d1 = new Drone(foret);
        Drone d2 = new Drone(115, eau);
        
        System.out.println("pour d1 "+d1.toString());
        System.out.println("pour d2 " + d2.toString());
       
        
          /*****************/
         /* ROBOT A ROUES */
        /*****************/
        
        RobotARoues r1 = new RobotARoues(libre);
        //RobotARoues r2 = new RobotARoues(100, foret);
        RobotARoues r2 = new RobotARoues(100, habitat);
       
       System.out.println("pour r1 " + r1.toString() );
       System.out.println("pour r2 " + r2.toString() );
       
       
          /*********************/
         /* ROBOT A CHENILLES */
        /*********************/
       
       RobotAChenilles r3 = new RobotAChenilles(libre);
       //RobotAChenilles r4 = new RobotAChenilles(50, eau);
       RobotAChenilles r4 = new RobotAChenilles(50, foret);
       System.out.println("pour r3 " + r3.toString() );
       System.out.println("pour r4 " + r4.toString() );

       
          /******************/
         /* ROBOT A PATTES */
        /******************/       
       RobotAPattes r5 = new RobotAPattes(foret);
       //RobotAPattes r6 = new RobotAPattes(eau);
       RobotAPattes r6 = new RobotAPattes(roche);
       System.out.println("pour r3 " + r5.toString() );
       System.out.println("pour r4 " + r6.toString() );
    }
    
}
