package net.minecraft.server.level;
import java.util.*; import net.minecraft.core.BlockPos; import net.minecraft.world.level.block.state.BlockState; import net.neoforged.neoforge.common.util.FakePlayerFactory.Actor;
public class ServerLevel {public final Map<BlockPos,BlockState> states=new HashMap<>();public final Set<BlockPos> unloaded=new HashSet<>(); public int writes,unloadedReads;public boolean interact=true,writeSuccess=true,border=true;
public boolean isOutsideBuildHeight(BlockPos p){return p.y() < -64 || p.y() >= 320;} public boolean hasChunkAt(BlockPos p){return !unloaded.contains(p);}
public class Border {public boolean isWithinBounds(BlockPos p){return border;}} public Border getWorldBorder(){return new Border();}
public BlockState getBlockState(BlockPos p){if(!hasChunkAt(p)){unloadedReads++;throw new AssertionError("Unloaded read "+p);}return states.getOrDefault(p,BlockState.AIR);}
public Object getBlockEntity(BlockPos p){return getBlockState(p).hasBlockEntity()?this:null;}
public boolean mayInteract(Actor actor,BlockPos pos){return interact;} public Object dimension(){return this;}
public boolean setBlockAndUpdate(BlockPos pos,BlockState state){writes++;if(!writeSuccess)return false;states.put(pos,state);return true;}}
