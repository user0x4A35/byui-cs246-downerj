import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player {
    private String name;
    private int health;
    private int mana;
    private int gold;
    private Map<String, Integer> equipment;
    
    public Player() {
        setName("Undefined");
        setHealth(100);
        setMana(0);
        setGold(0);
        equipment = new HashMap<String, Integer>();
    }
    
    public Player(String name, int health, int mana, int gold) {
        setName(name);
        setHealth(health);
        setMana(mana);
        setGold(gold);
        equipment = new HashMap<String, Integer>();
    }
    
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getMana() {
        return mana;
    }
    
    public int getGold() {
        return gold;
    }
    
    public int getEquipmentItemCount(String name) {
        int count;
        Integer countObj = equipment.get(name.toLowerCase());
        if (countObj == null) {
            count = 0;
        } else {
            count = (int) countObj;
        }

        return count;
    }

    public boolean hasEquipmentItem(String name) {
        Integer countObj = equipment.get(name.toLowerCase());
        boolean hasKey = (countObj != null);
        return hasKey;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public void setMana(int mana) {
        this.mana = mana;
    }
    
    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addEquipment(String name, int count) {
        equipment.put(name.toLowerCase(), (Integer) count);
    }

    public void incrementEquipmentItem(String name) {
        Integer countObj = equipment.get(name.toLowerCase());
        if (countObj == null) {
            equipment.put(name, (Integer) 1);
        } else {
            countObj++;
            equipment.put(name, countObj);
        }
    }

    public void decrementEquipmentItem(String name) {
        Integer countObj = equipment.get(name.toLowerCase());
        if (countObj != null && countObj > 0) {
            countObj--;
            equipment.put(name, countObj);
        }
    }

    public void removeEquipment(String name) {
        equipment.remove(name.toLowerCase());
    }
    
    public String getEquipmentAsString() {
        StringBuilder stream = new StringBuilder();
        Set<String> equipNames = equipment.keySet();
        for (String name : equipNames) {
            int count = (int) equipment.get(name);
            stream.append(String.format("<%s %d> ",
                name,
                count
            ));
        }

        return stream.toString();
    }

    @Override
    public String toString() {
        return String.format("Player \"%s\" <health %d> <mana %d> <gold %d> %s",
            name,
            health,
            mana,
            gold,
            getEquipmentAsString()
        );
    }
}
