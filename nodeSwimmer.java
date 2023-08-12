import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways

        String[] arrayLinks = new String[L];
        int jumpsToSI = N-1;
        String answer = "";
        ArrayList<String> severedLinks = new ArrayList<String>();

        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();

            arrayLinks[i] = N1 + " " + N2; 

        }

        System.err.println("");
        for(String i : arrayLinks){
            System.err.println(i);
        }
        System.err.println("");

        String[] arrayGateways = new String[E];

        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node

            arrayGateways[i] = String.valueOf(EI);

        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Bobnet agent is positioned this turn

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            String[] shortestRoutes = new String[E];

            for(int i=0; i<E; i++){

                ArrayList<String> routesTowardsSI = new ArrayList<String>();

                System.err.println("Investigamos la GateWay del Nodo: " + arrayGateways[i]);

                routesTowardsSI.add(arrayGateways[i]);

                shortestRoutes[i] = resultado(arrayLinks, Integer.valueOf(arrayGateways[i]), severedLinks, SI, routesTowardsSI);

                 System.err.println("La ruta más corta desde la GateWay del Nodo " + arrayGateways[i] + " es " + shortestRoutes[i]);

// Si el número de saltos es inferior al anterior, se sustituye el valor de "answer" por el índice del nuevo camino más corto.

                if((shortestRoutes[i].length()/2) < jumpsToSI){

                    jumpsToSI = shortestRoutes[i].length()/2;
                    answer = shortestRoutes[i].charAt(shortestRoutes[i].length()-3) + " " + shortestRoutes[i].charAt(shortestRoutes[i].length()-1);

                    System.err.println("answer: " + answer);

                }

            }

// Transformamos answer en los últimos 3 Char.

            answer = answer.length() > 3 ? answer.substring(answer.length()-3, answer.length()-1) : answer;

            // Example: 0 1 are the indices of the nodes you wish to sever the link between

// Doy la respuesta, asegurándome de que existe:

        for(String j : arrayLinks){

            System.err.println("answer: " + answer + " vs j: " + j);

            if(j.equals(answer)){
                System.out.println(j);
                severedLinks.add(answer);
            }
            

            if(j.equals(answer.charAt(2) + " " + answer.charAt(0))){
                System.out.println(j);
                severedLinks.add(j);
            }
        }
            
        }
    }

    public static String resultado(String[] arrayLinks, int gatewayNode, ArrayList<String> severedLinks, int bobnetStartingNode, ArrayList<String> routesTowardsSI){

        System.err.println("");
        System.err.print("gatewayNode: " + gatewayNode);
        System.err.print(" // startingNode: " + severedLinks);
        System.err.println(" // bobnetStartingNode: " + bobnetStartingNode);
        System.err.println();

// Empiezo a iterar desde la posición 0 de la lista de rutas. Defino un iterator para poder añadir más tarde más caminos.
// Guardo el bloque iterativo que funcionaba:
/* for (int w=0; w<routesTowardsSI.size(); w++){

            String i = routesTowardsSI.get(w); */
try{
        for ( String i : routesTowardsSI){

            System.err.println("Lista de rutas a investigar: " + routesTowardsSI);
            System.err.println("Ruta investigada: " + i);


// Elijo el último nodo que he investigado, lo convierto a String

            String ultimoChar = "" + i.charAt(i.length() - 1);
            System.err.println("El último carácter es " + ultimoChar + ". Iniciamos la comparativa del último caracter con la lista de links.");

// Comparo el último nodo con la lista de links, uno por uno

            for(String j : arrayLinks){

                if(j.contains(ultimoChar)){

// ...lo comparo para asegurarme de que no está repetido en mi lista de rutas. Si es así, dejo de compararlo y continuo. Ademas pongo un "try" para llevar el fallo al meter la primera iteración de i.

                    System.err.println(j + " contiene el ultimoChar, que es " + ultimoChar + ". La ruta investigada es i: " + i);

                    try{

                    if(j.equals(i.substring(i.length()-3, i.length()-1)) || j.equals((i.charAt(i.length()-1) + " " + i.charAt(i.length()-3)))){

                        continue;

                    }} catch (Exception e) {

                        System.err.println("Pillamos un Exception!");

                    }
                    
// Si no tenía esto en ruta, añado una nueva línea a la lista de rutas con el último nodo.

                    if(j.indexOf(ultimoChar) == 0){

                        routesTowardsSI.add(i.concat(" " + j.charAt(2)));
                        System.err.println("Añadimos una nueva ruta, que es " + i + " " + j.charAt(2));
                        
                        if (j.contains(Integer.toString(bobnetStartingNode))){

                            System.err.println("Hemos tocado con bobNet. La ruta más corta del nodo estudiado es: " + i + " " + Integer.toString(bobnetStartingNode));
                            return (i + " " + Integer.toString(bobnetStartingNode));

                        };

                    }else if(j.indexOf(ultimoChar) == 2){

                        routesTowardsSI.add(i.concat(" " + j.charAt(0)));
                        System.err.println("Añadimos una nueva ruta, que es " + i + " " + j.charAt(0));

                        if (j.contains(Integer.toString(bobnetStartingNode))){

                            System.err.println("Hemos tocado con bobNet. La ruta más corta del nodo estudiado es: " + i + " " + Integer.toString(bobnetStartingNode));
                            return (i + " " + Integer.toString(bobnetStartingNode));

                        };

                    }
                }else{

                    System.err.println(j + " NO contiene el ultimoChar, que es " + ultimoChar + ". La ruta investigada es i: " + i);

                }

            }

        }

} catch (Exception e){

        for(String i : arrayLinks){

            boolean includedInSeveredList = false;

            for (String j : severedLinks){
                if(i.equals(j)){
                    includedInSeveredList = true;
                    break;
                }
            }
            
            if(includedInSeveredList){
                continue;
            }else{
                return(i);
            }

        }


}
        return "Algo ha fallado";
    
    }
}
