import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.Set;


class ProblemaP2 {
    public String findOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>(); //lista de adyacencia   
        int[] inDegree = new int[26]; //grado de entrada de cada vertice utilizando el codigo ASCII de cada letra
        System.out.println(inDegree);
        buildGraph(graph, inDegree, words); //construimos el grafo
        return bfs(graph, inDegree); //hacemos BFS para encontrar el orden de las letras
    }

    private void buildGraph(Map<Character, Set<Character>> graph, int[] inDegree, String[] words) //metodo para construir el grafo
     {
        for (String Chars : words) // agregar todos los caracteres a la lista de adyacencia
        {
            for (char charVertex : Chars.toCharArray()) // agregar todos los caracteres a la lista de adyacencia
            {
                graph.putIfAbsent(charVertex, new HashSet<>()); // si no existe el vertice, lo agrega recordando que cada letra es un vertice y se le pone un set para los vertices adyacentes
            }
        }

        for (int i = 1; i < words.length; i++) //se recorre en la longitud de las palabras
        {
            //se comparan 2 palabras para ver si hay una relacion entre ellas, de forma que se hace letra por letra
            String String1 = words[i - 1]; //palabra anterior
            String String2 = words[i]; //palabra actual
            int minLen = Math.min(String1.length(), String2.length()); //obtenemos el minimo de caracteres entre las dos palabras
            for (int j = 0; j < minLen; j++)
            {
                char s1FirstChar = String1.charAt(j); //primer caracter de la palabra anterior
                char S2FirstChar = String2.charAt(j); //primer caracter de la palabra actual
                if (s1FirstChar != S2FirstChar) //se mira que los caracteres sean diferentes
                {                
                    if (!graph.get(s1FirstChar).contains(S2FirstChar)) // si no existe una relacion entre los caracteres 
                    {
                        graph.get(s1FirstChar).add(S2FirstChar); //se agrega la relacion entre los caracteres
                        inDegree[S2FirstChar - 'a']++; //se aumenta el grado de entrada del caracter
                    }
                    break; //se rompe el ciclo para pasar a la siguiente palabra
                }
                if (j + 1 == minLen && String1.length() > String2.length()) // si la palabra anterior es mayor que la actual
                {
                    graph.clear(); //se limpia el grafo
                    return;
                }
            }
        }
    }

    private String bfs(Map<Character, Set<Character>> graph, int[] inDegree) // metodo que usaremos para el bfs
    {
        StringBuilder stringBuilder = new StringBuilder(); //se crea un stringbuilder para almacenar el orden de las letras
        Queue<Character> queue = new LinkedList<>(); //se crea una cola para almacenar los vertices
        for (char vertex : graph.keySet()) //se recorre el grafo por sus vertices
        { 
            if (inDegree[vertex - 'a'] == 0) //si el grado de entrada es 0, es decir que no tiene ninguna relacion con otro vertice
            { 
                queue.offer(vertex); //se agrega a la cola
            }
        }

        while (!queue.isEmpty()) //mientras la cola no esté vacía
        { 
            char VertexOut = queue.poll(); //se saca el primer elemento de la cola
            stringBuilder.append(VertexOut); //se agrega al stringbuilder
            for (char VertexIN : graph.get(VertexOut)) //se recorre el grafo por sus vertices
            { 
                inDegree[VertexIN - 'a']--; //se disminuye el grado de entrada del vertice
                if (inDegree[VertexIN - 'a'] == 0) //si el grado de entrada es 0, es decir que no tiene ninguna relacion con otro vertice
                { 
                    queue.offer(VertexIN); //se agrega a la cola
                }
            } 
        }
        if (stringBuilder.length() != graph.size()) //si el tamaño del stringbuilder es diferente al tamaño del grafo
        {
            return "ERROR"; //se retorna ERROR
            //esto implica que encontró un orden de las letras que no es posible y que no utiliza todas las letras
        }
        else 
        {
            return stringBuilder.toString(); //se retorna el stringbuilder conviertiendolo a string
        }

       
    }



public static void main(String[] args) {
ProblemaP2 p = new ProblemaP2();
String[] dict1 = {"h","hjh","hjmh","hm","j","jjm","m","mh","mmhj"};
String[] dict3 = {"ab","ac","cc","cb"};
String[] dict2 = {"xx", "xp", "pj", "jjj" ,"jjw"};
String[] dict4 = {"ab" ,"ah","an" ,"mn" ,"mk"};

long inicio = System.currentTimeMillis();
System.out.println(p.findOrder(dict1));
System.out.println(p.findOrder(dict2));
System.out.println(p.findOrder(dict3));
System.out.println(p.findOrder(dict4));
long fin = System.currentTimeMillis();
System.out.println("Tiempo: " + (fin - inicio) + " ms");
    } 
}
