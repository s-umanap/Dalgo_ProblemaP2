from timeit import default_timer as timer


class pandora:
    def alfabeto_de_pandora(self,palabras : list[str]) -> str:
        

        K = 26 #Numero de letras del alfabeto
        N = len(palabras) #Numero de palabras
        adj=[[] for i in range(K)] #creamos una lista de listas con K listas vacias para la cantidad de letras del alfabeto y sus conexiones con otras letras
        for i in range(N-1): #recorremos las palabras
            palabra_1=palabras[i]
            palabra_2=palabras[i+1] 
            m=min(len(palabra_1),len(palabra_2)) #longitud minima de las palabras
            for j in range(m): #recorremos las letras de las palabras
                if palabra_1[j]!=palabra_2[j]: #si las letras son diferentes
                    adj[ord(palabra_1[j])-ord("a")].append(ord(palabra_2[j])-ord("a")) #agregamos la adyacencia a la lista de adyacencias
                    #ord() devuelve el valor unicode de un caracter y chr() devuelve el caracter de un valor unicode 

                    break #salimos del ciclo
    
        visited=[0]*K #creamos una lista de K elementos con valor 0 para crear un grafo donde cada letra del alfabeto es un nodo y sus conexiones son las aristas
        #0: no visitado 1: visitado 2: terminado, esto nos facilitara la busqueda de ciclos y realizar dfs para encontrar el orden topologico del grafo.
        
        #funcion para buscar el orden topologico con dfs y detectar ciclos utilizando una cola
        for i in range(K): #recorremos las letras del alfabeto
            for j in adj[i]: #recorremos las adyacencias de cada letra
                visited[j]+=1 #aumentamos el valor de las adyacencias de cada letra
        cola_de_letras=[] #creamos una cola de letras para guardar las letras que no tienen adyacencias
        for i in range(K): #recorremos las letras del alfabeto
            if visited[i]==0: #si la letra no tiene adyacencias
                cola_de_letras.append(i) #agregamos la letra a la cola de letras

        orden_topo_letras=[] #creamos una lista para guardar el orden topologico

        while cola_de_letras: #mientras la cola de letras no este vacia
            primera_letra_cola_actual=cola_de_letras.pop(0) #sacamos la primera letra de la cola de letras
            orden_topo_letras.append(primera_letra_cola_actual) #agregamos la letra al orden topologico
            
            #recorremos las adyacencias de la letra que sacamos de la cola de letras
            for j in adj[primera_letra_cola_actual]: #recorremos las adyacencias de la letra que sacamos de la cola de letras
                visited[j]-=1  #disminuimos el valor de las adyacencias de la letra que sacamos de la cola de letras
                if visited[j]==0: #si la letra no tiene adyacencias
                    cola_de_letras.append(j) #agregamos la letra a la cola de letras
        
        letras_ordenadas=[] #creamos una lista para guardar las letras ordenadas

        letras_sin_repetir=[] #creamos una lista para guardar las letras sin repetir
        for x in palabras:
            for y in x:
                if y not in letras_sin_repetir:
                    letras_sin_repetir.append(y)
    
        for i in orden_topo_letras: #recorremos el orden topologico
            if chr(i+ord("a")) in letras_sin_repetir: #si la letra esta en la lista de letras sin repetir
                letras_ordenadas.append(chr(i+ord("a"))) #agregamos las letras ordenadas a la lista de letras ordenadas

        cadena_resultante = "".join(letras_ordenadas) #retornamos las letras ordenadas

        if len(cadena_resultante) < len(letras_sin_repetir):
            return "ERROR"
        else:
            return cadena_resultante


print(pandora.alfabeto_de_pandora(pandora,["h","hjh","hjmh","hm","j","jjm","m","mh","mmhj"]))
print(pandora.alfabeto_de_pandora(pandora,["ab","ac","cc","ca"]))
print(pandora.alfabeto_de_pandora(pandora,["xx", "xp", "pj", "jjj" ,"jjw"]))
print(pandora.alfabeto_de_pandora(pandora,["ab" ,"ah","an" ,"mn" ,"mk"]))

print("##################### TESTS propios, fuera de las pruebas dadas en el documento #####################")
print(pandora.alfabeto_de_pandora(pandora,["xa","xy","yt","yy","ya"]))
print(pandora.alfabeto_de_pandora(pandora,["ab","ac","cc","ca","cb","bc","ba"]))
print(pandora.alfabeto_de_pandora(pandora,["ab","ac","cc","ca","cb","bc","ba","aa","bb","cc"]))
print(pandora.alfabeto_de_pandora(pandora,["ab","ac","cc","ca","cb","bc","ba","aa","bb","cc","dd","ee"]))
print(pandora.alfabeto_de_pandora(pandora,["ab","ac","ad","ae","af","ag",]))
print(pandora.alfabeto_de_pandora(pandora,["aa","ba","ca","xa","za"]))


### timer para medir el tiempo de ejecucion de alguna prueba en especifico, importante descomentar
#inicio = timer()
#print(Solution.alfabeto_de_pandora(Solution,["h","hjh","hjmh","hm","j","jjm","m","m","mmhj"]))
#fin = timer()
#print("Tiempo de ejecución: ", fin - inicio)