package com.foundations.calculator.network;
import com.foundations.calculator.FoundationsCalculator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
@EventBusSubscriber(modid=FoundationsCalculator.ID,bus=EventBusSubscriber.Bus.MOD)
public final class Networking {
    @SubscribeEvent public static void register(RegisterPayloadHandlersEvent e){e.registrar("6").playToServer(TransferPayload.TYPE,TransferPayload.CODEC,TransferPayload::handle);e.registrar("6").playToClient(ResearchSyncPayload.TYPE,ResearchSyncPayload.CODEC,ResearchSyncPayload::handle);e.registrar("6").playToServer(GuideQueryPayload.TYPE,GuideQueryPayload.CODEC,GuideQueryPayload::handle);e.registrar("6").playToClient(GuideSnapshotPayload.TYPE,GuideSnapshotPayload.CODEC,GuideSnapshotPayload::handle);}
    private Networking(){}
}
