package openblocks.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import openblocks.Config;
import openmods.utils.BlockNotifyFlags;
import openmods.utils.ColorUtils;
import openmods.utils.ColorUtils.ColorMeta;

public class BlockElevator extends OpenBlock {

	public static final int[] colors = new int[] { 0xe0e0e0, // 15
	0xc54a4a, // 1
	0x2b6631, // 2
	0x5d4b3f, // 3
	0x494c68, // 4
	0xaa55b2, // 9
	0x608696, // 6
	0xb0b0b0, // 7
	0x595959, // 8
	0xd490b6, // 9
	0x81c57c, // 10
	0x8c8f2e, // 11
	0x728abb, // 12
	0xaf60b6, // 13
	0xbd6c36, // 14
	0x252525, // 0
	};

	public BlockElevator() {
		super(Config.blockElevatorId, Material.ground);
	}

	@Override
	public boolean shouldRenderBlock() {
		return true;
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return colors[world.getBlockMetadata(x, y, z)];
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem();
		if (stack != null) {
			ColorMeta meta = ColorUtils.stackToColor(stack);
			if (meta != null) {
				int dmg = meta.vanillaId;
				// temp hack, dont tell anyone
				// NOTE: someone was to lazy to create custom item to make sure
				// default meta is 15 (white). And now we have to support that
				// weird stuff
				if (dmg == 15) dmg = 0;
				else if (dmg == 0) dmg = 15;
				world.setBlockMetadataWithNotify(x, y, z, dmg, BlockNotifyFlags.ALL);
				world.markBlockForRenderUpdate(x, y, z);
				return true;
			}
		}
		return false;
	}

}
