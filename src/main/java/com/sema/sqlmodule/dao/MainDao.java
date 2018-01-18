package com.sema.sqlmodule.dao;

import java.util.ArrayList;
import javax.sql.DataSource;

import com.sema.sqlmodule.model.ControlSampleVariable;
import com.sema.sqlmodule.model.Gene;
import com.sema.sqlmodule.model.GeneGeneCategory;
import com.sema.sqlmodule.model.Sample;
import com.sema.sqlmodule.model.Target;

public interface MainDao {

	public void setDataSource(DataSource ds);
	public void insertGene ( ArrayList<Gene> geneList);
	public void insertTarget ( ArrayList<Target> targetList);
	public void insertGeneGeneCategory ( ArrayList<GeneGeneCategory> geneCatList);
	public void insertControlSampleVariant ( ArrayList<ControlSampleVariable> controlSampVarlist);
	public void insertSample ( ArrayList<Sample> sampleList);



}