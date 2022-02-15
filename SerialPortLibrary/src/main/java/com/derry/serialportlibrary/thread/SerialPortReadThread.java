package com.derry.serialportlibrary.thread;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/**
 * 同学们：串口消息读取线程
 *       开启接收消息的线程
 *       读取 串口数据 需要用到线程
 */
public abstract class SerialPortReadThread extends Thread {

    public abstract void onDataReceived(byte[] bytes);

    private static final String TAG = SerialPortReadThread.class.getSimpleName();
    private InputStream mInputStream; // 此输入流==mFileInputStream(关联mFd文件句柄)
    private byte[] mReadBuffer; // 用于装载读取到的串口数据

    public SerialPortReadThread(InputStream inputStream) {
        mInputStream = inputStream;
        mReadBuffer = new byte[1024]; // 缓冲区
    }

    @Override
    public void run() {
        super.run();
        // 相当于是一直执行？为什么要一直执行？因为只要App存活，就需要读取 底层发过来的串口数据
        while (!isInterrupted()) {
            try {
                if (null == mInputStream) {
                    return;
                }

                Log.i(TAG, "run: ");
                int size = mInputStream.read(mReadBuffer);

                if (-1 == size || 0 >= size) {
                    return;
                }

                byte[] readBytes = new byte[size];
                // 拷贝到缓冲区
                System.arraycopy(mReadBuffer, 0, readBytes, 0, size);

                Log.i(TAG, "run: readBytes = " + new String(readBytes));
                onDataReceived(readBytes); // 发出去-(间接的发到SerialPortActivity中去打印显示)
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * 关闭线程 释放资源
     */
    public void release() {
        interrupt();

        if (null != mInputStream) {
            try {
                mInputStream.close();
                mInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
