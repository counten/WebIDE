package cn.codeyourlife.execute;

public class ClassModifier {
    /**
     * Class文件中常量池的起始偏移
     * cafe babe 0000 0034
     * 魔数      次、主版本号
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * tag为1表示此常量类型为 Utf8字符串。
     */
    private static final int CONSTANT_UTF8_TAG = 1;

    /**
     * tag为2-12对应的常量长度 4,4,8,8,2,2,4,4,4,4
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 4, 4, 8, 8, 2, 2, 4, 4, 4, 4};

    /**
     * 1个和2个字节的符号数，用来在classByte数组中取tag和len
     * tag用u1个字节表示
     * len用u2个字节表示
     */
    private static final int u1 = 1;
    private static final int u2 = 2;

    /**
     * 要被修改的字节码文件
     */
    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 从0x00000008开始向后取2个字节，表示的是常量池中常量的个数
     *
     * @return 常量池中常量的个数
     */
    public int getConstantPoolCount() {
        return ByteUtils.byte2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }

    /**
     * 字节码修改器，替换字节码常量池中 oldStr 为 newStr
     *
     * @param oldStr
     * @param newStr
     * @return 修改后的字节码字节数组
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        // 获取常量的个数
        int cpc = getConstantPoolCount();
        // 常量池真实的常量起始位置10
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 1; i < cpc; i++) {
            int tag = ByteUtils.byte2Int(classByte, offset, u1);
            if (tag == CONSTANT_UTF8_TAG) {
                // Utf8 字符串类型，才可能是对System的调用
                // U2(2字节)表示字符串的长度
                int len = ByteUtils.byte2Int(classByte, offset + u1, u2);
                offset += u1 + u2;
                // U1(1字节)*len 的字符串
                String str = ByteUtils.byte2String(classByte, offset, len);
                if (str.equals(oldStr)) {
                    // 命中需要替换的字符串
                    byte[] strReplaceBytes = ByteUtils.string2Byte(newStr);
                    byte[] intReplaceBytes = ByteUtils.int2Byte(strReplaceBytes.length, u2);
                    // 替换新的字符串的长度
                    classByte = ByteUtils.byteReplace(classByte, offset - u2, u2, intReplaceBytes);
                    // 替换字符串本身
                    classByte = ByteUtils.byteReplace(classByte, offset, len, strReplaceBytes);
                    return classByte;  // 就一个地方需要改，改完就可以返回了
                } else {
                    offset += len;
                }
            } else {
                // 另外10种常量类型不用处理
                // 增加tag和index长度的偏移
                offset += u1 + CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

}
