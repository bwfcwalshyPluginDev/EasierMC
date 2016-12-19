package me.ialistannen.itemrecipes.easiermc.util;

import com.perceivedev.perceivecore.gui.components.panes.tree.TreePaneNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A History with nodes the player visited
 */
public enum PlayerHistory {
    INSTANCE;

    private Map<UUID, HistoryObject> uuidHistoryMap = new HashMap<>();

    /**
     * Adds the Player to the history manager
     *
     * @param uuid The {@link UUID} of the Player
     */
    public void addPlayerHistory(UUID uuid) {
        uuidHistoryMap.put(uuid, new HistoryObject());
    }

    /**
     * Removes the Player from the manager
     *
     * @param uuid The {@link UUID} of the Player
     */
    public void removePlayer(UUID uuid) {
        uuidHistoryMap.remove(uuid);
    }

    /**
     * @param uuid The {@link UUID} of the Player
     * @return The History for the Player
     */
    public Optional<HistoryObject> getPlayerHistory(UUID uuid) {
        return Optional.ofNullable(uuidHistoryMap.get(uuid));
    }

    /**
     * @param uuid The {@link UUID} of the Player
     * @return True if there is a history Object saved for the Player
     */
    public boolean containsPlayerHistory(UUID uuid) {
        return uuidHistoryMap.containsKey(uuid);
    }

    /**
     * @param uuid The {@link UUID} of the Player
     * @return True if the Player has a previous Node
     */
    public boolean hasPreviousNode(UUID uuid) {
        return getPlayerHistory(uuid)
                .map(historyObject -> historyObject.getLast().isPresent())
                .orElse(false);
    }

    /**
     * @param uuid The {@link UUID} of the Player
     * @return The last {@link TreePaneNode} he was at
     */
    public Optional<TreePaneNode> getPreviousNode(UUID uuid) {
        return getPlayerHistory(uuid)
                .map(HistoryObject::getLast)
                .orElse(Optional.empty());
    }

    /**
     * Removes the previous node
     *
     * @param uuid The {@link UUID} of the Player
     */
    public void removePreviousNode(UUID uuid) {
        getPlayerHistory(uuid).ifPresent(historyObject -> historyObject.pollAndGetLast());
    }

    /**
     * Adds the node to the Player history
     *
     * @param uuid The {@link UUID} of the Player
     * @param node The {@link TreePaneNode} to add
     */
    public void addToPlayerHistory(UUID uuid, TreePaneNode node) {
        if (!containsPlayerHistory(uuid)) {
            addPlayerHistory(uuid);
        }

        getPlayerHistory(uuid)
                .ifPresent(historyObject -> historyObject.addNode(node));
    }

    public static class HistoryObject {
        private LinkedList<TreePaneNode> nodeStack = new LinkedList<>();

        /**
         * Adds a node to the History Object
         *
         * @param treePaneNode The Node to add
         */
        public void addNode(TreePaneNode treePaneNode) {
            nodeStack.push(treePaneNode);
        }

        /**
         * @return The node he is at
         */
        public Optional<TreePaneNode> getCurrent() {
            return Optional.ofNullable(nodeStack.peek());
        }

        /**
         * @return The last node he was at
         */
        public Optional<TreePaneNode> getLast() {
            return Optional.ofNullable(nodeStack.peek());
        }

        /**
         * Pops the current node from the stack and returns the node below
         *
         * @return The last node he was at
         */
        public Optional<TreePaneNode> pollAndGetLast() {
            nodeStack.poll();

            return getCurrent();
        }
    }
}
