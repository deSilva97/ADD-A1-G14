package com.export;

import java.io.FileWriter;
import java.util.List;

import com.model.Article;

public class CSVExport {
	
	static String path = "articulos.csv";

	public static boolean exportList(List<Article> list) {
				
		try (FileWriter fw = new FileWriter(path);){
			
			fw.write("ID, Nombre, Stock, Precio, Descripción\n");
			for (Article article : list) {
				fw.write(article.getId() + ",");
				fw.write(article.getName() + ",");
				fw.write(article.getStock() + ",");
				fw.write(String.valueOf(article.getPrice()) + ",");
				fw.write(article.getDescription() + "\n");
			}
			fw.flush();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		
		return false;
	}
}
