package us.potatoboy.ledger.listeners

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import us.potatoboy.ledger.actionutils.ActionFactory
import us.potatoboy.ledger.callbacks.BlockBurnCallback
import us.potatoboy.ledger.callbacks.BlockExplodeCallback
import us.potatoboy.ledger.database.ActionQueue

object BlockEventListener {
    init {
        BlockExplodeCallback.EVENT.register(::onExplode)
        BlockBurnCallback.EVENT.register(::onBurn)
    }

    private fun onBurn(world: World, pos: BlockPos, state: BlockState) {
        ActionQueue.addActionToQueue(ActionFactory.blockBreakAction(world, pos, state, "fire"))
    }

    private fun onExplode(
        world: World,
        entity: Entity?,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BlockEntity?
    ) {
        val source = entity?.let { Registry.ENTITY_TYPE.getId(it.type).path } ?: "explosion"

        ActionQueue.addActionToQueue(ActionFactory.blockBreakAction(world, blockPos, blockState, source))
    }
}