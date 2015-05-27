package ech98.echen.stackable;

/**
 * Created by echen on 5/26/15.
 */
public class FoodEssential_Object {
    private String upc;
    private String name;
    private String brand;

    public FoodEssential_Object(String upc, String name, String brand){
        this.upc = upc;
        this.name = name;
        this.brand = brand;
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
}
