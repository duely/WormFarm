package com.noobanidus.wormfarm.recipes;

import com.noobanidus.wormfarm.WormFarm;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HumidityRegistry extends Registry<HumidityRegistry.HumidityEntry> {
    private static HumidityRegistry instance = new HumidityRegistry();

    public static HumidityRegistry getInstance() {
        return instance;
    }

    public HumidityEntry getEmpty() {
        return HumidityEntry.EMPTY;
    }

    @Override
    public void addEntry (HumidityEntry entry) {
        if (findEntry(entry.getHumidity()) != HumidityEntry.EMPTY) {
            WormFarm.LOG.warn(String.format("Attempted to register a duplicate humidity entry: %s", entry.getHumidity()));
        } else {
            super.addEntry(entry);
        }
    }

    public void replaceEntry (HumidityEntry entry) {
        if (removeEntry(entry.getHumidity())) {
            addEntry(entry);
        } else {
            WormFarm.LOG.warn(String.format("Tried to modify a humidity entry `%s` but it didn't previously exist.", entry.getHumidity()));
        }
    }

    public HumidityEntry addEntry (String humidity, int burnTime, float matchModifier, float conflictModifier) {
        HumidityEntry entry = new HumidityEntry(humidity, burnTime, matchModifier, conflictModifier);
        addEntry(entry);
        return entry;
    }

    public MatchType matchType (SoilRegistry.SoilEntry soil, OrganicRegistry.OrganicEntry organic) {
        HumidityEntry soilEntry = instance.findEntry(soil.getHumidity());
        HumidityEntry organicEntry = instance.findEntry(organic.getHumidity());

        return soilEntry.matchType(organicEntry);
    }

    // Probably unneeded
    public MatchType matchType (ItemStack soil, ItemStack organic) {
        SoilRegistry.SoilEntry soilEntry = SoilRegistry.getInstance().findEntry(soil);
        if (soilEntry == SoilRegistry.SoilEntry.EMPTY) return MatchType.INVALID;

        OrganicRegistry.OrganicEntry organicEntry = OrganicRegistry.getInstance().findEntry(organic);
        if (organicEntry == OrganicRegistry.OrganicEntry.EMPTY) return MatchType.INVALID;

        HumidityEntry a = instance.findEntry(soilEntry.getHumidity());
        HumidityEntry b = instance.findEntry(organicEntry.getHumidity());

        return a.matchType(b);
    }

    public static class HumidityEntry extends Registry.RegistryEntry {
        public final static HumidityEntry EMPTY = new HumidityEntry("", -1, -1, -1);
        // This is probably overkill
        private final Set<String> matches = new ConcurrentSet<>();
        private final Set<String> conflicts = new ConcurrentSet<>();
        private String humidity;

        HumidityEntry(String humidity, int burnTime, float matchModifier, float conflictModifier) {
            super(burnTime, matchModifier, conflictModifier, "humidity");
            this.humidity = humidity;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public boolean addMatch(String match) {
            return matches.add(match);
        }

        public boolean addMatches(String... match) {
            return matches.addAll(Arrays.asList(match));
        }

        public boolean addConflict(String conflict) {
            return conflicts.add(conflict);
        }

        public boolean addConflicts(String... conflict) {
            return conflicts.addAll(Arrays.asList(conflict));
        }

        public boolean removeMatch(String match) {
            return matches.remove(match);
        }

        public boolean removeMatches(String... match) {
            return matches.removeAll(Arrays.asList(match));
        }

        public void clearMatches() {
            matches.clear();
        }

        public boolean removeConflict(String conflict) {
            return conflicts.remove(conflict);
        }

        public boolean removeConflicts(String... conflict) {
            return conflicts.removeAll(Arrays.asList(conflict));
        }

        public void clearConflicts() {
            conflicts.clear();
        }

        List<HumidityEntry> getEntries(Set<String> source) {
            List<HumidityEntry> entries = new ArrayList<>();
            source.forEach((a) -> {
                HumidityEntry entry = HumidityRegistry.instance.findEntry(a);

                // No empty entries or self-references.
                if (entry != HumidityEntry.EMPTY && !entry.getHumidity().equalsIgnoreCase((a))) {
                    entries.add(entry);
                }
            });
            return entries;
        }

        public List<HumidityEntry> getMatches() {
            return getEntries(matches);
        }

        public List<HumidityEntry> getConflicts() {
            return getEntries(conflicts);
        }

        @Override
        public boolean compareStack(ItemStack stack) {
            return false;
        }

        @Override
        public boolean compareString(String string) {
            return false;
        }

        public MatchType matchType (HumidityEntry otherEntry) {
            if (this == HumidityEntry.EMPTY || otherEntry == HumidityEntry.EMPTY) return MatchType.INVALID;

            if (this.getMatches().contains(otherEntry)) return MatchType.MATCH;

            if (this.getConflicts().contains(otherEntry)) return MatchType.CONFLICT;

            return MatchType.NEUTRAL;
        }

        public boolean isEmpty () {
            return this == EMPTY;
        }
    }

    public enum MatchType {
        INVALID("invalid"),
        CONFLICT("conflict"),
        NEUTRAL("neutral"),
        MATCH("match");

        private String key;

        MatchType (String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}

