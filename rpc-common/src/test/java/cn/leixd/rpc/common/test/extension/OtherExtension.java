package cn.leixd.rpc.common.test.extension;

public class OtherExtension implements Extension{
    @Override
    public void doSomething() {
        System.out.println("OtherExtension");
    }
}
