package cn.tesseract.union.api.rusted;

import cn.tesseract.union.accessor.PlayerAccessor;
import cn.tesseract.union.accessor.UnitAccessor;
import com.corrodinggames.rts.union.game.units.Unit;

public class RustedUnit extends RustedWrapper<Unit> {
    private static final RustedUnitList units = new RustedUnitList(Unit.field_1908);

    public RustedUnit(Unit instance) {
        super(instance);
    }

    public static RustedUnit warp(Unit inner) {
        return inner == null ? null : ((UnitAccessor) inner).get_wrapper();
    }

    public static RustedUnitList getUnitList() {
        return units;
    }

    public RustedPlayer getPlayer() {
        return ((PlayerAccessor) inner.player).get_wrapper();
    }

    public long getId() {
        return inner.id;
    }

    public String getName() {
        return inner.method_1059().getUnitName();
    }

    public String getMovementType() {
        return inner.getMovementType().toString();
    }

    public float getX() {
        return inner.x;
    }

    public float getY() {
        return inner.y;
    }

    public int getTimeAlive() {
        return inner.timeAlive;
    }

    public int getAmmo() {
        return inner.ammo;
    }

    public void setAmmo(int ammo) {
        inner.ammo = ammo;
    }

    public float getHp() {
        return inner.hp;
    }

    public void setHp(float hp) {
        inner.hp = hp;
    }

    public float getMaxHp() {
        return inner.maxHp;
    }

    public void setMaxHp(float maxHp) {
        inner.maxHp = maxHp;
    }

    public float getShield() {
        return inner.shield;
    }

    public void setShield(float shield) {
        inner.shield = shield;
    }

    public float getMaxShield() {
        return inner.maxShield;
    }

    public void setMaxShield(float maxShield) {
        inner.maxShield = maxShield;
    }

    public float getDir() {
        return inner.dir;
    }

    public void setDir(float dir) {
        inner.dir = dir;
    }

    public void remove() {
        inner.method_946();
    }
}
