package it.polito.tdp.itunes.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		Genre G = new Genre(2,"Jazz");
		
		
		m.creaGrafo(G);
		System.out.println(m.getDeltaMassimo());

	}

}
