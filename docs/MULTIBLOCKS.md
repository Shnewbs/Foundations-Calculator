# Machines and multiblocks

All directions below are relative to the controller. Its visible front faces the player when placed. **Inside** is the opposite direction; **side** is clockwise when looking in that inside direction. Rotating the controller rotates the layout. Supply FE by capability/cable or its battery slot.

## Basic and Advanced Greenhouses

Use **Build** to construct the imported original blueprint. The first four input slots are logs, wooden stairs, glass, planks, in that order. The remaining inputs are seeds. Refill materials as needed; construction resumes without replacing an obstruction or consuming a resource twice. Each block actually placed costs 100 FE.

| Tier | Clear construction bounds relative to controller | Growing area |
|---|---|---|
| Basic | Side −3…3, inside −1…5, height −1…4 | 3×3 at inside 1…3, except its central water cell: eight crops |
| Advanced | Side −5…5, inside −1…9, height −1…7 | 7×7 at inside 1…7, except the four water corners: 45 crops |

Prepare dirt/grass beneath the growing cells. The controller converts those blocks to hydrated farmland for 50 FE each and creates water in its irrigation cells for 1,000 FE each. Planting costs 50 FE, harvesting 150 FE and successful accelerated growth 150 FE. Broccoli can also grow outside; Prunae needs tier 2 and Fiddledew tier 3.

A chest directly outside the controller supplies seeds and receives produce. Without a chest, results use the machine's output slots and retained overflow. A lit Gas Lantern inside a basic/advanced greenhouse contributes CO₂. The gas mix changes acceleration intervals; ordinary crop light and survival rules still apply.

**Pause** or a redstone signal suspends farming. **Demolish** recovers matching structure blocks into outputs, preserving blocked overflow. It does not clear unrelated obstructions. The imported roof includes overhangs beyond the crop footprint.

## Flawless Greenhouse

This tier is built manually. Choose an interior length `L` from 1 to 64. Its width is four blocks; the two central columns contain crops. In the coordinates below the controller is `(side 0, inside 0, height 0)`.

| Position | Required blocks |
|---|---|
| Ends: inside `0` and `L+1`, side `0…3`, height `−1` | Stable Stone |
| Ends: side `0` and `3`, height `0` and `1` | Stable Stone; the controller replaces the first near corner |
| Every row, inside `0…L+1`, side `0…3`, height `2` | Bottom Quartz Slabs |
| Interior side walls: inside `1…L`, side `0` and `3`, height `0` and `1` | Flawless Glass |
| Interior crops: inside `1…L`, side `1` and `2`, height `0` | Seed/crop space above dirt or farmland |
| Interior irrigation: inside `1…L`, side `0` and `3`, height `−1` | Dirt/grass/farmland that the machine can turn into water |

Place the **CO₂ Generator** at `(3,0,0)` facing the same direction as the controller. It replaces that near corner's stable stone and is a valid frame block. Give it fuel and FE. Each fuel item costs **100,000 FE** and runs for **10,000 active ticks**. Normal fuel's gas contribution follows furnace burn time. Controlled Fuel supplies 800 units and pauses at 100% carbon, resuming at 92%, without consuming paused burn time. The greenhouse applies gas every second.

Flawless seeds use all fourteen inputs. **Pause** works as on the lower tiers; construction/demolition buttons are not present.

## Dynamic Calculator

Build a **7×7×7** hollow cube. Place the controller in the middle of one vertical face. The cube centre is three blocks inside the controller at the same height.

The top/bottom perimeter and four vertical corner pillars are Stable Stone. The other shell blocks are Flawless Glass, except the controller. Leave the 5×5×5 interior empty. The controller validates the shell before consuming inputs.

Its first two inputs run basic Calculator recipes, the next two Scientific recipes and the last three Atomic recipes. All lanes share output slots. Installed Atomic Assembly exposes the same three input lanes in a portable Flawless Calculator, with individual Calculate buttons and no placed shell.

## Calculator Locator

Use a Locator Module to bind your player UUID, then insert it into the locator. For radius `r` from 1 to 11, fill a `(2r+1)×(2r+1)` square around the controller with Calculator Plugs at the controller's height, each over Stable Stone. Leave the centre for the controller. Surround the square with a Stable Stone wall two blocks high, at heights `−1` and `0`; outer corners are not required.

Insert stable analysed circuit boards into plugs to stabilize the generator. Seven stable plugs allow generation without the owner online. With fewer, the owner must be present in the dimension and instability can alter time or affect them, matching the original mechanic. These effects are server configurable. The locator stores up to 50 million FE and charges a battery.

## Conductor Mast and Weather Stations

A mast reserves four blocks of height when placed. Breaking an upper section removes the whole mast and drops one controller. It creates visual lightning strikes and generates FE during a 200-tick burst. Weather Stations link to the nearest mast within ten blocks on the same level and increase the burst output. Transmitters within twenty blocks reduce the strike interval and can increase its output. Right-click a Weather Station to see its link.

## Other world machines

- **Weather Controller:** choose Time/Rain/Thunder and the target, then supply redstone and FE. A change takes 100 ticks at 2,500 FE per tick, followed by a short cooldown.
- **Magnetic Flux:** eight filter slots; whitelist/blacklist and item/tag versus component matching. Pulls drops within ten blocks into the inventory below. Redstone disables it; blocked transfers retain the item entity.
- **Assimilators:** place next to a three-block tree trunk, facing away from it, with at least ten Calculator leaves nearby. Stone Assimilators collect leaf nutrition into a module; Algorithm Assimilators collect pear/diamond products into their 27 bins.
- **Nutrition piping:** connect the matching processor to mature leaves using Amethyst or Tanzanite pipes/logs. Traversal is limited to loaded chunks and 512 visited positions.
- **Calculator Screen:** use the screen item on a horizontal face of a block exposing FE. It displays that side's stored energy/capacity.
