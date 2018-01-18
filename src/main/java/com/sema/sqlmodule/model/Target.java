package com.sema.sqlmodule.model;

public class Target {
	
	    private String name;
		private String gene_id;
	    private String exons;
	    private String chromosome;
	    
	    private String start_index;
	    private String end_index;
	    private String q25_quartile;
	    private String median;
	    private String q75_quartile;
	    private String run_combination_id;
	    public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getGene_id() {
			return gene_id;
		}
		public void setGene_id(String gene_id) {
			this.gene_id = gene_id;
		}
		public String getExons() {
			return exons;
		}
		public void setExons(String exons) {
			this.exons = exons;
		}
		public String getChromosome() {
			return chromosome;
		}
		public void setChromosome(String chromosome) {
			this.chromosome = chromosome;
		}
		public String getStart_index() {
			return start_index;
		}
		public void setStart_index(String start_index) {
			this.start_index = start_index;
		}
		public String getEnd_index() {
			return end_index;
		}
		public void setEnd_index(String end_index) {
			this.end_index = end_index;
		}
		public String getQ25_quartile() {
			return q25_quartile;
		}
		public void setQ25_quartile(String q25_quartile) {
			this.q25_quartile = q25_quartile;
		}
		public String getMedian() {
			return median;
		}
		public void setMedian(String median) {
			this.median = median;
		}
		public String getQ75_quartile() {
			return q75_quartile;
		}
		public void setQ75_quartile(String q75_quartile) {
			this.q75_quartile = q75_quartile;
		}
		public String getRun_combination_id() {
			return run_combination_id;
		}
		public void setRun_combination_id(String run_combination_id) {
			this.run_combination_id = run_combination_id;
		}




}
