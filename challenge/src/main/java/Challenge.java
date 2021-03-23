
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;
import bh.challenge.Categories;
import org.apache.log4j.*; 

public class Challenge{

	static Map<String, String> map = new HashMap<String, String>();
	private static final Logger logger = LogManager.getLogger(Challenge.class);  

    public static void main(String[] args) throws IOException {
 
    	inicializacion();
        
    	// Create Object
    	String category="Motos"; 
    	Categories motos = new Categories(category, map.get("ARG")); // Create object Motos for ARG
    	logger.info("Creation of object: "+category);
    	
    	// Get info from Object
        String id = motos.getID();							// Get ID
        String info = motos.getInfo();						// Get info
        List<String> meanPrice = motos.getMeanPrice(info); 	// Get Mean Price
       

        
        
        // Show info
    	logger.info("Getting ID for category "+category+": "+id);
    	//logger.info("Getting information: "+category);
    	logger.info("La lista de marcas es: ");
        for(String marca:motos.getListOfBrands()) {
        	logger.info(marca);
        }
        
    	logger.info("La precios promedios por marca es:");
        for(String price:meanPrice) {
        	logger.info(price);
        }
        

 
    }
    
    public static void inicializacion() {
    	BasicConfigurator.configure();
    	logger.info("Inicialization running ... ");
    	map.put("ARG", "MLA");
    }
   
}
