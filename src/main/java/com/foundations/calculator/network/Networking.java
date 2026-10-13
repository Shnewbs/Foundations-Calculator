package com.foundations.calculator.network;
import com.foundations.calculator.FoundationsCalculator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
public final class Networking {
    @SubscribeEvent public static void register(RegisterPayloadHandlersEvent e){e.registrar("8").playToServer(TransferPayload.TYPE,TransferPayload.CODEC,TransferPayload::handle);e.registrar("8").playToClient(ResearchSyncPayload.TYPE,ResearchSyncPayload.CODEC,ResearchSyncPayload::handle);e.registrar("8").playToServer(GuideQueryPayload.TYPE,GuideQueryPayload.CODEC,GuideQueryPayload::handle);e.registrar("8").playToClient(GuideSnapshotPayload.TYPE,GuideSnapshotPayload.CODEC,GuideSnapshotPayload::handle);}
    @SubscribeEvent public static void recipes(RegisterPayloadHandlersEvent e){e.registrar("8").playToClient(RecipeSnapshotPayload.TYPE,RecipeSnapshotPayload.CODEC,RecipeSnapshotPayload::handle);}
    private Networking(){}
}
