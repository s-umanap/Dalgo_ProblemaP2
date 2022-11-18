import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;


class ProblemaP2 { 
    public String findOrder(String[] words) { // O(n) 
        int[] inDegree = new int[26]; //grado de entrada de cada vertice utilizando el codigo ASCII de cada letra
		Map<Character, Set<Character>> graph = new HashMap<>(); //lista de adyacencia  
        buildGraph(graph, inDegree, words); //construimos el grafo
        return bfs(graph, inDegree); //hacemos BFS para encontrar el orden de las letras
    }

    private void buildGraph(Map<Character, Set<Character>> graph, int[] inDegree, String[] words) //metodo para construir el grafo O(n)-> n = vertices
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
            String String1 = words[i-1]; //palabra anterior
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
                        inDegree[S2FirstChar-'a']++; //se aumenta el grado de entrada del caracter
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
    private String bfs(Map<Character, Set<Character>> graph, int[] inDegree){ //metodo para hacer BFS O(n+e) -> n = vertices
        StringBuilder stringBuilder = new StringBuilder(); //se crea un stringbuilder para almacenar el orden de las letras
        Queue<Character> queue = new LinkedList<>(); //se crea una cola para almacenar los vertices
        for (char vertex : graph.keySet()) //se recorre el grafo por sus vertices
        { 
            if (inDegree[vertex-'a'] == 0) //si el grado de entrada es 0, es decir que no tiene ninguna relacion con otro vertice
            { 
                queue.offer(vertex); //se agrega a la cola
            }
        }

        while (!queue.isEmpty()) //mientras la cola no estÃ© vacÃ­a
        { 
            char VertexOut = queue.poll(); //se saca el primer elemento de la cola
            stringBuilder.append(VertexOut); //se agrega al stringbuilder
            for (char VertexIN : graph.get(VertexOut)) //se recorre el grafo por sus vertices
            { 
                inDegree[VertexIN-'a']--; //se disminuye el grado de entrada del vertice
                if (inDegree[VertexIN- 'a'] == 0) //si el grado de entrada es 0, es decir que no tiene ninguna relacion con otro vertice
                { 
                    queue.offer(VertexIN); //se agrega a la cola
                }
            } 
        }
        if (stringBuilder.length() != graph.size()) //si el tamaÃ±o del stringbuilder es diferente al tamaÃ±o del grafo
        {
            return "ERROR"; //se retorna ERROR
            //esto implica que encontró un orden de las letras que no es posible y que no utiliza todas las letras
        }
        else 
        {
            return stringBuilder.toString(); //se retorna el stringbuilder conviertiendolo a string 
        }

       
    }
public static void main(String[] args) throws IOException  { 
	ProblemaP2 instance = new ProblemaP2();
	try (  //se crea un try con recursos para leer el archivo
			InputStreamReader inputReader= new InputStreamReader(System.in); //se crea un lector de entrada
			BufferedReader Buffered = new BufferedReader(inputReader); // se crea un buffer para leer la entrada
			) { 
		String line = Buffered.readLine(); //se lee la primera linea
		int cases = Integer.parseInt(line); //se convierte a entero
		line = Buffered.readLine(); //se lee la segunda linea
		for(int i=0;i<cases && line!=null && line.length()>0 ;i++) //se recorre el numero de casos
		{
			String[] lineStr = line.split("\\s+"); //se separa la linea por espacios
			int pages_words = Integer.parseInt(lineStr[1]);  //se convierte a entero el numero de palabras por pagina
			int words_N = Integer.parseInt(lineStr[0]); //se convierte a entero el numero de palabras
			String[] words= new String[words_N*pages_words]; //se crea un arreglo de palabras
			for(int j=0;j<words_N ;j++) //se recorre el numero de palabras
			{
				line = Buffered.readLine(); //se lee la linea
				String[] page = line.split("\\s+"); //se separa la linea por espacios
				int NumberPage = Integer.parseInt(page[0]); //se convierte a entero el numero de pagina
				for(int k=0;k<pages_words ;k++) //se recorre el numero de palabras por pagina
				 {
					words[(NumberPage*pages_words)+(k)]=page[k+1]; // se agrega la palabra al arreglo de palabras
				}
			}
			System.out.println(instance.findOrder(words)); //se imprime el resultado del metodo findOrder con el arreglo de palabras como parametro
			line = Buffered.readLine(); //se lee la siguiente linea
		}

	} catch (IOException e) { //se atrapa la excepcion
		System.out.println("ERROR"); //se imprime ERROR

	} 
}
}
