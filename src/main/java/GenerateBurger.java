import java.util.ArrayList;

public class GenerateBurger {


    public GenerateBurger() {
    }


    public ArrayList<String> generateIngridientsList(ArrayList<String> s){
        ArrayList<String> newBurger = new ArrayList<>();
        int size = Utils.rnd(s.size());
        for (int i = 1; i < size; i++){
            int k = Utils.rnd(s.size()-1);
            //newBurger.add("\"" + s.get(k) + "\"");
            newBurger.add(s.get(k));
        }
        return newBurger;
    }

    public ArrayList<String> generateNullBurger(){
        ArrayList<String> newBurger = new ArrayList<>();
        return newBurger;
    }

    public ArrayList<String> generateWrongIngridient(ArrayList<String> s){
        ArrayList<String> newBurger = new ArrayList<>();

        newBurger.add(s.get(0)+"!123");

        return newBurger;
    }


}
