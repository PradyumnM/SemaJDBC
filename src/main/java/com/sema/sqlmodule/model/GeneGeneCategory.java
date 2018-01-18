package com.sema.sqlmodule.model;

public class GeneGeneCategory {
	String genecategory_id;
	String gene_id;
	String runCombinationId;
	String categoryName;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getRunCombinationId() {
		return runCombinationId;
	}
	public void setRunCombinationId(String runCombinationId) {
		this.runCombinationId = runCombinationId;
	}
	public String getGeneName() {
		return geneName;
	}
	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}
	String geneName;
	public String getGenecategory_id() {
		return genecategory_id;
	}
	public void setGenecategory_id(String genecategory_id) {
		this.genecategory_id = genecategory_id;
	}
	public String getGene_id() {
		return gene_id;
	}
	public void setGene_id(String gene_id) {
		this.gene_id = gene_id;
	}
	
}
