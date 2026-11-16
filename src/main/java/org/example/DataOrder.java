package org.example;

import java.util.List;

public class DataOrder {
    private List<String> ingredients;

    public DataOrder(){
    }

    public DataOrder(List<String> ingredients){

         this.ingredients=ingredients;
    }

    public List<String> getIngredients() {

         return ingredients;
    }

    public void setIngredients(List<String> ingredients) {

         this.ingredients = ingredients;
    }
}
