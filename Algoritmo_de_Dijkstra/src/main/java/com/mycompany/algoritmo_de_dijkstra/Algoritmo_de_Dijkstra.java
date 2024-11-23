package com.mycompany.algoritmo_de_dijkstra;

import java.util.*;

public class Algoritmo_de_Dijkstra {

    static class Grafo {
        private Map<String, Map<String, Integer>> adjList = new HashMap<>();

        public void agregarArista(String ciudad1, String ciudad2, int distancia) {
            adjList.putIfAbsent(ciudad1, new HashMap<>());
            adjList.putIfAbsent(ciudad2, new HashMap<>());
            adjList.get(ciudad1).put(ciudad2, distancia);
            adjList.get(ciudad2).put(ciudad1, distancia);  
        }

        public Map<String, Integer> obtenerVecinos(String ciudad) {
            return adjList.getOrDefault(ciudad, new HashMap<>());
        }

        public Set<String> obtenerCiudades() {
            return adjList.keySet();
        }
    }

    public void encontrarRutaMasCorta(Grafo grafo, String ciudadInicio, String ciudadDestino) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> previos = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (String ciudad : grafo.obtenerCiudades()) {
            distancias.put(ciudad, Integer.MAX_VALUE);
            previos.put(ciudad, null);
        }
        distancias.put(ciudadInicio, 0);
        colaPrioridad.add(new AbstractMap.SimpleEntry<>(ciudadInicio, 0));

        while (!colaPrioridad.isEmpty()) {
            Map.Entry<String, Integer> actual = colaPrioridad.poll();
            String ciudadActual = actual.getKey();

            if (ciudadActual.equals(ciudadDestino)) {
                break;
            }

            for (Map.Entry<String, Integer> vecino : grafo.obtenerVecinos(ciudadActual).entrySet()) {
                String ciudadVecina = vecino.getKey();
                int peso = vecino.getValue();
                int nuevaDistancia = distancias.get(ciudadActual) + peso;

                if (nuevaDistancia < distancias.get(ciudadVecina)) {
                    distancias.put(ciudadVecina, nuevaDistancia);
                    previos.put(ciudadVecina, ciudadActual);
                    colaPrioridad.add(new AbstractMap.SimpleEntry<>(ciudadVecina, nuevaDistancia));
                }
            }
        }

        if (distancias.get(ciudadDestino) == Integer.MAX_VALUE) {
            System.out.println("No hay ruta disponible entre " + ciudadInicio + " y " + ciudadDestino);
        } else {
            System.out.println("Distancia más corta: " + distancias.get(ciudadDestino));
            System.out.print("Ruta: ");
            imprimirRuta(previos, ciudadInicio, ciudadDestino);
        }
    }

    private static void imprimirRuta(Map<String, String> previos, String ciudadInicio, String ciudadDestino) {
        if (ciudadDestino == null) {
            return;
        }
        imprimirRuta(previos, ciudadInicio, previos.get(ciudadDestino));
        System.out.print(ciudadDestino + (ciudadDestino.equals(ciudadInicio) ? "" : " -> "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();

        grafo.agregarArista("A", "B", 4);
        grafo.agregarArista("A", "C", 2);
        grafo.agregarArista("B", "C", 5);
        grafo.agregarArista("B", "D", 10);
        grafo.agregarArista("C", "D", 3);

        System.out.print("Ciudad de inicio: ");
        String ciudadInicio = scanner.nextLine();
        System.out.print("Ciudad de destino: ");
        String ciudadDestino = scanner.nextLine();

        Algoritmo_de_Dijkstra algoritmo = new Algoritmo_de_Dijkstra();
        algoritmo.encontrarRutaMasCorta(grafo, ciudadInicio, ciudadDestino);

        scanner.close();
    }
}

