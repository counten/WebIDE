package cn.codeyourlife.execute;

public class HotSwapClassLoader extends ClassLoader {
    /**
     * ClassLoader.loadClass 是双亲委派模型，
     * 不能使用系统的类加载器，因为这个类加载器是独一份的，如果通过这个类加载器加载了我们的字节码，
     * 当客户端对源码进行了修改，再次提交运行时，应用程序类加载器会认为这个类已经加载过了，不会再次加载它，
     * 这样除非重启服务器，否则永远都无法执行客户端提交来的新代码。
     * 两个类相等需要满足以下 3 个条件：
     * - 同一个 .class 文件；
     * - 被同一个虚拟机加载；
     * - 被同一个类加载器加载；
     * 第三个条件更好破坏，即每次执行代码新建加载器；
     * 只有这个从客户端传来的类需要被多次加载，而这个类调用的其他类库方法之类的我们还是想要按照原有的双亲委派机制加载的
     * 加载客户端传来的类时使用自定义loadByte，不重载loadClass，虚拟机调用此类加载器时依然遵循双亲委派模型。
     * defineClass可以将存储字节码的数组装换成 Class 对象。
     * */

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classBytes) {
        return defineClass(null, classBytes, 0, classBytes.length);
    }
}
