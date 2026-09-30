package com.foundations.calculator.core;

import java.util.Objects;
import java.util.function.Supplier;

/** Holds an independent snapshot only when a value changes. Never owns a world or a player. */
public final class ChangeStamp<T> {
    private T snapshot;
    private boolean initialized;
    public boolean differs(T current) { return !initialized || !Objects.equals(snapshot, current); }
    public void capture(Supplier<T> copy) { snapshot = copy.get(); initialized = true; }
    public void clear() { snapshot = null; initialized = false; }
}
