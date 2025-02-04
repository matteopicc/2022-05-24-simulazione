package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	private ItunesDAO dao;
	private Graph<Track,DefaultWeightedEdge>grafo;
	private Map<Integer,Track>idMap;
	
	private List<Track> listaMigliore;
	int c;
	
	public List<Track>cercaLista(Track c, int m){
		//recupero la componente connessa di c
		List<Track>canzoniValide = new ArrayList<>();
		ConnectivityInspector<Track,DefaultWeightedEdge>ci = new ConnectivityInspector<>(this.grafo);
		canzoniValide.addAll(ci.connectedSetOf(c));
		
		List<Track>parziale = new ArrayList<>();
		listaMigliore = new ArrayList<>();	
		parziale.add(c);
		cerca(parziale,canzoniValide,m);
		
		return listaMigliore;	
	}
	
	
	 private void cerca(List<Track> parziale, List<Track> canzoniValide, int m) {
		 
		//controllo soluzione migliore
		 if(parziale.size()>listaMigliore.size()) {
			 listaMigliore = new ArrayList<>(parziale);
		 }
		 
		 
		for(Track t: canzoniValide) {
			if(!parziale.contains(t) && (sommaMemoria(parziale)+t.getBytes())<= m) {
			parziale.add(t);
			cerca(parziale,canzoniValide,m);
			parziale.remove(parziale.size()-1);
			}
		}
	}
	 
	 private int sommaMemoria(List<Track> canzoni) {
		 int somma = 0;
		 for(Track t: canzoni) {
			 somma += t.getBytes();
		 }
		 return somma;
	 }


	public Model() {
		dao = new ItunesDAO();
		idMap = new HashMap<>();
		
		this.dao.getAllTracks(idMap);
	}

	public List<Genre>getGeneri(){
		return dao.getAllGenres(); 
	 }
	
	
	public void creaGrafo(Genre genere) {
		//creo il grafo
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(genere, this.idMap));
		
		//aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(genere, idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getT1(), a.getT2(),a.getPeso());
		}
		
		System.out.println("Grafo creato!");
		System.out.println("# Vertici: "+this.grafo.vertexSet().size());
		System.out.println("# Archi: "+this.grafo.edgeSet().size());
	}
	
	public List<Track> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public List<Adiacenza>getDeltaMassimo(){
		List<Adiacenza>result = new ArrayList<>();
		int max = 0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			int peso = (int)(this.grafo.getEdgeWeight(e));
			if(peso > max) {
				result.clear();
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),peso));
				max=peso;
			}else if(peso == max) {
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),peso));
			}
		}
		return result;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null) {
			return false;
		}
		else
		return true;
	}
}
