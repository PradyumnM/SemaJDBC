package com.sema.sqlmodule.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sema.sqlmodule.model.ControlSampleVariable;
import com.sema.sqlmodule.model.Gene;
import com.sema.sqlmodule.model.GeneGeneCategory;
import com.sema.sqlmodule.model.Sample;
import com.sema.sqlmodule.model.Target;

public class MainDaoImpl implements MainDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void insertTarget(final ArrayList<Target> targetList) {
		String SQL = "INSERT INTO target (name,gene_id,exons,chromosome,start_index,end_index,q25_quartile,median,q75_quartile,run_combination_id) VALUES (?,(select id from gene where name =? and run_combination_id=?),?,?,?,?,?,?,?,?)";

		jdbcTemplateObject.batchUpdate(SQL, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Target rec = targetList.get(i);

				ps.setString(1, rec.getName());
				ps.setString(2, rec.getGene_id());
				ps.setString(3, rec.getRun_combination_id());
				ps.setString(4, rec.getExons());
				ps.setString(5, rec.getChromosome());
				ps.setString(6, rec.getStart_index());
				ps.setString(7, rec.getEnd_index());
				ps.setString(8, rec.getQ25_quartile());
				ps.setString(9, rec.getMedian());
				ps.setString(10, rec.getQ75_quartile());
				ps.setString(11, rec.getRun_combination_id());

			}

			public int getBatchSize() {
				return targetList.size();
			}
		});
		System.out.println("Created Record");
		return;
	}

	public void insertGene(final ArrayList<Gene> geneList) {
		String SQL = "insert into gene(`name`,`run_combination_id`) values(?,?)";
		/// Object[] params = {name, age};
		// jdbcTemplateObject.update( SQL, params);
		jdbcTemplateObject.batchUpdate(SQL, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Gene rec = geneList.get(i);

				ps.setString(1, rec.getName());
				ps.setString(2, rec.getRun_combination_id());

			}

			public int getBatchSize() {
				return geneList.size();
			}
		});

		System.out.println("Created Record");
		return;
	}

	public void insertGeneGeneCategory(final ArrayList<GeneGeneCategory> geneCatList) {
		String SQL = "insert into gene_genecategory (`id`,`genecategory_id`,`gene_id`) values(?,?,?)";

		jdbcTemplateObject.batchUpdate(SQL, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				GeneGeneCategory rec = geneCatList.get(i);
				ps.setInt(1, i);
				ps.setString(2, rec.getGenecategory_id());
				ps.setString(3, rec.getGene_id());

			}

			public int getBatchSize() {
				return geneCatList.size();
			}
		});
		System.out.println("Created Record");
		return;
	}

	public void insertSample(final ArrayList<Sample> sampleList) {
		String SQL = "INSERT INTO sample (lims_sample_id,sample_type) VALUES(?,?)";

		jdbcTemplateObject.batchUpdate(SQL, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Sample rec = sampleList.get(i);

				ps.setString(1, rec.getLims_sample_id());
				ps.setString(2, rec.getSample_type());

			}

			public int getBatchSize() {
				return sampleList.size();
			}
		});
		System.out.println("Created Record");
		return;
	}

	public void insertControlSampleVariant(final ArrayList<ControlSampleVariable> controlSampVarlist) {
		String SQL = "INSERT INTO control_sample_variants_reference (sample_id,target_name,chromosome,position,ref_allele,alt_allele,gene,cdna_change,protein_change,expected_al,expected_al_start,expected_al_end) VALUES ((select id from sample where lims_sample_id =?),?,?,?,?,?,?,?,?,?,?,?)";

		jdbcTemplateObject.batchUpdate(SQL, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ControlSampleVariable rec = controlSampVarlist.get(i);

				ps.setString(1, rec.getSample_id());
				ps.setString(2, rec.getName());
				ps.setString(3, rec.getChromosome());
				ps.setString(4, rec.getPosition());
				ps.setString(5, rec.getRef_allele());
				ps.setString(6, rec.getAlt_allele());
				ps.setString(7, rec.getGene());
				ps.setString(8, rec.getCdna_change());
				ps.setString(9, rec.getProtein_change());
				ps.setString(10, rec.getExpected_al());
				ps.setString(11, rec.getExpected_al_start());
				ps.setString(12, rec.getExpected_al_end());

			}

			public int getBatchSize() {
				return controlSampVarlist.size();
			}
		});
		System.out.println("Created Record");
		return;
	}

}
