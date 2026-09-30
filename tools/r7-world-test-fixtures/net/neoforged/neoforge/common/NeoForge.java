package net.neoforged.neoforge.common;
import java.util.*;import java.util.function.Consumer;import net.neoforged.bus.api.Event;
public class NeoForge {public static final Bus EVENT_BUS=new Bus();public static class Bus {public final List<Consumer<Event>> listeners=new ArrayList<>(); public final List<Event> events=new ArrayList<>();public <T extends Event>T post(T event){events.add(event);for(var listener:List.copyOf(listeners))listener.accept(event);return event;}public void reset(){events.clear();listeners.clear();}}}
