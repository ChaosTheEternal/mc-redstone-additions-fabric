# Redstone Additions
A Fabric mod for Minecraft (targetting 1.16.2).  The intent is to add new blocks to assist and condense certain redstone circuits into single blocks.

May continue work on the Forge version for 1.16 but unsure how long it will take for a stable 1.16 version of Forge.  This version is currently me porting what I did have done from Forge over to Fabric.

## Additions
### Redstone Inverter
A single block replacement for converting an on signal to off, originally by feeding a redstone line into a redstone torch attached to a block to turn the torch off.

### Instant Off Repeater
A redstone repeater modification that has the normal delay of an unlocked redstone repeater when getting powered, but with the minimum delay once power stops.

May revisit the ability to lock this repeater at a later date.

### Signal Extended Observer
A modified observer that can output a signal for more than one tick.

## Planned Additions
These additions are under consideration *if* their implementation seems feasible.  No planned additions are guaranteed to be part of the mod.

### Glazed/Glossy Blocks
Name not final (crafting will involve 8 of the block and a honey or slime block).  Alternative for the Glaze Container, due to limitations.  A large set of stock blocks from Minecraft except they act like Glazed Terracotta when next to slime/honey blocks being moved by pistons.  The ability to get the original blocks will exist by putting them through a furnace.

### Directed Redstone Wiring / Redstone Rods
Some option to make running redstone vertically (up and down) easier and more compact, or to allow an option to run redstone through water.  Like a "redstone ladder" except it can also transmit power down.

### Configurable Pistons
A modification of pistons that can be configured to have more than one block of reach and logic to also pull multiple blocks as if they were Slime or Honey blocks.

## On Hold Additions
### Glaze Container
A single block container that appears and mostly works like the block inserted but makes the block act like Glazed Terracotta and doesn't get pulled by Slime/Honey blocks.
#### Why?
Unlike what Forge offers for the interface, Fabric/Stock doesn't seemingly have methods to "get" a block's hardness or light level (and looking at the Redstone Lamp which would shift light levels based on whether it is on or off I don't see anything), and the method to get blast resistance doesn't offer parameters (meaning, I can't check what block is contained to use its blast resistance).

Forge also doesn't offer a means to dynamically adjust the tool used to mine a block (their "get" tool method has no parameters as well), but what I had got me closer.  Best option I was considering otherwise was, when the glaze container was "broken" it would place the contained block and just drop the container.  That would work for both, but then I still have the issue with Fabric/Stock not being able to adjust the light level, and I wasn't able to get that logic working entirely (it would place the contained block but not drop the container).