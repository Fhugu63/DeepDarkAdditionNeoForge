package com.fhugu.deepdarkaddition.entitys.entityclasses;

import com.fhugu.deepdarkaddition.entitys.EntitysAI.SculkCreeperAI;
import com.fhugu.deepdarkaddition.entitys.ModEntitys;
import com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper.SculkCreeperModel;
import com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper.SculkCreeperRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SculkCreeper extends Monster implements VibrationSystem {
    private int cooldownToAbility = 0;


    private int numOfAngry = 0;

    private final VibrationSystem.User vibrationUser;
    private VibrationSystem.Data vibrationData;
    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
    private boolean xrenRegistred = false;
    private SculkCreeperAI sculkCreeperAI;

    private final SculkCreeperRenderState rendererState = new SculkCreeperRenderState();

    public AnimationState reactToSoundAnim = new AnimationState();


    public SculkCreeper(EntityType<SculkCreeper> entityType, Level level) {
        super(entityType, level);
        this.vibrationUser = new SculkCreeper.VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));

        //this.alw

        this.sculkCreeperAI = new SculkCreeperAI();
    }

    @Override
    public Data getVibrationData() {
        return vibrationData;
    }

    @Override
    public User getVibrationUser() {
        return vibrationUser;
    }

    @Override
    public boolean dampensVibrations() {
        return true;
    }

    enum StepOfAngry {
        NEUTRAL,
        MEDIUMANGRY,
        ANGRY
    }

    private StepOfAngry stepOfAngry = StepOfAngry.NEUTRAL;

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            if (!xrenRegistred) {
                dynamicGameEventListener.add(serverLevel);
                this.setCustomName(null);
                this.setCustomNameVisible(false);
                xrenRegistred = true;
            }
            VibrationSystem.Ticker.tick(serverLevel, SculkCreeper.this.vibrationData, SculkCreeper.this.vibrationUser);

            sculkCreeperAI.tick(this, serverLevel);
        }
        super.tick();
    }



    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);

        nbt.putInt("cdToAbility", sculkCreeperAI.cooldownToAbility);
        nbt.putInt("numOfAngry", sculkCreeperAI.numOfAngry);
        if (sculkCreeperAI.targetUUID != null) {
            nbt.putUUID("targetUUID", sculkCreeperAI.targetUUID);
        }

        VibrationSystem.Data.CODEC
                .encodeStart(registryops, this.vibrationData)
                .resultOrPartial(p_351915_ -> System.out.println("Failed to encode vibration listener for sculkcreeper: '{}'"))
                .ifPresent(p_219418_ -> nbt.put("listener", p_219418_));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        if (nbt.contains("cdToAbility")) {
            sculkCreeperAI.cooldownToAbility = nbt.getInt("cdToAbility");
        }
        if (nbt.contains("numOfAngry")) {
            sculkCreeperAI.numOfAngry = nbt.getInt("numOfAngry");
        }
        if (nbt.contains("targetUUID")) {
            sculkCreeperAI.targetUUID = nbt.getUUID("targetUUID");
            this.setTarget(level().getPlayerByUUID(sculkCreeperAI.targetUUID));
        }

        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);

        if (nbt.contains("listener", 10)) {
            VibrationSystem.Data.CODEC
                    .parse(registryops, nbt.getCompound("listener"))
                    .resultOrPartial(p_351914_ -> System.out.println("Failed to parse vibration listener for sculkcreeper: '{}'"))
                    .ifPresent(p_281093_ -> this.vibrationData = p_281093_);
        }
    }

    public void walkTo(int x, int y, int z, Double speed) {
        this.navigation.moveTo(x, y, z, speed);
    }

    public void walkTo(LivingEntity target, Double speed) {
        this.navigation.moveTo(target, speed);
    }

    public void attackTarget() {
        if (getTarget() != null) {
            if (sculkCreeperAI.stepOfAngry == SculkCreeperAI.StepOfAngry.MEDIUMANGRY) {
                walkTo(getTarget(), sculkCreeperAI.nowSpeed);
            } else if (sculkCreeperAI.stepOfAngry == SculkCreeperAI.StepOfAngry.ANGRY) {
                if (sculkCreeperAI.cooldownToAbility == 0 && (this.getTarget().getY() - this.getY()) >= 3) {
                    useAbility();
                    sculkCreeperAI.resetCooldownToAbility();
                } else {
                    walkTo(getTarget(), sculkCreeperAI.nowSpeed);
                }
            }
        }
    }


    public void deleteTarget() {
        this.setTarget(null);
    }

    public Boolean canTargerEntity(Entity entity) {
        if (entity instanceof LivingEntity) {
            EntityType entityType = entity.getType();
            if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) && entityType != EntityType.ARMOR_STAND && entityType != EntityType.WARDEN
                    && entityType != ModEntitys.HUNGRY_SOUL.get() && entityType != ModEntitys.SCULK_CREEPER.get() && entityType != EntityType.ARROW &&
                    entityType != EntityType.SPECTRAL_ARROW && entityType != EntityType.ITEM && entityType != EntityType.ITEM_FRAME && entityType != EntityType.ITEM_DISPLAY
                    && !this.dead && this.distanceTo(entity) <= 30
            ) {
                return true;
            } else {
                deleteTarget();

            }
        }
        return false;
    }

    public void useAbility() {
        List<Player> players = level().getEntitiesOfClass(Player.class, AABB.ofSize(position(), 20.0, 20.0, 20.0));
        //players.forEach { sculkCreeperEntity -> sculkCreeperEntity.soundVibration(entity, pPos) }
        if (!level().isClientSide) {
            ServerLevel sLevel = (ServerLevel) level();
            //val particle = ShriekParticleOption(10)
            SculkChargeParticleOptions particle = new SculkChargeParticleOptions(10f);

            sLevel.sendParticles(
                    particle,
                    position().x, position().y + 1, position().z,
                    50,
                    0.1, 0.1, 0.1,
                    1.0
            );

            players.forEach(player -> player.hurtServer(sLevel, damageSources().explosion(null, this), 9.5f));
            cooldownToAbility = 60;
        }
    }

    @Override
    public void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));

        goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

        goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 0.5));
        goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ARMOR_TOUGHNESS, 10.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
    }

    class VibrationUser implements VibrationSystem.User {
        private static final int GAME_EVENT_LISTENER_RANGE = 16;
        private final PositionSource positionSource = new EntityPositionSource(SculkCreeper.this, SculkCreeper.this.getEyeHeight());
        private GameEvent lastreceivedVibrationGameEvent = null;

        @Override
        public int getListenerRadius() {
            return 16;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.WARDEN_CAN_LISTEN;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> gameEventHolder, GameEvent.Context context) {
            if (context.sourceEntity() instanceof LivingEntity && SculkCreeper.this.canTargerEntity(context.sourceEntity())
                    && gameEventHolder.value() != lastreceivedVibrationGameEvent) {
                lastreceivedVibrationGameEvent = gameEventHolder.value();
                SculkCreeper.this.reactToSoundAnim.start(9);
                serverLevel.broadcastEntityEvent(SculkCreeper.this, (byte) 10);
                //SculkCreeper.this.re
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onReceiveVibration(
                ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> gameEventHolder, @Nullable Entity entity, @Nullable Entity entity1, float p_283699_
        ) {
            if (gameEventHolder.is(GameEvent.BLOCK_ACTIVATE) || gameEventHolder.is(GameEvent.BLOCK_ATTACH) || gameEventHolder.is(GameEvent.BLOCK_CHANGE) ||
                    gameEventHolder.is(GameEvent.BLOCK_CLOSE) || gameEventHolder.is(GameEvent.BLOCK_DESTROY) || gameEventHolder.is(GameEvent.BLOCK_DEACTIVATE) ||
                    gameEventHolder.is(GameEvent.BLOCK_DETACH) || gameEventHolder.is(GameEvent.BLOCK_OPEN) || gameEventHolder.is(GameEvent.BLOCK_PLACE) ||
                    gameEventHolder.is(GameEvent.NOTE_BLOCK_PLAY) || gameEventHolder.is(GameEvent.CONTAINER_CLOSE) || gameEventHolder.is(GameEvent.CONTAINER_OPEN)) {
                sculkCreeperAI.hasAPurposeNow = SculkCreeperAI.Purposes.WALKTO;
                sculkCreeperAI.walkTo = blockPos;
            } else {
                if (entity != null) {
                    if (entity.isAlive() && entity instanceof LivingEntity && canTargerEntity(entity)) {
                        sculkCreeperAI.hasAPurposeNow = SculkCreeperAI.Purposes.ATTACK;
                        setTarget((LivingEntity) entity);
                    }
                }
            }
        }
    }
    
    /*void dropCustomDeathLoot1(DamageSource pSource, int pLooting, Boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);

        if (pSource.entity != null && !pSource.entity!!.level().isClientSide) {
            val entity = pSource.entity!!
                    val sLevel = entity.level() as ServerLevel
            val savedData = DeepDarkAdditionSaveData.getSavedData(sLevel)

            var diarys = savedData.diarys
            val splitedDiarys = diarys.split(",")

            var diaryHasSelected = false

            //println(splitedDiarys.contains("rdp1"))

            if (!splitedDiarys.contains("rdp1") && !diaryHasSelected) {
                diaryHasSelected = true

                val items = ItemStack(ModItems().RESEARHDIARYPARTONE.get(), 1)

                savedData.diarys += "rdp1,"
                savedData.setDirty()

                this.spawnAtLocation(items)
            }
            if (!splitedDiarys.contains("rdp2") && !diaryHasSelected) {
                diaryHasSelected = true

                val items = ItemStack(ModItems().RESEARHDIARYPARTTWO.get(), 1)

                savedData.diarys += "rdp2,"
                savedData.setDirty()

                this.spawnAtLocation(items)
            }
            if (!splitedDiarys.contains("rdp3") && !diaryHasSelected) {
                diaryHasSelected = true

                val items = ItemStack(ModItems().RESEARHDIARYPARTTHREE.get(), 1)

                savedData.diarys += "rdp3,"
                savedData.setDirty()

                this.spawnAtLocation(items)
            }
        }
    }*/
}