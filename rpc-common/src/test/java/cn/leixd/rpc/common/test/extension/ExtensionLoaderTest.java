package cn.leixd.rpc.common.test.extension;

import cn.lxd.rpc.common.extension.ExtensionLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ExtensionLoaderTest {
    @Test
    public void getExtensionLoaderTest() {
        ExtensionLoader<?> extensionLoader = ExtensionLoader.getLoader(Extension.class);
        Assert.assertNotNull(extensionLoader);
    }

    @Test
    public void getExtensionTest() {
        ExtensionLoader<?> extensionLoader = ExtensionLoader.getLoader(Extension.class);
        Extension extension = (Extension) extensionLoader.getExtension("other");
        Assert.assertTrue(extension instanceof OtherExtension);
    }

    @Test
    public void getDefaultExtensionTest() {
        ExtensionLoader<?> extensionLoader = ExtensionLoader.getLoader(Extension.class);
        Object extension = extensionLoader.getDefaultExtension();
        if(extension instanceof  DefaultExtension){
            System.out.println("111111111");
        }
        Assert.assertTrue(extension instanceof DefaultExtension);

    }

    @Test
    public void testResourcesLoader() throws IOException {
        String path = "META-INF/lxd-rpc/cn.lxd.rpc.common.test.extension.Extension";
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(path);
        System.out.println(resources.hasMoreElements());
    }

}
