package cn.leixd.rpc.common.test.extension;

public class DefaultExtension implements Extension{
    @Override
    public void doSomething() {
        System.out.println("DefaultExtension");
    }
}
