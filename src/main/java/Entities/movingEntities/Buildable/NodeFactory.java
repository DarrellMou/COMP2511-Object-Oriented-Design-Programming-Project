package Entities.movingEntities.Buildable;

import java.util.ArrayList;
import java.util.List;

public class NodeFactory {
    public static Node makeTree(String buildable) {
        if (buildable.equals("bow")) {
            List<Node> nodes = new ArrayList<>();
            nodes.add(new LeafNode("wood"));
            nodes.add(new LeafNode("arrow"));
            nodes.add(new LeafNode("arrow"));
            nodes.add(new LeafNode("arrow"));
            
            return new AND(nodes);
        }
        else if (buildable.equals("shield")) {
            List<Node> ORNodes = new ArrayList<>();
            ORNodes.add(new LeafNode("treasure"));
            ORNodes.add(new LeafNode("sun_stone"));
            ORNodes.add(new LeafNode("key"));
            
            List<Node> nodes = new ArrayList<>();
            nodes.add(new LeafNode("wood"));
            nodes.add(new LeafNode("wood"));
            nodes.add(new OR(ORNodes));

            return new AND(nodes);
        }
        else if (buildable.equals("sceptre")) {
            List<Node> ANDNodes = new ArrayList<>();
            ANDNodes.add(new LeafNode("arrow"));
            ANDNodes.add(new LeafNode("arrow"));

            List<Node> ORNodes1 = new ArrayList<>();
            ORNodes1.add(new LeafNode("wood"));
            ORNodes1.add(new AND(ANDNodes));
            
            List<Node> ORNodes2 = new ArrayList<>();
            ORNodes2.add(new LeafNode("treasure"));
            ORNodes2.add(new LeafNode("sun_stone"));
            ORNodes2.add(new LeafNode("key"));

            List<Node> nodes = new ArrayList<>();
            nodes.add(new LeafNode("sun_stone"));
            nodes.add(new OR(ORNodes1));
            nodes.add(new OR(ORNodes2));

            return new AND(nodes);
        }
        else if (buildable.equals("midnight_armour")) {
            List<Node> nodes = new ArrayList<>();
            nodes.add(new LeafNode("armour"));
            nodes.add(new LeafNode("sun_stone"));
            
            return new AND(nodes);
        }
        return null;
    }
}
