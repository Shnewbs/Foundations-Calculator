package com.foundations.calculator.content;
import java.util.*;
import net.minecraft.core.*;
/** Geometry translated from Calculator 1.12.2 (MIT, Ollie Lansdell). */
public final class GreenhouseBlueprint {
 public enum BlockType {LOG,STAIRS,GLASS,PLANKS}
 public record BlockPlace(BlockType type,int x,int y,int z,int meta){public BlockPos pos(){return new BlockPos(x,y,z);}}
 private final BlockPos pos;private final Direction forward;private final int tier;
 public GreenhouseBlueprint(BlockPos pos,Direction forward,int tier){this.pos=pos;this.forward=forward;this.tier=tier;}
 public List<BlockPlace> blocks(){Map<BlockPos,BlockPlace> unique=new LinkedHashMap<>();for(BlockPlace p:tier==1?basic():advanced())unique.put(p.pos(),p);return List.copyOf(unique.values());}
 private int intValues(int value,BlockType ignored){return (tier==1?5:8)-value;}
public int type(String string) {
		int meta = forward.getOpposite().get3DDataValue();
		if (string.equals("r")) {
			if (meta == 3) {
				return 1;
			}
			if (meta == 4) {
				return 3;
			}
			if (meta == 5) {
				return 2;
			}
			if (meta == 2) {
				return 0;
			}
		}
		if (string.equals("l")) {
			if (meta == 3) {
				return 0;
			}
			if (meta == 4) {
				return 2;
			}
			if (meta == 5) {
				return 3;
			}
			if (meta == 2) {
				return 1;
			}
		}
		if (string.equals("d")) {
			if (meta == 3) {
				return 4;
			}
			if (meta == 4) {
				return 6;
			}
			if (meta == 5) {
				return 7;
			}
			if (meta == 2) {
				return 5;
			}
		}
		if (string.equals("d2")) {
			if (meta == 3) {
				return 5;
			}
			if (meta == 4) {
				return 7;
			}
			if (meta == 5) {
				return 6;
			}
			if (meta == 2) {
				return 4;
			}
		}
		return 0;
	}
public ArrayList<BlockPlace> basic() {
        ArrayList<BlockPlace> blocks = new ArrayList<>();

		int hX = forward.getClockWise().getStepX();
		int hZ = forward.getClockWise().getStepZ();

		int hoX = forward.getClockWise().getOpposite().getStepX();
		int hoZ = forward.getClockWise().getOpposite().getStepZ();

		int fX = forward.getStepX();
		int fZ = forward.getStepZ();

		// end
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		for (int i = 1; i <= 2; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x, y + i, z, -1));
		}

		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 2, y + i, z + hZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 2, y + i, z + hoZ * 2, -1));
		}
		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.GLASS, x + hX, y + i, z + hZ, -1));
            blocks.add(new BlockPlace(BlockType.GLASS, x + hoX, y + i, z + hoZ, -1));
		}

		// front
        x = pos.getX() + forward.getStepX() * 4;
		y = pos.getY();
        z = pos.getZ() + forward.getStepZ() * 4;
		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 2, y + i, z + hZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 2, y + i, z + hoZ * 2, -1));
		}
		for (int i = 0; i <= 2; i++) {
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX, y + i, z + hZ, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX, y + i, z + hoZ, -1));
			if (i == 2)
				blocks.add(new BlockPlace(BlockType.PLANKS, x, y + i, z, -1));
		}

		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		// sides
		for (int i = 1; i <= 3; i++) {
			if (i != 2) {
                blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * 2 + fX * i, y - 1, z + hZ * 2 + fZ * i, -1));
                blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * 2 + fX * i, y - 1, z + hoZ * 2 + fZ * i, -1));
				for (int s = 0; s <= 1; s++) {

                    blocks.add(new BlockPlace(BlockType.GLASS, x + hX * 2 + fX * i, y + s, z + hZ * 2 + fZ * i, -1));
                    blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * 2 + fX * i, y + s, z + hoZ * 2 + fZ * i, -1));
				}
			}
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * 2 + fX * i, y + 2, z + hZ * 2 + fZ * i, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * 2 + fX * i, y + 2, z + hoZ * 2 + fZ * i, -1));
		}

		for (int Y = 0; Y <= 1; Y++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 2 + fX * 2, y + Y, z + hZ * 2 + fZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 2 + fX * 2, y + Y, z + hoZ * 2 + fZ * 2, -1));
		}

		// roof
		for (int i = -1; i <= 1; i++) {
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * i, y + 3, z + hZ * i, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * i + fX * 4, y + 3, z + hZ * i + fZ * 4, -1));
		}
		for (int i = -1; i <= 5; i++) {
			for (int s = 2; s <= 4; s++) {
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * intValues(s, BlockType.STAIRS) + fX * i, y + s, z + hZ * intValues(s, BlockType.STAIRS) + fZ * i, type("r")));
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * intValues(s, BlockType.STAIRS) + fX * i, y + s, z + hoZ * intValues(s, BlockType.STAIRS) + fZ * i, type("l")));
                blocks.add(new BlockPlace(BlockType.PLANKS, x + fX * i, y + 4, z + fZ * i, -1));
			}
		}

		// underroof
		for (int i = -1; i <= 5; i++) {
			if (i != -1 && i != 0 && i != 4 && i != 5) {
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hX + fX * i, y + 3, z + hZ + fZ * i, type("d")));
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX + fX * i, y + 3, z + hoZ + fZ * i, type("d2")));
			} else {
				if (i != 0 && i != 4) {
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX + fX * i, y + 3, z + hZ + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX + fX * i, y + 3, z + hoZ + fZ * i, type("d2")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * 2 + fX * i, y + 2, z + hZ * 2 + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * 2 + fX * i, y + 2, z + hoZ * 2 + fZ * i, type("d2")));
				}
			}
		}
		return blocks;
	}
