package graph;

public class my_main {
    public static void main(String[] args) {
        Agent a=new Agent(5,1,2,3,3,2,2);
        a.LoadFromJson("C:\\Users\\shira\\Desktop\\pok.json");
        System.out.println(a);
    }
}
