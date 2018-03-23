package tree.bintree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reference:
 * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 */

public class BinTreePrinter {

    public static <T> void printNode(BinTreeNode<T> root) {
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T> void printNodeInternal(List<BinTreeNode<T>> nodes,
                                              int level,
                                              int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) {
            return;
        }

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<BinTreeNode<T>> newNodes = new ArrayList<>();
        for (BinTreeNode<T> binTreeNode : nodes) {
            if (binTreeNode != null) {
                System.out.print(binTreeNode.getData());
                newNodes.add(binTreeNode.getLeft());
                newNodes.add(binTreeNode.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null) {
                    System.out.print("/");
                } else {
                    printWhitespaces(1);
                }

                printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null) {
                    System.out.print("\\");
                } else {
                    printWhitespaces(1);
                }

                printWhitespaces(endgeLines + endgeLines - i);
            }
            System.out.println("");
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T> int maxLevel(BinTreeNode<T> binTreeNode) {
        if (binTreeNode == null) {
            return 0;
        }

        return Math.max(maxLevel(binTreeNode.getLeft()),
                maxLevel(binTreeNode.getRight())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }
}