public ArrayList<BlockPlace> advanced() {
        ArrayList<BlockPlace> blocks = new ArrayList<>();

		int hX = forward.getClockWise().getStepX();
		int hZ = forward.getClockWise().getStepZ();

		int hoX = forward.getClockWise().getOpposite().getStepX();
		int hoZ = forward.getClockWise().getOpposite().getStepZ();

		int fX = forward.getStepX();
		int fZ = forward.getStepZ();

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		//end
		for (int i = 1; i <= 6; i++) {
			blocks.add(new BlockPlace(BlockType.LOG, x, y + i, z, -1));
		}

		for (int i = 0; i <= 3; i++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 4, y + i, z + hZ * 4, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 4, y + i, z + hoZ * 4, -1));
		}

		for (int i = 1; i <= 3; i++) {
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * i, y - 1, z + hZ * i, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * i, y - 1, z + hoZ * i, -1));
		}

		for (int j = 1; j <= 3; j++) {
			if (j != 3) {
				for (int i = 0; i <= 5; i++) {
                    blocks.add(new BlockPlace(BlockType.GLASS, x + hX * j, y + i, z + hZ * j, -1));
                    blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * j, y + i, z + hoZ * j, -1));
				}
			} else {
				for (int i = 0; i <= 4; i++) {
                    blocks.add(new BlockPlace(BlockType.GLASS, x + hX * j, y + i, z + hZ * j, -1));
                    blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * j, y + i, z + hoZ * j, -1));
				}
			}
		}

        x = pos.getX() + forward.getStepX() * 8;
		y = pos.getY();
        z = pos.getZ() + forward.getStepZ() * 8;
		for (int i = 0; i <= 3; i++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 4, y + i, z + hZ * 4, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 4, y + i, z + hoZ * 4, -1));
		}

		for (int i = 0; i <= 5; i++) {
			if (i <= 4) {
                blocks.add(new BlockPlace(BlockType.GLASS, x + hX * 3, y + i, z + hZ * 3, -1));
                blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * 3, y + i, z + hoZ * 3, -1));
			}
            blocks.add(new BlockPlace(BlockType.GLASS, x + hX * 2, y + i, z + hZ * 2, -1));
            blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * 2, y + i, z + hoZ * 2, -1));
		}

		for (int i = 0; i <= 6; i++) {
			if (i > 2) {
                blocks.add(new BlockPlace(BlockType.GLASS, x + hX, y + i, z + hZ, -1));
                blocks.add(new BlockPlace(BlockType.GLASS, x + hoX, y + i, z + hoZ, -1));
				blocks.add(new BlockPlace(BlockType.GLASS, x, y + i, z, -1));
			}
			if (i <= 2) {
                blocks.add(new BlockPlace(BlockType.PLANKS, x + hX, y + i, z + hZ, -1));
                blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX, y + i, z + hoZ, -1));
			}
			if (i == 2) {
				blocks.add(new BlockPlace(BlockType.PLANKS, x, y + i, z, -1));
			}
		}

		for (int i = 2; i <= 3; i++) {
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * i, y - 1, z + hZ * i, -1));
            blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * i, y - 1, z + hoZ * i, -1));
		}

		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		//sides
		for (int i = 1; i <= 7; i++) {
			if (i != 4) {
				for (int s = 0; s <= 2; s++) {
					if (s == 0) {
                        blocks.add(new BlockPlace(BlockType.PLANKS, x + hX * 4 + fX * i, y + s, z + hZ * 4 + fZ * i, -1));
                        blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX * 4 + fX * i, y + s, z + hoZ * 4 + fZ * i, -1));
					} else {
                        blocks.add(new BlockPlace(BlockType.GLASS, x + hX * 4 + fX * i, y + s, z + hZ * 4 + fZ * i, -1));
                        blocks.add(new BlockPlace(BlockType.GLASS, x + hoX * 4 + fX * i, y + s, z + hoZ * 4 + fZ * i, -1));
					}
				}
			}
		}

		for (int Y = 0; Y <= 3; Y++) {
            blocks.add(new BlockPlace(BlockType.LOG, x + hX * 4 + fX * 4, y + Y, z + hZ * 4 + fZ * 4, -1));
            blocks.add(new BlockPlace(BlockType.LOG, x + hoX * 4 + fX * 4, y + Y, z + hoZ * 4 + fZ * 4, -1));
		}

		//roof		
        blocks.add(new BlockPlace(BlockType.PLANKS, x + hX, y + 6, z + hZ, -1));
        blocks.add(new BlockPlace(BlockType.PLANKS, x + hoX, y + 6, z + hoZ, -1));

		for (int i = -1; i <= 9; i++) {
			for (int s = 3; s <= 7; s++) {
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * intValues(s, BlockType.STAIRS) + fX * i, y + s, z + hZ * intValues(s, BlockType.STAIRS) + fZ * i, type("r")));
                blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * intValues(s, BlockType.STAIRS) + fX * i, y + s, z + hoZ * intValues(s, BlockType.STAIRS) + fZ * i, type("l")));
                blocks.add(new BlockPlace(BlockType.PLANKS, x + fX * i, y + 7, z + fZ * i, -1));
			}
		}

		//under-roof
		for (int i = -1; i <= 9; i++) {
			if (i != 0) {
				if (i != 4 && i != 8) {
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * 4 + fX * i, y + 3, z + hZ * 4 + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * 4 + fX * i, y + 3, z + hoZ * 4 + fZ * i, type("d2")));
				}
				if (i != 8) {
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * 3 + fX * i, y + 4, z + hZ * 3 + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX * 2 + fX * i, y + 5, z + hZ * 2 + fZ * i, type("d")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hX + fX * i, y + 6, z + hZ + fZ * i, type("d")));

                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * 3 + fX * i, y + 4, z + hoZ * 3 + fZ * i, type("d2")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX * 2 + fX * i, y + 5, z + hoZ * 2 + fZ * i, type("d2")));
                    blocks.add(new BlockPlace(BlockType.STAIRS, x + hoX + fX * i, y + 6, z + hoZ + fZ * i, type("d2")));
				}
			}
		}
		return blocks;
	}
}
