package YangJu.ZuoYe.One;

import YangJu.LianXi.One.HelloClassLoader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JuYang
 * @date 2021年06月23日17:49
 */
public class HelloXlassLoader extends ClassLoader{
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String name = "Hello";
        String methodName = "hello";
        ClassLoader CL = new HelloClassLoader();
        Class<?> clazz = CL.loadClass(name);
        for(Method m :clazz.getDeclaredMethods()){
            System.out.println(m);
        }
        Object obj =clazz.getDeclaredConstructor().newInstance();
        Method md = clazz.getMethod(methodName);
        md.invoke(obj);
    }


    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String filename = ".xlass";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        try {
           int length =  inputStream.available();
           byte[] byteArray = new byte[length];
           inputStream.read(byteArray);
            byte[] bytes = decode(byteArray);
            return super.defineClass(className,bytes,0,bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(className,e);

        }finally {
            close(inputStream);
        }
    }
    //解码
    private static byte[] decode(byte[] bytes){
        byte[] byteLength = new byte[bytes.length];
        for(int i= 0; i < byteLength.length ;i++){
          byteLength[i] = (byte) (255-byteLength[i]);
        }
        return byteLength;
    }
    private static void close(Closeable in){
        if(in != null){
            try{
                in.close();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
