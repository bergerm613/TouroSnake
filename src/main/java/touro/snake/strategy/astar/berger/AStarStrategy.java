package touro.snake.strategy.astar.berger;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;
import touro.snake.strategy.astar.Node;

import java.util.ArrayList;
import java.util.HashSet;

public class AStarStrategy implements SnakeStrategy {
    Direction[] directions = Direction.values();

    @Override
    public void turnSnake(Snake snake, Garden garden) {
        Node startNode = new Node(snake.getHead());
        Food endNode = garden.getFood();

        if (endNode == null) {
            return;
        }

        ArrayList<Node> openNodes = new ArrayList<>();
        HashSet<Node> closedNodes = new HashSet<>();
        openNodes.add(startNode);

        while (true) {
            Node currentNode = getLowestNode(openNodes);
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);

            if (currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()) {
                Direction bestDirection = getFirstTurn(startNode, currentNode);
                snake.turnTo(bestDirection);
                break;
            }

            for (Direction d : directions) {
                Node neighbor = new Node(currentNode.moveTo(d), currentNode, endNode);
                if (snake.contains(neighbor) ||
                    !neighbor.inBounds() ||
                    closedNodes.contains(neighbor)) {
                    continue;
                }

                double newCostToNeighbor = currentNode.fromStart + currentNode.distance(neighbor);
                if (newCostToNeighbor < neighbor.fromStart || !openNodes.contains(neighbor)) {
                    openNodes.add(neighbor);
                }
            }
        }
    }

    private Direction getFirstTurn(Node startNode, Node currentNode) {
        while (currentNode.getParent() != startNode) {
            currentNode = currentNode.getParent();
        }

        Direction direction = null;
        if (startNode.getX() + 1 == currentNode.getX()) {
            direction = Direction.East;
        }
        else if (startNode.getX() - 1 == currentNode.getX()) {
            direction = Direction.West;
        }
        else if (startNode.getY() - 1 == currentNode.getY()) {
            direction = Direction.North;
        }
        else if (startNode.getY() + 1 == currentNode.getY())  {
            direction = Direction.South;
        }
        return direction;
    }


    private Node getLowestNode(ArrayList<Node> openNodes) {
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
}