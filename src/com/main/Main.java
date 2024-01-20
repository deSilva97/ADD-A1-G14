package com.main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.helper.ScannerHelper;
import com.model.Article;

public class Main {

	static final String PATH_NAME = "artículos.dat";
	
	private static Scanner scanner;
	private static ArrayList<Article> articleList;
	
	static int count;

	private static void init() {
		System.out.println("Inicializando...");
		count = 0;
		scanner = new Scanner(System.in);
		
		try {
			File file = new File(PATH_NAME);		
			if(file.exists()) {
				articleList = loadFile();
			} else {
				System.out.println("No hay archivo disponible por el momento");
				articleList = new ArrayList<Article>();
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
			System.out.println("5. Exportar .csv");
			System.out.println("0. Terminar programa");			
			
			System.out.print("Opcion: ");
			opcion = ScannerHelper.getValidInt(scanner, "Introduce un valor entero: ");			
			
			if(articleList.size() == 0 && ((opcion == 2 || opcion == 3 || opcion == 4 || opcion == 5))) {
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
				
		System.out.println("\nCreando artículo ID: " + id);
		System.out.print("> Nombre: ");
		String name = scanner.nextLine();
		System.out.print("> Cantidad: ");
		int stock = 0;
		do{
			stock = ScannerHelper.getValidInt(scanner, ">> Introduce una cantidad: ");
			if(stock <= 0)
				System.err.print(">> La cantidad no puede ser menor o igual a 0: ");
		} while(stock <= 0);
		System.out.print("> Precio: ");
		float price = 0;
		do {
			price = ScannerHelper.getValidFloat(scanner, ">> Introduce una cantidad: ");
			if(price < 0)
				System.err.print(">> La cantidad no puede ser menor que 0: ");
		}while(price < 0);
		System.out.print("> Descripción: ");
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
	private static ArrayList<Article> loadFile(){
		System.out.println("Cargando...");
		ArrayList<Article> list = new ArrayList<Article>();
		
		try (FileInputStream fileIn = new FileInputStream(PATH_NAME);
				ObjectInputStream reader = new ObjectInputStream(fileIn);){
			
			boolean eof = false;			
			
			while(!eof) {
				
				try {
					list = (ArrayList<Article>) reader.readObject();	
					
				} catch (EOFException e) {
					eof = true;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();					
				}
				catch (Exception e) {
					System.err.println("Ha ocurrido un error inesperado al leer el archivo");	
				}
				
			}
			
			return list;
			
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error inesperado al cargar el archivo");			
		}		
		return null;
	}
	//Save File
	private static boolean saveFile() {
		System.out.println("Guardando...");
		
		try (FileOutputStream fileOut = new FileOutputStream(PATH_NAME);
				ObjectOutputStream writter = new ObjectOutputStream(fileOut);){					
			
			writter.writeObject(articleList);
			
			return true;
		} catch (Exception e) {
			System.err.println("Ha ocurrido un error inesperado al guardar el archivo");
		}
		
		return false;
	}
}
