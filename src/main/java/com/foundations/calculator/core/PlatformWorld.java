package com.foundations.calculator.core;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.WorldClocks;
/** Clock and weather updates use the platform's persisted state. */
public final class PlatformWorld {
    private PlatformWorld() {}
    public static void setDayTime(ServerLevel level,long time){var clock=level.registryAccess().lookupOrThrow(Registries.WORLD_CLOCK).getOrThrow(WorldClocks.OVERWORLD);level.clockManager().setTotalTicks(clock,time);}
    public static void setWeather(ServerLevel level,int clear,int duration,boolean raining,boolean thundering){var weather=level.getWeatherData();weather.setClearWeatherTime(clear);weather.setRainTime(duration);weather.setThunderTime(duration);weather.setRaining(raining);weather.setThundering(thundering);weather.setDirty();}
}
