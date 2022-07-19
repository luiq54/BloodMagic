package wayoftime.bloodmagic.entity.mob;

public interface IDemon {
    void setSummonedConditions();

    boolean isAggro();

    void setAggro(boolean aggro);

    boolean getDoesDropCrystal();

    void setDropCrystal(boolean crystal);
}
