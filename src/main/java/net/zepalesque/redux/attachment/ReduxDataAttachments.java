package net.zepalesque.redux.attachment;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;

public class ReduxDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Redux.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ReduxPlayerAttachment>> REDUX_PLAYER = ATTACHMENTS.register("redux_player", () -> AttachmentType.builder(ReduxPlayerAttachment::new).serialize(ReduxPlayerAttachment.CODEC).copyOnDeath().build());

}
