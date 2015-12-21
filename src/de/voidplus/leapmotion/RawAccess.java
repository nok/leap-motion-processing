package de.voidplus.leapmotion;

public interface RawAccess<E> {
    boolean isValid();
    E getRaw();
}
