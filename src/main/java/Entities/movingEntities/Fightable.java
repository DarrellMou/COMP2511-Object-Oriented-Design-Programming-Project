package Entities.movingEntities;

public interface Fightable {
    /**
     * Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) /
     * 10)
     * <p>
     * Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) /
     * 5)
     * 
     * @return
     */
    public double calculateDamage();

    public void takeDamage(double Damage);
}
