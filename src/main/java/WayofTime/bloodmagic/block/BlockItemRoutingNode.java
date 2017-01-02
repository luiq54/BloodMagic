package WayofTime.bloodmagic.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import WayofTime.bloodmagic.api.Constants;
import WayofTime.bloodmagic.tile.routing.TileItemRoutingNode;
import WayofTime.bloodmagic.tile.routing.TileRoutingNode;

import javax.annotation.Nullable;

public class BlockItemRoutingNode extends BlockRoutingNode
{
    public BlockItemRoutingNode()
    {
        super();

        setUnlocalizedName(Constants.Mod.MODID + ".itemRouting");
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileRoutingNode)
        {
            ((TileRoutingNode) tile).removeAllConnections();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileItemRoutingNode();
    }
}
