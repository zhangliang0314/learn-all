IO:
  1，文件IO（磁盘IO）
  2，网络IO



文件IO
public int read(byte b[]) throws IOException {
    return readBytes(b, 0, b.length);
}
private native int readBytes(byte b[], int off, int len) throws IOException;

read：
  系统调用read方法，将磁盘上内容拷贝到OS内核缓冲区；DMA执行
  将内容从内核缓冲区拷贝到jvm堆内存中；CPU执行


网络IO：
    private int socketRead(FileDescriptor fd,
                           byte b[], int off, int len,
                           int timeout)throws IOException {
        return socketRead0(fd, b, off, len, timeout);
    }

   private native int socketRead0(FileDescriptor fd,byte b[], int off, int len,int timeout)throws IOException;

在read过程中, 首先是是否达到文件流末尾, 长度是否符合等校验. 校验完成之后, 通过调用socketRead(); 将socket缓冲区的数据读取进b中.
此时如果发生 ConnectionResetException异常, 对方可能是关闭了连接, 但是内和缓冲区中, 可能还会有数据没有读取完, 则后面再尝试读取一次, 将剩余的内容读取出来.
socketRead()方法最终调用native方法 socketRead0().
