package net.neoforged.bus.api; public interface ICancellableEvent {default void setCanceled(boolean v){((Event)this).cancelled=v;} default boolean isCanceled(){return ((Event)this).cancelled;} }
