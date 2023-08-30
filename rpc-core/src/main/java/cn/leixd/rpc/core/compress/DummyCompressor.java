package cn.leixd.rpc.core.compress;

/**
 * 伪压缩器，啥事不干。有一些序列化工具压缩已经做得很好了，无需再压缩
 *
 */
public class DummyCompressor implements Compressor {
    @Override
    public byte[] compress(byte[] bytes) {
        return bytes;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return bytes;
    }
}
