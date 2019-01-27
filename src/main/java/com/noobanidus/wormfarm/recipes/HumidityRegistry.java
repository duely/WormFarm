package com.noobanidus.wormfarm.recipes;

import io.netty.util.internal.ConcurrentSet;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HumidityRegistry extends Registry<HumidityRegistry.HumidityEntry> {
    public static HumidityRegistry instance = new HumidityRegistry();

    public HumidityEntry getEmpty() {
        return HumidityEntry.EMPTY;
    }

    public static class HumidityEntry extends RegistryEntry {
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
    }
}
