package ArielYogev_RotemAvraham;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public interface Examable extends Serializable {
 public void createExam(Stock s) throws FileNotFoundException, IOException,ClassNotFoundException, Exception ;
}
