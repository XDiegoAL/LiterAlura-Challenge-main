package com.literalura.challenge.principal;

import com.literalura.challenge.modelosDB.Autor;
import com.literalura.challenge.modelosDB.Libro;
import com.literalura.challenge.modelosurl.Lenguajes;
import com.literalura.challenge.modelosurl.ResultsResponse;
import com.literalura.challenge.modelosurl.ResultsUrl;
import com.literalura.challenge.repository.AutorRepository;
import com.literalura.challenge.repository.LibrosRepository;
import com.literalura.challenge.service.ConsumeApi;
import com.literalura.challenge.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumeApi consumeApi = new ConsumeApi();
    private final String URL_BASE = "http://gutendex.com/books/?";
    private Scanner scanner = new Scanner(System.in);
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibrosRepository librosRepository;
    private AutorRepository autorRepository;
    private List<Libro> librosRegistrados;
    private List<Autor> autoresRegistrados;

    public Principal(LibrosRepository repositorio, AutorRepository autorRepository) {
        this.librosRepository = repositorio;
        this.autorRepository = autorRepository;
    }

    public void mostrar(){
        Integer opc = -1;
        System.out.println("\n************* Bienvenido a LiterAlura =] *************");
        while (opc!=0) {
            imprimeMenu(false);
            try{
                opc = scanner.nextInt();
                scanner.nextLine();

                switch (opc){
                    case 1:
                        adquiereLibro();
                        break;
                    case 2:
                        muestraLibrosRegistrados();
                        break;
                    case 3:
                        muestraAutoresRegistrados();
                        break;
                    case 4:
                        muestraAutoresPorPeriodo();
                        break;
                    case 5:
                        muestraLibrosPorIdioma();
                        break;
                    case 6:
                        muestraTop10Libros();
                        break;
                    case 7:
                        buscaAutorPorNombre();
                        break;
                    case 8:
                        mostrarAutoresPorMilenio();
                        break;
                    case 9:
                        mostrarEstadisticas();
                        break;
                }

            }catch (Exception e){
                System.out.println("Ocurrió un error: respuesta no válida");
                scanner.nextLine();
            }

        }
    }

    //Caso 1
    @Transactional
    private void adquiereLibro(){
        System.out.println("\nIntroduzca el nombre del libro: ");
        var nombreLibro = scanner.nextLine();
        try {
            var respuesta = consumeApi.obtenerDatos(URL_BASE + "search=" + nombreLibro.replace(" ", "+").toLowerCase());
            if (respuesta.contains("{\"count\":0,\"next\":null,\"previous\":null,\"results\":[]}")) {
                System.out.println("Libro no encontrado");
            } else {
                ResultsUrl modelo = convierteDatos.adquiereDatos(respuesta, ResultsResponse.class).results().get(0); //Obtiene el primero

                // Verifica si el libro ya existe
                Optional<Libro> libroExistente = librosRepository.findByTituloLibroAndAutor_NombreAutor(modelo.tituloLibro(), modelo.autores().get(0).nombreAutor());
                if (libroExistente.isPresent()) {
                    System.out.println("El libro con el mismo título y autor ya existe.");
                    return;
                }
                Libro libro = new Libro(modelo);

                Optional<Autor> autorExistente = autorRepository.findByNombreAutor(modelo.autores().get(0).nombreAutor());
                Autor autor;
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(modelo.autores().get(0));
                    autor = autorRepository.save(autor);
                }

                try {
                    // Asociar el libro con el autor
                    libro.setAutor(autor);
                    librosRepository.save(libro);

                    // Agregar el libro a la lista de libros del autor
                    autor.getLibros().add(libro);
                    autorRepository.save(autor);

                    System.out.println("\nLibro agregado: \n" + libro);
                } catch (Exception e){
                    System.out.println("Ha ocurrido un error en el acceso a la base de datos: "+e);
                }
            }
        }catch (Exception e){
            System.out.println("Ocurrió un error: "+e);
        }
    }
    //Caso 2
    private void muestraLibrosRegistrados() {
        librosRegistrados = librosRepository.findAll();
        if(librosRegistrados.isEmpty()){
            System.out.println("Aún no hay libros registrados");
        }else{
            System.out.println("\n----------- Libros registrados -----------\n");
            librosRegistrados.stream()
                    .sorted(Comparator.comparing(Libro::getTituloLibro))
                    .forEach(System.out::println);
        }
    }
    //Caso 3
    private void muestraAutoresRegistrados() {
        autoresRegistrados = autorRepository.findAll();
        if(autoresRegistrados.isEmpty()){
            System.out.println("Aún no hay autores registrados");
        }else{
            System.out.println("\n----------- Autores registrados -----------\n");
            autoresRegistrados.stream()
                    .sorted(Comparator.comparing(Autor::getNombreAutor))
                    .forEach(System.out::println);
        }
    }

    //Caso 4
    private void muestraAutoresPorPeriodo() {
        Integer fechaInicial;
        Integer fechaFinal;
        try {
            System.out.println("Ingresa una fecha inicial: ");
            fechaInicial = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Ingresa una fecha final: ");
            fechaFinal = scanner.nextInt();
            scanner.nextLine();
            autoresRegistrados = autorRepository.findAutoresEntreAnios(fechaInicial,fechaFinal);

            if(autoresRegistrados.isEmpty()){
                System.out.println("No hay registro de ningún autor entre esas fechas\n");
            }else{
                System.out.println("\n----------- Autores entre "+fechaInicial+"-"+fechaFinal+" -----------\n");
                autoresRegistrados.stream()
                        .sorted(Comparator.comparing(Autor::getNombreAutor))
                        .forEach(System.out::println);
            }
        } catch (Exception e){
            System.out.println("Ocurrió un error: "+e);
            scanner.nextLine();
        }
    }
    //Caso 5
    public void muestraLibrosPorIdioma(){
        String respuestaIdioma;
        try {
            librosRegistrados = librosRepository.findAll();
            if (librosRegistrados.isEmpty()){
                System.out.println("No hay libros registrados aún\n");
                return;
            }
            var menu = """
                    Selecciona un idioma: 
                    es - Español 
                    en - Inglés
                    fr - Francés
                    pt - Portugués
                    -----------------------------""";
            System.out.println(menu);
            respuestaIdioma = scanner.nextLine();
            List<Libro> librosPorIdioma = librosRegistrados.stream()
                    .filter(e -> e.getLenguaje().equals(Lenguajes.fromString(respuestaIdioma)))
                    .toList();
            if(librosPorIdioma.isEmpty()){
                System.out.println("No existe ningún libro registrado con el lenguaje seleccionado\n");
                return;
            }else{
                System.out.println("\n-------------- Libros en "+Lenguajes.fromString(respuestaIdioma).toString().toLowerCase()+"--------------\n");
                librosPorIdioma.stream()
                        .sorted(Comparator.comparing(Libro::getTituloLibro))
                        .forEach(System.out::println);
            }
        } catch (Exception e){
            System.out.println("Ocurrió un error: "+e);
        }
    }
    //Caso 6
    private void muestraTop10Libros() {
        librosRegistrados = librosRepository.top10LibrosDescargados();
        if(librosRegistrados.isEmpty()){
            System.out.println("No hay libros registrados aún");
        }else{
            System.out.println("----------- Top libros más descargados -----------");
            librosRegistrados.forEach(System.out::println);
        }
    }

    //Caso 7
    private void buscaAutorPorNombre() {
        String nombreAutor;
        Optional<Autor> autorEncontrado;
        System.out.println("Ingresa el nombre del autor: ");
        try{
            nombreAutor = scanner.nextLine();
            autorEncontrado = autorRepository.encuentraAutorPorNombre(nombreAutor);
            if(autorEncontrado.isPresent()){
                System.out.println("\nAutor encontrado:\n"+autorEncontrado.get());
            }else{
                System.out.println("\nAutor no encontrado\n");
            }
        }catch (Exception e){
            System.out.println("Ocurrió un error: "+e);
        }
    }
    //Caso 8
    private void mostrarAutoresPorMilenio() {
        Integer year;
        Integer yearMas;
        System.out.println("\nIntroduce un año para buscar (Se buscará autores que nacieron entre ese año y +100 años):");
        try{
            year = scanner.nextInt();
            yearMas = year + 100;
            autoresRegistrados = autorRepository.encuentraAutoresPorMilenio(year,yearMas);
            if(autoresRegistrados.isEmpty()){
                System.out.println("No existen autores registrados dentro de los años de "+year+"-"+yearMas+'\n');
            }else{
                System.out.println("----------- Autores entre "+year+"-"+yearMas+" -----------");
                autoresRegistrados.stream()
                        .sorted(Comparator.comparing(Autor::getNombreAutor))
                        .forEach(System.out::println);
            }
        }catch (Exception e){
            System.out.println("Ha ocurrido un error: "+e);
        }
    }
    //Caso 9
    private void mostrarEstadisticas() {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        librosRegistrados = librosRepository.findAll();
        if(librosRegistrados.isEmpty()){
            System.out.println("No hay registros de libros");
        }else{
            var estadistica = librosRegistrados.stream()
                    .mapToDouble(Libro::getnDescargas)
                    .summaryStatistics();

            System.out.println("\n------------- Estadística -------------\n");
            System.out.println("Número de libros registrados: "+estadistica.getCount());
            System.out.println("Número máximo de descargas: "+estadistica.getMax());
            System.out.println("Número mínimo de descargas en un libro: "+estadistica.getMin());
            System.out.println("Promedio de descargas: "+estadistica.getAverage());
            System.out.println("Suma de todas las descargas: "+estadistica.getSum());
            System.out.println("\n---------------------------------------\n");
        }
    }



    private void imprimeMenu(boolean menuFinal){
        if(menuFinal){
            System.out.println("Cerrando la aplicación...");
        }else{
            var textoMenu = """
                    Elige una opción a través de un número:
                    1.- Buscar libro por título
                    2.- Listar libros registrados
                    3.- Listar autores registrados
                    4.- Listar autores vivos en un determinado periodo 
                    5.- Listar libros por idioma
                    6.- Muestra top 10 libros más descargados
                    7.- Buscar autor registrado por nombre
                    8.- Busca autores por siglo
                    9.- Estadísticas generales
                    0.- Salir
                   *****************************************************""";
            System.out.println(textoMenu);
        }

    }

}
