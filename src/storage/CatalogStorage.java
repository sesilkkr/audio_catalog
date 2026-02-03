package storage;

import model.Catalog;

import java.io.*;

public class CatalogStorage {

    private static final String FILE = "catalog.bin";

    public void save(Catalog catalog) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE))) {

            out.writeObject(catalog);

        } catch (IOException e) {
            System.out.println("Грешка при запис на каталога");
        }
    }

    public Catalog load() {
        File file = new File(FILE);
        if (!file.exists()) {
            return new Catalog();
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE))) {

            return (Catalog) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Грешка при четене на каталога");
            return new Catalog();
        }
    }
}
