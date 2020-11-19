import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BaseFrameTest {

    @Test
    void writeString() {

        String testString = "+13000 Аванс 25.02.2020";
        String stringSS = null;
        BaseFrame.writeString(testString);

        try (BufferedReader br = new BufferedReader(new FileReader(BaseFrame.filePath))) {
            String stringS;
            while ((stringS = br.readLine()) != null) {
                if (stringS.equals(testString)){
                    stringSS = testString;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(stringSS, testString);
    }
}