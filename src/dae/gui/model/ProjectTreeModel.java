/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.gui.model;

import com.jme3.scene.Node;
import dae.prefabs.Prefab;
import dae.prefabs.ui.events.LayerEvent;
import dae.prefabs.ui.events.LevelEvent;
import dae.project.Layer;
import dae.project.Level;
import dae.project.Project;
import dae.project.ProjectTreeNode;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Koen Samyn
 */
public class ProjectTreeModel implements TreeModel {

    private ArrayList<TreeModelListener> listeners
            = new ArrayList<TreeModelListener>();
    private Project project;

    public ProjectTreeModel(Project project) {
        this.project = project;
    }

    public Object getRoot() {
        return project;
    }

    public Object getChild(Object parent, int index) {
        if (parent == project) {
            return project.getLevel(index);
        } else if (parent instanceof Level) {
            Level l = (Level) parent;
            return l.getLevelChild(index);
        } else if (parent instanceof Layer) {
            Layer layer = (Layer) parent;
            return layer.getChild(index);
        } else if (parent instanceof Prefab) {
            Prefab p = (Prefab) parent;
            return p.getPrefabChildAt(index);
        } else {
            return null;
        }
    }

    public int getChildCount(Object parent) {
        if (parent == project) {
            return project.getNrOfLevels();
        } else if (parent instanceof Level) {
            Level l = (Level) parent;
            return l.getChildCount();
        } else if (parent instanceof Layer) {
            Layer layer = (Layer) parent;
            return layer.getChildCount();
        } else if (parent instanceof Prefab) {
            Prefab p = (Prefab) parent;
            int childCount = p.getPrefabChildCount();
            return childCount;
        } else {
            return 0;
        }
    }

    public boolean isLeaf(Object node) {

        if (node instanceof Prefab) {
            Prefab p = (Prefab) node;
            boolean isLeaf = p.isLeaf();
            return p.isLeaf();
        } else {
            return false;
        }
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Object lastComponent = path.getLastPathComponent();
        if (lastComponent instanceof Project) {
            Project p = (Project) lastComponent;
            p.setProjectName(newValue.toString());
        } else if (lastComponent instanceof Level) {
            Level l = (Level) lastComponent;
            l.setName(newValue.toString());
        } else if (lastComponent instanceof Layer) {
            Layer layer = (Layer) lastComponent;
            layer.setName(newValue.toString());
        } else if (lastComponent instanceof Prefab) {
            Prefab p = (Prefab) lastComponent;
            p.setName(newValue.toString());
        }
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof ProjectTreeNode && child instanceof ProjectTreeNode) {
            ProjectTreeNode parentNode = (ProjectTreeNode) parent;
            ProjectTreeNode childNode = (ProjectTreeNode) child;
            int index = parentNode.getIndexOfChild(childNode);
            return index;
        } else {
            Logger.getLogger("DArtE").log(java.util.logging.Level.WARNING, "Index of {0} in {1} is -1", new Object[]{child, parent});
            return -1;
        }
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    /**
     * Called when the contents of the JTree has changed.
     *
     * @param le the LevelEvent object
     */
    public void levelChanged(final LevelEvent le) {
        if (le.getEventType() == LevelEvent.EventType.NODEADDED) {
            for (Node n : le.getNodes()) {

                TreeModelEvent event = createInsertTreeModelEvent((ProjectTreeNode) n);
                if (event != null) {
                    for (TreeModelListener tml : listeners) {
                        tml.treeNodesInserted(event);
                    }
                }
            }
        } else if (le.getEventType() == LevelEvent.EventType.NODEREMOVED) {
            for (Node n : le.getNodes()) {

                TreeModelEvent event = createDeleteTreeModelEvent(le.getLevel(), le.getPreviousParent(), le.getPreviousIndex(), n);
                if (event != null) {
                    if (n instanceof Prefab) {
                        Prefab p = (Prefab) n;
                        Layer l = le.getLevel().getLayer(p.getLayerName());
                        l.removeNode(p);
                    } else {
                        Logger.getLogger("DArtE").log(java.util.logging.Level.WARNING, "{0} is not an instanceof dae.prefabs.Prefab");
                    }
                    for (TreeModelListener tml : listeners) {
                        tml.treeNodesRemoved(event);
                    }
                }
            }
        } else if (le.getEventType() == LevelEvent.EventType.NODEMOVED) {
            // first remove the node from the previous parent.
            for (Node n : le.getNodes()) {
                TreeModelEvent event = createDeleteTreeModelEvent(le.getLevel(), le.getPreviousParent(), le.getPreviousIndex(), n);
                if (event != null) {
                    for (TreeModelListener tml : listeners) {
                        tml.treeNodesRemoved(event);
                    }
                }
            }
            // now add it to the correct node.
            // tree structure changed for parent node.
            if (le.getCurrentParent() != null) {
                TreeModelEvent event = createInsertTreeModelEvent((ProjectTreeNode) le.getCurrentParent());
                if (event != null) {
                    for (TreeModelListener tml : listeners) {
                        tml.treeStructureChanged(event);
                    }
                }
            }
        }
    }

