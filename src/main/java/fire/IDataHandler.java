package fire;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IDataHandler {

    Manager readData() throws IOException, URISyntaxException;

    void writeData(Manager manager) throws IOException;
}
