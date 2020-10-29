package touro.snake.strategy.astar.berger;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;
import touro.snake.strategy.astar.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AStarStrategy implements SnakeStrategy {
    Direction[] directions = Direction.values();

    private List<Square> pathList = new ArrayList<>();
    private List<Square> consideredSquaresList = new ArrayList<>();

    @Override
    public void turnSnake(Snake snake, Garden garden) {
        Node startNode = new Node(snake.getHead());
        Food endNode = garden.getFood();

        if (endNode == null) {
            return;
        }

        List<Node> openNodes = new ArrayList<>();
        HashSet<Node> closedNodes = new HashSet<>();
        openNodes.add(startNode);

        while (!openNodes.isEmpty()) {
            Node currentNode = getLowestNode(openNodes);
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);

            if (currentNode.equals(endNode)) {
                updateConsideredSquaresList(openNodes, closedNodes);
                Direction bestDirection = getFirstTurn(startNode, currentNode);
                snake.turnTo(bestDirection);
                break;
            }

            for (Direction d : directions) {
                Node neighbor = new Node(currentNode.moveTo(d), currentNode, endNode);
                if (!closedNodes.contains(neighbor) && !snake.contains(neighbor) && neighbor.inBounds()) {
                    addNeighborToOpen(openNodes, neighbor);
                }
            }
        }
    }

    private void updateConsideredSquaresList(List<Node> openNodes, HashSet<Node> closedNodes) {
        consideredSquaresList.clear();
        consideredSquaresList.addAll(openNodes);
        consideredSquaresList.addAll(closedNodes);
    }

    private void addNeighborToOpen(List<Node> openNodes, Node neighbor) {
        int neighborIndex = openNodes.indexOf(neighbor);
        if (neighborIndex != -1) {
            Node oldNeighbor = openNodes.get(neighborIndex);

            if (neighbor.getCost() < oldNeighbor.getCost()) {
                openNodes.set(neighborIndex, neighbor);
            }
        }
        else {
            openNodes.add(neighbor);
        }
    }

    private Direction getFirstTurn(Node startNode, Node currentNode) {
        pathList.clear();
        while (!currentNode.getParent().equals(startNode)) {
            currentNode = currentNode.getParent();
            pathList.add(currentNode);
        }

        return startNode.directionTo(currentNode);
    }


    private Node getLowestNode(List<Node> openNodes) {
        Node lowestNode = null;
        double minVal = Double.MAX_VALUE;

        for (Node node : openNodes) {
            if (node.getCost() < minVal) {
                lowestNode = node;
                minVal = lowestNode.getCost();
            }
        }
        return lowestNode;
    }

    @Override
    public List<Square> getPath() {
        return pathList;
    }

    @Override
    public List<Square> getSearchSpace() {
        return consideredSquaresList;
    }
}