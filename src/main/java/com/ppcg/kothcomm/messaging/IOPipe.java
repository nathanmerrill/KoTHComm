package com.ppcg.kothcomm.messaging;

import java.io.*;

public class IOPipe {
    private final BufferedInputStream input;
    private final BufferedOutputStream output;
    private final BufferedInputStream error;
    private final Process process;


    public IOPipe(Process process){
        this.process = process;
        input = new BufferedInputStream(process.getInputStream());
        output = new BufferedOutputStream(process.getOutputStream());
        error = new BufferedInputStream(process.getErrorStream());
    }

    private String readStream(BufferedInputStream stream) throws IOException{
        int available = stream.available();
        if (available > 0) {
            byte[] bytes = new byte[available];
            stream.read(bytes);
            return new String(bytes);
        }
        return null;
    }

    private String readData(int timeout) throws IOException{
        long timeoutTime = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < timeoutTime) {
            String input = readStream(this.input);
            if (input != null){
                return input;
            }
            String error = readStream(this.error);
            if (error != null){
                throw new IOException("Error thrown by bot:"+error);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        throw new IOException("No input received after "+timeout+" milliseconds");
    }

    public void sendMessage(String message) throws IOException{
        output.write(message.getBytes());
        output.flush();
    }

    public String getMessage(int timeout) throws IOException{
        return readData(timeout);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        process.destroyForcibly();
    }
}
