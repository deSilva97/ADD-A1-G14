package com.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.helper.ScannerHelper;
import com.model.Article;

public class Main {

	static final String PATH_NAME = "artículos.dat";
	
	private static Scanner scanner;
	private static ArrayList<Article> articleList;
	
	static int count;
	
	static boolean debug = true;
	
	private static void init() {
		System.out.println("Inicializando...");
		count = 0;
		scanner = new Scanner(System.in);
		
		try {
			File file = new File(PATH_NAME);		
			if(file.exists()) {
				System.out.println("Leyendo archivo...");
			} else {
				System.out.println("No hay archivo disponible por el momento");
				articleList = new ArrayList<Article>();
				if(debug) {
					System.err.println("Está activado el modo debug");
					articleList.add(new Article(1, "Pan", "Lol es pan", 64, 2.40f));
					articleList.add(new Article(2, "Hierro", "", 12, 10));
					articleList.add(new Article(5, "Diamante", "", 1, 120.78f));
					articleList.add(new Article(generateUniqueID(), "Piedra", "Duro como la piedra", 36, 1));
				}
				
			}
		} catch (NullPointerException e) {
			// TODO: handle exception			
			e.printStackTrace();
		}
		
		System.out.println("Inicialización completada!\n");
	}
	private static void shutdown() {
		System.out.println("\nSaliendo del programa...");
		
		scanner.close();
		
		System.out.println("Programa terminado");
	}
	
	public static void main(String[] args) {
		init();
				
		int opcion = -1;
		do {
			System.out.println("\n--ALMACEN--\n");
			System.out.println("1. Añadir nuevo artículo");
			System.out.println("2. Borrar artículos por id");
			System.out.println("3. Consulta de artículo por id");
			System.out.println("4. Listado de todos los artículos");
			System.out.println("0. Terminar programa");			
			
			System.out.print("Opcion: ");
			opcion = ScannerHelper.getValidInt(scanner, "Introduce un valor entero: ");
			
			if(articleList.size() == 0 && ((opcion == 2 || opcion == 3 || opcion == 4))) {
				System.err.println("No hay artículos actualmente");			
			}else {
				switch (opcion) {
					case 0:
						System.out.println("Hasta pronto!");
						saveFile();
						shutdown();
						break;
					case 1:
						articleList.add(buildScannerArticle(generateUniqueID()));
						break;				
					case 2:							
						System.out.print("Borrar por el ID: ");
						Article deleteme = searchArticle(ScannerHelper.getValidInt(scanner, "Introduce un entero: "));
						if(deleteme != null)
							articleList.remove(deleteme);
						else System.err.println("No se ha encontrado el artículo correspondiente");
						break;
					case 3:
						System.out.print("Buscar por el ID: ");
						Article searched = searchArticle(ScannerHelper.getValidInt(scanner, "Introduce un entero: "));
						if(searched != null)
							articleList.remove(searched);
						else System.err.println("No se ha encontrado el artículo correspondiente");
						break;					
					case 4:
						listArticles();
						break;
					default:
						System.err.println("Opción no encontrada");
				}
			}
		} while (opcion != 0);

	}
	
	private static Article buildScannerArticle(int id) {
				
		System.out.println("Creando artículo ID: " + id);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		System.out.print("Cantidad: ");
		int stock = 0;
		do{
			stock = ScannerHelper.getValidInt(scanner, "Introduce una cantidad: ");
			if(stock <= 0)
				System.err.print("La cantidad no puede ser menor o igual a 0: ");
		} while(stock <= 0);
		System.out.print("Precio: ");
		float price = 0;
		do {
			price = ScannerHelper.getValidFloat(scanner, "Introduce una cantidad: ");
			if(price < 0)
				System.err.print("La cantidad no puede ser menor que 0: ");
		}while(price < 0);
		System.out.print("Descripción: ");
		String description = scanner.nextLine();
		
		Article article = new Article(id, name, description, stock, price); 		
		System.out.println("Artículo: " + article.toString());
		
		return article;
	}
	
	private static int generateUniqueID() {		
		System.out.println("Generando identificador...");
		count++;
		
		if(searchArticle(count) == null)
			return count;
		else return generateUniqueID();		
	}
	
	private static Article searchArticle(int id) {
		for (Article article : articleList) {
			if(id == article.getId())
				return article;
		}
		return null;
	}
	
	private static void listArticles(){
		
		System.out.println("Lista-------------");
		for (Article article : articleList) {			
			System.out.println("\t" + article.toString());
		}
		System.out.println("Fin---------------");
		
		
	}
	
	//Load File
	//Save File
	private static void saveFile() {
		
	}
}
