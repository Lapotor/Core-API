package minevalley.core.api;

import com.google.gson.Gson;
import lombok.NonNull;
import minevalley.core.api.corporations.Group;
import minevalley.core.api.corporations.StateCompany;
import minevalley.core.api.corporations.business.Aktiengesellschaft;
import minevalley.core.api.corporations.business.Einzelunternehmen;
import minevalley.core.api.corporations.business.Kapitalgesellschaft;
import minevalley.core.api.corporations.business.Personengesellschaft;
import minevalley.core.api.database.DatabaseEntry;
import minevalley.core.api.database.DatabaseEntryCollection;
import minevalley.core.api.database.DatabaseTable;
import minevalley.core.api.database.Value;
import minevalley.core.api.economy.BankAccount;
import minevalley.core.api.enums.DebugType;
import minevalley.core.api.enums.Server;
import minevalley.core.api.modulepipeline.Container;
import minevalley.core.api.modulepipeline.PipelineReceiver;
import minevalley.core.api.phone.Telephone;
import minevalley.core.api.regions.*;
import minevalley.core.api.timing.Reminder;
import minevalley.core.api.timing.RepeatingTimer;
import minevalley.core.api.timing.Timer;
import minevalley.core.api.users.OnlineUser;
import minevalley.core.api.users.User;
import minevalley.core.api.utils.*;
import minevalley.core.api.utils.armorstand.FakeArmorStand;
import minevalley.core.api.utils.command.PlayerCommand;
import minevalley.core.api.utils.gui.GuiBuilder;
import minevalley.core.api.utils.gui.GuiItem;
import minevalley.core.api.utils.gui.MultiPageGui;
import minevalley.smart.api.Session;
import minevalley.smart.api.SmartApp;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface CoreServer {

    JavaPlugin getInstance();

    int runSyncTaskLater(String taskName, long delay, Runnable runnable);

    int runAsyncTaskLater(String taskName, long delay, Runnable runnable);

    int runSyncTaskPeriodically(String taskName, long delay, long period, Runnable runnable);

    int runAsyncTaskPeriodically(String taskName, long delay, long period, Runnable runnable);

    boolean isCurrentlyRunning(int taskId);

    void cancelTask(int taskId);

    void registerListener(Class<? extends Event> cls, EventListener listener);

    void unregisterListener(Class<? extends Event> cls, EventListener listener);

    void registerListener(Listener listener);

    void registerPipelineReceiver(PipelineReceiver pipeLineManager);

    void sendPipelineContainer(String pipelineName, Container container);

    void registerCommand(PlayerCommand command);

    void sendTeamChatMessage(String message);

    void sendTeamChatMessage(BaseComponent[] message);

    void sendDebug(DebugType type, String message);

    DatabaseEntry getDatabaseEntry(String tableName, Value searchValue);

    DatabaseEntry getDatabaseEntryAnd(String tableName, Value... searchValues);

    DatabaseEntry getDatabaseEntryOr(String tableName, Value... searchValues);

    DatabaseEntryCollection getDatabaseEntryCollection(String tableName, Value searchValue);

    DatabaseEntryCollection getDatabaseEntryCollectionAnd(String tableName, Value... searchValues);

    DatabaseEntryCollection getDatabaseEntryCollectionOr(String tableName, Value... searchValues);

    DatabaseTable getDatabaseTable(String tableName);

    void setSetting(String key, String value);

    String getSetting(String key);

    void updateStatistic(String key, double value);

    double getStatistic(String key);

    User getUser(String uuid);

    OnlineUser getOnlineUser(Player player);

    OnlineUser getOnlineUser(Session session);

    ChatMenu createChatMenu(ChatMenu.Option... options);

    ChatMenu createChatMenu();

    void setMetadata(Metadatable metadatable, String key, Object value);

    void removeMetadata(Metadatable metadatable, String key);

    List<MetadataValue> getMetadata(Metadatable metadatable, String key);

    Gson getGson();

    String getName(String uniqueId);

    String getUniqueId(String name);

    String removeColorCodes(String text);

    String convertColorCodes(String text);

    int randomInteger(int chars);

    boolean isInteger(String string);

    boolean isDouble(String string);

    String formatMoney(int amountInCents);

    String formatRelativeTimestamp(long time);

    GuiBuilder createGui(int size);

    MultiPageGui createMultiPageGui(int size);

    GuiItem createGuiItem(ItemStack itemStack, Consumer<OnlineUser> consumer);

    GuiItem createAdvancedGuiItem(ItemStack itemStack, BiConsumer<OnlineUser, ClickType> consumer);

    GuiItem createAdvancedGuiItem(ItemStack itemStack, TriConsumer<OnlineUser, ClickType, Inventory> consumer);

    Countdown createCountdown();

    void startCountdown(Countdown countdown);

    void stopCountdown(Countdown countdown);

    ItemBuilder createItem(ItemStack itemStack);

    ItemBuilder createItem(Material material);

    ItemBuilder createItem(Material material, int data);

    ItemBuilder createItem(Player player);

    ItemBuilder createItem(UUID uniqueId);

    ItemBuilder createItem(String url);

    ItemBuilder createItem(String value, String signature);

    Inventory getInventoryFromString(String inventory);

    String getStringFromInventory(Inventory inventory);

    BankAccount getBankAccount(String iban);

    BankAccount getBankAccount(ItemStack bankingCard);

    BankAccount createBankAccount(Registrant holder);

    Group getGroup(String name);

    Region getRegion(int id);

    Region createRegion(List<Area> areas);

    List<Region> getRegions(Block block);

    Residence getResidence(int id);

    Residence getResidence(Region region);

    Plot createPlot(Region region, Street street, Registrant owner, int fertility, Sign plotSign, Block mailboxBlock,
                    Area mailboxConnectedBlocks, Location teleportLocation);

    Apartment createApartment(Region region, Registrant landlord, int fertility, int rent, Sign apartmentSign,
                              Block mailbox);

    Apartment createApartment(Region region, ApartmentBlock block, int rent, Sign apartmentSign, Block mailbox);

    ApartmentBlock createApartmentBlock(Street street, Location teleportLocation, Registrant landlord, int fertility,
                                        Block mailboxBlock, Area mailboxConnectedBlocks, Sign apartmentBlockSign,
                                        Sign bellSign, int maxFloors, int defaultFloor, Area defaultFloorShadow,
                                        List<Area> roofShadows, Area constructionFloorShadow, Vector constructionWorkerLocation,
                                        List<Location> craftsmanLocations, Block[] damagedFloorBlocks);

    ApartmentBlock createApartmentBlock(Street street, Location teleportLocation, Registrant landlord, int fertility,
                                        Block mailboxBlock, Area mailboxConnectedBlocks, Sign apartmentBlockSign,
                                        Sign bellSign, int floors, List<Location> craftsmanLocations, Block[] damagedFloorBlocks);

    List<Residence> getLoadedResidences();

    List<Street> getStreets();

    Street getStreet(int id);

    Street createStreet(String name, String description);

    District getDistrict(int id);

    District getDistrict(Chunk chunk);

    void updateDistricts();

    List<District> getDistricts();

    RadioMast createRadioMast(String name, Block location, int range);

    RadioMast getNearestRadioMast(Location location);

    Area getAreaFromString(String rawArea);

    Area getArea(Block loc1, Block loc2);

    void transferFromShadow(Area... areas);

    void loadPreset(Area presetArea, Block presetPivot, Block mainWorldPivot);

    void restartAndClean();

    FakeBlock createFakeBlock(Block block, Material material, int data);

    boolean containsForForbiddenWords(String string);

    Telephone getTelephone(String telephoneNumber);

    Registrant getRegistrant(String string);

    Boundary createBoundary(@NonNull Location anchorPoint, @NonNull Location pullPoint, @NonNull Consumer<PlayerInteractEvent> callback);

    Boundary createBoundary(@NonNull Location anchorPoint, @NonNull Vector vector, @NonNull Consumer<PlayerInteractEvent> callback);

    Timer startTimer(int delay, @NonNull Runnable callback);

    RepeatingTimer startRepeatingTimer(int period, @NonNull Runnable callback);

    Reminder createReminder(int hours, int minutes, @NonNull Runnable callback, DayOfWeek... weekdays);

    Reminder createReminder(int hours, int minutes, @NonNull Runnable callback, List<DayOfWeek> weekdays);

    Webhook createWebhook(String url);

    Webhook.EmbeddedMessage createEmbeddedMessage();

    boolean isMaintenance();

    NPC createNPC(UUID skinUniqueId, String name, Location location, boolean focusNearPlayers, boolean visibleToEveryone);

    NPC createNPC(UUID skinUniqueId, UUID uniqueId, String name, Location location, boolean focusNearPlayers, boolean visibleToEveryone);

    NPC createNPC(String value, String signature, String name, Location location, boolean focusNearPlayers, boolean visibleToEveryone);

    void registerSmartApp(SmartApp app);

    List<SmartApp> getSmartApps();

    List<OnlineUser> getOnlineUsers();

    CarBarrier createCarBarrier(Location loc, int rotation, List<Block> barrierBlocks);

    Hologram createHologram(Location loc, boolean visibleToEveryone, String... lines);

    String itemStackToString(ItemStack itemStack);

    ItemStack itemStackFromString(String itemStack);

    String locationToString(Location location);

    Location locationFromString(String location);

    String blockToString(Block block);

    Block blockFromString(String block);

    int convertHexToDecimalColor(String hex);

    Einzelunternehmen createEinzelunternehmen(User owner);

    Personengesellschaft createPersonengesellschaft(User owner, List<User> coOwners);

    Kapitalgesellschaft createKapitalgesellschaft(int address);

    StateCompany createStateCompany(int address, StateCompany.Sector sector);

    Aktiengesellschaft createAktiengesellschaft(int address, int stocks);

    List<StateCompany> getStateCompanies();

    FakeArmorStand createArmorStand(Location location);

    Server getServerType();

    String convertToTransparent(String text);

}