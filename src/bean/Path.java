package bean;

import java.util.Stack;

public class Path implements Cloneable{
    Stack<String> vertices;
    
    public Path(){
        vertices = new Stack<>();
    }
    
    public Path(Stack<String> v){
        vertices = v;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Path clone(){
        Path p = new Path((Stack<String>) vertices.clone());
        return p;
        
    }
    
    public void print(){
        while (!vertices.isEmpty()){
            System.out.print(vertices.pop());
        }
    }
}
