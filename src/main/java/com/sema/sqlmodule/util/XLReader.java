package com.sema.sqlmodule.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sema.sqlmodule.dao.MainDaoImpl;
import com.sema.sqlmodule.model.Gene;
import com.sema.sqlmodule.model.GeneGeneCategory;
import com.sema.sqlmodule.model.Target;

public class XLReader {
	public static ArrayList<Gene> geneList = new ArrayList<Gene>();
	public static ArrayList<GeneGeneCategory> geneCatList = new ArrayList<GeneGeneCategory>();
	public static ArrayList<Target> targetList = new ArrayList<Target>();
	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		MainDaoImpl semaJDBCTemplate = 
				(MainDaoImpl)context.getBean("studentJDBCTemplate");

		System.out.println("------Records Creation--------" );


		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);

		File folder = new File(prop.getProperty("filedir"));
		String fileDir=prop.getProperty("filedir");
		String geneCatFile1 = prop.getProperty("geneCatFile1");
		String geneCatFile2 = prop.getProperty("geneCatFile2");
		String geneCatFile3 = prop.getProperty("geneCatFile3");
		String geneCatFile4 = prop.getProperty("geneCatFile4");

		String geneCatName1 = prop.getProperty("geneCatName1");
		String geneCatName2 = prop.getProperty("geneCatName2");
		String geneCatName3 = prop.getProperty("geneCatName3");
		String geneCatName4 = prop.getProperty("geneCatName4");
		String targetFileName = prop.getProperty("targetfilecsv");
		String alterDelSqlFileName=prop.getProperty("alterdelfileName");
		String startSqlFileName = prop.getProperty("startsqlfile");
		String endSqlFileName = prop.getProperty("endsqlfile");
		String typeControl = prop.getProperty("typeControl");
		String outputfiledir = prop.getProperty("outputfiledir");
		File[] listOfFiles = folder.listFiles();
		String alterDeleteFlag=prop.getProperty("alterdeleteflag");

		String line1 = "";
		String extension = "";
		String outFileName = prop.getProperty("fileSql");
		StringBuffer colNameBuff = new StringBuffer("");
		StringBuffer recordBuff = new StringBuffer("");
		HashMap<String, String> geneSet = new HashMap<String, String>();
		ArrayList<String> runCombIdList = new ArrayList<String>();
		runCombIdList.add("1");
		runCombIdList.add("2");
		runCombIdList.add("3");
		runCombIdList.add("4");
		runCombIdList.add("5");
		runCombIdList.add("6");
		runCombIdList.add("7");
		runCombIdList.add("8");
		String temp = "";
		Connection con = null;
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(outputfiledir + outFileName));
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(prop.getProperty("database"), prop.getProperty("dbuser"),
					prop.getProperty("dbpassword"));
			Statement stmt = con.createStatement();
			Statement geneStmt = con.createStatement();


			if(alterDeleteFlag.equalsIgnoreCase("true")) 
			{
				BufferedReader br = new BufferedReader(new FileReader(fileDir+alterDelSqlFileName));
				while ((line1 = br.readLine()) != null) {
					fw.write(line1);
					fw.newLine();

				} 
				br.close();
			}



			for (File file : listOfFiles) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (file.isFile()) {
					int i = file.getName().lastIndexOf('.');
					if (i > 0) {
						extension = file.getName().substring(i + 1);
					}
					if (file.getName().equalsIgnoreCase(startSqlFileName)) {
						System.out.println("writing " + file.getName());
						while ((line1 = br.readLine()) != null) {
							fw.write(line1);
							fw.newLine();

						} 

					}
				} 
				br.close();
			}

			for (File file : listOfFiles) {
				if (file.isFile()) {
					int i = file.getName().lastIndexOf('.');
					if (i > 0) {
						extension = file.getName().substring(i + 1);
					}

					if (file.getName().substring(0, 8).equalsIgnoreCase("geneList")) {
						System.out.println("writing " + file.getName());
						BufferedReader br = new BufferedReader(new FileReader(file));
						while ((line1 = br.readLine()) != null) {
							// System.out.println(line1);
							if (file.getName().equalsIgnoreCase(geneCatFile1))
								geneSet.put(line1, geneCatName1);
							if (file.getName().equalsIgnoreCase(geneCatFile2))
								geneSet.put(line1, geneCatName2);
							if (file.getName().equalsIgnoreCase(geneCatFile3))
								geneSet.put(line1, geneCatName3);
							if (file.getName().equalsIgnoreCase(geneCatFile4))
								geneSet.put(line1, geneCatName4);
							/*
							 * fileBuff.append(line1); fw.write(line1); fw.newLine();
							 */

						}
						br.close();
					}

				}

			}
			// Writing to gene table
			ResultSet rs = stmt.executeQuery("SELECT * FROM run_combination;");
			int flag1 = 0;
			fw.newLine();
			fw.write("insert into gene(`name`,`run_combination_id`) values");
			for (int x=0; x<runCombIdList.size(); x++){
				// System.out.println(rs.getInt(1)+" "+rs.getInt(4));
				//temp = Integer.toString(rs.getInt(1)); for fetching runcomdid thr db
				//runCombIdList.add(temp);
				temp = runCombIdList.get(x);

				for (String key : geneSet.keySet()) {
					if (flag1 == 0) {
						fw.write("(");
						flag1 = 1;
					} else {
						fw.write(",");
						fw.newLine();
						fw.write("(");
					}
					// System.out.println(itr.next());
					fw.write("'" + key + "'" + "," + temp + ")");
					Gene geneEntry = new Gene();
					geneEntry.setName(key);
					geneEntry.setRun_combination_id(temp);
					geneList.add(geneEntry);

				}
			}
			semaJDBCTemplate.insertGene(geneList);
			fw.write(";");
			// Writing to target table
			int y=0;
			for (File file : listOfFiles) {

				if (file.getName().equalsIgnoreCase(targetFileName)) {
					System.out.println("writing target file " + file.getName());
					BufferedReader br = new BufferedReader(new FileReader(file));
					fw.newLine();
					colNameBuff.append("INSERT INTO target " + "(");
					br.readLine();
					colNameBuff.append(
							"name,gene_id,exons,chromosome,start_index,end_index,q25_quartile,median,q75_quartile,run_combination_id)"
									+ " VALUES ");
					fw.write(colNameBuff.toString());
					int flag = 0;
					String csvData[];
					while ((line1 = br.readLine()) != null) {

						for (int i = 0; i < runCombIdList.size(); i++) {
							if (flag == 0) {
								recordBuff.append("(");
								flag = 1;
							} else {
								fw.write(",");
								fw.newLine();
								recordBuff.append("(");
							}
							csvData = line1.split(",");
							String tempData[] = new String [csvData.length];
							y=0;
							for (int k = 0; k < csvData.length; k++) {
								if (k == 1)
								{recordBuff.append("(select id from gene where name ='" + csvData[k]
										+ "' and run_combination_id=" + runCombIdList.get(i) + "),");
								tempData[y++]= csvData[k];
								}
								else
								{
									if(csvData[k].charAt(0)=='"' && csvData[k].charAt(csvData[k].length()-1)!='"' )
									{recordBuff.append("'" + csvData[k].substring(1) +","+csvData[k+1].substring(0,csvData[k+1].length()-1) +"',");
									++k;
									tempData[y++]=csvData[k].substring(1) +","+csvData[k+1].substring(0,csvData[k+1].length()-1);
									}
									else 
									{
										recordBuff.append("'" + csvData[k] + "',");
										tempData[y++]= csvData[k];
									}

								}
							}
							Target targetEntry = new Target();
							targetEntry.setName(tempData[0]);
							targetEntry.setGene_id(tempData[1]);
							targetEntry.setExons(tempData[2]);
							targetEntry.setChromosome(tempData[3]);
							targetEntry.setStart_index(tempData[4]);
							targetEntry.setEnd_index(tempData[5]);
							targetEntry.setQ25_quartile(tempData[6]);
							targetEntry.setMedian(tempData[7]);
							targetEntry.setQ75_quartile(tempData[8]);
							targetEntry.setRun_combination_id(runCombIdList.get(i));
							targetList.add(targetEntry);
							recordBuff.append(runCombIdList.get(i) + ")");
							fw.write(recordBuff.toString());

							recordBuff.setLength(0);
						}

					}
					fw.write(";");
					br.close();
				}
			}

			semaJDBCTemplate.insertTarget(targetList);
			//writing sample related queries
			RandomGen addSampleQueries = new RandomGen();
			addSampleQueries.writeExcelContentToSQlFile(fw, typeControl,semaJDBCTemplate);



			// Writing to GeneGeneCategory
			ResultSet gst = geneStmt.executeQuery("SELECT * FROM run_combination;");
			int i = 1;
			fw.newLine();

			flag1 = 0;
			fw.newLine();
			fw.write("insert into gene_genecategory (`id`,`genecategory_id`,`gene_id`) values");
			for (int x=0; x<runCombIdList.size(); x++) {
				// System.out.println(rs.getInt(1)+" "+rs.getInt(4));
				//temp = Integer.toString(gst.getInt(1));

				temp = runCombIdList.get(x);
				for (String key : geneSet.keySet()) {
					if (flag1 == 0) {
						fw.write("(");
						flag1 = 1;
					} else {
						fw.write(",");
						fw.newLine();
						fw.write("(");
					}
					// System.out.println(itr.next());
					fw.write(i++ + ",(select id from genecategory where category_name='"+geneSet.get(key)+"')," +"(select id from gene where name ='"+key + "' and run_combination_id="+temp + "))");
					GeneGeneCategory entry = new GeneGeneCategory();

					entry.setRunCombinationId(temp);
					entry.setGeneName(geneSet.get(key));
					entry.setCategoryName(geneSet.get(key));
					geneCatList.add(entry);
				}
			}
			semaJDBCTemplate.insertGeneGeneCategory(geneCatList);
			fw.write(";");
			fw.newLine();
			// Writing after Target table and Gene_genecategory entries
			for (File file : listOfFiles) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				if (file.isFile()) {

					if (file.getName().equalsIgnoreCase(endSqlFileName)) {
						System.out.println("writing " + file.getName());
						while ((line1 = br.readLine()) != null) {
							fw.write(line1);
							fw.newLine();

						} // while end

					}
				} // If file End
				br.close();
			}

			con.close();
			fw.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		}

		/*
		 * Target CSV update
		 * 
		 * String csvFile =
		 * "/home/pradyumnm/Downloads/targetlistgeneslist/targetList.csv"; String line =
		 * ""; String cvsSplitBy = ","; String fileTarget = "targetNtxt"; StringBuffer
		 * colNameBuff = new StringBuffer(""); StringBuffer recordBuff = new
		 * StringBuffer(""); BufferedWriter rw = new BufferedWriter(new
		 * FileWriter(fileTarget)) ;
		 * 
		 * try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
		 * String columnList []= br.readLine().split(cvsSplitBy);
		 * colNameBuff.append("INSERT INTO target (");
		 * colNameBuff.append(columnList[0]); int j=0; for(int
		 * i=1;i<columnList.length;i++) colNameBuff.append(","+columnList[i]);
		 * colNameBuff.append(") VALUES ("); while ((line = br.readLine()) != null) {
		 * 
		 * // use comma as separator recordBuff.append(colNameBuff); String[] records =
		 * line.split(cvsSplitBy); recordBuff.append(records[0]); for(int
		 * i=1;i<records.length;i++) recordBuff.append(","+records[i]);
		 * 
		 * recordBuff.append(");");
		 * 
		 * 
		 * rw.write(recordBuff.toString()); rw.newLine();
		 * 
		 * recordBuff.setLength(0); } System.out.print(j); rw.close(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 * 
		 * // workbook.close(); inputStream.close();
		 */

		// -------------------------------------Excel reading
		/*
		 * String excelFilePath = "depth_config_stats.xlsx"; String fileName =
		 * "filename.sql"; FileInputStream inputStream = new FileInputStream(new
		 * File(excelFilePath));
		 * 
		 * Workbook workbook = new XSSFWorkbook(inputStream); Sheet firstSheet =
		 * workbook.getSheetAt(0); Iterator<Row> iterator = firstSheet.iterator();
		 * BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)) ; while
		 * (iterator.hasNext()) { Row nextRow = iterator.next(); Iterator<Cell>
		 * cellIterator = nextRow.cellIterator();
		 * 
		 * while (cellIterator.hasNext()) { Cell cell = cellIterator.next();
		 * 
		 * switch (cell.getCellType()) { case Cell.CELL_TYPE_STRING:
		 * //System.out.print(cell.getStringCellValue());
		 * bw.write(cell.getStringCellValue()); break; case Cell.CELL_TYPE_BOOLEAN:
		 * System.out.print(cell.getBooleanCellValue()); break; case
		 * Cell.CELL_TYPE_NUMERIC: System.out.print(cell.getNumericCellValue()); break;
		 * } System.out.print(" - "); } System.out.println(); }
		 */
	}

}
