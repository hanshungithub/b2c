import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestMethod {

    @Test
    public void ftpTest() {
        FTPClient client = new FTPClient();
        try {
            client.connect("192.168.48.128");
            client.login("ftpuser", "hanshun");

            FileInputStream inputStream = new FileInputStream(new File("E:\\source\\cjk.jpg"));
            client.changeWorkingDirectory("/home/ftpuser/www/images");
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.storeFile("cjk.jpg",inputStream );
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
