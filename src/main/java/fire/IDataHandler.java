package fire;

import java.io.IOException;

public interface IDataHandler {

    Manager readData() throws IOException;

    void writeData(Manager manager) throws IOException;
}
