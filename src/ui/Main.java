package ui;

import model.AudioItem;
import model.Catalog;
import model.Playlist;
import service.CatalogService;
import storage.CatalogStorage;
import storage.PlaylistStorage;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        CatalogStorage catalogStorage = new CatalogStorage();
        Catalog catalog = catalogStorage.load();
        CatalogService service = new CatalogService(catalog);

        System.out.println("Приложението стартира...");
        System.out.println("Заредени обекти: " + service.items().size());
        System.out.println("Заредени playlist-и: " + service.playlists().size());
        System.out.println();

        while (true) {
            printMenu();
            int choice = readInt("Избери опция: ");

            switch (choice) {
                case 1 -> addItem(service);
                case 2 -> removeItem(service);
                case 3 -> searchItems(service);
                case 4 -> filterItems(service);
                case 5 -> showAllItems(service);
                case 6 -> createPlaylist(service);
                case 7 -> addToPlaylist(service);
                case 8 -> removeFromPlaylist(service);
                case 9 -> sortPlaylist(service);
                case 10 -> playlistInfo(service);
                case 11 -> showPlaylists(service);
                case 12 -> sortCatalog(service);

                case 13 -> {
                    catalogStorage.save(catalog);
                    System.out.println("Каталогът е записан във файл catalog.bin");
                }
                case 14 -> savePlaylistToFile(service);
                case 15 -> loadPlaylistFromFile(service);
                case 16 -> removePlaylist(service);

                case 0 -> {
                    catalogStorage.save(catalog);
                    System.out.println("Каталогът е записан, край на програмата");
                    return;
                }
                default -> System.out.println("Невалидна опция");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("----- МЕНЮ -----");
        System.out.println("1  - Добави обект");
        System.out.println("2  - Изтрий обект");
        System.out.println("3  - Търсене");
        System.out.println("4  - Филтриране");
        System.out.println("5  - Покажи всички обекти");
        System.out.println("6  - Създай playlist");
        System.out.println("7  - Добави обект в playlist");
        System.out.println("8  - Премахни обект от playlist");
        System.out.println("9  - Сортирай playlist по заглавие");
        System.out.println("10 - Информация за playlist");
        System.out.println("11 - Покажи playlists");
        System.out.println("12 - Сортирай каталога");
        System.out.println("13 - Запази каталога във файл");
        System.out.println("14 - Запази playlist във файл");
        System.out.println("15 - Зареди playlist от файл");
        System.out.println("16 - Изтрий playlist");

        System.out.println("0  - Изход (запазва каталога)");
    }

    private static void addItem(CatalogService service) {
        System.out.print("Заглавие: ");
        String title = sc.nextLine();
        if (title.isBlank()) {
            System.out.println("Заглавието не може да е празно");
            return;
        }

        System.out.print("Жанр: ");
        String genre = sc.nextLine();

        int duration = readInt("Продължителност (сек): ");

        System.out.print("Автор/изпълнител: ");
        String author = sc.nextLine();

        int year = readInt("Година: ");

        System.out.println("Избери категория:");
        System.out.println("1 - песен");
        System.out.println("2 - подкаст");
        System.out.println("3 - аудиокнига");
        System.out.println("4 - албум");
        int c = readInt("Избор: ");

        String category;
        if (c == 1) category = "песен";
        else if (c == 2) category = "подкаст";
        else if (c == 3) category = "аудиокнига";
        else if (c == 4) category = "албум";
        else {
            System.out.println("Невалидна категория, обектът не е добавен");
            return;
        }

        AudioItem item = new AudioItem(title, genre, duration, author, year, category);
        service.addItem(item);

        System.out.println("Обектът е добавен");
    }

    private static void removeItem(CatalogService service) {
        if (service.items().isEmpty()) {
            System.out.println("Каталогът е празен, няма какво да се изтрие");
            return;
        }
        System.out.print("Въведи заглавие: ");
        String title = sc.nextLine();

        if (service.removeByTitle(title)) {
            System.out.println("Обектът е изтрит");
        } else {
            System.out.println("Няма такъв обект");
        }
    }

    private static void showAllItems(CatalogService service) {
        List<AudioItem> items = service.items();

        if (items.isEmpty()) {
            System.out.println("Каталогът е празен");
            return;
        }
        for (AudioItem item : items) {
            System.out.println(item);
        }
    }

    private static void searchItems(CatalogService service) {
        if (service.items().isEmpty()) {
            System.out.println("Каталогът е празен,няма какво да се търси");
            return;
        }

        System.out.println("1 - Търсене по заглавие (част)");
        System.out.println("2 - Търсене по автор (част)");
        System.out.println("3 - Търсене по жанр (точно)");
        int choice = readInt("Избери: ");

        List<AudioItem> result;

        if (choice == 1) {
            System.out.print("Въведи част от заглавие: ");
            result = service.searchTitle(sc.nextLine());
        } else if (choice == 2) {
            System.out.print("Въведи част от автор: ");
            result = service.searchAuthor(sc.nextLine());
        } else if (choice == 3) {
            System.out.print("Въведи жанр: ");
            result = service.searchGenre(sc.nextLine());
        } else {
            System.out.println("Невалидна опция");
            return;
        }

        printItemsResult(result);
    }

    private static void filterItems(CatalogService service) {
        if (service.items().isEmpty()) {
            System.out.println("Каталогът е празен, няма какво да се филтрира");
            return;
        }

        System.out.println("1 - Филтър по жанр");
        System.out.println("2 - Филтър по автор");
        System.out.println("3 - Филтър по година");
        System.out.println("4 - Филтър по категория");
        int choice = readInt("Избери: ");

        List<AudioItem> result;

        if (choice == 1) {
            System.out.print("Жанр: ");
            result = service.filterGenre(sc.nextLine());
        } else if (choice == 2) {
            System.out.print("Автор: ");
            result = service.filterAuthor(sc.nextLine());
        } else if (choice == 3) {
            int year = readInt("Година: ");
            result = service.filterYear(year);
        } else if (choice == 4) {
            System.out.print("Категория (песен/подкаст/аудиокнига/албум): ");
            result = service.filterCategory(sc.nextLine());
        } else {
            System.out.println("Невалидна опция");
            return;
        }

        printItemsResult(result);
    }

    private static void printItemsResult(List<AudioItem> result) {
        if (result == null || result.isEmpty()) {
            System.out.println("Няма резултати");
        } else {
            for (AudioItem item : result) {
                System.out.println(item);
            }
        }
    }

    private static void createPlaylist(CatalogService service) {
        System.out.print("Име на playlist: ");
        String name = sc.nextLine();

        if (service.createPlaylist(name)) {
            System.out.println("Playlist създаден");
        } else {
            System.out.println("Неуспешно създаване (възможно е вече да има такъв)");
        }
    }
    private static void removePlaylist(CatalogService service) {
        if (service.playlists().isEmpty()) {
            System.out.println("Няма playlist-и за изтриване");
            return;
        }

        System.out.print("Име на playlist за изтриване: ");
        String name = sc.nextLine().trim();

        if (service.removePlaylist(name)) {
            System.out.println("Playlist е изтрит");
        } else {
            System.out.println("Няма такъв playlist");
        }
    }

    private static void showPlaylists(CatalogService service) {
        List<Playlist> playlists = service.playlists();

        if (playlists.isEmpty()) {
            System.out.println("Няма playlist-и");
            return;
        }

        for (Playlist p : playlists) {
            System.out.println(p);
        }
    }

    private static void addToPlaylist(CatalogService service) {
        if (service.playlists().isEmpty()) {
            System.out.println("Няма playlist-и, първо трябва да се създаде playlist");
            return;
        }
        if (service.items().isEmpty()) {
            System.out.println("Каталогът е празен, няма какво да се добави в playlist");
            return;
        }

        System.out.print("Име на playlist: ");
        String playlist = sc.nextLine().trim();

        System.out.print("Заглавие на обекта (точно): ");
        String title = sc.nextLine().trim();

        if (service.addToPlaylist(playlist, title)) {
            System.out.println("Добавено в playlist");
        } else {
        if (service.getPlaylist(playlist) == null) {
            System.out.println("Няма такъв playlist");
        } else if (service.findByTitle(title) == null) {
            System.out.println("Няма обект с такова заглавие");
        } else {
            System.out.println("Грешка при добавяне");
        }
    }
}

    private static void removeFromPlaylist(CatalogService service) {
        if (service.playlists().isEmpty()) {
            System.out.println("Няма playlist-и");
            return;
        }
        System.out.print("Име на playlist: ");
        String playlist = sc.nextLine();

        System.out.print("Заглавие на обекта (точно): ");
        String title = sc.nextLine();

        if (service.removeFromPlaylist(playlist, title)) {
            System.out.println("Обектът е премахнат от playlist");
        } else {
            System.out.println("Грешка при премахване");
        }
    }

    private static void sortPlaylist(CatalogService service) {

        if (service.playlists().isEmpty()) {
            System.out.println("Няма playlist-и, няма какво да се сортира");
            return;
        }

        System.out.print("Име на playlist: ");
        String playlistName = sc.nextLine().trim();

        Playlist p = service.getPlaylist(playlistName);

        if (p == null) {
            System.out.println("Няма такъв playlist");
            return;
        }

        if (p.getItems().isEmpty()) {
            System.out.println("Playlist е празен, няма какво да се сортира");
            return;
        }

        service.sortPlaylistByTitle(playlistName);

        System.out.println("Playlist е сортиран по заглавие:");

        for (AudioItem item : p.getItems()) {
            System.out.println(item);
        }
    }


    private static void playlistInfo(CatalogService service) {
        if (service.playlists().isEmpty()) {
            System.out.println("Няма playlist-и");
            return;
        }

        System.out.print("Име на playlist: ");
        String name = sc.nextLine();

        Playlist p = service.getPlaylist(name);
        if (p == null) {
            System.out.println("Няма такъв playlist");
            return;
        }

        System.out.println("Информация за playlist:");
        System.out.println(p);

        if (p.getItems().isEmpty()) {
            System.out.println("Playlist е празен");
        } else {
            System.out.println("Обекти в playlist:");
            for (AudioItem item : p.getItems()) {
                System.out.println(item);
            }
        }
        int total = service.playlistDuration(name);
        System.out.println("Обща продължителност: " + total + " сек");
    }

    private static void sortCatalog(CatalogService service) {
        if (service.items().isEmpty()) {
            System.out.println("Каталогът е празен, няма какво да се сортира");
            return;
        }
        System.out.println("1 - Сортиране по заглавие");
        System.out.println("2 - Сортиране по година");
        int c = readInt("Избери: ");

        if (c == 1) {
            service.sortByTitle();
            System.out.println("Каталогът е сортиран по заглавие:");
            showAllItems(service);
        } else if (c == 2) {
            service.sortByYear();
            System.out.println("Каталогът е сортиран по година:");
            showAllItems(service);
        } else {
            System.out.println("Невалиден избор");
        }
    }

    private static void savePlaylistToFile(CatalogService service) {
        if (service.playlists().isEmpty()) {
            System.out.println("Няма playlist-и за запис");
            return;
        }

        PlaylistStorage ps = new PlaylistStorage();

        System.out.print("Име на playlist: ");
        String name = sc.nextLine();

        Playlist p = service.getPlaylist(name);
        if (p == null) {
            System.out.println("Няма такъв playlist");
            return;
        }

        System.out.print("Име на файл (пример: rock.bin): ");
        String fileName = sc.nextLine();

        if (!fileName.endsWith(".bin")) {
            fileName = fileName + ".bin";
        }

        ps.save(p, fileName);
        System.out.println("Playlist е записан във файл: " + fileName);
    }

    private static void loadPlaylistFromFile(CatalogService service) {
        PlaylistStorage ps = new PlaylistStorage();

        System.out.print("Име на файл (пример: rock.bin): ");
        String fileName = sc.nextLine();

        Playlist p = ps.load(fileName);
        if (p == null) {
            System.out.println("Неуспешно зареждане (няма файл или е повреден)");
            return;
        }

        if (service.getPlaylist(p.getName()) != null) {
            System.out.println("Вече има playlist с име: " + p.getName());
            return;
        }

        service.playlists().add(p);
        System.out.println("Зареден playlist: " + p.getName());
    }

    private static int readInt(String text) {
        while (true) {
            System.out.print(text);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Въведи число: ");
            }
        }
    }
}
