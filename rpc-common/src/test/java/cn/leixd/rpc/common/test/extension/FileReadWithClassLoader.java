package cn.leixd.rpc.common.test.extension;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public class FileReadWithClassLoader {

    @Test
    public void test2() throws IOException {
        // 使用ClassLoader加载文件
        String path = "META-INF/leixd-rpc/cn.leixd.rpc.core.config.loader.ConfigLoader";
//        String path = "META-INF/lxd-rpc/cn.lxd.rpc.common.test.extension.Extension";
        ClassLoader classLoader = FileReadWithClassLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
//        String path = "META-INF/leixd-rpc/cn.leixd.rpc.common.test.extension.Extension";
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(path);
        System.out.println(resources.hasMoreElements());
        if (inputStream != null) {
            try {
                // 读取文件内容
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    System.out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("文件未找到");
        }
    }
}
