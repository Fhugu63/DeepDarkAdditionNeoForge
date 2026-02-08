package com.fhugu.deepdarkaddition.entitys.entityclasses;

import com.fhugu.deepdarkaddition.entitys.ModEntitys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SculkCreeper extends PathfinderMob implements VibrationSystem {
    private int cooldownToAbility = 0;
    private UUID targetUUID = null;

    private int numOfAngry = 0;

    private final VibrationSystem.User vibrationUser;
    private VibrationSystem.Data vibrationData;
    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;

    public SculkCreeper(EntityType<SculkCreeper> entityType, Level level) {
        super(entityType, level);
        this.vibrationUser = new SculkCreeper.VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));
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
            VibrationSystem.Ticker.tick(serverLevel, SculkCreeper.this.vibrationData, SculkCreeper.this.vibrationUser);

            if (this.tickCount % 40 == 0) {
                Warden.applyDarknessAround(serverLevel, position(), this, 16);

            }


            if (numOfAngry <= 30) {
                stepOfAngry = StepOfAngry.NEUTRAL;
            } else if (numOfAngry <= 80) {
                stepOfAngry = StepOfAngry.MEDIUMANGRY;
            } else {
                stepOfAngry = StepOfAngry.ANGRY;
            }

            if (this.getTarget() != null) {
                double desiredSpeed = 0;
                if (numOfAngry >= 60) {
                    desiredSpeed = 1.3;
                } else {
                    desiredSpeed = 0.7;
                }

                this.navigation.moveTo(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), desiredSpeed);

                if (this.getTarget().getY()-this.getY() >= 1.5 && (this.distanceTo(this.getTarget()) - (this.getTarget().getY() - this.getY())) <= 2 && this.tickCount % 60 == 0) {
                    jump();
                }


                if (this.tickCount % 6 == 0 && numOfAngry <= 150) {
                    numOfAngry++;
                }
                if (numOfAngry >= 80) {
                    navigation.moveTo(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), 0.2);
                }
                if (numOfAngry >= 90) {
                    useAbility();
                }
            }



            if (cooldownToAbility != 0) {
                cooldownToAbility--;
            }
        }

        super.tick();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);

        nbt.putInt("cdToAbility", cooldownToAbility);
        nbt.putInt("numOfAngry", numOfAngry);
        if (targetUUID != null) {
            nbt.putUUID("targetUUID", targetUUID);
        }

        VibrationSystem.Data.CODEC
                .encodeStart(registryops, this.vibrationData)
                .resultOrPartial(p_351915_ -> System.out.println("Failed to encode vibration listener for SculkCreeper: '{}'"))
                .ifPresent(p_219418_ -> nbt.put("listener", p_219418_));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        if (nbt.contains("cdToAbility")) {
            cooldownToAbility = nbt.getInt("cdToAbility");
        }
        if (nbt.contains("numOfAngry")) {
            numOfAngry = nbt.getInt("numOfAngry");
        }
        if (nbt.contains("targetUUID")) {
            targetUUID = nbt.getUUID("targetUUID");
            this.setTarget(level().getPlayerByUUID(targetUUID));
        }

        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);

        if (nbt.contains("listener", 10)) {
            VibrationSystem.Data.CODEC
                    .parse(registryops, nbt.getCompound("listener"))
                    .resultOrPartial(p_351914_ -> System.out.println("Failed to parse vibration listener for Warden: '{}'"))
                    .ifPresent(p_281093_ -> this.vibrationData = p_281093_);
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
    
    void soundVibration(Entity entity, BlockPos pos) {
        if (entity != null && entity.isAlive() && canTargerEntity(entity)) {
            int radius = 16;
            LivingEntity lEntity = (LivingEntity) entity;
            Vec3i pPos = new Vec3i(pos.getX(), pos.getY(), pos.getZ());
            float distance = this.distanceTo(entity);
            Level level = level();

            if (canTargerEntity(lEntity) && distance <= radius) {
                VibrationParticleOption particle = new VibrationParticleOption(new BlockPositionSource(new BlockPos(new Vec3i((int) position().x, (int) position().y, (int) position().z))), 10);
                if (!level.isClientSide) {
                    ServerLevel sLevel = (ServerLevel) level;
                    sLevel.sendParticles(
                            particle,
                            pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5,
                            1,
                            0.0, 0.0, 0.0,
                            0.0
                    );

                    setTarget(lEntity);
                /*} else if (level.isClientSide) {
                    val cLevel = level as ClientLevel
                    cLevel.addParticle(
                        particle,
                        true,
                        pPos.x + 0.5, pPos.y + 0.5, pPos.z + 0.5,
                        0.0, 0.0, 0.0
                    )
                    target = entity*/
                }
            }
        }
    }

    void jump() {
        if (this.onGround()) {
            int jumpStrength = 2;
            //this.deltaMovement.
            this.moveTo(this.getX(), this.getY()+jumpStrength, this.getZ());
            this.hasImpulse = true;
        }
    }

    public void useAbility() {
        List<Player> players = level().getEntitiesOfClass(Player.class, AABB.ofSize(position(), 20.0, 20.0, 20.0));
        //players.forEach { sculkCreeperEntity -> sculkCreeperEntity.soundVibration(entity, pPos) }
        if (!level().isClientSide && cooldownToAbility == 0) {
            ServerLevel sLevel = (ServerLevel) level();
            //val particle = ShriekParticleOption(10)
            SculkChargeParticleOptions particle = new SculkChargeParticleOptions(10f);

            sLevel.sendParticles(
                    particle,
                    position().x, position().y+1, position().z,
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
                .add(Attributes.FOLLOW_RANGE, 3.0)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
    }

    class VibrationUser implements VibrationSystem.User {
        private static final int GAME_EVENT_LISTENER_RANGE = 16;
        private final PositionSource positionSource = new EntityPositionSource(SculkCreeper.this, SculkCreeper.this.getEyeHeight());

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
            return true;
        }

        @Override
        public void onReceiveVibration(
                ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> gameEventHolder, @Nullable Entity entity, @Nullable Entity entity1, float p_283699_
        ) {
            System.out.println("it work1!");
            if (entity != null) {
                if (entity.isAlive()) {
                    setTarget((LivingEntity) entity);
                    System.out.println("it work2!");
                }
                System.out.println("it work3!");
            } else {
                if (entity1.isAlive()) {
                    setTarget((LivingEntity) entity1);
                    System.out.println("it work2.1!");
                }
                System.out.println("it work3.1!");
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

                val item = ItemStack(ModItems().RESEARHDIARYPARTONE.get(), 1)

                savedData.diarys += "rdp1,"
                savedData.setDirty()

                this.spawnAtLocation(item)
            }
            if (!splitedDiarys.contains("rdp2") && !diaryHasSelected) {
                diaryHasSelected = true

                val item = ItemStack(ModItems().RESEARHDIARYPARTTWO.get(), 1)

                savedData.diarys += "rdp2,"
                savedData.setDirty()

                this.spawnAtLocation(item)
            }
            if (!splitedDiarys.contains("rdp3") && !diaryHasSelected) {
                diaryHasSelected = true

                val item = ItemStack(ModItems().RESEARHDIARYPARTTHREE.get(), 1)

                savedData.diarys += "rdp3,"
                savedData.setDirty()

                this.spawnAtLocation(item)
            }
        }
    }*/
}