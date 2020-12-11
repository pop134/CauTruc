
import java.awt.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ni3
 */
class NodeTree{
    double val;
    NodeTree left, right;
    
}
public class GraphicalTree {
    NodeTree root;
    
    GraphicalTree()
    {
        root = null;
        
    }
    int max(int a, int b)
    {
        if(a <= b)
            return b;
        return a;
    }
            
    int depth(NodeTree t) {
	if(t == null)
		return 0;
	return 1 + max(depth(t.left), depth(t.right));
    }

    void insert(String v,Graphics g)
    {
        double va = Double.parseDouble(v);
        if(root == null)
        {
            root = new NodeTree();
            root.left = null;
            root.right= null;
            root.val = va;
            return;
        }
        NodeTree parent = null;
        NodeTree tmp = root;
        while(tmp != null)
        {
            if(va < tmp.val)
            {
                parent = tmp;
                tmp = tmp.left;
            }
            else
            {
                parent = tmp;
                tmp = tmp.right;
            }
        }
        tmp = new NodeTree();
        tmp.left = null;
        tmp.right= null;
        tmp.val = va;
             
        if(va < parent.val)
        {
           parent.left = tmp; 
        }
        else
        {
           parent.right = tmp; 
        }
    }
    
      
    void draw(NodeTree t,Graphics g,int x,int y,int prevx,int prevy,int lev,int gap)
    {   

	if(t == null)
		return;
	g.drawOval(x, y, 30, 30);
	if((++lev) != 1)
        {
		g.drawLine(prevx + 15,prevy + 30,x + 15,y);
                gap = (gap  )/2;
        }
      
	g.drawString(""+t.val,x+2,y+17);
	draw(t.left,g,x-gap,y+50,x,y,lev,gap);
	draw(t.right,g,x+gap,y+50,x,y,lev,gap);
    }

     /*
    void draw(NodeTree t,Graphics g, int x, int y, int prevx,int prevy,int lev,int gap)
    {
        int xoff = 100;
        if(t == null)
            return;
        
        g.drawOval(x, y, 30, 30);
        if((++lev) != 1)
		g.drawLine(prevx,prevy+15,x,y-15);
        
        g.drawString(""+t.val, x +2 , y + 17);
        g.drawLine(x + 30, y + 30, x + xoff + 30, y + 60);
        draw(t.left,g, x - xoff,y + 60 );
        draw(t.right,g,x+ xoff,y + 60);
        
    }*/
    int delete(String v,Graphics g)
    {
        double va = Double.parseDouble(v);
        if(root == null)
        {
            return -1;
        }
        NodeTree parent = null;
        NodeTree tmp = root;
        while(tmp != null && tmp.val != va)
        {
            if(va < tmp.val)
            {
                parent = tmp; 
                tmp = tmp.left;
            }
            else
            {
                parent = tmp; 
                tmp = tmp.right;
            }
        }
        if(tmp.val == va)
        {
            if(tmp.left == null && tmp.right == null)
            {
              	if(tmp == root)
                { 
                    root = null;
                    return 1;
                }
                if(va < parent.val)
                    parent.left = null;
                else
                    parent.right = null;
                        
                return 1;
	    }
            if(tmp.left == null) {
                    if(tmp == root) {
                            root = tmp.right;
                            return 1;
                    }
                    if(va < parent.val)
                            parent.left = tmp.right;
                    else 
                            parent.right = tmp.right;
                    return 1;
            }

            if(tmp.right == null) {
                   
                    if(tmp == root) {
                            root = tmp.left;
                            return 1;
                    }
                    if(va < parent.val)
                            parent.left = tmp.left;
                    else 
                            parent.right = tmp.left;
                    return 1;
            }

            NodeTree rparent = null;
            NodeTree r = tmp.right;
            NodeTree rahead = r.left;
            while(rahead != null) {
                    rparent = r ;
                    r = rahead;
                    rahead = rahead.left;
            }

            tmp.val = r.val;
            if(tmp.right.left == null ) 
                    tmp.right = r.right;
            else
                    rparent.left = r.right;
            return 1;
        }
        else
        {
            return -1;
        }
        
    }
    
    int find(String v,Graphics g)
    {
        double va = Double.parseDouble(v);
        if(root == null)
        {
            return -1;
        }
        NodeTree tmp = root;
        while(tmp != null && tmp.val != va)
        {
            if(va < tmp.val)
            {
                tmp = tmp.left;
            }
            else
            {
                tmp = tmp.right;
            }
        }
             
        if(tmp == null)
        {
            return -1;
        }
        else
        {
            return 1;
        }
        
    }
}
