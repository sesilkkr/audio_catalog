package storage;
import model.Playlist;
import java.io.*;

public class PlaylistStorage {
    public void save(Playlist playlist, String fileName) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(playlist);

        } catch (IOException e) {
            System.out.println("Грешка при запис на playlist");
        }
    }

    public Playlist load(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            return (Playlist) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Грешка при четене на playlist");
            return null;
        }
    }
}
