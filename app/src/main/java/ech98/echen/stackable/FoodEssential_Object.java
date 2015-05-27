package ech98.echen.stackable;

/**
 * Created by echen on 5/26/15.
 */
public class FoodEssential_Object {
    private String upc;
    private String name;
    private String brand;
    private String image;

    public FoodEssential_Object(String upc, String name, String brand, String image){
        this.upc = upc;
        this.name = name;
        this.brand = brand;
        this.image = image;
    }
    public String getUpc(){
        return this.upc;
    }
    public String getName(){
        return this.name;
    }
    public String getBrand(){
        return this.brand;
    }
    public String getImage(){
        return this.image;
    }
}
