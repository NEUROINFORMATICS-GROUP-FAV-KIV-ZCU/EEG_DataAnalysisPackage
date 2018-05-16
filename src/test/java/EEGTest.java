import cz.zcu.kiv.Utils.Const;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static cz.zcu.kiv.Utils.Const.*;

public class EEGTest {

    public static void initalizeHDFSTest() throws IOException {
        FileUtils.deleteDirectory(new File(TEST_RESULTS_DIRECTORY));
        FileUtils.deleteDirectory(new File(TEST_OUTPUT_DIRECTORY));
        new File(TEST_RESULTS_DIRECTORY).mkdirs();
        new File(TEST_OUTPUT_DIRECTORY).mkdirs();
        System.setProperty(Const.HADOOP_USER_NAME_KEY,HADOOP_USER_NAME);
        FileSystem fs = FileSystem.get(URI.create(Const.HDFS_URI), Const.HDFS_CONF);
        Path local_file = new Path(LOCAL_TEST_DATA_DIRECTORY);
        Path remote_path = new Path(REMOTE_TEST_DATA_DIRECTORY);
        fs.copyFromLocalFile(local_file, remote_path);
        fs.close();
    }

    public static void unintializeHDFSTest() throws IOException {
        FileSystem fs = FileSystem.get(URI.create(Const.HDFS_URI), Const.HDFS_CONF);
        Path remote_path = new Path(REMOTE_TEST_DATA_DIRECTORY);
        fs.delete(remote_path,true);
        fs.close();
    }


}
