package cn.codeyourlife.service;

import cn.codeyourlife.compile.StringSourceCompiler;
import cn.codeyourlife.execute.CustomThreadFactory;
import cn.codeyourlife.execute.JavaClassExecutor;


import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.util.List;
import java.util.concurrent.*;

public class ExecuteStringSourceService {
    /* 客户端发来的程序的运行时间限制 */
    private static final int RUN_TIME_LIMITED = 15;

    /* N_THREAD = N_CPU + 1，因为是 CPU 密集型的操作 */
    private static final int N_THREAD = 5;

    private static final String THREAD_NAME_PREFIX = "CylProgramRunner_";

    // 不使用Executors 其提供的很多方法默认使用的都是无界的 LinkedBlockingQueue，
    // 高负载情境下，无界队列很容易导致 OOM，而 OOM 会导致所有请求都无法处理
    private static final ExecutorService pool = new ThreadPoolExecutor(
            N_THREAD, N_THREAD, 0L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(N_THREAD),
            new CustomThreadFactory(THREAD_NAME_PREFIX));

    private static final String WAIT_WARNING = "服务器忙，请稍后提交";
    private static final String NO_OUTPUT = "Nothing.";

    public String execute(String source, String systemIn) {
        // 编译结果收集器
        DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<>();

        // 编译源代码
        byte[] classBytes = StringSourceCompiler.compile(source, compileCollector);

        // 编译不通过，获取并返回编译错误信息
        if (classBytes == null) {
            // 获取编译错误信息
            List<Diagnostic<? extends JavaFileObject>> compileError = compileCollector.getDiagnostics();
            StringBuilder compileErrorRes = new StringBuilder();
            for (Diagnostic diagnostic : compileError) {
                compileErrorRes.append("Compilation error at ");
                compileErrorRes.append(diagnostic.getLineNumber());
                compileErrorRes.append(".");
                compileErrorRes.append(System.lineSeparator());
            }
            return compileErrorRes.toString();
        }

        // 运行字节码的main方法
        Callable<String> runTask = () -> JavaClassExecutor.execute(classBytes, systemIn);

        Future<String> res = null;
        try {
            res = pool.submit(runTask);
        } catch (RejectedExecutionException e) {
            return WAIT_WARNING;
        }

        // 获取运行结果，处理非客户端代码错误
        String runResult;
        try {
            // 限制程序的执行时间，保证安全
            runResult = res.get(RUN_TIME_LIMITED, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            runResult = "Program interrupted.";
        } catch (ExecutionException e) {
            runResult = e.getCause().getMessage();
        } catch (TimeoutException e) {
            runResult = "Time Limit Exceeded.";
        } finally {
            res.cancel(true);
        }
        return runResult != null ? runResult : NO_OUTPUT;
    }
}
