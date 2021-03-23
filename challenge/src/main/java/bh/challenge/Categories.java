package bh.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.*;

public class Categories {
	
	final String URL_site="https://api.mercadolibre.com/sites";
	private String ctryCode="";
    private String category="";
    private String url="";
    private String ID="";
    private List<String> listOfMarcas = new ArrayList<>();
    
    // Constantes
    final static String _RESULTS_ 		= "results";
    final static String _CONDITION_ 	= "condition";
    final static String _NEW_			= "new";
    final static String _ATTRIBUTES_ 	= "attributes";
    final static String _PRICE_			= "price";
    
	public Categories(String category, String country){
        this.category = category;   
        this.ID = getIdFromML();
        this.ctryCode=country; // Código de país -> Argentina: MLA
    }
	
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}



    public String getLink(){
    	
    	// https://api.mercadolibre.com/sites
    	// MLA -> Argentina
    	
    	// https://api.mercadolibre.com/sites/MLA/categories
    	// MLA1743 -> Autos/Motos/etc.
    	
    	// https://api.mercadolibre.com/categories/MLA1743
    	// MLA1763 -> Motos
    	
    	// curl -X GET -H "Accept: application/json" -H 'Authorization: Bearer $ACCESS_TOKEN' https://api.mercadolibre.com/sites/MLA/search?category=MLA1763

    	
        switch(this.category){
            case "Motos":   this.url="https://api.mercadolibre.com/sites/"+this.ctryCode+"/search?category="+this.ID;
                            break;
            default:        break;
        }
        return this.url;
    }

    public String getIdFromML(){
        switch(this.category){
            case "Motos":   this.ID="MLA1763"; // En este espacio tendríamos que hacer un search del ID.
                            break;
            default:        break;
        }
        return this.ID;
    }
    
    public String getInfo() throws IOException{
        String urlToRead = this.getLink();  		// Get Link for "category"
        String id="";                           	// Create string "id"

        //System.out.println(urlToRead);
        StringBuilder result = new StringBuilder();	// List of "Motos"
        URL url = new URL(urlToRead);           	// Create URL

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();	// Open connection for URL
        conn.setRequestMethod("GET");										// GET service
        conn.setRequestProperty("Accept","application/json");				// JSON formar
        conn.setRequestProperty("Authorization","Bearer $ACCESS_TOKEN");	// Requested by ML
        
        // Getting the information from connection
        try (var reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        
        return result.toString();		// Return information
    }
    
    public List<String> getMeanPrice(String info){
    	List<String> precios = new ArrayList<>();
    	String marca="";
    	JSONObject json = new JSONObject(info);
    	HashMap<String, Marca> hashmap = new HashMap<String,Marca>();
    	Double price = 0.0;
    	
    	// calculate mean value for each brand
    	
    	JSONArray item = (JSONArray) json.get(_RESULTS_);				// Get results from info

    	for(int i = 0;i<item.length();i++) {
            JSONObject obj = item.getJSONObject(i);						// Create JSON objecto for each record in info
            
            String condition = obj.get(_CONDITION_).toString();			// Get condition NEW/USED
                        
            if (!condition.equals(_NEW_)) {continue;}					// If it's not new, then we continue with the next one
                        
            JSONArray attributes = (JSONArray) obj.get(_ATTRIBUTES_);	// If it's new, then we get the attributes
            
            marca = getBrand(attributes); 								// Get brand from item
            price = Double.parseDouble(obj.get(_PRICE_).toString());	// Get price from item
            		
            if(!listOfMarcas.contains(marca)) {
            	hashmap.put(marca, new Marca(marca,price));
            	listOfMarcas.add(marca); 								// If does not exist, then I added to the list
            } else {
            	Marca mrc = hashmap.get(marca);							// Else I increase the counter and sum price
            	mrc.increaseCounter();
            	mrc.increasePrice(price);
            }
        }
    	
        for(String m:listOfMarcas) {
        	Marca mrc = hashmap.get(m);
        	precios.add("Para la marca " + m + ", el precio promedio es: " + mrc.getPrice()/mrc.getCounter());
        }
        
    	return precios;
    	
    }
    
    
    public String getBrand(JSONArray attributes){
    	// For each attribute, search brand and add to List
    	String marca = "";
    	boolean check = false;
    	
    	for(int j = 0;j<attributes.length();j++) {
            JSONObject att = attributes.getJSONObject(j);
            //System.out.println(att.toString());
            
            try{
            	check = att.get("name").toString().equals("Marca"); // Tomo el registro que posee la marca
            	
            	if(check) {
            		marca = att.get("value_name").toString().toLowerCase();            		
            	}
            }
            catch(Exception e){
            	continue;
            }
        }
    	
    	return marca;
    }
    
    public List<String> getListOfBrands(){
    	return listOfMarcas;
    }

    
}