    public Object[] createPathForNode(ProjectTreeNode start) {
        int depth = 1;
        ProjectTreeNode copy = start;
        while (start.getProjectParent() != null) {
            depth++;
            start = start.getProjectParent();
        }
        Object[] result = new Object[depth];
        for (int i = depth - 1; i >= 0; --i) {
            result[i] = copy;
            copy = copy.getProjectParent();
        }

        return result;
    }

    public void fireInsertLevelEvent(Level l, int index) {
        Object[] path = new Object[1];
        path[0] = this.project;
        int[] indices = {index};
        TreeModelEvent event = new TreeModelEvent(this, path, indices, new Object[]{l});

        for (TreeModelListener tml : listeners) {
            tml.treeNodesInserted(event);
        }
    }

    public TreeModelEvent createDeleteTreeModelEvent(Level level, Object previousParent, int previousIndex, Node n) {
        int depth = 1;

        ProjectTreeNode start = (ProjectTreeNode) previousParent;

        while (start.getProjectParent() != null) {
            ++depth;
            start = start.getProjectParent();
        }

        start = (ProjectTreeNode) previousParent;
        Object[] path = new Object[depth];
        for (int i = depth - 1; i >= 0; --i) {
            path[i] = start;
            start = start.getProjectParent();
        }

        int[] indices = {previousIndex};
        Object[] objects = {n};

        return new TreeModelEvent(this, path, indices, objects);
    }

    public TreeModelEvent createInsertTreeModelEvent(ProjectTreeNode n) {
        int depth = 0;

        ProjectTreeNode start = (ProjectTreeNode) n;
        ProjectTreeNode parent = start.getProjectParent();
        if (parent == null) {
            return null;
        }
        int index = parent.getIndexOfChild(start);

        while (start.getProjectParent() != null) {
            ++depth;
            start = start.getProjectParent();
        }

        Object[] path = new Object[depth];
        for (int i = depth - 1; i >= 0; --i) {
            path[i] = parent;
            parent = parent.getProjectParent();
        }

        int[] indices = {index};
        Object[] objects = {n};

        return new TreeModelEvent(this, path, indices, objects);
    }

    public void layerAdded(LayerEvent le) {
        Layer added = le.getLayer();
        TreeModelEvent layerAdd = createInsertTreeModelEvent(added);
        /*
        Layer added = le.getLayer();
        Object path[] = new Object[added.getNumParentLayers() + 2]; //  project and the level.
        path[0] = this.project;
        path[1] = added.getParentLevel();

        Layer start = added.getParentLayer();
        for (int index = path.length - 1; index > 1; --index) {
            path[index] = start;
            start = start.getParentLayer();
        }

        int[] indices = {added.getParentLayer().getIndexOfLayer(added)};
        Object[] objects = {added};

        TreeModelEvent event = new TreeModelEvent(this, path, indices, objects);
         */
        for (TreeModelListener tml : listeners) {
            tml.treeNodesInserted(layerAdd);
        }
    }

    public void fireDeleteLevelEvent(Level l, int index) {
        Object[] path = new Object[1];
        path[0] = this.project;
        int[] indices = {index};
        TreeModelEvent event = new TreeModelEvent(this, path, indices, new Object[]{l});

        for (TreeModelListener tml : listeners) {
            tml.treeNodesRemoved(event);
        }
    }
}
